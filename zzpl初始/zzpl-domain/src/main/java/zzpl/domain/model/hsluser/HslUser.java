package zzpl.domain.model.hsluser;
import hg.common.component.BaseModel;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import zzpl.domain.model.M;
@SuppressWarnings("serial")
//@Entity
//@Table(name=M.TABLE_PREFIX_HSL_USER+"USER")
public class HslUser extends BaseModel {
	
	/**
	 * 用户账户信息
	 */
	@Embedded
	private UserAuthInfo authInfo;

	/**
	 * 用户基本信息
	 */
	@Embedded
	private UserBaseInfo baseInfo;
	
	/**
	 * 用户联系方式
	 */
	@Embedded
	private UserContactInfo contactInfo;
	
	/**
	 * 用户状态
	 */
	@Embedded
	private UserStatus status;
	
	public UserAuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(UserAuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public UserBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public UserContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(UserContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
}
