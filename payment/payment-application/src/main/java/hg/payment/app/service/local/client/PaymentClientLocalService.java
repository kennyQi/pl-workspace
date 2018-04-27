package hg.payment.app.service.local.client;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.dao.client.PaymentClientDAO;
import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.pojo.command.admin.CreatePaymentClientCommand;
import hg.payment.pojo.command.admin.ModifyPaymentClientCommand;
import hg.payment.pojo.command.admin.StartPaymentClientCommand;
import hg.payment.pojo.command.admin.StopPaymentClientCommand;
import hg.payment.pojo.exception.PaymentException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PaymentClientLocalService extends BaseServiceImpl<PaymentClient, PaymentClientLocalQO, PaymentClientDAO>{

	@Autowired
	private PaymentClientDAO dao;
	@Autowired
	private PaymentClientCacheManager paymentClientCacheManager;
	
	@Override
	protected PaymentClientDAO getDao() {
		return dao;
	}
	
	/**
	 * 添加支付客户端
	 * @param command
	 * @return String 支付客户端ID
	 */
	public String createPaymentClient(CreatePaymentClientCommand command) throws PaymentException{
		
		String id = UUIDGenerator.getUUID();
		command.setId(id);
		PaymentClient paymentClient = new PaymentClient();
		paymentClient.createPaymentClient(command);
		save(paymentClient);
		//更新缓存
		paymentClientCacheManager.refreshPaymentClient();
		return paymentClient.getId();
	}
	
	
	/**
	 * 停用支付客户端
	 * @param command
	 * @return
	 */
	public Boolean stopPaymentClient(StopPaymentClientCommand command)throws PaymentException{
		if(StringUtils.isBlank(command.getId())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_WITHOUTID,
					"请提供客户端系统编号 ");
		}
		PaymentClient paymentClient = get(command.getId());
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端系统不存在 ");
		}
		paymentClient.stopPaymentClient(command);
		update(paymentClient);
		//更新缓存
		paymentClientCacheManager.refreshPaymentClient();
		return true;
	}
	
	/**
	 * 启用支付客户端
	 * @param command
	 * @return
	 */
	public Boolean startPaymentClient(StartPaymentClientCommand command)throws PaymentException{
		if(StringUtils.isBlank(command.getId())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_WITHOUTID,
					"请提供客户端系统编号 ");
		}
		PaymentClient paymentClient = get(command.getId());
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端系统不存在 ");
		}
		paymentClient.startPaymentClient(command);
		update(paymentClient);
		//更新缓存
		paymentClientCacheManager.refreshPaymentClient();
		return true;
	}
	
	/**
	 * 修改支付客户端信息
	 * @param command
	 */
	public Boolean modifyPaymentClient(ModifyPaymentClientCommand command)throws PaymentException{
		if(StringUtils.isBlank(command.getId())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_WITHOUTID,
					"请提供客户端系统编号 ");
		}
		PaymentClient paymentClient = get(command.getId());
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端系统不存在 ");
		}
		paymentClient.modifyPaymentClient(command);
		update(paymentClient);
		//更新缓存
		paymentClientCacheManager.refreshPaymentClient();
		return true;
	}
	
	

}
