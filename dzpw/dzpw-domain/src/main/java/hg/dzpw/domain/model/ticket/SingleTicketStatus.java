package hg.dzpw.domain.model.ticket;

import hg.dzpw.domain.model.M;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：SingleTicket状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-11-18下午3:05:44
 * @版本：V1.0
 */
@Embeddable
public class SingleTicketStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 待激活(未绑定游玩人信息)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_TOBE_ACTIVE = 0;
	/**
	 * 待游玩(绑定游玩人信息等待游玩)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_UNUSE = 1;
	/**
	 * 已游玩(游客景区游玩)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_USED = 2;
	/**
	 * 已失效(已过期未去景区游玩)
	 */
	public final static Integer SINGLE_TICKET_USE_CURRENT_INVALID = 3;
	/**
	 * 已失效(未支付订单的失效)
	 */
	public final static Integer SINGLE_TICKET_USE_CURRENT_INVALID_II = 4;
	/**
	 * 待退款(态已失效后退款给指定账户时退款失败，并给出退款原因)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_WAIT_REFUND = 5;
	/**
	 * 无须退款(不支持退款的票)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_NO_REFUND = 6;
	/**
	 * 已退款(游失效后退款给指定账户成功)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_REFUNDED = 7;
	/**
	 * 已结算(当前景区已结算)
	 */
	public final static Integer SINGLE_TICKET_CURRENT_SETTLED = 8;
	
	
	/**
	 * 使用状态
	 */
	@Column(name = "CURRENT", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer current;


	public Integer getCurrent() {
		return current;
	}


	public void setCurrent(Integer current) {
		this.current = current;
	}
	
}
