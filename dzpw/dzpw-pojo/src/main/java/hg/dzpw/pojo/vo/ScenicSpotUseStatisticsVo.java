package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：景区入园统计视图对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:29:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:29:07
 */
public class ScenicSpotUseStatisticsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 景区名称
	 */
	private String scenicSpotName;

	/**
	 * 入园人次
	 */
	private int useCount;

	/**
	 * 日均入园人次
	 */
	private double dayCount;
	
	private Integer ticketPolicyType;
	/**
	 * 游客姓名
	 */
	private String touristName;
	/**
	 * 游客证件号码
	 */
	private String tourisIdNo;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 票务编号
	 */
	private String ticketNo;
	/**
	 * 入园时间
	 */
	private Date enterDate;
	/**
	 * 单票张数
	 */
	private Integer sigleTicketNum;
	/**
	 * 联票张数
	 */
	private Integer groupTicketNum;
	/**
	 * 产品名称
	 */
	private String policyName;
	
	private String singleTicketId;
	
	private String groupTicketId;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
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

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getTouristName() {
		return touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}

	public String getTourisIdNo() {
		return tourisIdNo;
	}

	public void setTourisIdNo(String tourisIdNo) {
		this.tourisIdNo = tourisIdNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public Integer getSigleTicketNum() {
		return sigleTicketNum;
	}

	public void setSigleTicketNum(Integer sigleTicketNum) {
		this.sigleTicketNum = sigleTicketNum;
	}

	public Integer getGroupTicketNum() {
		return groupTicketNum;
	}

	public void setGroupTicketNum(Integer groupTicketNum) {
		this.groupTicketNum = groupTicketNum;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

}
