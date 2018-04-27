package plfx.yxgjclient.pojo.param;

import java.util.List;
/**
 * 
 * @author guotx
 * 时间 2015-07-23
 * BackAvailJournet和TakeoffAvailJourney基类
 * 解决解析xml为对象遇到找不到类异常
 *
 */
public class AvailJourney {
	/**
	 * 航班信息
	 */
	private List<Flight> flights;
	/**
	 * 单向飞行总时间
	 */
	private String totalDuration;
	/**
	 * 单向中转总停留时间
	 */
	private String transferTime;
	/**
	 * 运价信息
	 */
	private List<FareInfo> fareInfos;
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	public String getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public List<FareInfo> getFareInfos() {
		return fareInfos;
	}
	public void setFareInfos(List<FareInfo> fareInfos) {
		this.fareInfos = fareInfos;
	}
	
}
