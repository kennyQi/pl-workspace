package hg.dzpw.pojo.command.merchant.policy;

/**
 * @类功能说明：每日价格
 * @类修改者：
 * @修改日期：2015-2-9下午6:26:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-9下午6:26:36
 */
public class DatePrice {

	/**
	 * 日期字符串 yyyyMMdd
	 */
	private String date;

	/**
	 * 价格
	 */
	private Double price;

	/**
	 * 是否开放销售（为true时开放）
	 */
	private Boolean open = true;

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

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

}
