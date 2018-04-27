package plfx.api.client.api.v1.mp.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;

/**
 * 门票景点查询返回
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class MPQueryScenicSpotsResponse extends ApiResponse {

	private List<ScenicSpotDTO> ScenicSpots;

	private Integer totalCount;

	public List<ScenicSpotDTO> getScenicSpots() {
		return ScenicSpots;
	}

	public void setScenicSpots(List<ScenicSpotDTO> scenicSpots) {
		ScenicSpots = scenicSpots;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
