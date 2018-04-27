package slfx.admin.controller.auth;

import hg.common.model.DwzTreeNode;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.DwzTreeUtil;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hg.system.service.SecurityService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;

@Controller
public class RoleController extends BaseController {
	
	@Resource
	private SecurityService securityService;
	
	public static class RoleControllerDto extends DwzPaginQo {
		private String id;
		private AuthRole role;
		private List<String> permIds;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public AuthRole getRole() {
			return role;
		}
		public void setRole(AuthRole role) {
			this.role = role;
		}
		public List<String> getPermIds() {
			return permIds;
		}
		public void setPermIds(List<String> permIds) {
			this.permIds = permIds;
		}
	}

	/**
	 * 角色列表
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/auth/role/list")
	public String list(HttpServletRequest request, Model model, 
			@ModelAttribute RoleControllerDto dto){
		
		Pagination paging=dto.getPagination();
		paging=securityService.findRolePagination(paging);
		
		model.addAttribute("pagination", paging);
		
		return "/auth/roleList.html";
	}
	
	@RequestMapping(value="/auth/role/add")
	public String add(HttpServletRequest request, Model model, 
			@ModelAttribute RoleControllerDto dto){
		
		List<AuthPerm> permList=securityService.findAllPerms();
		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
		if(permList!=null){
			for(AuthPerm perm:permList){
				if(!SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())){
					DwzTreeNode dtn=new DwzTreeNode();
					dtn.setId(perm.getId());
					if(perm.getParentId()!=null && perm.getParentId().length()>0){
						dtn.setParentId(perm.getParentId());
					}
					dtn.setDisplayName(perm.getDisplayName());
					dtn.setTvalue(perm.getId().toString());
					dtn.setTname("permIds");
					dtnlist.add(dtn);
				}
			}
		}
		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder treeCheck expand");
		model.addAttribute("role", new AuthRole());
		model.addAttribute("permTreeHtml", permTreeHtml);
		model.addAttribute("hasPermList", new ArrayList<AuthPerm>());
		
		return "/auth/roleEdit.html";
	}

	@RequestMapping(value="/auth/role/edit")
	public String edit(HttpServletRequest request, Model model, 
			@ModelAttribute RoleControllerDto dto){

		List<AuthPerm> permList=securityService.findAllPerms();
		List<AuthPerm> hasPermList=securityService.findPermsByRoleId(dto.getId());

		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
		if(permList!=null){
			for(AuthPerm perm:permList){
				if(!SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())){
					DwzTreeNode dtn=new DwzTreeNode();
					dtn.setId(perm.getId());
					if(perm.getParentId()!=null && perm.getParentId().length()>0){
						dtn.setParentId(perm.getParentId());
					}
					for(AuthPerm hasPerm:hasPermList){
						if(hasPerm.getId().equals(perm.getId())){
							dtn.setChecked(true);
							break;
						}
					}
					dtn.setDisplayName(perm.getDisplayName());
					dtn.setTvalue(perm.getId().toString());
					dtn.setTname("permIds");
					dtnlist.add(dtn);
				}
			}
		}
		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder treeCheck expand");
		
		AuthRole role=securityService.findRoleById(dto.getId());
		model.addAttribute("role", role);
		model.addAttribute("permTreeHtml", permTreeHtml);
		model.addAttribute("hasPermList", hasPermList);
		
		return "/auth/roleEdit.html";
	}

	@ResponseBody
	@RequestMapping(value="/auth/role/del")
	public String del(HttpServletRequest request, Model model, 
			@ModelAttribute RoleControllerDto dto){
		if(dto.getId()!=null){
			securityService.deleteRoleById(dto.getId());
		}
		return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
	}

	@ResponseBody
	@RequestMapping(value="/auth/role/save")
	public String save(HttpServletRequest request, Model model, 
			@ModelAttribute RoleControllerDto dto){
		
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="保存成功";
		String callbackType=DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId="authRole";
		
		AuthRole role=dto.getRole();
		if(role!=null){
			AuthRole r=securityService.findRoleById(role.getId());
			boolean isExists=r==null?false:true;
			r=isExists?r:new AuthRole();
			
			r.setDisplayName(role.getDisplayName());
			r.setRoleName(role.getRoleName());
			try {
				if(isExists){
					securityService.updateRole2(r, dto.getPermIds());
				}else{
					securityService.insertRole2(r, dto.getPermIds());
				}
			} catch (Exception e) {
				statusCode=DwzJsonResultUtil.STATUS_CODE_500;
				message="保存失败";
			}
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, navTabId);
		
	}
	
}
