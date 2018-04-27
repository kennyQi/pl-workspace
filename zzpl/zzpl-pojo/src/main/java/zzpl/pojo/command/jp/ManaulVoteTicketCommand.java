package zzpl.pojo.command.jp;

import java.util.Date;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ManaulVoteTicketCommand extends BaseCommand {

	private String tasklistWaitID;

	public String getTasklistWaitID() {
		return tasklistWaitID;
	}

	public void setTasklistWaitID(String tasklistWaitID) {
		this.tasklistWaitID = tasklistWaitID;
	}

	/**
	 * 航班
	 */
	private String flightNO;
	/**
	 * 票号
	 */
	private String airID;
	/**
	 * 起飞时间
	 */
	private Date startData;
	/**
	 * 流程流转表ID
	 */
	private String tasklistID;

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
	/**
	 * 供应商价格
	 */
	private Double totalPrice;
	
	/**
	 * 企业支付价
	 */
	private Double platTotalPrice;

	/**
	 * 出票取到 1：易行 2:去哪 3：携程 99：其他
	 */
	private Integer ticketChannel;

	/**
	 * 当是99的时候填写信息
	 */
	private String ticketChannelName;

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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTicketChannel() {
		return ticketChannel;
	}

	public void setTicketChannel(Integer ticketChannel) {
		this.ticketChannel = ticketChannel;
	}

	public String getTicketChannelName() {
		return ticketChannelName;
	}

	public void setTicketChannelName(String ticketChannelName) {
		this.ticketChannelName = ticketChannelName;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

}