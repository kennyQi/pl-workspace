package hgfx.web.controller.distributor;

import hg.captcha.common.HGCaptchaConstants;
import hg.captcha.common.command.SmsCodeCommand;
import hg.captcha.common.dto.HGCaptchaDTO;
import hg.captcha.sdk.HGCaptchaService;
import hg.demo.member.common.domain.model.system.JsonUtil;
import hg.fx.command.DistributorRegister.CreateDistributorRegisterCommand;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.DistributorRegisterSPI;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.qo.SmsCodeRecordSQO;
import hgfx.web.controller.sys.BaseController;
import hgfx.web.util.SmsUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class DistributorRegisterController extends BaseController {
	
	@Autowired
	private SmsCodeRecordSPI smsCodeRecordSPI;
	@Autowired
	private DistributorRegisterSPI distributorRegisterSPI;
	@Autowired
	private SmsUtil smsUtil;
	@Autowired
	private HGCaptchaService hgCaptchaService;
	
	// 密码规则，数字，字母，特殊标点符合(除空格)8`20位
	private static final String REG_EX_PASS = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z`~!@#$%^&*+-=|{}':;_,\"\\[\\].<>/?]{8,20}$";
	
	private static final String SMS_MSG = SmsUtil.SMS_PRE汇购 +"您注册里程兑换平台的验证码为#SMS_CODE#，请于30分钟内正确输入验证码";
	
	@RequestMapping("/page")
	public ModelAndView registerPage(){
		return new ModelAndView("distributor/register.html");
	}
	
	@ResponseBody
	@RequestMapping("/sendSMS")
	public String sendSMS(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "cellNumber",defaultValue = "")String cellNumber){
		Map<String, String> map = new HashMap<String, String>();
		try{
			if(StringUtils.isBlank(cellNumber)){
				map.put("status", "false");
				map.put("msg", "1");
				return JsonUtil.parseObject(map, false);
			}
			if(distributorRegisterSPI.checkExistPhone(cellNumber)){
				map.put("status", "false");
				map.put("msg", "2");
				return JsonUtil.parseObject(map, false);
			}
			SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
			sqo.setType(SmsCodeRecord.TYPE_REGISTER);
			sqo.setMobile(cellNumber);
			sqo.setUsed(false);
			List<SmsCodeRecord> smsCodeRecords = smsCodeRecordSPI.queryList(sqo);
			final String finalcellNumber =  cellNumber;
			if(smsCodeRecords==null||smsCodeRecords.size()==0){
				//System.out.println("【倒计时】"+"【发了一条】");
				final String id = createSmsRecord("", finalcellNumber, 30).getId();
				new Thread(){
					public void run() {
						//短信表里无记录 发短信
						try {
							String code = sendSms(finalcellNumber);
							smsCodeRecordSPI.updateCode(id, code);
						} catch (Exception e) {
							smsCodeRecordSPI.deleteSMSCodeRecord(id);
						}
					};
				}.start();
				map.put("status", "success");
			}else{
				SmsCodeRecord smsCodeRecord = smsCodeRecords.get(0);
				long now =new Date().getTime();
				//System.out.println("【倒计时】"+"【当前时间】"+now);
				//System.out.println("【倒计时】"+"【创建时间】"+smsCodeRecord.getCreateDate().getTime());
				//System.out.println("【倒计时】"+"【失效时间】"+smsCodeRecord.getInvalidDate().getTime());
				if(smsCodeRecord.getInvalidDate().getTime()<now){
					//System.out.println("【倒计时】"+1);
					//短信表里有记录 但是已经过期 改状态 然后发信息
					ModifySmsCodeRecordCommand smsCommand =  new ModifySmsCodeRecordCommand();
					smsCommand.setId(smsCodeRecord.getId());
					smsCodeRecordSPI.modifyUsedStatus(smsCommand);
					final String id = createSmsRecord("", finalcellNumber, 30).getId();
					new Thread(){
						public void run() {
							//短信表里无记录 发短信
							try {
								String code = sendSms(finalcellNumber);
								smsCodeRecordSPI.updateCode(id, code);
							} catch (Exception e) {
								smsCodeRecordSPI.deleteSMSCodeRecord(id);
							}
						};
					}.start();
					map.put("status", "success");
				}else if(now-smsCodeRecord.getCreateDate().getTime()<60000){
					//System.out.println("【倒计时】"+2);
					//短信表里有记录 一分钟以内 计算时间返回前台
					map.put("status", "false");
					map.put("msg", "3");
					long sec = 60-((now-smsCodeRecord.getCreateDate().getTime())/1000);
					map.put("sec",String.valueOf(sec));
					return JsonUtil.parseObject(map, false);
				}else{
					//System.out.println("【倒计时】"+3);
					//短信表里有记录 一分钟过后 重新获取
					ModifySmsCodeRecordCommand smsCommand =  new ModifySmsCodeRecordCommand();
					smsCommand.setId(smsCodeRecord.getId());
					smsCodeRecordSPI.modifyUsedStatus(smsCommand);
					final String id = createSmsRecord("", finalcellNumber, 30).getId();
					new Thread(){
						public void run() {
							//短信表里无记录 发短信
							try {
								String code = sendSms(finalcellNumber);
								smsCodeRecordSPI.updateCode(id, code);
							} catch (Exception e) {
								smsCodeRecordSPI.deleteSMSCodeRecord(id);
							}
						};
					}.start();
					map.put("status", "success");
				}
			}
		}catch(Exception e){
			map.put("status", "false");
			map.put("msg", "发送失败");
		}
		return JsonUtil.parseObject(map, false);
	}
	
	
	@ResponseBody
	@RequestMapping("/reg")
	public String reg(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "login",defaultValue = "")String login,
			@RequestParam(value = "password",defaultValue = "")String password,
			@RequestParam(value = "realName",defaultValue = "")String realName,
			@RequestParam(value = "corpName",defaultValue = "")String corpName,
			@RequestParam(value = "url",defaultValue = "")String url,
			@RequestParam(value = "cellNumber",defaultValue = "")String cellNumber,
			@RequestParam(value = "validateCode",defaultValue = "")String validateCode,
			@RequestParam(value = "theSelect",defaultValue = "")String theSelect){
		Map<String, String> map = new HashMap<String, String>();
		try{
			if(StringUtils.isBlank(login)||StringUtils.isBlank(password)||StringUtils.isBlank(realName)||StringUtils.isBlank(corpName)||StringUtils.isBlank(url)||
					StringUtils.isBlank(cellNumber)||StringUtils.isBlank(validateCode)||StringUtils.equals(theSelect, "false")){
				//参数为空
				map.put("status", "false");
				map.put("msg", "0");
				if(StringUtils.equals(theSelect, "false")){
					map.put("msg", "4");
				}
				return JsonUtil.parseObject(map, false);
			}
			if(distributorRegisterSPI.checkExistAccount(login)){
				//账号重复
				map.put("status", "false");
				map.put("msg", "1");
				return JsonUtil.parseObject(map, false);
			}
			if(!password.matches(REG_EX_PASS)){
				map.put("status", "false");
				map.put("msg", "7");
				return JsonUtil.parseObject(map, false);
			}
			if(distributorRegisterSPI.checkExistPhone(cellNumber)){
				//联系电话已存在
				map.put("status", "false");
				map.put("msg", "2");
				return JsonUtil.parseObject(map, false);
			}
			SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
			sqo.setType(SmsCodeRecord.TYPE_REGISTER);
			sqo.setMobile(cellNumber);
			sqo.setUsed(false);
			List<SmsCodeRecord> smsCodeRecords = smsCodeRecordSPI.queryList(sqo);
			if(smsCodeRecords==null||smsCodeRecords.size()==0){
				//未获取短信验证码
				map.put("status", "false");
				map.put("msg", "5");
				return JsonUtil.parseObject(map, false);
			}
			SmsCodeRecord smsCodeRecord = smsCodeRecords.get(0);
			if(smsCodeRecord.getInvalidDate().getTime()<new Date().getTime()){
				//验证码失效
				ModifySmsCodeRecordCommand smsCommand =  new ModifySmsCodeRecordCommand();
				smsCommand.setId(smsCodeRecord.getId());
				smsCodeRecordSPI.modifyUsedStatus(smsCommand);
				map.put("status", "false");
				map.put("msg", "6");
				return JsonUtil.parseObject(map, false);
			}
			if(StringUtils.equals(smsCodeRecord.getCode(), validateCode)){
				//验证成功
				CreateDistributorRegisterCommand command = new CreateDistributorRegisterCommand();
				command.setLoginName(login);
				command.setPasswd(password);
				command.setLinkMan(realName);
				command.setCompanyName(corpName);
				command.setWebSite(url);
				command.setPhone(cellNumber);
				command.setCreateDate(new Date());
				distributorRegisterSPI.createDistributorRegister(command);
				ModifySmsCodeRecordCommand smsCommand =  new ModifySmsCodeRecordCommand();
				smsCommand.setId(smsCodeRecord.getId());
				smsCodeRecordSPI.modifyUsedStatus(smsCommand);
				map.put("status", "success");
			}else{
				//验证码输入错误
				map.put("status", "false");
				map.put("msg", "3");
				return JsonUtil.parseObject(map, false);
			}
		}catch(Exception e){
			map.put("status", "false");
			map.put("msg", "系统繁忙");
		}
		return JsonUtil.parseObject(map, false);
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
		command.setType(SmsCodeRecord.TYPE_REGISTER);
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
}
