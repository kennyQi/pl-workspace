package hg.payment.app.controller.test;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.pojo.command.dto.AlipayRefundRequestDTO;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.AlipayRefundCommand;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.spi.inter.AlipayPayOrderSpiService;
import hg.payment.spi.inter.PayOrderSpiService;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping(value = "/alipayorder")
public class AlipayPayOrderController {
	
	@Autowired
	private AlipayPayOrderSpiService alipayPayOrderSpiService;
	@Autowired
	private AlipayPayChannel alipay;
	@Autowired
	private PayOrderSpiService payOrderSpiService;

	@RequestMapping(value ="/testorder")
	public String toAlipayPayOrder(HttpServletRequest request, HttpServletResponse response){
		return "test/alipay_test.html";
	}
	
	@RequestMapping(value ="/order")
	@ResponseBody
	public String alipayPayOrder(HttpServletRequest request, HttpServletResponse response){
		
		CreateAlipayPayOrderCommand command = new CreateAlipayPayOrderCommand();
		
		PayOrderRequestDTO dto = new PayOrderRequestDTO();
		dto.setPaymentClientId("425078243aae407da092aaf3d4ab255b");
		dto.setPayerClientUserId("1");
		dto.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
		dto.setClientTradeNo(UUIDGenerator.getUUID());
		dto.setName("南航");
		dto.setIdCardNo("360723199107114111");
		dto.setMobile("15279107876");
		dto.setPayChannelType(1);
		dto.setPayeeAccount("zjhg_jiesuan@163.com");
		dto.setPayeeName("浙江汇购");
		dto.setAmount(0.01);
		dto.setPayerRemark("测试....");//弃用
		command.setPayOrderCreateDTO(dto);
		
		command.setSellerEmail("zjhg_jiesuan@163.com");//弃用
		command.setPartner("2088701074577516");
		command.setKeys("gfu3gpkuiam1idh45do66ay0se4a83jv");
		command.setSubject("商品名称");
		command.setBody("汇购测试");
		command.setShowUrl("www.baidu.com");
		
		if(StringUtils.isBlank(command.getPayOrderCreateDTO().getClientTradeNo())){
			command.getPayOrderCreateDTO().setClientTradeNo(UUIDGenerator.getUUID());
		}
		
		
		String alipayUrl = "";
		
		try{
			 //支付平台生成订单，并返回支付宝请求地址
			 alipayUrl = alipayPayOrderSpiService.createAlipayPayOrder(command);
			 return alipayUrl;
			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
		
		
	}
	
	
	@RequestMapping(value ="/newOrder")
	@ResponseBody
	public String alipayPayOrderNew(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CreateAlipayPayOrderCommand command){
		
//		CreateAlipayPayOrderCommand command = new CreateAlipayPayOrderCommand();
//		
//		PayOrderRequestDTO dto = new PayOrderRequestDTO();
//		dto.setPaymentClientId("425078243aae407da092aaf3d4ab255b");
//		dto.setPayerClientUserId("1");
//		dto.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
//		dto.setClientTradeNo(UUIDGenerator.getUUID());
//		dto.setName("南航");
//		dto.setIdCardNo("360723199107114111");
//		dto.setMobile("15279107876");
//		dto.setPayChannelType(1);
//		dto.setPayeeAccount("zjhg_jiesuan@163.com");
//		dto.setPayeeName("浙江汇购");
//		dto.setAmount(0.02);
//		command.setPayOrderCreateDTO(dto);
//		
//		command.setPartner("2088701074577516");
//		command.setKeys("gfu3gpkuiam1idh45do66ay0se4a83jv");
//		command.setSubject("商品名称");
//		command.setBody("汇购测试");
//		command.setShowUrl("www.baidu.com");
		
		if(StringUtils.isBlank(command.getPayOrderCreateDTO().getClientTradeNo())){
			command.getPayOrderCreateDTO().setClientTradeNo(UUIDGenerator.getUUID());
		}
		
		String alipayUrl = "";
		
		try{
			 //支付平台生成订单，并返回支付宝请求地址
			 String json = JSON.toJSONString(command);
			 alipayUrl = payOrderSpiService.pay(command.getPayOrderCreateDTO().getPaymentClientId(),
						command.getPayOrderCreateDTO().getPaymentClienKeys(),command.getPayOrderCreateDTO().getPayChannelType(),json);
			 return alipayUrl;
			
		}catch(PaymentException e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "向支付宝发送支付请求失败" + e.getMessage());
			return JSON.toJSONString(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "向支付宝发送支付请求失败" + e.getMessage());
			return JSON.toJSONString(e.getMessage());
		}
		
		
		
	}
	
	@RequestMapping(value ="/testrefund")
	public String toRefund(HttpServletRequest request, HttpServletResponse response){
		return "test/alipay_refund_test.html";
	}
	
	@RequestMapping(value ="/refund")
	@ResponseBody
	public String refund(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute AlipayRefundCommand command,
			@RequestParam(value="clientKeys",required=true) String clientKeys){
		
		List<AlipayRefundRequestDTO> dtoList = command.getAlipayRefundRequestDTOList();
		Iterator<AlipayRefundRequestDTO> iterator = dtoList.iterator();
		while(iterator.hasNext()){
			AlipayRefundRequestDTO dto =  iterator.next();
			if(StringUtils.isBlank(dto.getTradeNo())){
				iterator.remove();
			}
		}
		String json = JSON.toJSONString(command);
		
		if(command.getAlipayRefundRequestDTOList().size() == 0){
			return "请提供退款列表";
		}
		try {
			
			payOrderSpiService.refund(command.getAlipayRefundRequestDTOList().get(0).getPaymentClientId(),clientKeys, 1, json);
			
		} catch (PaymentException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "向支付宝发送支付请求失败" + e.getMessage());
			return JSON.toJSONString(e.getMessage());
		}
		
		return "支付宝已接收退款请求";
		
	}
	
}
