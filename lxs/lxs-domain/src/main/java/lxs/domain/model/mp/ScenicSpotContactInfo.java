package lxs.domain.model.mp;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @类功能说明:景区联系信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:07:31
 * @版本：V1.0
 */
@Embeddable
public class ScenicSpotContactInfo {

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 512)
	private String linkMan;
	
	/**
	 * 联系人职位
	 */
	@Column(name = "JOB", length = 512)
	private String job;

	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE", length = 512)
	private String telephone;

	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL", length = 512)
	private String email;

	/**
	 * 联系QQ
	 */
	@Column(name = "QQ", length = 512)
	private String qq;
	

	/**
	 * 公司传真
	 */
	@Column(name = "FAX", length = 512)
	private String fax;

	/**
	 * 联系地址
	 */
	@Column(name = "ADDRESS", length = 512)
	private String address;
	
	/**
	 * 交通指南
	 */
	@Column(name = "TRAFFIC", length = 512)
	private String traffic;

	/**
	 * 邮编
	 */
	@Column(name = "POSTCODE", length = 512)
	private String postcode;
	
	/**
	 * 百度经度
	 */
	@Column(name = "LONGITUDE", length = 512)
	private String longitude;
	
	/**
	 * 百度纬度
	 */
	@Column(name = "LATITUDE", length = 512)
	private String latitude;

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}