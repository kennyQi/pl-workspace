package hsl.domain.model.mp.order;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_USER + "ORDER_USER")
public class MPOrderUser extends BaseModel{

	/**
	 * 用户id
	 */
	@Column(name="USER_ID",length=64)
	private String userId;
	
	/**
	 * 用户名字
	 */
	@Column(name="USER_NAME",length=10)
	private String name;

	/**
	 * 用户身份证号
	 */
	@Column(name="IDCARD_NO",length=18)
	private String idCardNo;

	/**
	 * 用户手机号
	 */
	@Column(name="MOBILE",length=11)
	private String mobile;

	/**
	 * 作为游玩人关联的订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MP_ORDER_ID")
	private MPOrder order;
	
	/**
	 * 组织id
	 */
	@Column(name="COMPANY_ID",length=64)
	private String companyId;
	
	/**
	 * 组织名称
	 */
	@Column(name="COMPANY_NAME",length=64)
	private String companyName;
	
	/**
	 * 部门id
	 */
	@Column(name="DEPAITMENT_ID",length=64)
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	@Column(name="DEPAITMENT_NAME",length=64)
	private String departmentName;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public MPOrder getOrder() {
		return order;
	}

	public void setOrder(MPOrder order) {
		this.order = order;
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


}
