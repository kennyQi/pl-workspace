package zzpl.pojo.command.user;

import zzpl.pojo.dto.user.status.DepartmentStatus;
import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteDepartmentCommand extends BaseCommand {

	private String departmentID;
	
	private Integer stauts=DepartmentStatus.DELETED;
	
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	
}
