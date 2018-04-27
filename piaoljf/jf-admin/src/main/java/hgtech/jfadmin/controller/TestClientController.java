package hgtech.jfadmin.controller;


import hgtech.hjfclient.baseimpl.test.BasingExchangeImpl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hg.hjf.exchange.entity.BindUserDto;
import com.hg.hjf.exchange.entity.JFQueryDto;
import com.hg.hjf.exchange.entity.SendSmsDto;
import com.hg.hjf.exchange.entity.TransferDto;
import com.hg.hjf.exchange.entity.ValidateSmsCodeDto;
import com.hg.hjf.exchange.util.ExchangeUtil;

/**
 * 测试模拟商户端
 * @author zhangqq
 *
 */
@Controller
@RequestMapping(value = "/testClient")
public class TestClientController {

	
    //模拟用户
    static Map<String , String>  users ;
    
    public ExchangeUtil exchangeUtil; 
    
    public BasingExchangeImpl basingExchange;
    
    private String passK = "toutou1";
    
    public TestClientController(){
    	exchangeUtil = new ExchangeUtil(); 
    	basingExchange = new BasingExchangeImpl();
    }
    
    
    /**
     * 模拟商户验证用户并发送验证码
     * @param request
     * @param response
     * @param model
     * @param dto
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendSms")
    public String sendSms(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute SendSmsDto dto) throws Exception {
    	String smsMsg="短信发送的内容";
    	//调用hjfClient的sendUserVlidCode方法完成 验证用户是否存在，发送验证码
    	String result = exchangeUtil.sendUserVlidCode(dto, basingExchange, smsMsg, passK);
		return result;
    }
    
    /**
     * 模拟商户查询用户积分
     * @param request
     * @param response
     * @param model
     * @param dto
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryJF")
    public String queryJF(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute JFQueryDto dto) throws Exception {
    	//调用hjfClient的sendUserVlidCode方法完成 验证用户是否存在，发送验证码
    	String result = exchangeUtil.findJF(dto,basingExchange,passK);
    	return result;
    }
    /**
     * 模拟商户验证 手机验证码
     * @param request
     * @param response
     * @param model
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkSms")
    public String checkSms(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute ValidateSmsCodeDto dto) {
    	// 验证 手机验证码
    	String result = exchangeUtil.checkUserVlidCode(dto, basingExchange,passK);
		return result;
    }
    
    /**
     * 模拟商户绑定用户
     * @param request
     * @param response
     * @param model
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindUser")
    public String bindUser(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute BindUserDto dto) {
    	//
    	String result = exchangeUtil.bindUser(dto,basingExchange,passK);
		return result;
    }
    /**
     * 从商户转到汇积分
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/tohjf")
	public String tohjf(HttpServletRequest request,
			HttpServletResponse response , TransferDto dto) throws Exception, IOException {
		JSONObject js = new JSONObject();
		return exchangeUtil.payToHJF(dto, basingExchange,passK);
	}
    
    /**
     * 从汇积分转入商户
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping("/fromhjf")
	public String fromhjf(HttpServletRequest request,
			HttpServletResponse response, TransferDto dto) throws Exception, IOException {
    	JSONObject js = new JSONObject();
		//校验连接
		return exchangeUtil.addFromHjf(dto, basingExchange,passK);
	}
}
