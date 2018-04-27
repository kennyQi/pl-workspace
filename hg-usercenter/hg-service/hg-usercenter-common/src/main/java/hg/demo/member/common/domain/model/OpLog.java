package hg.demo.member.common.domain.model;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.common.base.BaseStringIdModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 
* <p>Title: UserBaseInfo</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 上午11:55:01
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX + "LOG")
public class OpLog extends BaseStringIdModel {

	/**
	 * 日志类型：1注册， 2登录，3修改基本信息，4重置密码，5修改密码
	 */
	@Column(name = "TYPE",  length = 8)
	private Integer type;
	
	/**
	 * 
	 */
	@Column(name = "CONTENT",  length = 2000)
	private String content;

	/**
	 * 
	 */
	@Column(name = "USER_NAME",  length = 256)
	private String userName;
	
	/**
	 * 
	 */
	@Column(name = "USER_ID",  length = 128)
	private String userId;
	
	/**
	 * 操作来源平台
	 */
	@Column(name = "OPT_APP_ID",  length = 128)
	private String optAppId;
	
	/**
	 * 1成功；-1失败
	 */
	@Column(name = "STATUS",  length = 8)
	private Integer status;
	
	/**
	 * 
	 */
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;
	
	/**
	 * 
	 */
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;


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
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
