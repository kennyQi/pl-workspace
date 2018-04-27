//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.mp.app.component.job.ScenicSpotUpdateJob;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-mp-test.xml" })
//public class JUnitTestRunJob {
//	
//	@Autowired
//	ScenicSpotUpdateJob job;
//
//	@Test
//	public void test() {
//		job.updateScenicSpot();
//	}
//	
//	public static void main(String[] args) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-slfx-mp-test.xml");
//		ScenicSpotUpdateJob job = ctx.getBean(ScenicSpotUpdateJob.class);
//		job.updateScenicSpot();
//	}
//
//}
