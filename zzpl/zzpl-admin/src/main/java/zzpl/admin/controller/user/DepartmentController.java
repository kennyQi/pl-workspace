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
import zzpl.app.service.local.user.DepartmentService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddDepartmentCommand;
import zzpl.pojo.command.user.DeleteDepartmentCommand;
import zzpl.pojo.command.user.ModifyDepartmentCommand;
import zzpl.pojo.dto.user.DepartmentDTO;
import zzpl.pojo.qo.user.DepartmentQO;
import zzpl.pojo.qo.user.UserQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends BaseController {

	/**
	 * 部门service
	 */
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * @Title: view 
	 * @author guok
	 * @时间  2015年6月25日 10:34:16
	 * @Description: 跳转部门列表页
	 * @param request
	 * @param model
	 * @param departmentQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute DepartmentQO departmentQO) {
		return "/content/user/department_list.html";
	}
	
	/**
	 * @Title: departmentList 
	 * @author guok
	 * @Description: 列表展示
	 * @time 2015年6月25日 10:41:24
	 * @param request
	 * @param model
	 * @param departmentQO
	 * @param pageNo
	 * @param pageSize
	 * @param departmentName
	 * @return Pagination 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/departmentList")
	public Pagination departmentList(HttpServletRequest request, Model model,
			DepartmentQO departmentQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "departmentName", required = false) String departmentName) {
		
		HgLogger.getInstance().error("cs",	"【DepartmentController】【departmentList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		departmentQO.setDepartmentName(departmentName);
		//获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		
		departmentQO.setCompanyID(user.getCompanyID());
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(departmentQO);
		if(StringUtils.isNotBlank(user.getCompanyID())){
			pagination = departmentService.queryPagination(pagination);
			pagination.setList(EntityConvertUtils.convertEntityToDtoList(
					(List<DepartmentDTO>) pagination.getList(), DepartmentDTO.class));
		}
		return pagination;
	}
	
	/**
	 * 
	 * @Title: deleteDepartment 
	 * @author guok
	 * @Description: 删除
	 * @time 2015年6月25日 11:10:06
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String deleteDepartment(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteDepartmentCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【DepartmentController】【deleteDepartment】,command："+JSON.toJSONString(command));
			departmentService.deleteDepartment(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("departmentMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", "删除失败", null, "");
		}
	}
	
	/**
	 * @throws Exception 
	 * @Title: add 
	 * @author guok
	 * @Description: 跳转添加页
	 * @time 2015年6月25日 11:09:52
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		return "/content/user/department_add.html";
	}
	
	/**
	 * @Title: departmentadd 
	 * @author guok
	 * @Description: 添加
	 * @time 2015年6月25日 11:21:03
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 * @throws DepartmentException String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/departmentadd")
	public String departmentAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddDepartmentCommand command){
			try {
				//获取当前登陆用户，以用于查找用户所属公司
				AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
				command.setUserID(user.getId());
				HgLogger.getInstance().error("cs",	"【DepartmentController】【departmentAdd】,command："+JSON.toJSONString(command));
				departmentService.addDepartment(command);
				return DwzJsonResultUtil.createJsonString("200", "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("departmentMenuID"));
			} catch (Exception e) {
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到编辑页
	 * @time 2015年6月25日 14:14:20
	 * @param request
	 * @param response
	 * @param model
	 * @param departmentID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "departmentID") String departmentID) {
		Department department=departmentService.get(departmentID);
		model.addAttribute("department", department);
		return "/content/user/department_edit.html";
	}
	
	/**
	 * @Title: departmentEdit 
	 * @author guok
	 * @Description: 编辑
	 * @Time 2015年6月25日 15:32:08
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/departmentedit")
	public String departmentEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyDepartmentCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【DepartmentController】【departmentEdit】,command："+JSON.toJSONString(command));
			departmentService.modfiyDepartment(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,SysProperties.getInstance().get("departmentMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
		
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到编辑页
	 * @time 2015年6月25日 14:14:20
	 * @param request
	 * @param response
	 * @param model
	 * @param departmentID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "departmentID") String departmentID) {
		Department department=departmentService.get(departmentID);
		
		UserQO qo=new UserQO();
		qo.setDepartmentID(departmentID);
		List<User> userList=userService.queryList(qo);
		
		model.addAttribute("userList", userList);
		model.addAttribute("department", department);
		return "/content/user/department_detail.html";
	}
	
}
