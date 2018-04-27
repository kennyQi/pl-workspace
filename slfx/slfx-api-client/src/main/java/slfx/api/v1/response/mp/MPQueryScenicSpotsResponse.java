package slfx.api.v1.response.mp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;

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
