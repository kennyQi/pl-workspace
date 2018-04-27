package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 经停信息
 * @author guotx
 * 2015-07-14
 */
@XStreamAlias("stopOver")
public class StopOver {
	/**
	 * 经停机场
	 */
	private String stopAirport;
	/**
	 * 经停时间
	 */
	private String stopDuration;
	public String getStopAirport() {
		return stopAirport;
	}
	public void setStopAirport(String stopAirport) {
		this.stopAirport = stopAirport;
	}
	public String getStopDuration() {
		return stopDuration;
	}
	public void setStopDuration(String stopDuration) {
		this.stopDuration = stopDuration;
	}
}
