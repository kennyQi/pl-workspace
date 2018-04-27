import javax.annotation.PostConstruct;

import hg.common.component.RedisLock;
import hg.dzpw.app.service.local.TicketPolicyLocalService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRun3 {
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private TicketPolicyLocalService ticketPolicyService;
	
	private RedisLock lock;
	
	@PostConstruct
	private void init(){
		lock = new RedisLock("");
	}

	@Test
	public void test(){
		lock.tryLock();
	}
	
	@Test
	public void test2(){
		ticketPolicyService.updateTicketPolicyStatus();
	}
	
}
