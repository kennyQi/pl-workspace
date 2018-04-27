package plfx.mp.domain.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class MPOrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否可以取消
	 */
	@Type(type = "yes_no")
	@Column(name = "CANCEL_ABLE")
	private Boolean cancelAble = true;

	/**
	 * 是否已取消
	 */
	@Type(type = "yes_no")
	@Column(name = "CANCEL_")
	private Boolean cancel = false;

	/**
	 * 是否已过期失效
	 */
	@Type(type = "yes_no")
	@Column(name = "OUT_OF_DATE")
	private Boolean outOfDate = false;

	/**
	 * 已预订待消费
	 */
	@Type(type = "yes_no")
	@Column(name = "PREPARED")
	private Boolean prepared = false;

	/**
	 * 已使用过
	 */
	@Type(type = "yes_no")
	@Column(name = "USED")
	private Boolean used = false;

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
