package hsl.domain.model.user;

import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：商城用户常用的他人用户信息，如常旅客，代购收货人等
 * @类修改者：yuxx
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月30日下午1:46:29
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class CommonUserInfo extends BaseModel {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 证件号
	 */
	private String idCardNo;

	/**
	 * 1身份证
	 */
	private String idCardType;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 创建资料用户
	 */
	private User user;

	/**
	 * 是否是用户本人资料
	 */
	private Boolean self;

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

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getSelf() {
		return self;
	}

	public void setSelf(Boolean self) {
		this.self = self;
	}

}
