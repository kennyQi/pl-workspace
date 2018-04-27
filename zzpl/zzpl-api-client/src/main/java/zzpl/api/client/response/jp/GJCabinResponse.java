package zzpl.api.client.response.jp;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GJCabinListDTO;

public class GJCabinResponse extends ApiResponse {
	private List<GJCabinListDTO> cabinListDTOs;

	public List<GJCabinListDTO> getCabinListDTOs() {
		return cabinListDTOs;
	}

	public void setCabinListDTOs(List<GJCabinListDTO> cabinListDTOs) {
		this.cabinListDTOs = cabinListDTOs;
	}

	
}
