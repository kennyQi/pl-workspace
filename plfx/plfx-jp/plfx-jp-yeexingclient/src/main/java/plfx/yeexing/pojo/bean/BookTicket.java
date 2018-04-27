package plfx.yeexing.pojo.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for BookTicket complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="BookTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encryptString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengerInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outorderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "BookTicket", propOrder = { "userName", "encryptString",
		"passengerInfo", "orderid", "outorderid", "sign" })
public class BookTicket {

	protected String userName;
	protected String encryptString;
	protected String passengerInfo;
	protected String orderid;
	protected String outorderid;
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
	 * Gets the value of the encryptString property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEncryptString() {
		return encryptString;
	}

	/**
	 * Sets the value of the encryptString property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEncryptString(String value) {
		this.encryptString = value;
	}

	/**
	 * Gets the value of the passengerInfo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassengerInfo() {
		return passengerInfo;
	}

	/**
	 * Sets the value of the passengerInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassengerInfo(String value) {
		this.passengerInfo = value;
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
	 * Gets the value of the outorderid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOutorderid() {
		return outorderid;
	}

	/**
	 * Sets the value of the outorderid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOutorderid(String value) {
		this.outorderid = value;
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
