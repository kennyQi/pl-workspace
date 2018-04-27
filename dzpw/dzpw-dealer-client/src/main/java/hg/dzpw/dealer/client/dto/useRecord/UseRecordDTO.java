package hg.dzpw.dealer.client.dto.useRecord;

import java.util.Date;
import hg.dzpw.dealer.client.common.BaseDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;

@SuppressWarnings("serial")
public class UseRecordDTO extends BaseDTO {
	
	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 核销设备标识
	 */
//	private String deviceId;
	/**
	 * 门票使用时间
	 */
	private Date useDate;
	/**
	 * 单票ID
	 */
	private String singleTicketId;
	/**
	 * 票号ID
	 */
	private String groupTicketId;
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	/**
	 * 景区名称
	 */
	private String scenicName;
	/**
	 * 入园核销方式 1、扫描身份证 2、填写证件号 3、扫描二维码
	 */
	private int checkType;
	
	private ScenicSpotDTO scenicSpot;
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
//	public String getDeviceId() {
//		return deviceId;
//	}
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getSingleTicketId() {
		return singleTicketId;
	}
	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}
	public String getGroupTicketId() {
		return groupTicketId;
	}
	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
	public ScenicSpotDTO getScenicSpot() {
		return scenicSpot;
	}
	public void setScenicSpot(ScenicSpotDTO scenicSpot) {
		this.scenicSpot = scenicSpot;
	}
	public String getScenicName() {
		return scenicName;
	}
	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
	
}
