package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 机票信息
 * @author guotx
 * 2015-07-10
 */
@XStreamAlias("ticketInfo")
public class TicketInfo{
	/**
	 * 旅客姓名
	 */
	private String passengerName;
	/**
	 * 机票票号
	 */
	private String ordDetEticketNo;
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getOrdDetEticketNo() {
		return ordDetEticketNo;
	}
	public void setOrdDetEticketNo(String ordDetEticketNo) {
		this.ordDetEticketNo = ordDetEticketNo;
	}
	
}