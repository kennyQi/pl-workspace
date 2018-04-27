package hsl.domain.model.dzp.policy;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 门票政策
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "POLICY")
public class DZPTicketPolicy extends BaseModel implements HSLConstants.DZPWTicketPolicyType {

	/**
	 * 门票政策基本信息
	 */
	@Embedded
	private DZPTicketPolicyBaseInfo baseInfo;

	/**
	 * 门票政策销售信息
	 */
	@Embedded
	private DZPTicketPolicySellInfo sellInfo;

	/**
	 * 门票政策使用条件信息
	 */
	@Embedded
	private DZPTicketPolicyUseCondition useInfo;

	/**
	 * 门票政策状态
	 */
	@Embedded
	private DZPTicketPolicyStatus status;

	/**
	 * 所属套票政策
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private DZPTicketPolicy parent;

	/**
	 * 所属的景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private DZPScenicSpot scenicSpot;

	/**
	 * 门票政策下的单个景区门票政策
	 */
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "parent")
	private List<DZPTicketPolicy> singleTicketPolicies;

	/**
	 * 政策类型
	 *
	 * @see HSLConstants.DZPWTicketPolicyType
	 */
	@Column(name = "POLICY_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 最新快照
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SNAPSHOT_ID")
	private DZPTicketPolicySnapshot lastSnapshot;

	/**
	 * 版本（冗余字段）
	 */
	@Column(name = "POLICY_VERSION", columnDefinition = M.NUM_COLUM)
	private Integer version;

	/**
	 * 每日价格
	 * <pre>
	 * 价格日历：日期(yyyyMMdd)，当日价格
	 * </pre>
	 */
	@OrderBy("date")
	@MapKey(name = "date")
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "ticketPolicy")
	private Map<String, DZPTicketPolicyDatePrice> price;

	/**
	 * 是否包含景区
	 *
	 * @param scenicSpotId 景区ID
	 * @return
	 */
	public boolean hasScenicSpot(String scenicSpotId) {
		if (scenicSpotId == null) return false;
		if (scenicSpotId.equals(M.getModelObjectId(getScenicSpot())))
			return true;
		if (getSingleTicketPolicies() != null)
			for (DZPTicketPolicy policy : getSingleTicketPolicies())
				if (scenicSpotId.equals(M.getModelObjectId(policy.getScenicSpot())))
					return true;
		return false;
	}

	public DZPTicketPolicyBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new DZPTicketPolicyBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(DZPTicketPolicyBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public DZPTicketPolicySellInfo getSellInfo() {
		if (sellInfo == null)
			sellInfo = new DZPTicketPolicySellInfo();
		return sellInfo;
	}

	public void setSellInfo(DZPTicketPolicySellInfo sellInfo) {
		this.sellInfo = sellInfo;
	}

	public DZPTicketPolicyUseCondition getUseInfo() {
		if (useInfo == null)
			useInfo = new DZPTicketPolicyUseCondition();
		return useInfo;
	}

	public void setUseInfo(DZPTicketPolicyUseCondition useInfo) {
		this.useInfo = useInfo;
	}

	public DZPTicketPolicyStatus getStatus() {
		if (status == null)
			status = new DZPTicketPolicyStatus();
		return status;
	}

	public void setStatus(DZPTicketPolicyStatus status) {
		this.status = status;
	}

	public DZPTicketPolicy getParent() {
		return parent;
	}

	public void setParent(DZPTicketPolicy parent) {
		this.parent = parent;
	}

	public DZPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(DZPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public List<DZPTicketPolicy> getSingleTicketPolicies() {
		if (singleTicketPolicies == null)
			singleTicketPolicies = new ArrayList<DZPTicketPolicy>();
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(List<DZPTicketPolicy> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Map<String, DZPTicketPolicyDatePrice> getPrice() {
		if (price == null)
			price = new LinkedHashMap<String, DZPTicketPolicyDatePrice>();
		return price;
	}

	public void setPrice(Map<String, DZPTicketPolicyDatePrice> price) {
		this.price = price;
	}

	public DZPTicketPolicySnapshot getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(DZPTicketPolicySnapshot lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}
}