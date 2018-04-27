package slfx.jp.domain.model.salepolicy;

import hg.common.component.BaseModel;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

/**
 * @类功能说明：经销商价格政策快照 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:30:30
 * @版本：V1.0
 */
@Entity(name="salePolicySnapshot_jp")
@Table(name = J.TABLE_PREFIX + "SALE_POLICY_SNAPSHOT")
public class SalePolicySnapshot extends BaseModel{
	private static final long serialVersionUID = 2542343061834012510L;

   /** 政策id */
	@Column(name = "POLICY_ID", length = 64)
   private String policyId;
   
   /** 供应商id */
   @Column(name = "SUPPLIER_ID", length = 64)
   private String supplierId;
   
   /** 商品筛选方式 */
   @Column(name = "FILTER_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
   private int filterType;
   
   /** 经销商id */
   @Column(name = "DEALER_ID", length = 64)
   private String dealerId;
   
   /** 政策生效时间 */
   @Column(name = "BEGIN_DATE", columnDefinition = J.DATE_COLUM)
   private Date beginDate;
   
   /** 政策结束时间 */
   @Column(name = "END_DATE", columnDefinition = J.DATE_COLUM)
   private Date endDate;
   
   /** 调价类型 */
   @Column(name = "MODIFY_PRICE_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
   private Integer modifyPriceType;
   
   /** 调价值 */
   @Column(name = "MODIFY_PROCE_VALUE", columnDefinition = J.MONEY_COLUM)
   private Double modifyProceValue;
   
   /** 备注 */
   @Column(name = "REMARK", length = 1000)
   private String remark;
   
   /** 供应商政策id集 */
   @Column(name = "SUPPLIER_POLICYIDS", length = 500)
   private String supplierPolicyIds;
   
   /** 优先级 */
   @Column(name = "LEVEL", columnDefinition = J.TYPE_NUM_COLUM)
   private int level;
   
   /** 快照日期 */
   @Column(name = "SNAPSHOT_DATE", columnDefinition = J.DATE_COLUM)
   private Date snapshotDate;

	public String getPolicyId() {
		return policyId;
	}
	
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public int getFilterType() {
		return filterType;
	}
	
	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
	
	public String getDealerId() {
		return dealerId;
	}
	
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getModifyPriceType() {
		return modifyPriceType;
	}
	
	public void setModifyPriceType(Integer modifyPriceType) {
		this.modifyPriceType = modifyPriceType;
	}
	
	public Double getModifyProceValue() {
		return modifyProceValue;
	}
	
	public void setModifyProceValue(Double modifyProceValue) {
		this.modifyProceValue = modifyProceValue;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSupplierPolicyIds() {
		return supplierPolicyIds;
	}
	
	public void setSupplierPolicyIds(String supplierPolicyIds) {
		this.supplierPolicyIds = supplierPolicyIds;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public Date getSnapshotDate() {
		return snapshotDate;
	}
	
	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

}