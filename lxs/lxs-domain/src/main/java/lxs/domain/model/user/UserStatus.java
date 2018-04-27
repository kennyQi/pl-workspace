package lxs.domain.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lxs.domain.model.M;

import org.hibernate.annotations.Type;

@Embeddable
public class UserStatus implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * 最后登陆时间
	 */
	@Column(name="LAST_LOGIN_TIME",columnDefinition=M.DATE_COLUM)
	private Date lastLoginTime;
	
	/**
	 * 是否通过手机或邮箱激活
	 */
	@Type(type = "yes_no")
	@Column(name="ACTIVATED")
	private Boolean activated;
	
	/**
	 * 是否通过邮箱验证
	 */
	@Type(type = "yes_no")
	@Column(name="ISEMAILCHECKED")
	private Boolean isEmailChecked;
	
	/**
	 * 是否通过手机验证
	 */
	@Type(type = "yes_no")
	@Column(name="ISTELCHECKED")
	private Boolean isTelChecked;
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getIsEmailChecked() {
		return isEmailChecked;
	}

	public void setIsEmailChecked(Boolean isEmailChecked) {
		this.isEmailChecked = isEmailChecked;
	}
	
	public Boolean getIsTelChecked() {
		return isTelChecked;
	}
	public void setIsTelChecked(Boolean isTelChecked) {
		this.isTelChecked = isTelChecked;
	}
	
}
