package hg.demo.member.service.domain.manager;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.spi.command.authuser.ChangePwdCommand;
import hg.demo.member.common.spi.command.authuser.CreateAuthUserCommand;
import hg.demo.member.common.spi.command.authuser.ResetPwdCommand;
import hg.demo.member.common.spi.command.authuser.UpdateTypeCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/5/27.
 */
public class AuthUserManager extends BaseDomainManager<AuthUser> {
    public AuthUserManager(AuthUser entity) {
        super(entity);
    }

    /**
     * 创建authUser
     * @param command
     * @return
     */
    public AuthUserManager create(CreateAuthUserCommand command) {
        entity.setId(UUIDGenerator.getUUID());
        entity.setLoginName(command.getLoginName());
        entity.setDisplayName(command.getDisplayName());
        entity.setEnable(command.getEnable());
        entity.setPasswd(command.getPasswd());
        entity.setCreateDate(command.getCreateDate());
        Set<AuthRole> set=new HashSet<AuthRole>();
        AuthRole authRole=new AuthRole();
        authRole.setId(command.getRoleId());
        set.add(authRole);
        entity.setAuthRoleSet(set);
        return this;
    }

    /**
     * 更新状态
     * @param command
     * @return
     */
    public AuthUserManager updateType(UpdateTypeCommand command){
        entity.setEnable(command.getEnable());
        return this;
    }

    /**
     * 重置密码
     * @param command
     * @return
     */
    public AuthUserManager resetPwd(ResetPwdCommand command){
        entity.setPasswd(command.getPassword());
        return this;
    }

    /**
     * 修改密码
     * @param command
     * @return
     */
    public AuthUserManager changepwd(ChangePwdCommand command){
        entity.setPasswd(command.getPassword());
        return this;
    }
}
