package slfx.api.v1.response.jp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;

/**
 * 
 * @类功能说明：机票出票结果RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:42:42
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPAskOrderTicketResponse extends ApiResponse {

	private List<FlightPassengerDTO> flightPassengerDTOList;

	public final static Integer LINKER_MISSING = -1001001; // 缺少联系人信息

	public List<FlightPassengerDTO> getFlightPassengerDTOList() {
		return flightPassengerDTOList;
	}

	public void setFlightPassengerDTOList(
			List<FlightPassengerDTO> flightPassengerDTOList) {
		this.flightPassengerDTOList = flightPassengerDTOList;
	}

	public static Integer getLinkerMissing() {
		return LINKER_MISSING;
	}

}
