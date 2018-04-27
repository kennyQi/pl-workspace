package hg.fx.domain.rebate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @author yangkang
 * @date 2016-07-22
 * 返利月报实体
 * */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "REBATE_REPORT")
public class RebateReport extends BaseStringIdModel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品ID
	 * */
	@Column(name = "PROD_ID", length = 64)
	private String prodId;
	
	/**
	 * 商品名称
	 */
	@Column(name = "PROD_NAME", length = 32)
	private String prodName;	
	
	/**
	 * 商品所属渠道名称
	 * */
	@Column(name = "CHANNEL_NAME", length = 32)
	private String channelName;
	
	/**
	 * 商户ID
	 * */
	@Column(name = "DISTRIBUTOR_ID", length = 64)
	private String distributorId;
	
	/**
	 * 商户名称
	 */
	@Column(name="DISTRIBUTOR_NAME", length = 256)
	private String distributorName;
	
	/**
	 * 消费额
	 * */
	@Column(name="CONSUME_NUM", columnDefinition = O.LONG_NUM_COLUM)
	private Long consumeNum;
	
	/**
	 * 返利额
	 * */
	@Column(name="REBATE_NUM", columnDefinition = O.LONG_NUM_COLUM)
	private Long rebateNum;
	
	/**
	 * 执行返利的区间级别
	 * */
	@Column(name="INTERVAL", length = 64)
	private String interval;
	
	/**
	 * 执行返利折扣
	 * */
	@Column(name = "PERCENT", columnDefinition = O.MONEY_COLUM)
	private Double percent;
	
	/**
	 * 报表对应执行规则ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REBATESET_ID")
	private RebateSet rebateSet;
	
	/**
	 * 生成时间
	 */
	@Column(name="CREATE_DATE",columnDefinition=O.DATE_COLUM)
	private Date createDate;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public Long getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Long consumeNum) {
		this.consumeNum = consumeNum;
	}

	public Long getRebateNum() {
		return rebateNum;
	}

	public void setRebateNum(Long rebateNum) {
		this.rebateNum = rebateNum;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public RebateSet getRebateSet() {
		return rebateSet;
	}

	public void setRebateSet(RebateSet rebateSet) {
		this.rebateSet = rebateSet;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
			
}
