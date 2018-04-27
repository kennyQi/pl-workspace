package hsl.app.service.local.user;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.clickrecord.ClickRateDao;
import hg.log.clickrecord.ClickRateQo;
import hg.log.clickrecord.ClickRecordDao;
import hg.log.clickrecord.ClickRecordQo;
import hg.log.po.clickrecord.ClickRate;
import hg.log.po.clickrecord.ClickRecord;
import hg.log.repository.ClickRateRepository;
import hg.log.repository.ClickRecordRepository;
import hg.log.util.HgLogger;
import hg.system.common.util.MailUtil;
import hsl.app.common.util.MobileNo;
import hsl.app.dao.MailCodeDao;
import hsl.app.dao.SMSCodeDao;
import hsl.app.dao.UserBindAccountDao;
import hsl.app.dao.UserDao;
import hsl.domain.model.event.MailValidateRecord;
import hsl.domain.model.event.SMSValidateRecord;
import hsl.domain.model.user.User;
import hsl.domain.model.user.UserBaseInfo;
import hsl.domain.model.user.UserBindAccount;
import hsl.domain.model.user.UserStatus;
import hsl.pojo.command.BindWXCommand;
import hsl.pojo.command.CheckRegisterMailCommand;
import hsl.pojo.command.CheckValidCodeCommand;
import hsl.pojo.command.SendMailCodeCommand;
import hsl.pojo.command.SendValidCodeCommand;
import hsl.pojo.command.UpdateBindMobileCommand;
import hsl.pojo.command.UpdateCompanyUserCommand;
import hsl.pojo.command.UpdateHeadImageCommand;
import hsl.pojo.command.UserClickRecordCommand;
import hsl.pojo.command.UserRegisterCommand;
import hsl.pojo.command.UserUpdatePasswordCommand;
import hsl.pojo.command.user.BatchRegisterUserCommand;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.mp.HslUserClickRecordQO;
import hsl.pojo.qo.user.HslSMSCodeQO;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
@Service
@Transactional
public class UserLocalService extends BaseServiceImpl<User, HslUserQO, UserDao>{

	@Autowired
	private UserDao userDao;
	@Autowired
	private SMSCodeDao smsCodeDao;
	@Autowired
	private MailCodeDao mailCodeDao;
	@Autowired
	private UserBindAccountDao userBindAccountDao;
	@Autowired
	private ClickRecordRepository clickRecordRepository;
	@Autowired
	private ClickRecordDao clickRecordDao;
	@Autowired
	private ClickRateDao clickRateDao;
	@Autowired
	private ClickRateRepository clickRateRepository;
	
	@Resource
	private SMSUtils smsUtils;
	@Autowired
	private HgLogger hgLogger;
	@Override
	protected UserDao getDao() {
		return userDao;
	}
	
	/**
	 * 注册
	 * @throws Exception 
	 */

