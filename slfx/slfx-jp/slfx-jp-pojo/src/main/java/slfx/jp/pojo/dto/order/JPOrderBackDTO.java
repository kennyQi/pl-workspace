package slfx.jp.pojo.dto.order;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：机票平台订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:55:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderBackDTO extends BaseJpDTO {
	
	private JPOrderDTO jpOrder;
	
	/** 易购退票订单号  */
	private String ygRefundOrderNo;
	
	/** 退废票平台订单号  */
	private String refundPlatformOrderNo;

	/**  需要退回用户的金额  */
	private Double payAmount;
	
	/** 乘机人 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="JP_ORDER_ID")
	private Set<Passenger> passangerList;
	
	/** 
	 * 退废类型：详情参考 JPOrderStatus.CATEGORY_REFUND 
	 */
	private Integer backCategory;
	
	/** 创建时间  */
	private Date createTime;

	public JPOrderDTO getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(JPOrderDTO jpOrder) {
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

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
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

	public void setBackCategory(Integer backCategory) {
		this.backCategory = backCategory;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}