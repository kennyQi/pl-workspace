package hsl.domain.model.dzp.policy;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * 门票政策基本信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketPolicyBaseInfo implements Serializable {

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
	@Column(name = "POLICY_KEY", length = 64)
	private String key;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	@Column(name = "RACK_RATE", columnDefinition = M.MONEY_COLUM)
	private Double rackRate;
	
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
	 * 包含景点，含可游玩天数(冗余字段)
	 * <pre>
	 *     格式：XXXX（X天）[、XXXX（X天）、XXXX（X天）]
	 * </pre>
	 */
	@Column(name = "SCENIC_SPOT_NAME_STR", length = 2048)
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

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getSaleAgreement() {
		return saleAgreement;
	}

	public void setSaleAgreement(String saleAgreement) {
		this.saleAgreement = saleAgreement;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}
}