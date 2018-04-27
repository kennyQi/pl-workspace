package hg.framework.common.base;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.List;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseSaga extends BaseStringIdModel {

	@Transient
	private List<? extends BaseSagaEvent> events;

	/**
	 * 是否结束
	 */
	@Type(type = "yes_no")
	@Column(name = "FINISH")
	private boolean finish;

	/**
	 * 是否成功
	 */
	@Type(type = "yes_no")
	@Column(name = "SUCCESS")
	private boolean success;

	/**
	 * 当前状态
	 */
	@Column(name = "CURRENT_STATUS")
	private int currentStatus;

	/**
	 * 检查该saga是否已经完成了
	 */
	public abstract boolean checkFinish();

	/**
	 * 接收事件
	 */
	public abstract void handle(BaseSagaEvent event);

	public List<? extends BaseSagaEvent> getEvents() {
		return events;
	}

	public void setEvents(List<? extends BaseSagaEvent> events) {
		this.events = events;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

}
