package hg.dzpw.pojo.vo;

import java.io.Serializable;

/**
 * @类功能说明：经销商销售统计视图对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:06:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:06:12
 */
public class DealerSaleStatisticsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商ID
	 */
	private String dealerId;

	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 经销商名称
	 */
	private String dealerName;

	/**
	 * 销量合计
	 */
	private int saleTicketTotalCount;

	/**
	 * 日均销量
	 */
	private double saleTicketDayCount;

	/**
	 * 销售额
	 */
	private double saleTotalAmount;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public int getSaleTicketTotalCount() {
		return saleTicketTotalCount;
	}

	public void setSaleTicketTotalCount(Long saleTicketTotalCount) {
		if (saleTicketTotalCount != null)
			this.saleTicketTotalCount = saleTicketTotalCount.intValue();
		else
			this.saleTicketTotalCount = 0;
	}

	public double getSaleTicketDayCount() {
		return saleTicketDayCount;
	}

	public void setSaleTicketDayCount(double saleTicketDayCount) {
		this.saleTicketDayCount = saleTicketDayCount;
	}

	public double getSaleTotalAmount() {
		return saleTotalAmount;
	}

	public void setSaleTotalAmount(double saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}

}
