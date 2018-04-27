package plfx.jp.command.pay;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：创建支付记录COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月17日上午9:34:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreatePayRecordCommand extends BaseCommand{
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	/**
	 * 来源经销商的ip地址 客户端调用时无需带上
	 */
	private String fromDealerIp;
	
	/**
	 * 订单类型
	 * 1：支付易行成功
	 * 2：支付易行失败
	 */
	private Integer payType;


	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getFromDealerIp() {
		return fromDealerIp;
	}

	public void setFromDealerIp(String fromDealerIp) {
		this.fromDealerIp = fromDealerIp;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
