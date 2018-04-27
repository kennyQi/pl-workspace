package hgfx.web.util;
 
import com.alibaba.fastjson.JSON;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import hg.fx.util.OpenSms;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信工具
 *
 * @since 0.7.1
 */
public class SmsUtil {

	// 短信发送地址
	private String smsAddress;

	// 短信发送用户名
	private String smsUser;

	// 短信发送密码
	private String smsPassword;
	
	public static final String SMS_PRE汇购 = "【汇购科技】";
	
	public SmsUtil() {
		
	}

	public SmsUtil(String smsAddress, String smsUser, String smsPassword) {
		this.smsAddress = smsAddress;
		this.smsUser = smsUser;
		this.smsPassword = smsPassword;
	}

	/**
	 * <pre>
	 * 短信工具
	 * --------------------------
	 * 0.7.1 > zhurz > 2015-11-09 17:58:14 > 结尾关闭HTTP
	 * 0.7.2 > zhurz > 2015-11-13 18:56:33 > bugfix
	 * </pre>
	 *
	 * @param mobile 手机号
	 * @param msg    消息
	 * @return <table> <tr> <th>返回参数</th> <th>参数名称</th> </tr> <tr> <td>01</td> <td>缺少用户名/密码</td> </tr> <tr> <td>02</td> <td>用户名密码错误</td> </tr> <tr> <td>03</td> <td>非法请求</td> </tr> <tr> <td>succ</td> <td>请求成功</td> </tr> </table>
	 * @throws Exception
	 */
	public String sendSms(String mobile, String msg) throws Exception {

		//POST请求提交
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(smsAddress);
		String result = null;
		try {

			// 防止乱码
			client.getParams().setHttpElementCharset("UTF-8");
			client.getParams().setContentCharset("UTF-8");

			// 平台调用的用户名密码
			post.setParameter("userName", smsUser);
			post.setParameter("pass", smsPassword);

			OpenSms os = new OpenSms();
			// 短信内容 不超过400个字符
			os.setContent(msg);
			// 接受号码
			os.setMobile(mobile);

			List<OpenSms> msgList = new ArrayList<OpenSms>();
			msgList.add(os);
			String jsonArrayString = JSON.toJSONString(msgList);

			post.setParameter("listSms", jsonArrayString);

			// 服务器状态码
			int status = client.executeMethod(post);

			if (status == 200)
				// 调用后返回结果
				result = post.getResponseBodyAsString();

		} finally {
			// 关闭连接
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
		}

		return result;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}

	public String getSmsUser() {
		return smsUser;
	}

	public void setSmsUser(String smsUser) {
		this.smsUser = smsUser;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public static void main(String[] args) {
		SmsUtil sendSms = new SmsUtil("http://sms.hg365.com/hgsms/send", "ittest", "ittest");
		try {
			String result = sendSms.sendSms("18626890576",
					"【票量旅游】尊敬的复核员zhurz，您有交易待批复，请在15点前登录平台的待办事项中查看并批复。");// zhurz
			// String result = sendSms.sendSms("13634153082", "Hello世界");//zhurz
			// String result = sendSms.sendSms("18668432229", "Hello世界");//lixx
			// String result = sendSms.sendSms("13757193676", "Hello世界");//yangk
			// String result = sendSms.sendSms("15336525966", "Hello世界");//zhangqy
//			System.out.println("返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
