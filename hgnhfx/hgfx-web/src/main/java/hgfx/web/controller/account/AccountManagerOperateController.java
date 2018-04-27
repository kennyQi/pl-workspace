package hgfx.web.controller.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import hg.captcha.common.HGCaptchaConstants;
import hg.captcha.common.command.SmsCodeCommand;
import hg.captcha.common.dto.HGCaptchaDTO;
import hg.captcha.sdk.HGCaptchaService;
import hg.demo.member.common.MD5HashUtil;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.SmsCodeRecordSQO;
import hgfx.web.component.cache.FindPwdManager;
import hgfx.web.controller.sys.BaseController;
import hgfx.web.util.JsonResult;
import hgfx.web.util.SmsUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：个人帐号管理controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月7日 下午3:40:10
 * @版本： V1.0
 */
@Controller
@RequestMapping(value = "account")
public class AccountManagerOperateController extends BaseController {
	@Autowired
	private SmsUtil smsUtil;
	@Autowired
	private SmsCodeRecordSPI smsCodeRecordSPI;
	@Autowired
	private FindPwdManager findPwdManager;
	@Autowired
	private HGCaptchaService hgCaptchaService;
	@Autowired
	private DistributorUserSPI distributorUserSPI;
	@Autowired
	private DistributorSPI distributorSPI;

