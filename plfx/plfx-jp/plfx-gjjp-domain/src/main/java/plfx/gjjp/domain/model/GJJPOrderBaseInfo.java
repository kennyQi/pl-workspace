package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import plfx.jp.domain.model.J;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.jp.domain.model.supplier.Supplier;

/**
 * @类功能说明：国际机票订单基本信息
 * @类修改者：
 * @修改日期：2015-7-14上午11:07:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14上午11:07:50
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderBaseInfo implements Serializable {

	/**
	 * 经销商订单号
	 */
	@Column(name = "DEALER_ORDER_ID", length = 64)
	private String dealerOrderId;

	/**
	 * 来源经销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_DEALER_ID")
	private Dealer fromDealer;

	/**
	 * 供应商订单号
	 */
	@Column(name = "SUPPLIER_ORDER_ID", length = 64)
	private String supplierOrderId;

	/**
	 * 供应商政策ID
	 */
	@Column(name = "SUPPLIER_POLICY_ID", length = 64)
	private String supplierPolicyId;

	/**
	 * 来源供应商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_SUPPLIER_ID")
	private Supplier fromSupplier;

	/**
	 * 平台价格政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_SNAPSHOT_ID")
	private JPPlatformPolicySnapshot policySnapshot;

	/**
	 * 是否换编码出票
	 */
	@Type(type = "yes_no")
	@Column(name = "IS_CHANGE_PNR")
	private Boolean isChangePnr;

	/**
	 * 出票类型
	 * 
	 * 1.B2B 2.BSP 3.不限
	 */
	@Column(name = "OUT_TICKET_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer outTicketType;

	/**
	 * Pnr
	 */
	@Column(name = "PNR", length = 16)
	private String pnr;

	/**
	 * 新pnr(换编码出票后的pnr)
	 */
	@Column(name = "NEW_PNR", length = 16)
	private String newPnr;

	/**
	 * 航空公司大pnr
	 */
	@Column(name = "BIG_PNR", length = 16)
	private String bpnr;

	/**
	 * 乘客数量
	 */
	@Column(name = "PASSENGER_NUMBER", columnDefinition = J.NUM_COLUM)
	private Integer passengerNumber;

	/**
	 * 供应商营业时间(格式:HH:mm-HH:mm)
	 */
	@Column(name = "WORK_TIME", length = 32)
	private String workTime;

	/**
	 * 退票时间段(格式:HH:mm-HH:mm)
	 */
	@Column(name = "REFUND_TIME", length = 32)
	private String refundTime;

	/**
	 * 订单在供应商预定时间
	 */
	@Column(name = "BOOK_TIME", columnDefinition = J.DATE_COLUM)
	private Date bookTime;

	/**
	 * 供应商预定保留时间
	 */
	@Column(name = "BOOK_REMAIN_TIME", columnDefinition = J.DATE_COLUM)
	private Date bookRemainTime;

	/**
	 * 出票时间
	 */
	@Column(name = "OUT_TICKET_TIME", columnDefinition = J.DATE_COLUM)
	private Date outTicketTime;

	/**
	 * 经销商在分销平台下单时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 1024)
	private String remark;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Dealer getFromDealer() {
		return fromDealer;
	}

	public void setFromDealer(Dealer fromDealer) {
		this.fromDealer = fromDealer;
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public String getSupplierPolicyId() {
		return supplierPolicyId;
	}

	public void setSupplierPolicyId(String supplierPolicyId) {
		this.supplierPolicyId = supplierPolicyId;
	}

	public Supplier getFromSupplier() {
		return fromSupplier;
	}

	public void setFromSupplier(Supplier fromSupplier) {
		this.fromSupplier = fromSupplier;
	}

	public JPPlatformPolicySnapshot getPolicySnapshot() {
		return policySnapshot;
	}

	public void setPolicySnapshot(JPPlatformPolicySnapshot policySnapshot) {
		this.policySnapshot = policySnapshot;
	}

	public Boolean getIsChangePnr() {
		return isChangePnr;
	}

	public void setIsChangePnr(Boolean isChangePnr) {
		this.isChangePnr = isChangePnr;
	}

	public Integer getOutTicketType() {
		return outTicketType;
	}

	public void setOutTicketType(Integer outTicketType) {
		this.outTicketType = outTicketType;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getNewPnr() {
		return newPnr;
	}

	public void setNewPnr(String newPnr) {
		this.newPnr = newPnr;
	}

	public String getBpnr() {
		return bpnr;
	}

	public void setBpnr(String bpnr) {
		this.bpnr = bpnr;
	}

	public Integer getPassengerNumber() {
		return passengerNumber;
	}

	public void setPassengerNumber(Integer passengerNumber) {
		this.passengerNumber = passengerNumber;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}

	public Date getBookRemainTime() {
		return bookRemainTime;
	}

	public void setBookRemainTime(Date bookRemainTime) {
		this.bookRemainTime = bookRemainTime;
	}

	public Date getOutTicketTime() {
		return outTicketTime;
	}

	public void setOutTicketTime(Date outTicketTime) {
		this.outTicketTime = outTicketTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
