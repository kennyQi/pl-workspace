package pl.cms.pojo.command.apply;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CreateMemberApplyCommand extends BaseCommand {

	private String scenicName;

	private String mobile;

	private String linkMan;

	private String remark;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

}
