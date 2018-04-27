package hg.dzpw.dealer.client.dto.policy;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明: 联票政策基本信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:03:52
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketPolicyBaseInfoDTO extends EmbeddDTO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 简称
	 */
	private String shortName;

	/**
	 * 联票OR单票介绍
	 */
	private String intro;

	/**
	 * 代码
	 */
	private String key;


	/**
	 * 单票、联票门市价/市场票面价
	 */
	private Double rackRate;
	
	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 预定须知
	 */
	private String notice;
	
	/**
	 * 售卖协议
	 */
	private String saleAgreement;
	
	/**
	 * 交通信息
	 */
	private String traffic;

	/**
	 * 包含景点(冗余字段)
	 */
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