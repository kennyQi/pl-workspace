package slfx.yg.open.inter.impl;

import hg.log.util.HgLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;

import slfx.jp.pojo.dto.supplier.yg.YGFlightPolicyDTO;
import slfx.jp.qo.client.PolicyByPnrQo;
import slfx.yg.open.utils.PnrUtil;

/**
 * 
 * @类功能说明：比价接口的细节实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午5:17:46
 * @版本：V1.0
 *
 */
public class QueryPriceByPnrHelp {
	public static Map<Integer,String> weekMap = new HashMap<Integer,String>();
	public static Map<Integer,String> monthMap = new HashMap<Integer,String>();
	
	static {
		//Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday|Mo,Tu,We,Th,Fr,Sa,Su
		weekMap.put(1, "SU");	//周日
		weekMap.put(2, "MO");	//周一
		weekMap.put(3, "TU");	//周二
		weekMap.put(4, "WE");	//周三
		weekMap.put(5, "TH");	//周四
		weekMap.put(6, "FR");	//周五
		weekMap.put(7, "SA");	//周六
	
		//1,2,3,4,5,6,7,8,9,10,11,12|Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
		monthMap.put(0, "JAN");
		monthMap.put(1, "FEB");
		monthMap.put(2, "MAR");
		monthMap.put(3, "APR");
		monthMap.put(4, "MAY");
		monthMap.put(5, "JUN");
		monthMap.put(6, "JUL");
		monthMap.put(7, "AUG");
		monthMap.put(8, "SEP");
		monthMap.put(9, "OCT");
		monthMap.put(10, "NOV");
		monthMap.put(11, "DEC");
	}
	
