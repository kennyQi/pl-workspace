package hsl.pojo.dto.user;
import java.util.Date;
public class UserStatusDTO {
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 是否通过手机或邮箱验证
	 */
	private Boolean activated;
	/**
	 * 是否通过邮箱验证
	 */
	private Boolean isEmailChecked;
	/**
	 * 是否通过手机验证
	 */
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

	public Boolean getIsTelChecked() {
		return isTelChecked;
	}

	public void setIsTelChecked(Boolean isTelChecked) {
		this.isTelChecked = isTelChecked;
	}

	public Boolean getIsEmailChecked() {
		return isEmailChecked;
	}

	public void setIsEmailChecked(Boolean isEmailChecked) {
		this.isEmailChecked = isEmailChecked;
	}
	
}
