package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：国际机票订单异常信息
 * @类修改者：
 * @修改日期：2015-7-13上午10:27:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-13上午10:27:02
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderExceptionInfo implements Serializable {

	/**
	 * 是否为异常订单
	 */
	@Type(type = "yes_no")
	@Column(name = "EXCEPTION_ORDER")
	private Boolean exceptionOrder = false;

	/**
	 * 异常订单添加时间
	 */
	@Column(name = "ADD_DATE", columnDefinition = J.DATE_COLUM)
	private Date addDate;

	/**
	 * 异常订单调整 金额
	 */
	@Column(name = "ADJUST_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double adjustAmount = 0d;

	/**
	 * 异常订单调整凭证
	 */
	@Column(name = "VOUCHER_PICTURE", length = 512)
	private String voucherPicture;

	/**
	 * 异常订单调整原因
	 */
	@Column(name = "ADJUST_REASON", length = 512)
	private String adjustReason;

	public Boolean getExceptionOrder() {
		return exceptionOrder;
	}

	public void setExceptionOrder(Boolean exceptionOrder) {
		this.exceptionOrder = exceptionOrder;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

}
