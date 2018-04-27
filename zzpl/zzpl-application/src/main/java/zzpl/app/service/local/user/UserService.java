package zzpl.app.service.local.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.saga.ResetPasswordSagaDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.DepartmentDAO;
import zzpl.app.dao.user.RoleDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.user.UserRoleDAO;
import zzpl.domain.model.saga.ResetPasswordSaga;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.AddTravleUserCommand;
import zzpl.pojo.command.user.AddUserCommand;
import zzpl.pojo.command.user.ChangePasswordCommand;
import zzpl.pojo.command.user.DeleteUserCommand;
import zzpl.pojo.command.user.ModifyTravleUserCommand;
import zzpl.pojo.command.user.ModifyUserCommand;
import zzpl.pojo.command.user.ResetPasswordCommand;
import zzpl.pojo.dto.user.UserDTO;
import zzpl.pojo.dto.user.status.UserStatus;
import zzpl.pojo.exception.user.UserException;
import zzpl.pojo.qo.user.UserQO;
import zzpl.pojo.qo.user.UserRoleQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.Md5Util;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.dao.AuthUserDao;
import hg.system.dao.StaffDao;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.model.staff.StaffBaseInfo;
import hg.system.qo.AuthUserQo;

@Service
@Transactional
public class UserService extends BaseServiceImpl<User, UserQO, UserDAO> {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DepartmentDAO departmentDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private AuthUserDao authUserDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ResetPasswordSagaDAO resetPasswordSagaDAO;

	/**
	 * 
	 * @方法功能说明：查询单条user
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午10:17:43
	 * @修改内容：
	 * @参数：@param userQO
	 * @参数：@return
	 * @return:UserDTO
	 * @throws
	 */
	public UserDTO queryUser(UserQO userQO) {
		User user = userDAO.queryUnique(userQO);
		UserDTO userDTO = BeanMapperUtils.getMapper().map(user, UserDTO.class);
		return userDTO;
	}

