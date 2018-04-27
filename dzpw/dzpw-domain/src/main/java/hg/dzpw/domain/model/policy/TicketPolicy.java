package hg.dzpw.domain.model.policy;

import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotChangeSingleTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotModifySingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotRemoveSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformChangeGroupTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformCreateGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformModifyGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.SingleTicketPolicy;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.ticketpolicy.PlatformCreateTicketPolicyCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 * @类功能说明：门票政策
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:51:34
 * @版本：V1.0
 */
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "TICKET_POLICY")
public class TicketPolicy extends BaseModel {
	private static final long serialVersionUID = 1L;

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
	 * 门票政策状态
	 */
	@Embedded
	private TicketPolicyStatus status;

	/**
	 * 所属门票政策
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_ID")
	private TicketPolicy parent;

	/**
	 * 所属的景区
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	/**
	 * 政策类型
	 */
	@Column(name = "TYPE", columnDefinition = M.NUM_COLUM)
	private Integer type;

	/**
	 * 基准价是否使用价格日历(联票无基准价 不使用    独立单票使用) 
	 */
	@Type(type = "yes_no")
	@Column(name = "USE_DATE_PRICE")
	private Boolean useDatePrice = false;

	/**
	 * 最新快照
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "SNAPSHOT_ID")
	private TicketPolicySnapshot lastSnapshot;

	/**
	 * 门票政策下的单票政策
	 */
	@Where(clause = "REMOVED='N' OR REMOVED IS NULL")
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<TicketPolicy> singleTicketPolicies;

	/**
	 * 版本（和最新快照版本一致）
	 */
	@Column(name = "VERSION", columnDefinition = M.NUM_COLUM)
	private Integer version;

	/**
	 * 价格日历
	 */
	@Transient
	private TicketPolicyPriceCalendar priceCalendar;
	
	/**
	 * @方法功能说明：是否为联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午5:23:46
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isGroup() {
		if (type != null && type == TICKET_POLICY_TYPE_GROUP.intValue())
			return true;
		return false;
	}
	/**
	 * @方法功能说明：是否为联票中的单票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午5:23:46
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isSingleInGroup() {
		if (type != null && type == TICKET_POLICY_TYPE_SINGLE_IN_GROUP.intValue())
			return true;
		return false;
	}
	
	/**
	 * @方法功能说明：是否为独立单票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午5:24:32
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isSingle(){
		if (type != null && type == TICKET_POLICY_TYPE_SINGLE.intValue())
			return true;
		return false;
	}
	
	/**
	 * @方法功能说明：是否为独立单票中的单票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午5:24:32
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isSingleInSingle(){
		if (type != null && type == TICKET_POLICY_TYPE_SINGLE_IN_SINGLE.intValue())
			return true;
		return false;
	}

	/**
	 * @方法功能说明: 新增单票政策
	 */
	public void createSingleTicketPolicy(
			PlatformCreateSingleTicketPolicyCommand command,
			TicketPolicy ticketPolicy) {
		
		setId(UUIDGenerator.getUUID());
		// 联票政策
		setParent(ticketPolicy);
		// 套票（联票）下的单票
		setType(TICKET_POLICY_TYPE_SINGLE_IN_GROUP);
		// 景区Id
		getScenicSpot().setId(command.getScenicSpotId());
		/**
		// 基准价
		getBaseInfo().setPrice(command.getScenicSpotPrice());*/
		// 快照
		TicketPolicySnapshot lastSnapshot = BeanMapperUtils.map(this,
				TicketPolicySnapshot.class);
		lastSnapshot.setId(UUIDGenerator.getUUID());
		lastSnapshot.setTicketPolicyId(getId());
		lastSnapshot.setVersion(1);
		setLastSnapshot(lastSnapshot);
	}

