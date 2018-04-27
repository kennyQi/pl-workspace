package hsl.api;

import hg.common.util.SMSUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class TestJPOrderLocalService {
	
	@Autowired
	private SMSUtils smsUtils;

	@Test
	public void testSMS(){
		try {
			String result = smsUtils.sendSms("18646292336",
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
