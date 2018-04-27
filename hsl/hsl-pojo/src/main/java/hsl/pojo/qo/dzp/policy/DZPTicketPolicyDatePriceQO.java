package hsl.pojo.qo.dzp.policy;

import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 电子票务-价格日历Qo
 * Created by hgg on 2016/3/7.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpTicketPolicyDatePriceDAO")
public class DZPTicketPolicyDatePriceQO extends BaseQo {

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 日期
	 */
	private String date;

	/**
	 * 价格
	 */
	private Double price;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
