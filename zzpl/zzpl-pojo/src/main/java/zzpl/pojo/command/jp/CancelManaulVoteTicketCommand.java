package zzpl.pojo.command.jp;

import java.util.Date;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CancelManaulVoteTicketCommand extends BaseCommand {
	
	private String flightNO;
	
	private String airID;
	
	private Date startData;
	
	private String tasklistID;
	
	private Double refundPrice;
	
	/**
	 * 当前处理人ID
	 */
	private String currentUserID;

	/**
	 * 当前处理人姓名
	 */
	private String currentUserName;
	
	/**
	 * 流程环节编号
	 */
	private Integer stepNO;

	/**
	 * 流程环节名称
	 */
	private String stepName;

	public String getFlightNO() {
		return flightNO;
	}

	public void setFlightNO(String flightNO) {
		this.flightNO = flightNO;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public Date getStartData() {
		return startData;
	}

	public void setStartData(Date startData) {
		this.startData = startData;
	}

	public String getTasklistID() {
		return tasklistID;
	}

	public void setTasklistID(String tasklistID) {
		this.tasklistID = tasklistID;
	}

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public Integer getStepNO() {
		return stepNO;
	}

	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}
	
}