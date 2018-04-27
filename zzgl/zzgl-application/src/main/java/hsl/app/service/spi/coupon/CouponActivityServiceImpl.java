package hsl.app.service.spi.coupon;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.coupon.CouponActivityLocalService;
import hsl.domain.model.coupon.CouponActivity;
import hsl.pojo.command.coupon.ActivateCouponActivityCommand;
import hsl.pojo.command.coupon.BatchIssueCouponCommand;
import hsl.pojo.command.coupon.CancelCouponActivityCommand;
import hsl.pojo.command.coupon.CheckCouponActivityCommand;
import hsl.pojo.command.coupon.CouponActivityQuotaMaxCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.coupon.FinishCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivitySendInfoCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.coupon.CouponActivityEventDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.coupon.HslCouponActivityEventQO;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.user.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日下午3:41:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午3:41:25
 *
 */
@Service
public class CouponActivityServiceImpl extends BaseSpiServiceImpl<CouponActivityDTO, HslCouponActivityQO, CouponActivityLocalService> 
	implements CouponActivityService {
	@Autowired
	private CouponActivityLocalService couponActivityLocalService;
	@Autowired
	private UserService userService;
	@Autowired
	private CouponService couponService;
	@Override
	public List<CouponActivityDTO> getCouponActivityList(HslCouponActivityQO qo) {
		List<CouponActivity> list=couponActivityLocalService.queryList(qo);
		ArrayList<CouponActivityDTO> dtolist=new ArrayList<CouponActivityDTO>();
		for(CouponActivity couponActivity:list){
			dtolist.add(BeanMapperUtils.map(couponActivity, CouponActivityDTO.class));
		}
		return dtolist;
	}

	@Override
	public CouponActivityDTO saveCouponActivity(ModifyCouponActivityCommand cmd) {
		HgLogger.getInstance().info("wuyg", "保存卡券活动");
		return couponActivityLocalService.createCouponActivity(cmd);
	}

	@Override
	public CouponActivityDTO updateCouponActivity(ModifyCouponActivityCommand cmd) throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.updateCouponActivity(cmd);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "更新活动发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public CouponActivityDTO cancelCouponActivity(CancelCouponActivityCommand cmd) throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.cancelCouponActivity(cmd);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "取消活动发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	protected CouponActivityLocalService getService() {
		return couponActivityLocalService;
	}

	@Override
	protected Class<CouponActivityDTO> getDTOClass() {
		return CouponActivityDTO.class;
	}


	@Override
	public CouponActivityEventDTO getCancelRemark(HslCouponActivityEventQO qo) {
		return couponActivityLocalService.getCancelRemark(qo);
	}

	@Override
	public CouponActivityDTO checkCouponActivity(CheckCouponActivityCommand command) throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.checkCouponActivity(command);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "活动审核发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public CouponActivityDTO activeCouponActivity(ActivateCouponActivityCommand command)
			throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.activateCouponActivity(command);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "活动上线发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public CouponActivityDTO couponActivityQuotaMax(CouponActivityQuotaMaxCommand command)throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.couponActivityQuotaMax(command);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "活动名额已满发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public CouponActivityDTO finshCouponActivity(
			FinishCouponActivityCommand command) throws CouponActivityException {
		CouponActivityDTO dto=null;
		try {
			dto=couponActivityLocalService.finishCouponActivity(command);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("wuyg", "活动结束发生异常："+e.getLocalizedMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public CouponActivityDTO modifyCouponSendInfo(ModifyCouponActivitySendInfoCommand command) throws CouponActivityException {
		return couponActivityLocalService.modifyCouponSendInfo(command);
	}

	@Override
	public String batchIssueCoupons(BatchIssueCouponCommand command) throws CouponActivityException {
		StringBuffer noSend=new StringBuffer();
		// 根据卡券活动ID查询
		HslCouponActivityQO qo = new HslCouponActivityQO();
		qo.setId(command.getCouponId());
		List<CouponActivityDTO> activityDTOs = this.queryList(qo);
		if (activityDTOs != null && activityDTOs.size() > 0) {
			for (String mobile : command.getMobiles()) {
				// 根据用户登录名查询用户
				HslUserQO hslUserQO = new HslUserQO();
				hslUserQO.setMobile(mobile);
				UserDTO userDTO = userService.queryUnique(hslUserQO);
				if (null == userDTO) {
					noSend.append(mobile);
					noSend.append(",");
					continue;
				}
				// 创建卡券command
				CouponMessage baseAmqpMessage = new CouponMessage();
				baseAmqpMessage.paddingUserRegisterContent(userDTO);
				CreateCouponCommand cmd = baseAmqpMessage.getContent();
				CouponActivityDTO activityDTO = activityDTOs.get(0);
				// 判断卡券活动的优先级
				cmd.setCouponActivityId(activityDTO.getId());
				try {
					HgLogger.getInstance().info("chenxy","批量发送卡券>>发放卡券：" + JSON.toJSONString(cmd, true));
					couponService.createCoupon(cmd);
				} catch (CouponException e) {
					e.printStackTrace();
					noSend.append(mobile);
					noSend.append(",");
					HgLogger.getInstance().error("chenxy","批量发送卡券>>活动id为：" + activityDTO.getId() + "的卡券,用户手机号为："+ mobile + "发放失败");
				}
			}
		} else {
			throw new CouponActivityException(CouponActivityException.ACTIVITY_ID_NULL, "卡券活动不存在");
		}
		String result=noSend.toString();
		return result;
	}
}
