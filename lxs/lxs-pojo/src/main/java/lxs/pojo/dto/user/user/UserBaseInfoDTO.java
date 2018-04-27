package lxs.pojo.dto.user.user;

import java.util.Date;

public class UserBaseInfoDTO {

	/**
	 * 真实姓名
	 */
	private String name;

	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 姓别 1男 2女
	 */
	private Integer gender;

	/**
	 * 出生年月日
	 */
	private String birthday;
	
	/**
	 * 注册时间
	 */
	private Date createTime;

	/**
	 * 昵称
	 */
	private String nickName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
