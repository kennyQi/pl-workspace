package hsl.pojo.dto.hotel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for Review complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Review">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Good" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Poor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Score" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Review")
public class ReviewDTO {
	/**
	 * 评价数量
	 */
    protected String count;
    /**
     * 好评数
     */
    protected String good;
    /**
     * 差评数
     */
    protected String poor;
    /**
     * 好评率
     */
    protected String score;

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * Gets the value of the good property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGood() {
        return good;
    }

    /**
     * Sets the value of the good property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGood(String value) {
        this.good = value;
    }

    /**
     * Gets the value of the poor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoor() {
        return poor;
    }

    /**
     * Sets the value of the poor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoor(String value) {
        this.poor = value;
    }

    /**
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScore(String value) {
        this.score = value;
    }

}
