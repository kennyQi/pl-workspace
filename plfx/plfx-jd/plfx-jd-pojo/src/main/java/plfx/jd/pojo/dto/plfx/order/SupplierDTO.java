package plfx.jd.pojo.dto.plfx.order;

import java.util.Date;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
@SuppressWarnings("serial")
public class SupplierDTO extends BaseJDDTO{

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 联系人
	 */
	private String linkName;
	
	/**
	 * 联系电话
	 */
	private String mobile;
	
	/**
	 * 电子邮箱
	 */
	private String email;
	
	/**
	 * QQ
	 */
	private String QQ;
	
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 合同开始时间
	 */
	private Date contractBeginDate;
	
    /**
     * 合同结束时间
     */
	private Date contractEndDate;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	/*** 未审核 **/
	public static Integer NOT_AUDIT = 1;
	/*** 审核不通过 **/
	public static Integer AUDIT_FAIL = 2;
	/*** 已审核 **/
	public static Integer AUDIT_SUCCESS = 3;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Date getContractBeginDate() {
		return contractBeginDate;
	}
	public void setContractBeginDate(Date contractBeginDate) {
		this.contractBeginDate = contractBeginDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
}
