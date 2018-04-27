package lxs.api.action.app;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.CarouselDTO;
import lxs.api.v1.request.qo.app.QueryCarouselQO;
import lxs.api.v1.response.app.QueryCarouselResponse;
import lxs.app.service.app.CarouselService;
import lxs.domain.model.app.Carousel;
import lxs.pojo.qo.app.CarouselQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component("QueryCarouselAction")
public class QueryCarouselAction implements LxsAction {

	@Autowired
	private CarouselService carouselService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev","【QueryCarouselAction】" + "进入action");
		QueryCarouselResponse carouselResponse = new QueryCarouselResponse();
		try {
			QueryCarouselQO queryCarouselQO = JSON.parseObject(apiRequest.getBody().getPayload(), QueryCarouselQO.class);
			List<CarouselDTO> carouselDTOs = new ArrayList<CarouselDTO>();
			CarouselQO carouselQO = new CarouselQO();
			if(queryCarouselQO.getCarouselLevel()!=null){
				carouselQO.setCarouselLevel(queryCarouselQO.getCarouselLevel());
			}
			carouselQO.setStatus(Carousel.ON);
			List<Carousel> carousels = carouselService.queryList(carouselQO);
			HgLogger.getInstance().info("lxs_dev","【QueryCarouselAction】【carousels长度】" + carousels.size());
			if (carousels != null && carousels.size() > 0) {
				for (Carousel carousel : carousels) {
					CarouselDTO carouselDTO = new CarouselDTO();
					carouselDTO.setCreateDate(carousel.getCreateDate());
					carouselDTO.setImageURL(SysProperties.getInstance().get("fileUploadPath")+carousel.getImageURL());
					carouselDTO.setCarouselType(carousel.getCarouselType());
					carouselDTO.setCarouselAction(carousel.getCarouselAction());
					carouselDTO.setNote(carousel.getNote());
					carouselDTOs.add(carouselDTO);
				}
				carouselResponse.setCarouselDTOs(carouselDTOs);
				carouselResponse.setMessage("查询成功");
				carouselResponse.setResult(ApiResponse.RESULT_CODE_OK);
				HgLogger.getInstance().info("lxs_dev","【QueryCarouselAction】【carouselDTOs长度】"+ carouselDTOs.size());
				HgLogger.getInstance().info("lxs_dev","【QueryCarouselAction】" + "查询轮播图片成功");
			} else {
				carouselResponse.setCarouselDTOs(carouselDTOs);
				carouselResponse.setMessage("查询成功");
				carouselResponse.setResult(ApiResponse.RESULT_CODE_OK);
			}
		} catch (Exception e) {
			carouselResponse.setMessage("查询失败");
			carouselResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryCarouselAction】"+"carouselResponse:"+JSON.toJSONString(carouselResponse));
		return JSON.toJSONString(carouselResponse);
	}

}