	public User register(UserRegisterCommand command,String clientKey) throws UserException{
		hgLogger.info("chenxy",command.getLoginName()+"用户注册查询手机验证码");
		
		//校验用户手机号是否已被使用
		HslUserQO userQo=new HslUserQO();
		userQo.setMobile(command.getMobile());
		List<User> users=this.userDao.queryList(userQo);
		if(users!=null&&users.size()>0){
			hgLogger.error("renfeng",command.getMobile()+"该手机号已被使用");
			throw new UserException(UserException.RESULT_MOBILE_BIND,"该手机号已被使用");
		}
		//网页个人用户注册的时候不需要验证手机
		if( clientKey.equals("wap") && clientKey.equals("weixin")){
		}else{
			//检验验证码信息
			SMSValidateRecord record=smsCodeDao.get(command.getValidToken());
			if(record==null){
				hgLogger.error("chenxy",command.getLoginName()+"用户注册手机验证码无效");
				throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码无效,请重新获取");
			}
			if(!record.getMobile().equals(command.getMobile())){
				hgLogger.error("chenxy",command.getLoginName()+"手机号码无效请重新获取");
				throw new UserException(UserException.RESULT_MOBILE_WRONG,"手机号码无效,请重新获取");
			}
			hgLogger.info("chenxy",command.getLoginName()+"用户注册查询手机验证码");
			record.checkSMSCode(command.getValidCode());
			smsCodeDao.update(record);
		}
		//查询邮箱是否已经被绑定
		HslUserQO qo = new HslUserQO();
		User user = null;
		if(command.getEmail() != null){
			qo.setEmail(command.getEmail());
			user= userDao.queryUnique(qo);
			if(user!=null){
				throw new UserException(UserException.RESULT_EMAIL_BIND, "邮箱已经绑定");
			}
		}
		//查询用户名是否重复
		HslUserQO hslUserQO=new HslUserQO();
		hslUserQO.setLoginName(command.getLoginName());	
		hslUserQO.setLoginNameLike(false);
		hgLogger.info("chenxy",command.getLoginName()+"用户注册判断用户名是否存在");
		user=userDao.queryUnique(hslUserQO);
		
		if(user==null){
			//用户不存在，保存新用户信息
			user=new User();
			user.register(command,clientKey);
			userDao.save(user);
			//注册成功发送邮件
			if(null!=command.getEmail()&&!command.getEmail().equals("")){
				MailValidateRecord record = new MailValidateRecord();
				SendMailCodeCommand sendMailCodeCommand=new SendMailCodeCommand();
				sendMailCodeCommand.setMail(command.getEmail());
				sendMailCodeCommand.setScene(SendMailCodeCommand.SENDMAIL_SCENE_REGISTER);
				record.createRegisterMailRecord(sendMailCodeCommand);
				String mailHost=SysProperties.getInstance().get("mailHost");
				String mailAccount=SysProperties.getInstance().get("mailAccount");
				String mailPwd=SysProperties.getInstance().get("mailPwd");
				List<String> accepts=new ArrayList<String>();
				accepts.add(command.getEmail());
				String title="票量-请激活您的票量账号";
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", user.getAuthInfo().getLoginName());
				map.put("url", getWebAppPath()+"/user/checkRegisterMail?token="+record.getId()+"&identify="+record.getValue());
				map.put("time",DateUtil.formatDateTime(new Date()));
				try {
					MailUtil.getInstance().sendTemplateMail1(mailHost, mailAccount, mailPwd, title,SysProperties.getInstance().get("registerTemplateId"), map, mailAccount, accepts);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mailCodeDao.save(record);
			}
			hgLogger.info("chenxy",command.getLoginName()+"用户注册成功");
			return user;
		}
		hgLogger.error("chenxy",command.getLoginName()+"用户注册>>账号已存在");
		throw new UserException(UserException.RESULT_LOGINNAME_REPEAT, "账号已存在");
	}
	/**
	 * 获取webapp路径
	 * @return
	 */
	private String getWebAppPath() {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String contextPath=ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
        String port = SysProperties.getInstance().get("port");
        String system = "http://" +SysProperties.getInstance().get("host") + ("80".equals(port) ? "" : ":" + port);
        if (!isRoot) {
        	system += contextPath;
        }
        return system;
	}
	/**
	 * 发送验证码
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public String sendValidCode(SendValidCodeCommand command) throws UserException{
		 //判断手机号的正确性
		if(MobileNo.isMobileNO(command.getMobile())){
		
			User user =new User();
			// 判断手机号是否已绑定
			HslUserQO hslUserQO = new HslUserQO();
			if(StringUtils.isNotBlank(command.getUserId())){
				user=userDao.get(command.getUserId());
			}else{
				hslUserQO.setMobile(command.getMobile());
				user= userDao.queryUnique(hslUserQO);
			}

			/**
			 * 查询当前手机号当天 发送且未验证的验证码几条
			 * 如果查过十条，则直接提示到达发送上限
			 */
			if(StringUtils.isNotBlank(command.getMobile())){
				HslSMSCodeQO smsCodeQO=new HslSMSCodeQO();
				smsCodeQO.setMobile(command.getMobile());
				smsCodeQO.setStatus(1);
				smsCodeQO.setSendDate(DateUtil.formatDate(new Date()));
				List<SMSValidateRecord> smsValidateRecords=smsCodeDao.queryList(smsCodeQO);
				if(smsValidateRecords!=null&&smsValidateRecords.size()>=10){
					throw new UserException(UserException.RESULT_MOBILE_MAX,"已达到该天该手机号发送上限");
				}
			}
			
			// 判断业务场景
			if (command.getScene() == 1 || command.getScene()==4) {
				hgLogger.info("chenxy", "短信验证码发送进入注册场景或者绑定新手机"+command.getMobile());
				//注册场景或者绑定新手机
				if (user == null) {
					SMSValidateRecord record = new SMSValidateRecord();
					record.createSMSValidateRecord(command);
					try {
							String smsAddress=SysProperties.getInstance().get("sms_sign");
							smsUtils.sendSms(record.getMobile(), "【"+smsAddress+"】您正在使用本手机号码绑定票量账号，您获取到的验证密码为"+record.getValue()+"。本短信密码作为验证凭证，10分钟后失效。客服电话：010—65912283。");
					} catch (Exception e) {
						e.printStackTrace();
						HgLogger.getInstance().error("chenxy", "UserLocalService->sendValidCode->手机号为:"+command.getMobile()+"exception："+ HgLogger.getStackTrace(e));
					}
					smsCodeDao.save(record);
					//返回token
					return record.getId();
				} else {
					hgLogger.error("chenxy", "短信验证码发送失败>>用户手机号已绑定"+command.getMobile());
					throw new UserException(UserException.RESULT_MOBILE_BIND, "该手机号用户已绑定");
				}
			}
			
			if(command.getScene() == 2 || command.getScene()==3){
				hgLogger.info("chenxy", "短信验证码发送进入修改密码场景或解绑手机场景");
				//修改密码场景或解绑手机场景
				if(user!=null && command.getMobile().equals(user.getContactInfo().getMobile())){
					SMSValidateRecord record = new SMSValidateRecord();
					record.createSMSValidateRecord(command);
					try {
						if(command.getScene()==2){
							smsUtils.sendSms(record.getMobile(), "【"+SysProperties.getInstance().get("sms_sign")+"】您正在使用本手机号码修改密码，您获取到的验证密码为"+record.getValue()+"。本短信密码作为验证凭证，10分钟后失效。客服电话：010—65912283。");
						}else{
							smsUtils.sendSms(record.getMobile(), "【"+SysProperties.getInstance().get("sms_sign")+"】您正在使用本手机号码解绑票量账号，您获取到的验证密码为"+record.getValue()+"。本短信密码作为验证凭证，10分钟后失效。客服电话：010—65912283。");
						}
					} catch (Exception e) {
						HgLogger.getInstance().error("zhangka", "UserLocalService->sendValidCode->exception:" + HgLogger.getStackTrace(e));
					}
					smsCodeDao.save(record);
					//返回token
					return record.getId();
				}else {
					hgLogger.error("chenxy", "短信验证码发送失败>>非绑定用户手机号");
					throw new UserException(UserException.RESULT_MOBILE_UNBIND,"非绑定用户手机号");
				}
			}
			
			//发放充值码
			if(command.getScene()==5){
				SMSValidateRecord record = new SMSValidateRecord();
				record.createSMSValidateRecord(command);
				try {
						smsUtils.sendSms(record.getMobile(), "【"+SysProperties.getInstance().get("sms_sign")+"】您正在使用本手机号码发放充值码，您获取到的验证密码为"+record.getValue()+"。本短信密码作为验证凭证，10分钟后失效。客服电话：010—65912283。");
					
				} catch (Exception e) {
					HgLogger.getInstance().error("renfeng", "UserLocalService->sendValidCode->exception:" + HgLogger.getStackTrace(e));
				}
				smsCodeDao.save(record);
				//返回token
				return record.getId();
			}
			//查看充值码场景
			if(command.getScene()==6){
				SMSValidateRecord record = new SMSValidateRecord();
				record.createSMSValidateRecord(command);
				try {
						smsUtils.sendSms(record.getMobile(), "【"+SysProperties.getInstance().get("sms_sign")+"】您正在使用本手机号码查看充值码，您获取到的验证密码为"+record.getValue()+"。本短信密码作为验证凭证，10分钟后失效。客服电话：010—65912283。");
					
				} catch (Exception e) {
					HgLogger.getInstance().error("renfeng", "UserLocalService->sendValidCode->exception:" + HgLogger.getStackTrace(e));
				}
				smsCodeDao.save(record);
				//返回token
				return record.getId();
			}
			
		}else{
			hgLogger.error("chenxy", "短信验证码发送失败>>手机号码错误");
			throw new UserException(UserException.RESULT_MOBILE_WRONG,"手机号码错误");
		}
		
