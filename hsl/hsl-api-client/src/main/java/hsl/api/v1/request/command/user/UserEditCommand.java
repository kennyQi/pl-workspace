package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class UserEditCommand extends ApiPayload{
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户头像
	 */
	private String image;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 省份id
	 */
	private String province;
	
	/**
	 * 城市id
	 */
	private String city;
	
	/**
	 * 邮箱
	 */
	private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
