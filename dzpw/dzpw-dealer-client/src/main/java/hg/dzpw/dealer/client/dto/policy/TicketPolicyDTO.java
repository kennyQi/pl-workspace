package hg.dzpw.dealer.client.dto.policy;

import hg.dzpw.dealer.client.common.BaseDTO;

import java.util.List;

/**
 * @类功能说明：门票政策（快照）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:51:34
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketPolicyDTO extends BaseDTO {

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
	 * 门票政策状态
	 */
	private TicketPolicyStatusDTO status;

	/**
	 * 所属套票政策ID
	 */
	private String parentId;

	/**
	 * 所属的景区ID
	 */
	private String scenicSpotId;

	/**
	 * 门票政策下的单个景区门票政策[按QO条件决定是否查询]
	 */
	private List<TicketPolicyDTO> singleTicketPolicies;

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

	public List<TicketPolicyDTO> getSingleTicketPolicies() {
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(
			List<TicketPolicyDTO> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TicketPolicyPriceCalendarDTO getCalendar() {
		return calendar;
	}

	public void setCalendar(TicketPolicyPriceCalendarDTO calendar) {
		this.calendar = calendar;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TicketPolicyStatusDTO getStatus() {
		return status;
	}

	public void setStatus(TicketPolicyStatusDTO status) {
		this.status = status;
	}

}