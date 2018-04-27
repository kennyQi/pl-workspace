package hg.dzpw.dealer.admin.component.manager;

import hg.dzpw.app.pojo.vo.DealerSessionUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @类功能说明：经销商管理员登录会话管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-4下午5:06:11
 * @版本：
 */
public class DealerSessionUserManager {
	/** 商户管理员登录用户 SESSION KEY */
	public final static String SESSION_USER_KEY = "_DEALER_SESSION_USER_";

	public static boolean isLogin(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(SESSION_USER_KEY);
		if (obj != null)
			return true;
		return false;
	}
	
	public static void login(HttpServletRequest request, DealerSessionUserVo vo) {
		request.getSession().setAttribute(SESSION_USER_KEY, vo);
	}

	public static void logout(HttpServletRequest request) {
		// 销毁session
		request.getSession().removeAttribute(SESSION_USER_KEY);
	}

	public static DealerSessionUserVo getSessionUser(HttpServletRequest request) {
		return (DealerSessionUserVo) request.getSession().getAttribute(SESSION_USER_KEY);
	}

	public static String getSessionUserId(HttpServletRequest request) {
		DealerSessionUserVo vo = getSessionUser(request);
		if (vo != null)
			return vo.getDealerId();
		return null;
	}
}
