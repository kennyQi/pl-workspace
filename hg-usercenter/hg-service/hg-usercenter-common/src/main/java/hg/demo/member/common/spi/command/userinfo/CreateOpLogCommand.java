package hg.demo.member.common.spi.command.userinfo;

import hg.framework.common.base.BaseSPICommand;

/**
 * 
* <p>Title: SaveUserInfoCommand</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月28日 上午9:11:18
 */
@SuppressWarnings("serial")
public class CreateOpLogCommand extends BaseSPICommand {

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 日志类型：1注册， 2登录，3修改基本信息，4重置密码，5修改密码
	 */
	private Integer type;
	
	/**
	 * 
	 */
	private String content;

	/**
	 * 
	 */
	private String userName;
	
	/**
	 * 
	 */
	private String userId;
	
	/**
	 * 操作来源平台
	 */
	private String optAppId;
	
	/**
	 * 1成功；-1失败
	 */
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOptAppId() {
		return optAppId;
	}

	public void setOptAppId(String optAppId) {
		this.optAppId = optAppId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
