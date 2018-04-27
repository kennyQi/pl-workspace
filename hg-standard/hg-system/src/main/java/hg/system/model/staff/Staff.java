package hg.system.model.staff;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.staff.CreateAuthStaffCommand;
import hg.system.command.staff.ModifyAuthStaffCommand;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.exception.HGException;
import hg.system.model.M;
import hg.system.model.auth.AuthUser;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = M.TABLE_PREFIX_SYS + "STAFF")
public class Staff extends BaseModel {
	private static final long serialVersionUID = 1L;

	public final static String DEFAULT_PASSWORD = "123456";

	/**
	 * 员工权限帐号
	 */
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@PrimaryKeyJoinColumn
	private AuthUser authUser;

	/**
	 * 员工基本信息
	 */
	private StaffBaseInfo info;

	/**
	 * 登录错误次数
	 */
	@Transient
	private Integer pwdErrorNum = 0;

	/**
	 * 最后登录错误时间（毫秒数）
	 */
	@Transient
	private Long lastErrorTime;

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public StaffBaseInfo getInfo() {
		if (info == null) {
			info = new StaffBaseInfo();
		}
		return info;
	}

	public void setInfo(StaffBaseInfo info) {
		this.info = info;
	}

	public Integer getPwdErrorNum() {
		return pwdErrorNum;
	}

	public void setPwdErrorNum(Integer pwdErrorNum) {
		this.pwdErrorNum = pwdErrorNum;
	}

	public Long getLastErrorTime() {
		return lastErrorTime;
	}

	public void setLastErrorTime(Long lastErrorTime) {
		this.lastErrorTime = lastErrorTime;
	}

	public void createStaff(CreateAuthStaffCommand command) throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getRealName()))
			requiredName.append("【姓名】");
		if (StringUtils.isBlank(command.getLoginName()))
			requiredName.append("【登录名】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.STAFF_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		AuthUser authUser = new AuthUser();
		StaffBaseInfo info = new StaffBaseInfo();

		authUser.setPasswd(MD5HashUtil.toMD5(DEFAULT_PASSWORD));
		authUser.setEnable(SecurityConstants.USER_ENABLE);
		authUser.setLoginName(command.getLoginName());
		authUser.setId(UUIDGenerator.getUUID());

		info.setEmail(command.getEmail());
		info.setMobile(command.getMobile());
		info.setRealName(command.getRealName());
		info.setTel(command.getTel());

		setInfo(info);
		setAuthUser(authUser);
		setId(authUser.getId());
	}

	public void modifyStaff(ModifyAuthStaffCommand command) throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getRealName()))
			requiredName.append("【姓名】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.STAFF_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		StaffBaseInfo info = getInfo();

		if (info != null) {
			info.setEmail(command.getEmail());
			info.setMobile(command.getMobile());
			info.setRealName(command.getRealName());
			info.setTel(command.getTel());
		}

		setInfo(info);
	}

	public void resetPwd() {

		// 1. 设置属性
		AuthUser authUser = getAuthUser();
		authUser.setPasswd(MD5HashUtil.toMD5(DEFAULT_PASSWORD));

		setAuthUser(authUser);
	}

}
