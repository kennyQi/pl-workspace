package lxs.pojo.qo.line;

import java.util.List;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class LineActivityQO extends BaseQo {
	private String lineID;

	private String startingCity;
	
	private List<String> startingProvince;
	
	private String activityType;
	
	public List<String> getStartingProvince() {
		return startingProvince;
	}

	public void setStartingProvince(List<String> startingProvince) {
		this.startingProvince = startingProvince;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getStartingCity() {
		return startingCity;
	}

	public void setStartingCity(String startingCity) {
		this.startingCity = startingCity;
	}

}
