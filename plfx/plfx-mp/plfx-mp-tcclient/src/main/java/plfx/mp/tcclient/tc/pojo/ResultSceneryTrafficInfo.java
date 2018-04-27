package plfx.mp.tcclient.tc.pojo;


/**
 * 用于调用同城获取景区交通信息请求返回结果
 * @author zhangqy
 *
 */
public class ResultSceneryTrafficInfo extends Result {
	/**
	 * 景点Id
	 */
	private Integer sceneryId;
	/**
	 * 景点经度
	 */
	private Double longitude;
	/**
	 * 景点纬度
	 */
	private Double latitude;
	/**
	 * 交通指南
	 */
	private String traffic;
	
	public Integer getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(Integer sceneryId) {
		this.sceneryId = sceneryId;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
}	
