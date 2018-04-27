/*package slfx.jp;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slfx.jp.app.service.local.FlightPolicyLocalService;
import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.spi.qo.admin.policy.PolicySnapshotQO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
public class TestPolicySnapshotLocalService {
	
	@Resource
	private FlightPolicyLocalService flightPolicyLocalService;	

	@Test
	public void test(){
		//查询比价快照
		PolicySnapshotQO policySnapshotQo = new PolicySnapshotQO();
		//政策快照表id
		policySnapshotQo.setId("d41723bc88084a42ab5e6345544ebdd4");
		try{
			JPPlatformPolicySnapshot jpPlatformPolicySnapshot = flightPolicyLocalService.queryUnique(policySnapshotQo);
		System.out.println("============"+jpPlatformPolicySnapshot.getCommRate());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		//查询比价快照
		JPPlatformPolicySnapshot model = new JPPlatformPolicySnapshot();
		
		Double d =123.00;
		model.setCommRate(d);
		System.out.println("model.getCommRate()====="+model.getCommRate());
	}
	
	
}
*/