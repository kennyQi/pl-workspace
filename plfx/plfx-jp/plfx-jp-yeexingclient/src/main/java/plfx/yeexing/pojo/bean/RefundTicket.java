package plfx.yeexing.pojo.bean;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RefundTicket complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RefundTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refundType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refundMemo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refundSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refund_notify_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RefundTicket", propOrder = { "userName", "orderid",
		"passengerName", "airId", "refundType", "refundMemo", "refundSegment",
		"refundNotifyUrl", "sign" })
public class RefundTicket {

	protected String userName;
	protected String orderid;
	protected String passengerName;
	protected String airId;
	protected String refundType;
	protected String refundMemo;
	protected String refundSegment;
	@XmlElement(name = "refund_notify_url")
	protected String refundNotifyUrl;
	protected String sign;

	/**
	 * Gets the value of the userName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the value of the userName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserName(String value) {
		this.userName = value;
	}

	/**
	 * Gets the value of the orderid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * Sets the value of the orderid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrderid(String value) {
		this.orderid = value;
	}

	/**
	 * Gets the value of the passengerName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassengerName() {
		return passengerName;
	}

	/**
	 * Sets the value of the passengerName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassengerName(String value) {
		this.passengerName = value;
	}

	/**
	 * Gets the value of the airId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAirId() {
		return airId;
	}

	/**
	 * Sets the value of the airId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAirId(String value) {
		this.airId = value;
	}

	/**
	 * Gets the value of the refundType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * Sets the value of the refundType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefundType(String value) {
		this.refundType = value;
	}

	/**
	 * Gets the value of the refundMemo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefundMemo() {
		return refundMemo;
	}

	/**
	 * Sets the value of the refundMemo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefundMemo(String value) {
		this.refundMemo = value;
	}

	/**
	 * Gets the value of the refundSegment property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefundSegment() {
		return refundSegment;
	}

	/**
	 * Sets the value of the refundSegment property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefundSegment(String value) {
		this.refundSegment = value;
	}

	/**
	 * Gets the value of the refundNotifyUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	/**
	 * Sets the value of the refundNotifyUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefundNotifyUrl(String value) {
		this.refundNotifyUrl = value;
	}

	/**
	 * Gets the value of the sign property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Sets the value of the sign property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSign(String value) {
		this.sign = value;
	}

}
