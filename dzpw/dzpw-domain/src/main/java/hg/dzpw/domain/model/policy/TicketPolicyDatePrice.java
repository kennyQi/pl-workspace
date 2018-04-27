package hg.dzpw.domain.model.policy;

import java.io.Serializable;

/**
 * @类功能说明：门票每日价格
 * @类修改者：
 * @修改日期：2015-2-10下午2:43:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-10下午2:43:04
 */
@SuppressWarnings("serial")
public class TicketPolicyDatePrice implements Serializable {

	/**
	 * 日期(yyyyMMdd)
	 * 在景区独立单票中是游玩日期，
	 * 在联票中是销售日期。
	 */
	private String date;

	/**
	 * 价格
	 */
	private Double price;

	/**
	 * 是否开放销售（为true时开放）
	 */
	private boolean open = true;

	public TicketPolicyDatePrice() {
	}

	public TicketPolicyDatePrice(String date, Double price, boolean open) {
		this.date = date;
		this.price = price;
		this.open = open;
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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
