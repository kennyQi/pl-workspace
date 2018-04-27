package hsl.h5.base.result.jp;

import hsl.pojo.dto.jp.FlightDTO;
import java.util.List;
import hsl.h5.base.result.api.ApiResult;

/**
 * 查询航班返回
 * @author yuxx
 *
 */
public class JPQueryFlightResult extends ApiResult{

	/**
	 * 航班列表
	 */
	private List<FlightDTO> flightList;

	/**
	 * 航班总数
	 */
	private Integer totalCount;

	public List<FlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
