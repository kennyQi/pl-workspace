package zzpl.app.service.local.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.RoleDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.user.UserRoleDAO;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.AddCompanyCommand;
import zzpl.pojo.command.user.DeleteCompanyCommand;
import zzpl.pojo.command.user.ModifyCompanyCommand;
import zzpl.pojo.dto.user.status.CompanyStatus;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.exception.user.CompanyException;
import zzpl.pojo.qo.user.CompanyQO;
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
	private UserRoleDAO userRoleDAO;

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
	 * @方法功能说明：新增
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
		authUser.setPasswd(Md5Util.MD5(command.getLoginName()));
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
		// 2.公司表
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
		// 3.用户表
		// ---------开始---------
		User user = new User();
		user.setId(id);
		user.setCompanyID(company.getId());
		user.setCompanyName(command.getCompanyName());
		user.setActivated(UserStatus.ACTIVATED);
		user.setStatus(UserStatus.NORMAL);
		userDAO.save(user);
		UserRole userRole = new UserRole();
		userRole.setId(UUIDGenerator.getUUID());
		userRole.setUser(user);
		userRole.setRole(roleDAO.get(SysProperties.getInstance().get("companyAdminID")));
		userRoleDAO.save(userRole);
		// ---------结束---------
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
