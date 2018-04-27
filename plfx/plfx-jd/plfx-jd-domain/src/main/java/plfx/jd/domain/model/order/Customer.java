package plfx.jd.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jd.domain.model.M;
import plfx.jd.pojo.dto.ylclient.order.ContactDTO;
import plfx.jd.pojo.dto.ylclient.order.CustomerDTO;
import plfx.jd.pojo.system.enumConstants.EnumGender;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月3日下午2:57:51
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "CUSTOMER")
public class Customer extends BaseModel{
	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * Email
	 */
	@Column(name = "EMAIL", length = 64)
	private String email;

	/**
	 * 手机
	 */
	@Column(name = "MOBILE", length = 64)
	private String mobile;

	/**
	 * 电话
	 */
	@Column(name = "PHONE", length = 64)
	private String phone;

	/**
	 * 传真
	 */
	@Column(name = "FAX", length = 64)
	private String fax;

	/**
	 * 性别
	 */
	@Column(name = "GENDER",columnDefinition = M.NUM_COLUM)
	private Integer gender;

	/**
	 * 证件类型
	 */
	@Column(name = "ID_TYPE",columnDefinition = M.NUM_COLUM)
	private Integer idType;

	/**
	 * 证件号码
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;

	/**
	 * 国籍
	 */
	@Column(name = "NATIONALITY", length = 64)
	private String nationality;

	/**
	 * 酒店确认号
	 */
	@Column(name = "CONFIRMATION_NUMBER", length = 100)
	private String confirmationNumber;
	/**
	 * 酒店订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JD_ORDER_ID")
	private HotelOrder hotelOrder;
	/**
	 * 客户类型
	 */
	@Column(name = "TYPE")
	private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getIdType() {
		return idType;
	}
	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getConfirmationNumber() {
		return confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public HotelOrder getHotelOrder() {
		return hotelOrder;
	}
	public void setHotelOrder(HotelOrder hotelOrder) {
		this.hotelOrder = hotelOrder;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void create(CustomerDTO customerDTO,HotelOrder order,String type) {
		setId(UUIDGenerator.getUUID());
		setEmail(customerDTO.getEmail());
		if(customerDTO.getGender() == EnumGender.Female){
			setGender(0);
		}else if(customerDTO.getGender() == EnumGender.Maile){
			setGender(1);
		}else{
			setGender(2);
		}
		setMobile(customerDTO.getMobile());
		setName(customerDTO.getName());
		setNationality(customerDTO.getNationality());
		setPhone(customerDTO.getPhone());
		setHotelOrder(order);
		setType(type);
	}
	public void create(ContactDTO contactDTO, HotelOrder order) {
		setId(UUIDGenerator.getUUID());
		setEmail(contactDTO.getEmail());
		if(contactDTO.getGender() == EnumGender.Female){
			setGender(0);
		}else if(contactDTO.getGender() == EnumGender.Maile){
			setGender(1);
		}else{
			setGender(2);
		}
		setMobile(contactDTO.getMobile());
		setName(contactDTO.getName());
		setPhone(contactDTO.getPhone());
		setFax(contactDTO.getFax());
		setHotelOrder(order);
		setType("1");
	}
}
