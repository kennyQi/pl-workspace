package hg.dzpw.dealer.client.dto.ticket;

import hg.dzpw.dealer.client.dto.tourist.TouristDTO;

import java.util.Date;

/**
 * @类功能说明：单票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:09:14
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class SingleTicketDTO extends TicketDTO {

	/**
	 * 所属门票ID
	 */
	private String groupTicketId;

	/**
	 * 对应景区ID
	 */
	private String scenicSpotId;
	/**
	 * 对应景区名称
	 */
	private String scenicName;
	/**
	 * 初次游玩时间
	 */
	private Date firstTimeUseDate;
	/**
	 * 游客[按QO条件决定是否查询]
	 */
	private TouristDTO tourist;
	
	/**
	 * SingleTicket状态
	 */
	private SingleTicketStatusDTO status;
	

	public SingleTicketStatusDTO getStatus() {
		return status;
	}

	public void setStatus(SingleTicketStatusDTO status) {
		this.status = status;
	}

	public TouristDTO getTourist() {
		return tourist;
	}

	public void setTourist(TouristDTO tourist) {
		this.tourist = tourist;
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

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Date getFirstTimeUseDate() {
		return firstTimeUseDate;
	}

	public void setFirstTimeUseDate(Date firstTimeUseDate) {
		this.firstTimeUseDate = firstTimeUseDate;
	}

}