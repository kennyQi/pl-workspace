package hsl.pojo.dto.hotel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for Detail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Detail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StarRate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ThumbNailUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BusinessZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Review" type="{}Review" minOccurs="0"/>
 *         &lt;element name="Features" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GeneralAmenities" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Traffic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Detail", propOrder = {
    "hotelName",
    "starRate",
    "category",
    "latitude",
    "longitude",
    "address",
    "phone",
    "thumbNailUrl",
    "city",
    "district",
    "businessZone",
    "review",
    "features",
    "generalAmenities",
    "traffic"
})
public class DetailDTO {
	/**
	 * 酒店名称
	 */
    protected String hotelName;
    /**
     * 酒店星级
     */
    protected int starRate;
    /**
     * 艺龙推荐级别
     */
    protected int category;
    /**
     * 维度
     */
    protected String latitude;
    /**
     * 经度
     */
    protected String longitude;
    /**
     * 地址
     */
    protected String address;
    /**
     * 电话
     */
    protected String phone;
    /**
     * 封面图片
     */
    protected String thumbNailUrl;
    /**
     * 城市
     */
    protected String city;
    /**
     * 行政区
     */
    protected String district;
    /**
     * 商业区
     */
    protected String businessZone;
    /**
     * 评价
     */
    protected ReviewDTO review;
    protected String features;
    protected String generalAmenities;
    /**
     * 交通服务
     */
    protected String traffic;

    /**
     * Gets the value of the hotelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the value of the hotelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelName(String value) {
        this.hotelName = value;
    }

    /**
     * Gets the value of the starRate property.
     * 
     */
    public int getStarRate() {
        return starRate;
    }

    /**
     * Sets the value of the starRate property.
     * 
     */
    public void setStarRate(int value) {
        this.starRate = value;
    }

    /**
     * Gets the value of the category property.
     * 
     */
    public int getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     */
    public void setCategory(int value) {
        this.category = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the thumbNailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    /**
     * Sets the value of the thumbNailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbNailUrl(String value) {
        this.thumbNailUrl = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the businessZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessZone() {
        return businessZone;
    }

    /**
     * Sets the value of the businessZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessZone(String value) {
        this.businessZone = value;
    }

    /**
     * Gets the value of the review property.
     * 
     * @return
     *     possible object is
     *     {@link ReviewDTO }
     *     
     */
    public ReviewDTO getReview() {
        return review;
    }

    /**
     * Sets the value of the review property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReviewDTO }
     *     
     */
    public void setReview(ReviewDTO value) {
        this.review = value;
    }

    /**
     * Gets the value of the features property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatures() {
        return features;
    }

    /**
     * Sets the value of the features property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatures(String value) {
        this.features = value;
    }

    /**
     * Gets the value of the generalAmenities property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneralAmenities() {
        return generalAmenities;
    }

    /**
     * Sets the value of the generalAmenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneralAmenities(String value) {
        this.generalAmenities = value;
    }

    /**
     * Gets the value of the traffic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraffic() {
        return traffic;
    }

    /**
     * Sets the value of the traffic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraffic(String value) {
        this.traffic = value;
    }

}
