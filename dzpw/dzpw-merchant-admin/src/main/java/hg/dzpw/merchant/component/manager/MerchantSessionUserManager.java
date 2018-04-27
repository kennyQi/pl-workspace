package hg.dzpw.merchant.component.manager;

import hg.dzpw.app.pojo.vo.MerchantSessionUserVo;

import javax.servlet.http.HttpServletRequest;


/**
 * @类功能说明：商户管理员登录用户会话管理
 * @类修改者：
 * @修改日期：2015-2-11上午10:04:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:04:22
 */
public class MerchantSessionUserManager {

	/** 商户管理员登录用户 SESSION KEY */
	public final static String SESSION_USER_KEY = "_MERCHANT_SESSION_USER_";

	/**
	 * @方法功能说明：当断是否登录
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:04:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public static boolean isLogin(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(SESSION_USER_KEY);
		if (obj != null)
			return true;
		return false;
	}
	
	/**
	 * @方法功能说明：登录
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:02:23
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param vo
	 * @return:void
	 * @throws
	 */
	public static void login(HttpServletRequest request, MerchantSessionUserVo vo) {
		request.getSession().setAttribute(SESSION_USER_KEY, vo);
	}

	/**
	 * @方法功能说明：登出
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:02:33
	 * @修改内容：
	 * @参数：@param request
	 * @return:void
	 * @throws
	 */
	public static void logout(HttpServletRequest request) {
		// 销毁session
		request.getSession().removeAttribute(SESSION_USER_KEY);
	}

	/**
	 * @方法功能说明：获得登录用户
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:02:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:MerchantAdminSessionUserVo
	 * @throws
	 */
	public static MerchantSessionUserVo getSessionUser(HttpServletRequest request) {
		return (MerchantSessionUserVo) request.getSession().getAttribute(SESSION_USER_KEY);
	}

	/**
	 * @方法功能说明：得到当前用户ID，如果未登录则为null
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:03:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getSessionUserId(HttpServletRequest request) {
		MerchantSessionUserVo vo = getSessionUser(request);
		if (vo != null)
			return vo.getScenicSpotId();
		return null;
	}
}
