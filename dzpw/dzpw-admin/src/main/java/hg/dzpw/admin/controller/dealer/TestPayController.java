//package hg.dzpw.admin.controller.dealer;
//
//import hg.dzpw.app.service.api.hjb.HJBPayService;
//import hg.dzpw.app.service.local.HJBTransferRecordLocalService;
//import hg.dzpw.domain.model.pay.HJBTransferRecord;
//import hg.dzpw.pojo.api.hjb.HJBTransferRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;
//import hg.dzpw.pojo.command.pay.DealerTransferToDzpwCommand;
//import hg.dzpw.pojo.common.HjbPayServiceConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class TestPayController {
//	
//	@Autowired
//	private HJBPayService hjbPayService;
//	
//	@Autowired
//	private HJBTransferRecordLocalService hjbTransferRecordLocalService;
//	
//	@Autowired
//	private HjbPayServiceConfig config;
//	
//	@RequestMapping("/testPay")
//	public void a(){
//		
//		HJBTransferRequestDto request = new HJBTransferRequestDto();
//		request.setMerchantId(config.getMerchantId());
//		request.setOriginalOrderNo("0000000000003");//等同于流水号
//		request.setPayCstNo(config.getDzpwCstNo());
//		request.setRcvCstNo("C100001183");//收款CST_NO 
//		request.setTrxAmount("100");
////		request.setUserId(config.getDzpwUserId());//电子票务付款方  不用设userId
//		request.setVersion(config.getVersion());
//		
//		//记录请求
//		HJBTransferRecord record = hjbTransferRecordLocalService.recordRequest(request, HJBTransferRecord.TYPE_DZPW_TO_SCENICSPOT, "0000000000003");
//		
//		HJBTransferResponseDto respDto = hjbPayService.signTransfer(request);
//		
//		
//	}
//	
//}
