package hsl.domain.model.mp.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class MPOrderStatus {

	/**
	 * 是否已取消
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_CANCEL")
	private Boolean cancel=false;

	/**
	 * 是否已过期失效
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_OUT_OF_DATE")
	private Boolean outOfDate=false;

	/**
	 * 已预订待消费
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_PREPARED")
	private Boolean prepared=false;

	/**
	 * 已使用过
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_USED")
	private Boolean used=false;

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

}
