package slfx.mp.app.pojo.qo;

import java.util.Date;

import hg.common.component.BaseQo;

/**
 * 查询平台调价政策
 * 
 * @author yuxx
 */
public class SalePolicySnapshotQO extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策编号
	 */
	private String policyId;
	
	/**
	 * 政策生效时间
	 */
	private Date beginDate;
	
	/**
	 * 政策结束时间
	 */
	private Date endDate;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 创建人
	 */
	private String createUserName;
	
	/**
	 * 创建人模糊查询
	 */
	private Boolean createUserNameLike = true;
	
	/**
	 * 适用范围 经销商ID
	 */
	private String dealerId;
	
	/**
	 * 景区名称
	 */
	private String scenicSpotName;
	
	/**
	 * 景区名称模糊查询
	 */
	private Boolean scenicSpotNameLike = true;
	
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
	 * 是否按快照日期从大到小
	 */
	private Boolean snapshotDateDesc = true;
	
	/**
	 * 是否最新快照
	 */
	private Boolean lastSnapshot;

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Boolean getScenicSpotNameLike() {
		return scenicSpotNameLike;
	}

	public void setScenicSpotNameLike(Boolean scenicSpotNameLike) {
		this.scenicSpotNameLike = scenicSpotNameLike;
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

	public Boolean getCreateUserNameLike() {
		return createUserNameLike;
	}

	public void setCreateUserNameLike(Boolean createUserNameLike) {
		this.createUserNameLike = createUserNameLike;
	}

	public Boolean getSnapshotDateDesc() {
		return snapshotDateDesc;
	}

	public void setSnapshotDateDesc(Boolean snapshotDateDesc) {
		this.snapshotDateDesc = snapshotDateDesc;
	}

	public Boolean getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(Boolean lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public String getScenicSpotAreaCode() {
		return scenicSpotAreaCode;
	}

	public void setScenicSpotAreaCode(String scenicSpotAreaCode) {
		this.scenicSpotAreaCode = scenicSpotAreaCode;
	}
	
}
