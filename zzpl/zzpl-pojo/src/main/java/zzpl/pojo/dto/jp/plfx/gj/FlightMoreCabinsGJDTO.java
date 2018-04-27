package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class FlightMoreCabinsGJDTO extends BaseDTO {
	/**
	 * 可用的航班组合(查看详情时列表都是同一航班组合，不同舱位和价格信息)
	 */
	private List<GJAvailableFlightGroupDTO> availableFlightGroups;

	public List<GJAvailableFlightGroupDTO> getAvailableFlightGroups() {
		return availableFlightGroups;
	}

	public void setAvailableFlightGroups(
			List<GJAvailableFlightGroupDTO> availableFlightGroups) {
		this.availableFlightGroups = availableFlightGroups;
	}

}
