package zzpl.pojo.command.user;

public class ModifyCostCenterCommand {

	private String id;

	/**
	 * 公司名称
	 */
	private String costCenterName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

}
