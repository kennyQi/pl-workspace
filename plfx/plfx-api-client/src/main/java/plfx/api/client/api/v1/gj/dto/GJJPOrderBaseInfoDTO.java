package plfx.api.client.api.v1.gj.dto;

import java.util.Date;

/**
 * @类功能说明：国际机票订单基本信息
 * @类修改者：
 * @修改日期：2015-7-13下午4:57:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-13下午4:57:47
 */
public class GJJPOrderBaseInfoDTO {

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 航程信息
	 * 
	 * 1.单程2.来回程
	 */
	private Integer lineType;

	/**
	 * 是否换编码出票
	 */
	private Boolean isChangePnr;

	/**
	 * Pnr
	 */
	private String pnr;

	/**
	 * 新pnr(换编码出票后的pnr)
	 */
	private String newPnr;

	/**
	 * 乘客数量
	 */
	private Integer passengerNumber;

	/**
	 * 供应商营业时间(格式:HH:mm-HH:mm)
	 */
	private String workTime;

	/**
	 * 退票时间段(格式:HH:mm-HH:mm)
	 */
	private String refundTime;

	/**
	 * 供应商预定保留时间
	 */
	private Date bookRemainTime;

	/**
	 * 经销商在分销平台下单时间
	 */
	private Date createDate;

	/**
	 * 备注
	 */
	private String remark;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public Boolean getIsChangePnr() {
		return isChangePnr;
	}

	public void setIsChangePnr(Boolean isChangePnr) {
		this.isChangePnr = isChangePnr;
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

	public Date getBookRemainTime() {
		return bookRemainTime;
	}

	public void setBookRemainTime(Date bookRemainTime) {
		this.bookRemainTime = bookRemainTime;
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
