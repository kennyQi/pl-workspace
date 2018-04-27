package hgtech.hjfclient.baseimpl.test;

import hg.common.util.SMSUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hg.hjf.exchange.base.BasingExchangeInterface;
import com.hg.hjf.exchange.entity.BindUserDto;
import com.hg.hjf.exchange.entity.JFQueryDto;
import com.hg.hjf.exchange.entity.SendSmsDto;
import com.hg.hjf.exchange.entity.ValidateSmsCodeDto;

public class BasingExchangeImpl implements BasingExchangeInterface {

	private static final int INT_30MIN = 30 * 60000;
    // 暂时先用map存短消息  消息的清除 模拟商户的短信收发平台
    private HashMap<String, String> smsMap = new HashMap<>();
    
  //模拟用户
    static Map<String , String>  users ;
	public BasingExchangeImpl(){
		//模拟存在的用户
    	users = new HashMap<String, String>();
    	users.put("13011112222", "13011112222");
    	users.put("13011113333", "13011113333");
    	users.put("13011111111", "13011111111");
    	users.put("13011114444", "13011114444");
	}
	
	
	@Override
	public String sendSms(String msg, SendSmsDto dto) {
		String result = "";
		try {
		String code = "" + (int) (Math.random() * 10000);
		// 模拟生成验证码并保存
		dto.setCode( code);
		dto.setCreateTime(new Date( System.currentTimeMillis()));
				smsMap.put(dto.getOrderId(), code);
		System.out.println("用户"+dto.getUser()+"验证码为"+code);
		} catch (Exception e) {
			result = e.toString();
		}	
		return result;
	}

	@Override
	public JSONObject valiSms(ValidateSmsCodeDto checkUserDto) {
		JSONObject js = new JSONObject();
		String checkSms = smsMap.get(checkUserDto.getOrderId());
		if(checkSms!=null){
	        	boolean status = checkUserDto.getCode().equals(checkSms);
	        	js.put("status", status);
	        	js.put("text", status ? "ok" : "验证码不对或超过30分钟");
		}else{
	        	js.put("status", false);
	        	js.put("text",   "验证码不对或超过30分钟");
		    
		}
		return js;
	}

	@Override
	public boolean checkUserPhoneValid(String user,String phone) {
		boolean contains = phone.equals(users.get(user));
		if(!contains){
			System.out.println("该用户 与集合中的用户手机列表不符");
		}
		return contains;
	}

	@Override
	public void payToHJF() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFromHJF() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindUser(BindUserDto dto) {
		// TODO Auto-generated method stub

	}
	
	public  String findJF(JFQueryDto jfQueryDto){
		JSONObject js = new JSONObject();
	        //模拟商户查询积分
			String jf="3000";
			js.put("status", true);
	        js.put("text", jf);
		return js.toJSONString();
	}

}
