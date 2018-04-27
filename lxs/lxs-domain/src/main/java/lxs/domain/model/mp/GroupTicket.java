package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_MP + "GROUP_TICKET")
public class GroupTicket extends BaseModel {

	@Embedded
	private TicketPolicyBaseInfo baseInfo;

	@Embedded
	private TicketPolicySellInfo sellInfo;

	@Embedded
	private TicketPolicyUseCondition useInfo;

	@Embedded
	private TicketPolicyStatus status;
	/**
	 * 景区id
	 */
	@Column(name = "SCENIC_SPOT_ID", length = 512)
	private String scenicSpotID;

	/**
	 * 所在省
	 */
	@Column(name = "PROVINCE_ID", length = 512)
	private String provinceId;

	/**
	 * 所在市
	 */
	@Column(name = "CITY_ID", length = 512)
	private String cityId;

	/**
	 * 所在区
	 */
	@Column(name = "AREA_ID", length = 512)
	private String areaId;

	/**
	 * 图片地址
	 */
	@Column(name = "URL", length = 512)
	private String url;

	/**
	 * 景区版本
	 */
	@Column(name = "VERSION_NO")
	private Integer versionNO;

	/**
	 * 同步时间
	 */
	@Column(name = "SYNC_TIME")
	private Date syncTime;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TicketPolicyBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(TicketPolicyBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public TicketPolicySellInfo getSellInfo() {
		return sellInfo;
	}

	public void setSellInfo(TicketPolicySellInfo sellInfo) {
		this.sellInfo = sellInfo;
	}

	public TicketPolicyUseCondition getUseInfo() {
		return useInfo;
	}

	public void setUseInfo(TicketPolicyUseCondition useInfo) {
		this.useInfo = useInfo;
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

	public TicketPolicyStatus getStatus() {
		return status;
	}

	public void setStatus(TicketPolicyStatus status) {
		this.status = status;
	}

}
