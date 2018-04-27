package zzpl.pojo.dto.workflow;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CommentDTO extends BaseDTO{
	
	private String account;
	
	private String flightNO;
	
	private String airID;
	
	private Date startData;
	
	private Double refundPrice;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

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

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}
	
}
