package slfx.yg.open.utils;


import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.command.client.ABEPassengerCommand;
import slfx.jp.command.client.ABEPriceDetailCommand;
import slfx.jp.command.client.YGFlightCommand;
import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;

/**
 * 
 * @类功能说明：数据XML解析工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:20:33
 * @版本：V1.0
 *
 */
@Component
public class YGXmlUtil {
	
	/**
	 * 处理接口xml格式字符串
	 * @param xml
	 * @return
	 */
	public static Element getRootElement(String xml){
		
		try {
			Document document = null;
			document = DocumentHelper.parseText(xml);
			return document.getRootElement();
		} catch (DocumentException e) {
			return null;
		}
	}
	
	/**
	 * 处理接口xml格式字符串
	 * 过滤一个xml头部
	 * @param xml
	 * @return
	 * @author tandeng
	 */
	public static Element getRootElementFilterChar(String xml){
		
		try {
			xml = xml.replace("&lt;", "<");
			xml = xml.replace("&gt;", ">");
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			xml = xml.replace("<string xmlns=\"http://tempuri.org/\">", "");
			xml = xml.replace("</string>", "");
			
			Document document = null;
			document = DocumentHelper.parseText(xml.trim());
			Element element = document.getRootElement();
			return element;
		} catch (DocumentException e) {
			
			return null;
		}
	}
	
