package hsl.pojo.qo.account;


import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class AccountQO extends BaseQo{
	/**
	 * 当前登录用户ID
	 */
	private String userID;
	
	/**
	 * 是否加载消费订单快照
	 */
	private boolean consumeOrderSnapshots; 

	private boolean isLock;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public boolean isConsumeOrderSnapshots() {
		return consumeOrderSnapshots;
	}

	public void setConsumeOrderSnapshots(boolean consumeOrderSnapshots) {
		this.consumeOrderSnapshots = consumeOrderSnapshots;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
	}
}
