package hgfx.web.controller.sys;

import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.SecuritySPI;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.DistributorUserSQO;

import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.code.kaptcha.Producer;

/**
 * Created by admin on 2016/5/23.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private Producer captchaProducer;

    @Resource
    private SecuritySPI securityService;
    
    @Autowired
	private JedisPool jedisPool;
    
    @Autowired
    private DistributorUserSPI distributorUserSPIService;

    private static final String LM_SESSION_VALID_CODE_KEY = "_LM_SESSION_VALID_CODE_";
    
    private static final String LOGIN_ERR = "_LOGIN_ERR_";

    /**
     * 
     * @方法功能说明：欢迎页
     * @修改时间：2016年6月7日下午7:10:30
     * @修改内容：
     * @参数：@param request
     * @参数：@param response
     * @参数：@return
     * @return:String
     * @throws
     */
    @RequestMapping(value = "/campaign")
    public String campaign(HttpServletRequest request,HttpServletResponse response){
    	return "campaign/campaign.html";
    }
    
    @RequestMapping(value = "/campaign/service/product/page")
    public String toServiceProductPage(){
    	return "campaign/serviceForProduct.html";
    }
    
    @RequestMapping(value = "/campaign/cooperationflow")
    public String toCooperationflowPage(){
    	return "campaign/cooperationFlow.html";
    }
    
    @RequestMapping(value = "/campaign/contactUs")
    public String toContactUsPage(){
    	return "campaign/contactUs.html";
    }
    
    /**
     * 登录页面
     * @param request
     * @param model
     * @param error
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model, @RequestParam(required = false) String error
    		, @RequestParam(required = false) String loginName){

        String message = "";
        if ("-1".equals(error))
        	message = "-1";
        else if("-2".equals(error))
        	message = "-2";
//            message = "验证码错误";
        else if (SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)) {
            message = "账号或密码错误";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)) {
            message = "账号被禁用";
        } else if (SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)) {
            message = "用户异常";
        }  else if ("100".equals(error)) {
            message = "请输入密码和用户名 ";
        }

        model.addAttribute("name", "");
        model.addAttribute("message", message);
        //错误三次，要验证码

        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
        {
        for(Cookie cookie:cookies)
        {
        	if("rememberAccount".equals(cookie.getName()))
        	{
        		model.addAttribute("rememberAccount", 1); //勾选
        		model.addAttribute("account", cookie.getValue());
        	}
        }
    }
        Integer errorNum = getErrorNum(loginName);
        if(errorNum!=null&&errorNum>=3)
        {
        	model.addAttribute("needValidate", 1);
        }
        return "/login/login.html";
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
    public RedirectView loginpost(HttpServletRequest request,Model model, @RequestParam(value = "loginName", required = false) String loginName,
                            @RequestParam(value = "password", required = false) String password,
                            @RequestParam(value = "validcode", required = false) String validcode,
                            Integer rememberAccount,HttpServletResponse response) {
               if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            model.addAttribute("error", "100");
            return new RedirectView("/login", true);
        }
        Integer errorNum = getErrorNum(loginName);
        if(errorNum>=3)
        {
        if (!validCodeCheck(request,validcode)){
        	if(StringUtils.isBlank(validcode))
        		 model.addAttribute("error", "-2"); //请输入验证码
        	else
        		model.addAttribute("error", "-1");  //验证码错误
            model.addAttribute("loginName", loginName);
            return new RedirectView("/login", true);
        }
        }
        DistributorUserSQO sqo = new DistributorUserSQO();
        sqo.setAccount(loginName);
        sqo.setEqAccount(true);
        sqo.setUserRemoved(false);
        DistributorUser user = null;
        try {
        	user = distributorUserSPIService.queryUnique(sqo);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_NULL);
            return new RedirectView("/login", true);
        }
        if (user==null){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD);
           setErrorNum(loginName);
           model.addAttribute("loginName", loginName);
            return new RedirectView("/login", true);
        }
        if (!StringUtils.equalsIgnoreCase(user.getPasswd(), MD5HashUtil.toMD5(password))){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD);
            setErrorNum(loginName);
            model.addAttribute("loginName", loginName);
            return new RedirectView("/login", true);
        }
        if (user.getDistributor().getStatus().equals(Distributor.STATUS_OF_DISABLE)){
            model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_LOCK);
            return new RedirectView("/login", true);
        }
        //刷新会话标识
        //备份session数据
        HttpSession httpSession = request.getSession();
        Map<String,Object> map = new HashMap<String, Object>();
        Enumeration<String> names = httpSession.getAttributeNames();
        while(names.hasMoreElements())
        {
        	String name = names.nextElement();
        	map.put(name, httpSession.getAttribute(name));
        }
        httpSession.invalidate(); //清空session
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0)
        {
        	for(Cookie cookie:cookies)
        	{
        		cookie.setValue(null);
        		cookie.setMaxAge(0); //cookie过期
        	}
        }
        
        httpSession = request.getSession(); 
        Set<String> keys = map.keySet();
        for(String key:keys)
        {
        	httpSession.setAttribute(key, map.get(key));
        }
        
        httpSession.setAttribute(SecurityConstants.SESSION_USER_KEY,user);
        clearErrNum(loginName);
        //记住账号
        Cookie cookie = new Cookie("rememberAccount",loginName);
        if(rememberAccount!=null&&rememberAccount==1)
        cookie.setMaxAge(3600);
        else
        cookie.setMaxAge(0);//马上过期，清除
        cookie.setPath("/");
        response.addCookie(cookie);
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
    
    /**
     * 登录错误次数
     * @author Caihuan
     * @date   2016年6月5日
     */
    public Integer setErrorNum(String loginName)
    {
    	Integer num = 0;
    	Jedis redis = jedisPool.getResource();
    	try
    	{
    	String strNum = redis.get(LOGIN_ERR+loginName);
    	if(strNum==null)
    	{
    		num = 0;
    	}
    	else
    	{
    		num = Integer.parseInt(strNum);
    	}
    	num++;
    	redis.set(LOGIN_ERR+loginName, String.valueOf(num));
    	redis.expire(LOGIN_ERR+loginName, 60*60*24);//设置一天后过期
    	}catch(Exception e)
    	{
    	}
    	finally{
    		redis.close();
    	}
    	return num;
    }
    
    public Integer getErrorNum(String loginName)
    {
    	Jedis redis = jedisPool.getResource();
    	try
    	{
    	String strNum = redis.get(LOGIN_ERR+loginName);
    	if(strNum!=null)
    	{
    		return Integer.parseInt(strNum);
    	}
    	}
    	catch(Exception e)
    	{}
    	finally{
    		redis.close();
    	}
    	return 0;
    }
    
    //清空错误次数
    public void clearErrNum(String loginName)
    {
    	Jedis redis = jedisPool.getResource();
    	try{
    	redis.del(LOGIN_ERR+loginName);
    	}catch(Exception e)
    	{}
    	finally{
    		redis.close();
    	}
    	
    }

}
