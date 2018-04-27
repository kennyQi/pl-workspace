package hg.dzpw.pojo.vo;

import java.io.Serializable;

/**
 * @类功能说明：套票（联票）入园统计视图对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:06:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:06:16
 */
public class GroupTicketUseStatisticsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 联票名称
	 */
	private String ticketPolicyName;

	/**
	 * 入园人次
	 */
	private int useCount;

	/**
	 * 日均入园人次
	 */
	private double dayCount;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(Long useCount) {
		if (useCount == null)
			this.useCount = 0;
		else
			this.useCount = useCount.intValue();
	}

	public double getDayCount() {
		return dayCount;
	}

	public void setDayCount(double dayCount) {
		this.dayCount = dayCount;
	}

}
