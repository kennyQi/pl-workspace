package lxs.domain.model.mp;

import java.util.Date;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_MP + "SCENIC_SPOT_PIC")
public class ScenicSpotPic extends BaseModel {

	/**
	 * 图片地址
	 */
	@Column(name = "URL", length = 512)
	private String url;

	/**
	 * 图片名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;

	/**
	 * 景区id
	 */
	@Column(name = "SCENIC_SPOT_ID", length = 512)
	private String scenicSpotID;

	/**
	 * 版本号
	 */
	@Column(name = "VERSION_NO")
	private Integer versionNO;

	/**
	 * 同步时间
	 */
	@Column(name = "SYNC_TIME")
	private Date syncTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
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

}
