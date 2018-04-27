package hsl.api.v1.response.jp;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.jp.FlightPassangerDto;

/**
 * 机票出票结果
 * 
 * @author yuxx
 * 
 */
public class JPAskOrderTicketResponse extends ApiResponse {

	private List<FlightPassangerDto> flightPassangerDtos;

	public final static Integer LINKER_MISSING = -1001001; // 缺少联系人信息

	public List<FlightPassangerDto> getFlightPassangerDtos() {
		return flightPassangerDtos;
	}

	public void setFlightPassangerDtos(List<FlightPassangerDto> flightPassangerDtos) {
		this.flightPassangerDtos = flightPassangerDtos;
	}

	public static Integer getLinkerMissing() {
		return LINKER_MISSING;
	}

}
