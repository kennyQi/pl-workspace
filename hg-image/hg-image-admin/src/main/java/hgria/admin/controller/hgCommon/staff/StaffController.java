package hgria.admin.controller.hgCommon.staff;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.command.staff.CreateAuthStaffCommand;
import hg.system.command.staff.ModifyAuthStaffCommand;
import hg.system.command.staff.RemoveAuthStaffCommand;
import hg.system.command.staff.ResetAuthStaffPwdCommand;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.EntityConvertUtils;
import hg.system.dto.auth.AuthRoleDTO;
import hg.system.dto.auth.AuthStaffDTO;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthRole;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthRoleQo;
import hg.system.qo.AuthStaffQo;
import hg.system.service.AuthRoleService;
import hg.system.service.AuthStaffService;
import hgria.admin.controller.BaseController;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：员工_控制器
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午9:45:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午9:45:39
 */
@Controller("hgStaffController")
@RequestMapping(value="hg/auth/staff")
public class StaffController extends BaseController {

	/**
	 * 员工service
	 */
	@Resource
	private AuthStaffService authStaffService;
	
	/**
	 * 角色service
	 */
	@Resource
	private AuthRoleService authRoleService;
	
	/**
	 * 员工页面
	 */
	public final static String PAGE_VIEW 	= "/content/staff/staff.html";
	
	/**
	 * 员工添加页面
	 */
	public final static String PAGE_ADD 	= "/content/staff/staffAdd.html";
	
	/**
	 * 员工编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/staff/staffEdit.html";
	
	
	/**
	 * 
	 * @方法功能说明：员工主页面
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日下午3:22:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		return PAGE_VIEW;
	}
	
	
	/**
	 * @方法功能说明：员工列表查询
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日下午3:34:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param staffQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/stafflist")
	public Pagination permlist(HttpServletRequest request, @ModelAttribute AuthStaffQo staffQo) {
		
		HgLogger.getInstance().info("zzb", "进入员工列表查询方法:staffQo【" + JSON.toJSONString(staffQo) + "】");
		
		staffQo.setIsLoginNameLike(true);
		staffQo.setIsRealNameLike(true);
		Pagination pagination = staffQo.getPagination();
		pagination.setCondition(staffQo);
		pagination = authStaffService.queryPagination(pagination);

		pagination.setList(EntityConvertUtils.convertEntityToDtoList
				((List<Staff>) pagination.getList(), AuthStaffDTO.class));

		pagination.setCondition(SecurityConstants.USER_ENABLE_LIST);
		return pagination;
	}


	/**
	 * @方法功能说明：员工添加
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日下午3:41:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param staffQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthStaffQo staffQo) {
		
		List<AuthRole> authRoleList = authRoleService.queryList(new AuthRoleQo());
		
		model.addAttribute("roleList", EntityConvertUtils
				.convertEntityToDtoList(authRoleList, AuthRoleDTO.class));
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：员工新增保存方法
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日上午11:04:26
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
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute CreateAuthStaffCommand command) {

		HgLogger.getInstance().info("zzb", "进入员工新增保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authStaffService.createAuthStaff(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "员工新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authStaff");
	}
	
	
	/**
	 * @方法功能说明：员工编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日上午11:33:29
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param staffQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthStaffQo staffQo) {
		
		HgLogger.getInstance().info("zzb", "进入员工编辑跳转方法:staffQo【" + JSON.toJSONString(staffQo, true) + "】");
		
		// 1. 检查
		if (staffQo == null || StringUtils.isBlank(staffQo.getId())) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑员工异常！");
		}
		
		Staff staff = authStaffService.queryUnique(staffQo);
		
		if (staff == null)
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑员工异常！");
		
		// 2. 查询出全部角色和已有角色
		List<AuthRole> authRoleList = authRoleService.queryList(new AuthRoleQo());
		model.addAttribute("roleList", EntityConvertUtils
				.convertEntityToDtoList(authRoleList, AuthRoleDTO.class));
		
		try {
			List<AuthRole> exitsRoleList = authStaffService.queryAuthRoleByStaff(staffQo);
			model.addAttribute("exitsRoleList", EntityConvertUtils
					.convertEntityToDtoList(exitsRoleList, AuthRoleDTO.class));
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "员工编辑跳转失败:staffQo【" + JSON.toJSONString(staffQo, true) + "】");
			e.printStackTrace();
		}
		
		model.addAttribute("staff", EntityConvertUtils
				.convertEntityToDto(staff, AuthStaffDTO.class));
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：员工编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日下午2:17:50
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
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute ModifyAuthStaffCommand command) {

		HgLogger.getInstance().info("zzb", "进入员工编辑保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authStaffService.modifyAuthPerm(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "员工编辑保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authStaff");
	}
	
	
	/**
	 * @方法功能说明：删除员工
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日上午9:21:40
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
	@RequestMapping("/del")
	public String del(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute RemoveAuthStaffCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入员工删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			authStaffService.removeAuthStaff(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "authStaff");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "员工删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
	
	/**
	 * @方法功能说明：重置员工密码
	 * @修改者名字：zzb
	 * @修改时间：2014年11月5日下午3:49:40
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
	@RequestMapping("/resetPwd")
	public String resetPwd(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute ResetAuthStaffPwdCommand command) {

		HgLogger.getInstance().info("zzb", "进入重置员工密码方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			authStaffService.resetAuthStaffPwd(command);
			return DwzJsonResultUtil.createJsonString("200", "重置密码成功，<br />默认密码：123456", null, "authStaff");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "重置员工密码删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
	
}
