package lxs.api.v1.dto.mp;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class GroupTicketDTO extends BaseDTO {

	/**
	 * 政策ID
	 */
	private String ticketPolicyID;

	private String scenicSpotID;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 简称
	 */
	private String shortName;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	private Double rackRate;

	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 图片地址
	 */
	private String url;

	private int validDays;

	private String scenicSpotNameStr;

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public String getTicketPolicyID() {
		return ticketPolicyID;
	}

	public void setTicketPolicyID(String ticketPolicyID) {
		this.ticketPolicyID = ticketPolicyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

}
