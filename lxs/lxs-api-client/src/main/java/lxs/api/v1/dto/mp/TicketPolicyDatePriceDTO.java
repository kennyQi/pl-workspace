package lxs.api.v1.dto.mp;

import lxs.api.v1.dto.BaseDTO;

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
public class TicketPolicyDatePriceDTO extends BaseDTO {

	/**
	 * 游玩日期(yyyyMMdd)
	 */
	private String date;

	/**
	 * 价格
	 */
	private Double price;

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
