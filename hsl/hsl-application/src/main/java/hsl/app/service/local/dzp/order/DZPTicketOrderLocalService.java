package hsl.app.service.local.dzp.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.common.ApiResponse;
import hg.log.util.HgLogger;
import hsl.app.common.util.OrderUtil;
import hsl.app.dao.dzp.order.DZPTicketOrderDAO;
import hsl.app.dao.dzp.policy.DZPTicketPolicyDAO;
import hsl.app.service.local.dzp.DZPWService;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.pojo.command.dzp.order.CreateDZPTicketOrderCommand;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.dzp.order.DZPTicketOrderQO;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 电子票订单
 *
 * @author zhurz
 * @since 1.8
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DZPTicketOrderLocalService extends BaseServiceImpl<DZPTicketOrder, DZPTicketOrderQO, DZPTicketOrderDAO> {

	@Autowired
	private DZPTicketOrderDAO dao;
	@Autowired
	private DZPTicketPolicyDAO policyDAO;

	@Autowired
	private DZPWService dzpwService;

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 日志工具
	 *
	 * @return
	 */
	private HgLogger logger() {
		return HgLogger.getInstance();
	}

	/**
	 * REDIS日期序列KEY前缀
	 */
	private final static String DZP_ORDER_NO_DATE_SEQ = "HSL:DZP_ORDER_NO_DATE_SEQ:";

	@Override
	protected DZPTicketOrderDAO getDao() {
		return dao;
	}

	/**
	 * 获得日期的序列，当其超过9999时重置为0
	 *
	 * @param date 日期
	 * @return
	 */
	private synchronized Integer getDateSeq(Date date) {
		Long seq = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			String dateKey = DZP_ORDER_NO_DATE_SEQ + DateUtil.formatDateTime(date, "yyyyMMdd");
			seq = jedis.incr(dateKey);
			if (seq > 9999) {
				seq = 0L;
				jedis.set(dateKey, "0");
			}
			jedis.expire(dateKey, (int) TimeUnit.DAYS.toSeconds(2));
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return seq.intValue();
	}

	/**
	 * 获得可用的门票政策
	 *
	 * @param policyId
	 * @return
	 */
	private DZPTicketPolicy getAvailableTicketPolicy(String policyId, Integer version) {
		if (StringUtils.isBlank(policyId) && version != null)
			throw new ShowMessageException("门票政策不可用");
		DZPTicketPolicyQO qo = new DZPTicketPolicyQO();
		qo.setId(policyId);
		qo.setClosed(false);
		qo.setFinished(false);
		qo.setVersion(version);
		qo.setType(DZPTicketPolicy.TICKET_POLICY_TYPE_GROUP, DZPTicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		DZPTicketPolicy policy = policyDAO.queryUnique(qo);
		if (policy == null)
			throw new ShowMessageException("门票政策不可用");
		return policy;
	}

	/**
	 * 向电子票务下单
	 *
	 * @param order
	 */
	private void createDZPWOrder(DZPTicketOrder order) {

	}

	/**
	 * 创建电子票订单
	 *
	 * @param command 下单命令
	 * @return
	 */
	public String createOrder(CreateDZPTicketOrderCommand command) {

		// 获取可用门票政策
		DZPTicketPolicy policy = getAvailableTicketPolicy(command.getTicketPolicyId(), command.getTicketPolicyVersion());

		// 生成订单号
		Date now = new Date();
		String orderNo = OrderUtil.createOrderNo(now, getDateSeq(now), 4, command.getFromType());

		// 创建订单
		DZPTicketOrder order = new DZPTicketOrder().manager().createOrder(command, orderNo, policy);

		// 保存订单
		getDao().save(order);

		// 调用下单接口向电子票务下单
		CreateTicketOrderResponse response = dzpwService.createTicketOrder(order.manager().buildDZPWCreateTicketOrderCommand());

		if (!ApiResponse.RESULT_SUCCESS.equals(response.getResult())) {
			ShowMessageException e = new ShowMessageException("下单失败");
			System.out.println(JSON.toJSONString(response, true));
			logger().error(this.getClass(), "zhurz", response.getMessage(), e);
			throw e;
		}

		// 处理下单结果
		order.manager().processDZPWCreateOrderResult(response.getTicketOrder());
		getDao().update(order);

		return JSON.toJSONString(order, new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (Hibernate.isInitialized(value)) return value;
				return null;
			}
		});
	}


}
