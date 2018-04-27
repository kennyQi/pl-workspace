package hg.demo.web.controller;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import hg.demo.member.common.common.DateUtils;
import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.UserBaseInfoSPI;
import hg.demo.member.common.spi.command.userinfo.ModifyPasswordCommand;
import hg.demo.member.common.spi.command.userinfo.ModifyUserInfoCommand;
import hg.demo.member.common.spi.qo.UserBaseInfoSQO;
import hg.demo.web.filter.HGMessageDigest;
import hg.framework.common.model.Pagination;
/**
 * 
 * @author xuww
 *
 */
public class UserApiController {
	private static Logger logger = LoggerFactory.getLogger(UserApiController.class);

	private static ApplicationContext context;
	private static UserBaseInfoSPI userBaseInfoSPIService;
	
	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userBaseInfoSPIService = context.getBean(UserBaseInfoSPI.class);
	}

	/**
	 * 查詢單個用戶
	 * @param id
	 * @return
	 */
	public static Result<UserBaseInfo> queryUserInfo(String id) {
		Result<UserBaseInfo> result = new Result<>();
		UserBaseInfo userBaseInfo = userBaseInfoSPIService.queryUserInfo(id);
		result.setResult(userBaseInfo);
		return result;
	}

	/**
	 * 查询用户列表
	 * @param sqo
	 * @return
	 */
	public static Result<Pagination<UserBaseInfo>> queryUserList(UserBaseInfoSQO sqo) {
		long a = System.currentTimeMillis();
		Result<Pagination<UserBaseInfo>> result = new Result<>();
		Pagination<UserBaseInfo> userpage = userBaseInfoSPIService.queryUserListPage(sqo);
		result.setResult(userpage);
		long time = System.currentTimeMillis() - a;
		return result;
	}
	/**
	 * 修改或注册用户
	 * @param command
	 * @return
	 */
	public static Result<UserBaseInfo> modifyUserInfo(ModifyUserInfoCommand command) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.saveUserInfo(command);
		return result;
	}
	/**
	 * 修改密码
	 * @param command
	 * @return
	 */
	public static Result<UserBaseInfo> modifyLoginPass(ModifyPasswordCommand command) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.changePassword(command);
		return result;
	}
	/**
	 * 用户激活
	 * @param id
	 * @return
	 */
	public static Result<UserBaseInfo> active(String id) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.active(id, 1);
		return result;
	}
	/**
	 * 用户禁用
	 * @param id
	 * @return
	 */
	public static Result<UserBaseInfo> disable(String id) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.active(id, -1);
		return result;
	}
	/**
	 * 生成签名
	 * @param appId
	 * @param time
	 * @return
	 */
	public static String genSign(String appId, String time){
		if(StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(time)){
			Date date = DateUtils.format(time);
			if(date == null){
				logger.warn("sign", "时间格式错误！");
				return "时间格式错误！";
			}
			long longtime = DateUtils.longtime(date);
			if(longtime > 1800000){//超过前後30分钟
				logger.warn("sign", "时间超时！");
				return "时间超时！";
			}
			HGMessageDigest hgDigest = HGMessageDigest.getInstance();
			return hgDigest.genSign(appId, time, hgDigest.getToken());
		}
		
		return "appId和time 不能为空！";
	}
}
