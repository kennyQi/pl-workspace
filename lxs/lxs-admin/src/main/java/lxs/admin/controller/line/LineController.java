package lxs.admin.controller.line;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.CarouselService;
import lxs.app.service.app.RecommendService;
import lxs.app.service.app.SubjectService;
import lxs.app.service.line.DateSalePriceService;
import lxs.app.service.line.DayRouteService;
import lxs.app.service.line.LineActivityService;
import lxs.app.service.line.LineImageService;
import lxs.app.service.line.LineSelectiveService;
import lxs.app.service.line.LineService;
import lxs.app.service.line.LineSubjectService;
import lxs.domain.model.line.DateSalePrice;
import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineActivity;
import lxs.domain.model.line.LineBaseInfo;
import lxs.domain.model.line.LineImage;
import lxs.domain.model.line.LineSelective;
import lxs.domain.model.line.LineSubject;
import lxs.pojo.command.line.CreateLineSelectiveCommand;
import lxs.pojo.command.line.DeleteLineSelectiveCommand;
import lxs.pojo.command.line.InitLineCommand;
import lxs.pojo.dto.line.HotelInfoDTO;
import lxs.pojo.dto.line.PriceDTO;
import lxs.pojo.qo.line.DateSalePriceQO;
import lxs.pojo.qo.line.DayRouteQO;
import lxs.pojo.qo.line.LineActivityQO;
import lxs.pojo.qo.line.LineImageQO;
import lxs.pojo.qo.line.LineQO;
import lxs.pojo.qo.line.LineSelectiveQO;
import lxs.pojo.qo.line.LineSubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路控制器
 * @类修改者：
 * @修改日期：2015年5月7日下午2:12:11
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月7日下午2:12:11
 */
@Controller
@RequestMapping(value = "/line")
public class LineController {
	
