package slfx.mp.app.pojo.qo;

import hg.common.component.BaseQo;

public class TCSupplierPolicySnapshotQO extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 平台景区id
	 */
	private String scenicSpotId;
	
	/**
	 * 政策id
	 */
	private String policyId;
	
	/**
	 * 是否最新快照
	 */
	private Boolean lastSnapshot;

	/**
	 * 同程景点ID
	 */
	private String tcScenicSpotsId;

	/**
	 * 景点名称
	 */
	private String tcScenicSpotsName;
	private boolean tcScenicSpotsNameLike;

	/**
	 * 所在省代码
	 */
	private String provinceCode;

	/**
	 * 所在城市代码
	 */
	private String cityCode;

	/**
	 * 所在区域代码
	 */
	private String areaCode;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Boolean getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(Boolean lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public String getTcScenicSpotsId() {
		return tcScenicSpotsId;
	}

	public void setTcScenicSpotsId(String tcScenicSpotsId) {
		this.tcScenicSpotsId = tcScenicSpotsId;
	}

	public String getTcScenicSpotsName() {
		return tcScenicSpotsName;
	}

	public void setTcScenicSpotsName(String tcScenicSpotsName) {
		this.tcScenicSpotsName = tcScenicSpotsName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public boolean isTcScenicSpotsNameLike() {
		return tcScenicSpotsNameLike;
	}

	public void setTcScenicSpotsNameLike(boolean tcScenicSpotsNameLike) {
		this.tcScenicSpotsNameLike = tcScenicSpotsNameLike;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

}
