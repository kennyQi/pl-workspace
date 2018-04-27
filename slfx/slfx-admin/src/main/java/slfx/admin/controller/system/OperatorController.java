package slfx.admin.controller.system;


import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.model.staff.StaffBaseInfo;
import hg.system.qo.StaffQo;
import hg.system.service.SecurityService;
import hg.system.service.StaffService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;

/**
 * 操作员管理
 * @author zhurz
 */
@Controller
@RequestMapping(value="/system/operator")
public class OperatorController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(OperatorController.class);
	@Autowired
	private HgLogger hgLogger;
	@Resource
	private StaffService staffService;
	
	@Resource
	private SecurityService securityService;

	public final static String DEFAULT_PASSWORD = "123456";
	
	public final static String PAGE_PATH_LIST = "/system/operList.html";
	public final static String PAGE_PATH_EDIT = "/system/operEdit.html";
	public final static String PAGE_PATH_ADD = "/system/operAdd.html";
	public final static String PAGE_PATH_SET_SELF_PWD = "/system/setSelfPwd.html";
	
	
	/**
	 * 操作员列表
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute StaffQo staffQo){
		hgLogger.info("wuyg", "查询操作员列表");
		Pagination pagination = new Pagination();
		
		pagination.setCondition(staffQo);
		
		pagination = staffService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("staffQo", staffQo);
		model.addAttribute("userEnableList", SecurityConstants.USER_ENABLE_LIST);
		
		return PAGE_PATH_LIST;
	}

	/**
	 * 跳转到操作员添加的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model){
		hgLogger.info("wuyg", "跳转到操作员添加的页面");
		model.addAttribute("authRoleList", securityService.findAllRoles());
		model.addAttribute("userEnableList", SecurityConstants.USER_ENABLE_LIST);
		model.addAttribute("operator", new Staff());
		model.addAttribute("editFlag", false);
		
		return PAGE_PATH_ADD;
	}
	
	/**
	 * 跳转到修改密码的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/toSetSelfPwd")
	public String setSelfPwd(HttpServletRequest request, 
			HttpServletResponse response,
			Model model,
			@RequestParam(value="id")String id) {

		StaffQo qo = new StaffQo();
		qo.setId(id);
		Staff staff = staffService.queryUnique(qo);
		
		model.addAttribute("staff", staff);
		
		return PAGE_PATH_SET_SELF_PWD;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/self/setPwd")
	public String selfSetPwd(@RequestParam(value="userId")String userId,
							@RequestParam(value="oldPwd")String oldPwd,
							@RequestParam(value="newPwdOnce")String newPwdOnce) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改密码成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		try {
			securityService.updateUserPassword(userId, oldPwd, newPwdOnce);
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "operator");
	}

	/**
	 * 跳转到操作员编辑的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response,
					   Model model, @RequestParam(value="id", required=false)String id){
		hgLogger.info("wuyg", "跳转到操作员编辑的页面");
		StaffQo qo = new StaffQo();
		qo.setId(id);
		Staff staff = staffService.queryUnique(qo);
		//查询用户管理角色的ROLE_NAME
		List<String> hasRoleList = securityService.findUserRoles(staff.getAuthUser().getLoginName());
		
		model.addAttribute("authRoleList", securityService.findAllRoles());
		model.addAttribute("staff", staff);
		model.addAttribute("hasRoleName", hasRoleList);
		
		return PAGE_PATH_EDIT;
	}

	/**
	 * 保存新增的操作员
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String save(@RequestParam(value="realName", required=false)String realName,
					   @RequestParam(value="roleIds", required=false)String roleIds,
					   @RequestParam(value="loginName", required=false)String loginName,
					   @RequestParam(value="mobile", required=false)String mobile,
					   @RequestParam(value="tel", required=false)String tel,
					   @RequestParam(value="email", required=false)String email){
		hgLogger.info("wuyg", "保存新增的操作员");
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		//判断登录名是否唯一
		boolean exist = staffService.isExistLoginName(loginName);
		if(exist){
			//登录名已存在
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			message = "保存失败，登录名已经存在";
			hgLogger.info("wuyg", "保存新增的操作员:保存失败，登录名已经存在");
			return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "");
		}
		
		try {
			String[] role_id = null;
			if(roleIds!=null){
				role_id = roleIds.split(",");
			}
			
			Staff staff = new Staff();
			StaffBaseInfo info = new StaffBaseInfo();
			AuthUser authUser = new AuthUser();
			
			info.setEmail(email);
			info.setMobile(mobile);
			info.setRealName(realName);
			info.setTel(tel);
			
			authUser.setPasswd(MD5HashUtil.toMD5(DEFAULT_PASSWORD));
			authUser.setEnable(SecurityConstants.USER_ENABLE);
			authUser.setLoginName(loginName);
			authUser.setId(UUIDGenerator.getUUID());
			
			staff.setInfo(info);
			staff.setAuthUser(authUser);
			staff.setId(authUser.getId());
			
			staffService.savaOperAndRole(staff, role_id);
			
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "保存失败";
		}
		hgLogger.info("wuyg", "保存新增的操作员"+message);
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "operator");
	}

	/**
	 * 更新操作员
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model,
						 @ModelAttribute Staff staff,
						 @RequestParam(value="roleIds", required=false)String roleIds){
		hgLogger.info("wuyg", "更新操作员");
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功!";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			
			String[] role_id = null;
			if(roleIds!=null){
				role_id = roleIds.split(",");
			}
			
			staffService.updateOperAndRole(staff, role_id);
			
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "修改失败!";
		}
		hgLogger.info("wuyg", "更新操作员"+message);
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "operator");
	}
	
	
	/**
	 * 变更操作员可用状态
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifyEnable")
	public String modifyEnable(HttpServletRequest request, HttpServletResponse response, Model model,
							   @RequestParam(value="enable", required=false)Short enable,
							   @RequestParam(value="id", required=false)String id){
		hgLogger.info("wuyg", "变更操作员可用状态");
		try {
			AuthUser authUser = securityService.findUserById(id);
			authUser.setEnable(enable);
			securityService.updateUser(authUser);
			hgLogger.info("wuyg", "变更操作员可用状态：成功");
			return DwzJsonResultUtil.createJsonString("200", "变更可用状态成功!", null, "operator");
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error("wuyg", "变更操作员可用状态：失败");
			return DwzJsonResultUtil.createJsonString("300", "变更可用状态失败!", null, "operator");
		}
	}

	
	/**
	 * 重置操作员密码
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/resetPwd")
	public String resetPwd(HttpServletRequest request, HttpServletResponse response, Model model,
						   @RequestParam(value="ids", required=false)String ids) {
		hgLogger.info("wuyg", "重置操作员密码");
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "重置密码成功（初始密码：" + DEFAULT_PASSWORD + "）";
		
		if (ids != null) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				AuthUser authUser = securityService.findUserById(id);
				if (authUser != null) {
					authUser.setPasswd(MD5HashUtil.toMD5(DEFAULT_PASSWORD));
					securityService.updateUser(authUser);
				}
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message, null, "operator");
	}
	
	
	@ResponseBody
	@RequestMapping("/del")
	public String del(HttpServletRequest request, HttpServletResponse response, Model model,
					  @RequestParam(value="id", required=false)String id){
		hgLogger.info("wuyg", "删除操作员");
		try {
			staffService.deleteById(id);
			securityService.deleteUserById(id);
			hgLogger.info("wuyg", "删除操作员：成功");
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "operator");
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error("wuyg", "删除操作员：失败");
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null, "");
		}
	}
	
	
}
