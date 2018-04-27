package hg.common.component;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 命令基类
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseCommand implements Serializable {

	/**
	 * 命令id
	 */
	private String commandId;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 命令处理结束时间
	 */
	private Date finishDate;

	/**
	 * 操作许可令牌
	 */
	private String token;

	/**
	 * 命令来源项目
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

	/**
	 * 来源命令
	 * （来源命令粒度大时派生成粒度小的命令去执行）
	 */
	private Object fromCommand;

	public String toJson() {
		return JSON.toJSONString(this);
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public Object getFromCommand() {
		return fromCommand;
	}

	public void setFromCommand(Object fromCommand) {
		this.fromCommand = fromCommand;
	}

}
