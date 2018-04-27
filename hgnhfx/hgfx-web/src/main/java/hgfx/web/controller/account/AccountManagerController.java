package hgfx.web.controller.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import hg.captcha.common.HGCaptchaConstants;
import hg.captcha.common.command.SmsCodeCommand;
import hg.captcha.common.dto.HGCaptchaDTO;
import hg.captcha.sdk.HGCaptchaService;
import hg.demo.member.common.MD5HashUtil;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.qo.DistributorSQO;
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
 * @类功能说明：忘记密码controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月5日 上午11:45:03
 * @版本： V1.0
 */
@Controller
@RequestMapping(value = "accountManager")
public class AccountManagerController extends BaseController {

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
			+ "尊敬的汇购用户，您正在进行密码重置操作，验证码为#SMS_CODE#，请于30分钟内正确输入验证码";
	private static final String SMS_MSG_SESSION_KEY = "_SMS_MSG_SESSION_KEY_";// 验证码记录放到session中key
	private static final String SMS_TO_RESET_PAGE_SESSION_KEY = "_SMS_TO_RESET_PAGE_SESSION_KEY_";// 随机生成的临时token
	// 密码规则，数字，字母，特殊标点符合(除空格)8`20位
	private static final String REG_EX_PASS = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z`~!@#$%^&*+-=|{}':;_,\"\\[\\].<>/?]{8,20}$";
	private static final String MOB_SESSION_NEAR = "_MOB_SESSION_NEAR_";
	private static final String SMS_SEND_SESSION_NEAR = "_SMS_SEND_SESSION_NEAR";

	/**
	 * 
	 * @方法功能说明：重置密码-(准备)发送短信验证码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:57:16
	 * @修改内容：
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "prepareSendSms", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult prepareSendSms(String mobile, HttpSession httpSession) {
		if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail("请输入手机号码");
		} else if (!isMobileNO(mobile)) {
			return JsonResult.fail("输入正确手机号");
		}

		try {

			// 在重置密码功能里面猜校验不存在该手机号相关的账号
			if (!distributorSPI.checkExistPhone(mobile)) {
				return JsonResult.fail("不存在该手机号相关的账号");
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

	/**
	 * 
	 * @方法功能说明：重置密码-发送短信验证码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月14日 下午6:16:49
	 * @修改内容：
	 * @param mobile
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "sendSms", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendSms(String mobile, HttpSession httpSession) {
		long time = System.currentTimeMillis();
		if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail("请输入手机号码");
		} else if (!isMobileNO(mobile)) {
			return JsonResult.fail("输入正确手机号");
		}

		try {

			// 在重置密码功能里面猜校验不存在该手机号相关的账号
			if (!distributorSPI.checkExistPhone(mobile)) {
				return JsonResult.fail("不存在该手机号相关的账号");
			}

			JsonResult result = sendSms(mobile, httpSession, time);
			if (result.getCode() == JsonResult.CODE_SUCCESS) {
				if (!StringUtils.isEmpty(result.getMsg())
						&& !result.getMsg().equals(JsonResult.MSG_SUCCESS)) {
					// 把手机号码加入到session
					httpSession.setAttribute(MOB_SESSION_NEAR, mobile);

					// 添加短信记录
					SmsCodeRecord scr = createSmsRecord(result.getMsg()
							.toString(), mobile, 30);
					// 把添加短信记录存到session中
					httpSession.setAttribute(SMS_MSG_SESSION_KEY,
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
	 * @方法功能说明：重置密码-校验短信验证码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 下午2:51:03
	 * @修改内容：
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkCode(String mobile, String code,
			HttpSession httpSession) {
		if (StringUtils.isEmpty(code)) {
			return JsonResult.fail("短信验证码错误请重新输入");
		} else if (StringUtils.isEmpty(mobile)) {
			return JsonResult.fail("请输入手机号码");
		}

		SmsCodeRecord scr = getCode(mobile, code);
		if (StringUtils.isEmpty(scr) || StringUtils.isEmpty(scr.getCode())) {
			return JsonResult.fail("短信验证码错误请重新输入");
		} else if (scr.getInvalidDate().getTime() < new Date().getTime()) {
			return JsonResult.fail("该验证码已过期");
		}

		// 改变验证码状态使用
		ModifySmsCodeRecordCommand command = new ModifySmsCodeRecordCommand();
		command.setId(scr.getId());
		smsCodeRecordSPI.modifyUsedStatus(command);

		String num = UUID.randomUUID().toString().replaceAll("-", "");
		httpSession.setAttribute(SMS_TO_RESET_PAGE_SESSION_KEY, num);
		return JsonResult.success(num);
	}

	/**
	 * 
	 * @方法功能说明：忘记密码跳转页
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:10:36
	 * @修改内容：
	 * @return
	 */
	@RequestMapping(value = "forgetPass", method = RequestMethod.GET)
	public ModelAndView toForgetPass() {
		return new ModelAndView("account/password_forget.ftl");
	}

	/**
	 * 
	 * @方法功能说明：重置密码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:12:40
	 * @修改内容：
	 * @return
	 */
	@RequestMapping(value = "resetPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult resetPass(String newPass, String againPass,
			HttpSession httpSession) {

		String codeJson = (String) httpSession
				.getAttribute(SMS_MSG_SESSION_KEY);
		if (StringUtils.isEmpty(codeJson)) {
			return JsonResult.fail("请先获取验证码");
		}

		SmsCodeRecord scr = JSON.parseObject(codeJson, SmsCodeRecord.class);
		String mobile = scr.getMobile();
		// 根据手机号码查询商户
		DistributorSQO sqo = new DistributorSQO();
		sqo.setPhone(mobile);
		List<Distributor> list = distributorSPI.queryList(sqo);
		// 根据商户id查询主账号
		DistributorUserSQO usqo = new DistributorUserSQO();
		usqo.setDistributorId(list.get(0).getId());
		usqo.setQueryReserveInfo(true);
		usqo.setType(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN);// 主账号
		List<DistributorUser> userList = distributorUserSPI.queryPagination(
				usqo).getList();
		DistributorUser user = userList.get(0);
		// 判断是否存在该号码的用户才能重置
		if (StringUtils.isEmpty(user)) {
			// 获取不到用户说明，没有经过获取短信验证码这一步，或者系统重启导致session失效
			return JsonResult.fail("请先获取验证码");
		}

		if (StringUtils.isEmpty(newPass) || StringUtils.isEmpty(againPass)) {
			return JsonResult.fail("请输入密码");
		} else if (!newPass.equals(againPass)) {
			return JsonResult.fail(301, "两次输入不一致，请重新输入");
		} else if (!newPass.matches(REG_EX_PASS)) {
			return JsonResult.fail("请填写由8~20位由字母/数字/符号(除空格)组成的新密码");
		}

		// 开始修改密码
		String mdPass = MD5HashUtil.toMD5(newPass);
		// System.out.println("未加密的密码：" + newPass + ",加密的密码：" + mdPass);
		ModifyDistributorUserCommand command = new ModifyDistributorUserCommand();
		command.setId(user.getId());
		command.setPassword(mdPass);
		if (StringUtils.isEmpty(distributorUserSPI.modify(command))) {
			return JsonResult.fail("重置密码失败");
		}

		httpSession.removeAttribute(SMS_TO_RESET_PAGE_SESSION_KEY);
		httpSession.removeAttribute(SMS_MSG_SESSION_KEY);
		return JsonResult.success("恭喜你密码修改成功");
	}

	/**
	 * 
	 * @方法功能说明：重置密码跳转页
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:12:40
	 * @修改内容：
	 * @return
	 */
	@RequestMapping(value = "resetPass", method = RequestMethod.GET)
	public ModelAndView toResetPass(String num, HttpSession httpSession) {
		String numSession = (String) httpSession
				.getAttribute(SMS_TO_RESET_PAGE_SESSION_KEY);
		if (StringUtils.isEmpty(numSession) || StringUtils.isEmpty(num)
				|| !numSession.equals(num)) {
			return null;
		}
		return new ModelAndView("account/password_reset.ftl");
	}

	/**
	 * 
	 * @方法功能说明：重置密码成功
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 下午1:13:27
	 * @修改内容：
	 * @return
	 */
	@RequestMapping(value = "resetPassSucc", method = RequestMethod.GET)
	public ModelAndView toResetPassSucc() {
		return new ModelAndView("account/password_reset-success.ftl");
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
		sqo.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);
		sqo.setBeginTime(sdf1.parse(beginTime));
		sqo.setEndTime(sdf1.parse(endTime));
		sqo.setUsed(null);
		Integer sendNum = smsCodeRecordSPI.queryCount(sqo);
		return sendNum;
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
		command.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);
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
	 * @方法功能说明：doto
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月12日 下午2:54:02
	 * @修改内容：
	 * @param mobile
	 * @param code
	 * @return
	 */
	private SmsCodeRecord getCode(String mobile, String code) {
		// 查询该手机号的最近的一条没有被使用的code
		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile(mobile);
		sqo.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);
		sqo.setUsed(false);
		sqo.setCode(code);
		return smsCodeRecordSPI.queryFirst(sqo);
	}

	/**
	 * 
	 * @方法功能说明：返回密码是否包含了数字字母，当返回值=2时是包含2种
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月15日 下午4:05:13
	 * @修改内容：
	 * @param password
	 * @return
	 */
	public static int passwordLevel(String password) {
		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < password.length(); i++) {
			String charType = CharMode(password.charAt(i));
			if (!charType.equals("8")) {
				set.add(charType);
			}
		}
		return set.size();
	}

	/**
	 * 
	 * @方法功能说明：判断字符的ascll码
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月15日 下午4:08:22
	 * @修改内容：
	 * @param iN
	 * @return
	 */
	public static String CharMode(int iN) {
		if (iN >= 48 && iN <= 57)// 数字
			return "1";
		if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
			// 大小写
			return "4";
		else
			return "8"; // 特殊字符
	}

	private synchronized JsonResult sendSms(String mobile,
			HttpSession httpSession, long time) throws Exception {
		// 上一次发送成功的时间
		Long timeStr = (Long) httpSession.getAttribute(SMS_SEND_SESSION_NEAR);
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

		httpSession.setAttribute(SMS_SEND_SESSION_NEAR, time);
		return JsonResult.success(code);
	}

	public static void main(String[] args) {
		// System.out.println("wgD123$'\"$-<>".matches(REG_EX_PASS));
		// System.out.println(passwordLevel("Zz1 ^&%*&@是"));
	}

}
