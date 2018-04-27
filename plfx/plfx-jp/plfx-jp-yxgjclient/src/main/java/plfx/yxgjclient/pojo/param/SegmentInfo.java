package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 各航段信息
 * @author guotx
 * 2015-07-05
 */
@XStreamAlias("segmentInfo")
public class SegmentInfo {
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 市场方航空公司
	 */
	private String marketingAirline;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 舱位
	 */
	private String cabinCode;
	/**
	 * 机型
	 */
	private String planeType;
	/**
	 * 出发时间
	 * yyyy-MM-dd HH:mm
	 */
	private String startTime;
	/**
	 * 到达时间
	 * yyyy-MM-dd HH:mm
	 */
	private String endTime;
	
	private String isCodeShare;
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getMarketingAirline() {
		return marketingAirline;
	}
	public void setMarketingAirline(String marketingAirline) {
		this.marketingAirline = marketingAirline;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getCabinCode() {
		return cabinCode;
	}
	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}
	public String getPlaneType() {
		return planeType;
	}
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsCodeShare() {
		return isCodeShare;
	}
	public void setIsCodeShare(String isCodeShare) {
		this.isCodeShare = isCodeShare;
	}
}
