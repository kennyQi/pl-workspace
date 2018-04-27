package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AlipayCommand extends BaseCommand{

	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 付款金额
	 */
	private Double price;

	public String trade_no;
	
	public String buyer_email;
	
	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

}
