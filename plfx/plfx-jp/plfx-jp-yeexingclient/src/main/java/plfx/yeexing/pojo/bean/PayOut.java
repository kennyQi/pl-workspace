package plfx.yeexing.pojo.bean;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PayOut complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PayOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payPlatform" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pay_notify_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="out_notify_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "PayOut", propOrder = { "userName", "orderid", "totalPrice",
		"payPlatform", "payNotifyUrl", "outNotifyUrl", "sign" })
public class PayOut {

	protected String userName;
	protected String orderid;
	protected String totalPrice;
	protected String payPlatform;
	@XmlElement(name = "pay_notify_url")
	protected String payNotifyUrl;
	@XmlElement(name = "out_notify_url")
	protected String outNotifyUrl;
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
	 * Gets the value of the totalPrice property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the value of the totalPrice property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTotalPrice(String value) {
		this.totalPrice = value;
	}

	/**
	 * Gets the value of the payPlatform property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPayPlatform() {
		return payPlatform;
	}

	/**
	 * Sets the value of the payPlatform property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPayPlatform(String value) {
		this.payPlatform = value;
	}

	/**
	 * Gets the value of the payNotifyUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	/**
	 * Sets the value of the payNotifyUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPayNotifyUrl(String value) {
		this.payNotifyUrl = value;
	}

	/**
	 * Gets the value of the outNotifyUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOutNotifyUrl() {
		return outNotifyUrl;
	}

	/**
	 * Sets the value of the outNotifyUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOutNotifyUrl(String value) {
		this.outNotifyUrl = value;
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
