package hsl.domain.model.coupon;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 卡券活动基本信息
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午9:09:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午9:09:00
 *
 */
@Embeddable
public class CouponActivityBaseInfo {
	/**
	 * 活动名称
	 */
	@Column(name="NAME", length = 64)
    private String name;
    /**
     * 活动规则说明
     */
	@Column(name="RULEINTRO",columnDefinition=M.TEXT_COLUM)
    private String ruleIntro;
    /**
     * 面值
     */
	@Column(name="FACEVALUE", columnDefinition=M.DOUBLE_COLUM)
    private Double faceValue;
	/**
	 * 代金券
	 */
	public static final int COUPONTYPE_VOUCHER=1;
	/**
	 * 现金券
	 */
	public static final int COUPONTYPE_CASH=2;
    /**
     * 卡券类型
     */
	@Column(name="COUPONTYPE",columnDefinition=M.TYPE_NUM_COLUM)
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
	public Double getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(Double faceValue) {
		this.faceValue = faceValue;
	}
	public int getCouponType() {
		return couponType;
	}
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

}