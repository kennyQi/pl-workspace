package hsl.app.service.local.coupon;
import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.dao.*;
import hsl.domain.model.coupon.*;
import hsl.domain.model.user.User;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.*;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.coupon.HslUserCouponStatisticsQO;
import hsl.pojo.qo.user.HslUserQO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Transactional(rollbackFor=Exception.class)
public class CouponLocalService extends BaseServiceImpl<Coupon, HslCouponQO, CouponDao>{
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private CouponActivityDao couponActivityDao;
	@Autowired
	private CouponActivityEventDao couponActivityEventDao;
	@Autowired
	private UserCouponStatisticsDao userCouponStatisticsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CouponTransferRecordLocalService transferRecordLocalService;
	@Override
	protected CouponDao getDao() {
		return couponDao;
	}
	/**
	 * @throws CouponException
	 * @方法功能说明：废弃卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月15日下午4:51:35
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Coupon
	 * @throws
	 */
	public CouponDTO cancelCoupon(CancelCouponCommand command) throws CouponException {
		Coupon coupon=super.get(command.getCouponId());
		if(coupon!=null){
			CouponEvent couponEvent=coupon.cancellation(command);//废弃该卡券
			//修改卡券信息
			couponDao.update(coupon);
			//添加卡券事件
			couponDao.save(couponEvent);
		}
		CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
		return couponDTO;
	}
	/**
	 * @throws CouponActivityException
	 * @throws CouponException
	 * @throws Exception
	 * @方法功能说明：添加卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月16日上午9:50:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Coupon
	 * @throws
	 */
	public List<CouponDTO> createCoupon(CreateCouponCommand command)throws CouponException, CouponActivityException {
		HgLogger.getInstance().info("chenxy", command.getLoginName() + "添加卡券");
		List<CouponDTO> dtos = new ArrayList<CouponDTO>();
		// 修改活动状态
		CouponActivity activity = couponActivityDao.get(command.getCouponActivityId());
		CouponIssueConditionInfo info = activity.getIssueConditionInfo();
		Date EndDate = info.getIssueEndDate();
		Date now = new Date();
		if (now.after(EndDate)) {// 已经超过发放期限，活动结束
			HgLogger.getInstance().warn("chenxy","卡券活动" + activity.getId() + "超过发放时间：" + EndDate + ",设为结束");
			CouponActivityEvent event = new CouponActivityEvent(CouponActivityEvent.EVENT_TYPE_OVER,command.getCouponActivityId());
			couponActivityEventDao.save(event);
			activity.finish();
			couponActivityDao.update(activity);
			// if(activity.getUseConditionInfo().getEndDate().before(now)){
			// //超过使用期限，设为结束
			// HgLogger.getInstance().warn("wuyg",
			// "卡券活动"+activity.getId()+"超过超过使用期限，设为结束");
			// CouponActivityEvent event=new
			// CouponActivityEvent(CouponActivityEvent.EVENT_TYPE_OVER,
			// command.getCouponActivityId());
			// couponActivityEventDao.save(event);
			// activity.getStatus().setCurrentValue(CouponActivity.COUPONACTIVITY_STATUS_ACTIVITY_OVER);
			// couponActivityDao.update(activity);
			// }
			return dtos;
		}
		int stat = activity.getStatus().getCurrentValue();
		if (stat == CouponActivity.COUPONACTIVITY_STATUS_ACTIVE) {
			// 活动正在进行
			long issuenum = activity.getStatus().getIssueNum();// 已发放数量
			long max = activity.getIssueConditionInfo().getIssueNumber();// 总数
			long pernumber = activity.getIssueConditionInfo().getPerIssueNumber();// 每人发的张数
			if (issuenum + pernumber <= max) {
				// 发放
				for (int i = 0; i < pernumber; i++) {
					UserSnapshot userSnapshot = new UserSnapshot();
					String userId = userSnapshot.create(command);
					getDao().save(userSnapshot);
					Coupon coupon = new Coupon();
					coupon.create(command, userId);
					couponDao.save(coupon);

					CouponDTO couponDTO = BeanMapperUtils.map(coupon,CouponDTO.class);
					dtos.add(couponDTO);
					// 设置卡券发放事件
					CouponEvent couponEvent = new CouponEvent();
					couponEvent.setOccurrenceTime(new Date());
					couponEvent.setEventType(CouponEvent.TYPE_SEND);// 设置卡券发放事件类型
					couponEvent.setCoupon(coupon);
					couponEvent.setId(UUIDGenerator.getUUID());
					getDao().save(couponEvent);
				}
				if (issuenum + pernumber == max) {
					activity.quotaMax();
					CouponActivityEvent event = new CouponActivityEvent(CouponActivityEvent.EVENT_TYPE_QUTOA_FULL,command.getCouponActivityId());
					couponActivityEventDao.save(event);
				} else {
					activity.getStatus().setIssueNum(issuenum + pernumber);
				}
				// 保存用户卡券统计
				UserCouponStatistics userCouponStatistics = new UserCouponStatistics();
				userCouponStatistics.createCouponStatistic(command);
				userCouponStatisticsDao.save(userCouponStatistics);
			} else {
				// 设为名额已满
				HgLogger.getInstance().info("chenxy",activity.getBaseInfo().getName() + "活动名额已满");
				activity.quotaMax();
				activity = couponActivityDao.get(command.getCouponActivityId());
				CouponActivityEvent event = new CouponActivityEvent(CouponActivityEvent.EVENT_TYPE_QUTOA_FULL,command.getCouponActivityId());
				couponActivityEventDao.save(event);
			}
			couponActivityDao.update(activity);
		} else {
			HgLogger.getInstance().error("wuyg", "活动当前状态不可发放卡券,当前状态:" + stat);
			throw new CouponException(CouponException.ACTIVITY_STATUS_ERROR,"当前状态：" + stat + "	,活动状态不符,不能发放");
		}
		// ConsumeOrderSnapshot consumeOrderSnapshot=new ConsumeOrderSnapshot();
		// String orderId=consumeOrderSnapshot.create(command);
		// getDao().save(consumeOrderSnapshot);
		HgLogger.getInstance().info("chenxy",command.getLoginName() + "添加卡券注册成功");
		// return null;
		return dtos;
	}
	/**
	 * @throws CouponException
	 * @方法功能说明：消费卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月16日上午11:12:25
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Coupon
	 * @throws
	 */
	public CouponDTO consumeCoupon(ConsumeCouponCommand command) throws CouponException {
		Coupon coupon=super.get(command.getCouponId());
		if(coupon!=null){
			CouponEvent couponEvent=coupon.consume(command);//消费该卡券
			//保存订单快照
			ConsumeOrderSnapshot consumeOrderSnapshot=new ConsumeOrderSnapshot();
			consumeOrderSnapshot.create(command);
			getDao().save(consumeOrderSnapshot);
			coupon.setConsumeOrder(consumeOrderSnapshot);
			//修改卡券信息
			couponDao.update(coupon);
			//添加卡券事件
			couponDao.save(couponEvent);
		}
		CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
		return couponDTO;
	}
	/**
	 * @throws CouponException
	 * @方法功能说明：过期卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月16日上午11:12:39
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Coupon
	 * @throws
	 */
	public CouponDTO overDueCoupon(CancelCouponCommand command) throws CouponException {
		Coupon coupon=super.get(command.getCouponId());
		if(coupon!=null){
			CouponEvent couponEvent=coupon.pastDue(command);//卡券过期
			//修改卡券信息
			couponDao.update(coupon);
			//添加卡券事件
			couponDao.save(couponEvent);
		}
		CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
		return couponDTO;
	}
	public CouponDTO getCouponByMaxTime(HslCouponQO qo) {
		Coupon coupon=couponDao.getCouponByMaxTime(qo);
		CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
		return couponDTO;

	}

