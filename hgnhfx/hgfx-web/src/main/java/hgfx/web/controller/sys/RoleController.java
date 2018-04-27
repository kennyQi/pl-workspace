//package hgfx.web.controller.sys;
//
//import java.util.*;
//
//import hg.demo.member.common.domain.model.AuthPerm;
//import hg.demo.member.common.domain.model.AuthRole;
//import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
//import hg.demo.member.common.domain.model.system.DwzTreeNode;
//import hg.demo.member.common.domain.model.system.DwzTreeUtil;
//import hg.demo.member.common.domain.model.system.SecurityConstants;
//import hg.demo.member.common.spi.AuthPermSPI;
//import hg.demo.member.common.spi.AuthRoleSPI;
//import hg.demo.member.common.spi.command.authRole.CreateAuthRoleCommand;
//import hg.demo.member.common.spi.command.authRole.DeleteAuthRoleCommand;
//import hg.demo.member.common.spi.command.authRole.ModifyAuthRoleCommand;
//import hg.demo.member.common.spi.qo.AuthPermSQO;
//import hg.demo.member.common.spi.qo.AuthRoleSQO;
//import hgfx.web.common.UserInfo;
//import hg.framework.common.model.LimitQuery;
//import hg.framework.common.model.Pagination;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/auth/role")
//public class RoleController extends BaseController {
//	@Resource
//	private AuthRoleSPI authRoleSPIService;
//	@Resource
//	private AuthPermSPI authPermSPIService;
//	/**
//	 * 角色列表
//	 * @param request
//	 * @param model
//	 * @param dto
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value="/list")
//	public String list(HttpServletRequest request, Model model,
//			@RequestParam(value="numPerPage", required = false) Integer pageSize,
//			@RequestParam(value="pageNum", required = false) Integer pageNo,
//			@ModelAttribute AuthRoleSQO sqo){
//
//		if (pageSize == null) {
//			pageSize = 20;
//		}
//		if (pageNo == null) {
//			pageNo = 1;
//		}
//		
//		sqo.setLimit(new LimitQuery());
//		sqo.getLimit().setPageNo(pageNo);
//		sqo.getLimit().setPageSize(pageSize);
//		Pagination paging = authRoleSPIService.queryAuthPermPagination(sqo);
//		paging.setPageNo(pageNo);
//		paging.setPageSize(pageSize);
//		model.addAttribute("pagination", paging);
//		model.addAttribute("roleName", sqo.getRoleName());
//		
//		return "/auth/roleList.ftl";
//	}
//	
//	/**
//	 * @Title: toCreate 
//	 * @author guok
//	 * @Description: 跳转添加
//	 * @Time 2016年5月27日上午11:49:00
//	 * @param request
//	 * @param model
//	 * @return String 设定文件
//	 * @throws
//	 */
//	@RequestMapping("/toCreate")
//	public String toCreate(HttpServletRequest request, Model model) {
//		List<AuthPerm> perms = authPermSPIService.queryAuthPerms(new AuthPermSQO());
//		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
//		if(perms!=null){
//			for(AuthPerm perm:perms){
//
//					DwzTreeNode dtn=new DwzTreeNode();
//					dtn.setId(perm.getId());
//					if(perm.getParentId()!=null && perm.getParentId().length()>0){
//						dtn.setParentId(perm.getParentId());
//					}
//					dtn.setDisplayName(perm.getDisplayName());
//					dtn.setTvalue(perm.getId());
//					dtn.setTname("permIds");
//					dtnlist.add(dtn);
//			}
//		}
//		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder treeCheck expand");
//		model.addAttribute("permTreeHtml", permTreeHtml);
//		model.addAttribute("hasPermList", new ArrayList<AuthPerm>());
//		model.addAttribute("perms", perms);
//		
//		return "/auth/roleAdd.ftl";
//	}
//	
//	/**
//	 * @Title: saveRole 
//	 * @author guok
//	 * @Description: 添加角色
//	 * @Time 2016年5月27日上午11:49:12
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @param command
//	 * @return String 设定文件
//	 * @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/saveRole")
//	public String saveRole(HttpServletRequest request,HttpSession httpSession,HttpServletResponse response, Model model, @ModelAttribute CreateAuthRoleCommand command){
//		
//		UserInfo userInfo= getUserInfo(httpSession);
//		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
//		String message="添加成功";
//		
//		List<AuthRole> lists = new ArrayList<AuthRole>();
//		lists = authRoleSPIService.queryAuthRoles(new AuthRoleSQO());
//		for (AuthRole authRole : lists) {
//			if (StringUtils.equals(authRole.getRoleName(), command.getRoleName())) {
//				statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//				message="角色名称已存在";
//				return DwzJsonResultUtil.createJsonString(statusCode, message, null, null);
//			}
//			if (StringUtils.equals(authRole.getDisplayName(), command.getDisplayName())) {
//				statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//				message="角色显示名已存在";
//				return DwzJsonResultUtil.createJsonString(statusCode, message, null, null);
//			}
//		}
//		
//		try {
//			Set<AuthPerm> authPerms = new LinkedHashSet<AuthPerm>();
//			if(command.getPermIds() != null){
//				for (String permID : command.getPermIds()) {
//					AuthPermSQO sqo = new AuthPermSQO();
//					sqo.setId(permID);
//					AuthPerm perm = authPermSPIService.queryAuthPerm(sqo);
//					authPerms.add(perm);
//				}
//			}
//			AuthRole authRole = authRoleSPIService.createAuthRole(command,authPerms);
//			logUtil.addLog(null, userInfo.getLoginName(), LogConstants.ROLE_ADD, authRole.getDisplayName()+": "+authRole.getId());
//			
//		} catch (Exception e) {
//			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//			message="添加失败";
//			e.printStackTrace();
//		}
//		
//		return DwzJsonResultUtil.createJsonString(statusCode, message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "authRole");
//	}
//	
//	/**
//	 * @Title: toEdit 
//	 * @author guok
//	 * @Description: 跳转修改
//	 * @Time 2016年5月27日上午11:49:24
//	 * @param request
//	 * @param model
//	 * @return String 设定文件
//	 * @throws
//	 */
//	@RequestMapping("/toEdit")
//	public String toEdit(HttpServletRequest request, Model model, @RequestParam(value="id", required = false) String id) {
//		AuthRoleSQO sqo = new AuthRoleSQO();
//		sqo.setId(id);
//		AuthRole role = authRoleSPIService.queryAuthRole(sqo);
//		if (role != null) {
//			model.addAttribute("role", role);
//		}
//		List<AuthPerm> perms = authPermSPIService.queryAuthPerms(new AuthPermSQO());
//		model.addAttribute("perms", perms);
//		//
//		List<AuthPerm> list=new ArrayList<AuthPerm>();
//		if(perms!=null){
//			for(AuthPerm perm:perms){
//				if((SecurityConstants.PERM_TYPE_AUTH==perm.getPermType())){
//					list.add(perm);
//				}
//			}
//		}
//		model.addAttribute("permList", list);
//		
//		AuthPermSQO authPermSQO = new AuthPermSQO();
//		authPermSQO.setRoleId(id);
//		List<AuthPerm> hasPermList= authPermSPIService.queryAuthPerms(authPermSQO);
//		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
//		if(perms!=null){
//			for(AuthPerm perm:perms){
//					DwzTreeNode dtn=new DwzTreeNode();
//					dtn.setId(perm.getId());
//					if(perm.getParentId()!=null && perm.getParentId().length()>0){
//						dtn.setParentId(perm.getParentId());
//					}
//					for(AuthPerm hasPerm:hasPermList){
//						if(hasPerm.getId().equals(perm.getId())){
//							dtn.setChecked(true);
//							break;
//						}
//					}
//					dtn.setDisplayName(perm.getDisplayName());
//					dtn.setTvalue(perm.getId());
//					dtn.setTname("permIds");
//					dtnlist.add(dtn);
//			}
//		}
//		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder treeCheck expand");
//		model.addAttribute("permTreeHtml", permTreeHtml);
//		model.addAttribute("hasPermList", hasPermList);
//		
//		return "/auth/roleEdit.ftl";
//	}
//	
//	/**
//	 * @Title: editRole 
//	 * @author guok
//	 * @Description: 修改角色
//	 * @Time 2016年5月27日上午11:49:35
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @param command
//	 * @return String 设定文件
//	 * @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/editRole")
//	public String editRole(HttpServletRequest request,HttpSession httpSession,HttpServletResponse response, Model model, @ModelAttribute ModifyAuthRoleCommand command){
//		UserInfo userInfo= getUserInfo(httpSession);
//		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
//		String message="修改成功";
//		List<AuthRole> lists = new ArrayList<AuthRole>();
//		lists = authRoleSPIService.queryAuthRoles(new AuthRoleSQO());
//		for (AuthRole authRole : lists) {
//			if (StringUtils.equals(authRole.getRoleName(), command.getRoleName())) {
//				statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//				message="角色名称已存在";
//				return DwzJsonResultUtil.createJsonString(statusCode, message, null, null);
//			}
//			if (StringUtils.equals(authRole.getDisplayName(), command.getDisplayName())) {
//				statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//				message="角色显示名已存在";
//				return DwzJsonResultUtil.createJsonString(statusCode, message, null, null);
//			}
//		}
//		try {
//			Set<AuthPerm> authPerms = new LinkedHashSet<AuthPerm>();
//			if(command.getPermIds() != null){
//				for (String permID : command.getPermIds()) {
//					AuthPermSQO sqo = new AuthPermSQO();
//					sqo.setId(permID);
//					AuthPerm perm = authPermSPIService.queryAuthPerm(sqo);
//					authPerms.add(perm);
//				}
//			}
//			AuthRole authRole = authRoleSPIService.modifyAuthRole(command,authPerms);
//			logUtil.addLog(null, userInfo.getLoginName(), LogConstants.ROLE_EDIT, authRole.getDisplayName()+": "+authRole.getId());
//		} catch (Exception e) {
//			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//			message="修改失败";
//			e.printStackTrace();
//		}
//		
//		return DwzJsonResultUtil.createJsonString(statusCode, message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "authRole");
//	}
//	
//	/**
//	 * @Title: deleteRole 
//	 * @author guok
//	 * @Description: 删除角色
//	 * @Time 2016年5月27日上午11:49:44
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @param command
//	 * @return String 设定文件
//	 * @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/deleteRole")
//	public String deleteRole(HttpServletRequest request,HttpSession httpSession, HttpServletResponse response, Model model, @ModelAttribute DeleteAuthRoleCommand command){
//		UserInfo userInfo= getUserInfo(httpSession);
//		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
//		String message="删除成功";
//		AuthRole authRole = new AuthRole();
//		AuthRoleSQO sqo = new AuthRoleSQO();
//		sqo.setId(command.getId());
//		authRole = authRoleSPIService.queryAuthRole(sqo);
//		if (authRole.getAuthUserSet().size() > 0) {
//			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//			message="角色下面还有操作员，请先删除操作员！";
//			return DwzJsonResultUtil.createJsonString(statusCode, message, null, null);
//		}
//		
//		try {
//			authRoleSPIService.deleteAuthRole(command);
//			logUtil.addLog(null, userInfo.getLoginName(), LogConstants.ROLR_DEL, command.getId());
//		} catch (Exception e) {
//			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
//			message="删除失败";
//			e.printStackTrace();
//		}
//		
//		return DwzJsonResultUtil.createJsonString(statusCode, message, null, "authRole");
//	}
//	
//}
