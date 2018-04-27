package hg.demo.web.controller;

import com.google.code.kaptcha.Producer;
import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.SecuritySPI;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.demo.member.common.spi.qo.Security.QueryAuthUserSQO;
import hg.demo.web.common.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import logUtil.LogConstants;
import logUtil.logUtil;

/**
 * Created by admin on 2016/5/23.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private Producer captchaProducer;

    @Resource
    private SecuritySPI securityService;

    private static final String LM_SESSION_VALID_CODE_KEY = "_LM_SESSION_VALID_CODE_";

    /**
     * 登录页面
     * @param request
     * @param model
     * @param error
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String loginName){

        String message = "";
        if ("-1".equals(error))
            message = "验证码错误";
        else if (SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME.equals(error)) {
            message = "用户名不存在";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)) {
            message = "用户名或密码错误";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)) {
            message = "用户被锁定";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)) {
            message = "用户异常";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_FREEZE_TIME.equals(error)) {
            message = "多次登录错误,用户处于禁止登录中,稍后再试";
        } else if ("100".equals(error)) {
            message = "请输入用户名和密码";
        }

        model.addAttribute("name", "");
        model.addAttribute("message", message);
        return "/login/login.ftl";
    }

    /**
     * 验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/login/valid-code-image.jpg")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(LM_SESSION_VALID_CODE_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
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
     * 提交登录
     * @param request
     * @param httpSession
     * @param model
     * @param loginName
     * @param password
     * @param validcode
     * @return
     */
    @RequestMapping(value = "/login/loginpost")
    public RedirectView loginpost(HttpServletRequest request, HttpSession httpSession,Model model, @RequestParam(value = "loginName", required = false) String loginName,
                            @RequestParam(value = "password", required = false) String password,
                            @RequestParam(value = "validcode", required = false) String validcode) {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            model.addAttribute("error", "100");
            return new RedirectView("/login", true);
        }
        if (!validCodeCheck(request,validcode)){
            model.addAttribute("error", "-1");
            return new RedirectView("/login", true);
        }
        QueryAuthUserSQO queryAuthUserSQO=new QueryAuthUserSQO();
        queryAuthUserSQO.setLoginName(loginName);
        AuthUser authUser= new AuthUser();
        try {
            authUser = securityService.queryAuthUser(queryAuthUserSQO);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_NULL);
            return new RedirectView("/login", true);
        }
        if (authUser==null){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
            return new RedirectView("/login", true);
        }
        if (!StringUtils.equalsIgnoreCase(authUser.getPasswd(), MD5HashUtil.toMD5(password))){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD);
            return new RedirectView("/login", true);
        }
        if (authUser.getEnable().equals((short)0)){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_LOCK);
            return new RedirectView("/login", true);
        }
        UserInfo userInfo=setUserInfo(authUser);
        httpSession.setAttribute(SecurityConstants.SESSION_USER_KEY,userInfo);
        logUtil.addLog(null, userInfo.getLoginName(), LogConstants.LOG_IN, null);
        return new RedirectView("/index", true);
    }

    /**
     * 退出
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login/loginout")
    public RedirectView loginout(HttpServletRequest request, HttpSession httpSession){
    	UserInfo userInfo= getUserInfo(httpSession);
    	logUtil.addLog(null, userInfo.getLoginName(), LogConstants.LOG_OUT, null);
        httpSession.removeAttribute(SecurityConstants.SESSION_USER_KEY);
        return new RedirectView("/login", true);
    }

    /**
     * 验证码验证
     * @param request
     * @param validCode
     * @return
     */
    protected boolean validCodeCheck(HttpServletRequest request, String validCode){
        if (validCode == null)
            return false;

        String sessionValidCode = (String) request.getSession().getAttribute(LM_SESSION_VALID_CODE_KEY);
        request.getSession().removeAttribute(LM_SESSION_VALID_CODE_KEY);

        if (sessionValidCode == null)
            return false;

        // 匹配验证码
        if (StringUtils.equalsIgnoreCase(validCode, sessionValidCode))
            return true;

        return false;
    }

}
