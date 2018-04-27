package pl.cms.domain.entity.contribution;

import java.util.Date;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import pl.cms.domain.entity.M;
import pl.cms.pojo.command.contribution.CheckContributionCommand;

/**
 * 
 * @类功能说明：稿件操作记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午2:36:09
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "CONTRIBUTION_RECORD")
public class ContributionRecord extends BaseModel {

	/**
	 * 审核的稿件
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRIBUTION_ID")
	private Contribution contribution;
	
	@Column(name = "ADMIN_ID", length = 64)
	private String adminId;

	@Column(name = "ADMIN_NAME", length = 64)
	private String adminName;


	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	@Column(name = "REMARK", length = 1024)
	private String remark;
	
	@Column(name = "CHECK_STATUS")
	@Type(type = "yes_no")
	private Boolean checkStatus;
	
	public void createContributionRecord(CheckContributionCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setAdminId(command.getAdminId());
		this.setAdminName(command.getAdminName());
		this.setRemark(command.getRemark());
		this.setCreateDate(new Date());
		this.setCheckStatus(command.getCheckStatus());
	}
	
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Contribution getContribution() {
		return contribution;
	}

	public void setContribution(Contribution contribution) {
		this.contribution = contribution;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}
