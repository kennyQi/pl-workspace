package slfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.ArrayList;
import java.util.Arrays;
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
import slfx.xl.pojo.command.salepolicy.CancelSalePolicyCommand;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import slfx.xl.pojo.command.salepolicy.IssueSalePolicyCommand;
import slfx.xl.pojo.dto.LineDealerDTO;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.log.SalePolicyWorkLogDTO;
import slfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;
import slfx.xl.pojo.qo.LineDealerQO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.pojo.qo.SalePolicyLineQO;
import slfx.xl.pojo.qo.SalePolicyWorkLogQO;
import slfx.xl.pojo.system.DealerConstants;
import slfx.xl.pojo.system.LineConstants;
import slfx.xl.pojo.system.SalePolicyConstants;
import slfx.xl.spi.inter.LineDealerService;
import slfx.xl.spi.inter.LineService;
import slfx.xl.spi.inter.SalePolicyLineService;
import slfx.xl.spi.inter.SalePolicyWorkLogService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：价格政策控制层
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午9:59:05
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/traveline/salepolicy")
public class SalePolicyController  extends  BaseController{
	
	@Autowired
	private SalePolicyLineService   salePolicyService;
	@Autowired
	private LineDealerService		lineDealerService;
	@Autowired
	private SalePolicyWorkLogService   salePolicyWorkLogService;
	@Autowired
	private  LineService   lineService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	
	/**
	 * @方法功能说明：
	 * @修改者名字：liusong
	 * @修改时间：2014年12月18日上午10:24:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyQO   价格政策查询QO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr  使用时间开始
	 * @参数：@param endTimeStr     使用时间结束
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/list")
	public String querySalePolicyList(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute SalePolicyLineQO salePolicyLineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			){
		//分页组件
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null?new Integer(1):pageNo);
		pagination.setPageSize(pageSize == null?new Integer(20):pageSize);
		
		if(salePolicyLineQO == null){
			salePolicyLineQO  = new SalePolicyLineQO();
		}
		
		//设置使用时间开始和使用时间结束
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			salePolicyLineQO.setStartDate(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			salePolicyLineQO.setEndDate(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		pagination.setCondition(salePolicyLineQO);
		pagination = salePolicyService.queryPagination(pagination);
		
		//查询经销商列表
		LineDealerQO  lineDealerQO = new LineDealerQO();
		lineDealerQO.setStatus(DealerConstants.USE);
		List<LineDealerDTO>  lineDealerList  = new ArrayList<LineDealerDTO>();
		lineDealerList = lineDealerService.queryList(lineDealerQO);
		
		//查询所有城市信息
		ProvinceQo province = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(province);
		
		//有线路类型城市编号查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(salePolicyLineQO.getCityOfType())){
			CityQo typeCityQo = new CityQo();
			typeCityQo.setProvinceCode(salePolicyLineQO.getProvinceOfType());
			List<City> typeCityList = cityService.queryList(typeCityQo);
			model.addAttribute("typeCityList", typeCityList);
		}
				
		model.addAttribute("policyStatusMap", SalePolicyConstants.policyStatusMap); //线路状态
		model.addAttribute("typeMap", LineConstants.typeMap); //线路类型
		model.addAttribute("pagination", pagination);
		model.addAttribute("salePolicyLineQO", salePolicyLineQO);
		model.addAttribute("lineDealerList", lineDealerList);
		model.addAttribute("provinceList", provinceList);
		
		return "/traveline/salepolicy/salepolicy_list.html";
	}
	
	/**
	 * @方法功能说明：价格政策详情
	 * @修改者名字：liusong
	 * @修改时间：2014年12月19日下午5:57:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyId  价格政策的id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String salePolicyDetail(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String salePolicyId) {
	
		SalePolicyLineQO qo = new SalePolicyLineQO();
		qo.setId(salePolicyId);
		SalePolicyLineDTO   salePolicyLineDTO = salePolicyService.querySalePolicyDetail(qo);
		
		//查询对应的价格政策的相关操作日志
		List<SalePolicyWorkLogDTO>  salePolicyWorkLogList  = new ArrayList<SalePolicyWorkLogDTO>();
		SalePolicyWorkLogQO  salePolicyWorkLogQo = new SalePolicyWorkLogQO();
		salePolicyWorkLogQo.setSalePolicyId(salePolicyId);
		salePolicyWorkLogList = salePolicyWorkLogService.queryList(salePolicyWorkLogQo);
		
		model.addAttribute("salePolicyLineDTO", salePolicyLineDTO);
		model.addAttribute("salePolicyWorkLogList", salePolicyWorkLogList);
		model.addAttribute("salePolicyPriceTypeMap", SalePolicyConstants.salePolicyPriceTypeMap);
		
		//返回view
		return "/traveline/salepolicy/salepolicy_detail.html";
	}
	
	/**
	 * @方法功能说明：新增价格政策返回view
	 * @修改者名字：liusong
	 * @修改时间：2014年12月22日上午11:00:29
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toadd")
	public   String  toSalePolicy(HttpServletRequest request,HttpServletResponse response, Model model){
		
		
		//查询经销商列表
		LineDealerQO  lineDealerQO = new LineDealerQO();
		List<LineDealerDTO>  lineDealerList  = new ArrayList<LineDealerDTO>();
		lineDealerList = lineDealerService.queryList(lineDealerQO);
		
		//线路
		model.addAttribute("salePolicyLineTypeMap", SalePolicyConstants.salePolicyLineTypeMap);
		model.addAttribute("TYPE_BY_LINENAME", SalePolicyConstants.TYPE_BY_LINENAME);
		model.addAttribute("typeMap", LineConstants.typeMap); //线路类型
		
		//价格政策
		model.addAttribute("salePolicyPriceTypeMap", SalePolicyConstants.salePolicyPriceTypeMap);
		//经销商
		model.addAttribute("lineDealerList", lineDealerList);
		//返回view
		return "/traveline/salepolicy/salepolicy_add.html";
	}
	
	/**
	 * @方法功能说明：新增价格政策验证价格政策是否已存在
	 * @修改者名字：liusong
	 * @修改时间：2014年12月22日上午11:04:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/validateSalePolicyAdd")
	@ResponseBody
	public  String   addSalePolicy(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute CreateSalePolicyCommand  command,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr){
		
			String result = "0";
			//时间格式转换
			if (null != beginTimeStr && !"".equals(beginTimeStr)) {
				command.setStartDate(DateUtil.dateStr2BeginDate(beginTimeStr));
			}
			
			if (null != beginTimeStr && !"".equals(beginTimeStr)) {
				command.setEndDate(DateUtil.dateStr2EndDate(endTimeStr));
			}
			
			
		 	LineQO lineQO = new LineQO();
		 	String  ids = request.getParameter("ids");//获取需要验证的线路id字符串
		 	
		   if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_LINENAME || StringUtils.isNotBlank(ids)){
			   List<String> lineIDList = new ArrayList<String>();
			   String[] arr = null;//new一个新的字符数组
			   if(ids != null && !"".equals(ids)){
					 if(ids.length() > 0){//截去线路id字符串前面的，
						 ids = ids.substring(1, ids.length());
				     }
					 arr = ids.split(",");
					 lineIDList = Arrays.asList(arr);
			   }
			   //3表示没有筛选到线路信息
			   if(lineIDList.size() == 0){
				   return "3";
			   }
			   lineQO.setIds(lineIDList);
			   System.out.println("lineIDList======="+lineIDList.size());
		   }else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_LINETYPE){
			   lineQO.setType(command.getLineType());
			   lineQO.setCityOfType(command.getCityOfLineType());
		   }else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_PRICE){
			   lineQO.setAdultPriceMax(command.getAdultPriceMax());
			   lineQO.setAdultPriceMin(command.getAdultPriceMin());
			   lineQO.setBeginDate(command.getStartDate());
			   lineQO.setEndDate(command.getEndDate());
		   }else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_START){
			   lineQO.setStartingCityID(command.getStartingCityID());
		   }else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_ALL){
			   //选择全部则不设置线路查询条件
		   }else if(command.getSelectLineType() == null){
			   return "4"; //4表示选择线路
		   }
		   
		   List<LineDTO> lineList = new ArrayList<LineDTO>();
		   lineList = lineService.queryList(lineQO);
		   System.out.println("lineList.size========================"+lineList.size());
		   if(lineList.size() != 0){
			   for(LineDTO line:lineList){
				   SalePolicyLineQO salePolicyQO = new SalePolicyLineQO();
				   salePolicyQO.setLineID(line.getId());
				   SalePolicyLineDTO  salePolicy = salePolicyService.queryUnique(salePolicyQO);
				   System.out.println("salePolicy========================"+salePolicy);
					if(salePolicy != null){//如果获得的线路id有对应的价格政策，则弹出提示框，提示其是否忽略继续操作
						System.out.println("salePolicy=222======================="+salePolicy);
						 result = "1";
						 System.out.println("result===============" +result);
					}
			   }
		   }else{
			   //3表示没有筛选到线路信息
			   result = "3";
		   }
		   System.out.println("returnResult ========================="+result);
		   return  JSON.toJSONString(result);
			
	}
	
	
	
	/**
	 * @方法功能说明：新增价格政策时点击忽略并继续操作时保存价格政策
	 * @修改者名字：liusong
	 * @修改时间：2014年12月25日下午5:27:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param user
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/addSalePolicy")
	@ResponseBody
	public  String   addSalePolicyLine(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute CreateSalePolicyCommand  command,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr){
		
		String  ids = request.getParameter("ids");
		if(ids != null && !"".equals(ids)){
			 if(ids.length() > 0){//截去线路id字符串前面的，
				 ids = ids.substring(1, ids.length());
		     }
			 
			String[] arr;//new一个新的字符数组
			arr = ids.split(",");//将线路id字符串转换成数组
			List<String> lineIDs = Arrays.asList(arr);
			command.setLineIds(lineIDs);
		}
		
		//时间格式转换
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			command.setStartDate(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			command.setEndDate(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		try{

			String message = "";
			String statusCode = "";

			AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			if(user == null){
				message = "请先登录";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			}else{
				command.setCreateName(user.getLoginName());
				//创建价格政策
				SalePolicyLineDTO salePolicyDTO = salePolicyService.create(command);
				if (salePolicyDTO != null) {
					message = "新增成功";
					statusCode = DwzJsonResultUtil.STATUS_CODE_200;
				} else {
					message = "新增失败";
					statusCode = DwzJsonResultUtil.STATUS_CODE_300;
					HgLogger.getInstance().error("liusong",
							"旅游线路管理-价格管理-新增价格政策失败" + JSON.toJSONString(command));
				}
			}
			
		
		   return DwzJsonResultUtil.createJsonString(statusCode, message,
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		}catch(Exception e){
			HgLogger.getInstance().error("liusong","旅游线路管理-价格管理-新增价格政策失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		}
	}
	
	
	/**
	 * @方法功能说明：//根据线路名称查询线路信息
	 * @修改者名字：liusong
	 * @修改时间：2014年12月22日下午5:13:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param lineName  线路名称
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/searchLine")
	@ResponseBody
	public  LineDTO   queryLine(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute (value = "lineName") String lineName ){
		
		//根据线路名称查询线路信息
		LineQO  lineQo   =   new  LineQO();
		LineDTO  lineDto = null;
		if(lineName != null && !"".equals(lineName)){
			lineQo.setLineName(lineName);
			lineDto  =  lineService.queryUnique(lineQo);
		}
		return lineDto;
	}
	
	/**
	 * @方法功能说明：当新增价格政策页面中请选择线路的值为”按线路名称“时
	 *  则跳转一个新窗口进行查找带回操作
	 * @修改者名字：liusong
	 * @修改时间：2014年12月22日下午5:50:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toLine")
	public  String  toLine(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute LineQO lineQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize){
		
		//查询所有的线路列表
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(10) : pageSize);

		if (lineQO == null) {
			lineQO = new LineQO();
		}

		pagination.setCondition(lineQO);
		pagination = lineService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("lineQO", lineQO);
		//返回view
	    return "/traveline/salepolicy/salepolicy_line.html";
		
	}
	
	
	/**
	 * @方法功能说明：政策发布跳转页面
	 * @修改者名字：liusong
	 * @修改时间：2014年12月30日下午2:44:11
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toRelease")
	public String toRelease(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String salePolicyId) {
		SalePolicyLineQO   qo = new SalePolicyLineQO();
		if (salePolicyId != null && !"".equals(salePolicyId)) {// 展示要上架的线路信息
			qo.setId(salePolicyId);
			SalePolicyLineDTO salePolicyDTO = salePolicyService.queryUnique(qo);
			model.addAttribute("salePolicyDTO", salePolicyDTO);
		}

		return "/traveline/salepolicy/salepolicy_release.html";
	}
	
	/**
	 * @方法功能说明：价格政策发布
	 * @修改者名字：liusong
	 * @修改时间：2014年12月29日下午4:14:25
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/release")
	@ResponseBody
	public  String  release(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute  IssueSalePolicyCommand    command){
		 
		   try{

				String message = "";
				String statusCode = "";
				Boolean  result = false;

				//发布价格政策
				AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
				if(user == null){
					message = "请先登录";
					statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				}else{
					command.setWorkerName(user.getLoginName());
					result = salePolicyService.isssue(command);
					if (result) {
						message = " 价格政策发布成功";
						statusCode = DwzJsonResultUtil.STATUS_CODE_200;
					} else {
						message = "价格政策发布失败";
						statusCode = DwzJsonResultUtil.STATUS_CODE_300;
						HgLogger.getInstance().error("liusong",
								"旅游线路管理-价格管理-价格政策发布失败" + JSON.toJSONString(command));
					}
				}
				
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		   }catch(Exception e){
			   HgLogger.getInstance().error("liusong","旅游线路管理-价格管理-价格政策发布失败" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_300, "发布失败",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		   }
	}
	
	/**
	 * @方法功能说明：取消价格政策跳转页面
	 * @修改者名字：liusong
	 * @修改时间：2014年12月30日下午2:45:14
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toCancel")
	public String toCancel(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String salePolicyId) {
		SalePolicyLineQO   qo = new SalePolicyLineQO();
		if (salePolicyId != null && !"".equals(salePolicyId)) {// 展示要上架的线路信息
			qo.setId(salePolicyId);
			SalePolicyLineDTO salePolicyDTO = salePolicyService.queryUnique(qo);
			model.addAttribute("salePolicyDTO", salePolicyDTO);
		}

		return "/traveline/salepolicy/salepolicy_cancel.html";
	}
	
	/**
	 * @方法功能说明：取消价格政策
	 * @修改者名字：liusong
	 * @修改时间：2014年12月29日下午4:26:44
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public  String  cancel(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute CancelSalePolicyCommand command){
		
		   try{

				String message = "";
				String statusCode = "";
				Boolean  result = false;

				//取消价格政策
				AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
				if(user == null){
					message = "请先登录";
					statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				}else{
					command.setWorkerName(user.getLoginName());
					result = salePolicyService.cancel(command);
					if (result) {
						message = " 价格政策取消成功";
						statusCode = DwzJsonResultUtil.STATUS_CODE_200;
					} else {
						message = "价格政策取消失败";
						statusCode = DwzJsonResultUtil.STATUS_CODE_300;
						HgLogger.getInstance().error("liusong",
								"旅游线路管理-价格管理-价格政策取消失败" + JSON.toJSONString(command));
					}
				}
				
				
				
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		   }catch(Exception e){
			   HgLogger.getInstance().error("liusong","旅游线路管理-价格管理-价格政策取消失败" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_300, "取消失败",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "salepolicy");
		   }
	}
	
	/**
	 * 
	 * @方法功能说明：政策取消原因
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月9日下午3:05:09
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param salePolicyLineQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/cancleRemark")
	public String showCancleRemark(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute SalePolicyLineQO salePolicyLineQO){
		
		SalePolicyLineDTO salePolicyDTO = salePolicyService.queryUnique(salePolicyLineQO);
		model.addAttribute("salePolicyDTO", salePolicyDTO);
		return "/traveline/salepolicy/salepolicy_cancel_detail.html";
		
	}
	
	/**
	 * 
	 * @方法功能说明：定时依据价格政策使用时间开始价格政策或者下架价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日上午10:25:55
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping("/timer")
	public void timer(HttpServletRequest request,HttpServletResponse response){
		
		//定时开始价格政策
		salePolicyService.startSalePolicy();
		//定时下架价格政策
		salePolicyService.downSalePolicy();
		
	}
	public static void main(String[] args) {
		Integer i=1;
		Integer o = 1;
		System.out.println(i==o);
	}
}