	public List<CouponDTO> orderRefund(OrderRefundCommand command) throws CouponException{
		HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券开始"+JSON.toJSONString(command));
		//删除订单快照，修改卡券状态
		HslCouponQO qo=new HslCouponQO();
		qo.setOrderId(command.getOrderId());
		qo.setCurrentValue(CouponStatus.TYPE_NOUSED);
		qo.setOccupy(true);
		List<Coupon> list=couponDao.queryList(qo);
		List<CouponDTO> dtos=new ArrayList<CouponDTO>();
		if(list==null||list.isEmpty()){
			HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券->没有对应的卡券");
			return dtos;
		}
		//退废卡券时不删除订单快照对象，只是去除引用
		for(int i=0;i<list.size();i++){
			Coupon coupon=list.get(i);
			coupon.setConsumeOrder(null);
			CouponActivity activity=coupon.getBaseInfo().getCouponActivity();
			//卡券事件
			CouponEvent couponEvent=new CouponEvent();
			couponEvent.setCoupon(coupon);
			couponEvent.setId(UUIDGenerator.getUUID());
			couponEvent.setOccurrenceTime(new Date());
			if(activity!=null){
				CouponUseConditionInfo useinfo=activity.getUseConditionInfo();
				Date end=useinfo.getEndDate();
				if(end.before(new Date())){
					HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券->卡券已经过期");
					//已经过期
					couponEvent.setEventType(CouponEvent.TYPE_PASTDUE);
					coupon.getStatus().setCurrentValue(CouponStatus.TYPE_OVERDUE);
				}else{
					HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券->卡券设为未使用");
					couponEvent.setEventType(CouponEvent.TYPE_RETURN);
					coupon.getStatus().setCurrentValue(CouponStatus.TYPE_NOUSED);
				}
				couponDao.save(couponEvent);
			}else{
				HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券->卡券没有对应的活动");
				throw new CouponException(CouponException.ACTIVITY_STATUS_ERROR, "卡券没有对应的活动");
			}
		}
		couponDao.updateList(list);
		HgLogger.getInstance().info("wuyg", "CouponLocalService->orderRefund->退还卡券->结束");
		for(Coupon coupon:list){
			CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
			dtos.add(couponDTO);
		}
		return dtos;
	}

