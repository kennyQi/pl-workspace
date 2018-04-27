package hsl.domain.model.dzp.scenicspot;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 入盟的景区
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@DynamicUpdate
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "SCENIC_SPOT")
public class DZPScenicSpot extends BaseModel {

	/**
	 * 景区基本信息
	 */
	@Embedded
	private DZPScenicSpotBaseInfo baseInfo;

	/**
	 * 景区联系信息
	 */
	@Embedded
	private DZPScenicSpotContactInfo contactInfo;

	/**
	 * 联票最低价格（冗余字段）
	 */
	@Column(name = "GROUP_PRICE_MIN", columnDefinition = M.MONEY_COLUM)
	private Double groupPriceMin;
	/**
	 * 单票最低价格（冗余字段）
	 */
	@Column(name = "SINGLE_PRICE_MIN", columnDefinition = M.MONEY_COLUM)
	private Double singlePriceMin;
	/**
	 * 最低价格（冗余字段）
	 */
	@Column(name = "PRICE_MIN", columnDefinition = M.MONEY_COLUM)
	private Double priceMin;

	/**
	 * 景区封面（取景区图片第一张，冗余字段）
	 */
	@OneToOne
	@JoinColumn(name = "COVER_PIC_ID")
	private DZPScenicSpotPic cover;

	/**
	 * 景区图片
	 */
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "scenicSpot")
	private List<DZPScenicSpotPic> pics;

	/**
	 * 关联的门票政策
	 */
	@Transient
	private List<DZPTicketPolicy> ticketPolicies;

	public DZPScenicSpotBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new DZPScenicSpotBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(DZPScenicSpotBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public DZPScenicSpotContactInfo getContactInfo() {
		if (contactInfo == null)
			contactInfo = new DZPScenicSpotContactInfo();
		return contactInfo;
	}

	public void setContactInfo(DZPScenicSpotContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<DZPScenicSpotPic> getPics() {
		if (pics == null)
			pics = new ArrayList<DZPScenicSpotPic>();
		return pics;
	}

	public void setPics(List<DZPScenicSpotPic> pics) {
		this.pics = pics;
	}

	public Double getGroupPriceMin() {
		return groupPriceMin;
	}

	public void setGroupPriceMin(Double groupPriceMin) {
		this.groupPriceMin = groupPriceMin;
	}

	public Double getSinglePriceMin() {
		return singlePriceMin;
	}

	public void setSinglePriceMin(Double singlePriceMin) {
		this.singlePriceMin = singlePriceMin;
	}

	public Double getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}

	public DZPScenicSpotPic getCover() {
		return cover;
	}

	public void setCover(DZPScenicSpotPic cover) {
		this.cover = cover;
	}

	public List<DZPTicketPolicy> getTicketPolicies() {
		if (ticketPolicies == null)
			ticketPolicies = new ArrayList<DZPTicketPolicy>();
		return ticketPolicies;
	}

	public void setTicketPolicies(List<DZPTicketPolicy> ticketPolicies) {
		this.ticketPolicies = ticketPolicies;
	}
}