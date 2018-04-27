package zzpl.app.service.local.user;

import hg.common.util.Md5Util;
import hg.common.util.SysProperties;
import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;
import hg.system.service.AuthStaffService;
import hg.system.service.AuthUserService;

import java.awt.image.BufferedImage;
import java.util.List;

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
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.service.local.util.LoginUtil;
import zzpl.app.service.local.util.SessionUserManager;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.LoginCommand;
import zzpl.pojo.command.user.ValidCodeCheckCommand;
import zzpl.pojo.exception.user.LoginException;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.user.UserQO;

import com.google.code.kaptcha.*;

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
	
	/**
	 * 验证码生产器
	 */
	@Autowired
	private Producer captchaProducer;
	
	/**
	 * 验证码session名称
	 */
	private static final String HG_RIA_SESSION_VALID_CODE_KEY = "_HG_RIA_SESSION_VALID_CODE_";
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private CompanyDAO companyDAO;

	
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
		String validcode = command.getValidcode();
		String loginName = command.getLoginName();
		String password  = command.getPassword();
		
		if (command.getCheckValidcode()) {
			// 2. 验证码检查
			ValidCodeCheckCommand validCodeCheckCommand                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               = new ValidCodeCheckCommand();
			validCodeCheckCommand.setValidCode(validcode);
			
			if (!validCodeCheck(validCodeCheckCommand, request)) {
				
				throw new LoginException(
						LoginException.VAILD_CODE_ERROR, "验证码不正确！");
			}
		}

		// 3. 账户密码检查
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			throw new LoginException(
					LoginException.NAME_OR_PAS_IS_EMPT, "用户名不存在！");
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
		
		// 2. 读取参数
		String loginName = command.getLoginName();
		String password  = command.getPassword();
		
		String adminID = SysProperties.getInstance().get("adminID");
		String travleAdminID = SysProperties.getInstance().get("travleAdminID");
		boolean flag = false;
		
		UserQO userQO2 = new UserQO();
		userQO2.setLoginName(Md5Util.MD5(loginName));
		userQO2.setPassword(Md5Util.MD5(Md5Util.MD5(password)));
		List<User> users = userDAO.queryList(userQO2);
		
		boolean companyFlay = false;
		String userID = "";
		for (User user : users) {
			Hibernate.initialize(user.getUserRoles());
			for (UserRole userRole : user.getUserRoles()) {
				if (StringUtils.equals(userRole.getRole().getId(), adminID)||StringUtils.equals(userRole.getRole().getId(), travleAdminID)) {
					flag = false;
					userID = user.getId();
					break;
				}
				flag = true;
			}
			
			if (flag) {
				if (user.getCompanyID() == null || StringUtils.isBlank(user.getCompanyID())) {
					throw new LoginException(
							LoginException.COMPANY_ERROR, "该用户无所属公司！");
				}
				CompanyQO companyQO = new CompanyQO();
				if (command.getCompanyNO() == null || StringUtils.isBlank(command.getCompanyNO())) {
					throw new LoginException(
							LoginException.COMPANY_ERROR, "公司编号不能为空！");
				}
				companyQO.setCompanyID(command.getCompanyNO());
				companyQO.setId(user.getCompanyID());
				Company company = companyDAO.queryUnique(companyQO);
				if(company != null){
					companyFlay = false;
					userID = user.getId();
					break;
				}else {
					companyFlay = true;
				}
			}
		}
		if (companyFlay) {
			throw new LoginException(
					LoginException.COMPANY_ERROR, "公司编号不正确！");
		}
		
		// 1. 检查
		loginCheck(command, request);
		
		// 2. 查出用户  及  所属工程
		AuthUser usr = authUserService.get(userID);
		if(usr==null){
			throw new LoginException(
					LoginException.PASSWORD_ERROR, "密码不正确！");
		}
		AuthStaffQo staffQo = new AuthStaffQo();
		staffQo.setLoginName(loginName);
//		ProjectQo projectQo = new ProjectQo();
//		projectQo.setStaffQo(staffQo);
//		Project project = projectService.queryUnique(projectQo);
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
	

}
