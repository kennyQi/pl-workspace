package hg.payment.spi.inter;

import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;


public interface AlipayPayOrderSpiService {

	/**
	 * 添加支付宝渠道支付单
	 * @param command
	 * @return
	 * @throws PaymentException
	 */
	public String createAlipayPayOrder(CreateAlipayPayOrderCommand command) throws PaymentException;
	
	/**
	 * 支付宝反馈支付成功
	 * @param id
	 * @throws PaymentException
	 */
//	public void paySuccess(String id) throws PaymentException;
	
	/**
	 * 支付宝反馈支付失败 
	 * @param id
	 * @throws PaymentException
	 */
//	public void payFail(String id) throws PaymentException;
	
	
	/**
	 * 通知商城支付结果
	 * @param id
	 * @throws PaymentException
	 */
//	public void notifyClient(String id)throws PaymentException;
	
	/**
	 * 组装传递给商城的数据
	 * @param id
	 * @return
	 */
//	public String notifyClientParam(AlipayPayOrderQO qo) throws PaymentException;
	
	
	
	
	
	
}
