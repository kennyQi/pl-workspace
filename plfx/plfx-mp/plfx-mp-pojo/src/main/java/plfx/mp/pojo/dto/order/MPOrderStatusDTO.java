package plfx.mp.pojo.dto.order;

import plfx.mp.pojo.dto.EmbeddDTO;

public class MPOrderStatusDTO extends EmbeddDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否可以取消
	 */
	private Boolean cancelAble;

	/**
	 * 是否已取消
	 */
	private Boolean cancel;

	/**
	 * 是否已过期失效
	 */
	private Boolean outOfDate;

	/**
	 * 已预订待消费
	 */
	private Boolean prepared;

	/**
	 * 已使用过
	 */
	private Boolean used;

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public Boolean getOutOfDate() {
		return outOfDate;
	}

	public void setOutOfDate(Boolean outOfDate) {
		this.outOfDate = outOfDate;
	}

	public Boolean getPrepared() {
		return prepared;
	}

	public void setPrepared(Boolean prepared) {
		this.prepared = prepared;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Boolean getCancelAble() {
		return cancelAble;
	}

	public void setCancelAble(Boolean cancelAble) {
		this.cancelAble = cancelAble;
	}

}
