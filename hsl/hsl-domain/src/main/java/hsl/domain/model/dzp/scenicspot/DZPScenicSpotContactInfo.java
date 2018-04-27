package hsl.domain.model.dzp.scenicspot;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 景区联系信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPScenicSpotContactInfo implements Serializable {

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 64)
	private String linkMan;

	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE", length = 64)
	private String telephone;

	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL", length = 128)
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
	@Column(name = "ADDRESS", length = 256)
	private String address;
	
	/**
	 * 交通指南
	 */
	@Column(name = "TRAFFIC", columnDefinition = M.TEXT_COLUM)
	private String traffic;

	/**
	 * 邮编
	 */
	@Column(name = "POSTCODE", length = 16)
	private String postcode;
	
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}