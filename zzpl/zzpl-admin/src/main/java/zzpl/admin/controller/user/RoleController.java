package zzpl.admin.controller.user;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

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

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.MenuService;
import zzpl.app.service.local.user.RoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Menu;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddRoleCommand;
import zzpl.pojo.command.user.DeleteRoleCommand;
import zzpl.pojo.command.user.ModifyRoleCommand;
import zzpl.pojo.dto.user.RoleDTO;
import zzpl.pojo.qo.user.MenuQO;
import zzpl.pojo.qo.user.RoleQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

	/**
	 * 角色service
	 */
	@Autowired
	private RoleService roleService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	/**
	 * 菜单service
	 */
	@Autowired
	private MenuService menuService;
	
	/**
	 * @Title: view 
	 * @author guok
	 * @时间  2015年6月26日 08:51:49
	 * @Description: 跳转部门列表页
	 * @param request
	 * @param model
	 * @param roleQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute RoleQO roleQO) {
		return "/content/user/role_list.html";
	}
	
	/**
	 * @Title: companyList 
	 * @author guok
	 * @Description: 列表展示
	 * @time 2015年6月26日 08:51:44
	 * @param request
	 * @param model
	 * @param roleQO
	 * @param pageNo
	 * @param pageSize
	 * @param roleName
	 * @return Pagination 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/roleList")
	public Pagination roleList(HttpServletRequest request, Model model,
			RoleQO roleQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "roleName", required = false) String roleName) {
		
		HgLogger.getInstance().error("cs",	"【roleController】【roleList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		roleQO.setRoleName(roleName);
		//获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		roleQO.setCompanyID(user.getCompanyID());
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(roleQO);
		if(StringUtils.isNotBlank(user.getCompanyID())){
			pagination = roleService.queryPagination(pagination);
			pagination.setList(EntityConvertUtils.convertEntityToDtoList(
					(List<RoleDTO>) pagination.getList(), RoleDTO.class));
		}
		return pagination;
	}
	
	/**
	 * 
	 * @Title: deleterole 
	 * @author guok
	 * @Description: 删除
	 * @time 2015年6月26日 08:51:38
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String deleterole(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteRoleCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【roleController】【deleterole】,command："+JSON.toJSONString(command));
			roleService.deleteRole(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("roleMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", "删除失败", null, "");
		}
	}
	
	/**
	 * @throws Exception 
	 * @Title: add 
	 * @author guok
	 * @Description: 跳转添加页
	 * @time 2015年6月26日 08:52:03
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception {
		MenuQO menuQO = new MenuQO();
		menuQO.setAuthority(2);
		List<Menu> menuList=menuService.queryList(menuQO);
		model.addAttribute("menuList", menuList);
		
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		
		return "/content/user/role_add.html";
	}
	
	/**
	 * @Title: roleadd 
	 * @author guok
	 * @Description: 添加
	 * @time 2015年6月26日 08:51:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 * @throws roleException String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/roleadd")
	public String roleAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddRoleCommand command){
			try {
				//获取当前登陆用户，以用于查找用户所属公司
				AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
				command.setUserID(user.getId());
				HgLogger.getInstance().error("cs",	"【roleController】【roleAdd】,command："+JSON.toJSONString(command));
				roleService.addRole(command);
				return DwzJsonResultUtil.createJsonString("200", "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("roleMenuID"));
			} catch (Exception e) {
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到编辑页
	 * @time 2015年6月26日 08:52:10
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "roleID") String roleID) {
		Role role=roleService.getById(roleID);
		MenuQO menuQO = new MenuQO();
		menuQO.setAuthority(2);
		List<Menu> menuList=menuService.queryList(menuQO);
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("role", role);
		return "/content/user/role_edit.html";
	}
	
	/**
	 * @Title: roleEdit 
	 * @author guok
	 * @Description: 编辑
	 * @Time 2015年6月26日 08:52:15
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/roleedit")
	public String roleEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyRoleCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【roleController】【roleEdit】,command："+JSON.toJSONString(command));
			roleService.modfiyRole(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,SysProperties.getInstance().get("roleMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
		
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到详情页
	 * @time 2015年6月29日 08:52:10
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "roleID") String roleID) {
		Role role=roleService.getById(roleID);
		model.addAttribute("role", role);
		return "/content/user/role_detail.html";
	}
	
}