	/**
	 * @方法功能说明: 新增联票政策
	 */
	public void create(PlatformCreateTicketPolicyCommand command) {
		setId(UUIDGenerator.getUUID());
		// 套票（联票）
		setType(TICKET_POLICY_TYPE_GROUP);
		getBaseInfo().setCreateDate(new Date());
		// 联票名称
		getBaseInfo().setName(command.getName());
		// 联票代码
		getBaseInfo().setKey(command.getKey());
		// 创建的管理者id
		getBaseInfo().setCreateAdminId(command.getCreateAdminId());
		// 数量
		getSellInfo().setRemainingQty(command.getRemainingQty());
		getUseInfo().setValidDays(command.getValidDays());
		// 有效期开始时间
		getUseInfo().setFixedUseDateStart(command.getFixedUseDateStart());
		// 有效期结束时间
		getUseInfo().setFixedUseDateEnd(command.getFixedUseDateEnd());
		/**
		// 基准价
		Double price = 0.0;
		for (int i = 0, len = command.getScenicSpotPrices().length; i < len; i++)
			price += command.getScenicSpotPrices()[i];
		getBaseInfo().setPrice(price);
		*/
		// 门市价
		getBaseInfo().setRackRate(command.getRackRate());
		// 预定须知
		getBaseInfo().setNotice(command.getNotice());
		// 景点简介
		getBaseInfo().setIntro(command.getIntro());
		// 交通指南
		getBaseInfo().setTraffic(command.getTraffic());
		// 包含所有景点名称组成的字符串
		getBaseInfo().setScenicSpotNameStr(command.getScenicSpotNameStr());
		// 快照
		TicketPolicySnapshot lastSnapshot = BeanMapperUtils.map(this,
				TicketPolicySnapshot.class);
		lastSnapshot.setId(UUIDGenerator.getUUID());
		lastSnapshot.setTicketPolicyId(getId());
		lastSnapshot.setVersion(1);
		setStatus(new TicketPolicyStatus());
		setLastSnapshot(lastSnapshot);
	}

	/**
	 * @throws DZPWException
	 * @方法功能说明: 审核通过
	 */
	public void checkPass() throws DZPWException {
		if (!getStatus().isFinish()) {
			getStatus().setCurrent(
					TicketPolicyStatus.TICKET_POLICY_STATUS_CHECKED);
		} else {
			throw new DZPWException(DZPWException.TICKET_POLICY_STATUS_FINISH,
					"联票政策已被下架");
		}
	}

	/**
	 * @方法功能说明: 审核不通过
	 */
	public void checkUnPass() throws DZPWException {
		if (!getStatus().isFinish()) {
			getStatus().setCurrent(
					TicketPolicyStatus.TICKET_POLICY_STATUS_DISAPPROVED);
		} else {
			throw new DZPWException(DZPWException.TICKET_POLICY_STATUS_FINISH,
					"联票政策已被下架");
		}
	}

	/**
	 * @方法功能说明: 发布
	 */
	public void issue() throws DZPWException {
		if (!getStatus().isFinish()) {
			getStatus().setCurrent(
					TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE);
		} else {
			throw new DZPWException(DZPWException.TICKET_POLICY_STATUS_FINISH,
					"联票政策已被下架");
		}
	}

