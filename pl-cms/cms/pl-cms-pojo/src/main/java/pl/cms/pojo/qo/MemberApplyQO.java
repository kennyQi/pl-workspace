package pl.cms.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class MemberApplyQO extends BaseQo {

	private String scenicName;

	private boolean scenicNameLike;

	private String mobile;

	private boolean mobileLike;

	private String linkMan;

	private boolean linkManLike;

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public boolean isScenicNameLike() {
		return scenicNameLike;
	}

	public void setScenicNameLike(boolean scenicNameLike) {
		this.scenicNameLike = scenicNameLike;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isMobileLike() {
		return mobileLike;
	}

	public void setMobileLike(boolean mobileLike) {
		this.mobileLike = mobileLike;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public boolean isLinkManLike() {
		return linkManLike;
	}

	public void setLinkManLike(boolean linkManLike) {
		this.linkManLike = linkManLike;
	}

}
