package service.test;

import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.message.CouponMessage;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestSendMessage {

	@Resource
	private RabbitTemplate amqpTemplate;
	@Test
	public void testSendMsg(){
		CreateCouponCommand cmd = new CreateCouponCommand();
		cmd.setSourceDetail("订单满送");
		cmd.setPayPrice(11d);
		cmd.setMobile("123456");
		cmd.setUserId("555555");
		cmd.setLoginName("sssss");
		CouponMessage baseAmqpMessage = new CouponMessage();
		baseAmqpMessage.setMessageContent(cmd);
		int type = 2;
		baseAmqpMessage.setType(type);
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);

		System.out.println("testSendMsg------------>>");
		amqpTemplate.convertAndSend("zzpl.order", baseAmqpMessage);
		System.out.println("testSendMsg------------<<");
		
	}
	
}
