package hg.payment.domain.model.event;

import java.util.Date;

import hg.common.component.BaseModel;

@SuppressWarnings("serial")
public class BasePayEvent extends BaseModel {

	private Date happenedDate;

	public Date getHappenedDate() {
		return happenedDate;
	}

	public void setHappenedDate(Date happenedDate) {
		this.happenedDate = happenedDate;
	}

}
