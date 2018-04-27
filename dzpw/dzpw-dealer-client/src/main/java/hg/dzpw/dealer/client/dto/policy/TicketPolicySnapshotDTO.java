package hg.dzpw.dealer.client.dto.policy;

import java.util.List;

import hg.dzpw.dealer.client.common.BaseDTO;

@SuppressWarnings("serial")
public class TicketPolicySnapshotDTO extends BaseDTO {
	
	/** 门票政策类型：独立单票 */
	public final static Integer TICKET_POLICY_TYPE_SINGLE = 1;
	/** 门票政策类型：套票（联票） */
	public final static Integer TICKET_POLICY_TYPE_GROUP = 2;

	/** 门票政策类型：套票（联票）下的单票，这类门票政策不做展示 */
	public final static Integer TICKET_POLICY_TYPE_SINGLE_IN_GROUP = 3;
	/** 门票政策类型：独立单票下的单票，这类门票政策不做展示 */
	public final static Integer TICKET_POLICY_TYPE_SINGLE_IN_SINGLE = 4;
	
	
	/**
	 * 门票政策基本信息
	 */
	private TicketPolicyBaseInfoDTO baseInfo;

	/**
	 * 门票政策销售信息
	 */
	private TicketPolicySellInfoDTO sellInfo;

	/**
	 * 门票政策使用条件信息
	 */
	private TicketPolicyUseConditionDTO useInfo;
	
	/**
	 * 所属门票政策快照ID
	 */
	private String parentId;

	/**
	 * 所属的景区ID
	 */
	private String scenicSpotId;

	/**
	 * 门票政策下的单个景区门票政策[按QO条件决定是否查询]
	 */
	private List<TicketPolicySnapshotDTO> singleTicketPolicies;

	/**
	 * 政策类型
	 */
	private Integer type;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 价格日历
	 * 景区独立单票是游玩日期价格[价格日历中没有的价格不卖票]
	 * [按QO条件决定是否查询]
	 */
	private TicketPolicyPriceCalendarDTO calendar;

	public TicketPolicyBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(TicketPolicyBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public TicketPolicySellInfoDTO getSellInfo() {
		return sellInfo;
	}

	public void setSellInfo(TicketPolicySellInfoDTO sellInfo) {
		this.sellInfo = sellInfo;
	}

	public TicketPolicyUseConditionDTO getUseInfo() {
		return useInfo;
	}

	public void setUseInfo(TicketPolicyUseConditionDTO useInfo) {
		this.useInfo = useInfo;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
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

	public TicketPolicyPriceCalendarDTO getCalendar() {
		return calendar;
	}

	public void setCalendar(TicketPolicyPriceCalendarDTO calendar) {
		this.calendar = calendar;
	}

	public List<TicketPolicySnapshotDTO> getSingleTicketPolicies() {
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(
			List<TicketPolicySnapshotDTO> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}
	
	
}
