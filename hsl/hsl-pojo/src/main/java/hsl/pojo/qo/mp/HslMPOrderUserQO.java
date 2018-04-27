package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslMPOrderUserQO extends BaseQo{

	private String userId;
	
	private String name;
	
	private boolean onlyMember;

	public boolean isOnlyMember() {
		return onlyMember;
	}

	public void setOnlyMember(boolean onlyMember) {
		this.onlyMember = onlyMember;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
