package lxs.api.v1.response.app;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.RecommendDTO;

/**
 * 
 * @类功能说明：查询推荐接口返回
 * @类修改者：
 * @修改日期：2015年9月18日上午11:06:11
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:06:11
 */
public class QueryRecommendResponse extends ApiResponse {

	private List<RecommendDTO> recommendDTOs;

	public List<RecommendDTO> getRecommendDTOs() {
		return recommendDTOs;
	}

	public void setRecommendDTOs(List<RecommendDTO> recommendDTOs) {
		this.recommendDTOs = recommendDTOs;
	}

}
