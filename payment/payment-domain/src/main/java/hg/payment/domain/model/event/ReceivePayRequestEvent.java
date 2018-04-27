package hg.payment.domain.model.event;

import hg.payment.domain.model.payorder.PayOrder;

import java.util.Date;

/**
 * 
 * @类功能说明：收到商城支付请求事件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月27日下午2:46:03
 *
 */
public class ReceivePayRequestEvent extends BasePayEvent {
	
	public PayOrder payOrderDTO;

	public PayOrder getPayOrder() {
		return payOrderDTO;
	}

	public void setPayOrder(PayOrder payOrderDTO) {
		this.payOrderDTO = payOrderDTO;
	}
	
	
	
}
