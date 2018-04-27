package hsl.pojo.dto.hotel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @类功能说明：价格信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:38:37
 * @版本：V1.0
 * 
 */
public class NightlyRateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员价
	 */
	protected BigDecimal member;
	/**
	 * 结算价
	 */
	protected BigDecimal cost;
	/**
	 * 原始价
	 */
	protected BigDecimal basis;
	/**
	 * 库存状态
	 */
	protected boolean status;
	/**
	 * 加床价
	 */
	protected BigDecimal addBed;
	/**
	 * 当天日期
	 */
	protected Date date;

	public BigDecimal getMember() {
		return member;
	}

	public void setMember(BigDecimal member) {
		this.member = member;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getBasis() {
		return basis;
	}

	public void setBasis(BigDecimal basis) {
		this.basis = basis;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public BigDecimal getAddBed() {
		return addBed;
	}

	public void setAddBed(BigDecimal addBed) {
		this.addBed = addBed;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

}
