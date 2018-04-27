import hg.demo.member.service.spi.impl.fx.DistributorRegisterSPIService;
import hg.fx.command.DistributorRegister.AduitDistributorRegisterCommand;
import hg.fx.command.DistributorRegister.CreateDistributorRegisterCommand;
import hg.fx.domain.DistributorRegister;
import hg.fx.spi.qo.DistributorRegisterSQO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DistributorRegistTest {

	@Autowired
	public DistributorRegisterSPIService service;
	@Test
	public void testCreate(){
		for(int i=0;i<10;i++){
		CreateDistributorRegisterCommand command = new CreateDistributorRegisterCommand();
		command.setLoginName("zqq01"+i);
		command.setPasswd("01"+i);
		command.setPhone("1311234560"+i);
		command.setLinkMan("zqq1"+i);
		command.setCompanyName("汇购1"+i);
		DistributorRegister item = service.createDistributorRegister(command);
		System.out.println(item);
	}
	}
	
	//@Test
	public void testExit(){
		//不存在
		System.out.println("#1"+service.checkExistAccount("zqq"));
		//存在申请记录中(待审核)
		System.out.println("#2"+service.checkExistAccount("zqq001"));
		//存在正式正式账户中
		System.out.println("#3"+service.checkExistAccount("test5"));
		//存在申请记录中(已拒绝)
		System.out.println("#4"+service.checkExistAccount("zqq003"));
		//存在申请记录中(通过)
		System.out.println("#5"+service.checkExistAccount("zqq002"));
	}
	
	//@Test
	public void testExit2(){
		//不存在
		System.out.println("#1"+service.checkExistPhone("zqq"));
		//存在申请记录中(待审核)
		System.out.println("#2"+service.checkExistPhone("131123445678"));
		//存在正式正式账户中
		System.out.println("#3"+service.checkExistPhone("18268207061"));
		//存在申请记录中(已拒绝)
		System.out.println("#4"+service.checkExistPhone("131123445675"));
		//存在申请记录中(通过)
		System.out.println("#5"+service.checkExistPhone("131123445679"));
	}
	
	//@Test
	public void testQueryOne(){
		DistributorRegisterSQO qo = new DistributorRegisterSQO();
		qo.setId("8866a872794c473c9e787a408c057a65");
		List<DistributorRegister> item = service.queryList(qo);
	}
	
	@Test
	public void testAduit(){
		AduitDistributorRegisterCommand cmd = new AduitDistributorRegisterCommand();
		List<String> ids = new ArrayList<String>();
		ids.add("8866a872794c473c9e787a408c057a65");
		ids.add("3d59058fcd3e431ab16831399c00fb9d");
		ids.add("fb793a1496b041c5b8349312a60dab84");
		//cmd.setIds(ids);
		cmd.setId("5e614f46d0c54b6da137dc0adcc5c28b");
		cmd.setIsPass(true);
		service.aduitDistributorRegister(cmd);
		System.out.println("##############");
	}
	
}
