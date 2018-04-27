package plfx.mp.tcclient.tc.domain;
/**
 * 提前预订列表
 * @author zhangqy
 *
 */
public class Ahead {
	/**
	 * 提前预订天数
	 */
	private Integer day;
	/**
	 * 提前预订时间
	 */
	private String time;
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
