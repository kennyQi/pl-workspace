package hg.payment.app.controller.test;

import java.io.IOException;

import hg.common.model.HttpResponse;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.HttpUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.payment.app.controller.BaseController;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.pojo.command.spi.payorder.hjb.CreateHJBPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.spi.inter.PayOrderSpiService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping(value = "/hjborder")
public class HJBPayOrderController extends BaseController{
	
	@Autowired
	private PayOrderSpiService payOrderSpiService;
	@Autowired
	private HJBPayChannel hjbPay;

	@RequestMapping("/testorder")
	public String toCreateHjbPayOrder(HttpServletRequest request, HttpServletResponse response){
		return "/test/hjb_test.html";
	}
	
	@RequestMapping("/order")
	@ResponseBody
	public String createHjbPayOrder(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CreateHJBPayOrderCommand command){
		
		
//		PayOrderRequestDTO dto = new PayOrderRequestDTO();
//		dto.setPaymentClientId("425078243aae407da092aaf3d4ab255b");
//		dto.setPayerClientUserId("1");
//		dto.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
//		dto.setClientTradeNo(UUIDGenerator.getUUID());
////		dto.setName("张三");
//		dto.setIdCardNo("360723199107114111");
//		dto.setMobile("15279107876");
//		dto.setPayChannelType(3);
//		dto.setPayeeAccount("6227002200039940074");
//		dto.setPayeeName("票量");
//		dto.setAmount(0.02);   
////		dto.setPayerRemark("汇金宝支付测试....");
//		
//		CreateHJBPayOrderCommand command = new CreateHJBPayOrderCommand();
//		command.setPayOrderCreateDTO(dto);
//		
//		command.setChannelId("LHBANK60");
//		command.setMerCode("8888888888");
//		command.setPayeeBankAccountName("刘志强");
//		command.setPayeeBankType("1051000");
//		command.setCustomerType("1");
//		command.setCallerIp("192.168.10.91");
//		command.setPayerBankType("1031000");
//		
//		command.setTradeName("交易名称");
//		command.setSubject("商品名称");
//		command.setDescription("商品描述");
		if(StringUtils.isBlank(command.getPayOrderCreateDTO().getClientTradeNo())){
			command.getPayOrderCreateDTO().setClientTradeNo(UUIDGenerator.getUUID());
		}
		
		
		try{
			String json = JSON.toJSONString(command);
			String hjbRequest = payOrderSpiService.pay(command.getPayOrderCreateDTO().getPaymentClientId(),
					command.getPayOrderCreateDTO().getPaymentClienKeys(),command.getPayOrderCreateDTO().getPayChannelType(), json);
			return hjbRequest;
		}catch(PaymentException e){
			HgLogger.getInstance().error("luoyun", "向汇金宝发送支付请求失败" + e.getMessage());
			return JSON.toJSONString(e.getMessage());
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "向汇金宝发送支付请求失败" + e.getMessage());
			return JSON.toJSONString(e.getMessage());
		}
		
		
	}
	
	
	
	
	
}
