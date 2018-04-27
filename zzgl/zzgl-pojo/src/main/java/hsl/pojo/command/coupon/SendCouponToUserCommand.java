package hsl.pojo.command.coupon;
import hg.common.component.BaseCommand;
public class SendCouponToUserCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户手机号
	 */
	private String mobile;
	/**
	 * 用户ID
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
     * 邮箱
    */
    private String email;
	/**
	 * 卡券ID
	 */
	private String couponId;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
