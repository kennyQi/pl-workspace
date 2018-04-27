package lxs.pojo.dto.user.user;

public class UserBusinessInfoDTO {
	
	/**
	 * 交易次數
	 */
	private String orderTimes;
	
	/**
	 * 交易額
	 */
	private String tradingVolume;

	public String getOrderTimes() {
		return orderTimes;
	}

	public void setOrderTimes(String orderTimes) {
		this.orderTimes = orderTimes;
	}

	public String getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(String tradingVolume) {
		this.tradingVolume = tradingVolume;
	}
	
	
}
