package plfx.api.client.api.v1.gj.response;

import java.util.List;

import plfx.api.client.api.v1.gj.dto.GJAvailableFlightGroupDTO;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：国际航班详情
 * @类修改者：
 * @修改日期：2015-7-2下午3:31:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:31:53
 */
@SuppressWarnings("serial")
public class FlightMoreCabinsGJResponse extends ApiResponse {

	/**
	 * 结果代码：航班舱位组合token过期或不存在
	 */
	public final static String RESULT_NOT_EXIST = "-1";
	/**
	 * 结果代码：没有更多的航班舱位组合
	 */
	public final static String RESULT_NO_MORE = "-2";

	/**
	 * 可用的航班组合(查看详情时列表都是同一航班组合，不同舱位和价格信息)
	 */
	private List<GJAvailableFlightGroupDTO> availableFlightGroups;

	public List<GJAvailableFlightGroupDTO> getAvailableFlightGroups() {
		return availableFlightGroups;
	}

	public void setAvailableFlightGroups(List<GJAvailableFlightGroupDTO> availableFlightGroups) {
		this.availableFlightGroups = availableFlightGroups;
	}

}