	/**
	 * 只占用卡券，不改变状态
	 * @param command
	 * @return
	 * @throws CouponException
	 */
	public List<CouponDTO> occupyCoupon(BatchConsumeCouponCommand command)
			throws CouponException{
		HgLogger.getInstance().info("wuyg", "CouponLocalService->occupyCoupon->占用卡券开始"+JSON.toJSONString(command));
		List<CouponDTO> list=new ArrayList<CouponDTO>();
		String[] couponids=command.getCouponIds();
		for(String couponid:couponids){
			HslCouponQO qo=new HslCouponQO();
			qo.setId(couponid);
			qo.setCurrentValue(CouponStatus.TYPE_NOUSED);
			Coupon coupon=super.queryUnique(qo);
			if(coupon.getHoldingUser().getUserId().equals(command.getUserID())){
				if(coupon!=null&&StringUtils.isNotBlank(command.getUserID())){
					ConsumeCouponCommand consumeCouponCommand=new ConsumeCouponCommand();
					consumeCouponCommand.setCouponId(couponid);
					consumeCouponCommand.setOrderId(command.getOrderId());
					consumeCouponCommand.setPayPrice(command.getPayPrice());
					consumeCouponCommand.setOrderType(command.getOrderType());
					if(coupon.getConsumeOrder()!=null){
						HgLogger.getInstance().error("wuyg", "CouponLocalService->occupyCoupon->卡券已有订单快照,已被占用."+JSON.toJSONString(coupon));
						throw new CouponException(CouponException.COUPON_UNAVAILABLE,"卡券已有订单快照,已被占用");
					}
					coupon.consume(consumeCouponCommand);//消费该卡券
					coupon.getStatus().setCurrentValue(CouponStatus.TYPE_NOUSED);
					//保存订单快照
					ConsumeOrderSnapshot consumeOrderSnapshot=new ConsumeOrderSnapshot();
					consumeOrderSnapshot.create(consumeCouponCommand);
					getDao().save(consumeOrderSnapshot);
					coupon.setConsumeOrder(consumeOrderSnapshot);
					//修改卡券信息
					couponDao.update(coupon);
					//添加卡券事件
					CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
					list.add(couponDTO);
				}
			}else{
				HgLogger.getInstance().error("wuyg", "CouponLocalService->occupyCoupon->卡券不属于本用户."+JSON.toJSONString(coupon));
				throw new CouponException(CouponException.COUPON_UNAVAILABLE,"请选择正确的卡券");//防止用户用别人的卡券
			}
		}

		HgLogger.getInstance().info("wuyg", "CouponLocalService->occupyCoupon->占用卡券完成");
		return list;
	}


