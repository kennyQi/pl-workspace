package hg.pojo.command;


@SuppressWarnings("serial")
public class ModifySupplierAptitudeCommand extends JxcCommand {

	/**
	 * 供应商id
	 */
	private String supplierId;
	
	/**
	 * 营业执照
	 */
	private String businessLicenseImageId;
	
	/**
	 * 税务登记证
	 */
	private String taxImageId;
	
	/**
	 * 法人身份证
	 */
	private String legalPersonIDCardImageId;
	
	/**
	 * 授权书
	 */
	private String authorizationImageId;
	
	/**
	 * 其他
	 */
	private String otherImageId;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getBusinessLicenseImageId() {
		return businessLicenseImageId;
	}

	public void setBusinessLicenseImageId(String businessLicenseImageId) {
		this.businessLicenseImageId = businessLicenseImageId;
	}

	public String getTaxImageId() {
		return taxImageId;
	}

	public void setTaxImageId(String taxImageId) {
		this.taxImageId = taxImageId;
	}

	public String getLegalPersonIDCardImageId() {
		return legalPersonIDCardImageId;
	}

	public void setLegalPersonIDCardImageId(String legalPersonIDCardImageId) {
		this.legalPersonIDCardImageId = legalPersonIDCardImageId;
	}

	public String getAuthorizationImageId() {
		return authorizationImageId;
	}

	public void setAuthorizationImageId(String authorizationImageId) {
		this.authorizationImageId = authorizationImageId;
	}

	public String getOtherImageId() {
		return otherImageId;
	}

	public void setOtherImageId(String otherImageId) {
		this.otherImageId = otherImageId;
	}
	
}
