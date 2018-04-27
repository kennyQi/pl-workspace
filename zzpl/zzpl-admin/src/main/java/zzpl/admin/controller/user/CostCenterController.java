package zzpl.admin.controller.user;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

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

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.CostCenterService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.CostCenter;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddCostCenterCommand;
import zzpl.pojo.command.user.DeleteCostCenterCommand;
import zzpl.pojo.command.user.ModifyCostCenterCommand;
import zzpl.pojo.dto.user.CostCenterDTO;
import zzpl.pojo.dto.user.status.CostCenterStatus;
import zzpl.pojo.exception.user.CostCenterException;
import zzpl.pojo.qo.user.CostCenterQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/costCenter")
public class CostCenterController extends BaseController{
	
	@Autowired
	private CostCenterService costCenterService;
	
	@Autowired
	private UserService userService;
	/**
	 * 
	 * @方法功能说明：跳转到列表页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param companyQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute CostCenterQO costCenterQO) {
		return "/content/user/cost_center_list.html";
	}

	/**
	 * 
	 * @方法功能说明：列表展示
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param companyQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param companyName
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/costcenterlist")
	public Pagination costCenterList(HttpServletRequest request, Model model,CostCenterQO costCenterQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		//获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		costCenterQO.setCompanyID(user.getCompanyID());
		//正常状态
		costCenterQO.setStatus(CostCenterStatus.NORMAL);
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(costCenterQO);
		if(StringUtils.isNotBlank(user.getCompanyID())){
			pagination = costCenterService.queryPagination(pagination);
			pagination.setList(BeanMapperUtils.getMapper().mapAsList(pagination.getList(),CostCenterDTO.class));
		}
		return pagination;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @方法功能说明：跳转到添加页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		return "/content/user/cost_center_add.html";
	}
	
	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/costcenteradd")
	public String costCenterAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddCostCenterCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【CostCenterController】【costCenterAdd】,command："+JSON.toJSONString(command));
			//获取当前登陆用户，以用于查找用户所属公司
			AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			User user=userService.get(authUser.getId());
			command.setCompanyName(user.getCompanyName());
			command.setCompanyID(user.getCompanyID());
			costCenterService.addCostCenter(command);
			return DwzJsonResultUtil.createJsonString("200", "添加成功", null, SysProperties.getInstance().get("costCenterMenuID"));
		} catch (CostCenterException e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}
	
	/**
	 * 
	 * @方法功能说明：删除
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:47
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String deleteCostCenter(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteCostCenterCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【CostCenterController】【deleteCostCenter】,command："+JSON.toJSONString(command));
			costCenterService.deleteCostCenter(command);
			return DwzJsonResultUtil
					.createJsonString("200", "删除成功!", null,  SysProperties.getInstance().get("costCenterMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", "删除失败", null, "");
		}
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到编辑页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:11:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param companyID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "costCenterID") String costCenterID) {
		CostCenter costCenter = costCenterService.get(costCenterID);
		model.addAttribute("costCenter", costCenter);
		return "/content/user/cost_center_edit.html";
	}
	/**
	 * 
	 * @方法功能说明：编辑
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:11:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/costcenteredit")
	public String costCenterEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyCostCenterCommand command) {
		HgLogger.getInstance().error("cs",	"【CostCenterController】【costCenterEdit】,command："+JSON.toJSONString(command));
		costCenterService.modfiyCostCenter(command);
		return DwzJsonResultUtil.createJsonString("200", "修改成功", null, SysProperties.getInstance().get("costCenterMenuID"));
	}
}
