package zzpl.pojo.command.user;

import zzpl.pojo.dto.user.status.CompanyStatus;
import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteCostCenterCommand extends BaseCommand {

	private String costCenterID;

	private Integer stauts = CompanyStatus.DELETED;

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

}
