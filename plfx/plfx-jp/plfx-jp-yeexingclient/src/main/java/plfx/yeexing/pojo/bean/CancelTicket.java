package plfx.yeexing.pojo.bean;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CancelTicket complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CancelTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cancel_notify_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "CancelTicket", propOrder = { "userName", "orderid",
		"passengerName", "cancelNotifyUrl", "sign" })
public class CancelTicket {

	protected String userName;
	protected String orderid;
	protected String passengerName;
	@XmlElement(name = "cancel_notify_url")
	protected String cancelNotifyUrl;
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
	 * Gets the value of the cancelNotifyUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCancelNotifyUrl() {
		return cancelNotifyUrl;
	}

	/**
	 * Sets the value of the cancelNotifyUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCancelNotifyUrl(String value) {
		this.cancelNotifyUrl = value;
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