	public static String readTxtFile(String filePath) {
		StringBuffer strBuff = new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					strBuff.append(lineTxt + "\n\r");

				}
				read.close();
			} else {
				HgLogger.getInstance().error("qiuxianxiang", "找不到指定的文件" + filePath);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("qiuxianxiang", "读取文件内容出错" + filePath);
		}

		return strBuff.toString();
	}
	
	public static String initPolicyRequestParam(PolicyByPnrQo qo) {
		/*
		Calendar cale = Calendar.getInstance();
		cale.setTime(qo.getDepartureDate());
		
		String filePath = "D:\\develop\\workspaces-sl\\shanglv\\slfx-jp\\"
				+ "slfx-jp-ygclient\\src\\main\\resources\\OneWayPnrTextTemplate.txt";
		String pnrText = readTxtFile(filePath);
		
		//-封装pnrText参数
		
		pnrText = pnrText.replace("{0}", qo.getPassengerName());
		pnrText = pnrText.replace("{1}", qo.getPnr());
		pnrText = pnrText.replace("{2}", qo.getFlightNo());
		pnrText = pnrText.replace("{3}", qo.getClassNo());
		String param4Week = weekMap.get(cale.get(Calendar.DAY_OF_WEEK));
		String param4 = param4Week + getDate(cale);
		pnrText = pnrText.replace("{4}", param4);
		pnrText = pnrText.replace("{5}", qo.getBoardPoint()+qo.getOffPoint());
		
		String param6 =  getTime(cale);//0106  1点零6分
		pnrText = pnrText.replace("{6}", param6);
		pnrText = pnrText.replace("{10}", qo.getCarrier());
		pnrText = pnrText.replace("{13}", qo.getFlightNo());
		String param14 = qo.getClassNo()+ getDate(cale);
		pnrText = pnrText.replace("{14}", param14);
		
		cale.setTime(qo.getTktStart());  //重新设置日历时间为出票时间
		String param8 = getTime(cale);
		pnrText = pnrText.replace("{8}", param8);
		String param9 = getDate(cale);
		pnrText = pnrText.replace("{9}", param9);
		String param11 = param9+ String.valueOf(cale.get(Calendar.YEAR)).substring(2, 4);  //21JUL14
		pnrText = pnrText.replace("{11}", param11);
		String param12 = param8;
		pnrText = pnrText.replace("{12}", param12);
		
		cale.setTime(qo.getArrivalDate());  //重新设置日历时间为到达时间
		String param7 = getTime(cale);
		pnrText = pnrText.replace("{7}", param7);
		*/
		//-封装pnrText参数
		String pnrText = PnrUtil.getPnrText(qo);
		String requestParam = "<?xml version=\"1.0\" encoding=\"utf-8\"?><YeeGo.QueryPolicyByPNR_1_0><Pnr>#{PNR}</Pnr><PnrText>#{PNR_TEXT}</PnrText><Plats>ALL</Plats></YeeGo.QueryPolicyByPNR_1_0>";
		requestParam = requestParam.replace("#{PNR}", qo.getPnr());
		requestParam = requestParam.replace("#{PNR_TEXT}", pnrText);
		return requestParam;
		
		/**
		以下为模板：
		 1.邱献祥 HP5P9J                                                               
		 2. CA8902 H MO21JUL PEKDLC HK1   2245 0005          E T2--                 
		 3.SHA/T SHA/T021-62277798/SHANGHAI EVER BRIGHT TOWN INT'L TRAVEL SERVICE CO.,LTD ABCDEFG                                                            
	 	 4.END                                                                          
		 5.TL/1455/21JUL/SHA255                                                         
		 6.SSR FOID CA HK1 NI12345678932165478/P1                                       
		 7.SSR ADTK 1E BY SHA21JUL14/1455 OR CXL CA8902 H21JUN                        
		 8.RMK AO/ABE/KH-104220941001/OP-NN ADMIN/DT-20121205142736                     
		 9.RMK CA/MXNT4G                                                                
		 10.SHA255
		 */
	}
	
	//获取格式 0106  1点零6分 的时间
	@SuppressWarnings("unused")
	private static String getTime(Calendar cale) {
		String paramHour = cale.get(Calendar.HOUR_OF_DAY) < 10 ? "0"+cale.get(Calendar.HOUR_OF_DAY) : cale.get(Calendar.HOUR_OF_DAY)+"";
		String paramMin = cale.get(Calendar.MINUTE) < 10 ? "0"+cale.get(Calendar.MINUTE) : cale.get(Calendar.MINUTE)+"";
		String param =  paramHour + paramMin ;//0106  1点零6分
		return param;//0106  1点零6分
	}
	//获取格式21JUN  7月21日 的日期
	@SuppressWarnings("unused")
	private static String getDate(Calendar cale) {
		int paramDay =  cale.get(Calendar.DAY_OF_MONTH);
		String paramMonth = monthMap.get(cale.get(Calendar.MONTH));
		return paramDay+paramMonth;//21JUN
	}
	
	//解析比价接口返回的xml，并且封装到FlightPolicyDTO列表中
	@SuppressWarnings("unchecked")
	public static List<YGFlightPolicyDTO> parseXml(Element root) {
		List<YGFlightPolicyDTO> policys = new ArrayList<YGFlightPolicyDTO>();

		List<Node> nodes = root.selectNodes("/YeeGo.QueryPolicyByPNR_1_0/Policys/Policy");
		for (Node node : nodes) {
			//FlightPolicyDTO policy = new FlightPolicyDTO();
			YGFlightPolicyDTO ygPolicy = new YGFlightPolicyDTO();

			Node flag = node.selectSingleNode("Flag");
			if (null != flag) {
				ygPolicy.setFlag(flag.getText());//Y
			}
			
			Node PolicyId = node.selectSingleNode("PolicyId");
			if (null != PolicyId) {
				ygPolicy.setPolicyId(PolicyId.getText());//10982749|
			}
			
			Node PlatCode = node.selectSingleNode("PlatCode");
			if (null != PlatCode) {
				ygPolicy.setPlatCode(PlatCode.getText());//004
			}
			
			Node PlatCodeForShort = node.selectSingleNode("PlatCodeForShort");
			if (null != PlatCodeForShort) {
				ygPolicy.setPlatCodeForShort(PlatCodeForShort.getText());//C5
			}
			
			Node PlatformMappingCode = node.selectSingleNode("PlatformMappingCode");
			if (null != PlatformMappingCode) {
				ygPolicy.setPlatformMappingCode(PlatformMappingCode.getText());//C5
			}
			
			Node PlatformName = node.selectSingleNode("PlatformName");
			if (null != PlatformName) {
				ygPolicy.setPlatformName(PlatformName.getText());//517NA
			}
			
			Node IsVip = node.selectSingleNode("IsVip");
			if (null != IsVip) {
				ygPolicy.setIsVip(IsVip.getText());//10
			}
			
			Node PlatformType = node.selectSingleNode("PlatformType");
			if (null != PlatformType) {
				ygPolicy.setPlatformType(PlatformType.getText());//P
			}
			
			//String tempTktOffice = root.selectSingleNode("/YeeGo.QueryPolicyByPNR_1_0/Policys/Policy/TktOffice").getText();
			//ygPolicy.setRemark(tempTktOffice == null?"BJS280":tempTktOffice);
			
			Node TktOffice = node.selectSingleNode("TktOffice");
			if(TktOffice == null || "".equals(TktOffice.getText())){
				ygPolicy.setTktOffice("BJS280");
			}else{
				ygPolicy.setTktOffice(TktOffice.getText());
			}
			
			Node SwitchPnr = node.selectSingleNode("SwitchPnr");
			if (null != SwitchPnr) {
				ygPolicy.setSwitchPnr(SwitchPnr.getText());//N
			}
			
			Node FltStart = node.selectSingleNode("FltStart");
			if (null != FltStart) {
				ygPolicy.setFltStart(FltStart.getText());
			}
			
			Node FltEnd = node.selectSingleNode("FltEnd");
			if (null != FltEnd) {
				ygPolicy.setFltEnd(FltEnd.getText());
			}
			
			Node PreTkt = node.selectSingleNode("PreTkt");
			if (null != PreTkt) {
				ygPolicy.setPreTkt(PreTkt.getText());
			}
			
			Node TktStart = node.selectSingleNode("TktStart");
			if (null != TktStart) {
				ygPolicy.setTktStart(TktStart.getText());
			}
			
			Node TktEnd = node.selectSingleNode("TktEnd");
			if (null != TktEnd) {
				ygPolicy.setTktEnd(TktEnd.getText());
			}
			
			Node Fare = node.selectSingleNode("Fare");
			if (null != Fare) {
				ygPolicy.setFare(Fare.getText());
			}
			
			Node Rebate = node.selectSingleNode("Rebate");
			if (null != Rebate) {
				ygPolicy.setRebate(Rebate.getText());
			}
			
			Node TaxAmount = node.selectSingleNode("TaxAmount");
			if (null != TaxAmount) {
				ygPolicy.setTaxAmount(TaxAmount.getText());//0
			}
			
			Node CommAmount = node.selectSingleNode("CommAmount");
			if (null != CommAmount) {
				ygPolicy.setCommAmount(CommAmount.getText());
			}
			
			Node PaymentFee = node.selectSingleNode("PaymentFee");
			if (null != PaymentFee) {
				ygPolicy.setPaymentFee(PaymentFee.getText());
			}
			
			Node CommRate = node.selectSingleNode("CommRate");
			if (null != CommRate) {
				ygPolicy.setCommRate(CommRate.getText());//4.70
			}
			
			Node CommMoney = node.selectSingleNode("CommMoney");
			if (null != CommMoney) {
				ygPolicy.setCommMoney(CommMoney.getText());//0
			}
			
			Node FareBase = node.selectSingleNode("FareBase");
			if (null != FareBase) {
				ygPolicy.setFareBase(FareBase.getText());
			}
			
			Node TktType = node.selectSingleNode("TktType");
			if (null != TktType) {
				ygPolicy.setTktType(TktType.getText());//B2B
			}
			
			Node AutoTicket = node.selectSingleNode("AutoTicket");
			if (null != AutoTicket) {
				ygPolicy.setAutoTicket(AutoTicket.getText());
			}
			
			Node Receipt = node.selectSingleNode("Receipt");
			if (null != Receipt) {
				ygPolicy.setReceipt(Receipt.getText());
			}
			
			Node PaymentType = node.selectSingleNode("PaymentType");
			if (null != PaymentType) {
				ygPolicy.setPaymentType(PaymentType.getText());
			}
			
			/*Node Remark = node.selectSingleNode("Remark");
			if (null != Remark) {
				ygPolicy.setRemark(Remark.getText());//无
			}*/
			ygPolicy.setRemark(root.selectSingleNode("/YeeGo.QueryPolicyByPNR_1_0/Policys/Policy/Remark").getText());
			
			Node RefundRule = node.selectSingleNode("RefundRule");
			if (null != RefundRule) {
				ygPolicy.setRefundRule(RefundRule.getText());
			}
			
			Node Efficiency = node.selectSingleNode("Efficiency");
			if (null != Efficiency) {
				ygPolicy.setEfficiency(Efficiency.getText());//0分钟20秒
			}
			
			Node TktWorktime = node.selectSingleNode("TktWorktime");
			if (null != TktWorktime) {
				ygPolicy.setTktWorktime(TktWorktime.getText());//00:00-23:59
			}
			
			Node RfdWorktime = node.selectSingleNode("RfdWorktime");
			if (null != RfdWorktime) {
				ygPolicy.setRfdWorktime(RfdWorktime.getText());//00:00-23:59
			}
			
			Node Price = node.selectSingleNode("Price");
			if (null != Price) {
				ygPolicy.setPrice(Price.getText());//710.00
			}
			
			Node SettlePrice = node.selectSingleNode("SettlePrice");
			if (null != SettlePrice) {
				ygPolicy.setSettlePrice(SettlePrice.getText());//0
			}
			Node IsSpecial = node.selectSingleNode("IsSpecial");
			if (null != IsSpecial) {
				ygPolicy.setIsSpecial(IsSpecial.getText());
			}
			
			Node WastWorkTime = node.selectSingleNode("WastWorkTime");
			if (null != WastWorkTime) {
				ygPolicy.setWastWorkTime(WastWorkTime.getText());
			}
			
			policys.add(ygPolicy);
			/*Node PolicyId = node.selectSingleNode("PolicyId");
			if (null != PolicyId) {
				policy.setPolicyId(PolicyId.getText());
			}

			Node PlatCode = node.selectSingleNode("PlatCode");
			if (null != PlatCode) {
				policy.setPlatCode(PlatCode.getText());
			}

			Node PlatCodeForShort = node
					.selectSingleNode("PlatCodeForShort");
			if (null != PlatCodeForShort) {
				policy.setPlatCode(PlatCodeForShort.getText());
			}

			Node TktOffice = node.selectSingleNode("TktOffice");
			if (null != TktOffice) {
				policy.setTktOffice(TktOffice.getText());
			}

			Node SwitchPnr = node.selectSingleNode("SwitchPnr");
			if (null != SwitchPnr) {
				policy.setSwitchPnr(SwitchPnr.getText());
			}

			Node FltStart = node.selectSingleNode("FltStart");
			if (null != FltStart) {
				// policy.setFltStart(FltStart.getText());
				policy.setFltStart(new java.util.Date());
			}

			Node FltEnd = node.selectSingleNode("FltEnd");
			if (null != FltEnd) {
				// policy.setFltEnd(FltEnd.getText());
				policy.setFltEnd(new java.util.Date());
			}

			Node PreTkt = node.selectSingleNode("PreTkt");
			if (null != PreTkt) {
				// policy.setPreTkt(PreTkt.getText());
				policy.setPreTkt(1);
			}

			Node TktStart = node.selectSingleNode("TktStart");
			if (null != TktStart) {
				// policy.setTktStart(TktStart.getText());
				policy.setTktStart(new java.util.Date());
			}

			Node TktEnd = node.selectSingleNode("TktEnd");
			if (null != TktEnd) {
				// policy.setTktEnd(TktEnd.getText());
				policy.setTktEnd(new java.util.Date());
			}

			Node Fare = node.selectSingleNode("Fare");
			if (null != Fare) {
				// policy.setFare(Double.parseDouble(Fare.getText()));
				policy.setFare(0.0);
			}

			Node Rebate = node.selectSingleNode("Rebate");
			if (null != Rebate) {
				// policy.setRebate(Double.parseDouble(Rebate.getText()));
				policy.setRebate(0);
			}

			Node TaxAmount = node.selectSingleNode("TaxAmount");
			if (null != TaxAmount) {
				policy.setTaxAmount(Double.parseDouble(TaxAmount.getText()));
			}

			Node CommAmount = node.selectSingleNode("CommAmount");
			if (null != CommAmount) {
				// policy.setCommAmount(Double.parseDouble(CommAmount.getText()));
				policy.setCommAmount(0.0);
			}

			Node PaymentFee = node.selectSingleNode("PaymentFee");
			if (null != PaymentFee) {
				// policy.setPaymentFee(Double.parseDouble(PaymentFee.getText()));
				policy.setPaymentFee(0.0);
			}

			Node CommRate = node.selectSingleNode("CommRate");
			if (null != CommRate) {
				policy.setCommRate(Double.parseDouble(CommRate.getText()));
			}

			Node FareBase = node.selectSingleNode("FareBase");
			if (null != FareBase) {
				policy.setFareBase(FareBase.getText());
			}

			Node TktType = node.selectSingleNode("TktType");
			if (null != TktType) {
				policy.setTktType(TktType.getText());
			}

			Node AutoTicket = node.selectSingleNode("AutoTicket");
			if (null != AutoTicket) {
				policy.setAutoTicket(AutoTicket.getText());
			}

			Node Receipt = node.selectSingleNode("Receipt");
			if (null != Receipt) {
				policy.setReceipt(Receipt.getText());
			}

			Node PaymentType = node.selectSingleNode("PaymentType");
			if (null != PaymentType) {
				policy.setPaymentType(PaymentType.getText());
			}

			Node Remark = node.selectSingleNode("Remark");
			if (null != Remark) {
				policy.setRemark(Remark.getText());
			}

			Node Efficiency = node.selectSingleNode("Efficiency");
			if (null != Efficiency) {
				// policy.setEfficiency(Efficiency.getText());
				policy.setEfficiency(0);
			}

			Node TktWorktime = node.selectSingleNode("TktWorktime");
			if (null != TktWorktime) {
				policy.setTktWorktime(TktWorktime.getText());
			}

			Node RfdWorktime = node.selectSingleNode("RfdWorktime");
			if (null != RfdWorktime) {
				policy.setRfdWorktime(RfdWorktime.getText());
			}
			
			Node IsSpecial = node.selectSingleNode("IsSpecial");
			if (null != IsSpecial) {
				policy.setIsSpecial(IsSpecial.getText());
			}
			policys.add(policy);
			 */
		}
		return policys;
	}
	
	
}
