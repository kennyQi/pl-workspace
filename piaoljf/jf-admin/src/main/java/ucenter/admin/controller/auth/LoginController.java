package ucenter.admin.controller.auth;

import hg.common.util.DwzJsonResultUtil;
import hg.hjf.domain.model.log.OperationLog;
import hg.logservice.OperationLogService;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.qo.StaffQo;
import hg.system.service.SecurityService;
import hg.system.service.StaffService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import ucenter.admin.common.LoginUtil;
import ucenter.admin.controller.BaseController;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SecurityService securityService;
	@Resource
	private StaffService staffService;

	@Autowired
	OperationLogService operationLogService;
	
	/**
	 * 后台登录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model, 
				        @RequestParam(required=false) String error,
				        @RequestParam(required=false)String loginName) {
		
		String message="";
		if(SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME.equals(error)){
			message="用户名不存在";
		}else if(SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)){
			
			Staff staff = LoginUtil.getLoginlimitmap().get(loginName);
			int x = 5 - staff.getPwdErrorNum();
			if(x==0){
				message="用户将被禁止登录1小时";
			}else{
				message="用户名或密码错误,"+x+"次后将被禁止登录1小时";
			}
			
		}else if(SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)){
			message="用户被锁定";
		}else if(SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)){
			message="用户异常";
		}else if(SecurityConstants.LOGIN_CHECK_ERROR_FREEZE_TIME.equals(error)){
			message="多次登录错误,用户处于禁止登录中,稍后再试";
		}
		
		model.addAttribute("message", message);
		
		return "/admin/login.html";
	}
	
	/**
	 * 登录表单提交入口
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login/check")
	public RedirectView loginCheck(HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam(value="loginName", required=false) String loginName,
			@RequestParam(value = "password", required = false) String password)
			throws IOException {

		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
			logerror(request,loginName,model);
			return new RedirectView("/login", true);
		}else{
			Staff errorStaff = LoginUtil.getLoginlimitmap().get(loginName);
			if(errorStaff!=null && errorStaff.getPwdErrorNum()>=5){
				if(System.currentTimeMillis() - errorStaff.getLastErrorTime() > 3600000){
					LoginUtil.getLoginlimitmap().remove(loginName);
					errorStaff = null;
				}else{
					//限制1小时内登录时未到
					model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_FREEZE_TIME);
					logerror(request,loginName,model);
					return new RedirectView("/login", true);
				}
			}
			
			StaffQo sqo=new StaffQo();
			sqo.setLoginName(loginName);
			Staff staff=staffService.queryUnique(sqo);
			
			if(staff == null){
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
				logerror(request,loginName,model);
				return new RedirectView("/login", true);
			}
	
			UsernamePasswordToken upt = new UsernamePasswordToken(loginName, MD5HashUtil.toMD5(password));
			Subject currentUser = SecurityUtils.getSubject();
	
			try {
				currentUser.login(upt);
			} catch (UnknownAccountException uae) {
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
				logerror(request,loginName,model);
				return new RedirectView("/login", true);
			} catch (IncorrectCredentialsException ice) {
				
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD);
				//密码错误时记录登录错误次数和时间
				if(errorStaff!=null){
					errorStaff.setLastErrorTime(System.currentTimeMillis());
					errorStaff.setPwdErrorNum(errorStaff.getPwdErrorNum()+1);
					LoginUtil.getLoginlimitmap().put(loginName, errorStaff);
				}else{
					Staff stf = new Staff();
					stf.setLastErrorTime(System.currentTimeMillis());
					stf.setPwdErrorNum(1);
					LoginUtil.getLoginlimitmap().put(loginName, stf);
				}
				model.addAttribute("loginName", loginName);
				logerror(request,loginName,model);
				return new RedirectView("/login", true);
			} catch (LockedAccountException lae) {
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_LOCK);
				logerror(request,loginName,model);
				return new RedirectView("/login", true);
			} catch (AuthenticationException ae) {
				ae.printStackTrace();
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_NULL);
				logerror(request,loginName,model);
				return new RedirectView("/login", true);
			}
	
			AuthUser usr = securityService.findUserByLoginName(loginName);
			currentUser.getSession().setAttribute(SecurityConstants.SESSION_USER_KEY, usr);
			request.getSession().setAttribute(SecurityConstants.SESSION_USER_KEY,usr);
			//登录成功后清空错误次数限制
			LoginUtil.getLoginlimitmap().remove(loginName);
			
		operationLogService.login(loginName, request.getRemoteAddr(), OperationLog.entryEntAdmin, true, "");	
		return new RedirectView("/home", true);
		}
	}
	


	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser u=(AuthUser)currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY); 
		currentUser.logout();
		currentUser.getSession().removeAttribute(SecurityConstants.SESSION_USER_KEY);
		if(u!=null)
		operationLogService.logout(u.getLoginName(), request.getRemoteAddr(), OperationLog.entryEntAdmin, true, "");
        response.sendRedirect(request.getContextPath()+"/login");
	}
	
	/**
	 * @方法功能说明：修改密码
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-6下午4:50:13
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/password-edit")
	public String passwordEdit() {
		return "/admin/passwordEdit.html";
	}
	/**
	 * @方法功能说明：修改密码
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-6下午4:50:13
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/change-password")
	public String changePassword(HttpServletRequest request,
			@RequestParam(value = "oldpwd", required = true) String oldpwd,
			@RequestParam(value = "newPwd", required = true) String newPwd,
			@RequestParam(value = "reNewPwd", required = true) String reNewPwd) {
		Object obj = request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if (obj != null) {
			AuthUser usr = (AuthUser) obj;
			if (oldpwd.length() == 0 || newPwd.length() < 6)
				return DwzJsonResultUtil.createSimpleJsonString(
						DwzJsonResultUtil.STATUS_CODE_300, "修改失败：密码长度不够");
			if (!StringUtils.equals(newPwd, reNewPwd))
				return DwzJsonResultUtil.createSimpleJsonString(
						DwzJsonResultUtil.STATUS_CODE_300, "修改失败：两次输入的密码不同");
			try {
				securityService.updateUserPassword(usr.getId(), oldpwd, newPwd);
				return DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_200, "修改成功",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
			} catch (Exception e) {
				e.printStackTrace();
				return DwzJsonResultUtil.createSimpleJsonString(
						DwzJsonResultUtil.STATUS_CODE_300,
						"修改失败：" + e.getMessage());
			}
		}
		return DwzJsonResultUtil.createSimpleJsonString(
				DwzJsonResultUtil.STATUS_CODE_500, "修改失败");
	}
	
	private void logerror(HttpServletRequest request, String loginName, Model model) {
		operationLogService.login(loginName, request.getRemoteAddr(),OperationLog.entryEntAdmin, false, errorMsg(model.asMap().get("error").toString()));
	}
	private String errorMsg(String errCode){
		int indexOf = SecurityConstants.LOGIN_CHECK_ERROR_LIST.indexOf(errCode);
		if(indexOf!=-1)
			return SecurityConstants.LOGIN_CHECK_ERROR_LIST.get(indexOf).getValue().toString();
		else
			return "未知的错误码";
	}
}
