package hsl.pojo.command.jp.plfx;

import hg.common.component.BaseCommand;

/****
 * 
 * @类功能说明：航班机票申请取消command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:04:58
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CancelTicketGNCommand extends  BaseCommand{

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 乘客姓名 
	 * 多个乘客之间用 ^ 分隔
	 */
	private String passengerName;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
}
