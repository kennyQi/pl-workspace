package hsl.h5.control;

import java.util.List;

import javax.annotation.Resource;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hsl.h5.base.utils.OpenidTracker;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.spi.inter.ad.HslAdPositionService;
import hsl.spi.inter.ad.HslAdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
public class IndexCtrl extends HslCtrl {
	
	@Resource
	private HslAdService hslAdService;
	@Resource
	private HslAdPositionService hslAdPositionService;
	
	
	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		Pagination adpage = new Pagination();
		adpage.setPageNo(1);
		adpage.setPageSize(getShowNo(HslAdConstant.HSL_H5_BANNER));
		HslAdQO hslAdQO = new HslAdQO();
		hslAdQO.setPositionId(HslAdConstant.HSL_H5_BANNER);
		hslAdQO.setIsShow(true);
		adpage.setCondition(hslAdQO);
		adpage = hslAdService.queryPagination(adpage);
		List<AdDTO> list = (List<AdDTO>)adpage.getList();
		if(list!=null&&list.size()>0){
			mav.addObject("adList", JSON.toJSONString(list));
		}else{
			mav.addObject("adList", "{}");
		}
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	@RequestMapping("welcome")
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView("welcome");
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	
	private int getShowNo(String positionId){
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		hslAdPositionQO.setPositionId(positionId);
		HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
		if(null == hslAdPositionDTO){
			HgLogger.getInstance().info("yuqz", "IndexController->getShowNo->查询广告位信息失败:" + JSON.toJSONString(hslAdPositionDTO));
		}
		return hslAdPositionDTO.getShowInfo().getShowNo();
	}
}
