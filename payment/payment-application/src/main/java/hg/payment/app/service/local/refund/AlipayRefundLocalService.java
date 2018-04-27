package hg.payment.app.service.local.refund;

import hg.common.component.BaseServiceImpl;
import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.log.util.HgLogger;
import hg.payment.app.cache.AlipayRefundErrorCodeCacheManager;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.dao.refund.AlipayRefundDAO;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.pojo.qo.refund.AlipayRefundLocalQO;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.domain.common.util.PaymentConstant;
import hg.payment.domain.common.util.RefundUtils;
import hg.payment.domain.common.util.hjb.XmlUtil;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.refund.AlipayRefund;
import hg.payment.domain.model.refund.Refund;
import hg.payment.domain.model.refund.RefundProcessor;
import hg.payment.pojo.command.admin.ModifyAlipayRefundCommand;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;
import hg.payment.pojo.command.dto.AlipayRefundRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.AlipayRefundCommand;
import hg.payment.pojo.dto.refund.RefundDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.system.command.backlog.CreateBacklogCommand;
import hg.system.command.backlog.ExecuteBacklogCommand;
import hg.system.exception.HGException;
import hg.system.model.backlog.Backlog;
import hg.system.qo.BacklogQo;
import hg.system.service.BacklogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;


@Service
@Transactional
public class AlipayRefundLocalService extends BaseServiceImpl<AlipayRefund,AlipayRefundLocalQO,AlipayRefundDAO>{

	@Autowired
	private AlipayRefundDAO dao;
	@Autowired
	private RefundLocalService refundLocalService;
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private AlipayPayChannel alipay;
	@Autowired
	private RefundUtils  refundUtils;
	@Autowired
	private BacklogService backlogService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private AlipayRefundErrorCodeCacheManager  alipayCache;
	
	@Override
	protected AlipayRefundDAO getDao() {
		return dao;
	}
	
