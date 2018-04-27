package lxs.domain.model.line;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lxs.domain.model.M;



/**
 * 
 * @类功能说明：线路订单游客信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午3:02:11
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name =M.TABLE_PREFIX_XL+ "LINE_ORDER_TRAVELER")
public class LineOrderTraveler extends BaseModel{
	
	/**
	 * 关联线路订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ORDER_ID")
	private LineOrder lineOrder;
	
	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64 )
	private String name;
	
	/**
	 * 手机号
	 */	
	@Column(name = "MOBILE", length = 11 )
	private String mobile;
	
	/**
	 * 游客类别
	 */
	@Column(name = "TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	public final static Integer TYPE_ADULT = 1;
	public final static Integer TYPE_CHILD = 2;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64 )
	private String idNo;

	/**
	 * 证件类型
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;

	public final static Integer ID_TYPE_SFZ = 1; // 身份证*/	
	/**
	 * 游玩人订单状态
	 */
	@Embedded
	private LineOrderStatus lineOrderStatus;
	
	/**
	 * 单人全款金额
	 */
	@Column(name = "SINGLE_SALE_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double singleSalePrice;
	
	/**
	 * 单人定金
	 */
	@Column(name = "SINGLE_BARGAIN_MONEY", columnDefinition = M.DOUBLE_COLUM)
	private Double singleBargainMoney;
	
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
	public LineOrder getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrder lineOrder) {
		this.lineOrder = lineOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	public LineOrderStatus getLineOrderStatus() {
		return lineOrderStatus;
	}

	public void setLineOrderStatus(LineOrderStatus lineOrderStatus) {
		this.lineOrderStatus = lineOrderStatus;
	}

	public Double getSingleSalePrice() {
		return singleSalePrice;
	}

	public void setSingleSalePrice(Double singleSalePrice) {
		this.singleSalePrice = singleSalePrice;
	}

	public Double getSingleBargainMoney() {
		return singleBargainMoney;
	}

	public void setSingleBargainMoney(Double singleBargainMoney) {
		this.singleBargainMoney = singleBargainMoney;
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

}