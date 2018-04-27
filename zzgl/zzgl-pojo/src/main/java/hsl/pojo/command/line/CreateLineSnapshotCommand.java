package hsl.pojo.command.line;

import java.util.Date;

public class CreateLineSnapshotCommand {
	/**
	 * 快照来源实体
	 */
	private String lineId;

	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 线路类别
	 */
	private Integer type;

	/**
	 * 出发地城市编号
	 */
	private String starting;

	/**
	 * 所有快照信息，Line对象的JSON
	 */
	private String allInfoLineJSON;
	
	/**
	 * 快照创建时间
	 */
	private Date createDate;
	
	private String cityOfType;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public String getAllInfoLineJSON() {
		return allInfoLineJSON;
	}

	public void setAllInfoLineJSON(String allInfoLineJSON) {
		this.allInfoLineJSON = allInfoLineJSON;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}
	
	

}
