//package hsl.api;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import hg.common.util.SMSUtils;
//import hg.common.util.SysProperties;
//import hsl.app.dao.JPPassangerDao;
//import hsl.app.service.local.jp.JPOrderLocalService;
//import hsl.domain.model.jp.JPOrder;
//import hsl.domain.model.jp.JPPassanger;
//import hsl.domain.model.jp.JPTicket;
//import hsl.pojo.dto.jp.JPOrderDTO;
//import hsl.pojo.qo.jp.HslJPOrderQO;
//import hsl.spi.command.JPOrderCommand;
//import hsl.spi.common.JPOrderStatus;
//import hsl.spi.inter.api.jp.JPService;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.util.HttpUtil;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
//public class TestJPOrderLocalService {
//	
//	@Resource
//	private JPOrderLocalService jpOrderLocalService;
//
//	@Resource
//	private JPService jpService;
//	@Resource
//	private SMSUtils smsUtils;
//
//	@Test
//	public void test(){
//		/*HslJPOrderQO qo = new HslJPOrderQO();
//		qo.setDealerOrderCode("A902153122000000");
//		System.out.println("11111111111111111111111");
//		JPOrder jpOrder = jpOrderLocalService.queryUnique(qo);
//		System.out.println("2222222222222222222222222");
//		jpOrder.setStatus(Integer.parseInt(JPOrderStatus.ALREADY_PAY));
//		
//		jpOrderLocalService.update(jpOrder);
//		System.out.println("------------"+JSON.toJSON(jpOrder.getId()));*/
//		
////		SMSUtils smsUtils=new SMSUtils("http://sms.hjb365.com/hgsms/send","ittest","ittest");
//		try {
//			smsUtils.sendSms("15088668720", "【汇商旅】您正在使用本手机号码修改密码，您获取到的验证密码为8720。http://www.baidu.com/。客服电话4000-186-566。");
//		} catch (Exception e) {
//		}
//	}
//	@Test
//	public void testQueryJPOrder(){
//		HslJPOrderQO qo = new HslJPOrderQO();
//		qo.setDealerOrderCode("A902172830000000");
//		
//		List<JPOrderDTO> jpOrderDTOs = jpService.queryOrder(qo);
//		
//		for (JPOrderDTO jpOrderDTO : jpOrderDTOs) {
//			System.out.println("============"+JSONObject.toJSON(jpOrderDTO));
//		}
//	}
//	
//	@Test
//	public void testQueryOrUpdateJPOrder(){
//		jpOrderLocalService.testQueryOrUpdateJPOrder();
//	}
//	
//	@Test
//	public void testQueryOrUpdateJPOrder2(){
//		//同步给商城
//		String notifyUrl = "http://192.168.10.101:8081/hsl-api/api/ticket/notify";
//		JPOrderCommand command = new JPOrderCommand();
//		command.setDealerOrderCode("A902172830000000");
//		command.setTktNo(new String[]{"7312391540890"});
//		//商城通知地址
//		command.setNotifyUrl(notifyUrl);
//		command.setFlag("Y");
//		command.setStatus(22);
//		
//		HttpUtil.reqForPost(notifyUrl,"msg=" + JSON.toJSONString(command), 60000);
//	}
//		
//		
//
//}
