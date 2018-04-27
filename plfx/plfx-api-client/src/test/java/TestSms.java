import hg.common.util.SMSUtils;

import org.junit.Test;

public class TestSms {

	@Test
	public void TestSmss(){
		String smsAddress = "http://sms.hg365.com/hgsms/send";
		String smsUser = "ittest";
		String smsPassword = "ittest";
		SMSUtils smsUtils = new SMSUtils(smsAddress, smsUser, smsPassword);
		String mobile = "13754326300";
		String msg = "【汇购科技】验证短信,验证码123456";
		try {
			String result = smsUtils.sendSms(mobile, msg);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
