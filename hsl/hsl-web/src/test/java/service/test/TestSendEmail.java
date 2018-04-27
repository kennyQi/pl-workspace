//package service.test;
//import hg.system.common.util.MailUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class TestSendEmail {
//	/**
//	 * 测试发送邮件
//	 * @throws MPException 
//	 */
//	@Test
//	public void testSendMail(){
//		String mailHost="mail.ply365.com";
//		String mailAccount="pl-01@ply365.com";
//		String mailPwd="?12345";
//		String title="票量-请激活您的票量账号";
//		String content="尊敬的XXXXXX：欢迎注册票量!	为了保证你正常使用票量提供的「机票预订」「门票预订」等功能，请激活账号。"
//				+ "点击下面的链接进行激活服务，如不能正常打开，请复制到浏览器地址栏中打开：Xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx票量时间 ";
//		try {
//			MailUtil.getInstance().sendMail1(mailHost,mailAccount,mailPwd, title,content, mailAccount,"595035525@qq.com");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