	/**
	 * @方法功能说明: 下架
	 */
	public void close() throws DZPWException {
		getStatus().setCurrent(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
	}

	/**
	 * @方法功能说明: 是否套票政策
	 * @return
	 */
	public Boolean isGroupPolicy() {
		if (type != null && type == TICKET_POLICY_TYPE_GROUP.intValue())
			return true;
		return false;
	}

	/**
	 * @方法功能说明：创建独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12下午4:26:02
	 * @参数：@param command
	 * @return:void
	 */
	public void scenicspotCreateSingleTicketPolicy(ScenicspotCreateSingleTicketPolicyCommand command, ScenicSpot scenicSpot) {

		Date now = new Date();
		
		// 独立单票
		setId(UUIDGenerator.getUUID());
		getBaseInfo().setCreateAdminId(command.getFromAdminId());
		getBaseInfo().setScenicSpotNameStr(scenicSpot.getBaseInfo().getName());
		getBaseInfo().setCreateDate(now);
		getBaseInfo().setModifyDate(now);
		getBaseInfo().setKey(command.getKey());
		getBaseInfo().setName(command.getName());
		getBaseInfo().setRackRate(command.getRackRate());
		getBaseInfo().setNotice(command.getNotice());
		getBaseInfo().setIntro(command.getIntro());
		getBaseInfo().setSaleAgreement(command.getSaleAgreement());
		getSellInfo().setReturnRule(command.getReturnRule());
		getSellInfo().setReturnCost(command.getReturnCost());
		getSellInfo().setRemainingQty(command.getRemainingQty());
		getSellInfo().setAutoMaticRefund(command.getAutoMaticRefund());
		getUseInfo().setValidDays(command.getValidDays());
		getUseInfo().setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
		getUseInfo().setValidTimesPerDay(1);//景区单天只能入园一次
		getStatus();
		// 独立单表统一使用价格日历
		setUseDatePrice(true);
		// 独立单票
		setType(TICKET_POLICY_TYPE_SINGLE);
		// 设置所属景区
		setScenicSpot(scenicSpot);
		
		// 单票 -------------------------------------------
		TicketPolicy ticketPolicy = new TicketPolicy();
		ticketPolicy.setId(UUIDGenerator.getUUID());
		ticketPolicy.getBaseInfo().setShortName(scenicSpot.getBaseInfo().getShortName());
		ticketPolicy.getBaseInfo().setScenicSpotNameStr(scenicSpot.getBaseInfo().getName());
		ticketPolicy.getBaseInfo().setCreateDate(now);
		ticketPolicy.getBaseInfo().setModifyDate(now);
		ticketPolicy.getBaseInfo().setCreateAdminId(command.getFromAdminId());
		ticketPolicy.getUseInfo().setValidDays(command.getValidDays());//景区的可连续游玩天数
		ticketPolicy.getUseInfo().setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
		ticketPolicy.getUseInfo().setValidTimesPerDay(1);//景区单天只能入园一次
		
		
		ticketPolicy.getStatus();
		ticketPolicy.setType(TICKET_POLICY_TYPE_SINGLE_IN_SINGLE);
		ticketPolicy.setParent(this);
		ticketPolicy.setScenicSpot(scenicSpot);
		// -------------------------------------------
		
		getSingleTicketPolicies().add(ticketPolicy);
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：修改独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-3下午3:28:50
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotModifySingleTicketPolicy(ScenicspotModifySingleTicketPolicyCommand command) throws DZPWException{

		// 已发布状态下的门票政策不可修改
		if (getStatus().isIssue())
			throw new DZPWException(DZPWException.SINGLE_TICKET_POLICY_ISSUE,
					"启用状态的门票政策不能修改，请禁止后操作。");

		// 独立单票
		getBaseInfo().setName(command.getName());
		getBaseInfo().setRackRate(command.getRackRate());
		getBaseInfo().setNotice(command.getNotice());
		getBaseInfo().setIntro(command.getIntro());
		getBaseInfo().setSaleAgreement(command.getSaleAgreement());
		getBaseInfo().setModifyDate(new Date());
		
		getUseInfo().setValidDays(command.getValidDays());
		
		getSellInfo().setReturnRule(command.getReturnRule());
		getSellInfo().setReturnCost(command.getReturnCost());
		getSellInfo().setRemainingQty(command.getRemainingQty());
		getSellInfo().setAutoMaticRefund(command.getAutoMaticRefund());

		// 单票  -------------------------------------------
		TicketPolicy ticketPolicy = getSingleTicketPolicies().get(0);
		
		ticketPolicy.getBaseInfo().setModifyDate(new Date());
		ticketPolicy.getUseInfo().setValidDays(command.getValidDays());
		
		
//		ticketPolicy.setBaseInfo(getBaseInfo());
//		ticketPolicy.setUseInfo(getUseInfo());
//		ticketPolicy.setSellInfo(getSellInfo());
		
	}

	/**
	 * @方法功能说明：运营端创建联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-17下午4:37:23
	 * @参数：@param command
	 * @参数：@param scenicSpotMap
	 * @return:void
	 * @throws
	 */
	public void platformCreateGroupTicketPolicy(PlatformCreateGroupTicketPolicyCommand command, Map<String, ScenicSpot> scenicSpotMap) throws DZPWException {

		// 检查
		if (command.getSingleTicketPolicies() == null || command.getSingleTicketPolicies().size() <= 1)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "联票需要添加2个或以上的景区");
		
		for (SingleTicketPolicy policy : command.getSingleTicketPolicies()) {
			
			ScenicSpot scenicSpot = scenicSpotMap.get(policy.getScenicSpotId());
			if (scenicSpot == null)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "未知的景区标识:" + policy.getScenicSpotId());
			
			if (policy.getValidDays() == null || policy.getValidDays() <= 0)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, String.format("[%s]缺少有效游玩天数", scenicSpot.getBaseInfo().getName()));
		}
		
