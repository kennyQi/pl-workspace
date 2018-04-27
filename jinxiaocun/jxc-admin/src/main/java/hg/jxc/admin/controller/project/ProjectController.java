package hg.jxc.admin.controller.project;

import hg.common.component.BaseQo;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateProjectCommand;
import hg.pojo.command.DeleteProjectCommand;
import hg.pojo.command.ModifyProjectCommand;
import hg.pojo.exception.ProjectException;
import hg.pojo.qo.ProjectQO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.system.OperationFormService;
import jxc.app.service.system.ProjectModeService;
import jxc.app.service.system.ProjectService;
import jxc.app.service.system.ProjectTypeService;
import jxc.domain.model.system.OperationForm;
import jxc.domain.model.system.Project;
import jxc.domain.model.system.ProjectMode;
import jxc.domain.model.system.ProjectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectTypeService projectTypeService;
	
	@Autowired
	private ProjectModeService projectModeService;
	
	@Autowired
	private OperationFormService operationFormService;

	/**
	 * 分页查询项目列表
	 * @param model
	 * @param dwzPaginQo
	 * @param qo
	 * @return
	 */
	@RequestMapping("/list")
	public String queryProjectList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo, ProjectQO qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = projectService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "project/project_list.html";
	}

	/**
	 * 跳转新增项目页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddProject(Model model) {
		
		List<OperationForm> operationFormList = operationFormService.queryList(new BaseQo());
		List<ProjectMode> projectModeList = projectModeService.queryList(new BaseQo());
		List<ProjectType> projectTypeList = projectTypeService.queryList(new BaseQo());
		
		model.addAttribute("operationFormList", operationFormList);
		model.addAttribute("projectModeList", projectModeList);
		model.addAttribute("projectTypeList", projectTypeList);

		return "project/project_add.html";
	}

	/**
	 * 新增项目
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createProject(CreateProjectCommand command, HttpServletRequest request) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		//判断是否选择现有运营形式
		if("".equals(command.getOperationFormId())){
			//获取填写的运营形式
			String extraOperationFormName = request.getParameter("extraOfName");
			//运营形式已填写
			OperationForm operationForm = operationFormService.createOperationForm(extraOperationFormName);
			command.setOperationFormId(operationForm.getId());
		}
		//判断是否选择现有项目类型
		if("".equals(command.getProjectTypeId())){
			//获取填写的项目类型
			String extraProjectTypeName = request.getParameter("extraPtName");
			//项目类型已填写
			ProjectType projectType = projectTypeService.createProjectType(extraProjectTypeName);
			command.setProjectTypeId(projectType.getId());
		}
		//判断是否选择现有项目模式
		if("".equals(command.getProjectModeId())){
			//获取填写的项目模式
			String extraProjectModeName = request.getParameter("extraPName");
			//项目模式已填写
			ProjectMode projectMode = projectModeService.createProjectMode(extraProjectModeName);
			command.setProjectModeId(projectMode.getId());
		}

		
		try {
			
			projectService.createProjcet(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "project_list");
			
		} catch (ProjectException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

		
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String deleteProject(DeleteProjectCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			
			projectService.deleteProjcet(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功", null, "project_list");
			
		} catch (ProjectException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());			
		}

	}

	/**
	 * 跳转项目编辑页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editProject(Model model, @RequestParam(value = "id", required = true) String id) {
		Project project = projectService.get(id);
		model.addAttribute("project", project);

		List<OperationForm> operationFormList = operationFormService.queryList(new BaseQo());
		List<ProjectMode> projectModeList = projectModeService.queryList(new BaseQo());
		List<ProjectType> projectTypeList = projectTypeService.queryList(new BaseQo());
		model.addAttribute("operationFormList", operationFormList);
		model.addAttribute("projectModeList", projectModeList);
		model.addAttribute("projectTypeList", projectTypeList);

		return "project/project_edit.html";
	}
	
	/**
	 * 更新项目
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateProject(ModifyProjectCommand command, HttpServletRequest request) {
		if("".equals(command.getOperationFormId())){
			//获取运营形式
			String extraOperationFormName = request.getParameter("extraOfName");
			//运营形式已填写
			OperationForm operationForm = operationFormService.createOperationForm(extraOperationFormName);
			command.setOperationFormId(operationForm.getId());
		}
		
		if("".equals(command.getProjectTypeId())){
			//获取项目类型
			String extraProjectTypeName = request.getParameter("extraPtName");
			//项目类型已填写
			ProjectType projectType = projectTypeService.createProjectType(extraProjectTypeName);
			command.setProjectTypeId(projectType.getId());
		}
		
		if("".equals(command.getProjectModeId())){
			//获取项目模式
			String extraProjectModeName = request.getParameter("extraPName");
			//项目模式已填写
			ProjectMode projectMode = projectModeService.createProjectMode(extraProjectModeName);
			command.setProjectModeId(projectMode.getId());
		}

		CommandUtil.SetOperator(getAuthUser(), command);
		try {
			
			projectService.updateProjcet(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "项目修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "project_list");
			
		} catch (ProjectException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}
		
	}
	

}