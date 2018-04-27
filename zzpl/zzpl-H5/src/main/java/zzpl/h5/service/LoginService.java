package zzpl.h5.service;

import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;
import hg.system.service.AuthStaffService;
import hg.system.service.AuthUserService;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.request.user.CheckSMSValidCommand;
import zzpl.api.client.request.user.GetResetPasswordSMSValidCommand;
import zzpl.api.client.response.user.CheckSMSValidResponse;
import zzpl.api.client.response.user.GetResetPasswordSMSValidResponse;
import zzpl.api.client.response.user.LoginResponse;
import zzpl.api.client.util.Md5Util;
import zzpl.h5.manager.SessionUserManager;
import zzpl.h5.service.command.LoginCommand;
import zzpl.h5.service.command.ValidCodeCheckCommand;
import zzpl.h5.service.exception.LoginException;
import zzpl.h5.util.LoginUtil;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;

@Service
@Transactional
public class LoginService {
	
		
	/**
	 * 操作员service
	 */
	@Resource
	private AuthStaffService 	authStaffService;
	
	/**
	 * 用户service
	 */
	@Resource
	private AuthUserService authUserService;
	
	@Autowired
	private UserService userService;
	
	
	private static final String HG_RIA_SESSION_VALID_CODE_KEY = "_HG_RIA_SESSION_VALID_CODE_";
	
	@Autowired
	private Producer captchaProducer;
	
	@Autowired
	private ApiClient apiClient;

	
	/**
	 * @方法功能说明：登陆检查
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日下午1:30:05
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws LoginException
	 * @return:void
	 * @throws
	 */
	public void loginCheck(LoginCommand command, HttpServletRequest request) throws LoginException  {
		
		// 1. 读取参数
		String loginName = command.getLoginName();
		String password  = command.getPassword();
		String companyNO = command.getCompanyNO();

		// 3. 账户密码检查
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)||StringUtils.isBlank(companyNO)) {
			throw new LoginException(
					LoginException.NAME_OR_PAS_IS_EMPT, "登录信息不能为空！");
		} else {
			// 3.1 用户非空检查
			AuthStaffQo staffQo = new AuthStaffQo();
			staffQo.setLoginName(loginName);
			Staff staff = authStaffService.queryUnique(staffQo);
			if (staff == null) {
				throw new LoginException(
						LoginException.NAME_OR_PAS_IS_EMPT, "用户名不存在！");
			}
		}
	}
	
	
	/**
	 * @param request 
	 * @方法功能说明：登陆
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日下午1:29:57
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws LoginException
	 * @return:void
	 * @throws
	 */
	public void login(LoginCommand command, HttpServletRequest request) throws LoginException  {
		
		// 1. 读取参数
		String loginName = command.getLoginName();
		String password  = command.getPassword();
		String userID = "";
		// 2. 检查
		loginCheck(command, request);
		UserDTO userDTO = new UserDTO();
		userDTO.setUserNO(command.getCompanyNO());
		userDTO.setLoginName(command.getLoginName());
		userDTO.setPassword(Md5Util.MD5(command.getPassword()));
		zzpl.api.client.request.user.LoginCommand apiCommand = new zzpl.api.client.request.user.LoginCommand();
		apiCommand.setUserDTO(userDTO);
		LoginResponse loginResponse = userService.Login(apiCommand);
		if(loginResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			userID=loginResponse.getUserID();
			request.getSession().setAttribute("USER_SESSION_ID", loginResponse.getSessionID());
		}else{
			throw new LoginException(LoginException.PASSWORD_ERROR, loginResponse.getMessage());
		}
		// 3. 查出用户  及  所属工程
		AuthUser usr = authUserService.get(userID);
		AuthStaffQo staffQo = new AuthStaffQo();
		staffQo.setLoginName(loginName);
		loginName = usr.getLoginName();
		UsernamePasswordToken upt = new UsernamePasswordToken(loginName,
				MD5HashUtil.toMD5(MD5HashUtil.toMD5(password)));
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 1. shiro 登陆
			currentUser.login(upt);
			// 2. 写入session
			SessionUserManager.login(currentUser, usr);
			// 3. 移除登陆次数
			LoginUtil.getLoginlimitmap().remove(loginName);
			System.out.println(JSON.toJSONString(request.getSession().getAttributeNames()));
		} catch (UnknownAccountException uae) {
			throw new LoginException(
					LoginException.NAME_OR_PAS_IS_EMPT, "用户名不存在！");
		} catch (IncorrectCredentialsException ice) {
			throw new LoginException(
					LoginException.PASSWORD_ERROR, "密码不正确！");
		} catch (LockedAccountException lae) {
			throw new LoginException(
					LoginException.USER_LOCKED, "用户被锁定！");
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			throw new LoginException(
					LoginException.USER_ERROR, "用户异常！");
		}
	}


	/**
	 * @方法功能说明：登出
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日下午2:10:47
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void logout() {
		// 销毁session
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		SessionUserManager.logout(currentUser);
	}
	
	/**
	 * @方法功能说明：生产验证图片
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日上午11:27:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @return:BufferedImage
	 * @throws
	 */
	public BufferedImage getKaptchaImage(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		// 生成验证码
		String capText = captchaProducer.createText();
		session.setAttribute(HG_RIA_SESSION_VALID_CODE_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		
		return bi;
	}

	
	/**
	 * @param request 
	 * @方法功能说明：验证验证码
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日下午12:03:08
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean validCodeCheck(ValidCodeCheckCommand command, HttpServletRequest request) {
		
		String validCode = command.getValidCode();
		
		if (validCode == null||StringUtils.isBlank(validCode))
			return false;

		String sessionValidCode = (String) request.getSession().getAttribute(
				HG_RIA_SESSION_VALID_CODE_KEY);
		request.getSession().removeAttribute(HG_RIA_SESSION_VALID_CODE_KEY);

		if (sessionValidCode == null)
			return false;

		// 匹配验证码
		if (StringUtils.equalsIgnoreCase(validCode, sessionValidCode))
			return true;

		return false;
	}
	
	public GetResetPasswordSMSValidResponse checkBh_Zh(	GetResetPasswordSMSValidCommand getResetPasswordSMSValidCommand) {
	//	getResetPasswordSMSValidCommand.setCompanyID("2886ae8b9d294b8194fca5fce4f7a448");
	//	getResetPasswordSMSValidCommand.setLoginName("qcr");
		ApiRequest request = new ApiRequest("GetResetPasswordSMSValid", "", getResetPasswordSMSValidCommand, "1.0");
		GetResetPasswordSMSValidResponse response = apiClient.send(request,GetResetPasswordSMSValidResponse.class);
		System.out.println(JSON.toJSON(response));
		return response;
	}


	

	public CheckSMSValidResponse checkSmsValid(CheckSMSValidCommand checkCommand) {
		//command.setSagaID("f49e046cd2ec4490bef4d114f972dd44");
		//command.setSmsValid("222621");
		ApiRequest request = new ApiRequest("CheckSMSValid", "", checkCommand, "1.0");
		CheckSMSValidResponse response = apiClient.send(request,CheckSMSValidResponse.class);
		System.out.println(JSON.toJSON(response));
		return response;
		
	}

}
