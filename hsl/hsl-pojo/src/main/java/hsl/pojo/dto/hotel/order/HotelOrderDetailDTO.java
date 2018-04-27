package hsl.pojo.dto.hotel.order;

import hsl.pojo.dto.hotel.GuaranteeRuleDTO;
import hsl.pojo.dto.hotel.JDLocalHotelDetailDTO;
import hsl.pojo.dto.hotel.NightlyRateDTO;
import hsl.pojo.dto.hotel.PrepayRuleDTO;
import hsl.pojo.util.enumConstants.EnumCurrencyCode;
import hsl.pojo.util.enumConstants.EnumInvoiceMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import slfx.jd.pojo.dto.ylclient.order.CreditCardWithStatusDTO;
import slfx.jd.pojo.dto.ylclient.order.ExtendInfoDTO;
import slfx.jd.pojo.dto.ylclient.order.InvoiceWithStatusDTO;
import slfx.jd.pojo.dto.ylclient.order.OrderRoomDTO;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月14日下午4:03:30
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class HotelOrderDetailDTO extends OrderBaseDTO implements Serializable{
	/**
	 * 艺龙会员卡号
	 */
	protected String elongCardNo;
	/**
	 * 联系人
	 */
	protected ContactDTO contact;
	/**
	 * 扩展字段
	 */
	protected ExtendInfoDTO extendInfo;
	/**
	 * 每个价格
	 */
	protected List<NightlyRateDTO> nightlyRates;
	/**
	 * 房间
	 */
	protected List<OrderRoomDTO> orderRooms;
	/**
	 * 订单编号
	 */
	protected long orderId;
	/**
	 * 订单状态
	 */
	protected String status;
	/**
	 * 展示给用户的状态
	 */
	protected Long showStatus;
	/**
	 * 下一次确认反馈时间点
	 */
	protected java.util.Date confirmPoint;
	/**
	 * 订单产生的罚金
	 */
	protected BigDecimal penaltyToCustomer;
	/**
	 * 罚金货币类型
	 */
	protected EnumCurrencyCode penaltyCurrencyCode;
	/**
	 * 最晚取消时间
	 */
	protected java.util.Date cancelTime;
	/**
	 * 是否要发票
	 */
	protected boolean hasInvoice;
	/**
     * 预付订单的发票开具模式
     */
	protected EnumInvoiceMode invoiceMode;
	/**
	 * 发票
	 */
	protected InvoiceWithStatusDTO invoice;
	/**
	 * 信用卡
	 */
	protected CreditCardWithStatusDTO creditCard;
	/**
	 * 担保规则
	 */
	protected GuaranteeRuleDTO guaranteeRule;
	/**
	 * 预付规则
	 */
	protected PrepayRuleDTO prepayRule;
	/**
	 * 增值服务
	 */
	protected List<String> valueAdds;
	/**
	 * 酒店名称
	 */
	protected String hotelName;
	/**
	 * 房间类型名称
	 */
	protected String roomTypeName;
	/**
	 * 政策名称
	 */
	protected String ratePlanName;
	/**
	 * 当前是否可取消
	 */
	protected Boolean isCancelable;
	/**
	 * 创建时间
	 */
	protected java.util.Date creationDate;
	/***
	 * 优惠券
	 */
	protected BigDecimal coupon;
	
	/**
	 * 酒店详情
	 */
	private JDLocalHotelDetailDTO hotelDto;
	
	
	
	public JDLocalHotelDetailDTO getHotelDto() {
		return hotelDto;
	}
	public void setHotelDto(JDLocalHotelDetailDTO hotelDto) {
		this.hotelDto = hotelDto;
	}
	public String getElongCardNo() {
		return elongCardNo;
	}
	public void setElongCardNo(String elongCardNo) {
		this.elongCardNo = elongCardNo;
	}
	public ContactDTO getContact() {
		return contact;
	}
	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}
	public ExtendInfoDTO getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(ExtendInfoDTO extendInfo) {
		this.extendInfo = extendInfo;
	}
	public List<NightlyRateDTO> getNightlyRates() {
		return nightlyRates;
	}
	public void setNightlyRates(List<NightlyRateDTO> nightlyRates) {
		this.nightlyRates = nightlyRates;
	}
	public List<OrderRoomDTO> getOrderRooms() {
		return orderRooms;
	}
	public void setOrderRooms(List<OrderRoomDTO> orderRooms) {
		this.orderRooms = orderRooms;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(Long showStatus) {
		this.showStatus = showStatus;
	}
	public java.util.Date getConfirmPoint() {
		return confirmPoint;
	}
	public void setConfirmPoint(java.util.Date confirmPoint) {
		this.confirmPoint = confirmPoint;
	}
	public BigDecimal getPenaltyToCustomer() {
		return penaltyToCustomer;
	}
	public void setPenaltyToCustomer(BigDecimal penaltyToCustomer) {
		this.penaltyToCustomer = penaltyToCustomer;
	}
	public EnumCurrencyCode getPenaltyCurrencyCode() {
		return penaltyCurrencyCode;
	}
	public void setPenaltyCurrencyCode(EnumCurrencyCode penaltyCurrencyCode) {
		this.penaltyCurrencyCode = penaltyCurrencyCode;
	}
	public java.util.Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(java.util.Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public boolean isHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
	public EnumInvoiceMode getInvoiceMode() {
		return invoiceMode;
	}
	public void setInvoiceMode(EnumInvoiceMode invoiceMode) {
		this.invoiceMode = invoiceMode;
	}
	public InvoiceWithStatusDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceWithStatusDTO invoice) {
		this.invoice = invoice;
	}
	public CreditCardWithStatusDTO getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCardWithStatusDTO creditCard) {
		this.creditCard = creditCard;
	}
	public GuaranteeRuleDTO getGuaranteeRule() {
		return guaranteeRule;
	}
	public void setGuaranteeRule(GuaranteeRuleDTO guaranteeRule) {
		this.guaranteeRule = guaranteeRule;
	}
	public PrepayRuleDTO getPrepayRule() {
		return prepayRule;
	}
	public void setPrepayRule(PrepayRuleDTO prepayRule) {
		this.prepayRule = prepayRule;
	}
	public List<String> getValueAdds() {
		return valueAdds;
	}
	public void setValueAdds(List<String> valueAdds) {
		this.valueAdds = valueAdds;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getRatePlanName() {
		return ratePlanName;
	}
	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}
	public Boolean getIsCancelable() {
		return isCancelable;
	}
	public void setIsCancelable(Boolean isCancelable) {
		this.isCancelable = isCancelable;
	}
	public java.util.Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}
	public BigDecimal getCoupon() {
		return coupon;
	}
	public void setCoupon(BigDecimal coupon) {
		this.coupon = coupon;
	}
	
	
	
	
}
