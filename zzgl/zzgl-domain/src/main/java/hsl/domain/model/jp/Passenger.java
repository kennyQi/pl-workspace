package hsl.domain.model.jp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JP + "PASSENGER")
public class Passenger extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private FlightOrder order;
	/**
	 * 票号
	 */
	@Column(name = "AIR_ID", columnDefinition = M.TEXT_COLUM)
	private String airId;
	/**
	 *乘客姓名
	 */
	@Column(name="PASSENGERNAME",length=64)
	private String passengerName;
	/**
	 * 乘客类型
	 */
	@Column(name="PASSENGERTYPE",length=64)
	private String passengerType;
	/**
	 * 证件类型
	 */
	@Column(name="IDTYPE",length=64)
	private String idType;
	/**
	 * 证件号
	 */
	@Column(name="IDNO",length=64)
	private String idNo;
	/**
	 * 乘客手机号
	 */
	@Column(name="mobile",length=64)
	private String mobile;
	
	/**
	 * 组织ID
	 */
	@Column(name = "COMPANY_ID")
	private String companyId;
	
	/**
	 * 组织名称
	 */
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID")
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;
	
	/**
	 * 成员ID
	 */
	@Column(name = "MEMBER_ID")
	private String memeberId;

	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public FlightOrder getOrder() {
		return order;
	}
	public void setOrder(FlightOrder order) {
		this.order = order;
	}
	public String getAirId() {
		return airId;
	}
	public void setAirId(String airId) {
		this.airId = airId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getMemeberId() {
		return memeberId;
	}
	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
