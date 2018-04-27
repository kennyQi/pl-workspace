package hsl.pojo.dto.line.order;

public class LineOrderFirstTopDTO {
	/**
	 * 经销商订单遍号
	 */
	private String dealerOrderNo;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 提前分钟
	 */
	private  String getTime;
	/**
	 * 总人数
	 */
	private Integer countPeople;
	/**
	 * 线路id
	 */
	private String id;
	public String getDealerOrderNo() {
		return dealerOrderNo;
	}
	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	public Integer getCountPeople() {
		return countPeople;
	}
	public void setCountPeople(Integer countPeople) {
		this.countPeople = countPeople;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
