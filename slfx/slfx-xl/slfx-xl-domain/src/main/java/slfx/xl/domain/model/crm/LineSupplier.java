package slfx.xl.domain.model.crm;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.xl.domain.model.M;
import slfx.xl.pojo.command.supplier.AuditLineSupplierCommand;
import slfx.xl.pojo.command.supplier.CreateLineSupplierCommand;
import slfx.xl.pojo.command.supplier.ModifyLineSupplierCommand;

/**
 * 
 * @类功能说明：供应商
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日上午10:38:03
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SUPPLIER")
@SuppressWarnings("serial")
public class LineSupplier extends BaseModel {

	/**
	 * 名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 联系人
	 */
	@Column(name = "LINK_NAME")
	private String linkName;
	
	/**
	 * 联系电话
	 */
	@Column(name = "MOBILE")
	private String mobile;
	
	/**
	 * 电子邮箱
	 */
	@Column(name = "EMAIL")
	private String email;
	
	/**
	 * QQ
	 */
	@Column(name = "QQ")
	private String QQ;
	
	/**
	 * 传真
	 */
	@Column(name = "FAX")
	private String fax;
	
	/**
	 * 合同开始时间
	 */
	@Column(name = "CONTRACT_BEGIN_DATE")
	private Date contractBeginDate;
	
    /**
     * 合同结束时间
     */
	@Column(name = "CONTRACT_END_DATE")
	private Date contractEndDate;
	
	/**
	 * 状态
	 */
	@Column(name = "STATUS")
	private Integer status;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	
	
	/*** 未审核 **/
	public static Integer NOT_AUDIT = 1;
	/*** 审核不通过 **/
	public static Integer AUDIT_FAIL = 2;
	/*** 已审核 **/
	public static Integer AUDIT_SUCCESS = 3;
	
	/**
	 * 添加线路供应商
	 * @param command
	 */
	public void create(CreateLineSupplierCommand command){
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setLinkName(command.getLinkName());
		setMobile(command.getMobile());
		setFax(command.getFax());
		setEmail(command.getEmail());
		setQQ(command.getQQ());
		setContractBeginDate(command.getContractBeginDate());
		setContractEndDate(command.getContractEndDate());
		setStatus(NOT_AUDIT); //新增线路供应商默认未审核
		setCreateDate(new Date());
	}
	
	/**
	 * 修改线路供应商信息
	 * @param command
	 */
	public void update(ModifyLineSupplierCommand command){
		setName(command.getName());
		setLinkName(command.getLinkName());
		setMobile(command.getMobile());
		setFax(command.getFax());
		setEmail(command.getEmail());
		setQQ(command.getQQ());
		setContractBeginDate(command.getContractBeginDate());
		setContractEndDate(command.getContractEndDate());
		setStatus(NOT_AUDIT); //修改线路供应商信息后状态为未审核
	}
	

	/**
	 * 审核
	 */
	public void audit(AuditLineSupplierCommand command){
		setStatus(command.getStatus());
		setRemark(command.getRemark());
	}
	
	
	
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