package hg.demo.member.service.spi.impl;

import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.domain.model.adminconfig.AdminConfig;
import hg.demo.member.common.domain.model.adminconfig.GeneralResult;
import hg.demo.member.common.spi.AdminConfigSPI;
import hg.demo.member.common.spi.command.adminconfig.ClearTestCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminAccountCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.DeleteAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.ModifyAdminConfigCommand;
import hg.demo.member.common.spi.command.authRole.CreateAuthRoleCommand;
import hg.demo.member.common.spi.command.staff.CreateStaffCommand;
import hg.demo.member.common.spi.qo.AdminConfigSQO;
import hg.demo.member.service.dao.hibernate.AdminConfigDAO;
import hg.demo.member.service.dao.hibernate.AuthPermDao;
import hg.demo.member.service.dao.hibernate.AuthRoleDao;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.StaffDAO;
import hg.demo.member.service.domain.manager.AdminConfigManager;
import hg.demo.member.service.domain.manager.AuthRoleManager;
import hg.demo.member.service.domain.manager.StaffManager;
import hg.demo.member.service.qo.hibernate.AdminConfigQO;
import hg.demo.member.service.qo.hibernate.AuthPermQO;
import hg.demo.member.service.qo.hibernate.AuthRoleQO;
import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.demo.member.service.qo.hibernate.StaffQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;

import java.io.FileWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("adminConfigSPIService")
public class AdminConfigSPIService extends BaseService implements AdminConfigSPI {

	
	//keyAdminCreated
	public static final String KEY_ADMIN_CREATED = "key_admin_created";

	//keyDbConfigOk
	public static final String KEY_DB_CONFIG_OK = "key_db_config_ok";
	
	//keyClearTestCount
	public static final String KEY_CLEAR_TEST_COUNT = "key_clear_test_count";
	
	@Autowired
	private AdminConfigDAO dao;
	
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private AuthPermDao authPermDAO;
	
	@Autowired
	private AuthUserDAO authUserDAO;
	
//	@Autowired
//	private StaffSPIService staffService;
//	@Autowired
//	private AuthRoleSPIService AuthRoleService;

	
	@Autowired
private AuthRoleDao authRoleDAO;

	/**
	 * 创建后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param command
	 *            后台系统配置创建命令
	 * @return 本次创建的后台系统配置
	 * @throws Exception 
	 */
	@Override
	public AdminConfig create(CreateAdminConfigCommand command) throws Exception {
		AdminConfigQO qo = new AdminConfigQO();
		qo.setDataKey(command.getDataKey());
		Integer count = dao.queryCount(qo );
		if (count>0) {
			throw new Exception("该配置键已经存在");
		}
		AdminConfig adminConfig = new AdminConfig();

		return dao.save(new AdminConfigManager(adminConfig).create(command).get());
	}

	/**
	 * 修改后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param command
	 *            后台系统配置修改命令
	 * @return 本次修改的后台系统配置
	 */
	@Override
	public AdminConfig modify(ModifyAdminConfigCommand command) {
		AdminConfig entity = dao.get(command.getId());
		return dao.update(new AdminConfigManager(entity).modify(command).get());
	}

	/**
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param command
	 *            后台系统配置删除命令
	 */
	@Override
	public void delete(DeleteAdminConfigCommand command) {

	}

	/**
	 * page 查询
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param sqo
	 *            查询qo
	 * @return 查询的后台系统配置
	 */
	@Override
	public Pagination<AdminConfig> queryAdminConfigPagination(AdminConfigSQO sqo) {
		return dao.queryPagination(AdminConfigQO.build(sqo));
	}

	/**
	 * 查询后台系统配置列
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param sqo
	 *            查询qo
	 * @return
	 */
	@Override
	public List<AdminConfig> queryAdminConfigList(AdminConfigSQO sqo) {
		return null;
	}

	/**
	 * 查询一个后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-service
	 * @date 2016-5-27
	 * @param sqo
	 *            查询qo
	 * @return 查询的后台系统配置
	 */
	@Override
	public AdminConfig queryAdminConfig(AdminConfigSQO sqo) {
		return null;
	}
	
