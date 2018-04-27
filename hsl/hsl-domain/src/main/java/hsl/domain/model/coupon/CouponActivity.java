package hsl.domain.model.coupon;

import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;
import hsl.domain.model.M;
import hsl.pojo.command.coupon.CheckCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivityCommand;
import hsl.pojo.exception.CouponActivityException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 卡券活动（一张赠券，可以用于折扣或抵价）
 *
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:59:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:59:56
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_COUPON + "COUPONACTIVITY")
public class CouponActivity extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 卡券活动基本信息
	 */
	@Embedded
	private CouponActivityBaseInfo baseInfo;
	/**
	 * 卡券发放条件信息
	 */
	@Embedded
	private CouponIssueConditionInfo issueConditionInfo;
	/**
	 * 卡券使用条件信息
	 */
	@Embedded
	private CouponUseConditionInfo useConditionInfo;
	/**------1.4.2新添加的字段--start1---***/
	/**
	 * 卡券赠送条件
	 */
	@Embedded
	private CouponSendConditionInfo sendConditionInfo;
	
	/**------1.4.2新添加的字段--end---***/
	
	/**
	 * 卡券活动状态
	 */
	@Embedded
	private CouponActivityStatus status;
	/**
	 * 卡券活动事件
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPONACTIVITY_ID")
	public List<CouponActivityEvent> eventList;

	/**
	 * 发放方式 1、注册发放
	 */
	public static final int ISSUE_WAY_REGIST = 1;
	/**
	 * 2、订单满送
	 */
	public static final int ISSUE_WAY_ORDER_OVER_LINE = 2;
	
	/**
	 * 3、发送福利
	 */
	public static final int ISSUE_WAY_SEND_WELFARE = 3;
	
	/**
	 * 4、转赠
	 */
	public static final int ISSUE_WAY_CONVERSED = 4;
	
	//使用条件
	public static final HashMap<String, String> conditionMap;

	static {
		conditionMap = new HashMap<String, String>();
		conditionMap.put("0", "不限");
		conditionMap.put("1", "订单满");
	}

	/**
	 * 使用条件：不限
	 */
	public static final int CONDITION_UNLIMITED = 0;
	/**
	 * 使用条件：订单满
	 */
	public static final int CONDITION_ORDER_OVER_LINE = 1;
	// 状态常量
	/**
	 * 1未审核
	 */
	public static final Integer COUPONACTIVITY_STATUS_UNCHECK = 1;
	/**
	 * 2审核未通过
	 */
	public static final Integer COUPONACTIVITY_STATUS_CHECK_FAIL = 2;
	/**
	 * 3审核成功
	 */
	public static final Integer COUPONACTIVITY_STATUS_CHECK_OK = 3;
	/**
	 * 4发放中
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVE = 4;
	/**
	 * 5名额已满
	 */
	public static final Integer COUPONACTIVITY_STATUS_QUOTA_FULL = 5;
	/**
	 * 6活动结束
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVITY_OVER = 6;
	/**
	 * 7取消
	 */
	public static final Integer COUPONACTIVITY_STATUS_ACTIVITY_CANCEL = 7;

	@SuppressWarnings("serial")
	public static final Map<String, String> STATUSMAP = new HashMap<String, String>() {
		{
			put("1", "未审核");
			put("2", "审核未通过");
			put("3", "审核成功");
			put("4", "发放中");
			put("5", "名额已满");
			put("6", "活动结束");
			put("7", "取消");
		}
	};

	/**
	 * @throws
	 * @方法功能说明：创建卡券活动
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void create(ModifyCouponActivityCommand cmd) {
		CouponActivityBaseInfo baseinfo = BeanMapperUtils.map(cmd, CouponActivityBaseInfo.class);
		CouponIssueConditionInfo issueinfo = BeanMapperUtils.map(cmd, CouponIssueConditionInfo.class);
		CouponUseConditionInfo useinfo = BeanMapperUtils.map(cmd, CouponUseConditionInfo.class);
		CouponSendConditionInfo sendConditionInfo = BeanMapperUtils.map(cmd, CouponSendConditionInfo.class);
		setBaseInfo(baseinfo);
		setIssueConditionInfo(issueinfo);
		setUseConditionInfo(useinfo);
		setSendConditionInfo(sendConditionInfo);
		setId(cmd.getId());// 3位英文数字，用于票券ID前三位
		if (status == null) {
			status = new CouponActivityStatus();
			status.setCurrentValue(COUPONACTIVITY_STATUS_UNCHECK);
			status.setIssueNum(0L);
		}
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：修改卡券活动基本信息
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void modifyBaseInfo(ModifyCouponActivityCommand command) throws CouponActivityException {

		if (status.getCurrentValue() >= COUPONACTIVITY_STATUS_ACTIVE) {
			throw new CouponActivityException(CouponActivityException.CANNOT_CHANGE_AFTER_ONLINE, "活动开始后不能修改");
		}
		baseInfo.setCouponType(command.getCouponType());
		baseInfo.setFaceValue(command.getFaceValue());
		baseInfo.setName(command.getName());
		baseInfo.setRuleIntro(command.getRuleIntro());
		status.setCurrentValue(COUPONACTIVITY_STATUS_UNCHECK);
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：修改卡券发放信息
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void modifyIssueInfo(ModifyCouponActivityCommand command)
			throws CouponActivityException {
		if (status.getCurrentValue() >= COUPONACTIVITY_STATUS_ACTIVE) {
			throw new CouponActivityException(
					CouponActivityException.CANNOT_CHANGE_AFTER_ONLINE,
					"活动开始后不能修改");
		}
		issueConditionInfo.setIssueBeginDate(command.getIssueBeginDate());
		issueConditionInfo.setIssueEndDate(command.getIssueEndDate());
		issueConditionInfo.setIssueNumber(command.getIssueNumber());
		issueConditionInfo.setIssueNumLine(command.getIssueNumLine());
		issueConditionInfo.setIssueWay(command.getIssueWay());
		issueConditionInfo.setPriority(command.getPriority());
		issueConditionInfo.setPerIssueNumber(command.getPerIssueNumber());
		status.setCurrentValue(COUPONACTIVITY_STATUS_UNCHECK);
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：修改卡券消费信息
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void modifyConsumeInfo(ModifyCouponActivityCommand command)
			throws CouponActivityException {
		if (status.getCurrentValue() >= COUPONACTIVITY_STATUS_ACTIVE) {
			throw new CouponActivityException(
					CouponActivityException.CANNOT_CHANGE_AFTER_ONLINE,
					"活动开始后不能修改");
		}
		useConditionInfo.setBeginDate(command.getBeginDate());
		useConditionInfo.setCondition(command.getCondition());
		useConditionInfo.setEndDate(command.getEndDate());
		useConditionInfo.setMinOrderPrice(command.getMinOrderPrice());
		useConditionInfo.setUsedKind(command.getUsedKind());
		status.setCurrentValue(COUPONACTIVITY_STATUS_UNCHECK);
	}

	public void modifySendInfo(ModifyCouponActivityCommand command) throws CouponActivityException {
		if (status.getCurrentValue() >= COUPONACTIVITY_STATUS_ACTIVE) {
			throw new CouponActivityException(CouponActivityException.CANNOT_CHANGE_AFTER_ONLINE, "活动开始后不能修改");
		}
		sendConditionInfo.setIsSend(command.getIsSend());
		sendConditionInfo.setSendAppendCouponIds(command.getSendAppendCouponIds());
		sendConditionInfo.setUserCreateTime(command.getUserCreateTime());
		status.setCurrentValue(COUPONACTIVITY_STATUS_UNCHECK);
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：活动审核
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void check(CheckCouponActivityCommand command)
			throws CouponActivityException {

		if (command.getApproved()) {
			if (getIssueConditionInfo().getIssueBeginDate().before(new Date())) {
				// 如果活动已经开始，设为上线
				getStatus().setCurrentValue(COUPONACTIVITY_STATUS_ACTIVE);
			} else {
				getStatus().setCurrentValue(COUPONACTIVITY_STATUS_CHECK_OK);
			}
		} else {
			getStatus().setCurrentValue(COUPONACTIVITY_STATUS_CHECK_FAIL);
		}

	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：活动上线
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void active() throws CouponActivityException {
		if (status.getCurrentValue() != COUPONACTIVITY_STATUS_CHECK_OK) {
			throw new CouponActivityException(
					CouponActivityException.CHECK_OK_NEEDED, "活动开始需要先审核通过");
		}
		status.setCurrentValue(COUPONACTIVITY_STATUS_ACTIVE);
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：活动结束
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void finish() throws CouponActivityException {
		if (status.getCurrentValue() < COUPONACTIVITY_STATUS_ACTIVE) {
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_HAVE_NOT_ACTIVE_YET,
					"活动尚未开始");
		}
		if (status.getCurrentValue() > COUPONACTIVITY_STATUS_QUOTA_FULL)
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_ALREADY_OVER, "活动已经结束");
		status.setCurrentValue(COUPONACTIVITY_STATUS_ACTIVITY_OVER);
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：名额已满
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void quotaMax() throws CouponActivityException {
		if (status.getCurrentValue() < COUPONACTIVITY_STATUS_ACTIVE)
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_HAVE_NOT_ACTIVE_YET,
					"活动尚未开始");
		if (status.getCurrentValue() > COUPONACTIVITY_STATUS_QUOTA_FULL)
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_ALREADY_OVER, "活动已经结束");
		status.setCurrentValue(COUPONACTIVITY_STATUS_QUOTA_FULL);
		status.setIssueNum(issueConditionInfo.getIssueNumber());
	}

	/**
	 * @throws CouponActivityException
	 * @throws
	 * @方法功能说明：活动取消
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:26:59
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public int cancel() throws CouponActivityException {
		if (status.getCurrentValue() < COUPONACTIVITY_STATUS_ACTIVE)
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_HAVE_NOT_ACTIVE_YET,
					"活动尚未开始");
		if (status.getCurrentValue() > COUPONACTIVITY_STATUS_QUOTA_FULL)
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_ALREADY_OVER, "活动已经结束");
		status.setCurrentValue(COUPONACTIVITY_STATUS_ACTIVITY_CANCEL);
		return 0;
	}

	public CouponActivityBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(CouponActivityBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public CouponIssueConditionInfo getIssueConditionInfo() {
		return issueConditionInfo;
	}

	public void setIssueConditionInfo(
			CouponIssueConditionInfo issueConditionInfo) {
		this.issueConditionInfo = issueConditionInfo;
	}

	public CouponUseConditionInfo getUseConditionInfo() {
		if (useConditionInfo == null)
			useConditionInfo = new CouponUseConditionInfo();
		return useConditionInfo;
	}

	public void setUseConditionInfo(CouponUseConditionInfo useConditionInfo) {
		this.useConditionInfo = useConditionInfo;
	}

	public CouponActivityStatus getStatus() {
		return status;
	}

	public void setStatus(CouponActivityStatus status) {
		this.status = status;
	}

	public CouponSendConditionInfo getSendConditionInfo() {
		return sendConditionInfo;
	}

	public void setSendConditionInfo(CouponSendConditionInfo sendConditionInfo) {
		this.sendConditionInfo = sendConditionInfo;
	}

}