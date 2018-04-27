package slfx.xl.pojo.dto;

import java.util.Date;

/**
 * 
 * 
 *@类功能说明：线路供应商DTO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日上午10:08:11
 *
 */
@SuppressWarnings("serial")
public class LineSupplierDTO extends BaseXlDTO{
	
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
	 * 备注
	 */
	private String remark;
	
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	
	

}