	/**
	 * 接收到第三方退款回调消息，修改订单信息和退款信息
	 * @param command
	 * @throws PaymentException
	 */
	public void refund(ModifyAlipayRefundCommand command) throws PaymentException{
		
		AlipayRefundLocalQO refundQo = new AlipayRefundLocalQO();
		List<ModifyRefundDTO> modifyRefundDTOList = command.getModifyRefundDTOList();
		for(ModifyRefundDTO dto:modifyRefundDTOList){
			refundQo.setThirdPartyTradeNo(dto.getThirdPartyTradeNo());
			refundQo.setRefundBatchNo(command.getRefundBatchNo());
			Refund refund = dao.queryUnique(refundQo);
			if(refund == null){
				throw new PaymentException(PaymentException.RESULT_REFUND_NOT_FOUND,"支付宝交易号" + dto.getThirdPartyTradeNo()
						+ "支付宝退款批次号" + command.getRefundBatchNo() + "的退款记录不存在");
			}
			//记录退款账号
			refund.modify(dto);
			//修改退款记录状态
			if(dto.getRefundSuccess() == RefundProcessor.REFUND_SUCCESS){
				refund.getRefundProcessor().receiveRefundSuccessNotice(refund);
			}else{
				String errorMessage = alipayCache.getMessageByCode(dto.getRefundRemark());
				refund.setRefundRemark(errorMessage);
				refund.getRefundProcessor().receiveRefundFailNotice(refund);
			}
			dao.update(refund);
			
//			//退款成功后，订单已全额退款状态为交易已关闭，非全额退款状态为有退款
//			if(dto.getRefundSuccess() == PayOrderProcessor.CLOSE){
//				refund.getPayOrder().getPayOrderProcessor().closePayOrder(refund.getPayOrder());
//			}else if(dto.getRefundSuccess() == PayOrderProcessor.HAVE_REFUND){
//				refund.getPayOrder().getPayOrderProcessor().haveRefund(refund.getPayOrder());
//			}
//			payOrderLocalService.update(refund.getPayOrder());
			
	  }
		
		
		
		
		
		
	}
	
	
	/**
	 * 批量创建支付宝退款请求
	 * @param command
	 * @throws PaymentException
	 */
	public void submitAlipayBatchRefundRequest(AlipayRefundCommand command) throws PaymentException{
		
		if(command.getAlipayRefundRequestDTOList() == null){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,"请提供退款列表");
		}else if(command.getAlipayRefundRequestDTOList().size() == 0){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,"请提供退款列表");
		}else{
			for(AlipayRefundRequestDTO dto:command.getAlipayRefundRequestDTOList()){
				refundLocalService.validBasicRefundRequestParams(dto);
			}
		}
		
		if(StringUtils.isBlank(command.getPartner())){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,"请提供支付宝合作商户编号");
		}
		
		if(StringUtils.isBlank(command.getKeys())){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,"请提供支付宝商户密钥");
		}
		
		String batchNo = refundUtils.getBatchNo();
		List<AlipayRefund> refundList = new ArrayList<AlipayRefund>();
		List<AlipayRefundRequestDTO> alipayRefundRequestDTOList = command.getAlipayRefundRequestDTOList();
		for(int i=0;i<alipayRefundRequestDTOList.size();i++){
			AlipayRefundRequestDTO dto = alipayRefundRequestDTOList.get(i);
			AlipayRefund alipayRefund = new AlipayRefund();
			//设置退款记录ID：批次号 + 列表顺序
			dto.setId(refundUtils.createRefundNo(batchNo, i));
			alipayRefund.createAlipayRefund(command,dto,batchNo);
			PayOrderLocalQO payOrderLocalQO = new PayOrderLocalQO();
			payOrderLocalQO.setPaymentClientID(dto.getPaymentClientId());
			payOrderLocalQO.setClientTradeNo(dto.getTradeNo());
			PayOrder payOrder = payOrderLocalService.queryUnique(payOrderLocalQO);
			payOrder.setPayChannel(alipay);
			alipayRefund.setPayOrder(payOrder);
			alipayRefund.getRefundProcessor().receiveRefundRequest(alipayRefund);
			dao.save(alipayRefund);
			refundList.add(alipayRefund);
			
			//订单状态记录为有退款记录
			payOrder.getPayOrderProcessor().haveRefund(payOrder); 
			payOrderLocalService.update(payOrder);
			
		}
		
		//创建退款记录时同步创建待办事项
		CreateBacklogCommand backlogcommand = new CreateBacklogCommand();
		backlogcommand.setType(PaymentConstant.BACKLOG_TYPE_PAYMENT_REFUND_ALIPAY);
		backlogcommand.setName("支付平台批量通知批次号" + batchNo + "退款记录的退款结果");
		Map<String,String> submitDesMap = new HashMap<String,String>();
		//组装待办事项Json描述字符串
		submitDesMap.put("refundBatchNo", batchNo);
		String submitDescription = JSONArray.toJSONString(submitDesMap);
		backlogcommand.setDescription(submitDescription);
		backlogcommand.setExecuteNum(5);
		
		try{
			backlogService.createBacklog(backlogcommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
		
		//组装退款请求字符串
		AlipayRefund alipayRefund = new AlipayRefund();
		String submitUrl = alipayRefund.buildRefundRequestParam(refundList);
		HttpResponse resp = HttpUtil.reqForGet(submitUrl,null);
		
		if(resp.getRespoinsCode() == 200){
			String xmlResponse = resp.getResult();
			HgLogger.getInstance().debug("luoyun", "【支付宝】支付宝批量退款请求xml同步返回参数" + xmlResponse);
			
			String success = XmlUtil.getNodeVal(xmlResponse, "is_success");
			for(Refund refund:refundList){
				if("T".equals(success)){  
					//退款申请被接受
					refund.getRefundProcessor().submitRefundRequestSuccess(refund);
					dao.update(refund);
				}else{  
					//退款申请没有被接受
					refund.getRefundProcessor().submitRefundRequestFail(refund);
					//记录错误信息
					String errorCode = XmlUtil.getNodeVal(xmlResponse, "error");
					String errorMessage = alipayCache.getMessageByCode(errorCode);
					ModifyRefundDTO dto = new ModifyRefundDTO();
					dto.setRefundRemark(errorMessage);
					refund.modify(dto);
					dao.update(refund);
					throw new PaymentException(PaymentException.REFUND_PAYOEDER_ERROR_OF_ALIPAY,errorMessage);
				}
			}
		}
		
		
	}
	
	
		
	/**
	 * 通知商城一个批次的退款结果
	 * @param refundId
	 * @throws PaymentException
	 */
	public void notifyClientRefundList(String batchNo)throws PaymentException{
		
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城退款结果，退款批次号" + batchNo);
		if(StringUtils.isBlank(batchNo)){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_WITHOUT_ID,
					"请提供退款批次号");
		}
		
		//查询退款列表
		AlipayRefundLocalQO refundQO = new AlipayRefundLocalQO();
		refundQO.setRefundBatchNo(batchNo);
		List<AlipayRefund> refundList = dao.queryList(refundQO);
		
		//查询和订单对应的待办事项
		BacklogQo backlogQo = new BacklogQo();
		backlogQo.setDescription(batchNo);
		backlogQo.setExecuteCountValid(false);
		backlogQo.setType(PaymentConstant.BACKLOG_TYPE_PAYMENT_REFUND_ALIPAY);
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
		
		PayOrder payOrder = refundList.get(0).getPayOrder(); //同一批次的退款列表关联的是同一个商城的订单
		//缓存中读取客户端信息
		PaymentClient client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
		if(client == null){
			throw new PaymentException(PaymentException.RESULT_PAYMENTCLIENT_NOTFOUND,
					"客户端不存在");
		}
		
		//响应给商城的退款信息
		List<RefundDTO> dtoList = new ArrayList<RefundDTO>();
		for(AlipayRefund refund:refundList){
			if(refund.getRefundProcessor().isNotifyRefund()){
				throw new PaymentException(PaymentException.RESULT_PAYORDER_DUPLICATE_NOTIFY,
						"退款记录" + refund.getId() + "已经成功通知商城退款结果，不允许重复通知");
			}
			RefundDTO dto = new RefundDTO();
			dto.setAmount(refund.getAmount());
			dto.setRefundDate(refund.getRefundDate());
			if(refund.getRefundProcessor().isRefundSuccess()){
				dto.setRefundStatus(1);
			}else if(refund.getRefundProcessor().isFailed()){
				dto.setRefundStatus(2);
				dto.setRefundRemark(refund.getRefundRemark());
			}
			
			dto.setTradeNo(refund.getPayOrder().getClientTradeNo());
			dtoList.add(dto);
		}
		
		String json = JSONArray.toJSONString(dtoList);
		String data = "type="+PaymentConstant.NOTIFY_CLIENT_TYPE_ALIPAY_REFUND + "&refundList=" + json;
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城地址：" + client.getClientMessageURL());
		//通过商城的消息地址通知商城
		HttpResponse res = HttpUtil.reqForPost(client.getClientMessageURL(),data,null);
		HgLogger.getInstance().debug("luoyun", "支付平台异步通知商城订单" + payOrder.getClientTradeNo() + "退款记录response状态码" + res.getRespoinsCode());
		for(AlipayRefund refund:refundList){
			if(res.getRespoinsCode() == 200){
				//设置订单通知状态为通知业务系统成功
				refund.getRefundProcessor().noticeRefundResultSuccess(refund);
			}else{
				//设置订单通知状态为通知业务系统失败
				refund.getRefundProcessor().noticeRefundResultFail(refund);
				HgLogger.getInstance().debug("luoyun", res.getResult());
			}
		}
		dao.updateList(refundList);
		
		//执行待办事项
		ExecuteBacklogCommand executeCommand = new ExecuteBacklogCommand();
		executeCommand.setSuccess(refundList.get(0).getRefundProcessor().isNotifyRefund());
		executeCommand.setBacklogId(backlog.getId());
		try{
			backlogService.executeBacklog(executeCommand);
		}catch(HGException e){
			e.printStackTrace();
			throw new PaymentException(e.getCode(),e.getMessage());
		}
	  }
		
		
	}
	


