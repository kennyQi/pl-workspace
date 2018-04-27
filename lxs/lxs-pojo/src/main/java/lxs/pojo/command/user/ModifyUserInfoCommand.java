package lxs.pojo.command.user;

import hg.common.component.BaseCommand;

/**
 * 用户修改资料
 * 
 * 生日只有第一次带值更新有效，其余每次都会更新，可为空
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class ModifyUserInfoCommand extends BaseCommand {

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 生日 yyyy-MM-dd
	 */
	private String birthDay;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 省份代码
	 */
	private String provinceId;

	/**
	 * 城市代码
	 */
	private String cityId;

	/**
	 * 区县代码
	 */
	private String areaId;

	/**
	 * 图片ID
	 */
	private String img_image_id;

	/**
	 * 图片名称
	 */
	private String img_title;
	
	/**
	 * 图片字节数组
	 */
	private byte[] img_image_bytearry;
	
	/**
	 * 图片URL
	 */
	private String img_url;
	
	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
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

	public byte[] getImg_image_bytearry() {
		return img_image_bytearry;
	}

	public void setImg_image_bytearry(byte[] img_image_bytearry) {
		this.img_image_bytearry = img_image_bytearry;
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

}