	/**
	 * 
	 * @方法功能说明：查询多条user
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午10:23:10
	 * @修改内容：
	 * @参数：@param userQO
	 * @参数：@return
	 * @return:List<UserDTO>
	 * @throws
	 */
	public List<UserDTO> queryUserList(UserQO userQO) {
		List<User> users = userDAO.queryList(userQO);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User user : users) {
			if (StringUtils.isNotBlank(user.getIdCardNO())||StringUtils.isNotBlank(user.getLinkMobile())) {
				UserDTO userDTO = BeanMapperUtils.getMapper().map(user,
						UserDTO.class);
				userDTOs.add(userDTO);
			}
		}
		return userDTOs;
	}
	
	/**
	 * @throws UserException 
	 * @Title: addUser 
	 * @author guok
	 * @Description: 添加人员
	 * @Time 2015年6月26日 16:40:48
	 * @param command void 设定文件
	 * @throws
	 */
	public void addUser(AddUserCommand command) throws UserException {
		HgLogger.getInstance().info("cs", "【UserService】【addUser】"+JSON.toJSONString(command));
		//根据部门ID查询部门
		Department department = new Department();
		if (StringUtils.isNotBlank(command.getDepartmentID())) {
			department=departmentDAO.get(command.getDepartmentID());
			if (department==null) {
				throw new UserException(UserException.DEPARMENT_NOT);
			}
		}
		
		Company company = new Company();
		if (StringUtils.isNotBlank(command.getCompanyID())) {
			company = companyDAO.get(command.getCompanyID());
			if (company==null) {
				throw new UserException(UserException.COMPANY_NOT);
			}
		}
		
		String adminID = SysProperties.getInstance().get("adminID");
		String travleAdminID = SysProperties.getInstance().get("travleAdminID");
		boolean flag = false;
		//修改时间2015年7月6日 14:40:29
		// 校验登录名是否存在
		UserQO userQO = new UserQO();
		userQO.setLoginName(Md5Util.MD5(command.getLoginName()));
		userQO.setCompanyID(company.getId());
		if (userDAO.queryCount(userQO) != 0)
			throw new UserException(UserException.LOGIN_NAME_REPEAT);
		flag = true;
		//判断登录名与差旅，平台管理员的是否相同
		userQO.setCompanyID(null);
		if (userDAO.queryCount(userQO) == 1) {
			User user1 = userDAO.queryUnique(userQO);
			Hibernate.initialize(user1.getUserRoles());
			for (UserRole userRole : user1.getUserRoles()) {
				if (StringUtils.equals(userRole.getRole().getId(), adminID)||StringUtils.equals(userRole.getRole().getId(), travleAdminID)) {
					flag = false;
					break;
				}
				flag = true;
			}
		}
		
		if (!flag) {
			throw new UserException(UserException.LOGIN_NAME_REPEAT);
		}
		
		//校验编号是否存在
		userQO = new UserQO();
		userQO.setUserNo(command.getUserNO());
		userQO.setCompanyID(company.getId());
		if (userDAO.queryCount(userQO) != 0)
			throw new UserException(UserException.USER_NO_REPEAT);
		
		//校验身份证是否存在
		userQO = new UserQO();
		userQO.setIdCardNO(command.getIdCardNO());
		userQO.setCompanyID(company.getId());
		if (userDAO.queryCount(userQO) != 0)
			throw new UserException(UserException.USER_IDCARDNO_REPEAT);
		
		//计算user中相同登录名数量
		UserQO userQO1 = new UserQO();
		userQO1.setShowLoginName(command.getLoginName());
		int count = userDAO.queryCount(userQO1);
		//生成统一主键
		String userID=UUIDGenerator.getUUID();
		
		//1.登陆表添加
		AuthUser authUser = new AuthUser();
		authUser.setId(userID);
		if (count>0) {
			count += 1;
			authUser.setLoginName(command.getLoginName()+count);
		}else {
			authUser.setLoginName(command.getLoginName());
		}
		authUser.setPasswd(Md5Util.MD5(Md5Util.MD5(command.getLoginName())));
		authUser.setDisplayName(company.getCompanyName() + "成员");
		authUser.setCreateDate(new Date());
		authUserDao.save(authUser);
		Staff staff = new Staff();
		staff.setId(userID);
		staff.setAuthUser(authUser);
		StaffBaseInfo staffBaseInfo = new StaffBaseInfo();
		staffBaseInfo.setRealName(company.getCompanyName() + "成员");
		staff.setInfo(staffBaseInfo);
		staffDao.save(staff);
		//2.插入人员
		User user=new User();
		if(command.getBuyOthers()!=null){
			user.setBuyOthers(1);
		}
		user.setId(userID);
		user.setLoginName(Md5Util.MD5(command.getLoginName()));//登录名
		user.setShowLoginName(command.getLoginName());
		user.setPassword(authUser.getPasswd());//密码
		user.setUserNO(command.getUserNO());
		user.setName(command.getName());
		user.setGender(command.getGender());
		user.setIdCardNO(command.getIdCardNO());
		user.setLinkEmail(command.getLinkEmail());
		user.setLinkMobile(command.getLinkMobile());
		user.setBirthday(command.getBirthday());
		user.setCreateTime(new Date());
		user.setStatus(UserStatus.NORMAL);
		user.setActivated(UserStatus.ACTIVATED);
		user.setProvinceID(command.getProvinceID());
		user.setCityID(command.getCityID());
		//公司信息
		user.setDepartmentID(command.getDepartmentID());
		if (department!=null) {
			user.setDepartmentName(department.getDepartmentName());
		}
		user.setCompanyID(company.getId());
		user.setCompanyName(company.getCompanyName());
		user.setUserNO(command.getUserNO());
		
		userDAO.save(user);
		HgLogger.getInstance().info("cs", "【UserService】【addUser】"+JSON.toJSONString(user));
		//3.用户角色中间表保存
		if (command.getRoleID()!=null&&command.getRoleID().length>0) {
			for (String roleId : command.getRoleID()) {
				UserRole baseRole = new UserRole();
				baseRole.setId(UUIDGenerator.getUUID());
				baseRole.setUser(user);
				baseRole.setRole(roleDAO.get(roleId));
				userRoleDAO.save(baseRole);
			}
		}
		
	}
	
	/**
	 * 
	 * @Title: modfiyUser 
	 * @author guok
	 * @Description: 修改
	 * @修改时间 2015年7月7日 09:00:35
	 * @param command void 设定文件
	 * @throws
	 */
	public void modfiyUser(ModifyUserCommand command) {
		HgLogger.getInstance().info("cs", "【UserService】【modfiyUser】"+JSON.toJSONString(command));
		//根据部门ID查询部门
		Department department=departmentDAO.get(command.getDepartmentID());
		User user=userDAO.get(command.getUserID());
		//现有用户角色缓存
		Hibernate.initialize(user.getUserRoles());
		if(command.getBuyOthers()!=null){
			if(StringUtils.equals(command.getBuyOthers()[0], "0")){
				user.setBuyOthers(0);				
			}else{
				user.setBuyOthers(1);
			}
		}else{
			if(user.getBuyOthers()!=null){
				user.setBuyOthers(user.getBuyOthers());
			}else{
				user.setBuyOthers(0);
			}
		}
		if(department!=null&&StringUtils.isNotBlank(department.getId())){
			user.setDepartmentID(department.getId());
		}
		if(command.getBirthday()!=null){
			user.setBirthday(command.getBirthday());
		}
		if(department!=null&&StringUtils.isNotBlank(department.getDepartmentName())){
			user.setDepartmentName(department.getDepartmentName());
		}
		if(command.getGender()!=null){
			user.setGender(command.getGender());
		}
		if(StringUtils.isNotBlank(command.getIdCardNO())){
			user.setIdCardNO(command.getIdCardNO());
		}
		if(StringUtils.isNotBlank(command.getUserNO())){
			user.setUserNO(command.getUserNO());
		}
		if(StringUtils.isNotBlank(command.getLinkEmail())){
			user.setLinkEmail(command.getLinkEmail());
		}
		if(StringUtils.isNotBlank(command.getLinkMobile())){
			user.setLinkMobile(command.getLinkMobile());
		}
		if(StringUtils.isNotBlank(command.getName())){
			user.setName(command.getName());
		}
		if(StringUtils.isNotBlank(command.getProvinceID())){
			user.setProvinceID(command.getProvinceID());
		}
		if(StringUtils.isNotBlank(command.getCityID())){
			user.setCityID(command.getCityID());
		}
		if(StringUtils.isNotBlank(command.getImage())){
			user.setImage(command.getImage());
		}
		if(StringUtils.isNotBlank(command.getImageID())){
			user.setImageID(command.getImageID());
		}
		user.setUserRoles(new ArrayList<UserRole>());
		userDAO.update(user);
		
		//修改角色
		//1.删除原有角色关系
		if(command.getRoleID()!=null){
			UserRoleQO userRoleQO = new UserRoleQO();
			userRoleQO.setUserID(user.getId());
			List<UserRole> userRoles = userRoleDAO.queryList(userRoleQO);
			for (UserRole userRole : userRoles) {
				userRole.setRole(new Role());
				userRoleDAO.update(userRole);
				
				userRoleDAO.delete(userRole);
			}
			//2.新建角色关系
			for (String roleID : command.getRoleID()) {
				Role role = roleDAO.get(roleID);
				UserRole userRole = new UserRole();
				userRole.setId(UUIDGenerator.getUUID());
				userRole.setRole(role);
				userRole.setUser(user);
				userRoleDAO.save(userRole);
			}
				
		}
		
	}
	
	/**
	 * @Title: deleteUser 
	 * @author guok
	 * @Description: 删除人员
	 * @time 2015年7月7日 09:04:40
	 * @param command
	 * @throws UserException void 设定文件
	 * @throws
	 */
	public void deleteUser(DeleteUserCommand command) throws UserException {
		HgLogger.getInstance().info("cs", "【UserService】【deleteUser】"+JSON.toJSONString(command));
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser authUser = (AuthUser)currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if (StringUtils.equals(command.getUserID(), authUser.getId())) {
			throw new UserException(UserException.ILLEGAL_HANDLE);
		}
		User user=userDAO.get(command.getUserID());
		user.setStatus(UserStatus.DELETED);
		userDAO.update(user);
	}
	
	/**
	 * 
	 * @方法功能说明：修改密码
	 * @修改者名字：cangs
	 * @修改时间：2015年7月30日下午4:00:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean changePassword(ChangePasswordCommand command){
		User user = userDAO.get(command.getUserID());
		if(StringUtils.equals(Md5Util.MD5(command.getPassword()), user.getPassword())){
			user.setPassword(Md5Util.MD5(command.getNewPassword()));
			userDAO.update(user);
			return true;
		}else{
			return false; 
		}
	}
	
	public void resetPassword(ResetPasswordCommand command){
		ResetPasswordSaga resetPasswordSaga = resetPasswordSagaDAO.get(command.getSagaID());
		User user = userDAO.get(resetPasswordSaga.getUserID());
		user.setPassword(Md5Util.MD5(command.getNewPassword()));
		AuthUser authUser = authUserDao.get(resetPasswordSaga.getUserID());
		authUser.setPasswd(Md5Util.MD5(command.getNewPassword()));
		authUserDao.update(authUser);
	}
	/**
	 * @Title: getById 
	 * @author guok
	 * @Description: 根据用户ID查询用户并且缓存用户角色
	 * @param id
	 * @return User 设定文件
	 * @throws
	 */
	public User getById(String id) {
		User user=userDAO.get(id);
		Hibernate.initialize(user.getUserRoles());
		return user;
	}
	
	/**
	 * @Title: travleAdd 
	 * @author guok
	 * @Description: 差旅管理员添加
	 * @Time 2015年8月4日下午3:56:49
	 * @param command
	 * @return
	 * @throws UserException User 设定文件
	 * @throws
	 */
	public User travleAdd(AddTravleUserCommand command) throws UserException {
		HgLogger.getInstance().info("gk", "【UserService】【travleAdd】"+JSON.toJSONString(command));
		// 校验登录名是否存在
		AuthUserQo authUserQo = new AuthUserQo();
		authUserQo.setLoginName(command.getLoginName());
		authUserQo.setLoginNameLike(false);
		if (authUserDao.queryCount(authUserQo) != 0)
			throw new UserException(UserException.LOGIN_NAME_REPEAT);
		
		//生成统一主键
		String userID=UUIDGenerator.getUUID();
		
		//1.登陆表添加
		AuthUser authUser = new AuthUser();
		authUser.setId(userID);
		authUser.setLoginName(command.getLoginName());
		authUser.setPasswd(Md5Util.MD5(Md5Util.MD5(command.getLoginName())));
		authUser.setDisplayName("差旅管理员");
		authUser.setCreateDate(new Date());
		authUserDao.save(authUser);
		Staff staff = new Staff();
		staff.setId(userID);
		staff.setAuthUser(authUser);
		StaffBaseInfo staffBaseInfo = new StaffBaseInfo();
		staffBaseInfo.setRealName("差旅管理员");
		staff.setInfo(staffBaseInfo);
		staffDao.save(staff);
		//2.插入人员
		User user=new User();
		user.setId(userID);
		user.setLoginName(Md5Util.MD5(command.getLoginName()));//登录名
		user.setShowLoginName(command.getLoginName());
		user.setPassword(authUser.getPasswd());//密码
		user.setName(command.getName());
		user.setUserNO(command.getUserNO());
		user.setGender(command.getGender());
		user.setIdCardNO(command.getIdCardNO());
		user.setLinkEmail(command.getLinkEmail());
		user.setLinkMobile(command.getLinkMobile());
		user.setBirthday(command.getBirthday());
		user.setCreateTime(new Date());
		user.setStatus(UserStatus.NORMAL);
		user.setActivated(UserStatus.ACTIVATED);
		//查看是否选择公司
		if (StringUtils.isNotBlank(command.getCompanyID())){
			user.setCompanyID(command.getCompanyID());
			//截取公司ID查询公司名称
			String[] companyID = user.getCompanyID().split(",");
			String companyName = "";
			for (String string : companyID) {
				if (companyID.length>1) {
					Company company = companyDAO.get(string);
					companyName += company.getCompanyName()+",";
				}else {
					Company company = companyDAO.get(string);
					companyName += company.getCompanyName();
					user.setCompanyID(user.getCompanyID()+",");
				}
			}
			user.setCompanyName(companyName);
		}
		
		userDAO.save(user);
		String roleID = SysProperties.getInstance().get("travleAdminID");
		Role role = roleDAO.get(roleID);
		UserRole userRole = new UserRole();
		userRole.setId(UUIDGenerator.getUUID());
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleDAO.save(userRole);
		
		return user;
	}

	/**
	 * @Title: modfiyTravle 
	 * @author guok
	 * @Description: 修改差旅管理员
	 * @Time 2015年8月5日上午9:38:54
	 * @param command void 设定文件
	 * @throws
	 */
	public void modfiyTravle(ModifyTravleUserCommand command) {
		HgLogger.getInstance().info("gk", "【UserService】【modfiyTravle】"+JSON.toJSONString(command));
		User user=userDAO.get(command.getUserID());
		//现有用户角色缓存
		Hibernate.initialize(user.getUserRoles());
		user.setUserNO(command.getUserNO());
		user.setBirthday(command.getBirthday());
		user.setGender(command.getGender());
		user.setIdCardNO(command.getIdCardNO());
		user.setLinkEmail(command.getLinkEmail());
		user.setLinkMobile(command.getLinkMobile());
		user.setName(command.getName());
		user.setCompanyID(command.getCompanyID());
		if (StringUtils.isNotBlank(command.getCompanyID())) {
			//截取公司ID查询公司名称
			String[] companyID = user.getCompanyID().split(",");
			String companyName = "";
			for (String string : companyID) {
				if (companyID.length>1) {
					Company company = companyDAO.get(string);
					companyName += company.getCompanyName()+",";
				}else {
					Company company = companyDAO.get(string);
					companyName += company.getCompanyName();
					user.setCompanyID(user.getCompanyID()+",");
				}
			}
			user.setCompanyName(companyName);
		}
		
		userDAO.update(user);
		
	}
	
	/**
	 * @Title: addUsers 
	 * @author guok
	 * @Description: 添加人员列表
	 * @Time 2015年8月18日下午2:43:15
	 * @param commands
	 * @throws UserException void 设定文件
	 * @throws
	 */
	public void addUsers(List<AddUserCommand> commands) throws UserException {
		HgLogger.getInstance().info("gk", "【UserService】【modfiyTravle】"+JSON.toJSONString(commands));
		for (AddUserCommand addUserCommand : commands) {
			addUser(addUserCommand);
		}
	}
	
	/**
	 * @throws UserException 
	 * 
	 * @方法功能说明：修改密码
	 * @修改者名字：guok
	 * @修改时间：2015年8月18日 14:44:57
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public void editPassword(ChangePasswordCommand command) throws UserException{
		User user = userDAO.get(command.getUserID());
		AuthUser authUser = authUserDao.get(command.getUserID());
		String password = Md5Util.MD5(Md5Util.MD5(command.getPassword()));
		if(StringUtils.equals(password, authUser.getPasswd())){
			user.setPassword(Md5Util.MD5(Md5Util.MD5(command.getNewPassword())));
			userDAO.update(user);
			authUser.setPasswd(Md5Util.MD5(Md5Util.MD5(command.getNewPassword())));
			authUserDao.update(authUser);
		}else{
			throw new UserException(UserException.USER_PASSWORD_ERROR);
		}
	}
	
	@Override
	protected UserDAO getDao() {
		return userDAO;
	}
	
}
