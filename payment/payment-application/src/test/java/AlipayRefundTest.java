import hg.payment.app.service.local.refund.AlipayRefundLocalService;
import hg.payment.domain.common.util.RefundUtils;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.pojo.command.admin.ModifyAlipayRefundCommand;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;
import hg.payment.pojo.command.dto.AlipayRefundRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.AlipayRefundCommand;
import hg.payment.pojo.exception.PaymentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-pay-test.xml" })
public class AlipayRefundTest {
	
	@Autowired
	private AlipayPayChannel alipay;
	@Autowired
	private RefundUtils  refundUtils;
	@Autowired
	private AlipayRefundLocalService  alipayRefundLocalService;
	
//	@Test
	public void refundNotifyTest(){
		
		String batchNo = "20141128000000000073";
		String result_details = "2014111200001000820035611523^0.01^TRADE_HAS_CLOSED$zjhg_jiesuan@163.com^2088701074577516^0.00^SUCCESS";
		
		List<ModifyRefundDTO> dtoList = new ArrayList<ModifyRefundDTO>();
		dtoList = refundUtils.paraseRefundNotify(result_details);
		ModifyAlipayRefundCommand command = new ModifyAlipayRefundCommand();
		command.setRefundBatchNo(batchNo);
		command.setModifyRefundDTOList(dtoList);
		try {
			alipayRefundLocalService.refund(command);
			alipayRefundLocalService.notifyClientRefundList(batchNo);
		} catch (PaymentException e) {
			e.printStackTrace();
		}
		
		
				
	}
	
	@Test
	public void buildRefundRequestTest(){
		
		
		AlipayRefundCommand command = new AlipayRefundCommand();
		command.setKeys("gfu3gpkuiam1idh45do66ay0se4a83jv");
		command.setPartner("2088701074577516");
		
		List<AlipayRefundRequestDTO> alipayRefundRequestDTOList = new ArrayList<AlipayRefundRequestDTO>();
		AlipayRefundRequestDTO dto = new AlipayRefundRequestDTO();
		dto.setAmount(0.01);
//		dto.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
		dto.setPaymentClientId("425078243aae407da092aaf3d4ab255b");
		dto.setRefundMessage("协商退款^#");
		dto.setTradeNo("4f00f7e63c384ba995da355ad3b9cca1");
		alipayRefundRequestDTOList.add(dto);
		
		
		command.setAlipayRefundRequestDTOList(alipayRefundRequestDTOList);
		
		try {
			alipayRefundLocalService.submitAlipayBatchRefundRequest(command);
		} catch (PaymentException e) {
			e.printStackTrace();
		}
		
	}

}
