package hg.payment.app.service.local.payorder;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.common.util.OrderNumberUtils;
import hg.payment.app.dao.payorder.AlipayPayOrderDAO;
import hg.payment.app.pojo.qo.payorder.AlipayPayOrderLocalQO;
import hg.payment.domain.common.util.PaymentConstant;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.alipay.AlipayPayOrder;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;
import hg.system.command.backlog.CreateBacklogCommand;
import hg.system.exception.HGException;
import hg.system.service.BacklogService;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;




@Service
@Transactional
public class AlipayPayOrderLocalService extends BaseServiceImpl<AlipayPayOrder,AlipayPayOrderLocalQO,AlipayPayOrderDAO>{

	@Autowired
	private AlipayPayOrderDAO dao;
	@Autowired
	private AlipayPayChannel alipay;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private BacklogService backlogService;
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	
	
	@Override
	protected AlipayPayOrderDAO getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public String createAlipayPayOrder(CreateAlipayPayOrderCommand command) throws PaymentException{
		
		//记录商城请求支付宝订单日志
		HgLogger.getInstance().debug("luoyun", "商城请求支付宝订单参数："+JSON.toJSONString(command));
		
		//重复支付判断
		payOrderLocalService.duplicatePay(command.getPayOrderCreateDTO());
		
		AlipayPayOrder alipayPayOrder = new AlipayPayOrder();
		if(command.getPayOrderCreateDTO() == null){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供支付请求基本参数");
		}
		//校验支付请求基本参数
		try{
			payOrderLocalService.validBasicPayRequestParams(command.getPayOrderCreateDTO());
		}catch(PaymentException e){
			throw e;
		}
		
		
		//校验合作商户ID
		if(StringUtils.isBlank(command.getPartner())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供支付宝合作商户编号");
		}
		
		//校验支付宝商户密钥
		if(StringUtils.isBlank(command.getKeys())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供支付宝商户密钥");
		}
		
		//校验订单名称
		if(StringUtils.isBlank(command.getSubject())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供订单名称");
		}
		
		//判断支付渠道是否为支付宝
		if(PayChannel.PAY_TYPE_ALIPAY != command.getPayOrderCreateDTO().getPayChannelType()){
			throw new PaymentException(PaymentException.CREATE_PAYORDER_INVALID_PAY_CHANNEL,
					"该接口只能请求支付宝支付功能");
		}
		
		
		PaymentClient paymentClient = cache.getPaymentClient(command.getPayOrderCreateDTO().getPaymentClientId());  
		alipayPayOrder.setPaymentClient(paymentClient);
		//工具类生成订单编号
		command.getPayOrderCreateDTO().setId(OrderNumberUtils.createOrderNo());
		//获取支付宝渠道Bean，设置支付宝参数，不入库
//		alipay.setChannelParams(command);
		//设置订单支付渠道信息
		alipayPayOrder.setPayChannel(alipay);
	    //设置支付订单基本参数
		alipayPayOrder.createPayOrderCommand(command.getPayOrderCreateDTO());
		//设置支付订单支付宝参数
		alipayPayOrder.createAlipayPayOrder(command);
		dao.save(alipayPayOrder);
		
		//创建订单时同步创建待办事项
		CreateBacklogCommand backlogcommand = new CreateBacklogCommand();
		backlogcommand.setType(PaymentConstant.BACKLOG_TYPE_PAYMENT_PAY);
		backlogcommand.setName("支付平台通知" + paymentClient.getName() + "支付宝支付结果");
		Map<String,String> submitDesMap = new HashMap<String,String>();
		//组装待办事项Json描述字符串
		submitDesMap.put("orderId", alipayPayOrder.getId());
		String submitDescription = JSONArray.toJSONString(submitDesMap);
		backlogcommand.setDescription(submitDescription);
		backlogcommand.setExecuteNum(5);
		
		try{
			backlogService.createBacklog(backlogcommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
		
		
		return payOrderLocalService.submitPayRequest(alipayPayOrder);
		
//	    alipayPayOrder.getPayOrderProcessor().receivePayRequest(alipayPayOrder);
		
		
//		try{
//			//生成支付宝请求地址
//			String url = alipaySubmit.buildRequest(alipayPayOrder.buildRequestParam(),"get","确认");
//			HgLogger.getInstance().info("luoyun", "支付平台支付宝订单："+ alipayPayOrder.getId() 
//					+ "商城订单号：" + alipayPayOrder.getClientTradeNo()  + "支付宝请求地址：" + url);
//			//推送给支付宝支付成功
//			alipayPayOrder.getPayOrderProcessor().submitPayRequestSuccess(alipayPayOrder);
//			dao.update(alipayPayOrder);
//			return url;
//		}catch(Exception e){
//			e.printStackTrace();
//			//推送给支付宝支付失败
//			alipayPayOrder.getPayOrderProcessor().submitPayRequestFail(alipayPayOrder);
//			dao.update(alipayPayOrder);
//			return "";
//		}
			
		
		
	}
	
	
	
	
	

}
