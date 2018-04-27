package zzpl.admin.controller.auth;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.LoginService;
import zzpl.app.service.local.user.RoleMenuService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.LoginCommand;
import zzpl.pojo.command.user.ValidCodeCheckCommand;
import zzpl.pojo.dto.user.MenuDTO;
import zzpl.pojo.dto.user.RoleMenuDTO;
import zzpl.pojo.dto.user.UserRoleDTO;
import zzpl.pojo.dto.user.status.CompanyStatus;
import zzpl.pojo.exception.user.LoginException;
import zzpl.pojo.qo.user.RoleMenuQO;
import zzpl.pojo.qo.user.UserRoleQO;

import com.alibaba.fastjson.JSON;

@Controller
public class LoginController extends BaseController {
	
	/**
	 * 登陆service
	 */
	@Autowired
	private LoginService loginService;
	/**
	 * 用户service
	 */
	@Autowired
	private UserService userService;
	/**
	 * 公司service
	 */
	@Autowired
	private CompanyService companyService;
	/**
	 * 用户角色service
	 */
	@Autowired
	private UserRoleService userRoleService;
	/**
	 * 角色菜单service
	 */
	@Autowired
	private RoleMenuService roleMenuService;
	
	/**
	 * @方法功能说明：验证码输出
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23下午4:57:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/login/valid-code-image.jpg")
	public void getKaptchaImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BufferedImage bi = loginService.getKaptchaImage(request, response);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * @方法功能说明：验证码检查
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23下午4:56:56
	 * @修改内容：
	 * @参数：@param validCode
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	protected boolean validCodeCheck(HttpServletRequest request,
			ValidCodeCheckCommand command) {

		return loginService.validCodeCheck(command, request);
	}

	/**
	 * 
	 * @方法功能说明：跳转登陆页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:09:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param message
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model, String message) {

		model.addAttribute("message", message);
		return "/admin/login.html";
	}

	/**
	 * 
	 * @方法功能说明：登录校验
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:09:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping(value = "/login/check")
	public RedirectView loginCheck(HttpServletRequest request,
			HttpServletResponse response, Model model, LoginCommand command)
			throws IOException {

		try {
			loginService.login(command, request);
		} catch (LoginException e) {
			HgLogger.getInstance().error("cs",
					"登陆失败:command【" + JSON.toJSONString(command, true) + "】");
			model.addAttribute("message", e.getMessage());
			return new RedirectView("/login", true);
		}
		return new RedirectView("/home", true);
	}

	/**
	 * @方法功能说明：注销登录
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:08:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		loginService.logout();
		response.sendRedirect(request.getContextPath() + "/login");
	}

	/**
	 * 
	 * @方法功能说明：登录后跳转
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:07:56
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/home")
	public String index(HttpServletRequest request,Model model){
		AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			//1.校验用户是否合法
			User zzplUser= userService.get(user.getId());
			if(zzplUser.getCompanyID()!=null){
				boolean flag = true;
				UserRoleQO userRoleQO = new UserRoleQO();
				userRoleQO.setUserID(zzplUser.getId());
				List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
				for (UserRole userRole : userRoles) {
					if(StringUtils.equals(userRole.getRole().getId(), SysProperties.getInstance().get("travleAdminID"))){
						model.addAttribute("role", "role");
						flag=false;
					}
				}
				if(flag&&companyService.get(zzplUser.getCompanyID()).getStatus()==CompanyStatus.DELETED){
					model.addAttribute("message", "账号失效,请联系管理员!");
					return "/admin/login.html";
				}
			}
			//2.获取用户角色
			UserRoleQO userRoleQO = new UserRoleQO();
			userRoleQO.setUserID(user.getId());
			List<UserRoleDTO> userRoleDTOs = userRoleService.queryUserRoleList(userRoleQO);
			//3.根据角色获取菜单
			List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
			List<String> strings = new ArrayList<String>();
			for(UserRoleDTO userRoleDTO:userRoleDTOs){
				RoleMenuQO roleMenuQO = new RoleMenuQO();
				roleMenuQO.setRoleID(userRoleDTO.getRole().getId());
				List<RoleMenuDTO> roleMenuDTOs = roleMenuService.queryRoleMenuList(roleMenuQO);
				for(RoleMenuDTO roleMenuDTO:roleMenuDTOs){
					if(!strings.contains(roleMenuDTO.getMenu().getId())){
						menuDTOs.add(roleMenuDTO.getMenu());
						strings.add(roleMenuDTO.getMenu().getId());
					}
				}
			}
			Collections.sort(menuDTOs, new Comparator<MenuDTO>() {
				@Override
				public int compare(MenuDTO o1,MenuDTO o2) {
					return o1.getSort().compareTo(o2.getSort());
				}
			});
			model.addAttribute("user", zzplUser);
			model.addAttribute("menuDTOs", menuDTOs);
			return "/home.html";
		}else{
			return "/admin/login.html";
		}
	}
	
	/**
	 * 
	 * @方法功能说明：主页面默认页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:08:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/admin/welcome.html";
	}
}
