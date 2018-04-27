package hg.payment.app.service.local.payorder;

import hg.common.component.BaseServiceImpl;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.common.util.OrderNumberUtils;
import hg.payment.app.dao.payorder.HJBPayOrderDAO;
import hg.payment.app.pojo.qo.payorder.HJBPayOrderLocalQO;
import hg.payment.domain.common.util.PaymentConstant;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.hjbPay.HJBPayOrder;
import hg.payment.pojo.command.spi.payorder.hjb.CreateHJBPayOrderCommand;
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

import com.alibaba.fastjson.JSONArray;

@Service
@Transactional
public class HJBPayOrderLocalService extends BaseServiceImpl<HJBPayOrder,HJBPayOrderLocalQO,HJBPayOrderDAO>{

	@Autowired
	private HJBPayOrderDAO dao;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private BacklogService backlogService;
	@Autowired
	private HJBPayChannel hjbPay;
	
	@Override
	protected HJBPayOrderDAO getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public String createHJBPayOrder(CreateHJBPayOrderCommand command)throws PaymentException {
		
		if(command.getPayOrderCreateDTO() == null){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供汇金宝支付请求基本参数");
		}
		//校验支付请求基本参数
		try{
			payOrderLocalService.validBasicPayRequestParams(command.getPayOrderCreateDTO());
		}catch(PaymentException e){
			throw e;
		}
		
		//校验商城在汇金宝唯一标识
		if(StringUtils.isBlank(command.getChannelId())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商城在汇金宝唯一标识ChannelId");
		}
		//校验商城在汇金宝商户编号
		if(StringUtils.isBlank(command.getMerCode())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商城在汇金宝商户编号MerCode");
		}
		//校验收款方开户行银行类型
		if(StringUtils.isBlank(command.getPayeeBankType())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供收款方开户行银行类型");
		}
		//校验收款方银行开户名
		if(StringUtils.isBlank(command.getPayeeBankAccountName())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供收款方银行开户名");
		}
		//校验付款方银行类型
		if(StringUtils.isBlank(command.getPayerBankType())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供付款方银行类型");
		}
		//校验付款方用户类型 
		if(StringUtils.isBlank(command.getCustomerType())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供付款方用户类型 ");
		}
		//校验IP
		if(StringUtils.isBlank(command.getCallerIp())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供服务器IP ");
		}
		//校验交易名称
		if(StringUtils.isBlank(command.getTradeName())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供交易名称");
		}
		//校验商品名称
		if(StringUtils.isBlank(command.getSubject())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商品名称");
		}
		//校验商品描述
		if(StringUtils.isBlank(command.getDescription())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商品描述");
		}
		//判断支付渠道是否为汇金宝
		if(PayChannel.PAY_TYPE_HJB != command.getPayOrderCreateDTO().getPayChannelType()){
			throw new PaymentException(PaymentException.CREATE_PAYORDER_INVALID_PAY_CHANNEL,
					"该接口只能请求汇金宝支付功能");
		}
		
		HJBPayOrder hjbPayOrder = new HJBPayOrder();
		PaymentClient paymentClient = cache.getPaymentClient(command.getPayOrderCreateDTO().getPaymentClientId());  
		hjbPayOrder.setPaymentClient(paymentClient);
		//工具类生成订单编号
		command.getPayOrderCreateDTO().setId(OrderNumberUtils.createOrderNo());
		//设置订单支付渠道信息
		hjbPayOrder.setPayChannel(hjbPay);
		//设置支付订单基本参数
		hjbPayOrder.createPayOrderCommand(command.getPayOrderCreateDTO());
		//设置支付订单支付宝参数
		hjbPayOrder.createhjbPayOrder(command);
		dao.save(hjbPayOrder);
		
		
		//创建订单时同步创建待办事项
		CreateBacklogCommand backlogcommand = new CreateBacklogCommand();
		backlogcommand.setType(PaymentConstant.BACKLOG_TYPE_PAYMENT_PAY);
		backlogcommand.setName("支付平台通知" + paymentClient.getName() + "汇金宝支付结果");
		Map<String,String> submitDesMap = new HashMap<String,String>();
		//组装待办事项Json描述字符串
		submitDesMap.put("orderId", hjbPayOrder.getId());
		String submitDescription = JSONArray.toJSONString(submitDesMap);
		backlogcommand.setDescription(submitDescription);
		backlogcommand.setExecuteNum(5);
		
		try{
			backlogService.createBacklog(backlogcommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
		
		
		return payOrderLocalService.submitPayRequest(hjbPayOrder);
//			
//		hjbPayOrder.getPayOrderProcessor().receivePayRequest(hjbPayOrder);
//		try{
//			//生成汇金宝请求报文
//			String message = hjbPayOrder.buildRequestParam();
//			HgLogger.getInstance().info("luoyun", "支付平台汇金宝订单："+ hjbPayOrder.getId() 
//					+ "商城订单号：" + hjbPayOrder.getClientTradeNo()  + "支付请求报文：" + message);
//			//推送给汇金宝支付成功
//			hjbPayOrder.getPayOrderProcessor().submitPayRequestSuccess(hjbPayOrder);
//			dao.update(hjbPayOrder);
//			return message;
//		}catch(Exception e){
//			e.printStackTrace();
//			//推送给汇金宝支付失败
//			hjbPayOrder.getPayOrderProcessor().submitPayRequestFail(hjbPayOrder);
//			dao.update(hjbPayOrder);
//			HgLogger.getInstance().info("luoyun", "支付平台汇金宝订单："+ hjbPayOrder.getId() 
//					+ "商城订单号：" + hjbPayOrder.getClientTradeNo()  + "支付请求报文生成失败");
//			return "";
//		}
		
	}
}
