package plfx.api.client.api.v1.gj.response;

import java.util.List;

import plfx.api.client.api.v1.gj.dto.GJAvailableFlightGroupDTO;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：国际航班查询结果
 * @类修改者：
 * @修改日期：2015-7-2下午3:31:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:31:53
 */
@SuppressWarnings("serial")
public class FlightGJResponse extends ApiResponse {

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
