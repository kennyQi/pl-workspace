package hg.dzpw.pojo.vo;

import java.io.Serializable;

/**
 * @类功能说明：财务汇总对账单视图对象
 * @类修改者：
 * @修改日期：2014-11-14上午10:10:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14上午10:10:32
 */
public class ReconciliationCollectOrderVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	/**
	 * 景区名称
	 */
	private String scenicSpotName;
	/**
	 * 应收金额（除去关闭和失败的所有订单金额总和）
	 */
	private Number price;
	/**
	 * 实收金额（所有已收款订单金额总和）
	 */
	private Number paidAmount;
	/**
	 * 景区结算金额（所有已游玩订单金额总和）
	 */
	private Number settlementAmount;
	/**
	 * 门票总数
	 */
	private Number groupTicketTotalNumber;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Number getPrice() {
		if (price == null)
			return 0;
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public Number getPaidAmount() {
		if (paidAmount == null)
			return 0;
		return paidAmount;
	}

	public void setPaidAmount(Number paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Number getSettlementAmount() {
		if (settlementAmount == null)
			return 0;
		return settlementAmount;
	}

	public void setSettlementAmount(Number settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Number getGroupTicketTotalNumber() {
		if (groupTicketTotalNumber == null)
			return 0;
		return groupTicketTotalNumber;
	}

	public void setGroupTicketTotalNumber(Number groupTicketTotalNumber) {
		this.groupTicketTotalNumber = groupTicketTotalNumber;
	}

}
