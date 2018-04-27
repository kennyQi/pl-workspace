package hsl.domain.model.hotel.order;
import hg.common.component.BaseModel;
import hsl.domain.model.M;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * @类功能说明：发票收件人
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月3日下午2:16:45
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JD + "RECIPIENT")
public class Recipient extends BaseModel{
		/**
		 * 省
		 */
		@Column(name = "PROVINCE", length = 64)
	    private String province;
	    /**
	     * 市
	     */
		@Column(name = "CITY", length = 64)
	    private String city;
	    /**
	     * 地区
	     */
		@Column(name = "DISTRICT", length = 64)
	    private String district;
	    /**
	     * 街道
	     */
		@Column(name = "STREET", length = 64)
	    private String street;
	    /**
	     * 邮编
	     */
		@Column(name = "POSTAL_CODE", length = 10)
	    private String postalCode;
	    /**
	     * 姓名
	     */
		@Column(name = "NAME", length = 64)
	    private String name;
	    /**
	     * 电话
	     */
		@Column(name = "PHONE", length = 12)
	    private String phone;
	    /**
	     * 邮件
	     */
		@Column(name = "EMAIL", length = 64)
	    private String email;
		
		
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
}
