package lxs.api.v1.response.mp;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.RegionDTO;

public class DZPWProvinceResponse  extends ApiResponse{

	private List<RegionDTO> regionDTOs;

	public List<RegionDTO> getRegionDTOs() {
		return regionDTOs;
	}

	public void setRegionDTOs(List<RegionDTO> regionDTOs) {
		this.regionDTOs = regionDTOs;
	}

}
