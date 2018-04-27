package hg.system.model.backlog;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.backlog.CreateBacklogLogCommand;
import hg.system.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 
 *@类功能说明：待办事项日志流水
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月17日下午3:45:54
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "BACKLOG_LOG")
public class BacklogLog extends BaseModel{
	
	/**
	 * 待办事项
	 */
	@ManyToOne
	@JoinColumn(name = "BACKLOG_ID")
	private Backlog backlog;
	
	/**
	 * 操作是否成功
	 */
	@Column(name = "SUCCESS")
	private Boolean success;
	
	/**
	 * 待办事项执行次数
	 */
	@Column(name = "OPERATE_NUM", columnDefinition = M.NUM_COLUM)
	private Integer operateNum;
	
	/**
	 * 操作时间
	 */
	@Column(name = "OPERATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date operateDate;
	
	/**
	 * 操作备注
	 */
	@Column(name = "OPERATE_CONTENT")
	private String operateContent;
	
	public void create(CreateBacklogLogCommand command){
		
		setId(UUIDGenerator.getUUID());
		
		backlog = new Backlog();
		backlog.setId(command.getBacklogId());
		setBacklog(backlog);
		
		success = command.getSuccess();
		operateNum = command.getOperateNum();
		operateDate = new Date();
		operateContent = command.getOperateContent();
		
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public Integer getOperateNum() {
		return operateNum;
	}

	public void setOperateNum(Integer operateNum) {
		this.operateNum = operateNum;
	}
	
	
	
	
	

}
