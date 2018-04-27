package slfx.mp.pojo.dto.platformpolicy;

import java.util.Date;

import slfx.mp.pojo.dto.BaseMpDTO;

/**
 * 经销商价格政策快照
 * 
 * @author Administrator
 */
public class SalePolicySnapshotDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商政策id
	 */
	private String policyId;
	/**
	 * 经销商政策名称
	 */
	private String policyName;
	/**
	 * 供应商id
	 */
	private String supplierId;
	/**
	 * 商品筛选方式 1按省市区 2按价格 3按景点名称
	 */
	private Integer filterType;
	// --------------- 按省市区 ---------------
	private String filterProvCode;
	private String filterCityCode;
	private String filterAreaCode;
	// --------------- 按价格 ---------------
	private Double filterPriceMin;
	private Double filterPriceMax;
	// --------------- 按景点名称 ---------------
	/**
	 * 景点id集
	 */
	private String scenicSpotIds;
	/**
	 * 景点名称集(冗余字段，格式：,某某景点,某某景点,)
	 */
	private String scenicSpotNames;
	/**
	 * 经销商id
	 */
	private String dealerId;
	/**
	 * 政策生效时间
	 */
	private Date beginDate;
	/**
	 * 政策结束时间
	 */
	private Date endDate;
	/**
	 * 调价类型
	 */
	private Integer modifyPriceType;
	/**
	 * 调价值
	 */
	private Double modifyProceValue;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 取消原因
	 */
	private String cancelRemark;
	/**
	 * 优先级
	 */
	private Integer level;
	/**
	 * 快照日期
	 */
	private Date snapshotDate;
	/**
	 * 创建人姓名
	 */
	private String operatorName;
	/**
	 * 状态
	 */
	private SalePolicySnapshotStatusDTO status;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public String getFilterProvCode() {
		return filterProvCode;
	}

	public void setFilterProvCode(String filterProvCode) {
		this.filterProvCode = filterProvCode;
	}

	public String getFilterCityCode() {
		return filterCityCode;
	}

	public void setFilterCityCode(String filterCityCode) {
		this.filterCityCode = filterCityCode;
	}

	public String getFilterAreaCode() {
		return filterAreaCode;
	}

	public void setFilterAreaCode(String filterAreaCode) {
		this.filterAreaCode = filterAreaCode;
	}

	public Double getFilterPriceMin() {
		return filterPriceMin;
	}

	public void setFilterPriceMin(Double filterPriceMin) {
		this.filterPriceMin = filterPriceMin;
	}

	public Double getFilterPriceMax() {
		return filterPriceMax;
	}

	public void setFilterPriceMax(Double filterPriceMax) {
		this.filterPriceMax = filterPriceMax;
	}

	public String getScenicSpotIds() {
		return scenicSpotIds;
	}

	public void setScenicSpotIds(String scenicSpotIds) {
		this.scenicSpotIds = scenicSpotIds;
	}

	public String getScenicSpotNames() {
		return scenicSpotNames;
	}

	public void setScenicSpotNames(String scenicSpotNames) {
		this.scenicSpotNames = scenicSpotNames;
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

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public SalePolicySnapshotStatusDTO getStatus() {
		return status;
	}

	public void setStatus(SalePolicySnapshotStatusDTO status) {
		this.status = status;
	}

}