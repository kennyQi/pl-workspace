package zzpl.domain.model.workflow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "COMMENT")
public class Comment extends BaseModel {
	/**
	 * 当前处理人ID
	 */
	@Column(name = "CURRENT_USER_ID", length = 512)
	private String currentUserID;

	/**
	 * 当前处理人姓名
	 */
	@Column(name = "CURRENT_USER_NAME", length = 512)
	private String currentUserName;
	
	/**
	 * 流程环节编号
	 */
	@Column(name = "STEP_NO", columnDefinition = M.NUM_COLUM)
	private Integer stepNO;

	/**
	 * 流程环节名称
	 */
	@Column(name = "STEP_NAME", length = 512)
	private String stepName;
	
	/**
	 * 意见JSON
	 */
	@Column(name = "COMMENT_JSON", length = 512)
	private String commentJSON;
	
	/**
	 * 意见类型
	 */
	@Column(name = "COMMENT_TYPE", length = 512)
	private String commentType;
	
	/**
	 * 送出时间
	 */
	@Column(name = "COMMENT_TIME", columnDefinition = M.DATE_COLUM)
	private Date commentTime;

	@OneToOne
	@JoinColumn(name="TASKLIST_ID")
	private Tasklist tasklist;
	
	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public Integer getStepNO() {
		return stepNO;
	}

	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getCommentJSON() {
		return commentJSON;
	}

	public void setCommentJSON(String commentJSON) {
		this.commentJSON = commentJSON;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Tasklist getTasklist() {
		return tasklist;
	}

	public void setTasklist(Tasklist tasklist) {
		this.tasklist = tasklist;
	}
	
}
