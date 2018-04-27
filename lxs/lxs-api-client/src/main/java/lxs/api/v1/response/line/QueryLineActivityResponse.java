package lxs.api.v1.response.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.MinPriceActivityDTO;
import lxs.api.v1.dto.line.TravlerNOActivityDTO;

public class QueryLineActivityResponse extends ApiResponse {
	private List<TravlerNOActivityDTO> travlerNOActivityDTO;

	private List<MinPriceActivityDTO> minPriceActivityDTO;

	public List<TravlerNOActivityDTO> getTravlerNOActivityDTO() {
		return travlerNOActivityDTO;
	}

	public void setTravlerNOActivityDTO(
			List<TravlerNOActivityDTO> travlerNOActivityDTO) {
		this.travlerNOActivityDTO = travlerNOActivityDTO;
	}

	public List<MinPriceActivityDTO> getMinPriceActivityDTO() {
		return minPriceActivityDTO;
	}

	public void setMinPriceActivityDTO(List<MinPriceActivityDTO> minPriceActivityDTO) {
		this.minPriceActivityDTO = minPriceActivityDTO;
	}

	
}
