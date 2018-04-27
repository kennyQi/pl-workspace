package hg.dzpw.domain.model.policy;

import hg.dzpw.domain.model.M;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明: 联票政策基本信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:03:52
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicyBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 128)
	private String name;

	/**
	 * 简称
	 */
	@Column(name = "SHORT_NAME", length = 64)
	private String shortName;

	/**
	 * 联票OR单票介绍
	 */
	@Column(name = "INTRO", columnDefinition = M.TEXT_COLUM)
	private String intro;

	/**
	 * 代码
	 */
	@Column(name = "_KEY", length = 64)
	private String key;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	@Column(name = "RACK_RATE", columnDefinition = M.MONEY_COLUM)
	private Double rackRate;
	
	/**
	 * 联票(与景区)结算价
	 */
	@Column(name = "SETTLEMENT_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double settlementPrice;
	
	/**
	 * 联票(与经销商)游玩价
	 */
	@Column(name = "PLAY_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double playPrice;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 修改时间
	 */
	@Column(name = "MODIFY_DATE", columnDefinition = M.DATE_COLUM)
	private Date modifyDate;

	/**
	 * 创建的管理者id
	 */
	@Column(name = "CREATE_ADMINID", length = 64)
	private String createAdminId;

	/**
	 * 预定须知
	 */
	@Column(name = "NOTICE", columnDefinition = M.TEXT_COLUM)
	private String notice;
	
	/**
	 * 售卖协议
	 */
	@Column(name = "SALE_AGREEMENT", columnDefinition = M.TEXT_COLUM)
	private String saleAgreement;

	/**
	 * 交通信息
	 */
	@Column(name = "TRAFFIC", columnDefinition = M.TEXT_COLUM)
	private String traffic;

	/**
	 * 包含景点(冗余字段)
	 */
	@Column(name = "SCENIC_SPOT_NAMESTR", length = 1024)
	private String scenicSpotNameStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateAdminId() {
		return createAdminId;
	}

	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId == null ? null : createAdminId
				.trim();
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice == null ? null : notice.trim();
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic == null ? null : traffic.trim();
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public String getSaleAgreement() {
		return saleAgreement;
	}

	public void setSaleAgreement(String saleAgreement) {
		this.saleAgreement = saleAgreement;
	}

}