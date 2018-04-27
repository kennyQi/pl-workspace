package plfx.mp.pojo.dto.platformpolicy;

import plfx.mp.pojo.dto.EmbeddDTO;

public class SalePolicySnapshotStatusDTO extends EmbeddDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策状态（冗余字段） 未发布，已发布，未开始，已开始，已下架，已取消
	 */
	private Integer status;

	/**
	 * 是否最新快照
	 */
	private Boolean lastSnapshot;

	/**
	 * 是否已发布
	 */
	private Boolean deploy;
	/**
	 * 是否已开始
	 */
	private Boolean start;
	/**
	 * 是否已取消
	 */
	private Boolean cancel;
	/**
	 * 是否已下架
	 */
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
