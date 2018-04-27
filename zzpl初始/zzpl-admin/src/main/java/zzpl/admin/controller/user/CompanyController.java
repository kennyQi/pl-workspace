package zzpl.admin.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.auth.AuthUser;
import hg.system.service.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddCompanyCommand;
import zzpl.pojo.command.user.DeleteCompanyCommand;
import zzpl.pojo.command.user.ModifyCompanyCommand;
import zzpl.pojo.dto.user.CompanyDTO;
import zzpl.pojo.exception.user.CompanyException;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.user.UserQO;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	/**
	 * 公司service
	 */
	@Autowired
	private CompanyService companyService;
	/**
	 * 登录用户service
	 */
	@Autowired
	private AuthUserService authUserService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @方法功能说明：跳转到列表页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param companyQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute CompanyQO companyQO) {
		return "/content/user/company_list.html";
	}

	/**
	 * 
	 * @方法功能说明：列表展示
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param companyQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param companyName
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/companylist")
	public Pagination companyList(HttpServletRequest request, Model model,
			CompanyQO companyQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "companyName", required = false) String companyName) {
		HgLogger.getInstance().error("cs",	"【CompanyController】【companyList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		companyQO.setCompanyName(companyName);
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(companyQO);
		pagination = companyService.queryPagination(pagination);
		pagination.setList(EntityConvertUtils.convertEntityToDtoList(
				(List<CompanyDTO>) pagination.getList(), CompanyDTO.class));
		return pagination;
	}

	/**
	 * 
	 * @方法功能说明：删除
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:47
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
	@RequestMapping(value = "/del")
	public String deleteCompany(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteCompanyCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【CompanyController】【deleteCompany】,command："+JSON.toJSONString(command));
			companyService.deleteCompany(command);
			return DwzJsonResultUtil
					.createJsonString("200", "删除成功!", null, "1");
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", "删除失败", null, "");
		}
	}

	/**
	 * 
	 * @方法功能说明：跳转到添加页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) {
		return "/content/user/company_add.html";
	}

	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:57
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
	@RequestMapping(value = "/companyadd")
	public String companyadd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddCompanyCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【CompanyController】【companyadd】,command："+JSON.toJSONString(command));
			companyService.addCompany(command);
			return DwzJsonResultUtil.createJsonString("200", "添加成功", null, "1");
		} catch (CompanyException e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}

	/**
	 * 
	 * @方法功能说明：跳转到编辑页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:11:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param companyID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "companyID") String companyID) {
		Company company = companyService.get(companyID);
		UserQO userQO = new UserQO();
		userQO.setCompanyID(companyID);
		userQO.setRoleID(SysProperties.getInstance().get("companyAdminID"));
		User user = userService.queryUnique(userQO);
		AuthUser authUser=authUserService.get(user.getId());
		model.addAttribute("loginName", authUser.getLoginName());
		model.addAttribute("company", company);
		return "/content/user/company_edit.html";
	}
	
	/**
	 * 
	 * @方法功能说明：编辑
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:11:12
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
	@RequestMapping(value = "/companyedit")
	public String companyedit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyCompanyCommand command) {
		HgLogger.getInstance().error("cs",	"【CompanyController】【companyedit】,command："+JSON.toJSONString(command));
			companyService.modfiyCompany(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功", null, "1");
	}
}
