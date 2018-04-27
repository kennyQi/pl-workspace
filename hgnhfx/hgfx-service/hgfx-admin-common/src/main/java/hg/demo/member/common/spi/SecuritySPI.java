package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.spi.command.authuser.*;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.demo.member.common.spi.qo.Security.*;
import hg.framework.common.base.BaseServiceProviderInterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
public interface SecuritySPI extends BaseServiceProviderInterface {
    /**
     * 更新用户
     * @param modifyAuthUserCommand
     * @return
     */
    AuthUser updateAuthUser(ModifyAuthUserCommand modifyAuthUserCommand);

    /**
     * 查找所有角色
     * @return
     */
    List<AuthRole> findAllRoles(AuthRoleSQO authRoleSQO);

    /**
     * 查询AuthUser
     * @param queryAuthUserSQO
     * @return
     */
    AuthUser queryAuthUser(QueryAuthUserSQO queryAuthUserSQO);

    /**
     * 根据id查询AuthUser
     * @return
     */
    AuthUser queryAuthUserById(QueryAuthUserByIdSQO queryAuthUserByIdSQO);

    /**
     * 查询用户的角色列表
     * @param findUserRolesSQO
     * @return
     */
    List<String> findUserRoles(FindUserRolesSQO findUserRolesSQO);

    /**
     * 更新用户状态
     * @param updateTypeCommand
     */
    void updateenable(UpdateTypeCommand updateTypeCommand);

    /**
     * 重置密码
     * @param resetPwdCommand
     */
    void resetpwd(ResetPwdCommand resetPwdCommand);

    /**
     * 修改密码
     * @param changePwdCommand
     */
    AuthUser changepwd(ChangePwdCommand changePwdCommand);

    /**
     * 检查登录名
     * @param checkLoginNameSQO
     * @return
     */
    boolean checkloginname(CheckLoginNameSQO checkLoginNameSQO);
    
    
    /**
     * 检查操作员显示名
     * @param checkLoginNameSQO
     * @return
     */
    boolean checkStaffName(CheckLoginNameSQO checkLoginNameSQO);


    /**
     * 用户角色资源列表
     * @param userPermSQO
     * @return
     */
    Set<String> getuserperm(UserPermSQO userPermSQO);
}
