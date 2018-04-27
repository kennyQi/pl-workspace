package hg.demo.member.common.spi.qo.log;

import hg.framework.common.base.BaseSPIQO;

@SuppressWarnings("serial")
public class LogSQO extends BaseSPIQO {
	/**
	 * 操作人
	 */
	private String userName;

	/**
	 * 操作
	 */
	private String update;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}


}
