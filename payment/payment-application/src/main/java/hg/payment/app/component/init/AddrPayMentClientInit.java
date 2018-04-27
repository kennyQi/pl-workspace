package hg.payment.app.component.init;

import java.util.List;

import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.app.service.local.client.PaymentClientLocalService;
import hg.payment.domain.model.client.PaymentClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 *@类功能说明：系统初始化加载支付平台的所有客户端到缓存
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年9月3日上午11:08:07
 *
 */
@Component
public class AddrPayMentClientInit implements InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(AddrPayMentClientInit.class);
	
//	@Autowired
//	private PaymentClientLocalService paymentClientLocalService;
	@Autowired
	private PaymentClientCacheManager paymentClientCacheManager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("开始加载支付平台客户端缓存...");
//		PaymentClientLocalQO qo = new PaymentClientLocalQO();
//		List<PaymentClient> listPaymentClients = paymentClientLocalService.queryList(qo);
		paymentClientCacheManager.refreshPaymentClient();
		logger.info("支付平台客户端缓存完毕");
	}

}
