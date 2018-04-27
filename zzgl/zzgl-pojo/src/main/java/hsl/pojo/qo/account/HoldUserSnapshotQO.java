package hsl.pojo.qo.account;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HoldUserSnapshotQO extends BaseQo{
private String userId;

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

}
