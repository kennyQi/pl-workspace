package hsl.api.v1.response.mp;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.mp.ScenicSpotDTO;

/**
 * 门票景点查询返回
 * 
 * @author yuxx
 * 
 */
public class MPQueryScenicSpotsResponse extends ApiResponse {

	private List<ScenicSpotDTO> ScenicSpots;

	private Integer totalCount;


	/**
	 * 景点查询无结果
	 */
	public final static String RESULT_SCENICSPOT_NOTFOUND = "-1";
	
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
