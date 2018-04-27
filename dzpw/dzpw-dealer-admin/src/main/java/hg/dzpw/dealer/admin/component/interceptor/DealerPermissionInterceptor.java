package hg.dzpw.dealer.admin.component.interceptor;

import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.app.common.util.DealerAuthUtils;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @类功能说明：经销商权限检查器
 * @类修改者：
 * @修改日期：2015-2-11上午10:07:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:07:13
 */
public class DealerPermissionInterceptor implements HandlerInterceptor {

	@Autowired
	private DealerLocalService dealerLocalService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		DealerAuthUtils.setCurrentSessionUserVo(DealerSessionUserManager
				.getSessionUser(request));
		
		// 登录检查
		if (!DealerSessionUserManager.isLogin(request)) {
			if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))
					|| request.getParameter("ajax") != null) {
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print(DwzJsonResultUtil.createJsonString("301", "登陆超时！请重新登陆！", null, null, null,
						request.getContextPath() + "/login", null));
			} else {
				response.sendRedirect(response.encodeRedirectURL(request
						.getContextPath() + "/login"));
			}
			return false;
		}
		
		DealerSessionUserVo vo = (DealerSessionUserVo)request.getSession().getAttribute(DealerSessionUserManager.SESSION_USER_KEY);
		DealerQo qo=new DealerQo();
		qo.setAdminLoginName(vo.getLoginName());
		qo.setAdminLoginNameLike(false);
		qo.setRemoved(null);
		Dealer dealer = dealerLocalService.queryUnique(qo);
		//景区是否被禁用
		if(dealer.getBaseInfo().getStatus()==0 || dealer.getBaseInfo().getRemoved()){
			
			request.getSession().removeAttribute(DealerSessionUserManager.SESSION_USER_KEY);
			request.getSession().setAttribute("msg", "用户被禁用或删除");
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath() + "/login"));
			
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		DealerAuthUtils.setCurrentSessionUserVo(DealerSessionUserManager
				.getSessionUser(request));
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
