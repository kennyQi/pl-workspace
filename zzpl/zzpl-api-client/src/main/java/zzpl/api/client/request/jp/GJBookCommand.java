package zzpl.api.client.request.jp;

import java.util.List;

import zzpl.api.client.base.ApiPayload;
import zzpl.api.client.dto.jp.GJFlightDTO;
import zzpl.api.client.dto.jp.GJPassengerDTO;

/****
 * 
 * @类功能说明：生成订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日上午9:41:20
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class GJBookCommand extends ApiPayload {

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 流程ID
	 */
	private String workflowID;

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;

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
	 * 票面价
	 */
	private String ibePrice;

	/**
	 * 税金
	 */
	private Double totalTax;

	/**
	 * 乘客集合
	 */
	private List<GJPassengerDTO> passengerList;

	/**
	 * 关联航班
	 */
	private List<GJFlightDTO> gjFlightDTOs;

	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
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

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}

	public List<GJPassengerDTO> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<GJPassengerDTO> passengerList) {
		this.passengerList = passengerList;
	}

	public List<GJFlightDTO> getGjFlightDTOs() {
		return gjFlightDTOs;
	}

	public void setGjFlightDTOs(List<GJFlightDTO> gjFlightDTOs) {
		this.gjFlightDTOs = gjFlightDTOs;
	}

	public Integer getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(Integer reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

}
