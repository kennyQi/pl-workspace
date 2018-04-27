import hg.dzpw.test.TestDaoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRun {
	
	@Autowired
	TestDaoService service;
	
//	@Test
	public void hello(){
		System.out.println("hello world");
	}
	
	@Test
	public void test() {
		System.out.println("--------->>"+service);
		service.test();
	}
	
	public static void main(String[] args) {
		
	}
}
