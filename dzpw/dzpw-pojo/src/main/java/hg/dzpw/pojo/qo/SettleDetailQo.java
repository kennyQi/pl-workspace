package hg.dzpw.pojo.qo;

import java.util.Date;

import hg.dzpw.pojo.common.BasePojoQo;

/**
 * 财务明细查询对象
 * @author CaiHuan
 *
 * 日期:2015-12-24
 */
public class SettleDetailQo extends BasePojoQo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 景区id
	 */
	private String scenicSpotId;
	
	/**
	 * 门票政策类型
	 */
	private Integer ticketType;
	
	/**
	 * 查询开始时间
	 */
	private Date dateBegin;
	
	/**
	 * 查询结束时间
	 */
	private Date dateEnd;
	
	/**
	 * 景区状态
	 */
	private Integer scenicSpotStatus;

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}


	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Integer getScenicSpotStatus() {
		return scenicSpotStatus;
	}

	public void setScenicSpotStatus(Integer scenicSpotStatus) {
		this.scenicSpotStatus = scenicSpotStatus;
	}
	
	
}
