package hg.dzpw.pojo.command.platform.policy;

/**
 * @类功能说明：联票下的单票
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-11-20下午4:08:37
 * @版本：V1.0
 *
 */
public class SingleTicketPolicy {
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	/**
	 * 结算价
	 */
	private Double settlementPrice;
	/**
	 * 游玩理财价
	 */
	private Double playPrice;
	/**
	 * 市场票面价
	 */
	private Double rackRate;
	/**
	 * 可连续游玩天数
	 */
	private Integer validDays;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

}
