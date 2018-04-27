package plfx.api.client.api.v1.mp.request;

import plfx.api.client.base.slfx.ApiPayload;

@SuppressWarnings("serial")
public class BaseCommand extends ApiPayload {
	/**
	 * 操作许可令牌
	 */
	private String token;
	/**
	 * 命令id
	 */
	private String commandId;
	/**
	 *  命令来源项
	 */
	private String fromProjectId;
	/**
	 * 命令来源用户
	 */
	private String fromProjectUserId;
	/**
	 * 命令来源管理员
	 */
	private String fromAdminId;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getFromProjectId() {
		return fromProjectId;
	}
	public void setFromProjectId(String fromProjectId) {
		this.fromProjectId = fromProjectId;
	}
	public String getFromProjectUserId() {
		return fromProjectUserId;
	}
	public void setFromProjectUserId(String fromProjectUserId) {
		this.fromProjectUserId = fromProjectUserId;
	}
	public String getFromAdminId() {
		return fromAdminId;
	}
	public void setFromAdminId(String fromAdminId) {
		this.fromAdminId = fromAdminId;
	}
	
}
