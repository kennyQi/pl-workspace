package plfx.gnjp.domain.model.order;

import java.io.Serializable;


/**
 * 
 * @类功能说明：订单状态
 * 用户：
 * 待确认               USER_PAY_WAIT
 * 已取消	    USER_TICKET_CANCEL
 * 出票处理中       USER_TICKET_DEALING
 * 出票失败           USER_TICKET_FAIL
 * 已出票               USER_TICKET_SUCC
 * 
 * 退票处理中       USER_TICKET_REFUND_DEALING
 * 退票失败           USER_TICKET_REFUND_FAIL
 * 退票成功           USER_TICKET_REFUND_SUCC
 * 
 * 
 * 未支付            USER_TICKET_NO_PAY
 * 已支付            USER_TICKET_PAY_SUCC
 * 待退款            USER_TICKET_REBACK_WAIT
 * 已退款            USER_TICKET_REBACK_SUCC
 * 
 * 
 * 
 * 商城
 * 待确认               SHOP_PAY_WAIT
 * 已取消		SHOP_TICKET_CANCEL
 * 出票处理中       SHOP_TICKET_DEALING
 * 出票失败           SHOP_TICKET_FAIL
 * 已出票               SHOP_TICKET_SUCC
 * 
 * 
 * 退票处理中       SHOP_TICKET_REFUND_DEALING
 * 退票失败           SHOP_TICKET_REFUND_FAIL
 * 退票成功           SHOP_TICKET_REFUND_SUCC
 * 
 * 
 * 待支付            SHOP_TICKET_NO_PAY
 * 已支付            SHOP_TICKET_PAY_SUCC
 * 已收款           SHOP_TICKET_RECEIVE_PAYMENT_SUCC
 * 待回款           SHOP_TICKET_TO_BE_BACK_WAIT
 * 已回款           SHOP_TICKET_TO_BE_BACK_SUCC
 * 待退款            SHOP_TICKET_REBACK_WAIT
 * 已退款            SHOP_TICKET_REBACK_SUCC
 * 
 * 分销
 * 待确认               PLFX_PAY_WAIT
 * 已取消		PLFX_TICKET_CANCEL
 * 出票处理中       PLFX_TICKET_DEALING
// * 出票待重试       PLFX_TICKET_TRY_AGAIN
 * 出票失败           PLFX_TICKET_FAIL
 * 已出票               PLFX_TICKET_SUCC
 * 
 * 退票处理中       PLFX_TICKET_REFUND_DEALING
 * 退票失败           PLFX_TICKET_REFUND_FAIL
 * 退票成功           PLFX_TICKET_REFUND_SUCC
 * 
 * 待支付             PLFX_TICKET_NO_PAY
 * 已支付             PLFX_TICKET_PAY_SUCC
 * 已收款            PLFX_TICKET_RECEIVE_PAYMENT_SUCC
 * 待回款            PLFX_TICKET_TO_BE_BACK_WAIT
 * 已回款            PLFX_TICKET_TO_BE_BACK_SUCC
 * 待退款             PLFX_TICKET_REBACK_WAIT
 * 已退款             PLFX_TICKET_REBACK_SUCC
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2015年6月30日下午1:27:53
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class GNJPOrderStatus implements Serializable{

	
   /** 订单状态 */
   private Integer status;
   
   /** 支付状态 */
   private Integer payStatus;
   
   public GNJPOrderStatus(Integer status) {
		setStatus(status);
	}

	public GNJPOrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GNJPOrderStatus(Integer status,Integer payStatus) {
		super();
		setStatus(status);
		setPayStatus(payStatus);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

   
}