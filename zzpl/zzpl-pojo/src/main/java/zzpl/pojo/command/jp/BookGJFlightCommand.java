package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

import java.util.Date;
import java.util.List;

import zzpl.pojo.dto.jp.plfx.gj.GJFlightCabinDTO;

@SuppressWarnings("serial")
public class BookGJFlightCommand extends BaseCommand {
	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 登录人姓名
	 */
	private String userName;

	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人电话
	 */
	private String linkTelephone;

	/**
	 * 联系人邮箱
	 */
	private String linkEmail;

	/**
	 * 联系地址
	 */
	private String linkAddress;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 乘客集合
	 */
	private String passengerListJSON;

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;

	/**
	 * 税金
	 */
	private Double totalTax;

	/**
	 * 票面价
	 */
	private String ibePrice;

	/**
	 * 提交审批时价格
	 */
	private Double commitPrice;

	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 总支付金额
	 */
	private Double totalPrice;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private Date createTime;

	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;

	/**
	 * 关联航班
	 */
	private List<GJFlightCabinDTO> gjFlightCabins;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkTelephone() {
		return linkTelephone;
	}

	public void setLinkTelephone(String linkTelephone) {
		this.linkTelephone = linkTelephone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPassengerListJSON() {
		return passengerListJSON;
	}

	public void setPassengerListJSON(String passengerListJSON) {
		this.passengerListJSON = passengerListJSON;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getCommitPrice() {
		return commitPrice;
	}

	public void setCommitPrice(Double commitPrice) {
		this.commitPrice = commitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(Integer reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public List<GJFlightCabinDTO> getGjFlightCabins() {
		return gjFlightCabins;
	}

	public void setGjFlightCabins(List<GJFlightCabinDTO> gjFlightCabins) {
		this.gjFlightCabins = gjFlightCabins;
	}

}
