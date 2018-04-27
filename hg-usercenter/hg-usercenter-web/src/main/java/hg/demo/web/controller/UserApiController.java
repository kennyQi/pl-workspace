package hg.demo.web.controller;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hg.demo.member.common.common.DateUtils;
import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.UserBaseInfoSPI;
import hg.demo.member.common.spi.command.userinfo.ModifyPasswordCommand;
import hg.demo.member.common.spi.command.userinfo.ModifyUserInfoCommand;
import hg.demo.member.common.spi.qo.UserBaseInfoSQO;
import hg.demo.web.aop.Action;
import hg.demo.web.common.UserConstants;
import hg.demo.web.component.cache.CacheManager;
import hg.demo.web.filter.HGMessageDigest;
import hg.framework.common.model.Pagination;
/**
 * 
 * @author xuww
 *
 */
@Controller
public class UserApiController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserBaseInfoSPI userBaseInfoSPIService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping(value="/queryUserInfo")
	public @ResponseBody Result<UserBaseInfo> queryUserInfo(Model model, @RequestParam(value="id", required = false) String id) {
		Result<UserBaseInfo> result = new Result<>();
		UserBaseInfo userBaseInfo = userBaseInfoSPIService.queryUserInfo(id);
		result.setResult(userBaseInfo);
		return result;
	}

	@Action(desc="查询用户信息列表", type = UserConstants.USERCENTER_QUERY_BASEINFOLIST)
	@RequestMapping(value="/queryUserList")
	public String queryUserList(HttpServletRequest request, Model model, @ModelAttribute("sqo") UserBaseInfoSQO sqo) {
		long a = System.currentTimeMillis();
		Result<Pagination<UserBaseInfo>> result = new Result<>();
		Pagination<UserBaseInfo> userpage = userBaseInfoSPIService.queryUserListPage(sqo);
		result.setResult(userpage);
		long time = System.currentTimeMillis() - a;
		model.addAttribute("pagination", userpage);
		model.addAttribute("time", time);
		model.addAttribute("request", request);
		return "/userinfo/index.ftl";
	}
	
	@Action(desc="用户信息修改", type = UserConstants.USERCENTER_MODIFY_BASEINFO)
	@RequestMapping({"/modifyUserInfo","/register"})
	public @ResponseBody Result<UserBaseInfo> modifyUserInfo(@ModelAttribute ModifyUserInfoCommand command) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.saveUserInfo(command);
		return result;
	}
	
	@Action(desc="用户密码修改", type = UserConstants.USERCENTER_MODIFY_PASSWORD)
	@RequestMapping("/modifyLoginPass")
	public @ResponseBody Result<UserBaseInfo> modifyLoginPass(@ModelAttribute ModifyPasswordCommand command) {
		Result<UserBaseInfo> result = userBaseInfoSPIService.changePassword(command);
		return result;
	}
	
	@Action(desc="用户激活", type = UserConstants.USERCENTER_ACTIVE)
	@RequestMapping(value="/active")
	public @ResponseBody Result<UserBaseInfo> active(Model model, 
			@RequestParam(value="id", required = false) String id) {
		Result<UserBaseInfo> result = activeDisable(id, 1);
		return result;
	}
	
	@Action(desc="用户禁用", type = UserConstants.USERCENTER_DISABLE)
	@RequestMapping(value="/disable")
	public @ResponseBody Result<UserBaseInfo> disable(Model model, 
			@RequestParam(value="id", required = false) String id) {
		Result<UserBaseInfo> result = activeDisable(id, -1);
		return result;
	}
	private Result<UserBaseInfo> activeDisable(String id, int status){
		Result<UserBaseInfo> result = userBaseInfoSPIService.active(id, status);
		return result;
	}
	
	@RequestMapping(value="/genSign")
	public String genSign(HttpServletRequest request, Model model){
		String appId = request.getParameter("appId");
		String time = request.getParameter("time");
		String sign = request.getParameter("sign");
		if(StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(time)){
			Date date = DateUtils.format(time);
			if(date == null){
				model.addAttribute("sign", "时间格式错误！");
				return "/userinfo/sign.ftl";
			}
			long longtime = DateUtils.longtime(date);
			if(longtime > 1800000){//超过前後30分钟
				model.addAttribute("sign", "时间超时！");
				return "/userinfo/sign.ftl";
			}
			HGMessageDigest hgDigest = HGMessageDigest.getInstance();
			sign = hgDigest.genSign(appId, time, hgDigest.getToken());
		}
		model.addAttribute("appId", appId);
		model.addAttribute("time", time);
		model.addAttribute("sign", sign);
		return "/userinfo/sign.ftl";
	}
}
