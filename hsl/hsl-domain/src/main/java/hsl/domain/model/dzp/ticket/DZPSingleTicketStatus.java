package hsl.domain.model.dzp.ticket;


import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 单票状态
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPSingleTicketStatus implements HSLConstants.DZPSingleTicketStatus, Serializable {

	/**
	 * 使用状态
	 * <Pre>
	 * 特别注意：这里的值代表的是直销订单的状态，和电子票务不完全一样！
	 * </Pre>
	 *
	 * @see HSLConstants.DZPSingleTicketStatus
	 */
	@Column(name = "SINGLE_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer current;

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}
}