	/**
	 * (仅适用非采购云接口)接口调用异常时 解析错误信息  记录信息
	 * @param xml
	 * @return
	 */
	public String parseErrorInfo(String xml){
		
		StringBuffer logBuffer = new StringBuffer();
		
		Element element = getRootElement(xml);
//		hgLogger.error("yangkang", 
//					   "接口调用异常信息 >>>请求时间:"
//					   + element.selectSingleNode("/ErrorInfo_1_0/CallTime").getText()
//					   + "   调用接口名称:" + element.selectSingleNode("/ErrorInfo_1_0/MethodName").getText()
//					   + "      错误代码:" + element.selectSingleNode("/ErrorInfo_1_0/Code").getText()
//					   + "      请求操作错误结果:" + element.selectSingleNode("/ErrorInfo_1_0/Content").getText());
		logBuffer.append("接口调用异常信息 >>>请求时间:"
				+ element.selectSingleNode("/ErrorInfo_1_0/CallTime").getText()
				+ "   调用接口名称:" + element.selectSingleNode("/ErrorInfo_1_0/MethodName").getText()
				+ "      错误代码:" + element.selectSingleNode("/ErrorInfo_1_0/Code").getText()
				+ "      请求操作错误结果:" + element.selectSingleNode("/ErrorInfo_1_0/Content").getText());
		
//		System.out.println("接口调用异常信息 >>>请求时间:"
//				+ element.selectSingleNode("/ErrorInfo_1_0/CallTime").getText()
//				+ "   调用接口名称:" + element.selectSingleNode("/ErrorInfo_1_0/MethodName").getText()
//				+ "      错误代码:" + element.selectSingleNode("/ErrorInfo_1_0/Code").getText()
//				+ "      请求操作错误结果:" + element.selectSingleNode("/ErrorInfo_1_0/Content").getText());
		return logBuffer.toString();
	}
	
	
	/**
	 *  ABE下单  创建Flight节点
	 * @param command
	 * @return
	 */
	public String createABEFlightStr(ABEOrderFlightCommand command){
		StringBuffer sb = new StringBuffer();
		sb.append("<Flight>");
			sb.append("<ID/>");
			sb.append("<FlightID/>");
			sb.append("<ElementNo/>");
			sb.append("<Type/>");
			sb.append("<TypeCode/>");
			sb.append("<ActionCode/>");
			sb.append("<Farebasis/>");
			sb.append("<Carrier>"+(command.getCarrier() == null ? "" : command.getCarrier())+"</Carrier>");
			sb.append("<FlightNo>"+(command.getFlightNo() == null ? "" : command.getFlightNo().substring(2,command.getFlightNo().length()))+"</FlightNo>");//航班号去掉航司代码
			sb.append("<ShareCarrier/>");
			sb.append("<ShareFlight/>");
			sb.append("<FromCity>"+(command.getStartCityCode() == null ? "" : command.getStartCityCode())+"</FromCity>");
			sb.append("<ArriveCity>"+(command.getEndCityCode() == null ? "" : command.getEndCityCode())+"</ArriveCity>");
			sb.append("<Mileage>"+(command.getMileage() == null ? "" : command.getMileage())+"</Mileage>");
			sb.append("<ClassCode>"+(command.getClassCode() == null ? "" : command.getClassCode())+"</ClassCode>");  
			sb.append("<ClassRebate>"+(command.getClassRebate() == null ? "" : command.getClassRebate())+"</ClassRebate>");
			sb.append("<ClassPrice>"+(command.getClassPrice() == null ? "" : command.getClassPrice())+"</ClassPrice>");
			sb.append("<BasePrice/>");
			sb.append("<YPrice>"+(command.getYPrice() == null ? "" : command.getYPrice())+"</YPrice>");
			sb.append("<FuelSurTax>"+(command.getFuelSurTax() == null ? "" : command.getFuelSurTax())+"</FuelSurTax>");
			sb.append("<AirportTax>"+(command.getAirportTax() == null ? "" : command.getAirportTax())+"</AirportTax>");
			sb.append("<DepartureDate>"+(command.getStartDate() == null ? "" : command.getStartDate())+"</DepartureDate>");
			sb.append("<DepartureTime>"+(command.getStartTime() == null ? "" : command.getStartTime())+"</DepartureTime>");
			sb.append("<ArrivalDate>"+(command.getEndDate() == null ? "" : command.getEndDate())+"</ArrivalDate>");
			sb.append("<ArrivalTime>"+(command.getEndTime() == null ? "" : command.getEndTime())+"</ArrivalTime>");
			sb.append("<Aircraft>"+(command.getAircraftCode() == null ? "" : command.getAircraftCode())+"</Aircraft>");
			sb.append("<Meal/><ViaPort/><ETicket/><OverstepPriceReason/><BoardPointAT/>");
			sb.append("<OffPointAT/><MinPrice/><CarrierCustomerNo/><Group/>");
		sb.append("</Flight>");
		return sb.toString();
	}
	
	
	/**
	 *  ABE下单  创建Passenger节点
	 * @param command
	 * @return
	 */
	public String createABEPassengerStr(ABEOrderFlightCommand command){
		//所有乘机人mobilePhone都使用联系人的mobile
		String abePassengerMobilePhone = null;
		try{
			abePassengerMobilePhone = command.getAbeLinkerInfoCommand().getMobilePhone();
		}catch(Exception e){
			abePassengerMobilePhone = abePassengerMobilePhone == null ?"":abePassengerMobilePhone;			
		}
		
		if(command.getAbePsgList()!=null && command.getAbePsgList().size()>0){
			StringBuffer sb = new StringBuffer();
			for(ABEPassengerCommand c : command.getAbePsgList()){
//				try {
//					byte[] nameByteArray = c.getName().getBytes("UTF-8");
//					c.setName(new String(nameByteArray, "UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					c.setName("");
//				}
				sb.append("<Passenger>");
				sb.append("<ElementNo/>");
				sb.append("<PsgID>"+(c.getPsgId() == null ? "" : c.getPsgId())+"</PsgID>");
				sb.append("<Name>"+PinYinUtil.chNameToPinYin(c.getName())+"</Name>");
				sb.append("<PsgType>"+(c.getPsgType() == null ? "" : c.getPsgType())+"</PsgType>");
				sb.append("<IdentityType>"+(c.getIdentityType() == null ? "" : c.getIdentityType())+"</IdentityType>");
				sb.append("<CardType>"+(c.getCardType() == null ? "" : c.getCardType())+"</CardType>");
				sb.append("<CardNo>"+(c.getCardNo() == null ? "" : c.getCardNo())+"</CardNo>");
				sb.append("<BirthDay>"+(c.getBirthDay() == null ? "" : c.getBirthDay())+"</BirthDay>");
				sb.append("<CarrierPsgID/>");
				sb.append("<Country></Country>");
				sb.append("<MobilePhone>"+(c.getMobliePhone() == null ? "" : c.getMobliePhone())+"</MobilePhone>");
				sb.append("<InsueSum/>");
				sb.append("<InsueFee/>");
				sb.append("<CarrierCard/>");
				sb.append("<CardValidDate/>");
				sb.append("<Gender/>");
				sb.append("<ServiceFee/>");
				sb.append("</Passenger>");
			}
			return sb.toString();
		}else{
			return "<Passenger/>";
		}
	}
	
	
	/**
	 *  ABE下单  创建Price节点
	 * @param command
	 * @return
	 */
	public String createABEPriceStr(ABEOrderFlightCommand command, YGUtil util){
		if(command.getAbePriceDetailList()!=null && command.getAbePriceDetailList().size()>0){
			StringBuffer sb = new StringBuffer();
			for(ABEPriceDetailCommand c : command.getAbePriceDetailList()){
				Double salePrice = c.getAirportTax()+c.getFuelSurTax()+c.getFare();
				sb.append("<Price>");
				sb.append("<PriceID>0</PriceID>");
				sb.append("<Z/>");
				sb.append("<Y/>");
				sb.append("<TktOffice>"+(util.getOffice() == null ? "" : util.getOffice())+"</TktOffice>");
				sb.append("<PsgType>"+(c.getPsgType() == null ? "" : c.getPsgType())+"</PsgType>");
				sb.append("<PsgID/>");
				sb.append("<YPrice>"+(command.getYPrice() == null ? "" : command.getYPrice())+"</YPrice>");
				sb.append("<Fare>"+(c.getFare() == null ? "" : c.getFare())+"</Fare>");
				sb.append("<TaxAmount>"+(c.getTaxAmount() == null ? "" : c.getTaxAmount())+"</TaxAmount>");
				sb.append("<FuelSurTax>"+(c.getFuelSurTax() == null ? "" : c.getFuelSurTax())+"</FuelSurTax>");
				sb.append("<AirportTax>"+(c.getAirportTax() == null ? "" : c.getAirportTax())+"</AirportTax>");
				sb.append("<SalePrice>"+(salePrice == null ? "" : salePrice)+"</SalePrice>");
				sb.append("<Group/><PriceType/>");
				sb.append("</Price>");  
			}
			return sb.toString();
		}else{
			return "<Price/>";
		}
	}
	
	
	/**
	 *  ABE下单  创建OrderInfo节点的子节点
	 * @param command
	 * @return
	 */
	public String createABEOrderInfoStr(ABEOrderFlightCommand command){
		if(command.getAbeOrderInfoCommand()!=null){
			StringBuffer sb = new StringBuffer();
			sb.append("<Address></Address>");
			sb.append("<Linker>"+(command.getAbeOrderInfoCommand().getLinker() == null ? "" : command.getAbeOrderInfoCommand().getLinker())+"</Linker>");
			sb.append("<Telephone>"+(command.getAbeOrderInfoCommand().getTelephone() == null ? "" : command.getAbeOrderInfoCommand().getTelephone())+"</Telephone>");
			sb.append("<IsDomc>"+(command.getAbeOrderInfoCommand().getIsDomc() == null ? "" : command.getAbeOrderInfoCommand().getIsDomc())+"</IsDomc>");
			sb.append("<TicketLimitDate></TicketLimitDate><TicketLimitTime></TicketLimitTime><PayPlatform></PayPlatform><BankCode></BankCode><NotifyURL></NotifyURL><NotifyType></NotifyType><Remark></Remark>");
			sb.append("<BalanceMoney>"+(command.getAbeOrderInfoCommand().getBalanceMoney() == null ? "" : command.getAbeOrderInfoCommand().getBalanceMoney())+"</BalanceMoney>");//结算价格  订单总金额
			sb.append("<PlatformCode>OTR</PlatformCode>");
			sb.append("<LocalOrder>Y</LocalOrder><ReqOrderNo></ReqOrderNo><SPID></SPID><AuthOffices></AuthOffices>");
			return sb.toString();
		}else{
			return "";
		}
	}
	
	
	/**
	 *  ABE下单  创建LinkerInfo节点的子节点
	 * @param command
	 * @return
	 */
	public String createABELinkerInfoStr(ABEOrderFlightCommand command){
		if(command.getAbeLinkerInfoCommand()!=null){
			StringBuffer sb = new StringBuffer();
			sb.append("<IsETiket>Y</IsETiket>");//是否电子机票 Y/N
			sb.append("<PayType/>");//支付方式 
			sb.append("<Address/>");//地址
			sb.append("<LinkerName>"+(command.getAbeLinkerInfoCommand().getLinkerName() == null ? "" : command.getAbeLinkerInfoCommand().getLinkerName())+"</LinkerName>");//联系人
			sb.append("<Zip/>");//邮编
			sb.append("<Telphone>"+(command.getAbeLinkerInfoCommand().getTelphone() == null ? "" : command.getAbeLinkerInfoCommand().getTelphone())+"</Telphone>");//电话 
			sb.append("<MobilePhone>"+(command.getAbeLinkerInfoCommand().getMobilePhone() == null ? "" : command.getAbeLinkerInfoCommand().getMobilePhone())+"</MobilePhone>");//手机
			sb.append("<SendTime/>");//送票时间
			sb.append("<LinkerEmail/>");//联系人Email
			sb.append("<NeedInvoices/>");//是否需要发票 Y/N
			sb.append("<InvoicesSendType/>");//发票发送方式 A邮寄 B配送
			sb.append("<Remark/>");//备注
			sb.append("<SendTktsTypeCode/>");//订单配送方式（By：不配送 ZQ:自取 SP:送票 YJ:邮寄 BZ:不制单）
			sb.append("<IsPrintSerial>"+(command.getAbeLinkerInfoCommand().getIsPrintSerial() == null ? "" : command.getAbeLinkerInfoCommand().getIsPrintSerial())+"</IsPrintSerial>");//是否打印行程单（Y/N)
			return sb.toString();
		}else{
			return "";
		}
	}
	
	
	/**
	 * 易购下单，创建航班订单节点
	 * @param yGOrderCommand
	 * @param util
	 * @return
	 */
	public String createYGFlightOrderStr(YGOrderCommand yGOrderCommand,YGUtil util){
		StringBuffer sb = new StringBuffer();
		if(yGOrderCommand!=null){
			sb.append("<Pnr>"+(yGOrderCommand.getPnr() == null ? "" : yGOrderCommand.getPnr())+"</Pnr>");
			sb.append("<PnrText>"+(yGOrderCommand.getPnrText() == null ? "" : yGOrderCommand.getPnrText())+"</PnrText>");
			sb.append("<PataText>"+(yGOrderCommand.getPataText()== null ?"":yGOrderCommand.getPataText())+"</PataText>");
			sb.append("<TicketType>"+(yGOrderCommand.getTicketType()==null?"Yeego":yGOrderCommand.getTicketType())+"</TicketType>");
			sb.append("<BigPNR>"+(yGOrderCommand.getBigPNR() == null ? "" : yGOrderCommand.getBigPNR())+"</BigPNR>");
			sb.append("<PolicyId>"+(yGOrderCommand.getPolicyId() == null ? "" : yGOrderCommand.getPolicyId())+"</PolicyId>");
			sb.append("<PolicyCommsion>");
			sb.append(yGOrderCommand.getPolicyCommsion() == null ?0.00:yGOrderCommand.getPolicyCommsion());
			sb.append("</PolicyCommsion>");
			sb.append("<PlatCode>"+(yGOrderCommand.getPlatCode() == null ? "" : yGOrderCommand.getPlatCode())+"</PlatCode>");
			sb.append("<PlatformType>"+(yGOrderCommand.getPlatformType()== null ?"":yGOrderCommand.getPlatformType())+"</PlatformType>");
			sb.append("<IsVip>"+(yGOrderCommand.isVip()== null ?"":yGOrderCommand.isVip())+"</IsVip>");
			sb.append("<AccountLevel>"+(yGOrderCommand.getAccountLevel()==null?"":yGOrderCommand.getAccountLevel())+"</AccountLevel>");
			sb.append("<BalanceMoney>"+(yGOrderCommand.getBalanceMoney() == null ? "" : yGOrderCommand.getBalanceMoney())+"</BalanceMoney>");
			sb.append("<Remark>"+(yGOrderCommand.getRemark() == null ? "" : yGOrderCommand.getRemark())+"</Remark>");
			sb.append("<WorkTime>"+(yGOrderCommand.getWorkTime() == null ? "" : yGOrderCommand.getWorkTime())+"</WorkTime>");
			sb.append("<RefundWorkTime>"+(yGOrderCommand.getRefundWorkTime() == null ? "" : yGOrderCommand.getRefundWorkTime())+"</RefundWorkTime>");
			sb.append("<WastWorkTime>"+(yGOrderCommand.getWastWorkTime() == null ? "" : yGOrderCommand.getWastWorkTime())+"</WastWorkTime>");
			sb.append("<ForceDelete>"+(yGOrderCommand.getForceDelete() == null?"":yGOrderCommand.getForceDelete())+"</ForceDelete>");
			sb.append("<BookingOffice>"+(yGOrderCommand.getTicketOffice() == null ? "" : yGOrderCommand.getTicketOffice())+"</BookingOffice>");
		}

		return sb.toString();
	}
	
	
	/**
	 * 易购下单，创建航班节点
	 * @param yGOrderCommand
	 * @param util
	 * @return
	 */
	public String createYGFlightStr(YGOrderCommand yGOrderCommand,YGUtil util){
		StringBuffer sb = new StringBuffer();
		//航班列表	   
		YGFlightCommand flight = yGOrderCommand.getFlight();
		if(flight!=null){
			sb.append("<Flights>");
			sb.append("<Flight>");
			sb.append("<Carrier>"+(flight.getCarrier() == null ? "" : flight.getCarrier())+"</Carrier>");
			sb.append("<FlightNo>"+(flight.getFlightNo() == null ? "" : flight.getFlightNo())+"</FlightNo>");
			sb.append("<BoardPoint>"+(flight.getBoardPoint() == null ? "" : flight.getBoardPoint())+"</BoardPoint>");
			sb.append("<OffPoint>"+(flight.getOffPoint() == null ? "" : flight.getOffPoint())+"</OffPoint>");
			sb.append("<ClassCode>"+(flight.getClassCode() == null ? "" : flight.getClassCode())+"</ClassCode>");
			sb.append("<DepartureDate>"+(flight.getDepartureDate() == null ? "" : flight.getDepartureDate())+"</DepartureDate>");
			sb.append("</Flight>");	
			sb.append("</Flights>");
		}

		return sb.toString();
	}
	
