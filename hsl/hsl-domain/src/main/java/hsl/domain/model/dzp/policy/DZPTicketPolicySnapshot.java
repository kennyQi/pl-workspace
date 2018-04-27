package hsl.domain.model.dzp.policy;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 电子票政策快照
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "POLICY_SNAPSHOT")
public class DZPTicketPolicySnapshot extends BaseModel implements HSLConstants.DZPWTicketPolicyType {
	
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
	 * 所属门票政策快照
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private DZPTicketPolicySnapshot parent;

	/**
	 * 所属的景区ID
	 */
	@Column(name = "SCENIC_SPOT_ID", length = 64)
	private String scenicSpotId;

	/**
	 * 所属的景区名称
	 */
	@Column(name = "SCENIC_SPOT_NAME", length = 128)
	private String scenicSpotName;

	/**
	 * 门票政策下的单个景区门票政策
	 */
	@OneToMany(mappedBy = "parent")
	private List<DZPTicketPolicySnapshot> singleTicketPolicies;

	/**
	 * 快照的门票政策ID
	 */
	@Column(name = "TICKET_POLICY_ID", length = 64)
	private String ticketPolicyId;

	/**
	 * 政策类型
	 *
	 * @see HSLConstants.DZPWTicketPolicyType
	 */
	@Column(name = "POLICY_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 版本
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
	@OneToMany(mappedBy = "ticketPolicySnapshot")
	private Map<String, DZPTicketPolicySnapshotDatePrice> price;

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

	public DZPTicketPolicySnapshot getParent() {
		return parent;
	}

	public void setParent(DZPTicketPolicySnapshot parent) {
		this.parent = parent;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public List<DZPTicketPolicySnapshot> getSingleTicketPolicies() {
		if (singleTicketPolicies == null)
			singleTicketPolicies = new ArrayList<DZPTicketPolicySnapshot>();
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(List<DZPTicketPolicySnapshot> singleTicketPolicies) {
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

	public Map<String, DZPTicketPolicySnapshotDatePrice> getPrice() {
		return price;
	}

	public void setPrice(Map<String, DZPTicketPolicySnapshotDatePrice> price) {
		this.price = price;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}
}
