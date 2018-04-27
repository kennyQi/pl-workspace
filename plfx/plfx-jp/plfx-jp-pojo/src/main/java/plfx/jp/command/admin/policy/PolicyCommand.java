package plfx.jp.command.admin.policy;

import hg.common.component.BaseCommand;

import java.util.Date;

/**
 * 
 * @类功能说明：添加、修改、政策的command类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:59:02
 * @版本：V1.0
 *
 */
public class PolicyCommand extends BaseCommand {
	
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -1943540135660669378L;
	/** 政策唯一标识ID*/
	private String id;
	/** 政策名称*/
	private String name;
	
	/** 供应商ID*/
	private String suppId;
	private String suppCode;
	
	/** 经销商ID*/
	private String dealerId;
	private String dealerCode;
	
	/** 开始生效时间*/
	private Date beginTime;
	
	/** 结束生效时间*/
	private Date endTime;
	
	/** 价格政策*/
	private Double pricePolicy;
	
	/** 价格百分比政策:以0.23的形式存在,即23%*/
	private Double percentPolicy;
	
	/** 备注*/
	private String remark;
	
	/** 取消原因*/
	private String cancleReason;
	/** 创建人*/
	private String createPersion;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	public String getCreatePersion() {
		return createPersion;
	}

	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}
	
}