		Date now = new Date();

		// 联票
		setId(UUIDGenerator.getUUID());
		getBaseInfo().setKey(command.getKey());
		getBaseInfo().setName(command.getName());
		getBaseInfo().setCreateAdminId(command.getFromAdminId());
		getBaseInfo().setRackRate(command.getRackRate());//联票所有景区的市场票面价总和
		getBaseInfo().setPlayPrice(command.getPlayPrice());//联票所有景区的游玩理财价总和
		getBaseInfo().setSettlementPrice(command.getSettlementPrice());//联票所有景区的结算价总和
		getSellInfo().setAutoMaticRefund(command.getAutoMaticRefund());//过期自动退
		getBaseInfo().setIntro(command.getIntro());
		getBaseInfo().setNotice(command.getNotice());
		getBaseInfo().setSaleAgreement(command.getSaleAgreement());
		getUseInfo().setValidUseDateType(command.getValidUseDateType());
		getUseInfo().setValidDays(command.getValidDays());//联票的有效天数
		getUseInfo().setValidTimesPerDay(1);//景区单天只能入园一次
		getSellInfo().setReturnRule(command.getReturnRule());
		getSellInfo().setReturnCost(command.getReturnCost());
		getSellInfo().setRemainingQty(command.getRemainingQty());
		getBaseInfo().setCreateDate(now);
		getBaseInfo().setModifyDate(now);
		getStatus();
		// 联票基准价不使用价格日历
		setUseDatePrice(false);
		// 联票
		setType(TICKET_POLICY_TYPE_GROUP);
		
		// 单票-------------------------------------------

		StringBuilder scenicSpotNames = new StringBuilder();
		for (SingleTicketPolicy stp : command.getSingleTicketPolicies()) {
			ScenicSpot scenicSpot = scenicSpotMap.get(stp.getScenicSpotId());

			if (scenicSpot == null)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "未知的景区标识:" + stp.getScenicSpotId());
			
