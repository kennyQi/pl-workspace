package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import hsl.pojo.util.HSLConstants;

/**
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderPassengerDAO")
public class YXJPOrderPassengerQO extends BaseQo {

	/**
	 * 所属订单
	 */
	@QOAttr(name = "fromOrder.id")
	private String fromOrderId;

	// 基本信息

	/**
	 * 姓名
	 */
	@QOAttr(name = "baseInfo.name", ifTrueUseLike = "nameLike")
	private String name;
	private boolean nameLike;

	/**
	 * 手机号
	 */
	@QOAttr(name = "baseInfo.mobile")
	private String mobile;

	/**
	 * 证件号
	 */
	@QOAttr(name = "baseInfo.idNo")
	private String idNo;

	/**
	 * 证件类型
	 *
	 * @see HSLConstants.Traveler
	 */
	@QOAttr(name = "baseInfo.idType")
	private Integer idType;

	/**
	 * 乘客类型（目前只支持成人）
	 *
	 * @see HSLConstants.Traveler
	 */
	@QOAttr(name = "baseInfo.type")
	private Integer type;

	// 员工在公司的信息（企业账户使用通讯录里的员工下单才有）

	/**
	 * 是否有所属公司
	 */
	private Boolean hasCompany;

	/**
	 * 组织ID
	 */
	@QOAttr(name = "companyInfo.companyId")
	private String companyId;

	/**
	 * 组织名称
	 */
	@QOAttr(name = "companyInfo.companyName")
	private String companyName;

	/**
	 * 部门ID
	 */
	@QOAttr(name = "companyInfo.departmentId")
	private String departmentId;

	/**
	 * 部门名称
	 */
	@QOAttr(name = "companyInfo.departmentName")
	private String departmentName;

	/**
	 * 成员ID
	 */
	@QOAttr(name = "companyInfo.memeberId")
	private String memeberId;

	// 机票信息

	/**
	 * 机票票号
	 */
	@QOAttr(name = "ticket.ticketNo")
	private String ticketNo;

	/**
	 * 机票订单状态
	 *
	 * @see HSLConstants.YXJPOrderPassengerTicket
	 */
	@QOAttr(name = "ticket.status")
	private Integer status;

	/**
	 * 包含机票订单状态
	 *
	 * @see HSLConstants.YXJPOrderPassengerTicket
	 */
	@QOAttr(name = "ticket.status", type = QOAttrType.IN)
	private Integer[] statuses;

	/**
	 * 航班信息
	 */
	@QOAttr(name = "ticket.flight", type = QOAttrType.JOIN)
	private YXJPOrderFlightQO flightQO;

	public String getFromOrderId() {
		return fromOrderId;
	}

	public void setFromOrderId(String fromOrderId) {
		this.fromOrderId = fromOrderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer... statuses) {
		this.statuses = statuses;
	}

	public YXJPOrderFlightQO getFlightQO() {
		return flightQO;
	}

	public void setFlightQO(YXJPOrderFlightQO flightQO) {
		this.flightQO = flightQO;
	}

	public Boolean getHasCompany() {
		return hasCompany;
	}

	public void setHasCompany(Boolean hasCompany) {
		this.hasCompany = hasCompany;
	}
}
