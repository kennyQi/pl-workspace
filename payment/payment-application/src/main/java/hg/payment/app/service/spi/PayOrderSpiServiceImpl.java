package hg.payment.app.service.spi;



import hg.log.util.HgLogger;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.service.local.payorder.AlipayPayOrderLocalService;
import hg.payment.app.service.local.payorder.HJBPayOrderLocalService;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.app.service.local.refund.AlipayRefundLocalService;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.PayOrderProcessor;
import hg.payment.pojo.command.spi.payorder.alipay.AlipayRefundCommand;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.command.spi.payorder.hjb.CreateHJBPayOrderCommand;
import hg.payment.pojo.dto.payorder.PayOrderDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.pojo.qo.payorder.PayOrderQO;
import hg.payment.spi.inter.PayOrderSpiService;
import hg.system.common.util.MD5HashUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("payOrderSpiService")
public class PayOrderSpiServiceImpl implements PayOrderSpiService{
	
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private AlipayPayOrderLocalService alipayPayOrderLocalService;
	@Autowired
	private HJBPayOrderLocalService hjbPayOrderLocalService;
	@Autowired
	private AlipayRefundLocalService alipayRefundLocalService;

	@Override
	public List<PayOrderDTO> queryPayOrderList(PayOrderQO qo) throws PaymentException {
		
		HgLogger.getInstance().debug("luoyun", "商城查询订单订状态参数：" + JSON.toJSONString(qo));
		if(qo == null){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"查询订单QO不能为空");
		}
		
