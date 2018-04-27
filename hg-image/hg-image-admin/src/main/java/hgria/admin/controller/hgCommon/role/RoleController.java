package hgria.admin.controller.hgCommon.role;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.command.role.CreateAuthRoleCommand;
import hg.system.command.role.ModifyAuthRoleCommand;
import hg.system.command.role.RemoveAuthRoleCommand;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.EntityConvertUtils;
import hg.system.dto.auth.AuthRoleDTO;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.qo.AuthRoleQo;
import hg.system.service.AuthRoleService;
import hgria.admin.controller.BaseController;

import java.util.Iterator;
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
 * 
 * @类功能说明：角色控制器
 * @类修改者：zzb
 * @修改日期：2014年10月15日下午5:04:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月15日下午5:04:18
 *
 */
@Controller("hgRoleController")
@RequestMapping(value="hg/auth/role")
public class RoleController extends BaseController {
	
	/**
	 * 角色service
	 */
	@Resource
	private AuthRoleService authRoleService;
	
	/**
	 * 角色页面
	 */
	public final static String PAGE_VIEW 	= "/content/role/role.html";
	
	/**
	 * 角色添加页面
	 */
	public final static String PAGE_ADD 	= "/content/role/roleAdd.html";
	
	/**
	 * 角色编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/role/roleEdit.html";
	
	/**
	 * 
	 * @方法功能说明：角色主页面
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
	 * @方法功能说明：角色列表查询
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日下午3:34:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param roleQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/rolelist")
	public Pagination permlist(HttpServletRequest request, @ModelAttribute AuthRoleQo roleQo) {
		
		HgLogger.getInstance().info("zzb", "进入角色列表查询方法:permQo【" + JSON.toJSONString(roleQo) + "】");
		
		roleQo.setRoleNameLike(true);
		roleQo.setDisplayNameLike(true);
		Pagination pagination = roleQo.getPagination();
		pagination.setCondition(roleQo);
		pagination = authRoleService.queryPagination(pagination);

		pagination.setList(EntityConvertUtils.convertEntityToDtoList
				((List<AuthRole>) pagination.getList(), AuthRoleDTO.class));

		pagination.setCondition(SecurityConstants.PERM_TYPE_LIST);
		return pagination;
	}


	/**
	 * @方法功能说明：角色添加
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日下午3:41:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param roleQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthRoleQo roleQo) {
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：角色新增保存方法
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
			@ModelAttribute CreateAuthRoleCommand command) {

		HgLogger.getInstance().info("zzb", "进入角色新增保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authRoleService.createAuthRole(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "角色新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authRole");
	}
	
	
	/**
	 * @方法功能说明：角色编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月4日上午11:33:29
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param roleQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthRoleQo roleQo) {
		
		HgLogger.getInstance().info("zzb", "进入角色编辑跳转方法:roleQo【" + JSON.toJSONString(roleQo, true) + "】");
		
		// 1. 检查
		if (roleQo == null || StringUtils.isBlank(roleQo.getId())) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑角色异常！");
		}
		
		AuthRole role = authRoleService.queryUnique(roleQo);
		
		if (role == null)
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑角色异常！");
		
		try {
			StringBuffer permIds = new StringBuffer("");
			List<AuthPerm> permList = authRoleService.queryAuthPermByAuthRole(roleQo);
			for (Iterator<AuthPerm> iterator = permList.iterator(); iterator.hasNext();) {
				AuthPerm authPerm = (AuthPerm) iterator.next();
				permIds.append(authPerm.getId() + ",");
			}
			model.addAttribute("permIds", permIds);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "角色编辑跳转失败:roleQo【" + JSON.toJSONString(roleQo, true) + "】");
			e.printStackTrace();
		}
		model.addAttribute("role", EntityConvertUtils
				.convertEntityToDto(role, AuthRoleDTO.class));
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：角色编辑保存
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
			@ModelAttribute ModifyAuthRoleCommand command) {

		HgLogger.getInstance().info("zzb", "进入角色编辑保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authRoleService.modifyAuthPerm(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "角色编辑保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authRole");
	}
	
	
	/**
	 * @方法功能说明：删除角色
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
			@ModelAttribute RemoveAuthRoleCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入角色删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			authRoleService.removeAuthRole(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "authRole");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "角色删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
}
