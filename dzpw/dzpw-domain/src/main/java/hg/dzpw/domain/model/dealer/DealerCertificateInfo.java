package hg.dzpw.domain.model.dealer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：经销商资质信息
 * @类修改者：
 * @修改日期：2015-2-12下午6:02:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-12下午6:02:07
 */
@Embeddable
public class DealerCertificateInfo implements Serializable {
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
	@Column(name = "ORGANIZATION_CODE", length = 1024)
	private String organizationCodeCertificate;

	/**
	 * 法人身份证(图片地址)
	 */
	@Column(name = "CORPORATE_ID_CARD", length = 1024)
	private String corporateIDCard;
	
	/**
	 * LOGO(图片地址)
	 */
	@Column(name = "DEALER_LOGO", length = 1024)
	private String dealerLogo;
	

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

	public String getDealerLogo() {
		return dealerLogo;
	}

	public void setDealerLogo(String dealerLogo) {
		this.dealerLogo = dealerLogo;
	}
}