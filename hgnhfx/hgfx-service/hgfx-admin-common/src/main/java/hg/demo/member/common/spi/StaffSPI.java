package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.domain.viewmodel.StaffViewModel;
import hg.demo.member.common.spi.command.staff.CreateStaffCommand;
import hg.demo.member.common.spi.command.staff.DeleteStaffCommand;
import hg.demo.member.common.spi.command.staff.ModifyStaffCommand;
import hg.demo.member.common.spi.qo.StaffSQO;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;

import java.util.List;

/**
 * Created by admin on 2016/5/20.
 */
public interface StaffSPI extends BaseServiceProviderInterface {
    /**
     * 新建
     * @param command
     * @return
     */
    Staff create(CreateStaffCommand command);

    /**
     * 修改
     * @param command
     * @return
     */
    Staff modify(ModifyStaffCommand command);

    /**
     * 删除
     * @param command
     */
    void delete(DeleteStaffCommand command);

    /**
     * 分页查询
     * @param sqo
     * @return
     */
    Pagination<StaffViewModel> queryStaffPagination(StaffSQO sqo);

    /**
     * 查询用户
     * @param sqo
     * @return
     */
    Staff queryStaff(StaffSQO sqo);
    
    List<Staff> queryList(StaffSQO sqo);
}
