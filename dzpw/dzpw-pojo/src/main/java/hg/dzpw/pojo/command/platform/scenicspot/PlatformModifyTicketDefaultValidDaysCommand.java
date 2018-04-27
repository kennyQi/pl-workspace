package hg.dzpw.pojo.command.platform.scenicspot;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：修改景区门票有效期默认天数
 * @类修改者：
 * @修改日期：2014-11-12下午5:51:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12下午5:51:00
 */
public class PlatformModifyTicketDefaultValidDaysCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 门票有效期默认天数
	 */
	private Integer days;

	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId == null ? null : scenicSpotId.trim();
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
}