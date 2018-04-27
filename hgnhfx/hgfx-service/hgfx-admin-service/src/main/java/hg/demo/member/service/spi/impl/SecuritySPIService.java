package hg.demo.member.service.spi.impl;

import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.spi.SecuritySPI;
import hg.demo.member.common.spi.command.authuser.*;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.demo.member.common.spi.qo.Security.*;
import hg.demo.member.service.dao.hibernate.AuthPermDao;
import hg.demo.member.service.dao.hibernate.AuthRoleDao;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.domain.manager.AuthUserManager;
import hg.demo.member.service.qo.hibernate.AuthPermQO;
import hg.demo.member.service.qo.hibernate.AuthRoleQO;
import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.framework.service.component.base.BaseService;

import hg.framework.service.component.base.db.DynamicDataSource;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
@Transactional
@Service("securitySPIService")
public class SecuritySPIService extends BaseService implements SecuritySPI {

    @Autowired
    private AuthUserDAO authUserDAO;
    @Autowired
    private AuthRoleDao authRoleDao;
    @Autowired
    private AuthPermDao authPermDao;

    @Override
    public AuthUser updateAuthUser(ModifyAuthUserCommand modifyAuthUserCommand) {
        AuthUser authUser=authUserDAO.get(modifyAuthUserCommand.getId());
        authUser.setLoginName(modifyAuthUserCommand.getLoginName());
        authUser.setPasswd(modifyAuthUserCommand.getPasswd());
        authUser.setEnable(modifyAuthUserCommand.getEnable());
        authUser.setDisplayName(modifyAuthUserCommand.getDisplayName());
        Set<AuthRole> set=new HashSet<AuthRole>();
        AuthRole authRole=new AuthRole();
        authRole.setId(modifyAuthUserCommand.getRoleId());
        set.add(authRole);
        authUser.setAuthRoleSet(set);
        authUserDAO.update(authUser);
        return authUser;
    }

    @Override
    public List<AuthRole> findAllRoles(AuthRoleSQO authRoleSQO) {
        return authRoleDao.queryList(AuthRoleQO.build(authRoleSQO));
    }

    @Override
    public AuthUser queryAuthUser(QueryAuthUserSQO queryAuthUserSQO) {
        AuthUserQO authUserQO=new AuthUserQO();
        authUserQO.setLogin(queryAuthUserSQO.getLoginName());
        AuthUser authUser= authUserDAO.queryUnique(authUserQO);
        return authUser;
    }

    @Override
    public AuthUser queryAuthUserById(QueryAuthUserByIdSQO queryAuthUserByIdSQO) {
        AuthUserQO authUserQO=new AuthUserQO();
        authUserQO.setId(queryAuthUserByIdSQO.getId());
        AuthUser authUser=authUserDAO.queryUnique(authUserQO);
        return authUser;
    }

    @Override
    public List<String> findUserRoles(FindUserRolesSQO findUserRolesSQO) {
        List<String> stringList=new ArrayList<String>();
        AuthUser authUser=authUserDAO.get(findUserRolesSQO.getId());
        if (authUser!=null&&authUser.getAuthRoleSet()!=null&&authUser.getAuthRoleSet().size()>0){
            for (AuthRole authRole :authUser.getAuthRoleSet()){
                stringList.add(authRole.getRoleName());
            }
        }
        return stringList;
    }

    @Override
    public void updateenable(UpdateTypeCommand updateTypeCommand) {
        AuthUser authUser= authUserDAO.get(updateTypeCommand.getId());
        if (authUser!=null){
            authUserDAO.update(new AuthUserManager(authUser).updateType(updateTypeCommand).get());
        }
    }

    @Override
    public void resetpwd(ResetPwdCommand resetPwdCommand) {
        AuthUser authUser=authUserDAO.get(resetPwdCommand.getId());
        if (authUser!=null){
            authUserDAO.update(new AuthUserManager(authUser).resetPwd(resetPwdCommand).get());
        }
    }

    @Override
    public AuthUser changepwd(ChangePwdCommand changePwdCommand) {
        AuthUser authUser=authUserDAO.get(changePwdCommand.getId());
        if (authUser!=null){
            return authUserDAO.update(new AuthUserManager(authUser).changepwd(changePwdCommand).get());
        }
        return authUser;
    }

    @Override
    public boolean checkloginname(CheckLoginNameSQO checkLoginNameSQO) {
        AuthUserQO authUserQO=new AuthUserQO();
        authUserQO.setLoginName(checkLoginNameSQO.getLoginName());
        if (authUserDAO.queryCount(authUserQO)>0){
            return false;
        }
        return true;
    }

    @Override
    public Set<String> getuserperm(UserPermSQO userPermSQO) {
        AuthUser authUser=authUserDAO.get(userPermSQO.getId());
        Set<String> lists =new HashSet<String>();
        if (authUser!=null){
            Set<AuthRole> set= authUser.getAuthRoleSet();
            if (set!=null&&set.size()>0){
                for (AuthRole authRole :set){
                    AuthPermQO authPermQO=new AuthPermQO();
                    AuthRoleQO authRoleQO=new AuthRoleQO();
                    authRoleQO.setId(authRole.getId());
                    authPermQO.setAuthRoleSet(authRoleQO);
                    List<AuthPerm> list= authPermDao.queryList(authPermQO);
                    if (list!=null&&list.size()>0){
                        for (AuthPerm authPerm:list){
                            lists.add(authPerm.getUrl());
                        }
                    }
                }
            }
        }
        return lists;
    }

	@Override
	public boolean checkStaffName(CheckLoginNameSQO checkLoginNameSQO) {
		AuthUserQO authUserQO=new AuthUserQO();
        authUserQO.setDisplay(checkLoginNameSQO.getDisName());
        if (authUserDAO.queryCount(authUserQO)>0){
            return false;
        }
        return true;
	}
}
