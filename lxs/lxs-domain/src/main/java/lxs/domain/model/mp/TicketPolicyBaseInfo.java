package lxs.domain.model.mp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lxs.domain.model.M;

/**
 * @类功能说明: 联票政策基本信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:03:52
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicyBaseInfo  {

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;

	/**
	 * 简称
	 */
	@Column(name = "SHORT_NAME", length = 512)
	private String shortName;

	/**
	 * 联票OR单票介绍
	 */
	@Column(name = "INTRO", length = 512)
	private String intro;

	/**
	 * 代码
	 * 原本字段应该定义为key可惜是MySQL的保留字，所以改成k
	 */
	@Column(name = "K", length = 512)
	private String key;

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
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;

	/**
	 * 修改时间
	 */
	@Column(name = "MODIFY_DATE",columnDefinition=M.DATE_COLUM)
	private Date modifyDate;

	/**
	 * 预定须知
	 */
	@Column(name = "NOTICE", length = 512)
	private String notice;
	
	/**
	 * 售卖协议
	 */
	@Column(name = "SALE_AGREEMENT", length = 512)
	private String saleAgreement;
	
	/**
	 * 交通信息
	 */
	@Column(name = "TRAFFIC", length = 512)
	private String traffic;

	/**
	 * 包含景点(冗余字段)
	 */
	@Column(name = "SCENIC_POT_NAME_STR", length = 512)
	private String scenicSpotNameStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}


	public String getSaleAgreement() {
		return saleAgreement;
	}

	public void setSaleAgreement(String saleAgreement) {
		this.saleAgreement = saleAgreement;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

}