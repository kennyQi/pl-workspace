package plfx.yeexing.pojo.command.order;

@SuppressWarnings("serial")
public class JPOrderCancelCommand extends JPBaseCommand{
	/**
	 * 乘客姓名
	 * 获取时使用urldecode解密
	 */
	private String passengerName;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
}
