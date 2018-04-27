package zzpl.domain.model.hsluser;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import zzpl.domain.model.M;

@Embeddable
public class UserBaseInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 真实姓名
	 */
	@Column(name="NAME" ,length=64)
	private String name;
	
	/**
	 * 身份证号
	 */
	@Column(name="IDCARD_NO",length=18)
	private String idCardNo;
	
	/**
	 * 性别
	 */
	@Column(name="GENDER" ,columnDefinition=M.NUM_COLUM)
	private Integer gender;

	/**
	 * 生日
	 */
	@Column(name="BIRTHDAY",columnDefinition=M.DATE_COLUM)
	private Date birthday;
	
	/**
	 * 来源
	 */
	@Column(name="SOURCE",length=64)
	private String source;
	
	/**
	 * 注册时间
	 */
	@Column(name="CREATE_TIME",columnDefinition=M.DATE_COLUM)
	private Date createTime;
	
	/**
	 * 类型 1为个人 2为企业
	 */
	@Column(name="TYPE",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer type;
	
	/**
	 * 昵称
	 */
	@Column(name="NICK_NAME",length=64)
	private String nickName;
	
	/**
	 * 头像
	 */
	@Column(name="IMAGE",length=255)
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
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