	public List<CouponDTO> confirmConsumeCoupon(BatchConsumeCouponCommand command)throws CouponException {
		List<CouponDTO> list=new ArrayList<CouponDTO>();
		String[] couponids=command.getCouponIds();
		for(String couponid:couponids){
			if(StringUtils.isBlank(couponid)){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE,"卡券id为空");
			}
			Coupon coupon=get(couponid);
			if(coupon==null){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE,"卡券对象为null,id:"+couponid);
			}
			CouponStatus status=coupon.getStatus();
			status.setCurrentValue(CouponStatus.TYPE_ISUSED);
			coupon.setStatus(status);
			update(coupon);
			CouponEvent couponEvent=new CouponEvent();
			couponEvent.setOccurrenceTime(new Date());
			couponEvent.setEventType(CouponEvent.TYPE_CONSUME);
			couponEvent.setId(UUIDGenerator.getUUID());
			couponEvent.setCoupon(coupon);
			//设置卡券事件的订单快照
			couponEvent.setConsumeOrder(coupon.getConsumeOrder());
			couponDao.save(couponEvent);
			CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
			list.add(couponDTO);
		}
		return list;
	}

	public CouponDTO sendCouponToUser(SendCouponToUserCommand command)throws CouponException, CouponActivityException {
		HslCouponQO couponQO = new HslCouponQO();
		couponQO.setCouponId(command.getCouponId());
		Coupon coupon = this.queryUnique(couponQO);
		UserSnapshot fromSnapshot=coupon.getHoldingUser();
		CouponActivity couponActivity = coupon.getBaseInfo().getCouponActivity();
		// 判断卡券活动是否可以赠送
		if (couponActivity.getSendConditionInfo().getIsSend()) {
			HslUserQO userQO = new HslUserQO();
			userQO.setId(command.getUserId());
			User user = userDao.queryUnique(userQO);
			try {
				// 保存现在的用户快照
				CreateCouponCommand createCouponCommand = new CreateCouponCommand();
				createCouponCommand.setEmail(user.getContactInfo().getEmail());
				createCouponCommand.setMobile(user.getContactInfo().getMobile());
				createCouponCommand.setRealName(user.getBaseInfo().getName());
				createCouponCommand.setLoginName(user.getAuthInfo().getLoginName());
				createCouponCommand.setUserId(command.getUserId());
				UserSnapshot toUserSnapshot = new UserSnapshot();
				toUserSnapshot.create(createCouponCommand);
				getDao().save(toUserSnapshot);
				coupon.setHoldingUser(toUserSnapshot);
				coupon.sendTimeAdd();
				couponDao.update(coupon);
				// 删除原来的用户快照
//				    getDao().delete(fromSnapshot);
				// 保存转赠记录
				transferRecordLocalService.createRecord(coupon.getId(), fromSnapshot, toUserSnapshot);
				// 设置卡券赠送事件
				CouponEvent couponEvent = new CouponEvent();
				couponEvent.setOccurrenceTime(new Date());
				couponEvent.setEventType(CouponEvent.TYPE_PRESENT);// 设置卡券发放事件类型
				couponEvent.setCoupon(coupon);
				couponEvent.setId(UUIDGenerator.getUUID());
				getDao().save(couponEvent);
				// 赠送之后判断是否有奖励卡券
				String couponActivityIds = couponActivity.getSendConditionInfo().getSendAppendCouponIds();
				if (couponActivityIds != null) {
					String[] couponActivityArry = couponActivityIds.split(",");
					for (String id : couponActivityArry) {
						HslUserCouponStatisticsQO userCouponStatisticsQO = new HslUserCouponStatisticsQO();
						userCouponStatisticsQO.setUserId(command.getUserId());
						userCouponStatisticsQO.setCouponActivityId(id);
						// 查询如果奖励卡券
						List<UserCouponStatistics> userCouponStatistics = userCouponStatisticsDao.queryList(userCouponStatisticsQO);
						if (couponActivity.getSendConditionInfo().getUserCreateTime()!=null&&user.getBaseInfo().getCreateTime().after(couponActivity.getSendConditionInfo().getUserCreateTime())) {
							if (userCouponStatistics.size() > 0) {
								continue;
							} else {
								// 保存奖励卡券
								HslCouponActivityQO couponActivityQO = new HslCouponActivityQO();
								couponActivityQO.setId(id);
								CouponActivity activity = couponActivityDao.queryUnique(couponActivityQO);
								CreateCouponCommand createCouponCommand2 = new CreateCouponCommand();
								createCouponCommand2.setCouponActivityId(id);
								createCouponCommand2.setDetail("详情");
								createCouponCommand2.setEmail(user.getContactInfo().getEmail());
								createCouponCommand2.setLoginName(user.getAuthInfo().getLoginName());
								createCouponCommand2.setMobile(user.getContactInfo().getMobile());
								createCouponCommand2.setOrderId(null);
								createCouponCommand2.setPerIssueNumber(activity.getIssueConditionInfo().getPerIssueNumber());
								createCouponCommand2.setRealName(user.getBaseInfo().getName());
								createCouponCommand2.setUserId(user.getId());
								this.createCoupon(createCouponCommand2);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("chenxy","转赠用户时异常" + HgLogger.getStackTrace(e));
			}
//			} else {
//				throw new CouponException(CouponException.COUPON_NOSEND,"卡券不能被赠送,用户不是新用户");
//			}
		} else {
			throw new CouponException(CouponException.COUPON_NOSEND, "卡券不能被赠送");
		}
		CouponDTO couponDTO=BeanMapperUtils.map(coupon,CouponDTO.class);
		return couponDTO;
	}
}
