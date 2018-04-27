package plfx.mp.tcclient.tc.domain;

/**
 * 	每日价格列表
 * @author zhangqy
 *
 */
public class Calendar {
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 价格id
	 */
	private Integer policyId;
	/**
	 * 门票名称
	 */
	private String policyName;
	/**
	 * 支付方式
	 */
	private Integer pMode;
	/**
	 * 同程价格
	 */
	private Double tcPrice;
	/**
	 * 库存量
	 */
	private Integer stock;
	/**
	 * 最小票数
	 */
	private Integer minT;
	/**
	 * 最大票数
	 */
	private Integer maxT;
	/**
	 * 最大可用现金券
	 */
	private Double cash;
	/**
	 * 门票说明
	 */
	private String remark;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public Integer getPMode() {
		return pMode;
	}
	public void setPMode(Integer pMode) {
		this.pMode = pMode;
	}
	public Double getTcPrice() {
		return tcPrice;
	}
	public void setTcPrice(Double tcPrice) {
		this.tcPrice = tcPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getMinT() {
		return minT;
	}
	public void setMinT(Integer minT) {
		this.minT = minT;
	}
	public Integer getMaxT() {
		return maxT;
	}
	public void setMaxT(Integer maxT) {
		this.maxT = maxT;
	}
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
