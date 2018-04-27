package hg.payment.app.cache;


import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.app.service.local.client.PaymentClientLocalService;
import hg.payment.domain.model.client.PaymentClient;
import hg.system.model.meta.Province;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;

/**
 * 
 * 
 *@类功能说明：支付平台的客户端系统缓存
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年9月3日上午10:53:05
 *
 */
@Component
public class PaymentClientCacheManager {
	
	@Resource
	private Jedis jedis;
	@Autowired
	private PaymentClientLocalService paymentClientLocalService;
	
	public final static String PAYMENT_CLIENT_CACHE = "payment_client_cache";
	
	/**
	 * 更新支付平台所有的客户端系统缓存
	 * @param paymentClients
	 */
	public void refreshPaymentClient() {
		//清空缓存
		jedis.del(PAYMENT_CLIENT_CACHE);
		//重新获取客户端缓存数据
		PaymentClientLocalQO qo = new PaymentClientLocalQO();
		List<PaymentClient> listPaymentClients = paymentClientLocalService.queryList(qo);
		for (PaymentClient p : listPaymentClients) {
			jedis.hset(PAYMENT_CLIENT_CACHE, p.getId(), JSON.toJSONString(p));
		}
	}
	
	/**
	 * 根据id从缓存获取客户端信息
	 * @param id
	 * @return
	 */
	public PaymentClient getPaymentClient(String id) {
		if (id == null){
			return null;
		}
		String json = jedis.hget(PAYMENT_CLIENT_CACHE, id);
		return json != null ? JSON.parseObject(json, PaymentClient.class) : null;
	}
}
