package lxs.domain.model.user;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lxs.domain.model.M;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.command.user.ModifyUserInfoCommand;
import lxs.pojo.command.user.RegisterCommand;


@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_USER + "USER")
public class LxsUser extends BaseModel {

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

	/**
	 * 头像
	 */
	@Embedded
	private Image titleImage;
	
	/**
	 * 联系人
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade={CascadeType.ALL})
	private List<Contacts> contacts;
	

	public List<Contacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}

	/**
	 * 用户注册
	 * 
	 * @param command
	 */
	public void register(RegisterCommand command, RegisterSaga saga) {

		setId(UUIDGenerator.getUUID());
		setBaseInfo(new UserBaseInfo());
		setAuthInfo(new UserAuthInfo());
		setContactInfo(new UserContactInfo());
		setStatus(new UserStatus());

		getBaseInfo().setCreateTime(new Date());
		getBaseInfo().setSource(command.getSource());
		getBaseInfo().setNickName(command.getNickName());
		getAuthInfo().setLoginName(saga.getMobile());
		getAuthInfo().setPassword(command.getEncryptPassword());
		getContactInfo().setMobile(saga.getMobile());
		getStatus().setActivated(true);
	}

	public void modifyInfo(ModifyUserInfoCommand command, Province province,
			City city, Image titleImage) {

		if (getBaseInfo() == null) {
			setBaseInfo(new UserBaseInfo());
		}

		if (command.getBirthDay() != null) {
			getBaseInfo().setBirthday(command.getBirthDay());
		}
		if (command.getGender() != null) {
			getBaseInfo().setGender(command.getGender());
		}if (command.getNickName() != null) {
			getBaseInfo().setNickName(command.getNickName());
		}if (command.getEmail() != null) {
			getContactInfo().setEmail(command.getEmail());
		}

		setTitleImage(titleImage);

		if (province != null) {
			getContactInfo().setProvinceId(province.getId());
			getContactInfo().setProvinceName(province.getName());
		}
		if (city != null) {
			getContactInfo().setCityId(city.getId());
			getContactInfo().setCityName(city.getName());
		}

	}

	/**
	 * 更新密码
	 * 
	 * @param newPassword
	 */
	public void updatePassword(String newPassword) {
		authInfo.setPassword(newPassword);
		setAuthInfo(authInfo);
	}

	/**
	 * 用户登录，记录登录时间
	 */
	public void login() {
		UserStatus status = new UserStatus();
		status.setLastLoginTime(new Date());
		setStatus(status);
	}

	public UserAuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(UserAuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public UserBaseInfo getBaseInfo() {
		if (baseInfo==null) {
			baseInfo=new UserBaseInfo();
		}
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public UserContactInfo getContactInfo() {
		if (contactInfo==null) {
			contactInfo=new UserContactInfo();
		}
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

	public Image getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(Image titleImage) {
		this.titleImage = titleImage;
	}

}
