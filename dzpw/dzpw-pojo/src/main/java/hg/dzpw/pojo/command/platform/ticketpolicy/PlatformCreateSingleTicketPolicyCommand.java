package hg.dzpw.pojo.command.platform.ticketpolicy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

@SuppressWarnings("serial")
public class PlatformCreateSingleTicketPolicyCommand extends DZPWPlatformBaseCommand{
	
	/**
	 * 可游玩景区Id
	 */
	private String scenicSpotId;
	/**
	 * 基准价
	 */
	private Double scenicSpotPrice;
	
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public Double getScenicSpotPrice() {
		return scenicSpotPrice;
	}
	public void setScenicSpotPrice(Double scenicSpotPrice) {
		this.scenicSpotPrice = scenicSpotPrice;
	}
	
}