	public GeneralResult createAdminAccount(CreateAdminAccountCommand command ) throws Exception {
		
		
		AdminConfigQO qo = new AdminConfigQO();
		qo.setDataKey(KEY_ADMIN_CREATED);
		AdminConfig adminAccountCreated = dao.queryUnique(qo );
		if (adminAccountCreated!=null) {
			GeneralResult gr = new GeneralResult();
			gr.setResultFalse();
			gr.setMessage("管理员账号已经初始化过");
			gr.addIntegerData("code", 1000);
			return gr ;

		}
		
		AuthUserQO authUserQO = new AuthUserQO();
		authUserQO.setLogin(command.getLoginName());
		 AuthUser authUser = authUserDAO.queryUnique(authUserQO);
		 if(authUser!=null) {
			 GeneralResult gr = new GeneralResult();
			 gr.setResultFalse();
			 gr.setMessage("该管理员登陆名已存在");
			 gr.addIntegerData("code", 1001);
			 return gr ;
			 
		 }
		
		
//		CreateAuthRoleCommand createAuthRoleCommand = new CreateAuthRoleCommand();
//		createAuthRoleCommand.setRoleName("admin");
//		createAuthRoleCommand.setDisplayName("admin");
//		AuthRole role = AuthRoleService.createAuthRole(createAuthRoleCommand);
		AuthPermQO authPermQO = new AuthPermQO();
		List<AuthPerm> authPermList = authPermDAO.queryList(authPermQO);
		
		
		
		AuthRole authRole = new AuthRole();
		Set<AuthPerm> authPermSet = new HashSet<AuthPerm>();
		authPermSet.addAll(authPermList);
		
		CreateAuthRoleCommand createAuthRoleCommand = new CreateAuthRoleCommand();
		createAuthRoleCommand.setRoleName( "admin" );
		createAuthRoleCommand.setDisplayName( "admin" );
		authRole = new AuthRoleManager(authRole).create(createAuthRoleCommand, authPermSet).get();
		authRole = authRoleDAO.save(authRole);
		
		

		
//		staffDAO.save();
		
		Staff staff = new Staff();

		CreateStaffCommand createStaffCommand = new CreateStaffCommand();
		//set...
		createStaffCommand.setRealName( command.getLoginName() );
		createStaffCommand.setLoginName( command.getLoginName() );
		createStaffCommand.setLoginPwd( MD5HashUtil.toMD5(command.getPwd()) );
		createStaffCommand.setRoleIds( authRole.getId() );
		createStaffCommand.setEmail( "" );
		createStaffCommand.setMobile( "" );
		createStaffCommand.setEnable( (short)1 );
		createStaffCommand.setTel( "" );
		createStaffCommand.setCreateTime( new Date() );

		staff = new StaffManager(staff).createStaff(createStaffCommand).get();//.create(createStaffCommand).get();
		staff = staffDAO.save(staff);
		
		
		
		
		CreateAdminConfigCommand createAdminConfigCommand = new CreateAdminConfigCommand();
		createAdminConfigCommand.setDataKey(KEY_ADMIN_CREATED);
		createAdminConfigCommand.setValue("1");
		create(createAdminConfigCommand);
		
		return GeneralResult.successResult("初始化成功，跳转到登陆");
	}
	
	public GeneralResult adminIsCreated() {
		AdminConfigQO qo = new AdminConfigQO();
		qo.setDataKey(KEY_ADMIN_CREATED);
		AdminConfig adminAccountCreated = dao.queryUnique(qo );
		if (adminAccountCreated!=null) {
			return GeneralResult.successResult("");
		}else {
			return GeneralResult.errorResult("");
			
		}
	}
	
	public GeneralResult genDbConfig(String jdbcUrl, String userName, String pwd) {
		FileWriter fw;

		String v = "jdbc_url=" + jdbcUrl + "\ndb_username=" + userName + "\ndb_password=" + pwd;
/*
		try {
			String u = this.getClass().getResource("/").toString().replace("file:", "");
			fw = new FileWriter(u + "/jdbc.properties");
			fw.write(v);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
//		System.out.println(v);
		return GeneralResult.successResult("数据库配置完成，需重启tomcat");
	}

	@Override
	public GeneralResult clearTest(ClearTestCommand command) throws Exception{
		/*AdminConfigQO adminConfigQO = new AdminConfigQO();
		adminConfigQO.setDataKey(KEY_CLEAR_TEST_COUNT);
		AdminConfig clearTestConfig = dao.queryUnique(adminConfigQO);
		if(clearTestConfig != null) {
			throw new Exception("测试数据只能清理一次");
		}
		
		

		
		
		StaffQO staffQO = new StaffQO();
		List<Staff> staffList = staffDAO.queryList(staffQO);
		for (Staff staff : staffList) {
			staffDAO.deleteById(staff.getId());
		}
		
		
		AuthRoleQO authRoleQO = new AuthRoleQO();
		List<AuthRole> authRoleList = authRoleDAO.queryList(authRoleQO);
		for (AuthRole authRole : authRoleList) {
			authRoleDAO.deleteById(authRole.getId());
		}
		
		
		AdminConfigQO qo = new AdminConfigQO();
		 List<AdminConfig> adminConfigList = dao.queryList(qo);
		 for (AdminConfig adminConfig : adminConfigList) {
			dao.delete(adminConfig);
		}
		
		
		AdminConfig adminConfig = new AdminConfig();

		CreateAdminConfigCommand createAdminConfigCommand = new CreateAdminConfigCommand();
		//set...
		createAdminConfigCommand.setValue("1" );
		createAdminConfigCommand.setDataKey(KEY_CLEAR_TEST_COUNT );
		adminConfig = new AdminConfigManager(adminConfig).create(createAdminConfigCommand).get();//.create(createAdminConfigCommand).get();
		adminConfig = dao.save(adminConfig);*/
		
		return GeneralResult.successResult("清除测试数据成功");
		
	}

	

}