	/**
	 * 易购下单，创建旅客节点列表
	 * @param yGOrderCommand
	 * @param util
	 * @return
	 */
	public String createYGFlightPassengersStr(YGOrderCommand yGOrderCommand,YGUtil util){
		StringBuffer sb = new StringBuffer();
		//旅客列表
		List<FlightPassengerDTO> passengers =yGOrderCommand.getPassengers();
		if(passengers!=null&&passengers.size()>0){	
			sb.append("<Passengers>");
			for(FlightPassengerDTO passenger:passengers){
				sb.append("<Passenger>");
				sb.append("<Name>"+(passenger.getName() == null ? "" : passenger.getName())+"</Name>");
				
					//旅客类型   ADT 成人CHD 儿童INF 婴儿UM 无陪伴儿童
					String psgType=passenger.getPassangerType();
					String psgText="";
					if("ADT".equals(psgType)){
						psgText="成人";
					}else if("CHD".equals(psgType)){
						psgText="儿童";
					}else if("INF".equals(psgType)){
						psgText="婴儿";
					}else if("UM".equals(psgType)){
						psgText="无陪伴儿童";
					}
				
			    sb.append("<PsgType>"+psgText+"</PsgType>");
				sb.append("<IdentityType>"+(passenger.getIdentityType() == null ? "" : passenger.getIdentityType())+"</IdentityType>");
					
				//航信系统证件类型 NI 身份证号FOID 护照号ID 其它类型
				String cardType=passenger.getCardType();
					
				sb.append("<CardType>"+(cardType == null ? "" : cardType)+"</CardType>");
				sb.append("<CardNo>"+(passenger.getCardNo() == null ? "" : passenger.getCardNo())+"</CardNo>");
				sb.append("<MobilePhone>"+(passenger.getMobile() == null ? "" : passenger.getMobile())+"</MobilePhone>");
				sb.append("<InsueSum>"+(passenger.getInsueSum() == null ? "" : passenger.getInsueSum())+"</InsueSum>");
				sb.append("<Fare>"+(passenger.getFare() == null ? "" : passenger.getFare())+"</Fare>");
				sb.append("<SalePrice>"+(passenger.getSalePrice() == null ? "" : passenger.getSalePrice())+"</SalePrice>");
				sb.append("<TaxAmount>"+(passenger.getTaxAmount() == null ? "" : passenger.getTaxAmount())+"</TaxAmount>");
				sb.append("</Passenger>");
			}
			
			sb.append("</Passengers>");
		}

		return sb.toString();
	}


	
}
