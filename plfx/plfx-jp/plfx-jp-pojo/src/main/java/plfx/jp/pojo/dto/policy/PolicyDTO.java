package plfx.jp.pojo.dto.policy;

import java.util.Date;

import plfx.jp.pojo.dto.BaseJpDTO;
import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.pojo.dto.supplier.SupplierDTO;

/**
 * 
 * @类功能说明：价格政策DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:53:59
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PolicyDTO extends BaseJpDTO  {

	/** 政策编号 */
	private String no;
	
	/** 政策名称 */
	private String name;
	
	/** 供应商ID */
	private String suppId;
	
	/** 经销商ID */
	private String dealerId;
	
	/** 开始生效时间 */
	private Date beginTime;
	
	/** 结束生效时间 */
	private Date endTime;
	
	/** 价格政策 */
	private Double pricePolicy;
	
	/** 价格百分比政策 */
	private Double percentPolicy;
	
	/** 状态 */
	private String status;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 备注 */
	private String remark;
	
	/** 取消原因 */
	private String cancleReason;
	
	/** 供应商 */
	private SupplierDTO supplier;

	/** 经销商 */
	private DealerDTO dealer;

	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public SupplierDTO getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierDTO supplier) {
		this.supplier = supplier;
	}

	public DealerDTO getDealer() {
		return dealer;
	}

	public void setDealer(DealerDTO dealer) {
		this.dealer = dealer;
	}
}