		return null;
	}
	
	/**
	 * 发送邮箱验证码
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public String sendMailValidCode(SendMailCodeCommand  command) throws UserException{
		 //判断邮箱的正确性
		if(isEmail(command.getMail())){
			User user =new User();
			// 判断邮箱是否已绑定
			HslUserQO hslUserQO = new HslUserQO();
			if(StringUtils.isNotBlank(command.getUserId())){
				user=userDao.get(command.getUserId());
			}else{
				hslUserQO.setEmail(command.getMail());
				user= userDao.queryUnique(hslUserQO);
			}
			// 判断业务场景
			if (command.getScene() ==SendMailCodeCommand.SENDMAIL_SCENE_RESETPWD) {
					//找回密码场景
				if(null!=user){
					hgLogger.info("chenxy","邮箱验证码发送进入找回密码" + command.getMail());
					MailValidateRecord record = new MailValidateRecord();
					record.createMailValidateRecord(command);
					try {
						String mailHost=SysProperties.getInstance().get("mailHost");
						String mailAccount=SysProperties.getInstance().get("mailAccount");
						String mailPwd=SysProperties.getInstance().get("mailPwd");
						List<String> accepts=new ArrayList<String>();
						accepts.add(command.getMail());
						String title="票量-请接受您的票量验证码";
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("name", user.getAuthInfo().getLoginName());
						map.put("code",record.getValue());
						map.put("time",DateUtil.formatDateTime(new Date()));
						HgLogger.getInstance().info("chenxy", "发送邮箱验证码使用模版的ID："+SysProperties.getInstance().get("resetTemplateId"));
						MailUtil.getInstance().sendTemplateMail1(mailHost, mailAccount, mailPwd, title,SysProperties.getInstance().get("resetTemplateId"), map, mailAccount, accepts);
						hgLogger.info("chenxy", "找回密码发送邮件验证码>>邮件发送成功");
					} catch (Exception e) {
						HgLogger.getInstance().error("chenxy","UserLocalService->sendMailValidCode->邮箱为:"+ command.getMail() + "exception："+ HgLogger.getStackTrace(e));
					}
					mailCodeDao.save(record);
					// 返回token
					return record.getId();
				}else{
					hgLogger.error("chenxy", "邮件验证码发送失败>>邮箱号码未绑定任何用户");
					throw new UserException(UserException.RESULT_MOBILE_WRONG,"邮箱号码未绑定任何用户");
				}
			}
		}else{
			hgLogger.error("chenxy", "短信验证码发送失败>>手机号码错误");
			throw new UserException(UserException.RESULT_MOBILE_WRONG,"手机号码错误");
		}
		return null;
	}
	
	/**
	 * 校验用户名密码
	 * @return
	 * @throws UserException
	 */
	public User userCheck(HslUserQO userQO) throws  UserException{
		
		//查询用户是否存在
		HgLogger.getInstance().info("yuqz", "UserLocalService->userCheck查询用户:" + JSON.toJSONString(userQO));
		userQO.setLoginNameLike(false);
		User user=userDao.queryUnique(userQO);
		//如果按照用户名没有查询到用户，则再用手机号查找用户
		if(user==null){
			HgLogger.getInstance().info("yuqz", "UserLocalService->userCheck按照用户名没有查询到用户，再用手机号查找用户:mobile:" + userQO.getMobile());
			userQO.setMobile(userQO.getLoginName());
			userQO.setLoginName(null);
			user=userDao.queryUnique(userQO);
		}
		
		HgLogger.getInstance().info("yuqz", "UserLocalService->userCheck查询用户完成，结果:" + JSON.toJSONString(user));
		if(user!=null){
			if(userQO.getPassword().equals(user.getAuthInfo().getPassword())){
				//用户登录记录登陆时间
				user.login();
				userDao.update(user);
				return user;
			}
			throw new UserException(UserException.RESULT_AUTH_FAIL, "用户名或密码错误");
		}

		throw new UserException(UserException.RESULT_LOGINNAME_NOTFOUND, "账号不存在");
	}
	
	/**
	 * 绑定微信
	 * @param command
	 * @return
	 * @throws UserException
	 */
	public UserBindAccount bindWX(BindWXCommand command) throws UserException{
		//查询用户是否存在
		HgLogger.getInstance().info("yuqz", "进入绑定微信localService->bindWX:" + command);
		User user=userDao.get(command.getUserId());
		//用户不存在
		if(user==null){
			HgLogger.getInstance().error("yuqz", "票量帐号不存在");
			throw new UserException(UserException.RESULT_HGLOGINNAME_NOTFOUND, "票量帐号不存在");
		}
		//密码错误
		else if(!command.getPassword().equals(user.getAuthInfo().getPassword())){
			HgLogger.getInstance().error("yuqz", "密码错误");
			throw new UserException(UserException.RESULT_PASSWORD_WRONG, "密码错误");
		}
		
		HslUserQO hslUserQO=new HslUserQO();
		hslUserQO.setId(command.getUserId());
		hslUserQO.setLoginName(user.getAuthInfo().getLoginName());
		hslUserQO.setLoginNameLike(false);
		HgLogger.getInstance().info("yuqz", "查询用户是否已绑定" + JSON.toJSONString(hslUserQO));
		//查询用户是否已绑定
		HslUserBindAccountQO hslUserBindAccountQO=new HslUserBindAccountQO();
		hslUserBindAccountQO.setUserQO(hslUserQO);
		HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX查询用户是否已绑定：" + JSON.toJSONString(hslUserBindAccountQO));
		UserBindAccount userBindAccount=userBindAccountDao.queryUnique(hslUserBindAccountQO);
		HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX查询用户是否已绑定完成,结果：" + JSON.toJSONString(userBindAccount));
		//汇购帐号已绑定其它微信号
		if(userBindAccount!=null){
			HgLogger.getInstance().error("yuqz", "票量帐号已绑定其它微信号");
			throw new UserException(UserException.RESULT_HGLOGINNAME_BINDING_REPEAT, "票量帐号已绑定其它微信号");
		}
		
		//查询微信账户是否已绑定
		hslUserBindAccountQO.setUserQO(null);
		hslUserBindAccountQO.setBindAccountId(command.getWxAccountId());
		HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX查询微信账户是否已绑定" + JSON.toJSONString(hslUserBindAccountQO));
		userBindAccount=userBindAccountDao.queryUnique(hslUserBindAccountQO);
		HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX查询微信账户是否已绑定完成,结果" + JSON.toJSONString(userBindAccount));
		//微信号已被绑定
		if (userBindAccount!=null) {
			HgLogger.getInstance().error("yuqz", "微信号已被绑定");
			throw new UserException(UserException.RESULT_BINDING_REPEAT, "微信号已被绑定");
		}
		
		//用户未绑定
		HgLogger.getInstance().info("yuqz", "用户未绑定");
		hslUserBindAccountQO.setUserQO(hslUserQO);
		HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX用户未绑定" + JSON.toJSONString(hslUserBindAccountQO));
		userBindAccount=userBindAccountDao.queryUnique(hslUserBindAccountQO);
		if(userBindAccount==null){
			//绑定用户
			userBindAccount=new UserBindAccount();
			HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX绑定用户command:" + JSON.toJSONString(command));
			HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX绑定用户user:" + JSON.toJSONString(user));
			userBindAccount.bindWX(command, user);
			HgLogger.getInstance().info("yuqz", "UserLocalService->bindWX绑定用户userBindAccount:" + JSON.toJSONString(userBindAccount));
			userBindAccountDao.save(userBindAccount);
			return userBindAccount;
		}
		return null;
	}
	
	public void checkValidCode(CheckValidCodeCommand command) throws UserException{
		// 校验验证码
		SMSValidateRecord record = smsCodeDao.get(command.getValidToken());
		if (record == null) {
			hgLogger.error("yuqz", "验证码验证失败>>验证码无效,请重新获取");
			throw new UserException(UserException.RESULT_VALICODE_INVALID,
					"验证码无效,请重新获取");
		}
		HgLogger.getInstance().info("yuqz", "检查手机短信验证码");
		record.checkSMSCode(command.getValidCode());
		smsCodeDao.update(record);
	}
	/**
	 * 更新密码
	 * @param command
	 * @return
	 * @throws UserException
	 */
	public User UserUpdatePassword(UserUpdatePasswordCommand command)	throws UserException {
		
		HslUserQO userQO = new HslUserQO();
		User user = new User();
		//command没有旧密码代表忘记密码操作
		if (StringUtils.isBlank(command.getOldPwd())) {
			if(command.getType()==1){
				// 校验验证码
				SMSValidateRecord record = smsCodeDao.get(command.getValidToken());
//				if (record == null) {
//					hgLogger.error("chenxy", "密码修改失败>>验证码无效,请重新获取");
//					throw new UserException(UserException.RESULT_VALICODE_INVALID,
//							"验证码无效,请重新获取");
//				}
//				hgLogger.info("chenxy", "检查手机短信验证码");
//				record.checkSMSCode(command.getIdentify());
//				smsCodeDao.update(record);
				
				if(record==null){
					hgLogger.error("zhaows",command.getNewPwd()+"用户注册手机验证码无效");
					throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码无效,请重新获取");
				}
				if(!record.getValue().equals(command.getToken())){
					hgLogger.error("zhaows",command.getNewPwd()+"用户注册手机验证码无效");
					throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码错误,请重新输入");
				}
				
				userQO.setMobile(record.getMobile());
				user = userDao.queryUnique(userQO);
			}else if(command.getType()==2){
				// 校验验证码
				MailValidateRecord record = mailCodeDao.get(command.getValidToken());
				if (record == null) {
					hgLogger.error("chenxy", "密码修改失败>>验证码无效,请重新获取");
					throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码无效,请重新获取");
				}
				if(!record.getValue().equals(command.getToken())){
					hgLogger.error("zhaows",command.getNewPwd()+"用户注册邮箱验证码无效");
					throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码错误,请重新获取");
				}
				hgLogger.info("chenxy", "检查邮箱短信验证码");
				//record.checkMailCode(command.getIdentify());
				record.checkMailCode(command.getToken());
				mailCodeDao.update(record);
				userQO.setEmail(record.getEmail());
				user = userDao.queryUnique(userQO);
			}
			// 查询用户是否存在
			if (user == null) {
				hgLogger.error("chenxy", "密码修改失败>>用户不存在");
				throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
			}
		
		}else{
			user = userDao.get(command.getUserId());
			// 查询用户是否存在
			if (user == null) {
				hgLogger.error("chenxy", "密码修改失败>>用户不存在");
				throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
			}

			if (!user.getAuthInfo().getPassword().equals(command.getOldPwd())) {
				hgLogger.error("chenxy", "密码修改失败>>旧密码错误");
				throw new UserException(UserException.OLD_PASSWORD_WRONG,
						"旧密码错误");
			}
		}
		
		user.updatePassword(command.getNewPwd());
		userDao.update(user);
		
		return user;
	}
	
	/**
	 * 重置密码
	 * @param command
	 * @return
	 * @throws UserException
	 */
	public User UserRetPassword(UserUpdatePasswordCommand command)	throws UserException{
		User user = new User();
		if(command!=null){
				user = userDao.get(command.getUserId());
				// 查询用户是否存在
				if (user == null) {
					hgLogger.error("liusong", "重置密码失败>>用户不存在");
					throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
				}else{
					user.updatePassword(command.getNewPwd());
					userDao.update(user);
					hgLogger.error("liusong", "重置密码成功");
				}
		}else{
			hgLogger.error("liusong", "重置密码失败>>用户不存在");
			throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
		}
		return user;
	}
	/**
	 * 查询用户信息
	 * @param userBindAccountQO
	 * @return
	 * @throws UserException
	 */
	public User queryUser(HslUserBindAccountQO userBindAccountQO) throws  UserException{
		HgLogger.getInstance().info("yuqz", "UserLocalService->queryUser查询用户信息" + JSON.toJSONString(userBindAccountQO));
		// 按照用户信息查询
		if (userBindAccountQO.getUserQO() != null) {
			if (StringUtils.isNotBlank(userBindAccountQO.getUserQO().getId())) {
				User user = userDao.get(userBindAccountQO.getUserQO().getId());
				if (user != null) {
					hgLogger.info("yuqz",userBindAccountQO.getBindAccountName()+"查询用户成功");
					return user;
				} else {
					hgLogger.error("yuqz",userBindAccountQO.getBindAccountName()+"查询用户失败>>用户不存在");
					throw new UserException(UserException.USER_NOT_FOUND,	"用户不存在");
				}
			} else if (StringUtils.isNotBlank(userBindAccountQO.getUserQO().getLoginName())) {
				User user = userDao.queryUnique(userBindAccountQO.getUserQO());
				if (user != null) {
					hgLogger.info("yuqz",userBindAccountQO.getBindAccountName()+"查询用户成功");
					return user;
				} else {
					hgLogger.error("yuqz",userBindAccountQO.getBindAccountName()+"查询用户失败>>用户不存在");
					throw new UserException(UserException.USER_NOT_FOUND,"用户不存在");
				}
			}
		}
		// 按照绑定信息查询
		if (userBindAccountQO.getAccountType() == null) {
			hgLogger.error("yuqz","查询用户失败>>用户不存在");
			throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
		}
		//查询绑定用户是否存在
		UserBindAccount userBindAccount=null;
		try{
			userBindAccount=userBindAccountDao.queryUnique(userBindAccountQO);
			HgLogger.getInstance().info("yuqz", "UserLocalService->queryUser查询绑定用户是否存在结果:" + JSON.toJSONString(userBindAccount));
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz", "UserLocalService->queryUser:");
			return null; 
		}
		if(userBindAccount==null || !Hibernate.isInitialized(userBindAccount.getUser())){
			HgLogger.getInstance().info("yuqz", "UserLocalService->queryUser用户未绑定");
			return null;
		}else{
			HgLogger.getInstance().info("yuqz", userBindAccountQO.getBindAccountName()+"查询用户成功");
			return userBindAccount.getUser();
		}
	}
	/**
	 * 新建用户浏览记录
	 * @param command
	 */
	public void createUserClickRecord(UserClickRecordCommand command){
		ClickRecordQo clickRecordQo = new ClickRecordQo();
		clickRecordQo.setScenicSpotId(command.getScenicSpotId());
		ClickRecord clickRecord = clickRecordDao.queryUnique(clickRecordQo);
		//新建用户浏览记录
		if(clickRecord==null){
			clickRecord = new ClickRecord();
			clickRecord.setId(UUIDGenerator.getUUID());
		}
		clickRecord.setFromIP(command.getFromIP());
		clickRecord.setCreateDate(new Date());
		clickRecord.setScenicSpotId(command.getScenicSpotId());
		clickRecord.setType(ClickRecord.CLICK_RECORD_SCENICSPOT_DETAIL);
		clickRecord.setUrl(command.getUrl());
		if(StringUtils.isNotBlank(command.getUserId())){
			clickRecord.setUserId(command.getUserId());
		}
		clickRecordRepository.save(clickRecord);
		//更新景区点击量
		createMPScenicSpotClickRate(command);
		
	}

	/**
	 * 查询用户浏览记录
	 * @param userClickRecordQO
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<ClickRecord> queryUserClickRecord(HslUserClickRecordQO userClickRecordQO){
		//分页查询用户点击记录
		Pagination pagination=new Pagination();
		ClickRecordQo clickRecordQo=new ClickRecordQo();
		clickRecordQo.setUserId(userClickRecordQO.getUserId());
		clickRecordQo.setCreateDateDesc(true);
		pagination.setCondition(clickRecordQo);
		pagination.setPageNo(1);
		pagination.setPageSize(userClickRecordQO.getPageSize());
	
		pagination=clickRecordDao.queryPagination(pagination);
		List<ClickRecord> list=(List<ClickRecord>)pagination.getList();
		
		return list;
	}
	
	public void createMPScenicSpotClickRate(UserClickRecordCommand command){
		ClickRateQo clickRateQo=new ClickRateQo();
		clickRateQo.setSceincSpotId(command.getScenicSpotId());
		ClickRate clickRate=clickRateDao.queryUnique(clickRateQo);
		if(clickRate!=null){
			clickRate.setClickAmount(clickRate.getClickAmount()+1);
		}else{
			clickRate=new ClickRate();
			clickRate.setScenicSpotId(command.getScenicSpotId());
			clickRate.setClickAmount(1L);
		}
		
		clickRateRepository.save(clickRate);
		
	}
	
	/**
	 * 修改企业用户信息
	 * @param command
	 * @return
	 */
	public User updateCompanyUser(UpdateCompanyUserCommand command){
		User user=userDao.get(command.getUserId());
		user.updateCompanyUser(command);
		userDao.update(user);
		return user;
	}
	
	
	/**
	 * 查询验证码
	 * @param hslSMSCodeQO
	 * @return
	 * @throws UserException 
	 */
	public String querySendValidCode(HslSMSCodeQO hslSMSCodeQO) throws UserException{
		
		SMSValidateRecord record = smsCodeDao.get(hslSMSCodeQO.getToken());
		if (record == null) {
			throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码无效,请重新获取");
		}
		record.checkSMSCode(hslSMSCodeQO.getValidCode());
		smsCodeDao.update(record);
		
		return "success";
	}
	
	/**
	 * 修改绑定手机
	 * @param command
	 * @return
	 */
	public User updateBindMobile(UpdateBindMobileCommand command){
		User user=userDao.get(command.getUserId());
		user.updateBindMobile(command);
		userDao.update(user);
		return user;
	}

	/**
	 * 修改头像
	 * @参数：@param command
	 * @参数：@return
	 * @return:User
	 * @throws
	 */
	public User updateHeadImage(UpdateHeadImageCommand command) {
		User user = userDao.get(command.getUserId());
		user.updateHeadImage(command);
		userDao.update(user);
		return user;
	}
	/**
	 * @方法功能说明：
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月20日下午4:50:39
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @throws UserException 
	 * @return:User
	 * @throws
	 */
	public User checkRegisterMail(CheckRegisterMailCommand command) throws UserException{
		MailValidateRecord record = mailCodeDao.get(command.getToken());
		if (record == null) {
			throw new UserException(UserException.RESULT_VALICODE_INVALID,"验证码无效,请重新获取");
		}
		HslUserQO userQO=new HslUserQO();
		userQO.setEmail(record.getEmail());
		User user=userDao.queryUnique(userQO);
		user.getStatus().setIsEmailChecked(true);
		userDao.update(user);
		return user;
	}
	/**
	 * @方法功能说明：验证邮箱
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月18日下午2:20:16
	 * @修改内容：
	 * @参数：@param email
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public static boolean isEmail(String email){     
	     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	     Pattern p = Pattern.compile(str);     
	     Matcher m = p.matcher(email);     
	     return m.matches();     
   } 
	public String sendRegisterMail(SendMailCodeCommand command) throws UserException{
		MailValidateRecord record = new MailValidateRecord();
		record.createRegisterMailRecord(command);
		String mailHost=SysProperties.getInstance().get("mailHost");
		String mailAccount=SysProperties.getInstance().get("mailAccount");
		String mailPwd=SysProperties.getInstance().get("mailPwd");
		List<String> accepts=new ArrayList<String>();
		accepts.add(command.getMail());
		String title="票量-请激活您的票量账号";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name",command.getMail());
		map.put("url", getWebAppPath()+"/user/checkRegisterMail?token="+record.getId()+"&identify="+record.getValue());
		map.put("time",DateUtil.formatDateTime(new Date()));
		try {
			MailUtil.getInstance().sendTemplateMail1(mailHost, mailAccount, mailPwd, title,SysProperties.getInstance().get("registerTemplateId"), map, mailAccount, accepts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailCodeDao.save(record);
		return record.getId();
	}

	public void batchRegisterUsers(BatchRegisterUserCommand command) throws UserException {
		List<UserDTO> list = command.getUsers();
		if(list!=null&&list.size()>0){
			for(UserDTO dto : list){
				User user = BeanMapperUtils.map(dto, User.class);
				UserBaseInfo baseInfo = user.getBaseInfo();
				if(baseInfo==null){
					baseInfo = new UserBaseInfo();
				}
				baseInfo.setCreateTime(new Date());
				baseInfo.setSource("PC");
				user.setBaseInfo(baseInfo);
				
				UserStatus status = user.getStatus();
				if(status==null){
					status = new UserStatus();
				}
				status.setActivated(true);
				user.setStatus(status);
				
				userDao.save(user);
			}
		}
	}
}
