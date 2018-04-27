package plfx.admin.controller.traveline;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.xl.pojo.command.route.CreateDayRouteCommand;
import plfx.xl.pojo.command.route.DeleteDayRouteCommand;
import plfx.xl.pojo.command.route.ModifyDayRouteCommand;
import plfx.xl.pojo.command.route.dto.HotelRequestDTO;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.route.DayRouteDTO;
import plfx.xl.pojo.dto.route.HotelInfoDTO;
import plfx.xl.pojo.dto.route.LineRouteDTO;
import plfx.xl.pojo.exception.SlfxXlException;
import plfx.xl.pojo.qo.DayRouteQO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.pojo.system.LineConstants;
import plfx.xl.spi.inter.DayRouteService;
import plfx.xl.spi.inter.LineService;

import com.alibaba.fastjson.JSON;

/**
 *@类功能说明：线路行程Controller
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月12日上午11:14:09
 */
@Controller
@RequestMapping("/traveline/route")
public class LineRouteController extends BaseController{
	
	@Autowired
	private DayRouteService dayRouteService;
	@Autowired
	private LineService lineService;
	
	/**
	 * 
	 * @方法功能说明：跳转到添加行程页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月17日上午11:12:30
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toadd")
	public String toAddRoute(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "id")String lineID){
		
		//查询每日行程列表
		DayRouteQO dayRouteQO = new DayRouteQO();
		dayRouteQO.setLineID(lineID);
		dayRouteQO.setDaysAsc(true);
		List<DayRouteDTO> dayRouteDTOList = new ArrayList<DayRouteDTO>();
		dayRouteDTOList = dayRouteService.queryList(dayRouteQO);
		
		//查询线路行程信息
		LineQO lineQO = new LineQO();
		lineQO.setId(lineID);
		LineDTO lineDTO = lineService.queryUnique(lineQO);
		
		LineRouteDTO lineRouteDTO = new LineRouteDTO();
		if(lineDTO != null && lineDTO.getRouteInfo() != null){
			lineRouteDTO.setRouteName(lineDTO.getRouteInfo().getRouteName());
			lineRouteDTO.setRouteDays(lineDTO.getRouteInfo().getRouteDays());
			lineRouteDTO.setShoppingTimeHour(lineDTO.getRouteInfo().getShoppingTimeHour());
			
			if(dayRouteDTOList.size() != 0){
				for(DayRouteDTO dayRouteDTO:dayRouteDTOList){
					String hotelInfoJson = dayRouteDTO.getHotelInfoJson();
					List<HotelInfoDTO> hotelInfoList = new ArrayList<HotelInfoDTO>();
					if(StringUtils.isNotBlank(hotelInfoJson)){
						hotelInfoList = JSON.parseArray(hotelInfoJson, HotelInfoDTO.class);
					}
					dayRouteDTO.setHotelList(hotelInfoList);
				}
				lineRouteDTO.setDayRouteList(dayRouteDTOList);;
			}
		}
		model.addAttribute("lineRoute",lineRouteDTO);
		model.addAttribute("vehicleMap", LineConstants.vehicleMap);
		model.addAttribute("stayLevelMap", LineConstants.stayLevelMap);
		model.addAttribute("lineId", lineID);
		return "/traveline/route/route_add.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明： 添加一日详情
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月17日上午11:12:09
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String 一日行程ID
	 * @throws
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public String addDayRoute(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute CreateDayRouteCommand command){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		
		try{
			//判断前一天日程是否已经保存
			if(command.getDays() != 1){
				Integer preDay = command.getDays() - 1;
				DayRouteQO dayRouteQO = new DayRouteQO();
				dayRouteQO.setLineID(command.getLineID());
				dayRouteQO.setDays(preDay);
				DayRouteDTO dayRouteDTO = dayRouteService.queryUnique(dayRouteQO);
				if(dayRouteDTO == null){
					resultMap.put("success", "false");
					resultMap.put("message", "请先保存第" + preDay + "天行程");
					return JSON.toJSONString(resultMap);
				}
			}
			     
			//删除一日行程信息中的空的酒店信息
				List<HotelRequestDTO> hotelList = command.getHotelList();
				if(hotelList != null && hotelList.size() != 0){
					Iterator<HotelRequestDTO> iterator = hotelList.iterator();
					while(iterator.hasNext()){
						HotelRequestDTO hotelDTO = (HotelRequestDTO)iterator.next();
						//酒店星级或者酒店名称为空时 删除此酒店信息
						if(StringUtils.isBlank(hotelDTO.getHotelName()) || hotelDTO.getStayLevel() == null){ 
							iterator.remove();
						}
					}
				}
		  
			//回传一日行程ID到页面
			DayRouteDTO dayRouteDTO =  dayRouteService.createDayRoute(command);
			if(StringUtils.isNotBlank(dayRouteDTO.getId())){
				resultMap.put("success", "true");
				resultMap.put("message", dayRouteDTO.getId());
			}else{
				resultMap.put("success", "false");
				resultMap.put("message","添加行程失败");
			}
		
		}catch(SlfxXlException e){
			if(e.getCode().equals(SlfxXlException.CREATE_DAYROUTE_DAY_NOT_NOTVALID)){
				resultMap.put("success", "false");
				resultMap.put("message", e.getMessage() + "，请先保存行程天数信息，再添加行程");
			}else{
				resultMap.put("success", "false");
				resultMap.put("message", e.getMessage());
			}
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-旅游线路资源维护-新增一日行程失败" + HgLogger.getStackTrace(e));
			resultMap.put("success", "false");
			resultMap.put("message", "添加行程失败");
		}
		return JSON.toJSONString(resultMap);
		
	}
	
	/**
	 * 
	 * @方法功能说明：添加一日行程前获取常量
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月17日上午11:12:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @return:String 常量JSON字符串
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/queryconstants")
	public String queryRouteConstants(HttpServletRequest request,HttpServletResponse response){
		
		List<String> list = new ArrayList<String>();
		list.add(JSON.toJSONString(LineConstants.vehicleMap));
		list.add(JSON.toJSONString(LineConstants.stayLevelMap));
		return JSON.toJSONString(list);
	}
	
	/**
	 * 
	 * @方法功能说明：修改一日行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:22:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public String modifyDayRoute(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute ModifyDayRouteCommand command){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		try{
		     
			String message = "修改成功";
			//删除每日行程信息中的空的酒店信息
				List<HotelRequestDTO> hotelList = command.getHotelList();
				if(hotelList != null && hotelList.size() != 0){
					Iterator<HotelRequestDTO> iterator = hotelList.iterator();
					while(iterator.hasNext()){
						HotelRequestDTO hotelDTO = (HotelRequestDTO)iterator.next();
						//酒店星级或者酒店名称为空时 删除此酒店信息
						if(StringUtils.isBlank(hotelDTO.getHotelName()) || hotelDTO.getStayLevel() == null){ 
							iterator.remove();
						}
					}
				}
			
			
			Boolean result = dayRouteService.modifyDayRoute(command);
			if(!result){
				message = "修改失败";
			}
			
			resultMap.put("success", result?"true":"false");
			resultMap.put("message", message);
		
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-旅游线路资源维护-修改一日行程失败" + HgLogger.getStackTrace(e));
			resultMap.put("success", "false");
			resultMap.put("message", "修改失败");
		}
		
		return JSON.toJSONString(resultMap);
	}
	
	/**
	 * @方法功能说明：删除一日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:23:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteDayRoute(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute DeleteDayRouteCommand command){
		
		try{
			String message = "删除成功";
			Boolean result = dayRouteService.deleteDayRoute(command);
			if(!result){
				message = "删除失败";
			}
			return message;
		
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-旅游线路资源维护-删除一日行程失败" + HgLogger.getStackTrace(e));
			return "删除失败";
		}
	}
}