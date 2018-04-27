package lxs.api.v1.response.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.DayRouteDTO;

public class QueryDayRouteResponse extends ApiResponse {

	List<DayRouteDTO> dayRouteList;

	public List<DayRouteDTO> getDayRouteList() {
		return dayRouteList;
	}

	public void setDayRouteList(List<DayRouteDTO> dayRouteList) {
		this.dayRouteList = dayRouteList;
	}

}
