package lxs.pojo.dto.line;

import lxs.pojo.dto.line.LineDTO;

public class LineSnapshotDTO {
	/**
	 * 快照来源实体
	 */
	private LineDTO line;

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
	 * 城市名称
	 */
	private String cityName;

	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
