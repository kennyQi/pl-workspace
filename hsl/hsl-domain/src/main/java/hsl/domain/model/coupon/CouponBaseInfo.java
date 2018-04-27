package hsl.domain.model.coupon;

import hsl.domain.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @类功能说明：卡券基本信息
 * @类修改者：
 * @修改日期：2014年10月15日上午8:40:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:40:20
 */
@Embeddable
public class CouponBaseInfo {
    /**
    * 卡券活动标识
    */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "COUPONACTIVITY_ID")
    private CouponActivity couponActivity;
    /**
    * 卡券创建日期
    */
	@Column(name="CREATEDATE", columnDefinition=M.DATE_COLUM)
    private Date createDate;
	/**
	 * 卡券来源描述
	 */
	@Column(name="sourceDetail",length=200)
	private String sourceDetail;
	public CouponActivity getCouponActivity() {
		return couponActivity;
	}
	public void setCouponActivity(CouponActivity couponActivity) {
		this.couponActivity = couponActivity;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSourceDetail() {
		return sourceDetail;
	}
	public void setSourceDetail(String sourceDetail) {
		this.sourceDetail = sourceDetail;
	}
}