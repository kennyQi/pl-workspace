package hg.dzpw.domain.model.pay;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：支付宝代扣、退款记录 
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-15上午10:56:03
 * @版本：V1.0
 *
 */
@Entity
@Table(name = M.TABLE_PREFIX + "ALIPAY_TRANSFER_RECORD")
public class AliPayTransferRecord extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商向电子票务支付--代扣
	 */
	public final static Integer TYPE_DEALER_TO_DZPW = 1;

	/**
	 * 电子票务向经销商退款
	 */
	public final static Integer TYPE_DZPW_TO_DEALER = 2;
	

	/**
	 * 转账类型
	 */
	@Column(name = "TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 记录所属DZPW订单号
	 */
	@Column(name = "TICKET_ORDER_ID", length = 64)
	private String ticketOrderId;
	
	/**
	 * 记录所属门票ID
	 * 注：只有类型为退款时该属性才有值
	 */
	@Column(name = "GROUP_TICKET_ID", length = 64)
	private String groupTicketId;

	/**
	 * 记录时间
	 */
	@Column(name = "RECORD_DATE", columnDefinition = M.DATE_COLUM)
	private Date recordDate;
	
	/**
	 * 是否有请求返回结果
	 */
	@Type(type = "yes_no")
	@Column(name = "HAS_RESPONSE")
	private Boolean hasResponse = false;
	
	/**
	 * 退款消息是否成功推送经销商
	 */
	@Type(type = "yes_no")
	@Column(name = "IS_NOTIFY_SUCCESS")
	private Boolean isNotifySuccess;
	
	/**
	 * 接口是否请求成功
	 */
	@Type(type = "yes_no")
	@Column(name = "IS_SUCCESS")
	private Boolean isSuccess;
	
	/**
	 * 支付宝流水号
	 */
	@Column(name = "TRADE_NO", length = 64)
	private String tradeNo;
	
	/**
	 * 退款批次号
	 * 唯一   格式：退款日期（8位当天日期）+票号
	 * 注：只有类型为退款时该属性才有值
	 */
	@Column(name = "REFUND_BATCH_NO", length = 64)
	private String refundBatchNo;

	/**
	 * 交易金额(单位元)
	 */
	@Column(name = "TRX_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double trxAmount;
	
	/**
	 * 转入账户
	 */
	@Column(name = "TRANS_ACCOUNT_IN", length = 64)
	private String transAccountIn;
	
	/**
	 * 转出账户
	 */
	@Column(name = "TRANS_ACCOUNT_OUT", length = 64)
	private String transAccountOut;
	
	/**
	 * TRADE_FINISHED：交易完成
	 * TRADE_SUCCESS：支付成功，此时可退款
	 * WAIT_BUYER_PAY：交易创建，等待支付
	 * TRADE_CLOSED：交易关闭
	 * 
	 * REFUND_SUCCESS:退款成功
	 * REFUND_FAIL:退款失败
	 */
	@Column(name = "STATUS", length = 16)
	private String status;
	
	/**
	 * 错误代码
	 */
	@Column(name = "ERROR_CODE", length = 64)
	private String errorCode;
	
	/**
	 * 请求接口参数JSON
	 */
	@Column(name = "REQUEST_DATA_JSON", columnDefinition = M.TEXT_COLUM)
	private String requestDataJson;
	
	/**
	 * 接口响应JSON
	 */
	@Column(name = "RESPONSE_DATA_JSON", columnDefinition = M.TEXT_COLUM)
	private String responseDataJson;

	
	/**
	 * @方法功能说明：代扣时构建请求记录
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-16下午1:56:26
	 * @参数：@param cae
	 * @参数：@param type
	 */
	public void createByCaeChargeParameter(CaeChargeParameter cae, Integer type){
		this.setId(UUIDGenerator.getUUID());
		this.setType(type);
		this.setRecordDate(new Date());
		this.setTicketOrderId(cae.getOut_order_no());
		this.setTrxAmount(Double.valueOf(cae.getAmount()));
		this.setTransAccountIn(cae.getTrans_account_in());
		this.setTransAccountOut(cae.getTrans_account_out());
		this.setRequestDataJson(JSON.toJSONString(cae));
	}
	
	
	/**
	 * @方法功能说明：更新代扣响应记录
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-16下午4:15:34
	 * @参数：@param resp
	 * @return:void
	 */
	public void setCaeChargeResponse(CaeChargeResponse resp){
		this.setIsSuccess(resp.isSuccess());
		this.setErrorCode(resp.getError());
		this.setStatus(resp.getStatus());
		this.setHasResponse(true);
		this.setTradeNo(resp.getTrade_no());
		this.setResponseDataJson(JSON.toJSONString(resp));
	}
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(Double trxAmount) {
		this.trxAmount = trxAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getResponseDataJson() {
		return responseDataJson;
	}


	public void setResponseDataJson(String responseDataJson) {
		this.responseDataJson = responseDataJson;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Boolean getHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(Boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTransAccountIn() {
		return transAccountIn;
	}

	public void setTransAccountIn(String transAccountIn) {
		this.transAccountIn = transAccountIn;
	}

	public String getTransAccountOut() {
		return transAccountOut;
	}

	public void setTransAccountOut(String transAccountOut) {
		this.transAccountOut = transAccountOut;
	}

	public String getRequestDataJson() {
		return requestDataJson;
	}

	public void setRequestDataJson(String requestDataJson) {
		this.requestDataJson = requestDataJson;
	}

	public Boolean getIsNotifySuccess() {
		return isNotifySuccess;
	}

	public void setIsNotifySuccess(Boolean isNotifySuccess) {
		this.isNotifySuccess = isNotifySuccess;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

}
