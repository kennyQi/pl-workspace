package hsl.pojo.dto.user;

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
	 * 来源
	 */
	private String source;
	
	/**
	 * 注册时间
	 */
	private Date createTime;

	/**
	 * 账户类型 1为个人  2为企业
	 */
	private Integer type;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 头像
	 */
	private String image;
	
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
