package plfx.jp.domain.model.policy;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：平台价格政策快照(发布时才建立快照)
 * @类修改者：
 * @修改日期：2015-7-10下午4:34:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-10下午4:34:02
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX + "POLICY_SNAPSHOT")
public class JPPlatformPolicySnapshot extends BaseModel {

	/**
	 * 平台价格政策id
	 */
	@Column(name = "POLICY_ID", length = 64)
	private String jpPlatformPolicyId;

	/**
	 * 政策编号
	 */
	@Column(name = "POLICY_NO", length = 64)
	private String no;

	/**
	 * 政策名称
	 */
	@Column(name = "POLICY_NAME", length = 64)
	private String name;

	/**
	 * 适用供应商，为null时为全部
	 */
	@Column(name = "SUPPLIER_ID", length = 64)
	private String supplierId;

	/**
	 * 适用经销商，为null时为全部
	 */
	@Column(name = "DEALER_ID", length = 64)
	private String dealerId;

	/**
	 * 开始生效时间
	 */
	@Column(name = "BEGIN_TIME", columnDefinition = J.DATE_COLUM)
	private Date beginTime;

	/**
	 * 结束生效时间
	 */
	@Column(name = "END_TIME", columnDefinition = J.DATE_COLUM)
	private Date endTime;

	/**
	 * 价格政策
	 */
	@Column(name = "PRICE_POLICY", columnDefinition = J.MONEY_COLUM)
	private Double pricePolicy;

	/**
	 * 价格百分比政策:以0.23的形式存在,即23%
	 */
	@Column(name = "PERCENT_POLICY", columnDefinition = J.MONEY_COLUM)
	private Double percentPolicy;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 4000)
	private String remark;

	/**
	 * 快照时间
	 */
	@Column(name = "SNAPSHOT_DATE", columnDefinition = J.DATE_COLUM)
	private Date snapshotDate;

	public String getJpPlatformPolicyId() {
		return jpPlatformPolicyId;
	}

	public void setJpPlatformPolicyId(String jpPlatformPolicyId) {
		this.jpPlatformPolicyId = jpPlatformPolicyId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getPricePolicy() {
		if (pricePolicy == null)
			return 0d;
		return pricePolicy;
	}

	public void setPricePolicy(Double pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	public Double getPercentPolicy() {
		if (percentPolicy == null)
			return 0d;
		return percentPolicy;
	}

	public void setPercentPolicy(Double percentPolicy) {
		this.percentPolicy = percentPolicy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

}
