package hg.dzpw.domain.model.scenicspot;

import hg.dzpw.domain.model.M;

import java.io.Serializable;

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
public class ScenicSpotContactInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人
	 */
	@Column(name = "LINKMAN", length = 64)
	private String linkMan;
	
	/**
	 * 联系人职位
	 */
	@Column(name = "LINKJOB",length = 64)
	private String job;

	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE", length = 64)
	private String telephone;

	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL", length = 256)
	private String email;

	/**
	 * 联系QQ
	 */
	@Column(name = "QQ", length = 16)
	private String qq;

	/**
	 * 公司传真
	 */
	@Column(name = "FAX", length = 32)
	private String fax;

	/**
	 * 联系地址
	 */
	@Column(name = "ADDRESS", length = 512)
	private String address;

	/**
	 * 交通指南
	 */
	@Column(name = "TRAFFIC", columnDefinition = M.TEXT_COLUM)
	private String traffic;

	/**
	 * 邮编
	 */
	@Column(name = "POSTCODE", length = 8)
	private String postcode;
	
	/**
	 * 百度经度
	 */
	@Column(name = "LONGITUDE",length=32)
	private String longitude;
	
	/**
	 * 百度纬度
	 */
	@Column(name = "LATITUDE",length = 32)
	private String latitude;

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan == null ? null : linkMan.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}