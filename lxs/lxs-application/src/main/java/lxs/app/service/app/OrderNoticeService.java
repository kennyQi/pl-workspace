package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import lxs.app.dao.app.OrderNoticeDao;
import lxs.domain.model.app.OrderNotice;
import lxs.pojo.command.app.AddOrderNoticeCommand;
import lxs.pojo.qo.app.OrderNoticeQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @类功能说明：订单通知service
 * @类修改者：
 * @修改日期：2015-10-26 上午11:14:48
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:14:48
 */
@Service
@Transactional
public class OrderNoticeService extends
		BaseServiceImpl<OrderNotice, OrderNoticeQO, OrderNoticeDao> {

	@Autowired
	private OrderNoticeDao ordernoticedao;

	@Override
	protected OrderNoticeDao getDao() {
		// TODO Auto-generated method stub
		return ordernoticedao;
	}
/**
 * 
	 * 
	 * @方法功能说明： 添加订单通知
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:14:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
 */
	public void addBean(AddOrderNoticeCommand command) {
		HgLogger.getInstance().info("jinyy", "【OrderNoticeService】【addBean】【AddOrderNoticeCommand】"+JSON.toJSONString(command));
		OrderNotice ordernotice = new OrderNotice();
		ordernotice.setId(UUIDGenerator.getUUID());
		ordernotice.setContactPerson(command.getContactPerson());
		ordernotice.setPhonoNumber(command.getPhonoNumber());
		save(ordernotice);

	}
/**
 * 
	 * 
	 * @方法功能说明：修改订单通知
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:14:24
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
 */
	public void updateBean(AddOrderNoticeCommand command) {
		HgLogger.getInstance().info("jinyy", "【OrderNoticeService】【updateBean】【AddOrderNoticeCommand】"+JSON.toJSONString(command));
		OrderNotice ordernotice = ordernoticedao.get(command.getId());
		ordernotice.setContactPerson(command.getContactPerson());
		ordernotice.setPhonoNumber(command.getPhonoNumber());
		getDao().update(ordernotice);

	}

}
