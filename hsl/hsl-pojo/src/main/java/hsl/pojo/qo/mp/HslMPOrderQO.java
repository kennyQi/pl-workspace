package hsl.pojo.qo.mp;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HslMPOrderQO extends BaseQo {
	
	/**
	 * 商城订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 平台订单号
	 */
	private String platformOrderCode;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 显示全部订单明细
	 */
	private Boolean detail;
	
	/**
	 * 政策ID
	 */
	private String policyId;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	/**
	 * 是否查询订单相关景区信息
	 */
	private Boolean withScenicSpot=false;
	
	/**
	 * 是否查询订单相关政策信息
	 */
	private Boolean withPolicy=false;
	
	/**
	 * （下单）起始时间
	 */
	private String beginTime;
	
	/**
	 * （下单）结束时间
	 */
	private String EndTime;
	/**
	 * 游玩时间范围
	 */
	private String startTravelTime;
	
	private String endTravelTime;
	
	/**
	 * 取单人id即成员id
	 */
	private String memberId;
	/**
	 * 预定人
	 */
	private String bookMan;
	/**
	 * 支付状态
	 */
	private String paymentStatus;
	
	/**
	 * 订单状态(1:已取消;2:已过期失效;3:已预订待消费;4:已使用过;)
	 */
	private Integer orderStatus;
	
	/**
	 * 支付类型
	 */
	private Integer paymentType;
	
	/**
	 * 景点名称是否支持模糊查询
	 */
	private Boolean scenicSpotsNameLike=false ;
	
	/**
	 * 景点名称
	 */
	private String scenicSpotsName;
	
	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 返回条数
	 */
	private Integer pageSize;
	
	/**
	 * 游玩人
	 */
	private String player;
	/**
	 * 是否全部为member
	 */
	private boolean showMember=false;
	public boolean isShowMember() {
		return showMember;
	}

	public void setShowMember(boolean showMember) {
		this.showMember = showMember;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getStartTravelTime() {
		return startTravelTime;
	}

	public void setStartTravelTime(String startTravelTime) {
		this.startTravelTime = startTravelTime;
	}

	public String getEndTravelTime() {
		return endTravelTime;
	}

	public void setEndTravelTime(String endTravelTime) {
		this.endTravelTime = endTravelTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Boolean getWithScenicSpot() {
		return withScenicSpot;
	}

	public void setWithScenicSpot(Boolean withScenicSpot) {
		this.withScenicSpot = withScenicSpot;
	}

	public Boolean getWithPolicy() {
		return withPolicy;
	}

	public void setWithPolicy(Boolean withPolicy) {
		this.withPolicy = withPolicy;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Boolean getScenicSpotsNameLike() {
		return scenicSpotsNameLike;
	}

	public void setScenicSpotsNameLike(Boolean scenicSpotsNameLike) {
		this.scenicSpotsNameLike = scenicSpotsNameLike;
	}

	public String getScenicSpotsName() {
		return scenicSpotsName;
	}

	public void setScenicSpotsName(String scenicSpotsName) {
		this.scenicSpotsName = scenicSpotsName;
	}



	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public Boolean getDetail() {
		return detail;
	}

	public void setDetail(Boolean detail) {
		this.detail = detail;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}

}
