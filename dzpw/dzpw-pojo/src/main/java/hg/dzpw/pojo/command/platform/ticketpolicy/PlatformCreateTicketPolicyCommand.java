package hg.dzpw.pojo.command.platform.ticketpolicy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;
import hg.dzpw.pojo.common.util.StringFilterUtil;
import java.util.Date;

/**
 * @类功能说明：创建门票策略
 * @类修改者：
 * @修改日期：2014-11-12上午10:43:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-11-12上午10:43:37
 */
public class PlatformCreateTicketPolicyCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 联票名称
	 */
	private String name;
	
	/**
	 * 联票代码
	 */
	private String key;
	
	/**
	 * 数量
	 */
	private Integer remainingQty;
	
	/**
	 * 固定有效期开始时间
	 */
	private Date fixedUseDateStart;

	/**
	 * 固定有效期结束时间
	 */
	private Date fixedUseDateEnd;

	/**
	 * 单票可入园天数
	 */
	private Integer validDays;
	
	/**
	 * 可游玩景区
	 */
	private String[] scenicSpotIds;
	
	/**
	 * 基准价
	 */
	private Double[] scenicSpotPrices;
	
	/**
	 * 门市价
	 */
	private Double rackRate;
	
	/**
	 * 预定须知
	 */
	private String notice;
	
	/**
	 * 景点简介
	 */
	private String intro;
	
	/**
	 * 交通指南
	 */
	private String traffic;
	/**
	 * 包含景点组成的字符串
	 */
	private String scenicSpotNameStr;
	
	/**
	 * 创建的管理者id
	 */
	private String createAdminId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : StringFilterUtil.reverseString(name.trim());
	}
	public Integer getRemainingQty() {
		return remainingQty;
	}
	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}
	public Date getFixedUseDateStart() {
		return fixedUseDateStart;
	}
	public void setFixedUseDateStart(Date fixedUseDateStart) {
		this.fixedUseDateStart = fixedUseDateStart;
	}
	public Date getFixedUseDateEnd() {
		return fixedUseDateEnd;
	}
	public void setFixedUseDateEnd(Date fixedUseDateEnd) {
		this.fixedUseDateEnd = fixedUseDateEnd;
	}
	public Integer getValidDays() {
		return validDays;
	}
	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}
	public Double[] getScenicSpotPrices() {
		return scenicSpotPrices;
	}
	public void setScenicSpotPrices(Double[] scenicSpotPrices) {
		this.scenicSpotPrices = scenicSpotPrices;
	}
	public Double getRackRate() {
		return rackRate;
	}
	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice == null ? null : notice.trim();
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic == null ? null : traffic.trim();
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key == null ? null : StringFilterUtil.reverseString(key.trim());
	}
	public String[] getScenicSpotIds() {
		return scenicSpotIds;
	}
	public void setScenicSpotIds(String[] scenicSpotIds) {
		this.scenicSpotIds = scenicSpotIds;
	}
	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}
	public String getCreateAdminId() {
		return createAdminId;
	}
	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId;
	}
	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}
}