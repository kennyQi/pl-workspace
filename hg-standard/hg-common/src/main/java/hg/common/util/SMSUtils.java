package hg.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SMSUtils {

	// 短信发送地址
	private String smsAddress;

	// 短信发送用户名
	private String smsUser;

	// 短信发送密码
	private String smsPassword;
	
	public SMSUtils(){
		
	}

	public SMSUtils(String smsAddress, String smsUser, String smsPassword) {
		this.smsAddress = smsAddress;
		this.smsUser = smsUser;
		this.smsPassword = smsPassword;
	}

	public String sendSms(String mobile, String msg) throws Exception {
		
		Map post=new HashMap();
		// 平台调用的用户名密码
		post.put("userName", smsUser);
		post.put("pass", smsPassword);
		OpenSms os = new OpenSms();
		os.setContent(msg);// 短信内容 不超过400个字符
		os.setMobile(mobile);// 接受号码
		List<OpenSms> list = new ArrayList<OpenSms>();
		list.add(os);
		String jsonArrayString = JSON.toJSONString(list);
		post.put("listSms", jsonArrayString);
		return WebServiceUtil.callWebServicePost(smsAddress, post);
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}
	public void setSmsUser(String smsUser) {
		this.smsUser = smsUser;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public static void main(String[] args) {
		SMSUtils sendSms = new SMSUtils();
		sendSms.smsUser="ittest";
		sendSms.smsPassword="ittest";
		sendSms.smsAddress="http://sms.hg365.com/hgsms/send";
		try {
			String result = sendSms.sendSms("15305153869",
					"尊敬的复核员zhurz，您有交易待批复，请在15点前登录平台的待办事项中查看并批复。");// zhurz
			// String result = sendSms.sendSms("13634153082", "Hello世界");//zhurz
			// String result = sendSms.sendSms("18668432229", "Hello世界");//lixx
			// String result = sendSms.sendSms("13757193676", "Hello世界");//yangk
			// String result = sendSms.sendSms("15336525966",
			// "Hello世界");//zhangqy
			System.out.println("返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
