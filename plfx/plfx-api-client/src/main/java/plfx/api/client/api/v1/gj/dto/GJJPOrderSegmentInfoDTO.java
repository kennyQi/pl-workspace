package plfx.api.client.api.v1.gj.dto;

import java.util.List;

import plfx.api.client.common.EmbeddDTO;

/**
 * @类功能说明：航段信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:05:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:05:22
 */
@SuppressWarnings("serial")
public class GJJPOrderSegmentInfoDTO extends EmbeddDTO {

	/**
	 * 退改签规则
	 */
	private String airRules;

	/**
	 * 去程总用时(单位分钟)
	 */
	private Integer takeoffTotalDuration;

	/**
	 * 去程航班
	 */
	private List<GJFlightCabinDTO> takeoffFlights;

	/**
	 * 回程总用时(单位分钟)
	 */
	private Integer backTotalDuration;

	/**
	 * 回程航班
	 */
	private List<GJFlightCabinDTO> backFlights;

	public String getAirRules() {
		return airRules;
	}

	public void setAirRules(String airRules) {
		this.airRules = airRules;
	}

	public Integer getTakeoffTotalDuration() {
		return takeoffTotalDuration;
	}

	public void setTakeoffTotalDuration(Integer takeoffTotalDuration) {
		this.takeoffTotalDuration = takeoffTotalDuration;
	}

	public List<GJFlightCabinDTO> getTakeoffFlights() {
		return takeoffFlights;
	}

	public void setTakeoffFlights(List<GJFlightCabinDTO> takeoffFlights) {
		this.takeoffFlights = takeoffFlights;
	}

	public Integer getBackTotalDuration() {
		return backTotalDuration;
	}

	public void setBackTotalDuration(Integer backTotalDuration) {
		this.backTotalDuration = backTotalDuration;
	}

	public List<GJFlightCabinDTO> getBackFlights() {
		return backFlights;
	}

	public void setBackFlights(List<GJFlightCabinDTO> backFlights) {
		this.backFlights = backFlights;
	}

}