	private static final String SMS_MSG = SmsUtil.SMS_PRE汇购
			+ "尊敬的汇购用户，您正在进行修改手机操作，验证码为#SMS_CODE#，请于30分钟内正确输入验证码";
	private static final String SMS_MSG_SESSION_KEY_OP = "_SMS_MSG_SESSION_KEY_";// 验证码记录放到session中key
	private static final String SMS_TO_RESET_PAGE_SESSION_KEY_OP = "_SMS_TO_RESET_PAGE_SESSION_KEY_";// 随机生成的临时token
	// 密码规则，数字，字母，特殊标点符合(除空格)8`20位
	private static final String REG_EX_PASS = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z`~!@#$%^&*+-=|{}':;_,\"\\[\\].<>/?]{8,20}$";
	private static final String MOB_SESSION_NEAR_OP = "_MOB_SESSION_NEAR_OP";
	private static final String SMS_SEND_SESSION_NEAR_OP = "_SMS_SEND_SESSION_NEAR_OP";

	/**
	 * 
	 * @方法功能说明：修改手机-发送短信验证码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:57:16
	 * @修改内容：
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "prepareSendSms", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult prepareSendSms(String mobile, HttpSession httpSession,
			Integer type) {
		if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("请输入手机号码",
					JsonResult.RESULT_CODE_TYPE_ONE));
		} else if (!isMobileNO(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("输入正确手机号",
					JsonResult.RESULT_CODE_TYPE_ONE));
		}

		try {

			// 从session中取出商户的手机号码,发送短信验证码
			DistributorUser user = getSessionUserInfo(httpSession);
			if (StringUtils.isEmpty(user)) {
				return JsonResult.fail("您还未登陆，请登录后再试");
			} else if (user.getStatus().equals(
					DistributorUser.DSTRIBUTOR_USER_TYPE_SUB)) {
				// 子帐号不能修改手机号码
				return JsonResult.fail("没有权限修改手机号码");
			}

			// 查询是否已经存在该手机号码
			if (distributorSPI.checkExistPhone(mobile)) {
				return JsonResult.fail(JsonResult.dealResult("该手机号码已存在",
						JsonResult.RESULT_CODE_TYPE_ONE));
			}

			// 每日找回密码限制：从缓存中获取该手机号码今天发短信的次数,limitNum次之后不能再找回密码
			Integer limitNum = findPwdManager.getFindPwdTimes();
			limitNum = limitNum == null ? 0 : limitNum;
			int sendNum = queryTodaysSmsNum(mobile);
			if (sendNum >= limitNum.intValue()) {
				return JsonResult.fail("此手机发送验证码次数已达上限，请在24小时后重试");
			}

			return JsonResult.success("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("发送失败");
		}
	}

	@RequestMapping(value = "sendSms", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendSms(String mobile, HttpSession httpSession,
			Integer type) {
		long time = System.currentTimeMillis();
		if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("请输入手机号码",
					JsonResult.RESULT_CODE_TYPE_ONE));
		} else if (!isMobileNO(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("输入正确手机号",
					JsonResult.RESULT_CODE_TYPE_ONE));
		}

		try {

			// 从session中取出商户的手机号码,发送短信验证码
			DistributorUser user = getSessionUserInfo(httpSession);
			if (StringUtils.isEmpty(user)) {
				return JsonResult.fail("您还未登陆，请登录后再试");
			} else if (user.getStatus().equals(
					DistributorUser.DSTRIBUTOR_USER_TYPE_SUB)) {
				// 子帐号不能修改手机号码
				return JsonResult.fail("没有权限修改手机号码");
			}

			// 查询是否已经存在该手机号码
			if (distributorSPI.checkExistPhone(mobile)) {
				return JsonResult.fail(JsonResult.dealResult("该手机号码已存在",
						JsonResult.RESULT_CODE_TYPE_ONE));
			}

			JsonResult result = sendSms(mobile, httpSession, time);
			if (result.getCode() == JsonResult.CODE_SUCCESS) {
				if (!StringUtils.isEmpty(result.getMsg())
						&& !result.getMsg().equals(JsonResult.MSG_SUCCESS)) {
					// 把手机号码加入到session
					httpSession.setAttribute(MOB_SESSION_NEAR_OP, mobile);

					// 添加短信记录
					SmsCodeRecord scr = createSmsRecord(result.getMsg()
							.toString(), mobile, 30);
					// 把添加短信记录存到session中
					httpSession.setAttribute(SMS_MSG_SESSION_KEY_OP,
							JSON.toJSONString(scr));
					return JsonResult.success("发送成功");
				} else {
					return JsonResult.success(101, "发送成功");
				}

			}
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return JsonResult.fail("发送失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("发送失败");
		}
	}

	/**
	 * 
	 * @方法功能说明：修改手机-校验短信验证码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午2:51:03
	 * @修改内容：
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkCode(String mobile, String code, Integer type,
			HttpSession httpSession) {
		DistributorUser user = getSessionUserInfo(httpSession);
		// 判断是否登录才能重置
		if (StringUtils.isEmpty(user)) {
			return JsonResult.fail("请先登录");
		} else if (!user.getStatus().equals(
				DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN)) {
			// 子帐号不能修改手机号码
			return JsonResult.fail("没有权限修改手机号码");
		}

		if (StringUtils.isEmpty(code)) {
			return JsonResult.fail(JsonResult.dealResult("短信验证码错误请重新输入",
					JsonResult.RESULT_CODE_TYPE_TWO));
		} else if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("请输入手机号码",
					JsonResult.RESULT_CODE_TYPE_ONE));
		}

		SmsCodeRecord scr = getCode(mobile, code);
		if (StringUtils.isEmpty(scr) || StringUtils.isEmpty(scr.getCode())) {
			return JsonResult.fail(JsonResult.dealResult("短信验证码错误请重新输入",
					JsonResult.RESULT_CODE_TYPE_TWO));
		} else if (scr.getInvalidDate().getTime() < new Date().getTime()) {
			return JsonResult.fail(JsonResult.dealResult("该验证码已过期",
					JsonResult.RESULT_CODE_TYPE_TWO));

		}

		// 查询是否已经存在该手机号码
		if (distributorSPI.checkExistPhone(mobile)) {
			return JsonResult.fail(JsonResult.dealResult("该手机号码已存在",
					JsonResult.RESULT_CODE_TYPE_ONE));
		} else {
			// 修改手机号码
			modifyMobile(httpSession, mobile);
		}

		// 改变验证码状态使用
		ModifySmsCodeRecordCommand command = new ModifySmsCodeRecordCommand();
		command.setId(scr.getId());
		smsCodeRecordSPI.modifyUsedStatus(command);

		String num = UUID.randomUUID().toString().replaceAll("-", "");
		httpSession.setAttribute(SMS_TO_RESET_PAGE_SESSION_KEY_OP, num);
		httpSession.removeAttribute(SMS_MSG_SESSION_KEY_OP);
		return JsonResult.success(num);
	}

	/**
	 * 
	 * @方法功能说明：修改密码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月7日 上午11:37:43
	 * @修改内容：
	 * @param originalPass
	 * @param newPass
	 * @param againPass
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "modfiyPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modfiyPass(String originalPass, String newPass,
			String againPass, HttpSession httpSession) {
		DistributorUser user = getSessionUserInfo(httpSession);
		// 判断是否登录才能重置
		if (StringUtils.isEmpty(user)) {
			return JsonResult.fail("请先登录");
		}

		if (StringUtils.isEmpty(originalPass)) {
			return JsonResult.fail(JsonResult.dealResult("请输入原始密码",
					JsonResult.RESULT_CODE_TYPE_ONE));
		}

		String mdOriginalPass = MD5HashUtil.toMD5(originalPass);
		DistributorUser userQ = getUser(httpSession);
		if (!userQ.getPasswd().equals(mdOriginalPass)) {
			return JsonResult.fail(JsonResult.dealResult("原始密码有误,请重新输入",
					JsonResult.RESULT_CODE_TYPE_ONE));
		}

		if (StringUtils.isEmpty(newPass)) {
			return JsonResult.fail(JsonResult.dealResult("请输入密码",
					JsonResult.RESULT_CODE_TYPE_TWO));
		} else if (StringUtils.isEmpty(againPass)) {
			return JsonResult.fail(JsonResult.dealResult("请输入密码",
					JsonResult.RESULT_CODE_TYPE_THREE));
		} else if (!newPass.equals(againPass)) {
			return JsonResult.fail(301, JsonResult.dealResult("两次输入不一致，请重新输入",
					JsonResult.RESULT_CODE_TYPE_THREE));
		} else if (!newPass.matches(REG_EX_PASS)) {
			return JsonResult.fail(JsonResult.dealResult(
					"请输入8~20位由字符/数字/符号(除空格)组成的新密码",
					JsonResult.RESULT_CODE_TYPE_TWO));
		} else if (!againPass.matches(REG_EX_PASS)) {
			return JsonResult.fail(JsonResult.dealResult(
					"请输入8~20位由字符/数字/符号(除空格)组成的新密码",
					JsonResult.RESULT_CODE_TYPE_THREE));
		}

		// 开始修改密码
		String mdPass = MD5HashUtil.toMD5(newPass);
		// System.out.println("未加密的密码：" + newPass + ",加密的密码：" + mdPass);
		ModifyDistributorUserCommand command = new ModifyDistributorUserCommand();
		command.setId(user.getId());
		command.setPassword(mdPass);
		if (StringUtils.isEmpty(distributorUserSPI.modify(command))) {
			return JsonResult.fail("修改密码失败");
		}

		httpSession.removeAttribute(SMS_TO_RESET_PAGE_SESSION_KEY_OP);
		httpSession.removeAttribute(SMS_MSG_SESSION_KEY_OP);
		resetUser(httpSession);// 重置session中的user
		return JsonResult.success("恭喜你密码修改成功");
	}

	/**
	 * 
	 * @方法功能说明：查询指定beginTime到endTime时间内的该mobile发送的短信条数
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午12:57:46
	 * @修改内容：
	 * @param mobile
	 * @return
	 * @throws ParseException
	 */
	private int queryTodaysSmsNum(String mobile) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date today = new Date();
		Calendar cnew = Calendar.getInstance();
		cnew.setTime(today);
		cnew.add(Calendar.DATE, 1);// 当前天数加一天

		String beginTime = sdf.format(today) + " 00:00:00";// 开始时间
		String endTime = sdf.format(cnew.getTime()) + " 00:00:00";// 结束时间

		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile(mobile);
		sqo.setType(SmsCodeRecord.TYPE_MODIFY_PHONE);
		sqo.setBeginTime(sdf1.parse(beginTime));
		sqo.setEndTime(sdf1.parse(endTime));
		sqo.setUsed(null);
		Integer sendNum = smsCodeRecordSPI.queryCount(sqo);
		return sendNum;
	}

	/**
	 * 
	 * @方法功能说明：修改手机号码功能
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月7日 下午12:03:20
	 * @修改内容：
	 * @param httpSession
	 * @param mobile
	 */
	private void modifyMobile(HttpSession httpSession, String mobile) {
		DistributorUser user = getSessionUserInfo(httpSession);
		if (!StringUtils.isEmpty(user)) {
			ModifyDistributorCommand cmd = new ModifyDistributorCommand();
			cmd.setId(user.getDistributor().getId());
			cmd.setPhone(mobile);
			distributorSPI.modifyDistributor(cmd);
			resetUser(httpSession);// 重置session中的user
		}
	}

	/**
	 * 
	 * @方法功能说明：添加发送短信记录
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午1:02:28
	 * @修改内容：
	 * @param code
	 * @param mobile
	 * @param invalid
	 */
	private SmsCodeRecord createSmsRecord(String code, String mobile,
			int invalid) {
		Calendar c = Calendar.getInstance();
		// 添加短信记录
		long createTime = System.currentTimeMillis();// 添加时间
		long invalidTime = createTime + invalid * 60 * 1000;// 失效时间

		CreateSmsCodeRecordCommand command = new CreateSmsCodeRecordCommand();
		command.setCode(code);
		command.setMobile(mobile);
		command.setType(SmsCodeRecord.TYPE_MODIFY_PHONE);
		c.setTimeInMillis(createTime);
		command.setCreateDate(c.getTime());
		c.setTimeInMillis(invalidTime);
		command.setInvalidDate(c.getTime());
		return smsCodeRecordSPI.create(command);
	}

	/**
	 * 
	 * @方法功能说明：发送短信
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午1:34:29
	 * @修改内容：
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	private String sendSms(String mobile) throws Exception {
		SmsCodeCommand command = new SmsCodeCommand();
		command.setMobile(mobile);
		command.setMsg(SMS_MSG);
		command.setSmsUser(smsUtil.getSmsUser());
		command.setSmsPassword(smsUtil.getSmsPassword());
		HGCaptchaDTO dto = hgCaptchaService.getCaptcha(
				HGCaptchaConstants.HG_CAPTCHA_TYPE_SMS_CODE, command);
		return dto.getText();
	}

	/**
	 * 
	 * @方法功能说明：帐号管理
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午8:43:17
	 * @修改内容：
	 * @return
	 */
	@RequestMapping(value = "accountManageOperate", method = RequestMethod.GET)
	public ModelAndView toAccountManageOperate(HttpSession httpSession) {
		ModelAndView model = new ModelAndView(
				"account/account-manage-operate.ftl");
		DistributorUser userSession = getUser(httpSession);

		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setDistributorId(userSession.getDistributor().getId());
		sqo.setQueryReserveInfo(true);
		sqo.setType(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN);// 主账号
		List<DistributorUser> userList = distributorUserSPI
				.queryPagination(sqo).getList();
		model.addObject("user", userList.get(0));
		model.addObject("userSess", getSessionUserInfo(httpSession));
		return model;
	}

	/**
	 * 
	 * @方法功能说明：doto
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月12日 下午3:16:50
	 * @修改内容：
	 * @param mobile
	 * @param code
	 * @return
	 */
	private SmsCodeRecord getCode(String mobile, String code) {
		// 查询该手机号的最近的一条没有被使用的code
		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile(mobile);
		sqo.setType(SmsCodeRecord.TYPE_MODIFY_PHONE);
		sqo.setUsed(false);
		sqo.setCode(code);
		return smsCodeRecordSPI.queryFirst(sqo);
	}

	private synchronized JsonResult sendSms(String mobile,
			HttpSession httpSession, long time) throws Exception {
		// 上一次发送成功的时间
		Long timeStr = (Long) httpSession
				.getAttribute(SMS_SEND_SESSION_NEAR_OP);
		if (!StringUtils.isEmpty(timeStr)) {
			System.out.println("上一次时间：" + timeStr);
			System.out.println("本次时间：" + time);
			System.out.println("时间差：" + (time - timeStr));
			if (time - timeStr <= 60 * 1000) {
				// 还没到1分钟
				return JsonResult.success();
			}
		}

		// 每日找回密码限制：从缓存中获取该手机号码今天发短信的次数,limitNum次之后不能再找回密码
		Integer limitNum = findPwdManager.getFindPwdTimes();
		limitNum = limitNum == null ? 0 : limitNum;
		int sendNum = queryTodaysSmsNum(mobile);
		if (sendNum >= limitNum.intValue()) {
			return JsonResult.fail("此手机发送验证码次数已达上限，请在24小时后重试");
		}

		String code = sendSms(mobile);
		// String code = "123456";
		// System.out.println("验证码为：" + code);
		if (StringUtils.isEmpty(code)) {
			return JsonResult.fail("发送失败");
		}

		httpSession.setAttribute(SMS_SEND_SESSION_NEAR_OP, time);
		return JsonResult.success(code);
	}

}
