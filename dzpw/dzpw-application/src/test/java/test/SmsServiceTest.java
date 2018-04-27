package test;

import hg.dzpw.app.service.api.sms.SmsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class SmsServiceTest extends TestCase {
	
	@Autowired
	private SmsService smsService;
	@Test
	public void sendMessage(){
		try {
			smsService.sendSms("13336232541", "发送中");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
