package lxs.pojo.dto.mp;

import java.util.Date;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class ScenicSpotDTO extends BaseDTO{
	/**
	 * 景区基本信息
	 */
	private ScenicSpotBaseInfoDTO baseInfo;

	/**
	 * 景区联系信息
	 */
	private ScenicSpotContactInfoDTO contactInfo;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	private Double rackRate;

	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 销量
	 */
	private Integer sales;

	/**
	 * 景区ID
	 */
	private Integer versionNO;

	/**
	 * 同步时间
	 */
	private Date syncTime;

	/**
	 * 包含政策数量
	 */
	private Integer policySUM;
	
	/**
	 * 本地状态
	 * 0：正常显示
	 * 1：隐藏
	 * 2：删除
	 */
	private Integer localStatus;
	
	/**
	 * 排序
	 */
	private Integer sort;

	public ScenicSpotBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ScenicSpotBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public ScenicSpotContactInfoDTO getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ScenicSpotContactInfoDTO contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(Integer versionNO) {
		this.versionNO = versionNO;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Integer getPolicySUM() {
		return policySUM;
	}

	public void setPolicySUM(Integer policySUM) {
		this.policySUM = policySUM;
	}

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
