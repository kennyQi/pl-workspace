package slfx.admin.controller.viewspot;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
import slfx.mp.command.admin.CreatePlatformPolicyCommand;
import slfx.mp.command.admin.ModifyPlatformPolicyCommand;
import slfx.mp.command.admin.StopPlatformPolicyCommand;
import slfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
import slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import slfx.mp.qo.PlatformPolicyQO;
import slfx.mp.spi.inter.AdminSalePolicyService;

/**
 * 景点门票-价格管理
 * @author lixuanxuan
 *
 */
@Controller
@RequestMapping(value = "/viewspot/price")
public class PriceController extends BaseController{
	
	
	@Autowired
	private AdminSalePolicyService adminSalePolicyService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CityService cityService;
	@Resource
	private AreaService areaService;
	@Autowired
	private HgLogger hgLogger;
	/**
	 * 查询价格政策列表
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String queryPlatformPolicyList(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@ModelAttribute PlatformPolicyQO qo,
			@RequestParam(value = "pageNum",required = false) Integer pageNo,
			@RequestParam(value = "numPerPage",required = false) Integer pageSize){
		hgLogger.info("wuyg", "admin查询价格政策列表");
		try{
			
			if(qo == null){
				qo = new PlatformPolicyQO();
			}
			
			//对qo中生效时间和结束时间的默认日期进行处理 更改为null
			String defaultDateStr = "1000-01-01";
			String formatStr = "yyyy-MM-dd";
			String beginDateStr = DateUtil.formatDateTime(qo.getBeginDate(),formatStr);
			String endDateStr = DateUtil.formatDateTime(qo.getEndDate(),formatStr);
			if(beginDateStr.equals(defaultDateStr)){
				qo.setBeginDate(null);
			}
			if(endDateStr.equals(defaultDateStr)){
				qo.setEndDate(null);
			}
			
			Pagination pagination = new Pagination();
			pageNo = pageNo == null ? new Integer(1):pageNo;
			pageSize = pageSize == null ? new Integer(20):pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(qo);
			
			
			pagination = adminSalePolicyService.queryPagination(pagination);
			
			
			//查询所有省
			ProvinceQo provinceQo=new ProvinceQo();
			List<Province> provinceList=provinceService.queryList(provinceQo);
			//查询所有市
			CityQo cityQo=new CityQo();
			List<City> cityList=cityService.queryList(cityQo);
			//查询所有区
			AreaQo areaQo=new AreaQo();
			List<Area> areaList=areaService.queryList(areaQo);
			
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			model.addAttribute("areaList", areaList);
			model.addAttribute("pagination", pagination);
			model.addAttribute("qo", qo);
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return "/viewspot/price/platformPolicy_list.html";
	}
	
	
	/**
	 * 新增价格政策
	 * @param request
	 * @param response
	 * @param mode
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createPlatformPolicy")
	public String createPlatformPolicy(HttpServletRequest request,
			HttpServletResponse response,Model mode,
			@ModelAttribute CreatePlatformPolicyCommand command){
		hgLogger.info("wuyg", "admin新增价格政策");
		String message = "新增价格政策成功";
		
		try{
			
			adminSalePolicyService.createPlatformPolicy(command);
			
		}catch(Exception e){
			e.printStackTrace();
			hgLogger.error("wuyg", "admin新增价格政策失败");
		}
		hgLogger.info("wuyg", "admin新增价格政策成功");
		return DwzJsonResultUtil.createJsonString("200", message, "closeCurrent", "pm_price");
		
	}
	
	
	/**
	 * 新增价格政策对话框
	 * @return
	 */
	@RequestMapping("/toCreatePlatformPolicy")
	public String toCreatePlatformPolicy(){
		
		return "/viewspot/price/platformPolicy_add.html";
	}
	
	
	
	
	/**
	 * 取消价格政策
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/stopPlatformPolicy")
	@ResponseBody
	public String stopPlatformPolicy(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@ModelAttribute StopPlatformPolicyCommand command){
		hgLogger.info("wuyg", "admin取消价格政策");
		String message = "取消价格政策成功";
		
		try{
			
			if(command == null){
				message = "取消价格政策失败，政策编号不能为空";
				return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			if(StringUtils.isBlank(command.getPolicyId())){
				message = "取消价格政策失败，政策编号不能为空";
				return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			if(!StringUtils.isNumeric(command.getPolicyId())){
				message = "取消价格政策失败，政策编号只能为数字";
				return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			//============= service ========
			
			
		}catch(Exception e){
			e.printStackTrace();
			hgLogger.error("wuyg", "admin取消价格政策失败");
		}
		hgLogger.info("wuyg", "admin取消价格政策成功");
	    return DwzJsonResultUtil.createJsonString("200", message, "closeCurrent", "pm_price");
		
		
		
	}
	
	/**
	 * 取消价格政策对话框
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping("/toStopPlatformPolicy")
	public String toStopPlatformPolicy(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@ModelAttribute StopPlatformPolicyCommand command){
		
		String message = "";
		
		if(command == null){
			message = "政策编号不能为空";
			return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
		}
		
		if(StringUtils.isBlank(command.getPolicyId())){
			message = "政策编号不能为空";
			return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
		}
		
		if(!StringUtils.isNumeric(command.getPolicyId())){
			message = "政策编号只能为数字";
			return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
		}
		
		model.addAttribute("policyId", command.getPolicyId());
		
		return "/viewspot/price/platformPolicy_cancle.html";
	}
	
	
	/**
	 * 查询价格政策详情
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping(value = "/queryPlatformPolicyDetail")
	public String queryPlatformPolicyDetail(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@ModelAttribute PlatformPolicyQO qo){
		hgLogger.info("wuyg", "admin查询价格政策详情");
		try{
			
			if(qo == null){
				return "error";
			}
			
			if(StringUtils.isBlank(qo.getPolicyId())){
				return "error";
			}
			
			
			if(!StringUtils.isNumeric(qo.getPolicyId())){
				return "error";
			}
			
			
			SalePolicySnapshotDTO dto = new SalePolicySnapshotDTO();
			//============= service ========
			
			model.addAttribute("salePolicySnapshotDTO",dto);
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return "/viewspot/price/platformPolicy_detail.html";
		
	}
	
	
	/**
	 * 查询取消价格政策原因
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping(value = "/queryPlatformPolicyStopInfo")
	public String queryPlatformPolicyStopInfo(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@ModelAttribute PlatformPolicyQO qo){
		hgLogger.info("wuyg", "admin查询取消价格政策原因");
		
		try{
			
			if(qo == null){
				return "error";
			}
			
			if(StringUtils.isBlank(qo.getPolicyId())){
				return "error";
			}
			
			if(!StringUtils.isNumeric(qo.getPolicyId())){
				return "error";
			}
			
			
			SalePolicySnapshotDTO salePolicySnapshot = new SalePolicySnapshotDTO();
			//============= service ========
			
			
			model.addAttribute("salePolicySnapshot", salePolicySnapshot);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/viewspot/price/platformPolicy_cancleInfo.html";
	}
	
	
	
	
	
	/**
	 * 发布价格政策
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping(value = "/releasePlatformPolicy")
	@ResponseBody
	public String releasePlatformPolicy(HttpServletRequest request, 
			HttpServletResponse response,Model model,
			@RequestParam(value = "flag",required = false) String flag,
			@ModelAttribute ModifyPlatformPolicyCommand command){
		hgLogger.info("wuyg", "admin发布价格政策");
		String message = "发布价格政策成功";
		
		try{
			
			if(command == null){
			    message = "价格政策编号不能为空";
			    return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			if(StringUtils.isBlank(command.getPolicyId())){
				message = "价格政策编号不能为空";
				return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			if(!StringUtils.isNumeric(command.getPolicyId())){
				message = "政策编号只能为数字";
				return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "pm_price");
			}
			
			
			//============= service ========
			
			
			if(flag.equals("dialog")){
				return DwzJsonResultUtil.createJsonString("200", message, "closeCurrent", "pm_price");
			}
			
			if(flag.equals("table")){
				return DwzJsonResultUtil.createJsonString("200", message, "", "pm_price");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "";
	}
	

	/**
	 * 弹出景点添加确认框
	 * @return
	 */
	@RequestMapping("/confirmScenicSpots")
	public String confirmScenicSpots(){
		
		return "/viewspot/price/platformPolicy_confirmScenicSpots.html";
		
	}
	
	
	/**
	 * 查询景区列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getscenicSpotList")
	@ResponseBody
	public String getscenicSpotList(HttpServletRequest request, 
			@RequestParam(value = "pageNum",required = false) Integer pageNo,
			@RequestParam(value = "numPerPage",required = false) Integer pageSize,
			@ModelAttribute ScenicSpotDTO qo,
			HttpServletResponse response,Model model){
		hgLogger.info("wuyg", "admin查询景区列表");
		
		try{
			
			pageNo = pageNo == null? new Integer(1):pageNo;
			pageSize = pageSize == null ? new Integer(8):pageSize;
			
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			
			pagination.setCondition(qo);

			
			ArrayList<ScenicSpotDTO> scenicSpotList = new ArrayList<ScenicSpotDTO>();
			//============= service ========
			
			String result = "";
			
			String headHtml = "<div class='unit'><table><tbody>";
			String footHtml = "</tbody></table></div>";
			String body = "<tr>";
			String endTr = "</tr>";
			String startTr = "<tr>";
			
			if(scenicSpotList.size() <= 4){
				
				for(int i = 0;i <= 3;i++){
					ScenicSpotDTO dto = scenicSpotList.get(i);
					body = body + getHtml(dto);
				}
				body = body + endTr;
				
			}else{
				
				for(int k = 0;k <= 3 ;k++){
					ScenicSpotDTO dto = scenicSpotList.get(k);
					body = body + getHtml(dto);
				}
				body = body + endTr + startTr;
				
				for(int j = 4;j < scenicSpotList.size(); j++){
					ScenicSpotDTO dto = scenicSpotList.get(j);
					body = body + getHtml(dto);
				}
				
				body = body + endTr;
			}
			
			result = headHtml + body + footHtml;
			
			
			return result;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	
	
	
	/**
	 * 依据景点信息生成td片段
	 * @param scenicSpot
	 * @return
	 */
	private String getHtml(ScenicSpotDTO dto){
		String html = "<td id='spNameTd'><input id='"+dto.getId()+"' type='checkbox' name='c1'  value='" +
				dto.getScenicSpotBaseInfo().getName() + "' />"
				+ dto.getScenicSpotBaseInfo().getName() + "</td>";
		return html;
	}
	
	
	
	
	
	
	
	
	
}