			TicketPolicy ticketPolicy = new TicketPolicy();
			ticketPolicy.setId(UUIDGenerator.getUUID());
			ticketPolicy.getBaseInfo().setName(scenicSpot.getBaseInfo().getName());
			ticketPolicy.getBaseInfo().setShortName(scenicSpot.getBaseInfo().getShortName());
			ticketPolicy.getBaseInfo().setScenicSpotNameStr(scenicSpot.getBaseInfo().getName());
			ticketPolicy.getBaseInfo().setCreateDate(now);
			ticketPolicy.getBaseInfo().setModifyDate(now);
			ticketPolicy.getBaseInfo().setCreateAdminId(command.getFromAdminId());
			ticketPolicy.getUseInfo().setValidDays(stp.getValidDays());//景区的可连续游玩天数
			ticketPolicy.getUseInfo().setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
			ticketPolicy.getUseInfo().setValidTimesPerDay(1);//景区单天只能入园一次
			ticketPolicy.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE_IN_GROUP);
			ticketPolicy.setParent(this);
			ticketPolicy.getStatus();
			ticketPolicy.getBaseInfo().setRackRate(stp.getRackRate());//单个景区的市场票面价
			ticketPolicy.getBaseInfo().setPlayPrice(stp.getPlayPrice());//单个景区的游玩理财价
			ticketPolicy.getBaseInfo().setSettlementPrice(stp.getSettlementPrice());//单个景区的结算价
			getSingleTicketPolicies().add(ticketPolicy);

			// 设置所属景区
			ticketPolicy.setScenicSpot(scenicSpot);
			if (scenicSpotNames.length() > 0)
				scenicSpotNames.append("、");
			
			scenicSpotNames.append(scenicSpot.getBaseInfo().getName());
		}
		
		getBaseInfo().setScenicSpotNameStr(scenicSpotNames.toString());
	}
	
	
	/**
	 * @方法功能说明：运营端修改联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-17下午4:37:45
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void platformModifyGroupTicketPolicy(PlatformModifyGroupTicketPolicyCommand command, Map<String, ScenicSpot> scenicSpotMap) throws DZPWException {

		// 已发布状态下的门票政策不可修改
		if (getStatus().isIssue())
			throw new DZPWException(DZPWException.SINGLE_TICKET_POLICY_ISSUE, "启用状态的联票政策不能修改，请禁止后操作。");
		

		// 检查
		if (command.getSingleTicketPolicies() == null || command.getSingleTicketPolicies().size() <= 1)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "联票需要添加2个或以上的景区");
		
		for (SingleTicketPolicy policy : command.getSingleTicketPolicies()) {
			ScenicSpot scenicSpot = scenicSpotMap.get(policy.getScenicSpotId());
			if (scenicSpot == null)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "未知的景区标识:" + policy.getScenicSpotId());
			
			if (policy.getValidDays() == null || policy.getValidDays() <= 0)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, String.format("[%s]缺少有效游玩天数", scenicSpot.getBaseInfo().getName()));
		}
		
		Date now = new Date();

		// 联票
		getBaseInfo().setKey(command.getKey());
		getBaseInfo().setName(command.getName());
		getBaseInfo().setIntro(command.getIntro());
		getBaseInfo().setNotice(command.getNotice());
		getBaseInfo().setSaleAgreement(command.getSaleAgreement());
		getBaseInfo().setModifyDate(now);
		getUseInfo().setValidUseDateType(command.getValidUseDateType());
		getUseInfo().setValidDays(command.getValidDays());
		getSellInfo().setReturnRule(command.getReturnRule());
		getSellInfo().setReturnCost(command.getReturnCost());
		getSellInfo().setRemainingQty(command.getRemainingQty());
		getBaseInfo().setRackRate(command.getRackRate());//联票所有景区的市场票面价总和
		getBaseInfo().setPlayPrice(command.getPlayPrice());//联票所有景区的游玩理财价总和
		getBaseInfo().setSettlementPrice(command.getSettlementPrice());//联票所有景区的结算价总和
		getSellInfo().setAutoMaticRefund(command.getAutoMaticRefund());//过期自动退
		getStatus();
		
		// -------------------------------------------

		StringBuilder scenicSpotNames = new StringBuilder();
//		double price = 0d;
		
		Map<String, TicketPolicy> oldPolicyMap = new HashMap<String, TicketPolicy>();
		for(TicketPolicy tp : getSingleTicketPolicies()) {
			tp.getStatus().setRemoved(true);
			oldPolicyMap.put(tp.getScenicSpot().getId(), tp);
		}
		getSingleTicketPolicies().clear();
		
		// 单票
		for (SingleTicketPolicy stp : command.getSingleTicketPolicies()) {
			ScenicSpot scenicSpot = scenicSpotMap.get(stp.getScenicSpotId());
			TicketPolicy ticketPolicy = oldPolicyMap.get(stp.getScenicSpotId());
			
			if (scenicSpot == null)
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "未知的景区标识:" + stp.getScenicSpotId());

			if (ticketPolicy == null) {
				ticketPolicy = new TicketPolicy();
				ticketPolicy.setId(UUIDGenerator.getUUID());
				ticketPolicy.getBaseInfo().setName(scenicSpot.getBaseInfo().getName());
				ticketPolicy.getBaseInfo().setShortName(scenicSpot.getBaseInfo().getShortName());
				ticketPolicy.getBaseInfo().setScenicSpotNameStr(scenicSpot.getBaseInfo().getName());
				ticketPolicy.getBaseInfo().setCreateDate(now);
				ticketPolicy.getBaseInfo().setCreateAdminId(command.getFromAdminId());
				ticketPolicy.getBaseInfo().setRackRate(stp.getRackRate());//单个景区的市场票面价
				ticketPolicy.getBaseInfo().setPlayPrice(stp.getPlayPrice());//单个景区的游玩理财价
				ticketPolicy.getBaseInfo().setSettlementPrice(stp.getSettlementPrice());//单个景区的结算价
				ticketPolicy.getUseInfo().setValidDays(stp.getValidDays());
				ticketPolicy.getUseInfo().setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
				ticketPolicy.setType(TICKET_POLICY_TYPE_SINGLE_IN_GROUP);
				ticketPolicy.setParent(this);
				ticketPolicy.getStatus();
			} else {
				ticketPolicy.getStatus().setRemoved(false);
				ticketPolicy.getBaseInfo().setSettlementPrice(stp.getSettlementPrice());
				ticketPolicy.getBaseInfo().setPlayPrice(stp.getPlayPrice());
				ticketPolicy.getBaseInfo().setRackRate(stp.getRackRate());
				ticketPolicy.getUseInfo().setValidDays(stp.getValidDays());
			}
			
			ticketPolicy.getBaseInfo().setModifyDate(now);
			getSingleTicketPolicies().add(ticketPolicy);
			
			// 设置所属景区
			ticketPolicy.setScenicSpot(scenicSpot);
			getSingleTicketPolicies().add(ticketPolicy);
			if (scenicSpotNames.length() > 0)
				scenicSpotNames.append("、");
			scenicSpotNames.append(scenicSpot.getBaseInfo().getName());
		}
		
		getBaseInfo().setScenicSpotNameStr(scenicSpotNames.toString());
	}

	/**
	 * @方法功能说明：运营端启用禁用联票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-18上午10:38:30
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean platformChangeGroupTicketPolicyStatus(
			PlatformChangeGroupTicketPolicyStatusCommand command) {
		
		boolean needSnapshot = false;
		List<TicketPolicy> policies = getSingleTicketPolicies();

		Integer newCurrentValue = TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH;

		// false为下架
		if (command.getActive() == null || !command.getActive()) {
			newCurrentValue = TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH;

		}
		// true为发布
		else {
			needSnapshot = !getStatus().isIssue();
			newCurrentValue = TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE;
		}

		Date now = new Date();
		getStatus().setCurrent(newCurrentValue);
		getBaseInfo().setModifyDate(now);
		if (policies != null)
			for (TicketPolicy policy : policies) {
				policy.getStatus().setCurrent(newCurrentValue);
				policy.getBaseInfo().setModifyDate(now);
			}
		
		return needSnapshot;
	}
	
	
	/**
	 * @方法功能说明：景区后台禁用启用门票政策
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-6上午11:03:35
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean			是否需要生成快照
	 * @throws
	 */
	public boolean scenicspotChangeSingleTicketPolicyStatus(
			ScenicspotChangeSingleTicketPolicyStatusCommand command) {
		
		boolean needSnapshot = false;
		List<TicketPolicy> policies = getSingleTicketPolicies();

		Integer newCurrentValue = null;

		// false为下架
		if (command.getActive() == null || !command.getActive()) {
			newCurrentValue = TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH;

		}
		// true为发布
		else {
			needSnapshot = !getStatus().isIssue();
			newCurrentValue = TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE;
		}

		Date now = new Date();
		getStatus().setCurrent(newCurrentValue);
		getBaseInfo().setModifyDate(now);
		if (policies != null)
			for (TicketPolicy policy : policies) {
				policy.getStatus().setCurrent(newCurrentValue);
				policy.getBaseInfo().setModifyDate(now);
			}
		
		return needSnapshot;
	}
	
	/**
	 * @方法功能说明：逻辑删除
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-3下午5:27:13
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void remove() {
		getStatus().setCurrent(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
		getStatus().setRemoved(true);
	}

	/**
	 * @方法功能说明：删除独立单票政策（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-12下午4:26:02
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void scenicspotRemoveSingleTicketPolicy(ScenicspotRemoveSingleTicketPolicyCommand command)
			throws DZPWException {
		
		if (getScenicSpot() == null
				|| !StringUtils.equals(command.getScenicSpotId(), getScenicSpot().getId()))
			throw new DZPWException(DZPWException.NO_AUTH, "无权操作！");
		
		if (command.getTicketPolicyId() == null || command.getTicketPolicyId().indexOf(getId()) == -1)
			return;
		
		remove();
		List<TicketPolicy> policies = getSingleTicketPolicies();
		if (policies != null)
			for (TicketPolicy policy : policies)
				policy.remove();
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
		if (sellInfo == null)
			sellInfo = new TicketPolicySellInfo();
		return sellInfo;
	}

	public void setSellInfo(TicketPolicySellInfo sellInfo) {
		this.sellInfo = sellInfo;
	}

	public TicketPolicyUseCondition getUseInfo() {
		if (useInfo == null)
			useInfo = new TicketPolicyUseCondition();
		return useInfo;
	}

	public void setUseInfo(TicketPolicyUseCondition useInfo) {
		this.useInfo = useInfo;
	}

	public TicketPolicyStatus getStatus() {
		if (status == null)
			status = new TicketPolicyStatus();
		return status;
	}

	public void setStatus(TicketPolicyStatus status) {
		this.status = status;
	}

	public TicketPolicy getParent() {
		return parent;
	}

	public void setParent(TicketPolicy parent) {
		this.parent = parent;
	}

	public ScenicSpot getScenicSpot() {
		// 如果是单票，才需要关联景区
		if (parent != null) {
			if (scenicSpot == null)
				scenicSpot = new ScenicSpot();
		}
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

	public TicketPolicySnapshot getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(TicketPolicySnapshot lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public List<TicketPolicy> getSingleTicketPolicies() {
		if (singleTicketPolicies == null)
			singleTicketPolicies = new ArrayList<TicketPolicy>();
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(List<TicketPolicy> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}

	public Boolean getUseDatePrice() {
		if (useDatePrice == null)
			useDatePrice = false;
		return useDatePrice;
	}

	public void setUseDatePrice(Boolean useDatePrice) {
		this.useDatePrice = useDatePrice;
	}

	public Integer getVersion() {
		if (version == null && getLastSnapshot() != null
				&& Hibernate.isInitialized(getLastSnapshot()))
			version = getLastSnapshot().getVersion();
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TicketPolicyPriceCalendar getPriceCalendar() {
		return priceCalendar;
	}

	public void setPriceCalendar(TicketPolicyPriceCalendar priceCalendar) {
		this.priceCalendar = priceCalendar;
	}

}