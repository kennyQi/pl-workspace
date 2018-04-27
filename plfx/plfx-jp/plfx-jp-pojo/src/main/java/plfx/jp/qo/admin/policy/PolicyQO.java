package plfx.jp.qo.admin.policy;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 
 * @类功能说明：价格政策QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:55:03
 * @版本：V1.0
 *
 */
public class PolicyQO extends BaseQo {
	

	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1226565321235213L;

	/** 政策编号 */
	private String no;
	
	/** 政策名称 */
	private String name;
	
	/** 供应商ID */
	private String suppId;
	/**
	 * 供应商代码
	 */
	private String suppCode;
	
	/** 经销商ID */
	private String dealerId;
	/**
	 * 经销商代码
	 */
	private String dealerCode;
	
	/** 开始生效时间 */
	private Date beginTime;
	
	/** 结束生效时间 */
	private Date endTime;

	/** 当前时间，用以判断该政策状态  */
	private Date currentTime;
	
	/** 价格政策 */
	private Double pricePolicy;
	
	/** 价格百分比政策 */
	private Double percentPolicy;
	
	/** 状态 */
	private String status;
	
	
	/** 创建人 */
	private String createPersion;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 备注 */
	private String remark;
	
	/** 取消原因 */
	private String cancleReason;

	private Boolean sortCreateTime; 
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSuppCode() {
		return suppCode;
	}

	public void setSuppCode(String suppCode) {
		this.suppCode = suppCode;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
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

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public Double getPricePolicy() {
		return pricePolicy;
	}

	public void setPricePolicy(Double pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	public Double getPercentPolicy() {
		return percentPolicy;
	}

	public void setPercentPolicy(Double percentPolicy) {
		this.percentPolicy = percentPolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatePersion() {
		return createPersion;
	}

	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCancleReason() {
		return cancleReason;
	}

	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}

	public Boolean getSortCreateTime() {
		return sortCreateTime;
	}

	public void setSortCreateTime(Boolean sortCreateTime) {
		this.sortCreateTime = sortCreateTime;
	}
	
}
