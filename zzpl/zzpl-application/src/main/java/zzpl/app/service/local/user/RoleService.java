package zzpl.app.service.local.user;

import java.util.*;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.MenuDAO;
import zzpl.app.dao.user.RoleDAO;
import zzpl.app.dao.user.RoleMenuDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.user.UserRoleDAO;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.Menu;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.RoleMenu;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.AddRoleCommand;
import zzpl.pojo.command.user.DeleteRoleCommand;
import zzpl.pojo.command.user.ModifyRoleCommand;
import zzpl.pojo.qo.user.RoleMenuQO;
import zzpl.pojo.qo.user.RoleQO;
import zzpl.pojo.qo.user.UserRoleQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, RoleQO, RoleDAO> {

	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private RoleMenuDAO roleMenuDAO;
	@Autowired
	private MenuDAO menuDAO;
	
	/**
	 * @Title: deleteRole 
	 * @author guok
	 * @Description: 删除角色
	 * @param command void 设定文件
	 * @throws
	 */
	public void deleteRole(DeleteRoleCommand command) {
		HgLogger.getInstance().info("cs", "【RoleService】【deleteRole】"+JSON.toJSONString(command));
		//先删除与角色关联表的数据
		//1.用户角色中间表
		UserRoleQO userRoleQo=new UserRoleQO();
		userRoleQo.setRoleID(command.getRoleID());
		List<UserRole> userRoleList=userRoleDAO.queryList(userRoleQo);
		if(userRoleList.size()>0){
			for (UserRole userRole : userRoleList) {
				userRoleDAO.deleteById(userRole.getId());
			}
		}
		//2.角色菜单中间表
		RoleMenuQO roleMenuQo=new RoleMenuQO();
		roleMenuQo.setRoleID(command.getRoleID());
		List<RoleMenu> roleMenuList=roleMenuDAO.queryList(roleMenuQo);
		if(roleMenuList.size()>0){
			for (RoleMenu roleMenu : roleMenuList) {
				roleMenuDAO.deleteById(roleMenu.getId());
			}
		}
		//最后删除角色表
		roleDAO.deleteById(command.getRoleID());
	}
	
	/**
	 * @Title: addRole 
	 * @author guok
	 * @Description: 添加角色
	 * @time 2015年6月26日 10:15:06
	 * @param command void 设定文件
	 * @throws
	 */
	public void addRole(AddRoleCommand command) {
		HgLogger.getInstance().info("cs", "【RoleService】【addRole】"+JSON.toJSONString(command));
		//根据用户查询企业ID
		User user=userDAO.get(command.getUserID());
		if(user!=null){
			//根据企业ID查询企业
			Company company=companyDAO.get(user.getCompanyID());
			if(company!=null){
				Role role=new Role();
				role.setId(UUIDGenerator.getUUID());
				role.setRoleName(command.getRoleName());
				role.setDescription(command.getDescription());
				role.setCreateTime(new Date());
				role.setCompany(company);
				role.setSort(roleDAO.maxProperty("sort", new RoleQO())+1);
				roleDAO.save(role);
				if(command.getMenuID()!=null){
					for (String menuId : command.getMenuID()) {
						Menu menu=menuDAO.get(menuId);
						RoleMenu roleMenu=new RoleMenu();
						roleMenu.setId(UUIDGenerator.getUUID());
						roleMenu.setMenu(menu);
						roleMenu.setRole(role);
						roleMenuDAO.save(roleMenu);
					}
				}
			}
		}
	}
	
	/**
	 * @Title: modfiyRole 
	 * @author guok
	 * @Description: 修改
	 * @Time 2015年6月26日 10:15:26
	 * @param command void 设定文件
	 * @throws
	 */
	public void modfiyRole(ModifyRoleCommand command) {
		HgLogger.getInstance().info("cs", "【RoleService】【modfiyRole】"+JSON.toJSONString(command));
		Role role=roleDAO.get(command.getRoleID());
		if(role!=null){
			role.setRoleName(command.getRoleName());
			role.setDescription(command.getDescription());
			roleDAO.update(role);
			//修改角色绑定的菜单
			if(command.getMenuID()!=null&&command.getMenuID().length>0){
				RoleMenuQO qo=new RoleMenuQO();
				qo.setRoleID(command.getRoleID());
				List<RoleMenu> roleMenuList=roleMenuDAO.queryList(qo);
				for (RoleMenu roleMenu : roleMenuList) {
					roleMenuDAO.delete(roleMenu);
				}
				for (String menuId : command.getMenuID()) {
					Menu menu=menuDAO.get(menuId);
					RoleMenu roleMenu=new RoleMenu();
					roleMenu.setId(UUIDGenerator.getUUID());
					roleMenu.setMenu(menu);
					roleMenu.setRole(role);
					roleMenuDAO.save(roleMenu);
				}
			}else {
				RoleMenuQO qo=new RoleMenuQO();
				qo.setRoleID(command.getRoleID());
				List<RoleMenu> roleMenuList=roleMenuDAO.queryList(qo);
				for (RoleMenu roleMenu : roleMenuList) {
					roleMenuDAO.delete(roleMenu);
				}
			}
		}
	}
	
	/**
	 * @Title: getById 
	 * @author guok
	 * @Description: 根据角色ID查询角色，并且缓存角色菜单中间表
	 * @param roleId
	 * @return Role 设定文件
	 * @throws
	 */
	public Role getById(String roleId) {
		Role role=roleDAO.get(roleId);
		Hibernate.initialize(role.getRoleMenus());
		return role;
	}

	@Override
	protected RoleDAO getDao() {
		return roleDAO;
	}

}
