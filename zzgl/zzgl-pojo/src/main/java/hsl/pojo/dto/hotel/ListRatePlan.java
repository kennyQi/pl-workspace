package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseRatePlanDTO;

import java.math.BigDecimal;
import java.util.List;
/**
 * 
 * @类功能说明：产品信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:53:55
 * @版本：V1.0
 * 
 */
public class ListRatePlan extends BaseRatePlanDTO {
	/**
	 * 销售状态
	 */
	protected boolean status;
	/**
	 * 客人类型
	 */
	protected String customerType;
	/**
	 * 房量限额
	 */
	protected int currentAlloment;
	/**
	 * 是否支持即时确认
	 */
	protected boolean instantConfirmation;
	/**
	 * 产品特性类型
	 */
	protected String productTypes;
	/**
	 * 是否今日特价
	 */
	protected boolean isLastMinuteSale;
	/**
	 * 每天可以销售的开始时间
	 */
	protected String startTime;
	/**
	 * 每天可以销售的结束时间
	 */
	protected String endTime;
	/**
	 * 总价
	 */
	protected BigDecimal totalRate;
	/**
	 * 日均价
	 */
	protected BigDecimal averageRate;
	/**
	 * 促销前的日均价
	 */
	protected BigDecimal averageBaseRate;
	/**
	 * 货币
	 */
	protected String currencyCode;
	/**
	 * 优惠券
	 */
	protected BigDecimal coupon;
	/**
	 * 每天价格集合
	 */
	protected List<NightlyRateDTO> nightlyRates;
	/**
	 * 对应的预订规则编号
	 */
	protected String bookingRuleIds;
	/**
	 * 对应的担保规则编号
	 */
	protected String guaranteeRuleIds;
	/**
	 * 对应的预付规则编号
	 */
	protected String prepayRuleIds;
	/**
	 * 对应的促销规则编号
	 */
	protected String drrRuleIds;
	/**
	 * 对应的增值服务编号
	 */
	protected String valueAddIds;
	/**
	 * 礼品ID
	 */
	protected String giftIds;
	/**
	 * 酒店特殊信息提示的编号
	 */
	protected String hAvailPolicyIds;
	/**
	 * 销售房型编号
	 */
	protected String roomTypeId;
	/**
	 * 供应商房型附加名称
	 */
	protected String suffixName;
	/**
	 * 供应商酒店编码
	 */
	protected String hotelCode;
	/**
	 * 预付产品发票模式
	 */
	protected String invoiceMode;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public int getCurrentAlloment() {
		return currentAlloment;
	}

	public void setCurrentAlloment(int currentAlloment) {
		this.currentAlloment = currentAlloment;
	}

	public boolean isInstantConfirmation() {
		return instantConfirmation;
	}

	public void setInstantConfirmation(boolean instantConfirmation) {
		this.instantConfirmation = instantConfirmation;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}

	public boolean isLastMinuteSale() {
		return isLastMinuteSale;
	}

	public void setLastMinuteSale(boolean isLastMinuteSale) {
		this.isLastMinuteSale = isLastMinuteSale;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}

	public BigDecimal getAverageBaseRate() {
		return averageBaseRate;
	}

	public void setAverageBaseRate(BigDecimal averageBaseRate) {
		this.averageBaseRate = averageBaseRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCoupon() {
		return coupon;
	}

	public void setCoupon(BigDecimal coupon) {
		this.coupon = coupon;
	}

	public List<NightlyRateDTO> getNightlyRates() {
		return nightlyRates;
	}

	public void setNightlyRates(List<NightlyRateDTO> nightlyRates) {
		this.nightlyRates = nightlyRates;
	}

	public String getBookingRuleIds() {
		return bookingRuleIds;
	}

	public void setBookingRuleIds(String bookingRuleIds) {
		this.bookingRuleIds = bookingRuleIds;
	}

	public String getGuaranteeRuleIds() {
		return guaranteeRuleIds;
	}

	public void setGuaranteeRuleIds(String guaranteeRuleIds) {
		this.guaranteeRuleIds = guaranteeRuleIds;
	}

	public String getPrepayRuleIds() {
		return prepayRuleIds;
	}

	public void setPrepayRuleIds(String prepayRuleIds) {
		this.prepayRuleIds = prepayRuleIds;
	}

	public String getDrrRuleIds() {
		return drrRuleIds;
	}

	public void setDrrRuleIds(String drrRuleIds) {
		this.drrRuleIds = drrRuleIds;
	}

	public String getValueAddIds() {
		return valueAddIds;
	}

	public void setValueAddIds(String valueAddIds) {
		this.valueAddIds = valueAddIds;
	}

	public String getGiftIds() {
		return giftIds;
	}

	public void setGiftIds(String giftIds) {
		this.giftIds = giftIds;
	}

	public String gethAvailPolicyIds() {
		return hAvailPolicyIds;
	}

	public void sethAvailPolicyIds(String hAvailPolicyIds) {
		this.hAvailPolicyIds = hAvailPolicyIds;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getSuffixName() {
		return suffixName;
	}

	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getInvoiceMode() {
		return invoiceMode;
	}

	public void setInvoiceMode(String invoiceMode) {
		this.invoiceMode = invoiceMode;
	}

}
