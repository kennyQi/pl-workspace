package hsl.domain.model.user;
import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.UpdateBindMobileCommand;
import hsl.pojo.command.UpdateCompanyUserCommand;
import hsl.pojo.command.UpdateHeadImageCommand;
import hsl.pojo.command.UserRegisterCommand;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_USER+"USER")
public class User extends BaseModel {
	
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

	/**
	 * 用户注册
	 * @param command
	 */
	public void register(UserRegisterCommand command,String clientKey){
		
		authInfo=new UserAuthInfo();
		baseInfo=new UserBaseInfo();
		status=new UserStatus();
		contactInfo=new UserContactInfo();
		authInfo.setLoginName(command.getLoginName());
		authInfo.setPassword(command.getPassword());
		if(StringUtils.isNotBlank(command.getName())){
			baseInfo.setName(command.getName());
		}
		if(StringUtils.isNotBlank(command.getNickName())){
			baseInfo.setNickName(command.getNickName());
		}
		baseInfo.setSource(clientKey);
		baseInfo.setCreateTime(new Date());
		baseInfo.setType(command.getType());
		
		if(StringUtils.isBlank(command.getMobile())){
			status.setActivated(false);
			status.setIsTelChecked(false);
		}else{
			contactInfo.setMobile(command.getMobile());
			status.setActivated(true);
			status.setIsTelChecked(true);
		}
		if (StringUtils.isNotBlank(command.getEmail())) {
			contactInfo.setEmail(command.getEmail());
			status.setIsEmailChecked(true);
		}else{
			status.setIsEmailChecked(false);
		}
		setAuthInfo(authInfo);
		setContactInfo(contactInfo);
		setBaseInfo(baseInfo);
		setStatus(status);
		setId(UUIDGenerator.getUUID());
	}
	
	/**
	 * 更新密码
	 * @param newPassword
	 */
	public void updatePassword(String newPassword){
		authInfo.setPassword(newPassword);
		setAuthInfo(authInfo);
	}
	
	/**
	 * 用户登录，记录登录时间
	 */
	public void login(){
		status.setLastLoginTime(new Date());
		setStatus(status);
	}
	
	public void updateCompanyUser(UpdateCompanyUserCommand command){
		if(StringUtils.isNotBlank(command.getNickName())){
			baseInfo.setNickName(command.getNickName());
		}
		if(StringUtils.isNotBlank(command.getName())){
			baseInfo.setName(command.getName());
		}
		if(StringUtils.isNotBlank(command.getImage())){
			baseInfo.setImage(command.getImage());
		}
		if(StringUtils.isNotBlank(command.getBirthday())){
			baseInfo.setBirthday(DateUtil.parseDate3(command.getBirthday()));
		}
		baseInfo.setGender(StringUtils.isBlank(command.getGender())?null:Integer.parseInt(command.getGender()));;
		contactInfo.setEmail(command.getEmail());
		contactInfo.setProvinceId(command.getProvince());
		contactInfo.setCityId(command.getCity());
		setBaseInfo(baseInfo);
		setContactInfo(contactInfo);
	}
	
	public void updateBindMobile(UpdateBindMobileCommand command){
		contactInfo.setMobile(command.getMobile());
		setContactInfo(contactInfo);
	}

	public void updateHeadImage(UpdateHeadImageCommand command) {
		baseInfo.setImage(command.getImagePath());
		setBaseInfo(baseInfo);
	}
}
