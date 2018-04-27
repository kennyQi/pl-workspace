package slfx.xl.domain.model.log;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import slfx.xl.domain.model.M;
import slfx.xl.domain.model.salepolicy.SalePolicy;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyWorkLogCommand;

@Entity
@Table(name = M.TABLE_PREFIX+"SALE_POLICY_LOG")
public class SalePolicyWorkLog extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	/**
	 *日志操作时间
	 */
	@Column(name = "LOG_DATE", columnDefinition = M.DATE_COLUM)
	private Date logDate;
	
	/**
	 * 操作名称
	 */
	@Column(name = "LOG_NAME", length = 64)
	private   String     salePolicyLogName;
	
	/**
	 * 操作人
	 */
	@Column(name = "LOG_WORKER", length = 64)
	private    String     salePolicyWorkerName;
	
	/**
	 * 操作信息
	 */
	@Column(name = "LOG_INFO", length = 512)
	private    String     salePolicyLogInfoName;
	
	/**
	 * 价格政策基本信息
	 */
	@ManyToOne
	@JoinColumn(name="SALEPOLICY_ID")
	private  SalePolicy   salePolicy;

	/**
	 * @方法功能说明：创建价格政策日志
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18上午10:20:41
	 * @param command
	 */
	public void create(CreateSalePolicyWorkLogCommand command){
		setId(UUIDGenerator.getUUID());
		setLogDate(new Date());
		//操作名称
		setSalePolicyLogName(command.getLogName());
		//操作人
		setSalePolicyWorkerName(command.getWorkerName());
		//操作信息
		setSalePolicyLogInfoName(command.getLogInfoName());
		//价格政策
		SalePolicy policy = new SalePolicy();
		policy.setId(command.getPolicyId());
		setSalePolicy(policy);
	}
	
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getSalePolicyLogName() {
		return salePolicyLogName;
	}
	public void setSalePolicyLogName(String salePolicyLogName) {
		this.salePolicyLogName = salePolicyLogName;
	}
	public String getSalePolicyWorkerName() {
		return salePolicyWorkerName;
	}
	public void setSalePolicyWorkerName(String salePolicyWorkerName) {
		this.salePolicyWorkerName = salePolicyWorkerName;
	}
	public String getSalePolicyLogInfoName() {
		return salePolicyLogInfoName;
	}
	public void setSalePolicyLogInfoName(String salePolicyLogInfoName) {
		this.salePolicyLogInfoName = salePolicyLogInfoName;
	}
	public SalePolicy getSalePolicy() {
		return salePolicy;
	}
	public void setSalePolicy(SalePolicy salePolicy) {
		this.salePolicy = salePolicy;
	}
}