package hsl.pojo.command.coupon;
public class CreateCouponCommand{
	/**
	 * 卡券活动ID
	 */
	private String couponActivityId;
	/**
	 * 卡券每人发放数量
	 */
	private Long perIssueNumber;
	/**
	 * 订单ID
	 */
    private String orderId;
    /**
	 * 支付价格
	 */
    private Double payPrice;
    /**
   	 * 详细
   	 */
    private String detail;
    /**
	 * 用户标识
	 */
    private String userId;
    /**
     * 用户登录名
    */
    private String loginName;
    /**
    * 用户真实姓名
    */
    private String realName;
    /**
     * 手机号
     */
     private String mobile;
    /**
     * 邮箱
    */
    private String email;
    /**
     * 来源描述
     */
    private String sourceDetail;
	public String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSourceDetail() {
		return sourceDetail;
	}
	public void setSourceDetail(String sourceDetail) {
		this.sourceDetail = sourceDetail;
	}
	public Long getPerIssueNumber() {
		return perIssueNumber;
	}
	public void setPerIssueNumber(Long perIssueNumber) {
		this.perIssueNumber = perIssueNumber;
	}
}
