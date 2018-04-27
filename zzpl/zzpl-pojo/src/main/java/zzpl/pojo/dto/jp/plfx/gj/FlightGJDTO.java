package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class FlightGJDTO extends BaseDTO{
	/**
	 * 可用的航班组合(包含最优价格舱位和价格)
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
