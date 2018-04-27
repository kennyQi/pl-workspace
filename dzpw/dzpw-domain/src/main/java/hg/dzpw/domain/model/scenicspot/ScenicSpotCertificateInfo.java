package hg.dzpw.domain.model.scenicspot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明:景区资质信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:07:55
 * @版本：V1.0
 */
@Embeddable
public class ScenicSpotCertificateInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 营业执照(图片地址)
	 */
	@Column(name = "BUSINESS_LICENSE", length = 1024)
	private String businessLicense;

	/**
	 * 税务登记证(图片地址)
	 */
	@Column(name = "TAX_REGISTRATION", length = 1024)
	private String taxRegistrationCertificate;

	/**
	 * 组织代码证(图片地址)
	 */
	@Column(name = "ORGANIZATION_CODE_CERTIFICATE", length = 1024)
	private String organizationCodeCertificate;

	/**
	 * 法人身份证(图片地址)
	 */
	@Column(name = "CORPORATE_ID_CARD", length = 1024)
	private String corporateIDCard;
	
	/**
	 * 景区LOGO(图片地址)
	 */
	@Column(name = "SCENIC_SPOT_LOGO", length = 1024)
	private String scenicSpotLogo;
	
	/**
	 * 组织机构证代码
	 */
	@Column(name= "ORGANIZATION_CODE")
	private String organizationCode;

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense == null ? null : businessLicense.trim();
	}

	public String getTaxRegistrationCertificate() {
		return taxRegistrationCertificate;
	}

	public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
		this.taxRegistrationCertificate = taxRegistrationCertificate == null ? null
				: taxRegistrationCertificate.trim();
	}

	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}

	public void setOrganizationCodeCertificate(
			String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate == null ? null
				: organizationCodeCertificate.trim();
	}

	public String getCorporateIDCard() {
		return corporateIDCard;
	}

	public void setCorporateIDCard(String corporateIDCard) {
		this.corporateIDCard = corporateIDCard == null ? null : corporateIDCard
				.trim();
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getScenicSpotLogo() {
		return scenicSpotLogo;
	}

	public void setScenicSpotLogo(String scenicSpotLogo) {
		this.scenicSpotLogo = scenicSpotLogo;
	}

}