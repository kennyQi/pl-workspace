import static org.junit.Assert.*;
import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.common.util.UUIDGenerator;
import hg.payment.app.service.local.payorder.HJBPayOrderLocalService;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.command.spi.payorder.hjb.CreateHJBPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-pay-test.xml" })
public class HJBPayOrderServiceTest {
	
	@Autowired
	private HJBPayOrderLocalService hjbPayOrderLocalService;
	@Autowired
	private HJBPayChannel hjbPay;

	
	@Test
	public void createTest(){
		
		PayOrderRequestDTO dto = new PayOrderRequestDTO();
		dto.setPaymentClientId("425078243aae407da092aaf3d4ab255b");
		dto.setPayerClientUserId("1");
		dto.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
		dto.setClientTradeNo(UUIDGenerator.getUUID());
//		dto.setName("张三");
		dto.setIdCardNo("360723199107114111");
		dto.setMobile("15279107876");
		dto.setPayChannelType(3);
		dto.setPayeeAccount("6227002200039940074");
		dto.setPayeeName("票量");
		dto.setAmount(0.02);   
//		dto.setPayerRemark("汇金宝支付测试....");
		
		CreateHJBPayOrderCommand command = new CreateHJBPayOrderCommand();
		command.setPayOrderCreateDTO(dto);
		
		command.setChannelId("LHBANK60");
		command.setMerCode("8888888888");
		command.setPayeeBankAccountName("刘志强");
		command.setPayeeBankType("1051000");
		command.setCustomerType("1");
		command.setCallerIp("192.168.10.91");
		command.setPayerBankType("1031000");
		
		command.setTradeName("交易名称");
		command.setSubject("商品名称");
		command.setDescription("商品描述");
		
		try {
			String request = hjbPayOrderLocalService.createHJBPayOrder(command);
			HttpResponse resp = HttpUtil.reqForPost(hjbPay.getHjbUrl(), request, null);
			assertEquals("请求失败",200,resp.getRespoinsCode());
		} catch (PaymentException e) {
			e.printStackTrace();
			
		}
	}

}
