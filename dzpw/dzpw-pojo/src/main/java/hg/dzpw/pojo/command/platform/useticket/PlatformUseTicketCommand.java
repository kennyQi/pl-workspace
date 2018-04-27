package hg.dzpw.pojo.command.platform.useticket;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;


/**
 * @类功能说明：核销门票COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-28下午3:38:40
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PlatformUseTicketCommand extends DZPWPlatformBaseCommand{
	
	/**
	 * 票号
	 */
	private String ticketNo;
	
	private String checkWay;
	
	/**
	 * 应急APP使用参数
	 */
//	private String secretKey;
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	/**
	 * 设备唯一标识
	 */
	private String deviceId;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getCheckWay() {
		return checkWay;
	}

	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

/*	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}*/
	
}
