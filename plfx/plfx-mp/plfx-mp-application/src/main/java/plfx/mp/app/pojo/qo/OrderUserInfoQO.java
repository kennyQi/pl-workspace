package plfx.mp.app.pojo.qo;

import hg.common.component.BaseQo;

public class OrderUserInfoQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道用户id
	 */
	private String channelUserId;
	/**
	 * 姓名
	 */
	private String name;
	private boolean nameLike;
	/**
	 * 手机
	 */
	private String mobile;
	private boolean mobileLike;

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
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

}
