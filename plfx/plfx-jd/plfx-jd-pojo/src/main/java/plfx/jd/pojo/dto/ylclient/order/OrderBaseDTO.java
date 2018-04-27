
package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.math.BigDecimal;

import plfx.jd.pojo.system.enumConstants.EnumConfirmationType;
import plfx.jd.pojo.system.enumConstants.EnumCurrencyCode;
import plfx.jd.pojo.system.enumConstants.EnumGuestTypeCode;
import plfx.jd.pojo.system.enumConstants.EnumPaymentType;


@SuppressWarnings("serial")
public class OrderBaseDTO implements Serializable{
	/**
	 * 酒店编号
	 */
    protected String hotelId;
    /**
	 * 房型编号
	 */
    protected String roomTypeId;
    /**
	 * 产品编号
	 */
    protected int ratePlanId;
    /**
	 * 入住时间
	 */
    protected java.util.Date arrivalDate;
    /**
	 * 离店时间
	 */
    protected java.util.Date departureDate;
    /**
	 * 客人类型
	 */
    protected EnumGuestTypeCode customerType;
    /**
	 * 付款类型
	 */
    protected EnumPaymentType paymentType;
    /**
	 * 房间数量
	 */
    protected int numberOfRooms;
    /**
	 * 客人数量
	 */
    protected int numberOfCustomers;
    /**
	 * 最早到店时间
	 */
    protected java.util.Date earliestArrivalTime;
    /**
	 * 最晚到店时间
	 */
    protected java.util.Date latestArrivalTime;
    /**
	 * 货币类型
	 */
    protected EnumCurrencyCode currencyCode;
    /**
	 * 总价
	 */
    protected BigDecimal totalPrice;
    /**
	 * 确认类型
	 */
    protected EnumConfirmationType confirmationType;
    /**
	 * 给酒店备注
	 */
    protected String noteToHotel;
    /**
	 * 给艺龙备注
	 */
    protected String noteToElong;

    /**
     * Gets the value of the hotelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelId() {
        return hotelId;
    }

    /**
     * Sets the value of the hotelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelId(String value) {
        this.hotelId = value;
    }

    /**
     * Gets the value of the roomTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomTypeId() {
        return roomTypeId;
    }

    /**
     * Sets the value of the roomTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomTypeId(String value) {
        this.roomTypeId = value;
    }

    /**
     * Gets the value of the ratePlanId property.
     * 
     */
    public int getRatePlanId() {
        return ratePlanId;
    }

    /**
     * Sets the value of the ratePlanId property.
     * 
     */
    public void setRatePlanId(int value) {
        this.ratePlanId = value;
    }

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Date }
     *     
     */
    public java.util.Date getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Date }
     *     
     */
    public void setArrivalDate(java.util.Date value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Date }
     *     
     */
    public java.util.Date getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Date }
     *     
     */
    public void setDepartureDate(java.util.Date value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link EnumGuestTypeCode }
     *     
     */
    public EnumGuestTypeCode getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumGuestTypeCode }
     *     
     */
    public void setCustomerType(EnumGuestTypeCode value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link EnumPaymentType }
     *     
     */
    public EnumPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPaymentType }
     *     
     */
    public void setPaymentType(EnumPaymentType value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the numberOfRooms property.
     * 
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the value of the numberOfRooms property.
     * 
     */
    public void setNumberOfRooms(int value) {
        this.numberOfRooms = value;
    }

    /**
     * Gets the value of the numberOfCustomers property.
     * 
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    /**
     * Sets the value of the numberOfCustomers property.
     * 
     */
    public void setNumberOfCustomers(int value) {
        this.numberOfCustomers = value;
    }

    /**
     * Gets the value of the earliestArrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Date }
     *     
     */
    public java.util.Date getEarliestArrivalTime() {
        return earliestArrivalTime;
    }

    /**
     * Sets the value of the earliestArrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Date }
     *     
     */
    public void setEarliestArrivalTime(java.util.Date value) {
        this.earliestArrivalTime = value;
    }

    /**
     * Gets the value of the latestArrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Date }
     *     
     */
    public java.util.Date getLatestArrivalTime() {
        return latestArrivalTime;
    }

    /**
     * Sets the value of the latestArrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Date }
     *     
     */
    public void setLatestArrivalTime(java.util.Date value) {
        this.latestArrivalTime = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link EnumCurrencyCode }
     *     
     */
    public EnumCurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumCurrencyCode }
     *     
     */
    public void setCurrencyCode(EnumCurrencyCode value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalPrice(BigDecimal value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the confirmationType property.
     * 
     * @return
     *     possible object is
     *     {@link EnumConfirmationType }
     *     
     */
    public EnumConfirmationType getConfirmationType() {
        return confirmationType;
    }

    /**
     * Sets the value of the confirmationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumConfirmationType }
     *     
     */
    public void setConfirmationType(EnumConfirmationType value) {
        this.confirmationType = value;
    }

    /**
     * Gets the value of the noteToHotel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteToHotel() {
        return noteToHotel;
    }

    /**
     * Sets the value of the noteToHotel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteToHotel(String value) {
        this.noteToHotel = value;
    }

    /**
     * Gets the value of the noteToElong property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteToElong() {
        return noteToElong;
    }

    /**
     * Sets the value of the noteToElong property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteToElong(String value) {
        this.noteToElong = value;
    }

}
