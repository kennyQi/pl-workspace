package hg.fx.command.operationLog;

import java.util.Date;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseSPICommand;

public class CreateOperationLogCommand extends BaseSPICommand {
	
	private static final long serialVersionUID = 1L;
	
	private String id; // 主键
	 
	private Date createDate; // 创建时间
	
	private String content; // 日志内容
	
	private Integer type; // 日志类型
	
	private AuthUser authUser; // 操作人 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}
	
}
