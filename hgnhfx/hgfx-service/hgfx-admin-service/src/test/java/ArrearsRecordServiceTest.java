import hg.demo.member.service.spi.impl.fx.ArrearsRecordSPIService;
import hg.framework.common.model.LimitQuery;
import hg.fx.command.arrearsRecord.AuditArrearsRecordCommand;
import hg.fx.command.arrearsRecord.CreateArrearsRecordCommand;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.spi.qo.ArrearsRecordSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ArrearsRecordServiceTest {

	@Autowired
	private ArrearsRecordSPIService arrearsRecordSPIService;
	
	@Test
	public void create(){
//		System.out.println("CREATE_start");
		CreateArrearsRecordCommand command = new CreateArrearsRecordCommand();
		command.setApplyArrears(123);
		command.setAuthUserID("f426105586e941ddacb63d2276ae150d");
		command.setDistributorID("test");
		command.setFromPlatform("setFromPlatform");
		command.setPreArrears(456);
		command.setReason("setReason");
//		System.out.println(JSON.toJSONString(arrearsRecordSPIService.create(command)));
//		System.out.println("CREATE_end");
	}
	
	@Test
	public void audit(){
//		System.out.println("AUDIT_start");
		AuditArrearsRecordCommand aduitArrearsRecordCommand = new AuditArrearsRecordCommand();
		aduitArrearsRecordCommand.setCheckStatus(AuditReserveRecordCommand.CHECK_STATUS_REFUSE);
		aduitArrearsRecordCommand.setArrearsRecordID("4eb7f517726c47199ec2ec2318b3642d");
//		System.out.println(JSON.toJSONString(arrearsRecordSPIService.audit(aduitArrearsRecordCommand)));
//		System.out.println("AUDIT_end");
	}
	
	@Test
	public void queryPagination(){
//		System.out.println("Pagination_start");
		ArrearsRecordSQO arrearsRecordSQO = new ArrearsRecordSQO();
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setByPageNo(true);
		limitQuery.setPageNo(2);
		limitQuery.setPageSize(2);
		arrearsRecordSQO.setLimit(limitQuery);
		arrearsRecordSQO.setDistributorID("test");
//		System.out.println(JSON.toJSONString(arrearsRecordSPIService.queryArrearsRecordPagination(arrearsRecordSQO)));
//		System.out.println("Pagination_end");
	}
	
	@Test
	public void queryList(){
//		System.out.println("List_start");
		ArrearsRecordSQO arrearsRecordSQO = new ArrearsRecordSQO();
		arrearsRecordSQO.setDistributorID("test");
//		System.out.println(JSON.toJSONString(arrearsRecordSPIService.queryArrearsRecordList(arrearsRecordSQO)));
//		System.out.println("List_end");
	}
	
	@Test
	public void query(){
//		System.out.println("One_start");
		ArrearsRecordSQO arrearsRecordSQO = new ArrearsRecordSQO();
		arrearsRecordSQO.setArrearsRecordID("4eb7f517726c47199ec2ec2318b3642d");
//		System.out.println(JSON.toJSONString(arrearsRecordSPIService.queryArrearsRecordByID(arrearsRecordSQO)));
//		System.out.println("One_end");
	}
	
}
