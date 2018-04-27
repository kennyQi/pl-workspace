package hg.demo.member.service.domain.manager;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.domain.model.StaffBaseInfo;
import hg.demo.member.common.spi.command.staff.CreateStaffCommand;
import hg.demo.member.common.spi.command.staff.ModifyStaffCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.base.BaseModel;
import hg.framework.common.util.UUIDGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/5/23.
 */
public class StaffManager extends BaseDomainManager<Staff> {
    public StaffManager(Staff entity) {
        super(entity);
    }

    public StaffManager create(CreateStaffCommand command, AuthUser authUser){
        entity.setId(authUser.getId());
        StaffBaseInfo staffBaseInfo=new StaffBaseInfo();
        staffBaseInfo.setMobile(command.getMobile());
        staffBaseInfo.setEmail(command.getEmail());
        staffBaseInfo.setRealName(command.getRealName());
        staffBaseInfo.setTel(command.getTel());
        entity.setStaffBaseInfo(staffBaseInfo);
        entity.setAuthUser(authUser);
        return this;
    }

    /**
     * 创建staff
     * @param command
     * @return
     */
    public StaffManager createStaff(CreateStaffCommand command){
        entity.setId(UUIDGenerator.getUUID());
        StaffBaseInfo staffBaseInfo=new StaffBaseInfo();
        staffBaseInfo.setMobile(command.getMobile());
        staffBaseInfo.setEmail(command.getEmail());
        staffBaseInfo.setRealName(command.getRealName());
        staffBaseInfo.setTel(command.getTel());
        entity.setStaffBaseInfo(staffBaseInfo);

        AuthUser authUser=new AuthUser();
        authUser.setId(entity.getId());
        authUser.setLoginName(command.getLoginName());
        authUser.setDisplayName(command.getRealName());
        authUser.setEnable(command.getEnable());
        authUser.setPasswd(command.getLoginPwd());
        authUser.setCreateDate(command.getCreateTime());
        Set<AuthRole> set=new HashSet<AuthRole>();
        AuthRole authRole=new AuthRole();
        authRole.setId(command.getRoleIds());
        set.add(authRole);
        authUser.setAuthRoleSet(set);

        entity.setAuthUser(authUser);
        return this;
    }

    /**
     * 更新staff
     * @param command
     * @return
     */
    public StaffManager modify(ModifyStaffCommand command){
        entity.getAuthUser().setDisplayName(command.getRealName());
        Set<AuthRole> set = new HashSet<AuthRole>();
        AuthRole authRole = new AuthRole();
        authRole.setId(command.getRoleIds());
        set.add(authRole);
        entity.getAuthUser().setAuthRoleSet(set);
        entity.getStaffBaseInfo().setRealName(command.getRealName());
        entity.getStaffBaseInfo().setEmail(command.getEmail());
        entity.getStaffBaseInfo().setTel(command.getTel());
        entity.getStaffBaseInfo().setMobile(command.getMobile());
        return this;
    }
}
