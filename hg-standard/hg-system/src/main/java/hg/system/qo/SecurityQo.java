package hg.system.qo;

/**
 * 查询权限相关的对象
 * @author zhurz
 */
public class SecurityQo extends BaseAuthQo {
	
	private String loginName;
	private String permDisplayName;

	//--------------LIKE--------------
	private String loginNameLike;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginNameLike() {
		loginNameLike="%"+loginName+"%";
		return loginNameLike;
	}

	public void setLoginNameLike(String loginNameLike) {
		this.loginNameLike = loginNameLike;
	}

	public String getPermDisplayName() {
		return permDisplayName;
	}

	public void setPermDisplayName(String permDisplayName) {
		this.permDisplayName = permDisplayName;
	}
	
}
