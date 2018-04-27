package slfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import slfx.admin.controller.BaseController;
import slfx.xl.pojo.command.line.AuditLineCommand;
import slfx.xl.pojo.command.line.CopyLineCommand;
import slfx.xl.pojo.command.line.CreateLineCommand;
import slfx.xl.pojo.command.line.GroundingLineCommand;
import slfx.xl.pojo.command.line.ModifyLineCommand;
import slfx.xl.pojo.command.line.UnderCarriageLineCommand;
import slfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
import slfx.xl.pojo.dto.LineSupplierDTO;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.price.DateSalePriceDTO;
import slfx.xl.pojo.qo.DateSalePriceQO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.pojo.qo.LineSupplierQO;
import slfx.xl.pojo.system.LineConstants;
import slfx.xl.pojo.system.SupplierConstants;
import slfx.xl.spi.inter.DateSalePriceService;
import slfx.xl.spi.inter.LineService;
import slfx.xl.spi.inter.LineSupplierService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：线路Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月12日下午4:40:29
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping("/traveline/line")
public class LineController extends BaseController {

	@Autowired
	private LineSupplierService lineSupplierService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private LineService lineService;
	@Autowired
	private DateSalePriceService xlDateSalePriceService;

	/**
	 * 查询线路列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String queryLineList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute LineQO lineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {
		HgLogger.getInstance().info("yuqz", "LineController->queryLineList->开始查询>>>>>>>");
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);

		if (lineQO == null) {
			lineQO = new LineQO();
		}

		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		if (StringUtils.isBlank(lineQO.getStartingDepart())) {
			lineQO.setStartingProvinceID(null);
			lineQO.setStartingCityID(null);
		}
		if (StringUtils.isBlank(lineQO.getDomesticLine())) {
			lineQO.setCityOfType(null);
			lineQO.setProvinceOfType(null);
		}
		HgLogger.getInstance().info("yuqz", "LineController->queryLineList->lineQO=" + JSON.toJSONString(lineQO));
		pagination.setCondition(lineQO);
		pagination = lineService.queryPagination(pagination);
		HgLogger.getInstance().info("yuqz", "LineController->queryLineList->pagination=" + JSON.toJSONString(pagination));
		List<LineDTO> lineDTOList = (List<LineDTO>) pagination.getList();
		for(LineDTO lineDTO:lineDTOList){
			if(null!=lineDTO.getBaseInfo()){
				DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
				dateSalePriceQO.setLineID(lineDTO.getId());
				String nowDateStr = DateUtil.formatDate(new Date());
				dateSalePriceQO.setSaleDate(nowDateStr);
				Double averPrice = xlDateSalePriceService.countDailyAverPrice(dateSalePriceQO);
				lineDTO.getBaseInfo().setAverPrice(averPrice);
			}	
		 }
	
	//	pagination.setList(lineDTOList);
		
		//查询所有城市信息
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		//查询所有城市信息
		ProvinceQo province = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(province);
		
		//有出发城市查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineQO.getStartingCityID())){
			CityQo startCityQo = new CityQo();
			startCityQo.setProvinceCode(lineQO.getStartingProvinceID());
			List<City> startCityList = cityService.queryList(startCityQo);
			model.addAttribute("startCityList", startCityList);
		}
		
		//有线路类型城市编号查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineQO.getCityOfType())){
			CityQo typeCityQo = new CityQo();
			typeCityQo.setProvinceCode(lineQO.getProvinceOfType());
			List<City> typeCityList = cityService.queryList(typeCityQo);
			model.addAttribute("typeCityList", typeCityList);
		}
		
		
		
		model.addAttribute("statusMap", LineConstants.statusMap); // 线路状态
		model.addAttribute("typeMap", LineConstants.typeMap); // 线路类型
		model.addAttribute("pagination", pagination);
		model.addAttribute("lineQO", lineQO);
		model.addAttribute("cityList", cityList);
		model.addAttribute("provinceList", provinceList);

		return "/traveline/line/line_list.html";
	}

	/**
	 * 查询线路详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param lineId
	 * @return
	 */
	@RequestMapping("/info")
	public String queryLineInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "lineId") String lineId) {
		LineQO qo = new LineQO();
		qo.setId(lineId);
		LineDTO lineDTO = lineService.queryUnique(qo);
		model.addAttribute("lineDTO", lineDTO);
		return "/traveline/line/line_info.html";
	}

	/**
	 * 跳转到新增线路页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toadd")
	public String toAddLine(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		LineSupplierQO lineSupplierQO = new LineSupplierQO();
		lineSupplierQO.setStatus(SupplierConstants.AUDIT_SUCCESS);
		List<LineSupplierDTO> supplierList = new ArrayList<LineSupplierDTO>();
		supplierList = lineSupplierService.queryList(lineSupplierQO);
		// 供应商列表
		model.addAttribute("supplierList", supplierList);
		// 推荐指数
		model.addAttribute("RECOMMAND_MAP",
				LineConstants.recommendationLevelMap);
		// 线路类型
		model.addAttribute("TYPE_MAP", LineConstants.typeMap);
		// 查询所有省
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinceList", provinceList);
		return "/traveline/line/line_add.html";
	}

	/**
	 * @方法功能说明：查询所有的省份
	 * @修改者名字：liusong
	 * @修改时间：2014年12月15日下午3:40:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/searchProvince")
	@ResponseBody
	public String searchProvince(HttpServletRequest request, Model model) {
		// 查询所有省
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinceList", provinceList);
		return JSON.toJSONString(provinceList);
	}

	/**
	 * 查询市
	 * 
	 * @param request
	 * @param model
	 * @param province
	 * @return
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

	/**
	 * 新增线路
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public String addLine(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute CreateLineCommand command) {

		try {

			String message = "";
			String statusCode = "";
			Boolean result = lineService.createLine(command);

			if (result) {
				message = "新增成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "新增失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun",
						"旅游线路管理-旅游线路资源维护-新增线路失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");

		} catch (Exception e) {
			 HgLogger.getInstance().error("luoyun", "旅游线路管理-旅游线路资源维护-新增线路失败" + e.getMessage());
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}

	}

	/**
	 * 修改头部线路行程信息
	 * @方法功能说明：
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月6日下午2:50:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/modifyRouteInfo")
	@ResponseBody
	public String modifyLineRouteInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyLineRouteInfoCommand command) {

		try {

			String message = "";
			String statusCode = "";
			Boolean result = lineService.modifyLineRouteInfo(command);

			if (result) {
				message = "修改成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "修改失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message, "",
					"line");

		} catch (Exception e) {
			 HgLogger.getInstance().error("luoyun", "旅游线路管理-旅游线路资源维护-修改行程信息失败" +
			 e.getMessage());
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "修改失败", "", "line");
		}
	}

	/**
	 * @方法功能说明：线路基本信息详情
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日上午9:42:20
	 * @修改内容：
	 * @参数：id 线路的id号
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String lineDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {
		LineQO qo = new LineQO();
		qo.setId(lineId);
		LineDTO lineDTO = lineService.queryUnique(qo);
		model.addAttribute("lineDTO", lineDTO);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		model.addAttribute("cityList", cityList);
		return "/traveline/line/line_detail.html";
	}

	/**
	 * @方法功能说明：线路基本信息详情时查询出发地以及目的地城市及途经城市
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日下午2:25:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param city[]
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/queryCity")
	@ResponseBody
	public String queryCitys(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "cityCodes", required = false) String cityCodes) {
		// 查询市
		CityQo cityQo = new CityQo();
		City city = new City();
		List<City> cityList = new ArrayList<City>();
		String[] citys = cityCodes.split(",");
		if (citys != null && citys.length > 0) {
			for (int i = 0; i < citys.length; i++) {// 遍历城市id数组依次查询城市名称
				cityQo.setId(citys[i]);
				city = cityService.queryUnique(cityQo);
				cityList.add(city);
			}
		}
		return JSON.toJSONString(cityList);
	}

	/**
	 * @方法功能说明：线路基本信息修改时的信息展示
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日下午4:11:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command 修改command
	 * @参数：@param lineId 要修改的线路的id号
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toedit")
	public String lineEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {

		LineQO qo = new LineQO();// 线路查询qo
		LineDTO lineDTO = null;// 线路查询返回的dto
		LineSupplierQO lineSupplierQO = new LineSupplierQO();// 供应商查询qo
		List<LineSupplierDTO> supplierList = new ArrayList<LineSupplierDTO>();// 供应商查询返回的列表
		ProvinceQo provinceQo = new ProvinceQo();// 省份查询qo
		List<Province> provinceList = new ArrayList<Province>();// 所有的省份列表
		try {
			if (lineId != null && !"".equals(lineId)) {// 展示要修改的线路信息
				qo.setId(lineId);
				lineDTO = lineService.queryUnique(qo);

				lineSupplierQO.setStatus(SupplierConstants.AUDIT_SUCCESS);
				supplierList = lineSupplierService.queryList(lineSupplierQO);
				// 供应商列表
				model.addAttribute("supplierList", supplierList);
				// 推荐指数
				model.addAttribute("RECOMMAND_MAP",
						LineConstants.recommendationLevelMap);
				// 线路类型
				model.addAttribute("TYPE_MAP", LineConstants.typeMap);
				// 查询所有省
				provinceList = provinceService.queryList(provinceQo);
				model.addAttribute("provinceList", provinceList);

				
				if(StringUtils.isNotBlank(lineDTO.getBaseInfo().getStarting())){
					// 查询出发城市所在省份编号
					CityQo startCityQo = new CityQo();
					startCityQo.setId(lineDTO.getBaseInfo().getStarting());
					City startCity = cityService.queryUnique(startCityQo);
					lineDTO.getBaseInfo().setStartProvince(startCity.getParentCode());
					//查询出发城市所在省份的所有城市
					CityQo startCityListQo = new CityQo();
					startCityListQo.setProvinceCode(startCity.getParentCode());
					List<City> startCityList = cityService.queryList(startCityListQo);
					model.addAttribute("startCityList", startCityList);
				}
				
				if(StringUtils.isNotBlank(lineDTO.getBaseInfo().getCityOfType())){
					//查询线路类别中的城市所在省份编号
					CityQo cityOfTypeQo= new CityQo();
					cityOfTypeQo.setId(lineDTO.getBaseInfo().getCityOfType());
					City cityOfType = cityService.queryUnique(cityOfTypeQo);
					lineDTO.getBaseInfo().setProvinceOfType(cityOfType.getParentCode());
					//查询线路类别中的城市所在省份的所有城市
					CityQo cityOfTypeListQo = new CityQo();
					cityOfTypeListQo.setProvinceCode(cityOfType.getParentCode());
					List<City> cityOfTypeList = cityService.queryList(cityOfTypeListQo);
					model.addAttribute("cityOfTypeList", cityOfTypeList);
				}
				
				model.addAttribute("lineDTO", lineDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/traveline/line/line_edit.html";
	}

	/**
	 * @方法功能说明：线路基本信息修改
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日下午5:31:45
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public String toLineEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyLineCommand command) {

		try {
			String message = "";
			String statusCode = "";
			Boolean result = lineService.modifyLine(command);

			if (result) {
				message = "修改成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "修改失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error(
						"liusong",
						"旅游线路管理-旅游线路资源维护-修改线路基本信息失败"
								+ JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "修改线路基本信息失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
	}

	/**
	 * @方法功能说明：展示线路审核页面，操作线路是否审核通过
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日上午10:17:38
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineId 线路ID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAudit")
	public String toAudit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {
		LineQO qo = new LineQO();// 线路查询qo
		if (lineId != null && !"".equals(lineId)) {// 展示要审核的线路信息
			qo.setId(lineId);
			LineDTO lineDTO = lineService.queryUnique(qo);
			// 要审核的线路dto
			model.addAttribute("lineDTO", lineDTO);
			model.addAttribute("STATUS_MAP", LineConstants.AUDIT_STATUS_MAP);
		}

		return "/traveline/line/line_audit.html";
	}

	/**
	 * @方法功能说明：审核旅游线路资源
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日上午11:05:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command 审核command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public String audit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AuditLineCommand command) {
		try {
			boolean result = lineService.auditLine(command);
			String message = "";
			String statusCode = "";
			if (result) {
				message = "审核操作成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "审核操作失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error(
						"liusong",
						"旅游线路管理-旅游线路资源维护管理-审核旅游线路资源操作失败"
								+ JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");

		} catch (Exception e) {
			HgLogger.getInstance().error("liusong",
					"旅游线路管理-旅游线路资源维护管理-审核旅游线路资源操作失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "审核操作失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
	}

	/**
	 * @方法功能说明：旅游线路资源信息线路基本信息是否上架
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午1:57:14
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineId 要上架的线路的id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toGrounding")
	public String toGrounding(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {
		LineQO qo = new LineQO();// 线路查询qo
		if (lineId != null && !"".equals(lineId)) {// 展示要上架的线路信息
			qo.setId(lineId);
			LineDTO lineDTO = lineService.queryUnique(qo);
			// 要上架的线路dto
			model.addAttribute("lineDTO", lineDTO);
		}
		return "/traveline/line/line_grounding.html";
	}

	/**
	 * @方法功能说明：旅游线路资源信息线路基本信息上架
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午2:00:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command 上架
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/grounding")
	@ResponseBody
	public String grounding(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute GroundingLineCommand command) {
		String message = "";
		String statusCode = "";
		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		//只查询有没有団期，没有查当前日期之后的団期。如有需求可加当前时间到查询条件里面
		dateSalePriceQO.setLineID(command.getLineID());
		DateSalePriceDTO dateSalePriceDTO = xlDateSalePriceService.queryUnique(dateSalePriceQO);
		if(null == dateSalePriceDTO){
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			message = "没有団期不允许上架";
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
		try {
			boolean result = lineService.groundingLine(command);
			
			if (result) {
				message = "上架操作成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "上架操作失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error(
						"liusong",
						"旅游线路管理-旅游线路资源维护管理-上架旅游线路资源操作失败"
								+ JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");

		} catch (Exception e) {
			HgLogger.getInstance().error("liusong",
					"旅游线路管理-旅游线路资源维护管理-上架旅游线路资源操作失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "上架操作失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
	}

	/**
	 * @方法功能说明：旅游线路资源信息线路基本信息是否下架
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午2:21:44
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineId 线路id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toUnderCarriage")
	public String toUnderCarriage(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {
		LineQO qo = new LineQO();// 线路查询qo
		if (lineId != null && !"".equals(lineId)) {// 展示要下架的线路信息
			qo.setId(lineId);
			LineDTO lineDTO = lineService.queryUnique(qo);
			// 要下架的线路dto
			model.addAttribute("lineDTO", lineDTO);
			model.addAttribute("STATUS_MAP",
					LineConstants.UNDERCARRIAGE_STATUS_MAP);
		}

		return "/traveline/line/line_underCarriage.html";
	}

	/**
	 * @方法功能说明：旅游线路资源信息线路基本信息下架
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午2:00:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command 上架
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/underCarriage")
	@ResponseBody
	public String underCarriage(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute UnderCarriageLineCommand command) {
		try {
			boolean result = lineService.underCarriageLine(command);
			String message = "";
			String statusCode = "";
			if (result) {
				message = "下架操作成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "下架操作失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error(
						"liusong",
						"旅游线路管理-旅游线路资源维护管理-下架旅游线路资源操作失败"
								+ JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");

		} catch (Exception e) {
			HgLogger.getInstance().error("liusong",
					"旅游线路管理-旅游线路资源维护管理-上架旅游线路资源操作失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "下架操作失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
	}

	/**
	 * @方法功能说明：线路基本信息复制
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午3:27:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineId 被复制的线路信息的id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toCopy")
	public String toCopy(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineId) {
		LineQO qo = new LineQO();// 线路查询qo
		if (lineId != null && !"".equals(lineId)) {
			qo.setId(lineId);
			//LineDTO lineDTO = lineService.queryUnique(qo);
			// 被复制的线路信息dto
			model.addAttribute("lineID", lineId);
			// 推荐指数
			model.addAttribute("RECOMMAND_MAP",
					LineConstants.recommendationLevelMap);
			// 线路类型
			model.addAttribute("TYPE_MAP", LineConstants.typeMap);

			LineSupplierQO lineSupplierQO = new LineSupplierQO();// 供应商查询qo
			List<LineSupplierDTO> supplierList = new ArrayList<LineSupplierDTO>();// 供应商查询返回的列表
			lineSupplierQO.setStatus(SupplierConstants.AUDIT_SUCCESS);
			supplierList = lineSupplierService.queryList(lineSupplierQO);
			// 供应商列表
			model.addAttribute("supplierList", supplierList);
		}

		return "/traveline/line/line_copy.html";
	}

	/**
	 * 
	 * @方法功能说明：线路的复制
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月7日下午4:54:30
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/copy")
	@ResponseBody
	public String copy(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute CopyLineCommand command) {
		try {
			String message = "";
			String statusCode = "";
			Boolean result = lineService.copyLine(command);
			;

			if (result) {
				message = "复制成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "复制失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("liusong",
						"旅游线路管理-旅游线路资源维护-线路复制失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");

		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "线路复制失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
		}
	}
	@RequestMapping(value = "/uniqueName")
	@ResponseBody
	public String uniqueName(HttpServletRequest request, Model model,
			@RequestParam(value = "name", required = false) String name){
		LineQO qo = new LineQO();
		qo.setLineName(name);
		qo.setNameLike(false);
		LineDTO line = lineService.queryUnique(qo);
		String message = "";
		if(line != null){
			message = "线路名称重复";
		}
		return JSON.toJSONString(message);
	}
	@RequestMapping(value = "/uniqueNumber")
	@ResponseBody
	public String uniqueNumber(HttpServletRequest request, Model model,
			@RequestParam(value = "number", required = false) String number){
		LineQO qo = new LineQO();
		qo.setNumber(number);
		LineDTO line = lineService.queryUnique(qo);
		String message = "";
		if(line != null){
			message = "线路编号重复";
		}
		return JSON.toJSONString(message);
	}
}