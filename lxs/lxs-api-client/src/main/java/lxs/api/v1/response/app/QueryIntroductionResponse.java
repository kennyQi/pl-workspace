package lxs.api.v1.response.app;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.IntroductionDTO;

/**
 * 
 * @类功能说明：查询轮播图接口返回
 * @类修改者：
 * @修改日期：2015年9月18日上午11:05:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015年9月18日上午11:05:51
 */
public class QueryIntroductionResponse extends ApiResponse {

	private List<IntroductionDTO> introductionDtos;

	public List<IntroductionDTO> getIntroductionDtos() {
		return introductionDtos;
	}

	public void setIntroductionDtos(List<IntroductionDTO> introductionDtos) {
		this.introductionDtos = introductionDtos;
	}

	

}
