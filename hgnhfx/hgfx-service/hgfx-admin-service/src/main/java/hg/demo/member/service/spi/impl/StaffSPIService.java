package hg.demo.member.service.spi.impl;

import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.domain.viewmodel.StaffViewModel;
import hg.demo.member.common.spi.StaffSPI;
import hg.demo.member.common.spi.command.staff.CreateStaffCommand;
import hg.demo.member.common.spi.command.staff.DeleteStaffCommand;
import hg.demo.member.common.spi.command.staff.ModifyStaffCommand;
import hg.demo.member.common.spi.qo.StaffSQO;
import hg.demo.member.service.dao.hibernate.StaffDAO;
import hg.demo.member.service.domain.manager.StaffManager;
import hg.demo.member.service.qo.hibernate.StaffQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
@Transactional
@Service("staffSPIService")
public class StaffSPIService extends BaseService implements StaffSPI {

    public final static String DEFAULT_PASSWORD = "123456";
    @Autowired
    private StaffDAO staffDAO;

    @Override
    public Staff create(CreateStaffCommand command) {
        Staff staff = new Staff();
        if (StringUtils.isBlank(command.getLoginPwd()))
            command.setLoginPwd(MD5HashUtil.toMD5(DEFAULT_PASSWORD));

        command.setLoginPwd(MD5HashUtil.toMD5(command.getLoginPwd()));
        return staffDAO.save(new StaffManager(staff).createStaff(command).get());
    }

    @Override
    public Staff modify(ModifyStaffCommand command) {
        Staff staff = new Staff();
        staff = staffDAO.get(command.getUserId());
        return staffDAO.update(new StaffManager(staff).modify(command).get());
    }

    @Override
    public void delete(DeleteStaffCommand command) {
        staffDAO.deleteById(command.getId());
    }

    @Override
    public Pagination<StaffViewModel> queryStaffPagination(StaffSQO sqo) {
        Pagination<StaffViewModel> viewModelPagination=new Pagination<>();
        List<StaffViewModel> list=new ArrayList<>();
        Pagination<Staff> pagination= staffDAO.queryPagination(StaffQO.build(sqo));
        for (Staff staff:pagination.getList()){
            StaffViewModel staffViewModel=new StaffViewModel();
            staffViewModel.setStaff(staff);
            for (AuthRole authRole:staff.getAuthUser().getAuthRoleSet()){
                Set<String> set=new HashSet<String>();
                set.add(authRole.getRoleName());
                staffViewModel.setRoleset(set);
            }
            list.add(staffViewModel);
        }
        viewModelPagination.setList(list);
        return viewModelPagination;
    }

    @Override
    public Staff queryStaff(StaffSQO sqo) {

        return staffDAO.queryUnique(StaffQO.build(sqo));
    }

    @Override
	public List<Staff> queryList(StaffSQO sqo) {
		return staffDAO.queryList(StaffQO.build(sqo));
	}
}
