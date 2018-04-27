package zzpl.api.client.request.jp;

import java.util.List;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class GNCancelTicketCommand extends ApiPayload {

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 流程ID
	 */
	private String workflowID;

	/**
	 * 下一步执行人ID（办结时，不需要该字段）
	 */
	private List<String> nextUserIDs;

	/**
	 * 当前环节
	 */
	private Integer currentStepNO;

	/**
	 * 下一步环节
	 */
	private Integer nextStepNO;
	
	/**
	 * 经销商订单号ID
	 */
	private String orderID;
	
	/**
	 * 经销商订单号
	 */
	private String orderNO;
	
	/**
	 * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	private String refundType;

	/**
	 * 申请理由
	 */
	private String refundMemo;

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

	public List<String> getNextUserIDs() {
		return nextUserIDs;
	}

	public void setNextUserIDs(List<String> nextUserIDs) {
		this.nextUserIDs = nextUserIDs;
	}

	public Integer getCurrentStepNO() {
		return currentStepNO;
	}

	public void setCurrentStepNO(Integer currentStepNO) {
		this.currentStepNO = currentStepNO;
	}

	public Integer getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(Integer nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
	
	
	
	
	
	
	/**
	 * 经销商订单号ID
	 *//*
	private String orderID;
	
	*//**
	 * 经销商订单号
	 *//*
	private String orderNO;

	*//**
	 * 乘客姓名
	 *//*
	private List<String> passengerNames;

	*//**
	 * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 *//*
	private String refundType;

	*//***
	 * 申请理由
	 *//*
	private String refundMemo;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public List<String> getPassengerNames() {
		return passengerNames;
	}

	public void setPassengerNames(List<String> passengerNames) {
		this.passengerNames = passengerNames;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}*/

}
