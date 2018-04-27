package hg.demo.member.common.domain.viewmodel;

import hg.demo.member.common.domain.model.Staff;
import hg.framework.common.base.BaseObject;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by admin on 2016/6/6.
 */
public class StaffViewModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * 权限集合
     */
    private Set<String> roleset;
    /**
     * 员工信息
     */
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Set<String> getRoleset() {
        return roleset;
    }

    public void setRoleset(Set<String> roleset) {
        this.roleset = roleset;
    }
}
