import hg.demo.member.service.spi.impl.fx.ReserveRecordSPIService;
import hg.framework.common.model.LimitQuery;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.spi.qo.ReserveRecordSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ReserveRecordServiceTest {

	@Autowired
	private ReserveRecordSPIService reserveRecordSPIService;
	
	@Test
	public void create(){
//		System.out.println("CREATE_start");
		CreateReserveRecordCommand command = new CreateReserveRecordCommand();
		command.setAuthUserID("f426105586e941ddacb63d2276ae150d");
		command.setCardNo("setCardNo");
		command.setDistributorID("test");
		command.setFromPlatform("setFromPlatform");
		command.setIncrement(123L);
		command.setOrderCode("setOrderCode");
		command.setProdName("setProdName");
		command.setProvePath("setProvePath");
		command.setStatus(456);
		command.setTradeNo("setTradeNo");
		command.setType(3);
//		System.out.println(JSON.toJSONString(reserveRecordSPIService.create(command)));
//		System.out.println("CREATE_end");
	}
	
	@Test
	public void audit(){
//		System.out.println("AUDIT_start");
		AuditReserveRecordCommand auditReserveRecordCommand = new AuditReserveRecordCommand();
		auditReserveRecordCommand.setCheckStatus(AuditReserveRecordCommand.CHECK_STATUS_REFUSE);
		auditReserveRecordCommand.setReserveRecordID("bbd719f227af4809aff05695435215d1");
//		System.out.println(JSON.toJSONString(reserveRecordSPIService.audit(auditReserveRecordCommand)));
//		System.out.println("AUDIT_end");
	}
	
	@Test
	public void queryPagination(){
//		System.out.println("Pagination_start");
		ReserveRecordSQO reserveRecordSQO = new ReserveRecordSQO();
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setByPageNo(true);
		limitQuery.setPageNo(2);
		limitQuery.setPageSize(2);
		reserveRecordSQO.setLimit(limitQuery);
		reserveRecordSQO.setDistributorID("test");
		reserveRecordSPIService.queryReserveRecordPagination(reserveRecordSQO);
//		System.out.println(JSON.toJSONString(reserveRecordSPIService.queryReserveRecordPagination(reserveRecordSQO)));
//		System.out.println("Pagination_end");
	}
	
	@Test
	public void queryList(){
//		System.out.println("List_start");
		ReserveRecordSQO reserveRecordSQO = new ReserveRecordSQO();
		reserveRecordSQO.setDistributorID("test");
//		System.out.println(JSON.toJSONString(reserveRecordSPIService.queryReserveRecordList(reserveRecordSQO)));
//		System.out.println("List_end");
	}
	
	@Test
	public void query(){
//		System.out.println("One_start");
		ReserveRecordSQO reserveRecordSQO = new ReserveRecordSQO();
		reserveRecordSQO.setReserveRecordID("1");
//		System.out.println(JSON.toJSONString(reserveRecordSPIService.queryReserveRecordByID(reserveRecordSQO)));
//		System.out.println("One_end");
	}
	
}
