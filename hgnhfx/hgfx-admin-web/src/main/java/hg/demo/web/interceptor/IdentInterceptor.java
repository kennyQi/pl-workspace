package hg.demo.web.interceptor;

import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.SecuritySPI;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.demo.member.common.spi.qo.Security.UserPermSQO;
import hg.demo.web.common.UserInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by admin on 2016/5/27.
 */
public class IdentInterceptor implements HandlerInterceptor {
    @Resource
    private SecuritySPI securityService;
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        UserInfo userInfo=(UserInfo) httpServletRequest.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
        UserPermSQO userPermSQO=new UserPermSQO();
        userPermSQO.setId(userInfo.getUserId());
        Set<String> set= securityService.getuserperm(userPermSQO);
        String url=httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();
        //去除项目名
        url = StringUtils.replace(url, contextPath, "");
        if (!set.contains(url)&&!url.equals("/")) {
            String requesttype = httpServletRequest.getHeader("X-Requested-With");
            if (!StringUtils.isEmpty(requesttype) && requesttype.equals("XMLHttpRequest")) {
                httpServletResponse.sendRedirect("" + httpServletRequest.getContextPath() + "/noauthorized/ajax");
            } else {
                httpServletResponse.sendRedirect("" + httpServletRequest.getContextPath() + "/noauthorized");
            }
            return false;
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
