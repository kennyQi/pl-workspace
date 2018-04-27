package hsl.h5.base.result.mp;

import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.mp.ScenicSpotDTO;

/**
 * 门票景点查询返回
 * 
 * @author yuxx
 * 
 */
public class MPQueryScenicSpotsResult extends ApiResult{

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
