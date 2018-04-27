//package slfx.jp;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
//import slfx.jp.domain.model.log.JPOrderLog;
//import slfx.jp.pojo.system.OrderLogConstants;
//import slfx.jp.spi.inter.JPOrderLogService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class JPOrderLogServiceTest {
//	@Autowired
//	JPOrderLogService jpOrderLogService;
//	
//	@Test
//	public void TestCreateOrderLog(){
//		//添加操作日志
//		CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
//		logCommand.setWorker(OrderLogConstants.FX_WORKER);
//		logCommand.setJpOrderId("048d2a2c68b54301b8ec2379da4431f5");
//		logCommand.setLogName(OrderLogConstants.CANCEL_LOG_NAME);
//		logCommand.setLogInfo(OrderLogConstants.CANCEL_FX_LOG_INFO);
//		jpOrderLogService.create(logCommand);
//	}
//}
