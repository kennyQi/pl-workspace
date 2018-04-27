package hg.payment.spi.inter;

import hg.payment.pojo.dto.payorder.PayOrderDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.pojo.qo.payorder.PayOrderQO;

import java.util.List;

public interface PayOrderSpiService{
	
	/**
	 * 商城查询订单是否支付成功
	 * @param qo
	 * @return json格式
	 * @throws PaymentException
	 */
	public List<PayOrderDTO> queryPayOrderList(PayOrderQO qo) throws PaymentException;
	
	/**
	 * 支付
	 * @param clientID 客户端ID
	 * @param clientKey 客户端密钥
	 * @param payType 支付渠道
	 * @param context 渠道参数（JSON格式）
	 * @return 对应的支付跳转表单模块
	 * @throws PaymentException
	 */
	public String pay(String clientID,String clientKey,Integer payType,String context) throws PaymentException;
	
	
	/**
	 * 商城查询订单是否支付成功
	 * @param qo
	 * @return json格式
	 * @throws PaymentException
	 */
	public String queryPayOrder(PayOrderQO qo) throws PaymentException;
	
	/**
	 * 退款
	 * @param clientID 客户端ID
	 * @param clientKey 客户端密钥
	 * @param payType 支付渠道
	 * @param context 渠道参数（JSON格式）
	 * @throws PaymentException
	 */
	public void refund(String clientID,String clientKey, Integer payType, String context)
			throws PaymentException;

}
