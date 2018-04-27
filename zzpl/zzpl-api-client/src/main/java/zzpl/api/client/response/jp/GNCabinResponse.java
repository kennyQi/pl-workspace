package zzpl.api.client.response.jp;

import java.util.ArrayList;
import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNCabinListDTO;

import com.alibaba.fastjson.JSON;

public class GNCabinResponse extends ApiResponse {
	private List<GNCabinListDTO> gnCabinListDTOs;

	public List<GNCabinListDTO> getGnCabinListDTOs() {
		return gnCabinListDTOs;
	}

	public void setGnCabinListDTOs(List<GNCabinListDTO> gnCabinListDTOs) {
		this.gnCabinListDTOs = gnCabinListDTOs;
	}

	public static void main(String[] args) {
		GNCabinResponse gnCabinResponse = new GNCabinResponse();
		List<GNCabinListDTO> flightGNInfoDTOs = new ArrayList<GNCabinListDTO>();
		GNCabinListDTO gnCabinInfoDTO = new GNCabinListDTO();
		gnCabinInfoDTO.setCabinDiscount("100");
		gnCabinInfoDTO.setCabinName("头等舱");
		gnCabinInfoDTO.setCabinSales("5");
		gnCabinInfoDTO.setCabinType("1");
		gnCabinInfoDTO
				.setEncryptString("79fccacuigjjdskf098dafgiudashgertlkjhl");
		gnCabinInfoDTO.setIbePrice("1888");
		flightGNInfoDTOs.add(gnCabinInfoDTO);
		flightGNInfoDTOs.add(gnCabinInfoDTO);
		flightGNInfoDTOs.add(gnCabinInfoDTO);
		flightGNInfoDTOs.add(gnCabinInfoDTO);
		gnCabinResponse.setGnCabinListDTOs(flightGNInfoDTOs);
		System.out.println(JSON.toJSON(gnCabinResponse));
	}
}
