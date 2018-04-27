package slfx.mp.app.pojo.qo;

import hg.common.component.BaseQo;
import java.util.Date;

/**
 * 内部查询平台政策用
 * 
 * @author zhurz
 */
public class SalePolicySnapshotLocalQO extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商政策id
	 */
	private String policyId;
	/**
	 * 供应商id
	 */
	private String supplierId;
	// --------------- 按省市区 ---------------
	private String filterProvCode;
	private String filterCityCode;
	private String filterAreaCode;
	// --------------- 按价格 ---------------
	private Double filterPriceMin;
	private Double filterPriceMax;
	// --------------- 按景点 ---------------
	private String scenicSpotId;
	// --------------- 筛选时间 ---------------
	private Date beginDate;
	private Date endDate;

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

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
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

}
