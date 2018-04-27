package hsl.pojo.dto.hotel.order;
import java.io.Serializable;
import java.util.List;
import slfx.jd.pojo.dto.ylclient.order.BaseNightlyRateDTO;
import slfx.jd.pojo.dto.ylclient.order.ContactDTO;
import slfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import slfx.jd.pojo.dto.ylclient.order.CreditCardDTO;
import slfx.jd.pojo.dto.ylclient.order.ExtendInfoDTO;
public class OrderCreateCommandDTO extends OrderBaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 合作伙伴订单确认号（必填）
	 */
	protected String affiliateConfirmationId;
	/**
	 * 客户ip地址（必填）
	 */
	protected String customerIPAddress;
	/**
	 * 是否已担保或已付款（必填）
	 */
	protected boolean isGuaranteeOrCharged;
	/**
	 * 供应商卡号(选填)
	 */
	protected String supplierCardNo;
	/**
	 * 是否需要发票(选填)
	 */
	protected boolean isNeedInvoice;
	/**
	 * 联系人（必填）
	 */
	protected ContactDTO contact;
	/**
	 * 扩展字段
	 */
	protected ExtendInfoDTO extendInfo;
	/**
	 * 多天价格
	 */
	protected List<BaseNightlyRateDTO> nightlyRatesDTO;
	/**
	 * 客人信息（必填）
	 */
	protected List<CreateOrderRoomDTO> orderRoomsDTO;
	/**
	 * 发票信息(选填)
	 */
	protected InvoiceDTO invoice;
	/**
	 * 信用卡
	 */
	protected CreditCardDTO creditCard;
	protected Boolean isForceGuarantee;
	protected Integer mustVouchInRuleType;

	public String getAffiliateConfirmationId() {
		return affiliateConfirmationId;
	}

	public void setAffiliateConfirmationId(String affiliateConfirmationId) {
		this.affiliateConfirmationId = affiliateConfirmationId;
	}

	public String getCustomerIPAddress() {
		return customerIPAddress;
	}

	public void setCustomerIPAddress(String customerIPAddress) {
		this.customerIPAddress = customerIPAddress;
	}

	public boolean isGuaranteeOrCharged() {
		return isGuaranteeOrCharged;
	}

	public void setGuaranteeOrCharged(boolean isGuaranteeOrCharged) {
		this.isGuaranteeOrCharged = isGuaranteeOrCharged;
	}

	public String getSupplierCardNo() {
		return supplierCardNo;
	}

	public void setSupplierCardNo(String supplierCardNo) {
		this.supplierCardNo = supplierCardNo;
	}

	public boolean isNeedInvoice() {
		return isNeedInvoice;
	}

	public void setNeedInvoice(boolean isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void 陈鑫亚(ContactDTO contact) {
		this.contact = contact;
	}

	public ExtendInfoDTO getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(ExtendInfoDTO extendInfo) {
		this.extendInfo = extendInfo;
	}

	public List<BaseNightlyRateDTO> getNightlyRatesDTO() {
		return nightlyRatesDTO;
	}

	public void setNightlyRatesDTO(List<BaseNightlyRateDTO> nightlyRatesDTO) {
		this.nightlyRatesDTO = nightlyRatesDTO;
	}

	public List<CreateOrderRoomDTO> getOrderRoomsDTO() {
		return orderRoomsDTO;
	}

	public void setOrderRoomsDTO(List<CreateOrderRoomDTO> orderRoomsDTO) {
		this.orderRoomsDTO = orderRoomsDTO;
	}

	public InvoiceDTO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}

	public CreditCardDTO getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardDTO creditCard) {
		this.creditCard = creditCard;
	}

	public Boolean getIsForceGuarantee() {
		return isForceGuarantee;
	}

	public void setIsForceGuarantee(Boolean isForceGuarantee) {
		this.isForceGuarantee = isForceGuarantee;
	}

	public Integer getMustVouchInRuleType() {
		return mustVouchInRuleType;
	}

	public void setMustVouchInRuleType(Integer mustVouchInRuleType) {
		this.mustVouchInRuleType = mustVouchInRuleType;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}
	
	

}
