//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import hg.common.util.UUIDGenerator;
//import hg.log.domainevent.DomainEventDao;
//import hg.log.domainevent.DomainEventQo;
//import hg.log.po.domainevent.DomainEvent;
//import hg.log.repository.DomainEventRepository;
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
//	private DomainEventDao domainEventDao;
//	
//	@Test
//	public void test() {
//		//	model业务方法中接收的参数
////		UserRegisterCommand command = new UserRegisterCommand();
//		String parma = "abc";
//		
//		
//		
//		////////////////////
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
//		///////////////////
//		
//
//		DomainEventQo qo = new DomainEventQo();
//		qo.setModelName("JPOrder");
//		qo.setMethodName("");
//		List<DomainEvent> events = domainEventDao.queryList(qo);
//		System.out.println(JSON.toJSONString(events.get(0)));
//		
//		///////////////////
//		
//		
//		
//		DomainEvent event2 = domainEventRepository.findOne(event.getId());
//		System.out.println(JSON.toJSONString(event2));
//	}
//}
