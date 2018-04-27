package hsl.pojo.command;

import java.util.Date;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CreateHotScenicSpotCommand  extends BaseCommand{
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 是否为当前热门景点
	 */
	private Boolean open;
	
	/**
	 * 设置为当前热门景点的时间
	 */
	private Date openDate;
	
	/**
	 * 平台景区id
	 */
	private String scenicSpotId;
	
	/**
	 * 景点名称
	 */
	private String name;
	
	/**
	 * 景点简介
	 */
	private String intro;

	/**
	 * 景点首图
	 */
	private String image;

	/**
	 * 景点级别
	 */
	private String grade;

	/**
	 * 景点别名
	 */
	private String alias;
	
	/**
	 * 景区地址
	 */
	private String address;
	/**
	 * 所在省
	 */
	private String province;
	/**
	 * 所在城市
	 */
	private String city;
	
	/**
	 * 交通指南
	 */
	private String traffic;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	
	
}
