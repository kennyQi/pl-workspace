package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;
import lxs.api.v1.dto.user.UserDTO;

/**
 * 用户修改资料
 * 
 * 生日只有第一次带值更新有效，其余每次都会更新，可为空
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class ModifyUserInfoCommand extends ApiPayload {

	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private UserDTO userDTO;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
/*
	*//**
	 * 用户名
	 * *//*
	private String loginName;
	
	*//**
	 * 昵称
	 *//*
	private String nickName;

	*//**
	 * 性别
	 *//*
	private Integer gender;

	*//**
	 * 生日 yyyy-MM-dd
	 *//*
	private String birthDay;

	*//**
	 * 邮箱
	 *//*
	private String email;

	*//**
	 * 省份代码
	 *//*
	private String provinceId;

	*//**
	 * 城市代码
	 *//*
	private String cityId;

	*//**
	 * 区县代码
	 *//*
	private String areaId;

	*//**
	 * 图片ID
	 *//*
	private String img_image_id;

	*//**
	 * 图片名称
	 *//*
	private String img_title;
	
	*//**
	 * 图片字节数组
	 *//*
	private byte[] img_image_bytearry;

	public byte[] getImg_image_bytearry() {
		return img_image_bytearry;
	}

	public void setImg_image_bytearry(byte[] img_image_bytearry) {
		this.img_image_bytearry = img_image_bytearry;
	}

	public String getImg_image_id() {
		return img_image_id;
	}

	public void setImg_image_id(String img_image_id) {
		this.img_image_id = img_image_id;
	}

	public String getImg_title() {
		return img_title;
	}

	public void setImg_title(String img_title) {
		this.img_title = img_title;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public static void main(String[] arg) {
		ModifyUserInfoCommand m = new ModifyUserInfoCommand();
		m.setLoginName("18888888888");
		m.setNickName("nicheng");
		String s = "what's the byte";
		m.setImg_image_bytearry	(s.getBytes());
		m.setGender(1);
		m.setBirthDay("2000-01-01 01:01:01");
		m.setEmail("888@ply365.com");
		m.setProvinceId("132");
		m.setCityId("456");
		m.setAreaId("789");
		m.setImg_image_id("951");
		m.setImg_title("touxiang");
		//m.setImg_url("D:/apache-tomcat-7.0.40/webapps/hsl-api/upload/head/2014/10/23e1938a15-e3a0-4a41-a09f-eb4a054b4224.jpg");
		System.out.println(JSON.toJSON(m));
		
	}*/
}
