package hsl.app.service.local.coupon;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.dao.CouponActivityDao;
import hsl.app.dao.CouponActivityEventDao;
import hsl.domain.model.coupon.CouponActivity;
import hsl.domain.model.coupon.CouponActivityBaseInfo;
import hsl.domain.model.coupon.CouponActivityEvent;
import hsl.domain.model.coupon.CouponIssueConditionInfo;
import hsl.domain.model.coupon.CouponSendConditionInfo;
import hsl.domain.model.coupon.CouponUseConditionInfo;
import hsl.pojo.command.coupon.ActivateCouponActivityCommand;
import hsl.pojo.command.coupon.CancelCouponActivityCommand;
import hsl.pojo.command.coupon.CheckCouponActivityCommand;
import hsl.pojo.command.coupon.CouponActivityQuotaMaxCommand;
import hsl.pojo.command.coupon.FinishCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivitySendInfoCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.coupon.CouponActivityEventDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.qo.coupon.HslCouponActivityEventQO;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日下午3:44:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午3:44:55
 * 
 */
@Service
@Transactional
public class CouponActivityLocalService extends
		BaseServiceImpl<CouponActivity, HslCouponActivityQO, CouponActivityDao> {
	@Autowired
	private CouponActivityDao couponActivityDao;
	@Autowired
	private CouponActivityEventDao couponActivityEventDao;

	@Override
	protected CouponActivityDao getDao() {
		return couponActivityDao;
	}

	public CouponActivityEventDTO getCancelRemark(HslCouponActivityEventQO qo) {
		CouponActivityEvent event = couponActivityEventDao.queryUnique(qo);
		return BeanMapperUtils.map(event, CouponActivityEventDTO.class);
	}

	public CouponActivityDTO cancelCouponActivity(
			CancelCouponActivityCommand cmd) throws CouponActivityException {
		if (StringUtils.isBlank(cmd.getCouponActivityId()))
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_ID_NULL, "活动id为空！");
		CouponActivity activity = couponActivityDao.get(cmd
				.getCouponActivityId());
		if (activity == null)
			throw new IllegalArgumentException("活动id有误！	id:"
					+ cmd.getCouponActivityId());
		CouponActivityEvent event = new CouponActivityEvent(
				CouponActivityEvent.EVENT_TYPE_CANCELED,
				cmd.getCouponActivityId(), cmd.getRemark());
		couponActivityEventDao.save(event);
		activity.cancel();
		try {
			couponActivityDao.update(activity);
			return BeanMapperUtils.map(activity, CouponActivityDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CouponActivityDTO createCouponActivity(
			ModifyCouponActivityCommand cmd) {
		CouponActivityEvent event = new CouponActivityEvent(
				CouponActivityEvent.EVENT_TYPE_CREATE, cmd.getId());
		couponActivityEventDao.save(event);
		CouponActivity entity = new CouponActivity();
		entity.create(cmd);
		couponActivityDao.save(entity);
		return BeanMapperUtils.map(entity, CouponActivityDTO.class);
	}

	/**
	 * 
	 * @方法功能说明：审核卡券活动
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日下午4:32:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws CouponActivityException
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO checkCouponActivity(
			CheckCouponActivityCommand command) throws CouponActivityException {

		CouponActivity activity = couponActivityDao.load(command
				.getCouponActivityId());

		activity.check(command);

		// 1未审核 2审核未通过 3审核成功 4发放中 5名额已满 6活动结束7已取消
		// 根据活动状态判断审核结果，保存事件
		switch (activity.getStatus().getCurrentValue()) {
		case 2:
			HgLogger.getInstance().info("wuyg", "卡券活动事件：审核未通过");
			CouponActivityEvent event = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_CHECK_FAIL,
					activity.getId(), "活动" + activity.getId()
							+ activity.getBaseInfo().getName() + "审核未通过");
			couponActivityEventDao.save(event);
			break;
		case 3:
			HgLogger.getInstance().info("wuyg", "卡券活动事件：审核通过");
			CouponActivityEvent event2 = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_CHECK_OK, activity.getId(),
					"活动" + activity.getId() + activity.getBaseInfo().getName()
							+ "审核通过");
			couponActivityEventDao.save(event2);
			break;
		case 4:
			HgLogger.getInstance().info("wuyg", "卡券活动事件：审核通过");
			CouponActivityEvent event3 = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_CHECK_OK, activity.getId(),
					"活动" + activity.getId() + activity.getBaseInfo().getName()
							+ "审核通过");
			couponActivityEventDao.save(event3);
			HgLogger.getInstance().info("wuyg", "卡券活动事件：上线");
			CouponActivityEvent event4 = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_ONLINE, activity.getId(),
					"活动" + activity.getId() + activity.getBaseInfo().getName()
							+ "上线");
			couponActivityEventDao.save(event4);
			break;
		default:
			break;
		}

		return BeanMapperUtils.map(activity, CouponActivityDTO.class);
	}

	/**
	 * @throws CouponActivityException
	 * 
	 * @方法功能说明：活动开始时间到，上线活动
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日下午4:34:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO activateCouponActivity(
			ActivateCouponActivityCommand command)
			throws CouponActivityException {
		HgLogger.getInstance().info("wuyg", "卡券活动事件：开始发放");
		CouponActivity activity = couponActivityDao.load(command
				.getCouponActivityId());

		activity.active();

		CouponActivityEvent event = new CouponActivityEvent(
				CouponActivityEvent.EVENT_TYPE_ONLINE, activity.getId(), "活动"
						+ activity.getId() + activity.getBaseInfo().getName()
						+ "上线");
		couponActivityEventDao.save(event);

		return BeanMapperUtils.map(activity, CouponActivityDTO.class);
	}

	/**
	 * 
	 * @方法功能说明：活动结束
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日下午4:39:10
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws CouponActivityException
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO finishCouponActivity(
			FinishCouponActivityCommand command) throws CouponActivityException {
		HgLogger.getInstance().info("wuyg", "卡券活动事件：活动结束");
		CouponActivity activity = couponActivityDao.load(command
				.getCouponActivityId());

		activity.finish();

		CouponActivityEvent event = new CouponActivityEvent(
				CouponActivityEvent.EVENT_TYPE_OVER, activity.getId(), "活动"
						+ activity.getId() + activity.getBaseInfo().getName()
						+ "结束");
		couponActivityEventDao.save(event);

		return BeanMapperUtils.map(activity, CouponActivityDTO.class);
	}

	/**
	 * 
	 * @方法功能说明：活动名额满
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日下午4:39:10
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws CouponActivityException
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO couponActivityQuotaMax(
			CouponActivityQuotaMaxCommand command)
			throws CouponActivityException {
		HgLogger.getInstance().info("wuyg", "卡券活动事件：名额已满");
		CouponActivity activity = couponActivityDao.load(command
				.getCouponActivityId());

		activity.quotaMax();

		CouponActivityEvent event = new CouponActivityEvent(
				CouponActivityEvent.EVENT_TYPE_QUTOA_FULL, activity.getId(),
				"活动" + activity.getId() + activity.getBaseInfo().getName()
						+ "名额已满");
		couponActivityEventDao.save(event);

		return BeanMapperUtils.map(activity, CouponActivityDTO.class);
	}

	public CouponActivityDTO updateCouponActivity(
			ModifyCouponActivityCommand cmd) throws CouponActivityException {
		if (StringUtils.isBlank(cmd.getId()))
			throw new CouponActivityException(
					CouponActivityException.ACTIVITY_ID_NULL, "活动id为空！");
		CouponActivity activity = couponActivityDao.get(cmd.getId());
		int i = 0;
		CouponActivityBaseInfo baseinfo = activity.getBaseInfo();
		// 比较基本信息
		boolean stat = isModified(baseinfo, cmd);
		if (stat) {
			// 基本信息有改动
			CouponActivityEvent event = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_MODIFY_BASEINFO, cmd.getId());
			couponActivityEventDao.save(event);
			activity.modifyBaseInfo(cmd);
			i++;
		}

		CouponIssueConditionInfo issueinfo = activity.getIssueConditionInfo();
		// 比较发放信息
		stat = isModified(issueinfo, cmd);
		if (stat) {
			CouponActivityEvent event = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_MODIFY_ISSUEINFO,
					cmd.getId());
			couponActivityEventDao.save(event);
			activity.modifyIssueInfo(cmd);
			i++;
		}

		// 比较使用信息
		CouponUseConditionInfo useinfo = activity.getUseConditionInfo();
		stat = isModified(useinfo, cmd);
		if (stat) {
			CouponActivityEvent event = new CouponActivityEvent(
					CouponActivityEvent.EVENT_TYPE_MODIFY_CONSUMEINFO,
					cmd.getId());
			couponActivityEventDao.save(event);
			activity.modifyConsumeInfo(cmd);
			i++;
		}
		// 比较使用信息
		CouponSendConditionInfo sendConditionInfo = activity.getSendConditionInfo();
		stat = isModified(sendConditionInfo, cmd);
		if (stat) {
			CouponActivityEvent event = new CouponActivityEvent(CouponActivityEvent.EVENT_TYPE_MODIFY_CONSUMEINFO,cmd.getId());
			couponActivityEventDao.save(event);
			activity.modifySendInfo(cmd);
			i++;
		}
		try {
			if (i > 0) {
				couponActivityDao.update(activity);
			}
			return BeanMapperUtils.map(activity, CouponActivityDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 比较src和target，src中的数据是否和target中的一样，一样则返回false,不一样则返回true,
	 * src中的字段比target的少，以src为准,出错则返回true
	 * 
	 * @param src
	 * @param target
	 * @return
	 */
	private boolean isModified(Object src, ModifyCouponActivityCommand target) {
		if (src == null || target == null)
			return true;
		Method[] methods = src.getClass().getMethods();
		try {
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					Object value = method.invoke(src);
					Method targetmethod = target.getClass().getMethod(
							method.getName());
					Object value2 = targetmethod.invoke(target);
					if (value != null && value2 != null) {
						if (value.equals(value2)) {
							continue;
						} else {
							return true;
						}
					} else {
						return true;
					}

				}
			}
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @throws CouponActivityException 
	 * @方法功能说明：修改卡券的赠送信息
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月4日下午2:51:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO modifyCouponSendInfo(ModifyCouponActivitySendInfoCommand command) throws CouponActivityException {
		if(StringUtils.isNotBlank(command.getSendAppendCouponIds())){
			String[] ids=command.getSendAppendCouponIds().split(",");
			for(String id:ids){
				HslCouponActivityQO qo = new HslCouponActivityQO();
				qo.setId(id);
				CouponActivity couponActivity = couponActivityDao.queryUnique(qo);
				if(couponActivity==null){
					throw new CouponActivityException(CouponActivityException.ACTIVITY_ID_NULL,"奖励的卡券活动不存在");
				}
				else if(couponActivity.getIssueConditionInfo().getIssueWay()!=CouponActivity.ISSUE_WAY_CONVERSED){
					throw new CouponActivityException(CouponActivityException.ACTIVITY_ID_NULL,"奖励的卡券活动发放渠道非转赠卡券");
				}
			}
		}
		// 根据卡券活动ID 查询卡券活动
		HslCouponActivityQO qo = new HslCouponActivityQO();
		qo.setId(command.getId());
		CouponActivity couponActivity = couponActivityDao.queryUnique(qo);
		// 根据查询的卡券活动去设置赠送属性
		CouponSendConditionInfo couponSendConditionInfo = new CouponSendConditionInfo();
		couponSendConditionInfo.setIsSend(true);
		couponSendConditionInfo.setUserCreateTime(command.getUserCreateTime());
		couponSendConditionInfo.setSendAppendCouponIds(command.getSendAppendCouponIds());
		couponActivity.setSendConditionInfo(couponSendConditionInfo);
		couponActivityDao.update(couponActivity);
		return BeanMapperUtils.map(couponActivity, CouponActivityDTO.class);
	}
	public void initCouponOrderType(){
		
	}
}
