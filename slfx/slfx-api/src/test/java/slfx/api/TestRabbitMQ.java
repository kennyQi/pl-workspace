//package slfx.api;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
//import slfx.xl.spi.inter.LineService;
//
///**
// * @类功能说明：测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-xl-test.xml" })
//public class TestRabbitMQ {
//	
//	@Autowired
//	private LineService lineService;
//	
//	@Test
//	public void send(){
//		
//		System.out.println("-----------------");
//		
//		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//		apiCommand.setLineId("166943f0564447aaa703db4e398d84e4");
//		apiCommand.setStatus("3");
//		
//		lineService.sendLineUpdateMessage(apiCommand);
//		System.out.println("===================");
//	}
//	
//	@Test
//	public void receive(){
//		
//	}
//	
//}