	@Autowired
	private LineService lineService;
	@Autowired
	private DayRouteService dayRouteService;
	@Autowired
	private LineSelectiveService lineSelectiveService;
	@Autowired
	private DateSalePriceService dateSalePriceService;
	@Autowired
	private LineImageService lineImageService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private LineActivityService lineActivityService;
	@Autowired
	private LineSubjectService lineSubjectService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CarouselService carouselService;

	
	/**
	 * @方法功能说明：跳转线路主页
	 * @修改者名字：chenxy
	 * @修改时间：2015年2月3日下午5:26:37
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, Model model) {
		return "/line/lineSyncList.html";
	}

	/**
	 * 
	 * @方法功能说明：更改本地状态
	 * @修改者名字：cangs
	 * @修改时间：2015年10月12日上午10:15:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param lineID
	 * @参数：@param status
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/changeLocalStatus")
	@ResponseBody
	public String changeLocalStatus(HttpServletRequest request, @RequestParam(value = "id") String lineID,@RequestParam(value = "status") int status){
		Line line = lineService.get(lineID);
		line.setLocalStatus(status);
		lineService.update(line);
		if(status==2){
			List<String> subjectIDs = lineSubjectService.refresh(lineID);
			subjectService.refresh(subjectIDs);
			recommendService.refresh();
			carouselService.refresh();
			lineSelectiveService.delSelectiveByNullScenicSpot();
		}
		return DwzJsonResultUtil.createSimpleJsonString("200", "设置成功");
	}
	
	/**
	 * 
	 * @方法功能说明：置顶
	 * @修改者名字：cangs
	 * @修改时间：2015年10月12日下午1:55:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param lineID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/setTop")
	@ResponseBody
	public String setTop(HttpServletRequest request, @RequestParam(value = "id") String lineID,@RequestParam(value = "status") String status){
		Line line = lineService.get(lineID);
		if(StringUtils.equals(status, "set")){
			int sort = lineService.getMaxSort();
			line.setSort(sort+1);
		}else{
			line.setSort(0);
		}
		lineService.update(line);
		return DwzJsonResultUtil.createSimpleJsonString("200", "设置成功");
	}
	/**
	 * 
	 * @方法功能说明：跳转到活动线路页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月5日下午3:06:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/setActivityPage")
	public String setActivityPage(HttpServletRequest request, Model model,@RequestParam(value = "id") String lineID) {
		model.addAttribute("lineID", lineID);
		LineActivityQO lineActivityQO = new LineActivityQO();
		lineActivityQO.setLineID(lineID);
		LineActivity lineActivity = lineActivityService.queryUnique(lineActivityQO);
		if(lineActivity!=null){
			model.addAttribute("lineActivity", lineActivity);
		}
		return "/line/setActivity.html";
	}
	
	/**
	 * 
	 * @方法功能说明：设置为活动线路
	 * @修改者名字：cangs
	 * @修改时间：2015年6月5日下午3:06:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineID
	 * @参数：@param activityType
	 * @参数：@param travlerNO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/setActivity")
	@ResponseBody
	public String setActivity(HttpServletRequest request, Model model,
			@RequestParam(value = "lineID") String lineID,
			@RequestParam(value = "activityType") String activityType,
			@RequestParam(value = "travlerNO",required=false) String travlerNO) {
		LineActivityQO lineActivityQO = new LineActivityQO();
		lineActivityQO.setLineID(lineID);
		LineActivity lineActivity = lineActivityService.queryUnique(lineActivityQO);
		if(lineActivity!=null){
			lineActivityService.deleteById(lineActivity.getId());
		}
		Line line=lineService.get(lineID);
		lineActivity = new LineActivity();
		if(StringUtils.equals(activityType, "2")){
			//多人活动
			lineActivity.setId(UUIDGenerator.getUUID());
			lineActivity.setActivityType(activityType);
			lineActivity.setTravlerNO(travlerNO);
			lineActivity.setMinPrice(line.getMinPrice());
			lineActivity.setCreateDate(new Date());
			lineActivity.setLine(line);
		}else{
			//价格活动
			lineActivity.setId(UUIDGenerator.getUUID());
			lineActivity.setActivityType(activityType);
			lineActivity.setAdultUnitPrice(dateSalePriceService.getMin("adultPrice",lineID));
			lineActivity.setChildUnitPrice(dateSalePriceService.getMin("childPrice",lineID));
			lineActivity.setCreateDate(new Date());
			lineActivity.setLine(line);
		}
		lineActivityService.save(lineActivity);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "设置成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineList");
	}	
	
	/**
	 * 
	 * @方法功能说明：删除活动线路
	 * @修改者名字：cangs
	 * @修改时间：2015年6月5日下午3:09:55
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param activityID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/delActivity")
	@ResponseBody
	public String delActivity(HttpServletRequest request, Model model,
			@RequestParam(value = "id") String activityID){
		lineActivityService.deleteById(activityID);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "设置成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineList");
	}
	
	
	
	
	
	
	/**
	 * 
	 * @方法功能说明：线路同步
	 * @修改者名字：cangs
	 * @修改时间：2015年5月7日下午2:13:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync")
	public String syncLine(HttpServletRequest request, Model model) {
		// 添加分页参数
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		InitLineCommand initLineCommand = new InitLineCommand();
		if (StringUtils.isNotBlank(beginTime)) {
			initLineCommand.setStartDate(DateUtil.parseDate3(beginTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			initLineCommand.setEndDate(DateUtil.parseDate3(endTime));
		}
		try {
			lineService.initLineData(initLineCommand);
			//更新主题
			List<LineSubject> lineSubjects = lineSubjectService.queryList(new LineSubjectQO());
			for (LineSubject lineSubject : lineSubjects) {
				if(lineService.get(lineSubject.getLineID())==null){
					List<String> subjectIDs = lineSubjectService.refresh(lineSubject.getLineID());
					subjectService.refresh(subjectIDs);
				}
			}
			//更新推荐
			recommendService.refresh();
			//更新轮播
			carouselService.refresh();
			//更新精选
			lineSelectiveService.delSelectiveByNullScenicSpot();
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev", "【syncLine】"+"异常，"+HgLogger.getStackTrace(e));
		}
		return "/line/lineSyncList.html";
	}

	/**
	 * 
	 * @方法功能说明：线路列表
	 * @修改者名字：cangs
	 * @修改时间：2015年5月11日下午3:52:36
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/lineList")
	public String queryLineList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute LineQO lineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		int localstatus = lineQO.getLocalStatus();
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if(lineQO.getForSale()==0){
			lineQO.setForSaletype("yes");
		}
		if(localstatus==0){
			lineQO.setLocalStatus(LineQO.SHOW);
		}else if(localstatus==1){
			lineQO.setLocalStatus(LineQO.HIDE);
		}else{
			lineQO.setLocalStatus(LineQO.NOT_DEL);
		}
		List<String> stringss = new ArrayList<String>();
		if(StringUtils.isNotBlank(lineQO.getStart_province())&&StringUtils.isBlank(lineQO.getStartCity())){
			CityQo cityQo = new CityQo();
			cityQo.setProvinceCode(lineQO.getStart_province());
			List<City> cities = cityService.queryList(cityQo);
			for(City city:cities){
				stringss.add(city.getCode());
			}
		}
		lineQO.setSort(LineQO.QUERY_WITH_TOP);
		Pagination pagination = new Pagination();
		pagination.setCondition(lineQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = lineService.queryPagination(pagination);
		List<Line> lines=(List<Line>) pagination.getList();
		int count=0;
		for(Line line:lines){
			String destinationCity = "";
			if(line.getBaseInfo()!=null){
				if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
					String[] strings=line.getBaseInfo().getStarting().split(",");
					for(int i=0;i<strings.length;i++){
						destinationCity=destinationCity+cityService.get(strings[i]).getName();
						if(i+1<strings.length){
							destinationCity=destinationCity+",";
						}
					}
				}
			}
			LineBaseInfo lineBaseInfo = new LineBaseInfo();
			lineBaseInfo=line.getBaseInfo();
			lineBaseInfo.setStarting(destinationCity);
			lines.get(count).setBaseInfo(lineBaseInfo);
			count++;
		}
		pagination.setList(lines);
		model.addAttribute("pagination", pagination);
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinceList", provinceList);
		if(lineQO.getStart_province()!=null&&StringUtils.isNotBlank(lineQO.getStart_province())){
			CityQo cityQo = new CityQo();
			cityQo.setProvinceCode(lineQO.getStart_province());
			List<City> cities = cityService.queryList(cityQo);
			model.addAttribute("cityList",cities);
		}
		lineQO.setLocalStatus(localstatus);
		model.addAttribute("lineQO",lineQO);
		return "/line/linelist.html";
	}
	
	/**
	 * 
	 * @方法功能说明：线路详情
	 * @修改者名字：cangs
	 * @修改时间：2015年5月11日下午3:52:50
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param type
	 * @参数：@param lineID
	 * @参数：@param pictureFolderID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/info")
	public String getLineInfo(HttpServletRequest request, Model model,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "id") String lineID,
			@RequestParam(value = "pfid") String pictureFolderID) {
		Line line = lineService.get(lineID);
		if(line.getBaseInfo()!=null){
			LineBaseInfo lineBaseInfo = new LineBaseInfo();
			lineBaseInfo=line.getBaseInfo();
			String destinationCity = "";
			if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
				String[] strings=line.getBaseInfo().getStarting().split(",");
				for(int i=0;i<strings.length;i++){
					destinationCity=destinationCity+cityService.get(strings[i]).getName();
					if(i+1<strings.length){
						destinationCity=destinationCity+",";
					}
				}
			}
			lineBaseInfo.setStarting(destinationCity);
			destinationCity = "";
			if(line.getBaseInfo().getDestinationCity()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getDestinationCity())){
				String[] strings=line.getBaseInfo().getDestinationCity().split(",");
				for(int i=0;i<strings.length;i++){
					destinationCity=destinationCity+cityService.get(strings[i]).getName();
					if(i+1<strings.length){
						destinationCity=destinationCity+",";
					}
				}
			}
			lineBaseInfo.setDestinationCity(destinationCity);
			line.setBaseInfo(lineBaseInfo);
		}
		model.addAttribute("line", line);
		if(StringUtils.equals(type, "detail")){
			//查看详情
			return "/line/line_detail.html";
		}else if(StringUtils.equals(type, "route")){
			//查看行程
			DayRouteQO dayRouteQO = new DayRouteQO();
			dayRouteQO.setLineID(lineID);
			List<DayRoute> dayRoutes=dayRouteService.queryList(dayRouteQO);
			int count=0;
			for(DayRoute dayRoute:dayRoutes){
				String hotels="";
				if(dayRoute.getHotelInfoJson()!=null&&StringUtils.isNotBlank(dayRoute.getHotelInfoJson())){
					List<HotelInfoDTO> hotelInfoList = new ArrayList<HotelInfoDTO>();
					hotelInfoList=JSON.parseArray(dayRoute.getHotelInfoJson(),HotelInfoDTO.class);
					for (HotelInfoDTO hotelInfoDTO:hotelInfoList) {
						if(hotelInfoDTO.getStayLevel()==7){
							hotels+="暂无";
						}else{
							if(hotelInfoDTO.getStayLevel()<=3){
								hotels+=hotelInfoDTO.getHotelName()+"("+(hotelInfoDTO.getStayLevel()+2)+"星级)";
							}else{
								hotels+=hotelInfoDTO.getHotelName()+"("+(hotelInfoDTO.getStayLevel()-1)+"标星)";
							}
						}
					 }
				}
				dayRoutes.get(count).setHotelInfoJson(hotels);
				count++;
			}
			model.addAttribute("routes", dayRoutes);
			return "/line/line_route.html";
		}else{
			//查看图片
			LineImageQO lineImageQO= new LineImageQO();
			if(StringUtils.equals("home", pictureFolderID)){
				lineImageQO.setLineID(lineID);
			}else{
				lineImageQO.setLineDayRouteId(pictureFolderID);
			}
			List<LineImage> lineImages=lineImageService.queryList(lineImageQO);
			int count=0;
			for(LineImage lineImage:lineImages){
				if(lineImage.getUrlMapsJSON()!=null){
					Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
					if(lineMap.get(SysProperties.getInstance().get("slfxImageType"))==null){
						if(lineMap.get("default")!=null){
							lineImages.get(count).setFdfsFileInfoJSON(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
						}
					}else{
						lineImages.get(count).setFdfsFileInfoJSON(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageType")));
					}
				}
				count++;
			}
			model.addAttribute("pictures",lineImages);
			DayRouteQO dayRouteQO = new DayRouteQO();
			dayRouteQO.setLineID(lineID);
			List<DayRoute> dayRoutes= dayRouteService.queryList(dayRouteQO);
			model.addAttribute("picturefolders", dayRoutes);
			model.addAttribute("lineID", lineID);
			return "/line/line_picture.html";
		}
	}
	
	/**
	 * 
	 * @方法功能说明：精选列表
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日下午7:35:39
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineSelectiveQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/lineSelectiveList")
	public String queryLineSelectiveList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute LineSelectiveQO lineSelectiveQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if(StringUtils.isNotBlank(lineSelectiveQO.getBeginTime()) && StringUtils.isBlank(lineSelectiveQO.getEndTime())){
			lineSelectiveQO.setEndTime(lineSelectiveQO.getBeginTime());
		}
		if(StringUtils.isBlank(lineSelectiveQO.getBeginTime()) && StringUtils.isNotBlank(lineSelectiveQO.getEndTime())){
			lineSelectiveQO.setBeginTime(lineSelectiveQO.getEndTime());
		}
		Pagination pagination = new Pagination();
		pagination.setCondition(lineSelectiveQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = lineSelectiveService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("lineSelectiveQO",lineSelectiveQO);
		return "/line/lineSelectiveList.html";
	}

	/**
	 * 
	 * @方法功能说明：调整精选位置
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日下午7:35:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param lineSelectiveID
	 * @参数：@param type
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/changeSort")
	@ResponseBody
	public String changeSort(HttpServletRequest request,
			@RequestParam(value = "id") String lineSelectiveID,
			@RequestParam(value = "type") String type){
		LineSelective lineSelective = lineSelectiveService.get(lineSelectiveID);
		LineSelectiveQO lineSelectiveQO = new LineSelectiveQO();
		lineSelectiveQO.setForSale(1);
		if(lineSelective.getSort()==1&&StringUtils.equals("up", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该线路已在最末尾");
		}else if(lineSelective.getSort()==lineSelectiveService.getMaxSort()&&StringUtils.equals("down", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该线路已在最第一位");
		}else{
			if(StringUtils.equals("down", type)){
				int sort_a=lineSelective.getSort();
				int sort_b=0;
				lineSelectiveQO.setSort(lineSelective.getSort()+1);
				LineSelective lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
				int i=1;
				while(lineSelective2==null){
					lineSelectiveQO.setSort(lineSelective.getSort()+1+i);
					lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
					i++;
				}
				sort_b=lineSelective2.getSort();
				lineSelective.setSort(sort_b);
				lineSelective2.setSort(sort_a);
				lineSelectiveService.update(lineSelective);
				lineSelectiveService.update(lineSelective2);
				return DwzJsonResultUtil.createSimpleJsonString("200", "下移成功");
			}else{
				int sort_a=lineSelective.getSort();
				int sort_b=0;
				lineSelectiveQO.setSort(lineSelective.getSort()-1);
				LineSelective lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
				int i=1;
				while(lineSelective2==null){
					lineSelectiveQO.setSort(lineSelective.getSort()-1-i);
					lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
					i++;
				}
				sort_b=lineSelective2.getSort();
				lineSelective.setSort(sort_b);
				lineSelective2.setSort(sort_a);
				lineSelectiveService.update(lineSelective);
				lineSelectiveService.update(lineSelective2);
				return DwzJsonResultUtil.createSimpleJsonString("200", "上移成功");
			}
		}
	}
	

	/**
	 * 
	 * @方法功能说明：设置精选
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日下午7:34:19
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param lineID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/selective")
	@ResponseBody
	public String createLineSelective(HttpServletRequest request,
			@RequestParam(value = "id") String lineID){
		//设置优选
		LineSelectiveQO lineSelectiveQO = new LineSelectiveQO();
		lineSelectiveQO.setLineID(lineID);
		if(lineSelectiveService.queryCount(lineSelectiveQO)==0){
			CreateLineSelectiveCommand command = new CreateLineSelectiveCommand();
			command.setLineId(lineID);
			//1：线路
			command.setType("1");
			//产品名称
			command.setName(lineService.get(lineID).getBaseInfo().getName());
			lineSelectiveService.createLineSelective(command);
			return DwzJsonResultUtil.createSimpleJsonString("200", "设置成功");
		}else{
			return DwzJsonResultUtil.createSimpleJsonString("500", "该线路已经是精选线路");
		}
	}	
	
	/**
	 * 
	 * @方法功能说明：取消精选
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日下午7:34:56
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param lineSelectiveID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/deleteLineSelective")
	@ResponseBody
	public String deleteLineSelective(HttpServletRequest request,
			@RequestParam(value = "id") String lineSelectiveID){
		LineSelective lineSelective = lineSelectiveService.get(lineSelectiveID);
		LineSelective lineSelective2 = null;
		if(lineSelective.getSort()==1&&lineSelectiveService.getMaxSort()!=1){
			LineSelectiveQO lineSelectiveQO= new LineSelectiveQO();
			lineSelectiveQO.setForSale(1);
			lineSelectiveQO.setSort(lineSelective.getSort()+1);
			lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
			int i=1;
			while(lineSelective2==null){
				lineSelectiveQO.setSort(lineSelective.getSort()+1+i);
				lineSelective2=lineSelectiveService.queryUnique(lineSelectiveQO);
				i++;
			}
			lineSelective2.setSort(1);
			lineSelectiveService.update(lineSelective2);
		}
		DeleteLineSelectiveCommand deleteLineSelectiveCommand = new DeleteLineSelectiveCommand();
		deleteLineSelectiveCommand.setId(lineSelectiveID);
		lineSelectiveService.deleteLineSelective(deleteLineSelectiveCommand);
		return DwzJsonResultUtil.createSimpleJsonString("200", "取消成功");
	}
	
	
	/**
	 * 
	 * @方法功能说明：查询城市
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日下午6:17:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param province
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request, Model model,
			@RequestParam(value = "province", required = false) String province) {
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		return JSON.toJSONString(cityList);
	}
	
	
	@RequestMapping(value="/list")
	public String toPriceList(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=false) String lineID){
		model.addAttribute("lineID", lineID);
		return "/line/price/price_list.html";
	}
	
	@RequestMapping(value="/datesaleprice")
	public String toDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="lineID",required=false) String lineID){
		model.addAttribute("lineID", lineID);
		return "/line/price/datesaleprice.html";
	}
	
	
	
	/**
	 * 查询线路团期列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/query")
	@ResponseBody
	public String queryDateSalePriceList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="lineID",required=false) String lineID,
			@RequestParam(value="month",required=false) Integer month,
			@RequestParam(value="year",required=false) Integer year){
		
		try{
			
			List<PriceDTO> priceDTOList = new ArrayList<PriceDTO>();
			
			//年月不存在 则默认显示本月的团期数据
			Calendar calendar = Calendar.getInstance();
			if(year != 0){
				calendar.set(Calendar.YEAR, year);
			}
			if(month != -1){
				calendar.set(Calendar.MONTH, month);
			}
			
			//获取当月的第一天
			calendar.set(Calendar.DAY_OF_MONTH,1);
			Date firstDay  = calendar.getTime();
			//获取当月的最后一天
			calendar.add(Calendar.MONDAY,1);
			calendar.add(Calendar.DAY_OF_MONTH,-1);
			Date lastDay =  calendar.getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date currentDate = firstDay;
			calendar.setTime(currentDate);
			while(currentDate.before(lastDay) || currentDate.equals(lastDay)){
				DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
				dateSalePriceQO.setLineID(lineID);
				String currentDateStr = format.format(currentDate);
				dateSalePriceQO.setSaleDate(currentDateStr);
				
				DateSalePrice dateSalePriceDTO = dateSalePriceService.queryUnique(dateSalePriceQO);
				PriceDTO priceDTO = new PriceDTO();
				priceDTO.setSaleDate(currentDateStr);
				if(dateSalePriceDTO != null){
					priceDTO.setId(dateSalePriceDTO.getId());
					priceDTO.setAdultPrice(dateSalePriceDTO.getAdultPrice());
					priceDTO.setChildPrice(dateSalePriceDTO.getChildPrice());
					priceDTO.setLineID(dateSalePriceDTO.getLine().getId());
					priceDTO.setNumber(dateSalePriceDTO.getNumber());
				}else{ //不存在则价格人数显示0
					priceDTO.setAdultPrice(0.00);
					priceDTO.setChildPrice(0.00);
					priceDTO.setNumber(0);
				}
				priceDTOList.add(priceDTO);
				calendar.add(Calendar.DATE,1);
				currentDate =  calendar.getTime();
			}
			
			return JSON.toJSONString(priceDTOList);
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-查询团期失败" + HgLogger.getStackTrace(e));
			return "";
		}
		
	}

}
