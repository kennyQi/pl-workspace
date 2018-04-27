package lxs.api.v1.request.qo.line;

import java.util.Date;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class DateSalePriceQO extends ApiPayload {
	private String lineID;

	private Date startDate;

	private Date endDate;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
