package lxs.api.controller.lineH5;

import hg.common.util.SysProperties;
import hg.system.model.meta.City;
import hg.system.service.impl.CityServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lxs.api.controller.BaseController;
import lxs.app.service.line.DayRouteService;
import lxs.app.service.line.LineImageService;
import lxs.app.service.line.LineService;
import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.HotelInfo;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineImage;
import lxs.pojo.dto.line.HotelInfoDTO;
import lxs.pojo.qo.line.DayRouteQO;
import lxs.pojo.qo.line.LineImageQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("line")
public class LineController extends BaseController{

	@Autowired
	private LineService lineService;
	
	@Autowired
	private DayRouteService dayRouteService;
	
	@Autowired
	private CityServiceImpl cityServiceImpl;
	
	@Autowired
	private LineImageService lineImageService;

	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/detail");
		Line line = lineService.get(id);
		if(line.getBaseInfo()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
			City city = cityServiceImpl.get(line.getBaseInfo().getStarting());
			mav.addObject("city", city);
		}
		//线路
		mav.addObject("line", line);
		LineImageQO lineImageQO = new LineImageQO();
		lineImageQO.setLineID(id);
		//线路轮播图
		mav.addObject("headImages", getImageURL(lineImageService.queryList(lineImageQO)));
		DayRouteQO dayRouteQO = new DayRouteQO();
		dayRouteQO.setLineID(id);
		List<DayRoute> dayRoutes = dayRouteService.queryList(dayRouteQO);
		int i = 0;
		for (DayRoute dayRoute : dayRoutes) {
			if(dayRoute.getHotelInfoJson()!=null&&StringUtils.isNotBlank(dayRoute.getHotelInfoJson())){
				List<HotelInfo> hotelList = JSON.parseArray(dayRoute.getHotelInfoJson(), HotelInfo.class);
				String hotelSTR = "";
				for (HotelInfo hotelInfo : hotelList) {
					if(hotelInfo.getStayLevel()==7){
						hotelSTR="暂无";
					}else{
						if(hotelInfo.getStayLevel()<=3){
							hotelSTR=hotelInfo.getHotelName()+"("+(hotelInfo.getStayLevel()+2)+"星级)";
						}else{
							hotelSTR=hotelInfo.getHotelName()+"("+(hotelInfo.getStayLevel()-1)+"标星)";
						}
					}
				}
				dayRoutes.get(i).setHotelInfoJson(hotelSTR);
			}
			//行程
			mav.addObject("dayRoutes", dayRoutes);
			lineImageQO = new LineImageQO();
			lineImageQO.setLineDayRouteId(dayRoute.getId());
			List<LineImage> lineImages = lineImageService.queryList(lineImageQO);
			dayRoutes.get(i).setLineImageList(getImageURL(lineImages));
			i++;
		}
		return mav;
	}
	
	public List<LineImage> getImageURL(List<LineImage> lineImages){
		int i = 0;
		for (LineImage lineImage : lineImages) {
			if(lineImage.getUrlMapsJSON()!=null){
				Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(),Map.class);
				if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
					if(lineMap.get("default")!=null){
						lineImages.get(i).setUrlMapsJSON(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
					}
				}else{
					lineImages.get(i).setUrlMapsJSON(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
				}
			}
			i++;
		}
		return lineImages;
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("favorable")
	public ModelAndView favorable(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/favorable");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}
	
	@RequestMapping("feature")
	public ModelAndView feature(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/feature");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}
	
	@RequestMapping("traffic")
	public ModelAndView traffic(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/traffic");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}   
	
	@RequestMapping("notice")
	public ModelAndView notice(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/notice");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}
	
	@RequestMapping("fee")
	public ModelAndView fee(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/fee");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}
	
	@RequestMapping("book")
	public ModelAndView book(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/book");
		Line line = lineService.get(id);
		mav.addObject("line", line);
		return mav;
	}
	@RequestMapping("dayRoute")
	public ModelAndView dayRoute(@RequestParam(value="id",required=true) String id){
		ModelAndView mav = new ModelAndView("line/dayroute");
		DayRouteQO dayRouteQO = new DayRouteQO();
		dayRouteQO.setLineID(id);
		List<DayRoute> dayRoutes = dayRouteService.queryList(dayRouteQO);
		int count=0;
		for(DayRoute dayRoute:dayRoutes){
			String hotels="";
			if(dayRoute.getHotelInfoJson()!=null&&StringUtils.isNotBlank(dayRoute.getHotelInfoJson())){
				List<HotelInfoDTO> hotelInfoList = new ArrayList<HotelInfoDTO>();
				hotelInfoList=JSON.parseArray(dayRoute.getHotelInfoJson(),HotelInfoDTO.class);
				for (HotelInfoDTO hotelInfoDTO:hotelInfoList) {
					if(hotelInfoDTO.getStayLevel()<=3){
						hotels+=hotelInfoDTO.getHotelName()+"("+(hotelInfoDTO.getStayLevel()+2)+"星级)";
					}else{
						hotels+=hotelInfoDTO.getHotelName()+"("+(hotelInfoDTO.getStayLevel()-1)+"标星)";
					}
				 }
			}
			dayRoutes.get(count).setHotelInfoJson(hotels);
			count++;
		}
		mav.addObject("dayRoutes", dayRoutes);
		return mav;
	}
	
}
