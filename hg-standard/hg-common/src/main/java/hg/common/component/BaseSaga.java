package hg.common.component;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseSaga extends BaseModel implements ColumnDefinitionMysql {

	@Transient
	private List<? extends BaseEvent> events;

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
	@Column(name = "CURRENT_STATUS", columnDefinition = NUM_COLUM)
	private int currentStatus;

	/**
	 * 检查该saga是否已经完成了
	 */
	public abstract boolean checkFinish();

	/**
	 * 接收事件
	 */
	public abstract void handle(BaseEvent event);

	public List<? extends BaseEvent> getEvents() {
		return events;
	}

	public void setEvents(List<? extends BaseEvent> events) {
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
