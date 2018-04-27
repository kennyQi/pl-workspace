package hsl.pojo.dto.coupon;
/**
 * @author Administrator
 *
 */
public class CouponActivityBaseInfoDTO {
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动规则说明
	 */
	private String ruleIntro;
	/**
	 * 面值
	 */
	private double faceValue;
	/**
	 * 卡券类型
	 */
	private int couponType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuleIntro() {
		return ruleIntro;
	}
	public void setRuleIntro(String ruleIntro) {
		this.ruleIntro = ruleIntro;
	}
	public double getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}
	public int getCouponType() {
		return couponType;
	}
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

}
