package hgfx.web.controller.sys;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.demo.member.common.domain.model.system.DwzTreeNode;
import hg.demo.member.common.domain.model.system.DwzTreeUtil;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.AuthPermSPI;
import hg.demo.member.common.spi.AuthRoleSPI;
import hg.demo.member.common.spi.command.authPerm.CreateOrModifyAuthPermCommand;
import hg.demo.member.common.spi.command.authPerm.DeleteAuthPermCommand;
import hg.demo.member.common.spi.qo.AuthPermSQO;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/auth/perm")
public class PermController {

	public static final String navTabId = "authPerm";
	public static final String rel = "jbsxBoxAuthPerm";
	
	@Resource
	private AuthPermSPI authPermSPIService;
	@Resource
	private AuthRoleSPI authRoleSPIService;
	/**
	 * 权限资源管理
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model,
			@RequestParam(value="numPerPage", required = false) Integer pageSize,
			@RequestParam(value="pageNum", required = false) Integer pageNo,
			@ModelAttribute AuthPermSQO sqo){
		if (pageSize == null) {
			pageSize = 20;
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNo);
		sqo.getLimit().setPageSize(pageSize);
		
		Pagination<AuthPerm> permAllList=authPermSPIService.queryAuthPermPagination(sqo);
		permAllList.setPageNo(pageNo);
		permAllList.setPageSize(pageSize);
		
		List<AuthPerm> List=permAllList.getList();
		List<DwzTreeNode> dtnList=new ArrayList<DwzTreeNode>();
		for(AuthPerm perm:List){
			if(!SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())){
				DwzTreeNode dtn=new DwzTreeNode();
				dtn.setDisplayName(perm.getDisplayName());
				dtn.setTarget("ajax");
				dtn.setRel(rel);
				dtn.setHref(request.getContextPath()+"/auth/perm/list?id="+perm.getId());
				dtn.setId(perm.getId());
				if(perm.getParentId()!=null && perm.getParentId().length()>0){
					dtn.setParentId(perm.getParentId());
				}
				dtnList.add(dtn);
			}
		}
		
		String authPermTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnList, null);
		
		model.addAttribute("authPermTreeHtml", authPermTreeHtml);
		
		list(request, model, pageSize,pageNo, sqo);
		
		return "/auth/permIndex.ftl";
	}
	/**
	 * 权限资源列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model, 
			@RequestParam(value="numPerPage", required = false) Integer pageSize,
			@RequestParam(value="pageNum", required = false) Integer pageNo,
			@ModelAttribute AuthPermSQO sqo) {
		
		if (pageSize == null) {
			pageSize = 20;
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNo);
		sqo.getLimit().setPageSize(pageSize);
		
		Map<String, Object> conditionMap=new HashMap<String, Object>();
		conditionMap.put("permType", sqo.getPermType());
		conditionMap.put("parentId", sqo.getPermId());
		if(StringUtils.isNotBlank(sqo.getUrl())) {
			conditionMap.put("urlLike", sqo.getUrl().trim());
		}
		
		Pagination<AuthPerm> paging=authPermSPIService.queryAuthPermPagination(sqo);
		paging.setPageNo(pageNo);
		paging.setPageSize(pageSize);
		
		model.addAttribute("pagination", paging);
		model.addAttribute("sqo", sqo);
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
		
		return "/auth/permList.ftl";
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
			@ModelAttribute CreateOrModifyAuthPermCommand command){
		
		AuthPerm perm=new AuthPerm();
		perm.setPermType(SecurityConstants.PERM_TYPE_AUTH);
		perm.setParentId(command.getId());
		
		model.addAttribute("perm", perm);
		AuthRoleSQO s=new AuthRoleSQO();
		model.addAttribute("roleList", authRoleSPIService.queryAuthRoles(s));
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
		AuthPermSQO sqo=new AuthPermSQO();
		List<AuthPerm> list=authPermSPIService.queryAuthPerms(sqo);
		List<AuthPerm> permList=new ArrayList<AuthPerm>();
		for(AuthPerm p:list){
			if(!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())){
				permList.add(p);
			}
			
		}
		model.addAttribute("parentPermList", permList);
		
		return "/auth/permEdit.ftl";
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
			@ModelAttribute CreateOrModifyAuthPermCommand command){
		
 		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="保存成功";
		String callbackType=DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		AuthPerm perm=command.getPerm();
		if(perm!=null){
			//错误检查
			if(SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType()) && StringUtils.isBlank(perm.getPermRole())){
			 	statusCode=DwzJsonResultUtil.STATUS_CODE_300;
				message="类型为角色时，角色不能留空！";
			}
			if(DwzJsonResultUtil.STATUS_CODE_200.equals(statusCode)){
				if(StringUtils.isNotBlank(perm.getId())){
					authPermSPIService.modify(command);
				}else{
				try {
						authPermSPIService.create(command);
					
				} catch (Exception e) {
					e.printStackTrace();
					statusCode=DwzJsonResultUtil.STATUS_CODE_500;
					message="保存失败";
				}
				}
			}
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, navTabId, null, null, rel);
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
			@ModelAttribute CreateOrModifyAuthPermCommand command){
		AuthPermSQO sqo=new AuthPermSQO();
		sqo.setId(command.getId());
		AuthPerm perm=authPermSPIService.queryAuthPerm(sqo);
		if(perm!=null){
			command.setPerm(perm);
			AuthRoleSQO s=new AuthRoleSQO();
			model.addAttribute("roleList", authRoleSPIService.queryAuthRoles(s));
			model.addAttribute("perm", perm);
			model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
			AuthPermSQO sqo2=new AuthPermSQO();
			List<AuthPerm> list=authPermSPIService.queryAuthPerms(sqo2);
			List<AuthPerm> permList=new ArrayList<AuthPerm>();
			for(AuthPerm p:list){
				if(!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())
						&& !p.getId().equals(perm.getId())){
					permList.add(p);
				}
			}
			model.addAttribute("parentPermList", permList);
		}else{
			return add(request, response, model, command);
		}
		
		return "/auth/permEdit.ftl";
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
			@ModelAttribute DeleteAuthPermCommand sqo){
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="删除成功";
		try {
			if(sqo.getId()!=null){
				authPermSPIService.delete(sqo);
			}else if(sqo.getIds()!=null && sqo.getIds().length>0){
				for (String id : sqo.getIds()) {
					sqo.setId(id);
					authPermSPIService.delete(sqo);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
			message="删除失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, null, navTabId, null, null, rel);
	}
	
}
