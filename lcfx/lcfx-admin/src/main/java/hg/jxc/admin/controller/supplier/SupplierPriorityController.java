package hg.jxc.admin.controller.supplier;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateSupplierPriorityCommand;
import hg.pojo.command.DeleteSupplierPriorityCommand;
import hg.pojo.command.ModifySupplierPriorityCommand;
import hg.pojo.qo.SupplierPriorityPolicyQO;
import hg.pojo.qo.SupplierPriorityQO;
import hg.pojo.qo.SupplierQO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.supplier.SupplierPriorityPolicyService;
import jxc.app.service.supplier.SupplierPriorityService;
import jxc.app.service.supplier.SupplierService;
import jxc.app.service.system.ProjectService;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierPriority;
import jxc.domain.model.supplier.SupplierPriorityPolicy;
import jxc.domain.model.system.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/supplier/priority")
public class SupplierPriorityController extends BaseController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SupplierPriorityPolicyService priorityPolicyService;

	@Autowired
	private SupplierPriorityService priorityService;

	/**
	 * 分页查询供应商优先级列表
	 * @param model
	 * @param dwzPaginQo
	 * @return
	 */
	@RequestMapping("/list")
	public String querySupplierPriorityList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo) {
		Pagination pagination = createPagination(dwzPaginQo, new SupplierPriorityPolicyQO());
		pagination = priorityPolicyService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "/supplier/priority/supplier_priority_list.html";
	}

	/**
	 * 跳转供应商优先级新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddSupplierPriority(Model model) {
		//查询所有供应商列表
		SupplierQO qo = new SupplierQO();
		List<Supplier> supplierList = supplierService.queryList(qo);
		//查询没有设置供应商优先级的项目
		List<Project> projectList = priorityService.findProjectNotInSupplierPriority();
		
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("projectList", projectList);
		
		return "/supplier/priority/supplier_priority_add.html";
	}

	/**
	 * 创建供应商优先级
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createSupplierPriority(CreateSupplierPriorityCommand command, HttpServletRequest request) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		if (command.getProjectIdList() == null || command.getProjectIdList().size() == 0) {
			return JsonResultUtil.dwzErrorMsg("未选择项目，不能添加");
		}
		if (command.getSupplierIdList() == null || command.getSupplierIdList().size() == 0) {
			return JsonResultUtil.dwzErrorMsg("未设置供应商优先级，不能添加");
		}
		command.setPriorityList(new ArrayList<Integer>());
		for (int i = 1; i <= command.getSupplierIdList().size(); i++) {
			command.getPriorityList().add(i);
		}

		priorityService.createSupplierPriority(command);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "sp_list");
	}

	/**
	 * 删除供应商优先级
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteSupplierPriority(DeleteSupplierPriorityCommand command) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		
		priorityService.deleteSupplierPriority(command);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功", null, "sp_list");
	}

	/**
	 * 跳转编辑供应商优先级页面
	 * @param model
	 * @param supplierPriorityPolicyQO
	 * @return
	 */
	@RequestMapping("/edit")
	public String editSupplierPriority(Model model, SupplierPriorityPolicyQO supplierPriorityPolicyQO) {

		//获取没有设置供应商优先级的项目
		List<Project> projectNotUseList = priorityService.findProjectNotInSupplierPriority();
		//根据供应商优先级策略id，生成已选择的项目列表
		List<Project> projectList=this.createProjectList(supplierPriorityPolicyQO.getId());

		model.addAttribute("projectUseSize", projectList.size());
		//按照选取在前原则，生成项目列表
		projectList.addAll(projectNotUseList);
		model.addAttribute("projectList", projectList);
		
		//根据供应商优先级策略id和项目id，生成供应商列表
		List<Supplier> supplierList=this.createSupplierList(supplierPriorityPolicyQO.getId(), projectList.get(0).getId());

		model.addAttribute("supplierList", supplierList);
		model.addAttribute("supplierPriorityPolicyId", supplierPriorityPolicyQO.getId());

		return "/supplier/priority/supplier_priority_edit.html";
	}

	/**
	 * 更新供应商优先级
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateSupplierPriority(ModifySupplierPriorityCommand command) {
		if (command.getProjectIdList() == null || command.getProjectIdList().size() == 0) {
			return JsonResultUtil.dwzErrorMsg("未选择项目，不能添加");
		}
		if (command.getSupplierIdList() == null || command.getSupplierIdList().size() == 0) {
			return JsonResultUtil.dwzErrorMsg("未设置供应商优先级，不能添加");
		}

		
		CommandUtil.SetOperator(getAuthUser(), command);
		
		command.setPriorityList(new ArrayList<Integer>());
		for (int i = 1; i <= command.getSupplierIdList().size(); i++) {
			command.getPriorityList().add(i);
		}
		priorityService.updateSupplierPriority(command);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "sp_list");
	}
	
	/**
	 * 根据供应商优先级策略id，生成已选择的项目列表
	 * @param policyId
	 * @return
	 */
	public List<Project> createProjectList(String policyId){
		
		//根据供应商优先级策略id，查询供应商优先级
		SupplierPriorityQO supplierPriorityQO = new SupplierPriorityQO();
		supplierPriorityQO.setSupplierPriorityPolicyId(policyId);
		List<SupplierPriority> supplierPriorityList = priorityService.queryList(supplierPriorityQO);

		//项目去重
		Set<String> projectIds = new HashSet<String>();
		for (SupplierPriority supplierPriority : supplierPriorityList) {
			projectIds.add(supplierPriority.getProject().getId());
		}
		
		List<Project> projectList=new ArrayList<Project>();
		Project project=new Project();
		
		for (String projectId : projectIds) {
			project=projectService.get(projectId);
			projectList.add(project);
		}
		
		return projectList;
	}
	
	/**
	 * 根据供应商优先级策略id和项目id，生成供应商列表
	 * @param policyId
	 * @param projectId
	 * @return
	 */
	public List<Supplier> createSupplierList(String policyId,String projectId){
		
		//根据供应商优先级策略id和项目id，查询供应商优先级
		SupplierPriorityQO supplierPriorityQO = new SupplierPriorityQO();
		supplierPriorityQO.setSupplierPriorityPolicyId(policyId);
		supplierPriorityQO.setProjectId(projectId);
		List<SupplierPriority> supplierPriorityList=priorityService.queryList(supplierPriorityQO);
		
		List<Supplier> supplierList = new ArrayList<Supplier>();
		//按照优先级顺序，生成供应商列表
		for (SupplierPriority supplierPriority : supplierPriorityList) {
			Supplier supplier=supplierService.get(supplierPriority.getSupplier().getId());
			supplierList.add(supplier);
		}
		
		/*
		//获取所有供应商列表
		List<Supplier> supplierAllList=supplierService.queryList(new SupplierQO());
		SupplierPriorityPolicy policy=priorityPolicyService.get(policyId);
		//把没有供应商优先级的供应商加到列表最后
		for (Supplier supplier : supplierAllList) {
			if(!policy.getSupplierName().contains(" "+supplier.getBaseInfo().getName()+" ")){
				supplierList.add(supplier);
			}
		}
		 */

		return supplierList;
	}

}