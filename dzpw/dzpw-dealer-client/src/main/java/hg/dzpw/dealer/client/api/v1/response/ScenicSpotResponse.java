package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;

import hg.dzpw.dealer.client.common.ApiPageResponse;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;

/**
 * @类功能说明：景区查询结果
 * @类修改者：
 * @修改日期：2014-11-24上午10:57:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:57:18
 */
@SuppressWarnings("serial")
public class ScenicSpotResponse extends ApiPageResponse {

	/** 查询失败 */
	public final static String RESULT_QUERY_FAIL = "-1";

	/**
	 * 景区列表
	 */
	private List<ScenicSpotDTO> scenicSpots;

	public List<ScenicSpotDTO> getScenicSpots() {
		return scenicSpots;
	}

	public void setScenicSpots(List<ScenicSpotDTO> scenicSpots) {
		this.scenicSpots = scenicSpots;
	}

	public ScenicSpotResponse() {
		super();
	}

	public ScenicSpotResponse(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

}
