package hsl.h5.base.result.jp;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.jp.FlightOrderDTO;

import java.util.List;

/**
 * 查询机票订单响应
 * 
 * @author yuxx
 * 
 */
public class JPQueryOrderResult extends ApiResult {

	/**
	 * 机票订单
	 */
	private List<FlightOrderDTO> flightOrderDTO;
	
	/**
	 * 订单总条数
	 */
	private Integer totalCount;

	private FlightOrderDTO flightOrder;
	
	
	public List<FlightOrderDTO> getFlightOrderDTO() {
		return flightOrderDTO;
	}

	public void setFlightOrderDTO(List<FlightOrderDTO> flightOrderDTO) {
		this.flightOrderDTO = flightOrderDTO;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public FlightOrderDTO getFlightOrder() {
		return flightOrder;
	}

	public void setFlightOrder(FlightOrderDTO flightOrder) {
		this.flightOrder = flightOrder;
	}

}
