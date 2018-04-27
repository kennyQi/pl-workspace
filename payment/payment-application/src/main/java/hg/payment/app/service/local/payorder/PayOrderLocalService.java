package hg.payment.app.service.local.payorder;



import hg.common.component.BaseServiceImpl;
import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.log.util.HgLogger;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.dao.payorder.PayOrderDAO;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.domain.common.util.PaymentConstant;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.PayOrderProcessor;
import hg.payment.pojo.command.admin.ModifyPayOrderCommand;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.system.command.backlog.ExecuteBacklogCommand;
import hg.system.common.util.MD5HashUtil;
import hg.system.exception.HGException;
import hg.system.model.backlog.Backlog;
import hg.system.qo.BacklogQo;
import hg.system.service.BacklogService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 *@类功能说明：提供给后台支付订单查询
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年9月10日下午3:05:40
 *
 */
@Service
@Transactional
public class PayOrderLocalService extends BaseServiceImpl<PayOrder, PayOrderLocalQO, PayOrderDAO>{

	@Autowired
	private PayOrderDAO dao;
	@Autowired
	private BacklogService backlogService;
	@Autowired
	private PaymentClientCacheManager cache;
	
	@Override
	protected PayOrderDAO getDao() {
		return dao;
	}
	
	/**
	 * 支付完成第三方反馈后修改订单信息
	 * @param command
	 */
	public void payOrder(ModifyPayOrderCommand command) throws PaymentException{
		HgLogger.getInstance().debug("luoyun", "支付完成第三方反馈后修改订单信息"+JSON.toJSONString(command));
		if(StringUtils.isBlank(command.getPayOrderId())){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_WITHOUT_ID,
					"请提供支付平台订单号");
		}
		PayOrder payOrder = get(command.getPayOrderId());
		if(payOrder == null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,
					"支付平台订单:"+command.getPayOrderId()+"不存在");
		}
		payOrder.getPayerInfo().setPayerAccount(command.getPayerAccount());
		payOrder.setThirdPartyTradeNo(command.getThirdPartyTradeNo());
		payOrder.setOrderRemark(command.getOrderRemark());
		if(command.getPaySuccess()){
			payOrder.getPayOrderProcessor().receivePaySuccessNotice(payOrder);
		}else{
			payOrder.getPayOrderProcessor().receivePayFailNotice(payOrder);
		}
		dao.update(payOrder);
		
	}
	
	
	/**
	 * 同步通知商城成功
	 * @param id
	 * @throws PaymentException
	 */
	@Deprecated
	public void notifySuccess(String id)throws PaymentException{
		if(StringUtils.isBlank(id)){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_WITHOUT_ID,
					"请提供支付平台订单号");
		}
		PayOrder payOrder = get(id);
		if(payOrder == null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,
					"支付平台订单:"+id+"不存在");
		}
		
		//不允许重复通知
		if(payOrder.getPayOrderProcessor().isNotify()){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_DUPLICATE_NOTIFY,
					"订单" + id + "已经成功通知商城，不允许重复通知");
		}
		
		payOrder.getPayOrderProcessor().noticePayResultSuccess(payOrder);
		dao.update(payOrder);
		
		//查询和订单对应的待办事项
		BacklogQo backlogQo = new BacklogQo();
		backlogQo.setDescription(id);
		Backlog backlog = new Backlog();
	    backlog = backlogService.queryUnique(backlogQo);
		if(backlog == null){
			throw new PaymentException(PaymentException.RESULT_BACKLOG_NOTFOUND,"待办事项不存在");
		}
		//设置待办事项执行成功，此订单的待办事项就不会在定时器任务列表中了
		ExecuteBacklogCommand executeCommand = new ExecuteBacklogCommand();
		executeCommand.setSuccess(true);
		executeCommand.setBacklogId(backlog.getId());
		try{
			backlogService.executeBacklog(executeCommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
		
	}
	
	
	/**
	 * 通知商城支付结果
	 * @param id
	 * @throws PaymentException
	 */
	public void notifyClientPay(String id)throws PaymentException{
		
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城支付结果支付平台订单号" + id);
		if(StringUtils.isBlank(id)){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_WITHOUT_ID,
					"请提供支付平台订单号");
		}
		PayOrder payOrder = get(id);
		if(payOrder == null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,
					"支付平台订单"+id+"不存在");
		}
		if(payOrder.getPayOrderProcessor().isNotify()){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_DUPLICATE_NOTIFY,
					"订单" + id + "已经成功通知商城支付结果，不允许重复通知");
		}
		//查询和订单对应的待办事项
		BacklogQo backlogQo = new BacklogQo();
		backlogQo.setDescription(id);
		backlogQo.setType(PaymentConstant.BACKLOG_TYPE_PAYMENT_PAY);
		backlogQo.setExecuteCountValid(false);
		Backlog backlog = new Backlog();
	    backlog = backlogService.queryUnique(backlogQo);
		if(backlog == null){
			throw new PaymentException(PaymentException.RESULT_BACKLOG_NOTFOUND,"待办事项不存在");
		}
		//检查通知次数
		if(backlog.getBacklogInfo().getExecuteNum() <= backlog.getBacklogStatus().getExecuteCount()){
			throw new PaymentException(HGException.BACKLOG_EXECUTE_COUNT_NOTVALID,
					backlog.getBacklogInfo().getName() + "已超过规定商城通知次数，不再通知");
		}
		//缓存中读取客户端信息
		PaymentClient client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
		if(client == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端不存在");
		}
		//订单没有收到支付成功或者支付失败的通知 就不通知商城
		if(payOrder.getPayOrderProcessor().getPayStatus() != PayOrderProcessor.PAY_SUCCESS
				&& payOrder.getPayOrderProcessor().getPayStatus() != PayOrderProcessor.PAY_FAIL){
			return ;
		}
		
		//传递商城订单编号给商城
		String url = client.getClientMessageURL() + "?type=" + PaymentConstant.NOTIFY_CLIENT_TYPE_PAY + "&orderNo=" + payOrder.getClientTradeNo();
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城地址：" + url);
		//通过商城的消息地址通知商城
		HttpResponse res = HttpUtil.reqForGet(url,null);
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城订单" + payOrder.getClientTradeNo() + "response状态码" + res.getRespoinsCode());
		if(res.getRespoinsCode() == 200){
			//设置订单通知状态为通知业务系统成功
			payOrder.getPayOrderProcessor().noticePayResultSuccess(payOrder);
			dao.update(payOrder);
		}else{
			//设置订单通知状态为通知业务系统失败
			payOrder.getPayOrderProcessor().noticePayResultFail(payOrder);
			dao.update(payOrder);
			HgLogger.getInstance().debug("luoyun", res.getResult());
		}
		
		//执行待办事项
		ExecuteBacklogCommand executeCommand = new ExecuteBacklogCommand();
		executeCommand.setSuccess(payOrder.getPayOrderProcessor().isNotify());
		executeCommand.setBacklogId(backlog.getId());
		try{
			backlogService.executeBacklog(executeCommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
	}
	
	
	/**
	 * 判断订单页面显示的支付状态
	 * @param order
	 * @return
	 */
	public String showOrderPayStatus(PayOrder order){
		
		if(order.getPayOrderProcessor()== null){
			return "-";
		}else if(order.getPayOrderProcessor().haveRefund()){
			return "有退款记录";
		}else if(order.getPayOrderProcessor().isPaySuccess()){
			return "支付成功";
		}else if(order.getPayOrderProcessor().isFailed()){
			return "支付失败";
		}else if(order.getPayOrderProcessor().isSubmitted()){
			return "已推送";
		}else{
			return "未推送";
		}
	}
	
	/**
	 * 判断订单页面显示的通知状态
	 * @param order
	 * @return
	 */
	public String showOrderNotify(PayOrder order){
		if(order.getPayOrderProcessor()== null){
			return "-";
		}else if(order.getPayOrderProcessor().isNotify()){
			return "已通知商城支付结果";
		}else{
			return "-";
		}
	}
	
	/**
	 * 查询订单
	 * @param orderNo
	 * @return
	 * @throws PaymengException
	 */
	public List<PayOrder> queryPayOrderList(PayOrderLocalQO qo) throws PaymentException{
		
		List<PayOrder> payOrderList = dao.queryList(qo);
		return payOrderList;
	}
	
	/**
	 * 校验支付请求基本参数
	 * @param payOrderCreateDTO
	 * @return
	 * @throws PaymentException
	 */
	public void validBasicPayRequestParams(PayOrderRequestDTO payOrderRequestDTO) throws PaymentException{
		
		//校验支付客户端
		if(StringUtils.isBlank(payOrderRequestDTO.getPaymentClientId())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供客户端编号");
		}
		PaymentClient paymentClient = cache.getPaymentClient(payOrderRequestDTO.getPaymentClientId());       
		if(paymentClient == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端不存在");
		}
		if(paymentClient.getValid() == false){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_DONOTVALID,
					"客户端不可用");
		}
		//校验商户密钥
		if(StringUtils.isBlank(payOrderRequestDTO.getPaymentClienKeys())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商户的支付平台密钥");
		}
		if(!MD5HashUtil.toMD5(paymentClient.getSecretKey()).equals(payOrderRequestDTO.getPaymentClienKeys())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTMATCHKEY,
					"商户的支付平台密钥不正确");
		}
		//校验商户订单编号
		if(StringUtils.isBlank(payOrderRequestDTO.getClientTradeNo())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供商户订单编号");
		}
		//校验支付渠道 
		if(payOrderRequestDTO.getPayChannelType() == null){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供支付渠道");
		}
		if(!paymentClient.isValidPayChannel(payOrderRequestDTO.getPayChannelType())){
			throw new PaymentException(PaymentException.RESULT_PAYCHANNEL_NOTVALID,
					"支付渠道在该客户端不可用");
		}
		//校验收款方的帐号
		if(StringUtils.isBlank(payOrderRequestDTO.getPayeeAccount())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供收款方的帐号");
		}
		//校验收款户名
		if(StringUtils.isBlank(payOrderRequestDTO.getPayeeName())){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供收款户名");
		}
		//校验金额
		if(payOrderRequestDTO.getAmount() == null){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请提供金额");
		}
		if(payOrderRequestDTO.getAmount() < 0){
			throw new PaymentException(PaymentException.CREATE_PAYOEDER_WITHOUTPARAM,
					"请金额必须大于0.1元");
		}

		
	}
	
	/**
	 * 判断重复通知
	 * @param payOrderRequestDTO
	 * @throws PaymentException
	 */
	public void duplicatePay(PayOrderRequestDTO payOrderRequestDTO)throws PaymentException{
		
		PayOrderLocalQO qo = new PayOrderLocalQO();
		qo.setPaymentClientID(payOrderRequestDTO.getPaymentClientId());
		qo.setSecretKey(payOrderRequestDTO.getPaymentClienKeys());
		qo.setClientTradeNo(payOrderRequestDTO.getClientTradeNo());
		qo.setPaySuccess(true);
		PayOrder payOrder = dao.queryUnique(qo);
		if(payOrder != null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_DUPLICATE_PAY,"该订单已支付");
		}
		
	}
	
	
	public String submitPayRequest(PayOrder payOrder)throws PaymentException{
		
//		//创建订单时同步创建待办事项
//		CreateBacklogCommand backlogcommand = new CreateBacklogCommand();
//		backlogcommand.setType(BacklogType.ba);
//		backlogcommand.setName("支付平台通知" + payOrder.getPaymentClient().getName() + "支付宝支付结果");
//		Map<String,String> submitDesMap = new HashMap<String,String>();
//		//组装待办事项Json描述字符串
//		submitDesMap.put("orderId", payOrder.getId());
//		String submitDescription = JSONArray.toJSONString(submitDesMap);
//		backlogcommand.setDescription(submitDescription);
//		backlogcommand.setExecuteNum(5);
//		
//		try{
//			backlogService.createBacklog(backlogcommand);
//		}catch(HGException e){
//			e.printStackTrace();
//			throw new PaymentException(e.getCode(),e.getMessage());
//		}
		
		payOrder.getPayOrderProcessor().receivePayRequest(payOrder);
		
		try{
		//生成支付请求地址
		String url = payOrder.buildRequestParam();
		//推送给第三方支付渠道成功
		payOrder.getPayOrderProcessor().submitPayRequestSuccess(payOrder);
		dao.update(payOrder);
		return url;
		
	 }catch(Exception e){
		e.printStackTrace();
		//推送给第三方支付渠道失败
		payOrder.getPayOrderProcessor().submitPayRequestFail(payOrder);
		dao.update(payOrder);
		return null;
	 }
				
		
   }
	
	/**
	 * 商城查询订单是否支付成功
	 * @param orderNo
	 * @return
	 * @throws PaymengException
	 */
	public String queryPayOrder(PayOrderLocalQO qo) throws PaymentException{
		
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
		
		if(StringUtils.isBlank(qo.getSecretKey())){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"请提供客户端密钥");
		}
		if(!MD5HashUtil.toMD5(client.getSecretKey()).equals(qo.getSecretKey())){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTMATCHKEY,
					"客户端的支付平台密钥不正确");
		}
		if(!client.getValid()){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_DONOTVALID, 
					"客户端不可用");
		}
		if(StringUtils.isBlank(qo.getClientTradeNo())){
			throw new PaymentException(PaymentException.QUERY_PAYOEDER_WITHOUTPARAM,"请提供订单编号");
		}
		
		PayOrder payOrder = dao.queryUnique(qo);
		if(payOrder == null){
			HgLogger.getInstance().error("luoyun", client.getName() + "向支付平台查询订单" + qo.getClientTradeNo() + "失败，订单不存在");
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,
					client.getName() + "向支付平台查询订单" + qo.getClientTradeNo() + "失败，订单不存在");
		}
		
		String json = payOrder.notifyClientParam();
		return json;
	}
	
	
	
	
}
