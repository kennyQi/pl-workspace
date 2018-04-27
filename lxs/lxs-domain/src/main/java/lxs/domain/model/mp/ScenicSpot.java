package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

/**
 * @类功能说明: 入盟的景区
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:26:50
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_MP + "SCENIC_SPOT")
public class ScenicSpot extends BaseModel {

	/**
	 * 景区基本信息
	 */
	@Embedded
	private ScenicSpotBaseInfo baseInfo;

	/**
	 * 景区联系信息
	 */
	@Embedded
	private ScenicSpotContactInfo contactInfo;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	@Column(name = "RACK_RATE")
	private Double rackRate;

	/**
	 * 联票(与经销商)游玩价
	 */
	@Column(name = "PLAY_PRICE")
	private Double playPrice;

	/**
	 * 销量
	 */
	@Column(name = "SALES")
	private Integer sales;

	/**
	 * 包含政策数量
	 */
	@Column(name = "POLICY_SUM")
	private Integer policySUM;

	public final static Integer SHOW = 0;
	public final static Integer HIDDEN = 1;
	public final static Integer DELETED = 2;
	/**
	 * 本地状态
	 * 0：正常显示
	 * 1：隐藏
	 * 2：删除
	 */
	@Column(name = "LOCAL_STATUS")
	private Integer localStatus;

	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;
	
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

	public ScenicSpotBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ScenicSpotBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public ScenicSpotContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ScenicSpotContactInfo contactInfo) {
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