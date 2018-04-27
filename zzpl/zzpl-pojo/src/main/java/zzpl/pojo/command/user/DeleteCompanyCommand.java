package zzpl.pojo.command.user;

import zzpl.pojo.dto.user.status.CompanyStatus;
import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteCompanyCommand extends BaseCommand {

	private String companyID;

	private Integer stauts = CompanyStatus.DELETED;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

}
