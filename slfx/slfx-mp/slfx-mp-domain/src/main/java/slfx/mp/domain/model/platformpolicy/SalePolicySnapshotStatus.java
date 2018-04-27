package slfx.mp.domain.model.platformpolicy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import slfx.mp.domain.model.M;

@Embeddable
public class SalePolicySnapshotStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策状态（冗余字段） 未发布，已发布，未开始，已开始，已下架，已取消
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer status;

	/**
	 * 是否最新快照
	 */
	@Type(type = "yes_no")
	@Column(name = "LAST_SNAPSHOT")
	private Boolean lastSnapshot;

	/**
	 * 是否已发布
	 */
	@Type(type = "yes_no")
	@Column(name = "DEPLOY")
	private Boolean deploy;
	/**
	 * 是否已开始
	 */
	@Type(type = "yes_no")
	@Column(name = "START_")
	private Boolean start;
	/**
	 * 是否已取消
	 */
	@Type(type = "yes_no")
	@Column(name = "CANCEL_")
	private Boolean cancel;
	/**
	 * 是否已下架
	 */
	@Type(type = "yes_no")
	@Column(name = "PAST")
	private Boolean past;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(Boolean lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public Boolean getDeploy() {
		return deploy;
	}

	public void setDeploy(Boolean deploy) {
		this.deploy = deploy;
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public Boolean getPast() {
		return past;
	}

	public void setPast(Boolean past) {
		this.past = past;
	}

}
