package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：pnr比价 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:26:46
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "COMPARE_PRICE")
public class ComparePrice extends BaseModel{

	/** PNR编码 */
	@Column(name = "PNR", length = 8)
	private String pnr;

	/** 比价平台 ：platCode*/
	@Column(name = "PLATS", length = 32)
	private String plats;

	/**
	 * 比价结果选中的政策
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FLIGHT_POLICY_ID")
	public FlightPolicy compareResultPolicy;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getPlats() {
		return plats;
	}

	public void setPlats(String plats) {
		this.plats = plats;
	}

	public FlightPolicy getCompareResultPolicy() {
		return compareResultPolicy;
	}

	public void setCompareResultPolicy(FlightPolicy compareResultPolicy) {
		this.compareResultPolicy = compareResultPolicy;
	}

}