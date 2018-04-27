package hsl.domain.model.dzp.policy;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 政策状态
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketPolicyStatus implements Serializable {

	/**
	 * 是否已被电子票务下架
	 */
	@Type(type = "yes_no")
	@Column(name = "FINISHED")
	private Boolean finished = false;

	/**
	 * 是否已被本平台关闭
	 */
	@Type(type = "yes_no")
	@Column(name = "CLOSED")
	private Boolean closed = false;

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
}
