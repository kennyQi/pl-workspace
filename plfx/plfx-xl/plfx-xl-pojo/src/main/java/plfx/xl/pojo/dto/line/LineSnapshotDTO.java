package plfx.xl.pojo.dto.line;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：线路快照DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月17日下午1:54:57
 * 
 */
@SuppressWarnings("serial")
public class LineSnapshotDTO extends BaseXlDTO{

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

}