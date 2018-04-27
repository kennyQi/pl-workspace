package zzpl.app.service.local.user;

import hg.common.component.BaseServiceImpl;
import hg.common.util.Md5Util;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.system.dao.AuthUserDao;
import hg.system.dao.StaffDao;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.model.staff.StaffBaseInfo;
import hg.system.qo.AuthUserQo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.MenuDAO;
import zzpl.app.dao.user.RoleDAO;
import zzpl.app.dao.user.RoleMenuDAO;
import zzpl.app.dao.user.RoleWorkflowDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.user.UserRoleDAO;
import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.app.dao.workflow.WorkflowDAO;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.app.dao.workflow.WorkflowStepDAO;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.RoleMenu;
import zzpl.domain.model.user.RoleWorkflow;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.domain.model.workflow.Workflow;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.command.user.AddCompanyCommand;
import zzpl.pojo.command.user.DeleteCompanyCommand;
import zzpl.pojo.command.user.ModifyCompanyCommand;
import zzpl.pojo.dto.user.status.CompanyStatus;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.exception.user.CompanyException;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;

@Service
@Transactional
public class CompanyService extends
		BaseServiceImpl<Company, CompanyQO, CompanyDAO> {

	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private AuthUserDao authUserDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private MenuDAO menuDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private RoleMenuDAO roleMenuDAO;
	@Autowired
	private WorkflowDAO workflowDAO;
	@Autowired
	private WorkflowStepDAO workflowStepDAO;
	@Autowired
	private RoleWorkflowDAO roleWorkflowDAO;
	@Autowired
	private WorkflowStepActionDAO workflowStepActionDAO;
	@Autowired
	private StepActionDAO stepActionDAO;
	/**
	 * 
	 * @方法功能说明：删除
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:13:08
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void deleteCompany(DeleteCompanyCommand command) {
		Company company = companyDAO.get(command.getCompanyID());
		company.setStatus(command.getStauts());
		companyDAO.update(company);
	}

	/**
	 * 
	 * @方法功能说明：新增公司（新增企业级用户并给企业级用户赋菜单，并且初始化一个员工角色）
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:13:12
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws CompanyException
	 * @return:void
	 * @throws
	 */
	public void addCompany(AddCompanyCommand command) throws CompanyException {
		// 校验公司ID是否存在
		CompanyQO companyQO = new CompanyQO();
		companyQO.setCompanyID(command.getCompanyID());
		if (companyDAO.queryCount(companyQO) != 0)
			throw new CompanyException(CompanyException.COMPANY_ID_REPEAT);
		// 校验管理员登录名是否存在
		AuthUserQo authUserQo = new AuthUserQo();
		authUserQo.setLoginName(command.getLoginName());
		authUserQo.setLoginNameLike(false);
		if (authUserDao.queryCount(authUserQo) != 0)
			throw new CompanyException(CompanyException.LOGIN_NAME_REPEAT);
		// 生成统一ID
		String id = UUIDGenerator.getUUID();
		// 1.系统登录表
		// ---------开始---------
		AuthUser authUser = new AuthUser();
		authUser.setId(id);
		authUser.setLoginName(command.getLoginName());
		authUser.setPasswd(Md5Util.MD5(Md5Util.MD5(command.getLoginName())));
		authUser.setDisplayName(command.getCompanyName() + "管理员");
		authUser.setCreateDate(new Date());
		authUserDao.save(authUser);
		Staff staff = new Staff();
		staff.setId(id);
		staff.setAuthUser(authUser);
		StaffBaseInfo staffBaseInfo = new StaffBaseInfo();
		staffBaseInfo.setRealName(command.getCompanyName() + "管理员");
		staff.setInfo(staffBaseInfo);
		staffDao.save(staff);
		// ---------结束---------
		// 2.添加公司信息
		// ---------开始---------
		Company company = new Company();
		company.setId(UUIDGenerator.getUUID());
		company.setCompanyID(command.getCompanyID());
		company.setCompanyName(command.getCompanyName());
		company.setDescription(command.getDescription());
		company.setStatus(CompanyStatus.NORMAL);
		company.setSort(companyDAO.maxProperty("sort", new CompanyQO()) + 1);
		company.setCreateTime(new Date());
		companyDAO.save(company);
		// ---------结束---------
		// 3.添加‘企业管理员’角色
		// ---------开始---------
		Role role = new Role();
		String adminRoleID = command.getCompanyID()+SysProperties.getInstance().get("companyAdminID");
		if(roleDAO.get(adminRoleID)!=null)
			throw new CompanyException(CompanyException.COMPANY_ID_REPEAT);
		role.setId(adminRoleID);
		role.setRoleName(SysProperties.getInstance().get("adminRoleName"));
		role.setDescription(SysProperties.getInstance().get("adminRoleName"));
		role.setSort(1);
		role.setCompany(company);
		role.setCreateTime(new Date());
		roleDAO.save(role);
		// ---------结束---------
		// 4.给‘企业管理员’角色添加菜单
		// ---------开始---------
		String[] menuIDs = SysProperties.getInstance().get("adminMenu").split(",");
		for(int i=0;i<menuIDs.length;i++){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setId(UUIDGenerator.getUUID());
			roleMenu.setRole(roleDAO.get(adminRoleID));
			roleMenu.setMenu(menuDAO.get(menuIDs[i]));
			roleMenuDAO.save(roleMenu);
		}
		// ---------结束---------
		// 5.添加用户基本信息
		// ---------开始---------
		User user = new User();
		user.setId(id);
		user.setLoginName(Md5Util.MD5(command.getLoginName()));
		user.setShowLoginName(command.getLoginName());
		user.setPassword(Md5Util.MD5(Md5Util.MD5(command.getLoginName())));
		user.setCompanyID(company.getId());
		user.setCompanyName(company.getCompanyName());
		user.setActivated(UserStatus.ACTIVATED);
		user.setStatus(UserStatus.NORMAL);
		user.setName("系统管理员");
		user.setUserNO("SYS");
		user.setDepartmentName("系统管理员");
		userDAO.save(user);
		UserRole userRole = new UserRole();
		userRole.setId(UUIDGenerator.getUUID());
		userRole.setUser(user);
		userRole.setRole(roleDAO.get(adminRoleID));
		userRoleDAO.save(userRole);
		// ---------结束---------
		//6.添加‘员工’角色
		role = new Role();
		role.setId(UUIDGenerator.getUUID());
		role.setRoleName(SysProperties.getInstance().get("employeeRoleName"));
		role.setDescription(SysProperties.getInstance().get("employeeRoleName"));
		role.setSort(2);
		role.setCompany(company);
		role.setCreateTime(new Date());
		roleDAO.save(role);
		//新增流程
		//购票流程
		//退票流程
		//保存购票流程
		Workflow buyworkflow = new Workflow();
		buyworkflow.setId(UUIDGenerator.getUUID());
		buyworkflow.setCompanyID(company.getId());
		buyworkflow.setCompanyName(company.getCompanyName());
		buyworkflow.setCreateTime(new Date());
		buyworkflow.setWorkflowName("购票流程");
		workflowDAO.save(buyworkflow);
		//保存角色流程关系
		RoleWorkflow buyroleWorkflow = new RoleWorkflow();
		buyroleWorkflow.setId(UUIDGenerator.getUUID());
		buyroleWorkflow.setWorkflow(buyworkflow);
		buyroleWorkflow.setRole(role);
		roleWorkflowDAO.save(buyroleWorkflow);
		//保存退票流程
		Workflow refundworkflow = new Workflow();
		refundworkflow.setId(UUIDGenerator.getUUID());
		refundworkflow.setCompanyID(company.getId());
		refundworkflow.setCompanyName(company.getCompanyName());
		refundworkflow.setCreateTime(new Date());
		refundworkflow.setWorkflowName("退票流程");
		workflowDAO.save(refundworkflow);
		//保存角色流程关系
		RoleWorkflow refundroleWorkflow = new RoleWorkflow();
		refundroleWorkflow.setId(UUIDGenerator.getUUID());
		refundroleWorkflow.setWorkflow(refundworkflow);
		refundroleWorkflow.setRole(role);
		roleWorkflowDAO.save(refundroleWorkflow);
		//环节1
		WorkflowStep workflowStep_A1 = new WorkflowStep();
		workflowStep_A1.setId(UUIDGenerator.getUUID());
		workflowStep_A1.setWorkflowName(buyworkflow.getWorkflowName());
		workflowStep_A1.setWorkflowID(buyworkflow.getId());
		workflowStep_A1.setStepName("申请");
		workflowStep_A1.setStepNO(1);
		workflowStep_A1.setPreviousStepNO("0");
		workflowStep_A1.setNextStepNO("2;");
		workflowStep_A1.setCompanyName(company.getCompanyName());
		workflowStep_A1.setCompanyID(company.getId());
		workflowStep_A1.setStepType(1);
		workflowStepDAO.save(workflowStep_A1);
		//环节2
		WorkflowStep workflowStep_A2 = new WorkflowStep();
		workflowStep_A2.setId(UUIDGenerator.getUUID());
		workflowStep_A2.setWorkflowName(buyworkflow.getWorkflowName());
		workflowStep_A2.setWorkflowID(buyworkflow.getId());
		workflowStep_A2.setStepName("出票");
		workflowStep_A2.setStepNO(2);
		workflowStep_A2.setPreviousStepNO("1;");
		workflowStep_A2.setNextStepNO("99;");
		workflowStep_A2.setCompanyName(company.getCompanyName());
		workflowStep_A2.setCompanyID(company.getId());
		workflowStep_A2.setStepType(1);
		workflowStep_A2.setChooseExecutorType(2);
		workflowStep_A2.setExecutor(SysProperties.getInstance().get("travleAdminID"));
		List<WorkflowStepAction> workflowStepActions = new ArrayList<WorkflowStepAction>();
		int max = workflowStepActionDAO.maxProperty("sort", new WorkflowStepActionQO());
		WorkflowStepAction workflowStepAction_A21  = new WorkflowStepAction();
		workflowStepAction_A21.setId(UUIDGenerator.getUUID());
		workflowStepAction_A21.setOrderType("1");
		workflowStepAction_A21.setStatus(1);
		workflowStepAction_A21.setStepAction(stepActionDAO.get("3"));
		workflowStepAction_A21.setSort(max+1);
		workflowStepAction_A21.setWorkflowStep(workflowStep_A2);
		workflowStepActions.add(workflowStepAction_A21);
		WorkflowStepAction workflowStepAction_A22  = new WorkflowStepAction();
		workflowStepAction_A22.setId(UUIDGenerator.getUUID());
		workflowStepAction_A22.setOrderType("1");
		workflowStepAction_A22.setStatus(1);
		workflowStepAction_A22.setStepAction(stepActionDAO.get("13"));
		workflowStepAction_A22.setSort(max+2);
		workflowStepAction_A22.setWorkflowStep(workflowStep_A2);
		workflowStepActions.add(workflowStepAction_A22);
		WorkflowStepAction workflowStepAction_A23  = new WorkflowStepAction();
		workflowStepAction_A23.setId(UUIDGenerator.getUUID());
		workflowStepAction_A23.setOrderType("1");
		workflowStepAction_A23.setStatus(1);
		workflowStepAction_A23.setStepAction(stepActionDAO.get("16"));
		workflowStepAction_A23.setSort(max+3);
		workflowStepAction_A23.setWorkflowStep(workflowStep_A2);
		workflowStepActions.add(workflowStepAction_A23);
		WorkflowStepAction workflowStepAction_A24  = new WorkflowStepAction();
		workflowStepAction_A24.setId(UUIDGenerator.getUUID());
		workflowStepAction_A24.setOrderType("1");
		workflowStepAction_A24.setStatus(1);
		workflowStepAction_A24.setStepAction(stepActionDAO.get("17"));
		workflowStepAction_A24.setSort(max+4);
		workflowStepAction_A24.setWorkflowStep(workflowStep_A2);
		workflowStepActions.add(workflowStepAction_A24);
		workflowStep_A2.setWorkflowStepActions(workflowStepActions);
		workflowStepDAO.save(workflowStep_A2);
		//环节1
		WorkflowStep workflowStep_B1 = new WorkflowStep();
		workflowStep_B1.setId(UUIDGenerator.getUUID());
		workflowStep_B1.setWorkflowName(refundworkflow.getWorkflowName());
		workflowStep_B1.setWorkflowID(refundworkflow.getId());
		workflowStep_B1.setStepName("申请");
		workflowStep_B1.setStepNO(1);
		workflowStep_B1.setPreviousStepNO("0");
		workflowStep_B1.setNextStepNO("2;");
		workflowStep_B1.setCompanyName(company.getCompanyName());
		workflowStep_B1.setCompanyID(company.getId());
		workflowStep_B1.setStepType(1);
		workflowStepDAO.save(workflowStep_B1);
		//环节2
		WorkflowStep workflowStep_B2 = new WorkflowStep();
		workflowStep_B2.setId(UUIDGenerator.getUUID());
		workflowStep_B2.setWorkflowName(refundworkflow.getWorkflowName());
		workflowStep_B2.setWorkflowID(refundworkflow.getId());
		workflowStep_B2.setStepName("退票");
		workflowStep_B2.setStepNO(2);
		workflowStep_B2.setPreviousStepNO("1;");
		workflowStep_B2.setNextStepNO("99;");
		workflowStep_B2.setCompanyName(company.getCompanyName());
		workflowStep_B2.setCompanyID(company.getId());
		workflowStep_B2.setStepType(1);
		workflowStep_B2.setChooseExecutorType(2);
		workflowStep_B2.setExecutor(SysProperties.getInstance().get("travleAdminID"));
		List<WorkflowStepAction> workflowStepActions_B = new ArrayList<WorkflowStepAction>();
		WorkflowStepAction workflowStepAction_B21  = new WorkflowStepAction();
		workflowStepAction_B21.setId(UUIDGenerator.getUUID());
		workflowStepAction_B21.setOrderType("1");
		workflowStepAction_B21.setStatus(1);
		workflowStepAction_B21.setStepAction(stepActionDAO.get("7"));
		workflowStepAction_B21.setSort(max+4);
		workflowStepAction_B21.setWorkflowStep(workflowStep_B2);
		workflowStepActions_B.add(workflowStepAction_B21);
		WorkflowStepAction workflowStepAction_B22  = new WorkflowStepAction();
		workflowStepAction_B22.setId(UUIDGenerator.getUUID());
		workflowStepAction_B22.setOrderType("1");
		workflowStepAction_B22.setStatus(1);
		workflowStepAction_B22.setStepAction(stepActionDAO.get("17"));
		workflowStepAction_B22.setSort(max+5);
		workflowStepAction_B22.setWorkflowStep(workflowStep_B2);
		workflowStepActions_B.add(workflowStepAction_B22);
		workflowStep_B2.setWorkflowStepActions(workflowStepActions_B);
		workflowStepDAO.save(workflowStep_B2);
	}

	/**
	 * 
	 * @方法功能说明：修改
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:13:22
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modfiyCompany(ModifyCompanyCommand command) {
		Company company = companyDAO.get(command.getId());
		company.setCompanyName(command.getCompanyName());
		company.setDescription(command.getDescription());
		companyDAO.update(company);
	}

	@Override
	protected CompanyDAO getDao() {
		return companyDAO;
	}

}
