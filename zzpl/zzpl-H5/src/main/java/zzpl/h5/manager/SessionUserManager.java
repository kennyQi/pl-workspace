package zzpl.h5.manager;

import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;

/**
 * @类功能说明：后台会话用户管理
 * @类修改者：
 * @修改日期：2014-9-23上午10:46:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-9-23上午10:46:17
 */
public class SessionUserManager {

	/**
	 * 用户session
	 */
	public final static String SESSION_USER_KEY = "_SESSION_USER_";
	
	/**
	 * 工程session
	 */
	public final static String SESSION_PROJECT_KEY = "_SESSION_PROJECT_";
	
	
	/**
	 * @方法功能说明：当断是否登录
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23上午10:58:45
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public static boolean isLogin(HttpServletRequest request) {
		Object obj = request.getSession()
				.getAttribute(SESSION_USER_KEY);
		if (obj != null)
			return true;
		return false;
	}
	
	
	/**
	 * @param project 
	 * @方法功能说明：登陆
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日下午1:44:20
	 * @修改内容：
	 * @参数：@param user
	 * @return:void
	 * @throws
	 */
	public static void login(Subject currentUser, AuthUser user) {
		currentUser.getSession().setAttribute(SESSION_USER_KEY, user);
	}

	
	/**
	 * @param currentUser
	 * @方法功能说明：登出
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日下午1:44:35
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public static void logout(Subject currentUser) {
		// 销毁session
		currentUser.getSession().removeAttribute(SESSION_USER_KEY);
		currentUser.getSession().removeAttribute(SESSION_PROJECT_KEY);
	}

}
