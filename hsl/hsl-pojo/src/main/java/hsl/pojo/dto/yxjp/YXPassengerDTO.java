package hsl.pojo.dto.yxjp;

import hsl.pojo.util.HSLConstants;

import java.io.Serializable;

/**
 * 乘客DTO
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class YXPassengerDTO implements Serializable {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 证件类型
	 *
	 * @see HSLConstants.Traveler
	 */
	private Integer idType;

	/**
	 * 乘客类型（目前只支持成人）
	 *
	 * @see HSLConstants.Traveler
	 */
	private Integer type;

	/**
	 * 员工ID（企业账户选择员工才有）
	 */
	private String companyMemberId;

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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyMemberId() {
		return companyMemberId;
	}

	public void setCompanyMemberId(String companyMemberId) {
		this.companyMemberId = companyMemberId;
	}
}
