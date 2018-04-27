package slfx.mp.domain.model.order;

import java.io.Serializable;

/**
 * 游客信息
 * 
 * @author Administrator
 */
public class Traveler implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 游客姓名
	 */
	private String name;
	/**
	 * 游客手机
	 */
	private String mobile;
	/**
	 * 是否陪同
	 */
	private Boolean accompany;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getAccompany() {
		return accompany;
	}

	public void setAccompany(Boolean accompany) {
		this.accompany = accompany;
	}

}