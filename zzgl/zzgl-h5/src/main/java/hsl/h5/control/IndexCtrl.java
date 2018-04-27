package hsl.h5.control;

import java.util.List;

import javax.annotation.Resource;

import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hsl.h5.base.utils.OpenidTracker;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.dto.ad.HslAdPositionShowInfoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.spi.inter.ad.HslAdPositionService;
import hsl.spi.inter.ad.HslAdService;
import hsl.spi.inter.line.HslLineService;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private HslLineService hslLineService;
	
	@RequestMapping("init")
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView("index");
		Pagination adpage = new Pagination();
		adpage.setPageNo(1);
		int showNo=getShowNo(HslAdConstant.HSL_H5_BANNER);
		if(showNo==0){
			//显示条数获取为空时，默认显示6条
			showNo=6;
		}
		adpage.setPageSize(showNo);
		HslAdQO hslAdQO = new HslAdQO();
		hslAdQO.setPositionId(HslAdConstant.HSL_H5_BANNER);
		hslAdQO.setIsShow(true);
		adpage.setCondition(hslAdQO);
		adpage = hslAdService.queryPagination(adpage);
		@SuppressWarnings("unchecked")
		List<AdDTO> list = (List<AdDTO>)adpage.getList();
		if(list!=null&&list.size()>0){
			mav.addObject("adList", JSON.toJSONString(list));
		}else{
			mav.addObject("adList", "{}");
		}
		
		//热门推荐线路
		HslLineQO hslLineQO=new HslLineQO();
		Pagination linePagination = new Pagination();
		linePagination.setPageNo(1);
		linePagination.setPageSize(6);
		linePagination.setCondition(hslLineQO);
		linePagination.setCheckPassLastPageNo(false);
		linePagination = hslLineService.queryPagination(linePagination);
		
		@SuppressWarnings("unchecked")
		List<LineDTO> lineList=(List<LineDTO>) linePagination.getList();
		
		mav.addObject("lineList", lineList);
		
		String image_host=SysProperties.getInstance().get("image_host");
		HgLogger.getInstance().info("renfeng", "IndexController->init,image_host:" + image_host);
		
		mav.addObject("image_host", image_host);
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	@RequestMapping("welcome")
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView("welcome");
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	@RequestMapping("error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView("/error");
		return mav;
	}
	private int getShowNo(String positionId){
		HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
		hslAdPositionQO.setPositionId(positionId);
		HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
		if(null == hslAdPositionDTO){
			HgLogger.getInstance().info("yuqz", "IndexController->getShowNo->查询广告位信息失败:" + JSON.toJSONString(hslAdPositionDTO));
			return 0;
		}
		HslAdPositionShowInfoDTO info=hslAdPositionDTO.getShowInfo();
		return info.getShowNo();
	}
}
