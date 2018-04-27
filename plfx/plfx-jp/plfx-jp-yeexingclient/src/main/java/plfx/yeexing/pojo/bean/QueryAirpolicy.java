package plfx.yeexing.pojo.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for QueryAirpolicy complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="QueryAirpolicy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encryptString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airpGet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airpSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tickType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "QueryAirpolicy", propOrder = { "userName", "encryptString",
		"airpGet", "airpSource", "tickType", "sign" })
public class QueryAirpolicy {

	protected String userName;
	protected String encryptString;
	protected String airpGet;
	protected String airpSource;
	protected String tickType;
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
	 * Gets the value of the airpGet property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAirpGet() {
		return airpGet;
	}

	/**
	 * Sets the value of the airpGet property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAirpGet(String value) {
		this.airpGet = value;
	}

	/**
	 * Gets the value of the airpSource property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAirpSource() {
		return airpSource;
	}

	/**
	 * Sets the value of the airpSource property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAirpSource(String value) {
		this.airpSource = value;
	}

	/**
	 * Gets the value of the tickType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTickType() {
		return tickType;
	}

	/**
	 * Sets the value of the tickType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTickType(String value) {
		this.tickType = value;
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
