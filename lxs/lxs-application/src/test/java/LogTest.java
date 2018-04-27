//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import hg.common.util.UUIDGenerator;
//import hg.log.po.domainevent.DomainEvent;
//import hg.log.repository.DomainEventRepository;
//import hg.log.repository.LogRepository;
//import hg.log.util.HgLogger;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext-log-test.xml" })
//public class LogTest {
//	
//	@Autowired
//	private DomainEventRepository domainEventRepository;
//	
//	@Autowired
//	private HgLogger logger;
//	
//	@Test
//	public void test() {
//		//	model业务方法中接收的参数
////		UserRegisterCommand command = new UserRegisterCommand();
//		String parma = "abc";
//		DomainEvent event = new DomainEvent();
//		event.setId(UUIDGenerator.getUUID());
//		event.setCreateDate(new Date());
//		event.setProjectId("汇商旅");
//		event.setEnvId("测试");
//		event.setModelName("hg.log.po.domainevent.DomainEvent");
//		event.setMethodName("register");
//		
//		List<String> params = new ArrayList<String>();
//		params.add(JSON.toJSONString(parma));
//		
//		event.setParams(params);
//		domainEventRepository.save(event);
//		
//		DomainEvent event2 = domainEventRepository.findOne(event.getId());
//		System.out.println(JSON.toJSONString(event2));
//	}
//	
//	@Test
//	public void testLog() {
//		logger.debug("lixx", "no");
//		
// 		logger.debug(LogTest.class, "yuxx", "ok", null, "下单", "登陆");
//	}
//}
