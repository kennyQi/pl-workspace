package hgria.admin.controller.hgCommon.perm;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.command.perm.CreateAuthPermCommand;
import hg.system.command.perm.ModifyAuthPermCommand;
import hg.system.command.perm.RemoveAuthPermCommand;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.EntityConvertUtils;
import hg.system.dto.auth.AuthPermDTO;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.qo.AuthPermQo;
import hg.system.service.AuthPermService;
import hgria.admin.common.hgUtil.HgCommonUtil;
import hgria.admin.common.hgUtil.HgCommonUtil.TreeTran;
import hgria.admin.controller.BaseController;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @类功能说明：资源控制器
 * @类修改者：zzb
 * @修改日期：2014年10月15日下午5:04:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月15日下午5:04:18
 *
 */
@Controller("hgPermController")
@RequestMapping(value="hg/auth/perm")
public class PermController extends BaseController {
	
	/**
	 * 资源service
	 */
	@Resource
	private AuthPermService authPermService;
	
	/**
	 * 资源页面
	 */
	public final static String PAGE_VIEW = "/content/perm/perm.html";
	
	/**
	 * 资源添加页面
	 */
	public final static String PAGE_ADD = "/content/perm/permAdd.html";
	
	/**
	 * 资源编辑页面
	 */
	public final static String PAGE_EDIT = "/content/perm/permEdit.html";
	
	/**
	 * 
	 * @方法功能说明：资源主页面
	 * @修改者名字：zzb
	 * @修改时间：2014年10月15日下午5:04:14
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
	 * @方法功能说明：资源树加载
	 * @修改者名字：zzb
	 * @修改时间：2014年10月15日下午5:04:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param parentId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/permTree")
	public JSONArray permTree(AuthPermQo permQo) {
		
		HgLogger.getInstance().info("zzb", "进入资源树加载方法:permQo【" + JSON.toJSONString(permQo) + "】");
		
		// 1. 查询
		List<AuthPerm> authPermList = authPermService.queryList(permQo);

		return HgCommonUtil.toTreeJson(authPermList, new TreeTran() {
			@Override
			public String getId(Object ob) {
				return ((AuthPerm) ob).getId();
			}
			@Override
			public String getText(Object ob) {
				return ((AuthPerm) ob).getDisplayName();
			}
			@Override
			public Boolean getOpened(Object ob) {
				return true;
			}
		});

	}
	
	
	/**
	 * @方法功能说明：进入资源列表查询方法
	 * @修改者名字：zzb
	 * @修改时间：2014年10月16日上午10:40:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param permQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/permlist")
	public Pagination permlist(HttpServletRequest request, @ModelAttribute AuthPermQo permQo) {
		
		HgLogger.getInstance().info("zzb", "进入资源列表查询方法:permQo【" + JSON.toJSONString(permQo) + "】");
		
		Pagination pagination = permQo.getPagination();
		pagination.setCondition(permQo);
		pagination = authPermService.queryPagination(pagination);

		pagination.setList(EntityConvertUtils.convertEntityToDtoList
				((List<AuthPerm>) pagination.getList(), AuthPermDTO.class));

		pagination.setCondition(SecurityConstants.PERM_TYPE_LIST);
		return pagination;
	}

	/**
	 * @方法功能说明：资源添加
	 * @修改者名字：zzb
	 * @修改时间：2014年10月16日下午3:41:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param permQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthPermQo permQo) {
		
		HgLogger.getInstance().info("zzb", "进入资源添加跳转方法:permQo【" + JSON.toJSONString(permQo) + "】");
		List<AuthPerm> list = authPermService.queryList(new AuthPermQo());
		List<AuthPerm> permList = new ArrayList<AuthPerm>();
		for (AuthPerm p : list) {
			if (!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())) {
				permList.add(p);
			}
		}
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
		model.addAttribute("parentPermList", 
				EntityConvertUtils.convertEntityToDtoList(permList, AuthPermDTO.class));
		model.addAttribute("parentId", permQo.getParentId());
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：资源添加保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午9:21:14
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
			@ModelAttribute CreateAuthPermCommand command) {

		HgLogger.getInstance().info("zzb", "进入资源新增保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authPermService.createAuthPerm(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "资源新增保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authPerm");
	}

	
	/**
	 * @方法功能说明：资源编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午10:21:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param permQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute AuthPermQo permQo) {

		HgLogger.getInstance().info("zzb", "进入资源编辑跳转方法:permQo【" + JSON.toJSONString(permQo, true) + "】");
		
		// 1. 检查
		if (permQo == null || StringUtils.isBlank(permQo.getId())) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑资源异常！");
		}
		
		AuthPerm authPerm = authPermService.queryUnique(permQo);
		if (authPerm == null)
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "编辑资源异常！");
		
		// 2. 设置属性
		List<AuthPerm> list = authPermService.queryList(new AuthPermQo());
		List<AuthPerm> permList = new ArrayList<AuthPerm>();
		for (AuthPerm p : list) {
			if (!SecurityConstants.PERM_TYPE_ROLE.equals(p.getPermType())) {
				permList.add(p);
			}
		}
		model.addAttribute("permTypeList", SecurityConstants.PERM_TYPE_LIST);
		model.addAttribute("parentPermList", permList);
		model.addAttribute("perm", EntityConvertUtils
				.convertEntityToDto(authPerm, AuthPermDTO.class));
		return PAGE_EDIT;
	}
	
	/**
	 * @方法功能说明：资源编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午9:21:14
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
			@ModelAttribute ModifyAuthPermCommand command) {

		HgLogger.getInstance().info("zzb", "进入资源编辑保存方法:command【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			authPermService.modifyAuthPerm(command);
		} catch (HGException e) {
			HgLogger.getInstance().error("zzb", "资源编辑保存失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "authPerm");
	}
	
	
	/**
	 * @方法功能说明：资源删除
	 * @修改者名字：zzb
	 * @修改时间：2014年10月31日下午5:17:22
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
			@ModelAttribute RemoveAuthPermCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入资源删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			authPermService.removeAuthPerm(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "authPerm");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "资源删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}

}