		if(StringUtils.isBlank(qo.getPaymentClientID())){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"请提供客户端编号");
		}
		
		PaymentClient client = cache.getPaymentClient(qo.getPaymentClientID());
		if(client == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,"客户端不存在");
		}
		
		if(StringUtils.isBlank(qo.getPaymentClienKeys())){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"请提供客户端密钥");
		}
		if(!MD5HashUtil.toMD5(client.getSecretKey()).equals(qo.getPaymentClienKeys())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTMATCHKEY,
					"客户端的支付平台密钥不正确");
		}
		if(!client.getValid()){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_DONOTVALID, 
					"客户端不可用");
		}
		if(StringUtils.isBlank(qo.getOrderNo())){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"请提供订单编号");
		}
		
		PayOrderLocalQO localQo = new PayOrderLocalQO();
		localQo.setClientTradeNo(qo.getOrderNo());
		localQo.setPaymentClientID(qo.getPaymentClientID());
		localQo.setSecretKey(qo.getPaymentClienKeys());
		localQo.setPaySuccess(qo.getPaySuccess());
		List<PayOrder> payOrderList = payOrderLocalService.queryPayOrderList(localQo);
		List<PayOrderDTO> dtoList = new ArrayList<PayOrderDTO>();
		for(PayOrder payOrder:payOrderList){
			PayOrderDTO dto = new PayOrderDTO();
//			if(payOrder.getPayOrderProcessor().getPayStatus() == PayOrderProcessor.CLOSE){ //交易已关闭
//				dto.setPayStatus(4); 
//			}else 
			if(payOrder.getPayOrderProcessor().haveRefund()){ //有退款  
				dto.setPayStatus(3);
			}else if(payOrder.getPayOrderProcessor().isPaySuccess()){  //支付成功
				dto.setPayStatus(1);
			}else if(payOrder.getPayOrderProcessor().isFailed()){ //支付失败
				dto.setPayStatus(2); 
			}
			dto.setBuyer_email(payOrder.getPayerInfo().getPayerAccount());
			dto.setTrade_no(payOrder.getThirdPartyTradeNo());
			dtoList.add(dto);
		}
		String json = JSON.toJSONString(dtoList);
		HgLogger.getInstance().debug("luoyun", "商城查询订单订状态,支付平台返回值：" + json);
		return dtoList;
	}

	@Override
	public String pay(String clientID,String clientKey, Integer payType, String context)
			throws PaymentException {
		HgLogger.getInstance().debug("lixuanxuan", "创建支付单请求！客户端ID："+ clientID + "!支付渠道ID:" + payType + "!渠道参数：" + context);
		//校验数据是否有效
		if(StringUtils.isBlank(clientID)){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,"请提供客户端编号");
		}
		if(payType == null){
			throw new PaymentException(PaymentException.RESULT_PAYTYPE_WITHOUTID,"请提供支付类型");
		}
		if(StringUtils.isBlank(context)){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCONTEXT_WITHOUTID,"请提供合法的渠道参数");
		}
		if (StringUtils.isBlank(clientKey)) {
			throw new PaymentException(
					PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商户的支付平台密钥");
		}
		//校验数据真实合法性
		PaymentClient paymentClient = cache.getPaymentClient(clientID);       
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端不存在");
		}
		//校验支付客户端是否可用
		if(paymentClient.getValid() == false){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_DONOTVALID,
					"客户端不可用");
		}
		//校验商户密钥		
		if (!MD5HashUtil.toMD5(paymentClient.getSecretKey()).equals(
				clientKey)) {
			throw new PaymentException(
					PaymentException.RESULT_PAYMENTCLIENT_NOTMATCHKEY,
					"商户的支付平台密钥不正确");
		}
		 //校验支付渠道是否是客户端可用的支付渠道
		if(!paymentClient.isValidPayChannel(payType)){
			throw new PaymentException(PaymentException.RESULT_PAYCHANNEL_NOTVALID,
					"支付渠道在该客户端不可用");
		}		
		
		/**
		 * 根据支付类型解析json
		 */
		/******************************* 支付宝 *******************************/
		if(payType.equals(PayChannel.PAY_TYPE_ALIPAY)){
			CreateAlipayPayOrderCommand alipayPayOrderCommand = JSON.parseObject(context,CreateAlipayPayOrderCommand.class);
			//重复支付判断
			payOrderLocalService.duplicatePay(alipayPayOrderCommand.getPayOrderCreateDTO());
			return alipayPayOrderLocalService.createAlipayPayOrder(alipayPayOrderCommand);
		}
		/******************************* 汇金宝 *******************************/
		if(payType.equals(PayChannel.PAY_TYPE_HJB)){
			CreateHJBPayOrderCommand hjbPayOrderCommand = JSON.parseObject(context,CreateHJBPayOrderCommand.class);
			//重复支付判断
			payOrderLocalService.duplicatePay(hjbPayOrderCommand.getPayOrderCreateDTO());
			return hjbPayOrderLocalService.createHJBPayOrder(hjbPayOrderCommand);
		}
		return null;
	}

	@Override
	public String queryPayOrder(PayOrderQO qo) throws PaymentException {
		HgLogger.getInstance().debug("luoyun", "商城查询订单订状态参数：" + JSON.toJSONString(qo));
		PayOrderLocalQO localQo = new PayOrderLocalQO();
		localQo.setClientTradeNo(qo.getOrderNo());
		localQo.setPaymentClientID(qo.getPaymentClientID());
		localQo.setSecretKey(qo.getPaymentClienKeys());
		String json = payOrderLocalService.queryPayOrder(localQo);
		HgLogger.getInstance().debug("luoyun", "商城查询订单订状态,支付平台返回值：" + json);
		return json;
	}
	
	
	@Override
	public void refund(String clientID,String clientKey, Integer payType, String context)
			throws PaymentException {
		
		HgLogger.getInstance().debug("luoyun", "商城请求退款，客户端ID："+ clientID + "渠道ID:" + payType + "退款参数：" + context);
		
		if(StringUtils.isBlank(clientID)){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,"请提供客户端编号");
		}
		if(payType == null){
			throw new PaymentException(PaymentException.RESULT_PAYTYPE_WITHOUTID,"请提供退款类型");
		}
		if(StringUtils.isBlank(context)){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCONTEXT_WITHOUTID,"请提供退款参数");
		}
		if (StringUtils.isBlank(clientKey)) {
			throw new PaymentException(
					PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商户的支付平台密钥");
		}
		PaymentClient paymentClient = cache.getPaymentClient(clientID);       
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端不存在");
		}
		if(paymentClient.getValid() == false){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_DONOTVALID,
					"客户端不可用");
		}
		if (!MD5HashUtil.toMD5(paymentClient.getSecretKey()).equals(
				clientKey)) {
			throw new PaymentException(
					PaymentException.RESULT_PAYMENTCLIENT_NOTMATCHKEY,
					"商户的支付平台密钥不正确");
		}
		if(!paymentClient.isValidPayChannel(payType)){
			throw new PaymentException(PaymentException.RESULT_PAYCHANNEL_NOTVALID,
					"支付渠道在该客户端不可用");
		}	
		
		/**
		 * 根据支付类型解析json
		 */
		/******************************* 支付宝 *******************************/
		if(payType.equals(PayChannel.PAY_TYPE_ALIPAY)){
			AlipayRefundCommand command = JSON.parseObject(context,AlipayRefundCommand.class);
			alipayRefundLocalService.submitAlipayBatchRefundRequest(command);
		}
	}

}
