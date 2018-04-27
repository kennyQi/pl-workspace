package slfx.mp.command.admin;

import hg.common.component.BaseCommand;
import java.util.Date;

/**
 * 创建平台政策
 * 
 * @author yuxx
 */
public class CreatePlatformPolicyCommand extends BaseCommand {
	
	/**
	 * 政策ID
	 */
	private String policyId;
	
	/**
	 * 政策优先级
	 */
	private Integer level;
	
	/**
	 * 政策名称
	 */
	private String policyName;
	
	/**
	 * 供应商id
	 */
	private String supplierId;
	
	/**
	 * 筛选方式
	 */
	private int filterType;
	
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
	 * 快照日期
	 */
	private Date snapshotDate;
	
	/**
	 * 景区所在省
	 */
	private String  scenicSpotProviceCode;
	
	/**
	 * 景区所在市
	 */
	private String  scenicSpotCityCode;
	
	/**
	 * 景区所在区
	 */
	private String  scenicSpotAreaCode;

	/**
	 * 景点id集
	 */
	private String scenicSpotIds;

	/**
	 * 价格区间  - 最高价格
	 */
	private Double highPrice;
	
	/**
	 * 价格区间 - 最低价格
	 */
	private Double lowPrice;
	
	/**
	 * 创建人姓名
	 */
	private String operatorName;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
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

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
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

	public String getScenicSpotProviceCode() {
		return scenicSpotProviceCode;
	}

	public void setScenicSpotProviceCode(String scenicSpotProviceCode) {
		this.scenicSpotProviceCode = scenicSpotProviceCode;
	}

	public String getScenicSpotCityCode() {
		return scenicSpotCityCode;
	}

	public void setScenicSpotCityCode(String scenicSpotCityCode) {
		this.scenicSpotCityCode = scenicSpotCityCode;
	}

	public String getScenicSpotAreaCode() {
		return scenicSpotAreaCode;
	}

	public void setScenicSpotAreaCode(String scenicSpotAreaCode) {
		this.scenicSpotAreaCode = scenicSpotAreaCode;
	}

	public String getScenicSpotIds() {
		return scenicSpotIds;
	}

	public void setScenicSpotIds(String scenicSpotIds) {
		this.scenicSpotIds = scenicSpotIds;
	}

	public int getFilterType() {
		return filterType;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}
