package hg.dzpw.domain.model.policy;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @类功能说明：门票政策快照
 * @类修改者：
 * @修改日期：2014-11-25下午6:09:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-25下午6:09:08
 */
@Entity
@Table(name = M.TABLE_PREFIX + "TICKET_POLICY_SNAPSHOT")
public class TicketPolicySnapshot extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票政策基本信息
	 */
	@Embedded
	private TicketPolicyBaseInfo baseInfo;

	/**
	 * 门票政策销售信息
	 */
	@Embedded
	private TicketPolicySellInfo sellInfo;

	/**
	 * 门票政策使用条件信息
	 */
	@Embedded
	private TicketPolicyUseCondition useInfo;

	/**
	 * 所属的景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	/**
	 * 政策类型
	 */
	@Column(name = "POLICY_TYPE")
	private Integer type;

	/**
	 * 所属门票政策ID
	 */
	@Column(name = "TICKET_POLICY_ID", length = 64)
	private String ticketPolicyId;

	/**
	 * 所属门票政策快照
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PARENT_ID")
	private TicketPolicySnapshot parent;

	/**
	 * 门票政策下的单票政策快照
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<TicketPolicySnapshot> singleTicketPolicies;

	/**
	 * 快照时间
	 */
	@Column(name = "SNAPSHOT_DATE", columnDefinition = M.DATE_COLUM)
	private Date snapshotDate;

	/**
	 * 版本（初始版本为1，每次发布增加1）
	 */
	@Column(name = "VERSION", columnDefinition = M.NUM_COLUM)
	private Integer version;

	/**
	 * @方法功能说明：创建门票政策快照
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-6上午10:25:37
	 * @修改内容：
	 * @参数：@param ticketPolicy			只能是类型为独立单票或套票（联票）的政策
	 * @return:void
	 * @throws
	 */
	public void createSnapshot(TicketPolicy ticketPolicy) {
		copyTicketPolicy(ticketPolicy);
		List<TicketPolicy> ticketPolicies = ticketPolicy.getSingleTicketPolicies();
		List<TicketPolicySnapshot> snapshots = getSingleTicketPolicies();
		if (ticketPolicies != null)
			for (TicketPolicy tp : ticketPolicies) {
				TicketPolicySnapshot snapshot = new TicketPolicySnapshot();
				snapshot.copyTicketPolicy(tp);
				snapshot.setParent(this);
				snapshots.add(snapshot);
			}
	}
	
	private void copyTicketPolicy(TicketPolicy ticketPolicy) {
		setId(UUIDGenerator.getUUID());
		setBaseInfo(ticketPolicy.getBaseInfo());
		setSellInfo(ticketPolicy.getSellInfo());
		setUseInfo(ticketPolicy.getUseInfo());
		setScenicSpot(ticketPolicy.getScenicSpot());
		setSnapshotDate(new Date());
		setTicketPolicyId(ticketPolicy.getId());
		setType(ticketPolicy.getType());
		if (ticketPolicy.getLastSnapshot() != null)
			setVersion(ticketPolicy.getLastSnapshot().getVersion() + 1);
		else
			setVersion(1);
		ticketPolicy.setVersion(getVersion());
		ticketPolicy.setLastSnapshot(this);
	}

	public TicketPolicyBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new TicketPolicyBaseInfo();
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

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public Integer getVersion() {
		if (version == null)
			version = 1;
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TicketPolicySnapshot getParent() {
		return parent;
	}

	public void setParent(TicketPolicySnapshot parent) {
		this.parent = parent;
	}

	public List<TicketPolicySnapshot> getSingleTicketPolicies() {
		if (singleTicketPolicies == null)
			singleTicketPolicies = new ArrayList<TicketPolicySnapshot>();
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(
			List<TicketPolicySnapshot> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}

}
