package slfx.jp.domain.model.order;

import java.util.Date;
import java.util.Set;

import hg.common.component.BaseModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：机票平台退票订单 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:27:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "ORDER_REFUND")
public class JPOrderBack extends BaseModel {
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	private JPOrder jpOrder;
	
	/** 易购退票订单号  */
	@Column(name = "YG_REFUND_ORDER_NO", length = 64 )
	private String ygRefundOrderNo;
	
	/** 退废票平台订单号  */
	@Column(name = "REFUND_PLATFORM_ORDER_NO", length = 64 )
	private String refundPlatformOrderNo;

	/**  需要退回用户的金额  */
	@Column(name = "PAY_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double payAmount;
	
	/** 乘机人 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="JP_ORDER_ID")
	private Set<Passenger> passangerList;
	
	/** 
	 * 退废类型：详情参考 JPOrderStatus.CATEGORY_REFUND 
	 */
	@Column(name = "BACK_CATEGORY", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer backCategory;
	
	/** 创建时间  */
	@Column(name = "CREATE_TME", columnDefinition = J.DATE_COLUM)
	private Date createTime;
	
	public JPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(JPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}
	
	public String getYgRefundOrderNo() {
		return ygRefundOrderNo;
	}

	public void setYgRefundOrderNo(String ygRefundOrderNo) {
		this.ygRefundOrderNo = ygRefundOrderNo;
	}

	public String getRefundPlatformOrderNo() {
		return refundPlatformOrderNo;
	}

	public void setRefundPlatformOrderNo(String refundPlatformOrderNo) {
		this.refundPlatformOrderNo = refundPlatformOrderNo;
	}

	public Set<Passenger> getPassangerList() {
		return passangerList;
	}

	public void setPassangerList(Set<Passenger> passangerList) {
		this.passangerList = passangerList;
	}

	public Integer getBackCategory() {
		return backCategory;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setBackCategory(Integer backCategory) {
		this.backCategory = backCategory;
	}
}