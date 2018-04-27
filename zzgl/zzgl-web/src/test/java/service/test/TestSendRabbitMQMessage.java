package service.test;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.message.CouponMessage;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:amqp-producer-test.xml" })
public class TestSendRabbitMQMessage {
	
	@Autowired
	RabbitTemplate template;
	
	/**
	 * @方法功能说明：测试发送消息
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-20下午2:39:32
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void test() {
		//发放卡券：订单满
		CreateCouponCommand cmd=new CreateCouponCommand();
		cmd.setSourceDetail("订单满送");
		cmd.setPayPrice(500.0);
		cmd.setMobile("1863823612");
		cmd.setRealName("sds");
		cmd.setUserId("c1a36bba7ea7487aadbd83fb679cc21e");
		cmd.setEmail("");
		cmd.setLoginName("aaaa");
		CouponMessage baseAmqpMessage=new CouponMessage();
		baseAmqpMessage.setMessageContent(cmd);
		baseAmqpMessage.setType(2);
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		template.convertAndSend("zzpl.order",baseAmqpMessage);
		
		
		//注册时发送卡券功能
//		CouponMessage baseAmqpMessage=new CouponMessage();
//		CreateCouponCommand command=new CreateCouponCommand();
//		command.setLoginName("aaaa");
//		command.setMobile("18638236219");
//		command.setRealName("sdsds");
//		command.setDetail("sdsdsd");
//		command.setEmail("595035525@qq.com");
//		command.setUserId("c1a36bba7ea7487aadbd83fb679cc21e");
//		command.setSourceDetail("注册成功");
//		baseAmqpMessage.setContent(command);
//		baseAmqpMessage.setType(1);
//		baseAmqpMessage.setSendDate(new Date());
//		baseAmqpMessage.setArgs(null);
//		System.out.println("发送消息");
//		template.convertAndSend("hsl.regist",baseAmqpMessage);
	}
	
}
