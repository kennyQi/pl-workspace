//package slfx.jp;
//
//import hg.common.util.DwzJsonResultUtil;
//import hg.common.util.SysProperties;
//
//import org.apache.commons.lang.StringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.dubbo.common.json.JSON;
//
//import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
//import slfx.jp.pojo.dto.order.JPOrderDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
//import slfx.jp.spi.command.admin.ApplyRefundCommand;
//import slfx.jp.spi.command.client.YGApplyRefundCommand;
//import slfx.jp.spi.inter.JPPlatformApplyRefundService;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//import slfx.jp.spi.qo.admin.PlatformOrderQO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class TestApplyAbolishRefund {
//	
//	@Autowired
//	private YGFlightService ygFlightServiceImpl;
//	
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	@Autowired
//	private JPPlatformApplyRefundService jpPlatformApplyRefundService;
//	
//	@Test
//	public void testYGRefundActionTypes(){
//		
//		YGRefundActionTypesDTO dto = ygFlightServiceImpl.getRefundActionType("008");
//		
//		YGRefundActionTypeDTO  typeDto = dto.getActionTypeList().get(0);
//		
//		YGApplyRefundCommand command = new YGApplyRefundCommand();
//		command.setOrderNo("");
//		command.setActionType(typeDto.getActionTypeCode());
//		ygFlightServiceImpl.applyRefund(command);
//		
//		
//	}
//	
//	@Test
//	public void test2(){
//		String slfxOrderNo ="";
//		
//		//查询订单详情
//		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
//		
//		platformOrderQO.setId("");
//		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
//
//		YGApplyRefundCommand command = new YGApplyRefundCommand();
//		command.setTicketNos("");//多个票号使用|隔开
//		command.setRefundType("");//F:废  T：退
//		command.setActionType("");//退费类型
//		command.setNoticeUrl(SysProperties.getInstance().get("http_domain") + "/slfx/api/backOrDiscardTicket/notify");//退废票完成通知地址
//		
//		//处理退废票逻辑
//		if (jpOrderDTO != null) {
//			command.setOrderNo(jpOrderDTO.getYgOrderNo());
//			//供记录日志使用，是平台的订单id。
//			command.setCommandId("");
//			
//			if (StringUtils.isNotBlank(jpOrderDTO.getFlightSnapshotJSON())) {
//				try {
//					SlfxFlightDTO flightDto = JSON.parse(jpOrderDTO.getFlightSnapshotJSON(),SlfxFlightDTO.class);
//					command.setSegment(flightDto.getStartPort()+flightDto.getEndPort());
//				} catch (com.alibaba.dubbo.common.json.ParseException e) {
//				}
//			}
//
//			
//			
//			YGApplyRefundDTO ygApplyRefundDTO = jpPlatformApplyRefundService.applyRefund(command);
//			
//			if (ygApplyRefundDTO != null) {
//				if ("0".equals(ygApplyRefundDTO.getErrorCode())) {
//					//更新平台订单状态
//					ApplyRefundCommand applyRefundCommand = new ApplyRefundCommand();
//					applyRefundCommand.setReason(command.getReason());
//					//applyRefundCommand.setOrderNo(id);
//					applyRefundCommand.setRefundOrderNo(ygApplyRefundDTO.getRefundOrderNo());
//					//applyRefundCommand.setTicketNos(ticketNos);
//					applyRefundCommand.setRefundType(command.getRefundType());						
//					applyRefundCommand.setActionType(command.getActionType());
//					
//					jpPlatformOrderService.adminRefundOrder(applyRefundCommand);
//				}
//			}
//		}
//		
//	}
//}
