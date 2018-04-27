package hg.dzpw.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * @类功能说明：汇金宝转账记录查询对象
 * @类修改者：
 * @修改日期：2015-5-6下午4:14:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午4:14:09
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "hjbTransferRecordDao")
public class HJBTransferRecordQo extends BaseQo {

	/**
	 * 转账类型
	 */
	@QOAttr(name = "type")
	private Integer type;

	/**
	 * 目标ID
	 */
	@QOAttr(name = "targetId")
	private String targetId;

	/**
	 * 记录时间
	 */
	@QOAttr(name = "recordDate", type = QOAttrType.GE)
	private Date recordDateBegin;
	@QOAttr(name = "recordDate", type = QOAttrType.LE)
	private Date recordDateEnd;
	@QOAttr(name = "recordDate", type = QOAttrType.ORDER)
	private Integer recordDateOrder = 0;

	/**
	 * 是否有返回结果
	 */
	@QOAttr(name = "hasResponse")
	private Boolean hasResponse;

	// ----------------------- 请求 -----------------------

	/**
	 * 付款方编号
	 */
	@QOAttr(name = "payCstNo", ifTrueUseLike = "payCstNoLike")
	private String payCstNo;
	private Boolean payCstNoLike = false;

	/**
	 * 收款方编号
	 */
	@QOAttr(name = "rcvCstNo", ifTrueUseLike = "rcvCstNoLike")
	private String rcvCstNo;
	private Boolean rcvCstNoLike = false;

	/**
	 * 交易金额(单位分)
	 */
	@QOAttr(name = "trxAmount", type = QOAttrType.GE)
	private Integer trxAmountBegin;
	@QOAttr(name = "trxAmount", type = QOAttrType.LE)
	private Integer trxAmountEnd;

	/**
	 * 企业用户在汇金宝平台的操作员编号
	 */
	@QOAttr(name = "userId")
	private String userId;

	/**
	 * 接入商城订单号
	 */
	@QOAttr(name = "originalOrderNo")
	private String originalOrderNo;

	// ----------------------- 返回结果 -----------------------

	/**
	 * 交易状态 1:交易成功; 2:失败
	 */
	@QOAttr(name = "status")
	private String status;
	/**
	 * 汇金宝交易订单编号
	 */
	@QOAttr(name = "hjbOrderNo")
	private String hjbOrderNo;
	/**
	 * 错误代码
	 */
	@QOAttr(name = "errorCode")
	private String errorCode;
	/**
	 * 交易信息
	 */
	@QOAttr(name = "message", type = QOAttrType.LIKE_ANYWHERE)
	private String message;

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

	public Date getRecordDateBegin() {
		return recordDateBegin;
	}

	public void setRecordDateBegin(Date recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}

	public Date getRecordDateEnd() {
		return recordDateEnd;
	}

	public void setRecordDateEnd(Date recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
	}

	public Boolean getHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(Boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	public String getPayCstNo() {
		return payCstNo;
	}

	public void setPayCstNo(String payCstNo) {
		this.payCstNo = payCstNo;
	}

	public Boolean getPayCstNoLike() {
		return payCstNoLike;
	}

	public void setPayCstNoLike(Boolean payCstNoLike) {
		this.payCstNoLike = payCstNoLike;
	}

	public String getRcvCstNo() {
		return rcvCstNo;
	}

	public void setRcvCstNo(String rcvCstNo) {
		this.rcvCstNo = rcvCstNo;
	}

	public Boolean getRcvCstNoLike() {
		return rcvCstNoLike;
	}

	public void setRcvCstNoLike(Boolean rcvCstNoLike) {
		this.rcvCstNoLike = rcvCstNoLike;
	}

	public Integer getTrxAmountBegin() {
		return trxAmountBegin;
	}

	public void setTrxAmountBegin(Integer trxAmountBegin) {
		this.trxAmountBegin = trxAmountBegin;
	}

	public Integer getTrxAmountEnd() {
		return trxAmountEnd;
	}

	public void setTrxAmountEnd(Integer trxAmountEnd) {
		this.trxAmountEnd = trxAmountEnd;
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

	public Integer getRecordDateOrder() {
		return recordDateOrder;
	}

	public void setRecordDateOrder(Integer recordDateOrder) {
		this.recordDateOrder = recordDateOrder;
	}

}
