package hg.payment.app.service.local.refund;

import hg.common.component.BaseServiceImpl;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.dao.payorder.PayOrderDAO;
import hg.payment.app.dao.refund.RefundDAO;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.pojo.qo.refund.RefundLocalQO;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.refund.Refund;
import hg.payment.domain.model.refund.RefundProcessor;
import hg.payment.pojo.command.dto.RefundRequestDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.system.service.BacklogService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RefundLocalService extends BaseServiceImpl<Refund, RefundLocalQO, RefundDAO>{
	
	
	@Autowired
	private PayOrderDAO payOrderDao;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private RefundDAO dao;
	@Autowired
	private BacklogService backlogService;
	
	
	@Override
	protected RefundDAO getDao() {
		return dao;
	}
	
	
	/**
	 * 计算订单可退款金额
	 * @param refundLocalQO
	 * @return
	 */
	public Double validRefundAmount(RefundLocalQO refundLocalQO) throws PaymentException{
		
		//查询退款的订单
		PayOrderLocalQO payOrderLocalQO = new PayOrderLocalQO();
		payOrderLocalQO.setPaymentClientID(refundLocalQO.getPaymentClientID());
		payOrderLocalQO.setClientTradeNo(refundLocalQO.getClientTradeNo());
		PayOrder payOrder = payOrderDao.queryUnique(payOrderLocalQO);
		if(payOrder == null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,"进行订单可退金额计算时，客户订单号" + 
							refundLocalQO.getClientTradeNo() + "不存在");
		}
		//查询订单退款记录
		List<Refund> refundList = new ArrayList<Refund>();
		refundList = dao.queryList(refundLocalQO);
		if(refundList.size() == 0){
			return payOrder.getAmount();
		}else{
			Double refundAmount = 0.0; //已退款金额
			for(Refund refund:refundList){
				if(refund.getRefundProcessor().getRefundStatus() == RefundProcessor.NOTIFY_CLIENT_REFUND_SUCCESS){
					refundAmount = refundAmount  + refund.getAmount();
				}
			}
			return (payOrder.getAmount() - refundAmount) > 0 ? payOrder.getAmount() - refundAmount : 0;
		}
	}

	/**
	 * 校验退款请求基本参数
	 */
	public void validBasicRefundRequestParams(RefundRequestDTO refundRequestDTO) throws PaymentException{
		
		if(refundRequestDTO == null){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,"请提供退款请求基本参数");
		}
		
		if(StringUtils.isBlank(refundRequestDTO.getTradeNo())){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,
					"请提供商户订单编号");
		}
		if(refundRequestDTO.getAmount() == null){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_WITHOUTPARAM,
					"请提供退款金额");
		}
		if(refundRequestDTO.getAmount() < 0){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_PARAM_NOT_VALID,
					"退款金额不合理");
		}
		PayOrderLocalQO payOrderLocalQo = new PayOrderLocalQO();
		payOrderLocalQo.setPaymentClientID(refundRequestDTO.getPaymentClientId());
		payOrderLocalQo.setClientTradeNo(refundRequestDTO.getTradeNo());
		PayOrder payOrder = payOrderDao.queryUnique(payOrderLocalQo);
		PaymentClient paymentClient = cache.getPaymentClient(refundRequestDTO.getPaymentClientId()); 
		if(payOrder == null){
			throw new PaymentException(PaymentException.RESULT_PAYORDER_NOTFOUND,"商城" + paymentClient.getName() + 
					"订单号为" + refundRequestDTO.getTradeNo() + "的订单不存在");
		}
		if(!payOrder.getPayOrderProcessor().isPaySuccess()){
			throw new PaymentException(PaymentException.REFUND_PAYOEDER_STATUS_NOT_VALID,
					"客户订单号" + refundRequestDTO.getTradeNo() + "未支付，不允许退款");
		}
//		RefundLocalQO refundLocalQO = new RefundLocalQO();
//		refundLocalQO.setPaymentClientID(refundRequestDTO.getPaymentClientId());
//		refundLocalQO.setClientTradeNo(refundRequestDTO.getTradeNo());
//		if(refundRequestDTO.getAmount() > validRefundAmount(refundLocalQO)){
//			throw new PaymentException(PaymentException.REFUND_PAYOEDER_PARAM_NOT_VALID,
//					"退款金额大于可退款金额");
//		}
		
	}
	
	
	
	/**
	 * 判断显示的退款状态
	 * @param order
	 * @return
	 */
	public String showRefundStatus(Refund refund){
		
		if(refund.getRefundProcessor() == null){
			return "-";
		}else if(refund.getRefundProcessor().isRefundSuccess()){
			return "退款成功";
		}else if(refund.getRefundProcessor().isFailed()){
			return "退款失败";
		}else if(refund.getRefundProcessor().isSubmitted()){
			return "已推送";
		}
		
		return "-";

	}
	
	
	public String showRefundNotify(Refund refund){
		if(refund.getRefundProcessor() == null){
			return "-";
		}else if(refund.getRefundProcessor().isNotifyRefund()){
			return "已通知商城退款结果";
		}else{
			return "-";
		}
	}

	
	
}
