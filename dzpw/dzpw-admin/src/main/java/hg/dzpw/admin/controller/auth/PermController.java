package hg.dzpw.admin.controller.auth;

import hg.common.model.DwzTreeNode;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.DwzTreeUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hg.system.service.SecurityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/auth/perm")
public class PermController extends BaseController {

	public static final String navTabId = "authPerm";
	public static final String rel = "jbsxBoxAuthPerm";
	
	@Autowired
	private SecurityService securityService;
	
	public static class PermControllerDto extends DwzPaginQo {
		private String id;
		private List<String> ids;
		private Short permType;
		private AuthPerm perm;
		private String url;
		private String permId;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<String> getIds() {
			return ids;
		}
		public void setIds(List<String> ids) {
			this.ids = ids;
		}
		public Short getPermType() {
			return permType;
		}
		public void setPermType(Short permType) {
			this.permType = permType;
		}
		public AuthPerm getPerm() {
			return perm;
		}
		public void setPerm(AuthPerm perm) {
			this.perm = perm;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPermId() {
			return permId;
		}
		public void setPermId(String permId) {
			this.permId = permId;
		}
	}

	
	/**
	 * 权限资源管理
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model, 
			@ModelAttribute PermControllerDto dto){
		
		List<AuthPerm> permAllList=securityService.findAllPerms();
		
		List<DwzTreeNode> dtnList=new ArrayList<DwzTreeNode>();
		for(AuthPerm perm:permAllList){
			if(!SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())){
				DwzTreeNode dtn=new DwzTreeNode();
				dtn.setDisplayName(perm.getDisplayName());
				dtn.setTarget("ajax");
				dtn.setRel(rel);
				dtn.setHref(request.getContextPath()+"/auth/perm/list?permId="+perm.getId());
				dtn.setId(perm.getId());
				if(perm.getParentId()!=null && perm.getParentId().length()>0){
					dtn.setParentId(perm.getParentId());
				}
				dtnList.add(dtn);
			}
		}
		
		String authPermTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnList, null);
		
		model.addAttribute("authPermTreeHtml", authPermTreeHtml);
		
		list(request, model, dto);
		
		return "/auth/permIndex.html";
	}

	/**
	 * 权限资源列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model, 
			@ModelAttribute PermControllerDto dto) {
		
		Map<String, Object> conditionMap=new HashMap<String, Object>();
		conditionMap.put("permType", dto.getPermType());
		conditionMap.put("parentId", dto.getPermId());
		if(StringUtils.isNotBlank(dto.getUrl())) {
			conditionMap.put("urlLike", dto.getUrl().trim());
		}
		
		Pagination paging=dto.getPagination();
		paging.setCondition(conditionMap);
		
		paging=this.securityService.findPermPagination(paging);
		
		model.addAttribute("pagination", paging);
		model.addAttribute("dto", dto);
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
		
		return "/auth/permList.html";
	}
	
	/**
	 * 删除权限资源
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public String del(HttpServletRequest request, HttpServletResponse response, Model model, 
			@ModelAttribute PermControllerDto dto){
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="删除成功";
		try {
			if(dto.getId()!=null){
				securityService.deletePermById(dto.getId());
			}else if(dto.getIds()!=null && dto.getIds().size()>0){
				securityService.deletePermByIds(dto.getIds());
			}
		} catch (Exception e) {
			e.printStackTrace();
			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
			message="删除失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, null, navTabId, null, null, rel);
	}

	/**
	 * 跳转到添加权限资源的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model, 
			@ModelAttribute PermControllerDto dto){
		
		AuthPerm perm=new AuthPerm();
		perm.setPermType(SecurityConstants.PERM_TYPE_AUTH);
		perm.setParentId(dto.getPermId());
		
		model.addAttribute("perm", perm);
		model.addAttribute("roleList", securityService.findAllRoles());
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);

		List<AuthPerm> list=securityService.findAllPerms();
		List<AuthPerm> permList=new ArrayList<AuthPerm>();
		for(AuthPerm p:list){
			if(!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())){
				permList.add(p);
			}
		}
		model.addAttribute("parentPermList", permList);
		
		return "/auth/permEdit.html";
	}

	/**
	 * 跳转到编辑权限资源的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model, 
			@ModelAttribute PermControllerDto dto){
		
		AuthPerm perm=securityService.findPermById(dto.getId());
		if(perm!=null){
			dto.setPerm(perm);
			model.addAttribute("roleList", securityService.findAllRoles());
			model.addAttribute("perm", perm);
			model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);

			List<AuthPerm> list=securityService.findAllPerms();
			List<AuthPerm> permList=new ArrayList<AuthPerm>();
			for(AuthPerm p:list){
				if(!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())
						&& !p.getId().equals(perm.getId())){
					permList.add(p);
				}
			}
			model.addAttribute("parentPermList", permList);
		}else{
			return add(request, response, model, dto);
		}
		
		return "/auth/permEdit.html";
	}

	/**
	 * 保存权限资源
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model, 
			@ModelAttribute PermControllerDto dto){
		
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="保存成功";
		String callbackType=DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		AuthPerm perm=dto.getPerm();
		if(perm!=null){
			//错误检查
			if(SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType()) && StringUtils.isBlank(perm.getPermRole())){
				statusCode=DwzJsonResultUtil.STATUS_CODE_300;
				message="类型为角色时，角色不能留空！";
			}
			if(DwzJsonResultUtil.STATUS_CODE_200.equals(statusCode)){
				AuthPerm p=securityService.findPermById(perm.getId());
				boolean isExists=p==null?false:true;
				p=p==null?new AuthPerm():p;
				p.setDisplayName(perm.getDisplayName());
				p.setUrl(perm.getUrl());
				p.setPermType(perm.getPermType());
				p.setPermRole(perm.getPermRole());
				p.setParentId(perm.getParentId());
				try {
					if(isExists){
						securityService.updatePerm(p);
					}else{
						securityService.insertPerm(p);
					}
				} catch (Exception e) {
					e.printStackTrace();
					statusCode=DwzJsonResultUtil.STATUS_CODE_500;
					message="保存失败";
				}
			}
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, navTabId, null, null, rel);
	}
	
}
