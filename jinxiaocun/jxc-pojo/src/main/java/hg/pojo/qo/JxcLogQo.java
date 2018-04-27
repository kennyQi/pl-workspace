package hg.pojo.qo;

import hg.log.base.BaseLogQo;

public class JxcLogQo extends BaseLogQo{
	
	private String userName;

	private String name;
	
	private String userType;

	private String content;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public JxcLogQo(String userName, String name, String userType) {
		super();
		this.userName = userName;
		this.name = name;
		this.userType = userType;
	}

	public JxcLogQo() {
		super();
	}
	
}
