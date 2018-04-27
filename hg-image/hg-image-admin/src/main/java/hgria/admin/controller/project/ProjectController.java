package hgria.admin.controller.project;


import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;
import hg.system.dto.auth.AuthStaffDTO;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;
import hg.system.service.AuthStaffService;
import hgria.admin.app.pojo.command.project.CreateProjectCommand;
import hgria.admin.app.pojo.command.project.ModifyProjectCommand;
import hgria.admin.app.pojo.command.project.RemoveProjectCommand;
import hgria.admin.app.pojo.dto.project.ProjectDTO;
import hgria.admin.app.pojo.exception.IMAGEException;
import hgria.admin.app.pojo.qo.ProjectQo;
import hgria.admin.app.service.ProjectService;
import hgria.admin.controller.BaseController;
import hgria.domain.model.project.Project;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：工程管理类	
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午4:00:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午4:00:08
 */
@Controller
@RequestMapping(value="hg/auth/project")
public class ProjectController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	/**
	 * 工程service
	 */
	@Resource(name="projectService")
	private ProjectService projectService;
	
	/**
	 * 员工service
	 */
	@Resource
	private AuthStaffService authStaffService;
	
	
	/**
	 * 工程主页面
	 */
	public final static String PAGE_VIEW 	= "/content/project/project.html";
	
	/**
	 * 工程添加页面
	 */
	public final static String PAGE_ADD 	= "/content/project/projectAdd.html";
	
	/**
	 * 工程编辑页面
	 */
	public final static String PAGE_EDIT 	= "/content/project/projectEdit.html";
	
	
	/**
	 * @方法功能说明：工程主页面跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月28日下午4:06:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param projectQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String index(HttpServletRequest request, Model model, 
			@ModelAttribute ProjectQo projectQo) {
		
		HgLogger.getInstance().info("zzb", "进入工程主页面方法:projectQo【" + JSON.toJSONString(projectQo) + "】");
		
		return PAGE_VIEW;
	}
	
	
	/**
	 * @方法功能说明：工程列表查询
	 * @修改者名字：zzb
	 * @修改时间：2014年11月7日下午5:40:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param projectQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/projectlist")
	public Pagination projectlist(HttpServletRequest request, @ModelAttribute ProjectQo projectQo) {
		
		HgLogger.getInstance().info("zzb", "进入工程列表查询方法:projectQo【" + JSON.toJSONString(projectQo) + "】");
		
		// 1. 设置属性值
		projectQo.setNameLike(true);
		
		// 2. 设置pagination并查询
		Pagination pagination = new Pagination();
		pagination.setCondition(projectQo);
		pagination.setPageNo(projectQo.getPageNo());
		pagination.setPageSize(projectQo.getPageSize());
		pagination = projectService.queryPagination(pagination);
		
		pagination.setList(EntityConvertUtils.convertEntityToDtoList
				((List<Project>) pagination.getList(), ProjectDTO.class));

		return pagination;
	}
	
	
	/**
	 * @方法功能说明：添加跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午4:01:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param projectQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute ProjectQo projectQo) {

		HgLogger.getInstance().info("zzb", "进入工程添加跳转方法:projectQo【" + JSON.toJSONString(projectQo, true) + "】");
		
		// 1. 查询出所有操作员
		AuthStaffQo staffQo = new AuthStaffQo();
		List<Staff> staffList = authStaffService.queryList(staffQo);
		
		model.addAttribute("staffList", EntityConvertUtils.convertEntityToDtoList
				((List<Staff>) staffList, AuthStaffDTO.class));
		
		return PAGE_ADD;
	}
	
	
	/**
	 * @方法功能说明：新增保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午6:39:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param project
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute CreateProjectCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入工程新增保存方法:project【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			// 2.2 保存
			projectService.createProject(command);
		} catch (IMAGEException e) {
			HgLogger.getInstance().error("zzb", "工程新增保存失败:project【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "project");
	}

	
	/**
	 * @方法功能说明：工程编辑跳转
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午5:58:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param projectQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, 
			Model model, @ModelAttribute ProjectQo projectQo) {

		HgLogger.getInstance().info("zzb", "进入工程编辑跳转方法:projectQo【" + JSON.toJSONString(projectQo, true) + "】");
		
		// 1. 空值判断
		if (projectQo == null || StringUtils.isBlank(projectQo.getId()))
			return null;
		
		// 2. 查询该对象
		projectQo.setStaffQo(new AuthStaffQo());
		Project project = projectService.queryUnique(projectQo);
		
		// 3. 查询出所有操作员
		AuthStaffQo staffQo = new AuthStaffQo();
		List<Staff> staffList = authStaffService.queryList(staffQo);
		
		// 4. 设置返回值
		model.addAttribute("project",   EntityConvertUtils.convertEntityToDto
				(project, ProjectDTO.class));
		model.addAttribute("staffList", EntityConvertUtils.convertEntityToDtoList
				((List<Staff>) staffList, AuthStaffDTO.class));
		return PAGE_EDIT;
	}
	
	
	/**
	 * @方法功能说明：工程编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年11月17日下午6:43:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param project
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute ModifyProjectCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入工程编辑保存方法:project【" + JSON.toJSONString(command, true) + "】");

		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 2. 保存
		try {
			projectService.modifyProject(command);
		} catch (IMAGEException e) {
			HgLogger.getInstance().error("zzb", "工程编辑保存失败:project【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "project");
	}
	
	
	/**
	 * @方法功能说明：工程删除
	 * @修改者名字：zzb
	 * @修改时间：2014年11月19日下午1:56:39
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
			@ModelAttribute RemoveProjectCommand command) {
		
		HgLogger.getInstance().info("zzb", "进入工程删除方法:command【" + JSON.toJSONString(command, true) + "】");
		
		try {
			projectService.removeProject(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "project");
		} catch (Exception e) {
			HgLogger.getInstance().error("zzb", "工程删除失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
}
