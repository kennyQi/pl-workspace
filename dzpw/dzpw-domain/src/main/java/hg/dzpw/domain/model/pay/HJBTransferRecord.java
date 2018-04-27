package hg.dzpw.domain.model.pay;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.pojo.api.hjb.HJBTransferRequestDto;
import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：汇金宝转账记录
 * @类修改者：
 * @修改日期：2015-5-6下午12:00:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午12:00:43
 */
@Entity
@Table(name = M.TABLE_PREFIX + "HJB_TRANSFER_RECORD")
public class HJBTransferRecord extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商向电子票务转账，targetId为ticketOrder.id
	 */
	public final static Integer TYPE_DEALER_TO_DZPW = 1;

	/**
	 * 电子票务向景区转账，targetId为singleTicket.id
	 */
	public final static Integer TYPE_DZPW_TO_SCENICSPOT = 2;
	
	/**
	 * 电子票务向经销商转账 targetId为 ticketOrder.id
	 */
	public final static Integer TYPE_DZPW_TO_DEALER = 3;

	/**
	 * 转账类型
	 */
	@Column(name = "TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 目标ID
	 */
	@Column(name = "TARGET_ID", length = 64)
	private String targetId;

	/**
	 * 记录时间
	 */
	@Column(name = "RECORD_DATE", columnDefinition = M.DATE_COLUM)
	private Date recordDate;
	
	/**
	 * 是否有返回结果
	 */
	@Type(type = "yes_no")
	@Column(name = "HAS_RESPONSE")
	private Boolean hasResponse = false;

	// ----------------------- 请求 -----------------------

	/**
	 * 接口版本号
	 */
	@Column(name = "VERSION", length = 16)
	private String version;
	/**
	 * 票务平台唯一标识
	 */
	@Column(name = "MERCHANT_ID", length = 64)
	private String merchantId;
	/**
	 * 付款方编号
	 */
	@Column(name = "PAY_CST_NO", length = 64)
	private String payCstNo;
	/**
	 * 收款方编号
	 */
	@Column(name = "RCV_CST_NO", length = 64)
	private String rcvCstNo;
	/**
	 * 交易金额(单位分)
	 */
	@Column(name = "TRX_AMOUNT", columnDefinition = M.NUM_COLUM)
	private Integer trxAmount;
	/**
	 * 企业用户在汇金宝平台的操作员编号
	 */
	@Column(name = "USER_ID", length = 64)
	private String userId;
	/**
	 * 接入商城订单号
	 */
	@Column(name = "ORIGINAL_ORDER_NO", length = 64)
	private String originalOrderNo;

	// ----------------------- 返回结果 -----------------------

	/**
	 * 交易状态 1:交易成功; 2:失败
	 */
	@Column(name = "STATUS", length = 16)
	private String status;
	/**
	 * 汇金宝交易订单编号
	 */
	@Column(name = "HJB_ORDER_NO", length = 64)
	private String hjbOrderNo;
	/**
	 * 错误代码
	 */
	@Column(name = "ERROR_CODE", length = 64)
	private String errorCode;
	/**
	 * 交易信息
	 */
	@Column(name = "MESSAGE", length = 512)
	private String message;

	/**
	 * @方法功能说明：创建记录
	 * @修改者名字：zhurz
	 * @修改时间：2015-5-6下午3:52:11
	 * @修改内容：
	 * @参数：@param requestDto
	 * @参数：@param type
	 * @参数：@param targetId
	 * @return:void
	 * @throws
	 */
	public void create(HJBTransferRequestDto requestDto, Integer type,
			String targetId) {

		setId(UUIDGenerator.getUUID());

		setType(type);
		setTargetId(targetId);
		setRecordDate(new Date());

		setVersion(requestDto.getVersion());
		setMerchantId(requestDto.getMerchantId());
		setPayCstNo(requestDto.getPayCstNo());
		setRcvCstNo(requestDto.getRcvCstNo());
		setTrxAmount(Integer.valueOf(requestDto.getTrxAmount()));
		setUserId(requestDto.getUserId());
		setOriginalOrderNo(requestDto.getOriginalOrderNo());

	}

	/**
	 * @方法功能说明：记录返回
	 * @修改者名字：zhurz
	 * @修改时间：2015-5-6下午3:52:24
	 * @修改内容：
	 * @参数：@param responseDto
	 * @return:void
	 * @throws
	 */
	public void recordResponse(HJBTransferResponseDto responseDto) {
		if (responseDto == null)
			return;
		setStatus(responseDto.getStatus());
		setHjbOrderNo(responseDto.getOrderNo());
		setErrorCode(responseDto.getErrCode());
		setMessage(responseDto.getMessage());
		setHasResponse(true);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayCstNo() {
		return payCstNo;
	}

	public void setPayCstNo(String payCstNo) {
		this.payCstNo = payCstNo;
	}

	public String getRcvCstNo() {
		return rcvCstNo;
	}

	public void setRcvCstNo(String rcvCstNo) {
		this.rcvCstNo = rcvCstNo;
	}

	public Integer getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(Integer trxAmount) {
		this.trxAmount = trxAmount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOriginalOrderNo() {
		return originalOrderNo;
	}

	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHjbOrderNo() {
		return hjbOrderNo;
	}

	public void setHjbOrderNo(String hjbOrderNo) {
		this.hjbOrderNo = hjbOrderNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

}
