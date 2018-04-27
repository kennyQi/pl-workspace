package lxs.api.v1.response.app;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.CarouselDTO;

/**
 * 
 * @类功能说明：查询轮播图接口返回
 * @类修改者：
 * @修改日期：2015年9月18日上午11:05:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:05:51
 */
public class QueryCarouselResponse extends ApiResponse {

	private List<CarouselDTO> carouselDTOs;

	public List<CarouselDTO> getCarouselDTOs() {
		return carouselDTOs;
	}

	public void setCarouselDTOs(List<CarouselDTO> carouselDTOs) {
		this.carouselDTOs = carouselDTOs;
	}

}
