package slfx.yg.open.inter.impl;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.pojo.dto.flight.FlightStopInfoDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEXmlRtPnrDTO;
import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsJsonResult;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.pojo.dto.supplier.yg.YGAskOrderTicketDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightPolicyDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderStatusDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.qo.client.PatFlightQO;
import slfx.jp.qo.client.PolicyByPnrQo;
import slfx.jp.qo.client.QueryWebFlightsQO;
import slfx.yg.open.inter.YGFlightService;
import slfx.yg.open.utils.YGUtil;
import slfx.yg.open.utils.YGXmlUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：易购航班查询SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:18:02
 * @版本：V1.0
 *
 */
@Service("ygFlightService")
public class YGFlightServiceImpl implements YGFlightService{
	
	@Autowired
	private YGUtil ygUtil;
	
	@Autowired
	private YGXmlUtil ygXmlUtil;
	
	public YGUtil getYgUtil() {
		return ygUtil;
	}

	public void setYgUtil(YGUtil ygUtil) {
		this.ygUtil = ygUtil;
	}

	public YGXmlUtil getYgXmlUtil() {
		return ygXmlUtil;
	}

	public void setYgXmlUtil(YGXmlUtil ygXmlUtil) {
		this.ygXmlUtil = ygXmlUtil;
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public QueryWebFlightsDTO queryFlights(QueryWebFlightsQO qo) {
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3(易购-运价云-航班查询接口)开始,qo=="+ JSON.toJSONString(qo));
		QueryWebFlightsDTO qoDto = new QueryWebFlightsDTO();//查询返回实体
	
		List<YGFlightDTO> list = null;
		StringBuffer sb = new StringBuffer();
		
		PostMethod post = ygUtil.getYJPostMethod();
		//设置参数
		sb.append("<QueryWebFlights_1_3>");
		sb.append("<From>"+(qo.getFrom() == null ? "":qo.getFrom())+"</From>");
		sb.append("<Arrive>"+(qo.getArrive() == null ? "" : qo.getArrive())+"</Arrive>");
		sb.append("<Date>"+(qo.getDateStr() == null ? "" : qo.getDateStr())+"</Date>");
		sb.append("<Carrier>"+(qo.getCarrier() == null ? "" : qo.getCarrier())+"</Carrier>");
		sb.append("<Time>"+(qo.getTimeStr() == null ? "" : qo.getTimeStr())+"</Time>");
		sb.append("<StopType>"+(qo.getStopType() == null ? "" : qo.getStopType())+"</StopType>");
		sb.append("<CmdShare>"+(qo.getCmdShare() == null ? "" : qo.getCmdShare())+"</CmdShare>");
		sb.append("<MaxNum>0</MaxNum>");
		sb.append("<UserSource>1</UserSource>");
		sb.append("<Policy>N</Policy>");
		sb.append("</QueryWebFlights_1_3>");
		
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "<Filter_1_0><DataSource>SYS</DataSource><ReturnType>B</ReturnType><Compress>N</Compress></Filter_1_0>");
		//post.setParameter("request", "<QueryWebFlights_1_2><From>PEK</From><Arrive>DLC</Arrive><Date>2014-11-25</Date><Carrier></Carrier><Time>0000</Time><StopType>D</StopType><CmdShare>1</CmdShare><Option/><MaxNum/><UserSource>1</UserSource></QueryWebFlights_1_2>");
		//post.setParameter("filter", "<Filter_1_0><DataFormat>X</DataFormat><DataSource>AG</DataSource><Compress>N</Compress></Filter_1_0>");
		
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));

		String result = ygUtil.sendPost(post);
		//System.out.println("-------------"+result);
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3--返回=="+result);
			
		Element root = ygXmlUtil.getRootElement(result);
		
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
			
HgLogger.getInstance().error("yangkang","QueryWebFlights_1_3--错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return null;

		} else if (root != null) {
			list = new ArrayList<YGFlightDTO>();
			QueryWebFlightsJsonResult queryResult = JSONObject.parseObject(root.getText(), QueryWebFlightsJsonResult.class);

			// 解析航空公司信息A
			JSONObject aObj = JSONObject.parseObject(queryResult.getA());
			HashMap<String, String> aMap = new HashMap<String, String>();
			for (String ak : aObj.keySet()) {
				JSONArray akArr = (JSONArray) aObj.get(ak);
				aMap.put(ak, akArr.get(0).toString());
			}

			// 解析机场信息P
			JSONObject pObj = JSONObject.parseObject(queryResult.getP());
			//机场代码map
			HashMap<String, String> pMap = new HashMap<String, String>();
			//出发到达城市代码map
			HashMap<String, String> cityCodeMap = new HashMap<String, String>();
			for (String pk : pObj.keySet()) {
				JSONArray pArr = (JSONArray) pObj.get(pk);
				pMap.put(pk, pArr.get(0).toString());
				cityCodeMap.put(pk,  pArr.get(3).toString());
			}

			// 解析航班日期字典D
			JSONObject dObj = JSONObject.parseObject(queryResult.getD());

			// 解析机型列表J
			HashMap<String, JSONArray> jMap = JSONObject.parseObject(queryResult.getJ(), HashMap.class);
			HashMap<String, String> airTypeMap = new HashMap<String, String>();// 解析后的机型MAP  key为机型代码  value为机型名称
			for (String key : jMap.keySet()) {
				String s = jMap.get(key).get(0).toString();
				airTypeMap.put(key, s);
			}

			// 解析退改签T
			JSONObject tObj = JSONObject.parseObject(queryResult.getT());
			// 退改签信息
			HashMap<String, String> tgqMap = new HashMap<String, String>();
			for (String tk : tObj.keySet()) {
				JSONArray tArr = (JSONArray) tObj.get(tk);
				// 拼接退改签ID下的信息
				String tgq = "退票规定: " + tArr.get(0) + "  改期规定: " + tArr.get(1) + "  签转规定: " + tArr.get(1);
				tgqMap.put(tk, tgq);
			}
			qoDto.setTgNoteMap(tgqMap);

			//解析每趟航班单程航线价格列表H
			JSONArray hArray = JSONArray.parseArray(queryResult.getH());
			//所有航班舱位信息  航班号座位KEY
			HashMap<String, List<SlfxFlightClassDTO>> airMap = new HashMap<String, List<SlfxFlightClassDTO>>();
			//每趟航班最低价舱位信息   航班号座位KEY
			HashMap<String, SlfxFlightClassDTO> cheapestClassMap = new HashMap<String, SlfxFlightClassDTO>();
			
			for (Object obj : hArray) {

				JSONArray arr = (JSONArray) obj;
				List<SlfxFlightClassDTO> flightClassList = new ArrayList<SlfxFlightClassDTO>();
				// 遍历某航班价格列表里每个舱位价格信息 jsonarray
				JSONArray airArr = (JSONArray) arr.get(3);
				for (Object airObj : airArr) {
					JSONArray arr2 = (JSONArray) airObj;
					// 创建舱位信息
					SlfxFlightClassDTO flightClass = new SlfxFlightClassDTO();
					flightClass.setOriginalPrice(Double.parseDouble(arr2.get(0).toString().trim()));// 票面价
					flightClass.setDiscount(Integer.parseInt(arr2.get(1).toString().trim()));// 折扣
					flightClass.setProxyCost(Double.parseDouble(arr2.get(2).toString().trim()));// 代理费
					flightClass.setAwardCost(Double.parseDouble(arr2.get(3).toString().trim()));//奖励
					flightClass.setSettleAccounts(Double.parseDouble(arr2.get(4).toString().trim()));//结算
					flightClass.setLastSeat(arr2.get(6).toString());// 剩余座位
					flightClass.setClassCode(arr2.get(7).toString());// 舱位代码
					flightClass.setClassType(arr2.get(8).toString());// 舱位类型
					flightClass.setTgNoteKey(arr2.get(9).toString());//退改签ID
					flightClass.setAllowChildren("Y".equals(arr2.get(11).toString())?true : false); //是否允许儿童预订 Y允许  N不允许
					flightClassList.add(flightClass);
					
				}
				// 航班号为key
				airMap.put(arr.get(0).toString(), flightClassList);
				
				SlfxFlightClassDTO fcd = new SlfxFlightClassDTO();
				fcd.setOriginalPrice(Double.parseDouble(arr.get(1).toString()));//舱位最低价
				fcd.setClassType(arr.get(2).toString());//舱位类型
				cheapestClassMap.put(arr.get(0).toString(), fcd);
				
			}

			//每趟航班列表信息 F
			JSONObject fObj = JSONObject.parseObject(queryResult.getF());
			for (String key : fObj.keySet()) {
				
				YGFlightDTO ygflightDto = new YGFlightDTO();
				ygflightDto.setFlightNo(key);
				ygflightDto.setCheapestFlightClass(cheapestClassMap.get(key));//最低价舱位
				String code = key.substring(0, 2);// 航空公司代码 CA1699
				ygflightDto.setAirCompName(aMap.get(code));
				ygflightDto.setCarrier(code);
				ygflightDto.setFlightClassList(airMap.get(key));
				
				// 航班信息
				String info = fObj.get(key).toString();
				JSONArray jasonArray = JSONArray.parseArray(info);
				for (int i = 0; i < jasonArray.size(); i++) {
					switch (i) {
					case 0:
						ygflightDto.setStartPort(jasonArray.get(i).toString());// 起飞机场代码
						ygflightDto.setStartPortName(pMap.get(jasonArray.get(i).toString()));// 出发机场简称
						ygflightDto.setStartCityCode(cityCodeMap.get(jasonArray.get(i).toString()));//出发城市代码
						break;
					case 1:
						ygflightDto.setEndPort(jasonArray.get(i).toString());// 到达机场代码
						ygflightDto.setEndPortName(pMap.get(jasonArray.get(i).toString()));// 到达机场简称
						ygflightDto.setEndCityCode(cityCodeMap.get(jasonArray.get(i).toString()));//到达城市代码
						break;
					case 2:
						ygflightDto.setStartDate(dObj.get(jasonArray.get(i).toString()).toString().replaceAll("/", "-"));
						break;// 起飞日期序号 转换为 出发日期 2014/06/16 格式化成 2014-06-16
					case 3:
						ygflightDto.setStartTime(jasonArray.get(i).toString());
						break;// 起飞时间
					case 4:
						ygflightDto.setEndDate(dObj.get(jasonArray.get(i).toString()).toString().replaceAll("/", "-"));
						break;// 到达日期序号 转换为 到达日期 2014/06/16 格式化成 2014-06-16
					case 5:
						ygflightDto.setEndTime(jasonArray.get(i).toString());
						break;// 到达时间
					case 6:
						ygflightDto.setFlyTime(Integer.valueOf(jasonArray.get(i).toString()));
						break;// 飞行时长（分钟）
					case 7:
						ygflightDto.setAircraftCode(jasonArray.get(i).toString());//机型代码
						ygflightDto.setAircraftName(airTypeMap.get(jasonArray.get(i).toString()));//机型名称
						break;
					case 8:
						ygflightDto.setShareFlightNum(jasonArray.get(i).toString());
						break;// 共享航班
					case 9:
						ygflightDto.setStopNum(Integer.valueOf(jasonArray.get(i).toString()));
						break;// 经停次数
					case 10:
						ygflightDto.setStartTerminal(jasonArray.get(i).toString());
						break;// 出发航站楼
					case 11:
						ygflightDto.setEndTerminal(jasonArray.get(i).toString());
						break;// 到达航站楼
					case 12:
						ygflightDto.setIsFood("1".equals(jasonArray.get(i).toString())?true : false);
						break;// 有无餐食
					case 13:
						Double airportFee = Double.parseDouble(jasonArray.get(i).toString());
						ygflightDto.setAirportTax(airportFee);
						break;// 机建费
					case 14:
						Double oilFee = Double.parseDouble(jasonArray.get(i).toString());
						ygflightDto.setFuelSurTax(oilFee);
						break;// 燃油费
					case 15:
						String mileageStr = jasonArray.get(i).toString();
						ygflightDto.setMileage(
									mileageStr == null?0:Integer.parseInt(mileageStr));
						break;// 里程
					case 16:
						ygflightDto.setYPrice(Double.parseDouble(jasonArray.get(i).toString()));
						break;// Y舱价格
					}
				}
				
				list.add(ygflightDto);
			}
			qoDto.setFlightList(list);
		}
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3(易购-运价云-航班查询接口)结束,dto=="+ JSON.toJSONString(qoDto));
		return qoDto;
	}

	
	@SuppressWarnings("static-access")
	@Override
	public List<SlfxFlightPolicyDTO> queryPolicyByPNR(PolicyByPnrQo qo) {
		
		HgLogger.getInstance().info("yangkang","QueryPolicyByPNR_1_0(易购-采购云-通过PNR比价接口)开始,qo=="+ JSON.toJSONString(qo));

		List<SlfxFlightPolicyDTO> policys = null;
		
		PostMethod post = ygUtil.getCGPostMethod();
		String requestParam = QueryPriceByPnrHelp.initPolicyRequestParam(qo);
		post.setParameter("request", requestParam);
//System.out.println("requestParam==========="+requestParam);
		post.setParameter("filter", "");
		
		HgLogger.getInstance().info("yangkang","QueryPolicyByPNR_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+requestParam+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
		HgLogger.getInstance().info("yangkang","QueryPolicyByPNR_1_0--返回=="+result);

		Element root = ygXmlUtil.getRootElement(result);
		if(root!=null){
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			if(root2!=null){
				if("0".equals(root2.selectSingleNode("/YeeGo.QueryPolicyByPNR_1_0/ErrorCode").getText())){
					List<YGFlightPolicyDTO> ygPolicys = QueryPriceByPnrHelp.parseXml(root2);
					
					//易购dto转化成平台dto tandeng 20140816 begin
					if(ygPolicys != null && ygPolicys.size() > 0){
						policys = new ArrayList<SlfxFlightPolicyDTO>();
						SlfxFlightPolicyDTO policyDTO = null;
						for (YGFlightPolicyDTO ygFlightPolicyDTO : ygPolicys) {
							policyDTO = new SlfxFlightPolicyDTO();
							
							if(ygFlightPolicyDTO.getPolicyId() != null){
								policyDTO.setPolicyId(ygFlightPolicyDTO.getPolicyId());
							}
							
							if(ygFlightPolicyDTO.getPlatCodeForShort() != null){
								policyDTO.setPlatCode(ygFlightPolicyDTO.getPlatCodeForShort());
							}

							if(ygFlightPolicyDTO.getPlatformName() != null){
								policyDTO.setPlatformName(ygFlightPolicyDTO.getPlatformName());
							}
							
							if(ygFlightPolicyDTO.getTktOffice() != null){
								policyDTO.setTktOffice(ygFlightPolicyDTO.getTktOffice());
							}
							
							if(ygFlightPolicyDTO.getSwitchPnr() != null){
								policyDTO.setSwitchPnr(ygFlightPolicyDTO.getSwitchPnr());
							}
							
							if(ygFlightPolicyDTO.getFltStart() != null){
								try{
									policyDTO.setFltStart(DateUtil.parseDate3(ygFlightPolicyDTO.getFltStart()));									
								}catch(Exception e){
									
								}
							}
							
							if(ygFlightPolicyDTO.getFltEnd() != null){
								try{
									policyDTO.setFltEnd(DateUtil.parseDate3(ygFlightPolicyDTO.getFltEnd()));									
								}catch(Exception e){
									
								}
							}
							
							if(ygFlightPolicyDTO.getPreTkt() != null && !"".equals(ygFlightPolicyDTO.getPreTkt())){
								try{
									policyDTO.setPreTkt(Integer.parseInt(ygFlightPolicyDTO.getPreTkt()));									
								}catch(Exception e){
									
								}
							}
							
							if(ygFlightPolicyDTO.getTktStart() != null){
								try{
									policyDTO.setTktStart(DateUtil.parseDate3(ygFlightPolicyDTO.getTktStart()));									
								}catch(Exception e){
									
								}
							}
							
							if(ygFlightPolicyDTO.getTktEnd() != null){
								try{									
									policyDTO.setTktEnd(DateUtil.parseDate3(ygFlightPolicyDTO.getTktStart()));
								}catch(Exception e){
									
								}
							}
							
							//仅供参考的价格(接口中返回的fare是空值，用price代替) tandeng 2014-08-19
							if(ygFlightPolicyDTO.getPrice() != null && !"".equals(ygFlightPolicyDTO.getPrice())){
								try{
									policyDTO.setFare(Double.parseDouble(ygFlightPolicyDTO.getPrice()));									
								}catch(Exception e){
								}
							}else{
								policyDTO.setFare(0.00);	
							}
							
							if(ygFlightPolicyDTO.getPrice() != null && !"".equals(ygFlightPolicyDTO.getPrice())){
								try{
									policyDTO.setTicketPrice(Double.parseDouble(ygFlightPolicyDTO.getPrice()));
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setTicketPrice(0.00);
							}
							
							if(ygFlightPolicyDTO.getRebate() != null && !"".equals(ygFlightPolicyDTO.getRebate())){
								try{
									policyDTO.setRebate(Integer.parseInt(ygFlightPolicyDTO.getRebate()));
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setRebate(0);
							}
							
							if(ygFlightPolicyDTO.getTaxAmount() != null && !"".equals(ygFlightPolicyDTO.getTaxAmount())){
								try{									
									policyDTO.setTaxAmount(Double.parseDouble(ygFlightPolicyDTO.getTaxAmount()));
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setTaxAmount(0.00);
							}
							
							if(ygFlightPolicyDTO.getCommAmount() != null && !"".equals(ygFlightPolicyDTO.getCommAmount())){
								try{
									policyDTO.setCommAmount(Double.parseDouble(ygFlightPolicyDTO.getCommAmount()));
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setCommAmount(0.00);
							}
							
							if(ygFlightPolicyDTO.getPaymentFee() != null && !"".equals(ygFlightPolicyDTO.getPaymentFee())){
								try{
									policyDTO.setPaymentFee(Double.parseDouble(ygFlightPolicyDTO.getPaymentFee()));									
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setPaymentFee(0.00);
							}
							
							if(ygFlightPolicyDTO.getCommRate() != null  && !"".equals(ygFlightPolicyDTO.getCommRate())){
								try{
									policyDTO.setCommRate(Double.parseDouble(ygFlightPolicyDTO.getCommRate()));									
								}catch(Exception e){
									
								}
							}else{
								policyDTO.setCommRate(0.00);
							}
							
							if(ygFlightPolicyDTO.getFareBase() != null){
								policyDTO.setFareBase(ygFlightPolicyDTO.getFareBase());
							}
							
							if(ygFlightPolicyDTO.getTktType() != null){
								policyDTO.setTktType(ygFlightPolicyDTO.getTktType());
							}
							
							if(ygFlightPolicyDTO.getAutoTicket() != null){
								policyDTO.setAutoTicket(ygFlightPolicyDTO.getAutoTicket());
							}
							
							if(ygFlightPolicyDTO.getReceipt() != null){
								policyDTO.setReceipt(ygFlightPolicyDTO.getReceipt());
							}
							
							if(ygFlightPolicyDTO.getPaymentType() != null){
								policyDTO.setPaymentType(ygFlightPolicyDTO.getPaymentType());
							}
							
							if(ygFlightPolicyDTO.getRemark() != null){
								policyDTO.setRemark(ygFlightPolicyDTO.getRemark());
							}
							
							if(ygFlightPolicyDTO.getEfficiency() != null){
								//3分钟15秒 转换成秒
								try{
									String temp = ygFlightPolicyDTO.getEfficiency();
									String temp1 =temp.substring(0, temp.indexOf("分"));
									String temp2 =temp.substring(temp.indexOf("钟")+1, temp.indexOf("秒"));
									Integer efficiency = Integer.parseInt(temp1) * 60 + Integer.parseInt(temp2);
									policyDTO.setEfficiency(efficiency);
								}catch(Exception e){
									policyDTO.setEfficiency(0);
								}
							}else{
								policyDTO.setEfficiency(0);
							}
							
							if(ygFlightPolicyDTO.getTktWorktime() != null){
								policyDTO.setTktWorktime(ygFlightPolicyDTO.getTktWorktime());
							}
							
							if(ygFlightPolicyDTO.getRfdWorktime() != null){
								policyDTO.setRfdWorktime(ygFlightPolicyDTO.getRfdWorktime());
							}
							
							/*
							policyDTO.setFuelSurTax(0.00);
							policyDTO.setAirportTax(0.00);
							*/
							
							if(ygFlightPolicyDTO.getIsSpecial() != null){
								policyDTO.setIsSpecial(ygFlightPolicyDTO.getIsSpecial());
							}
							
							if(ygFlightPolicyDTO.getWastWorkTime() != null){ // 格式：<WastWorkTime>23:59止</WastWorkTime>
								String suffix = "止"; 
								String wastWorkTime = ygFlightPolicyDTO.getWastWorkTime().trim();
								if (wastWorkTime.contains(suffix)) {
									wastWorkTime = wastWorkTime.replace(suffix, "");
								}
								policyDTO.setWastWorkTime(wastWorkTime.trim());
							}
							
							
							if(qo.getClassNo() != null){
								policyDTO.setClassCode(qo.getClassNo());
							}
							
							if (null != ygFlightPolicyDTO.getIsVip()) {
								policyDTO.setIsVip(ygFlightPolicyDTO.getIsVip());//10
							}
							
							if (null != ygFlightPolicyDTO.getPlatformType()) {
								policyDTO.setPlatformType(ygFlightPolicyDTO.getPlatformType());//P
							}
							
							policys.add(policyDTO);
						}
					}
					//易购dto转化成平台dto tandeng 20140816 end
					return policys;
				}else{
					/*hgLogger.error("qiuxianxiang", "调用  YeeGo.QueryPolicyByPNR_1_0(PNR比价接口) 失败,   " +
						     "ERROR CODE: "+root2.selectSingleNode("/YeeGo.QueryPolicyByPNR_1_0/ErrorCode").getText()+"    " +
						     "ERROR MSG: "+root2.selectSingleNode("/YeeGo.QueryPolicyByPNR_1_0/RrrorMsg").getText()+"  调用时间："+ new Date());*/
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		HgLogger.getInstance().info("yangkang","QueryPolicyByPNR_1_0--(易购-采购云-通过PNR比价接口)结束,dto=="+ JSON.toJSONString(policys));
		return policys;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public List<FlightStopInfoDTO> queryFlightStop(String flightNo, String DateStr) {
HgLogger.getInstance().info("yangkang","QueryFlightStop_1_0(易购-运价云-查询航班经停信息接口)开始,qo=="+flightNo+"-"+DateStr);
		List<FlightStopInfoDTO> list = new ArrayList<FlightStopInfoDTO>();
		
		StringBuffer sb = new StringBuffer(); 
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<QueryFlightStop_1_0>");
		sb.append("<FlightNo>"+(flightNo == null ? "" : flightNo)+"</FlightNo>");
		sb.append("<Date>"+(DateStr == null ? "" : DateStr)+"</Date>");
		sb.append("</QueryFlightStop_1_0>");
		
		PostMethod post = ygUtil.getYJPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "<Filter_1_0><DataFormat>J</DataFormat></Filter_1_0>");
		
HgLogger.getInstance().info("yangkang","QueryFlightStop_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
HgLogger.getInstance().info("yangkang","QueryFlightStop_1_0--返回=="+result);
		
		Element root = ygXmlUtil.getRootElement(result);
		
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
HgLogger.getInstance().error("yangkang","QueryFlightStop_1_0--错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return null;

		} else if (root != null) {

			JSONObject obj = (JSONObject) JSONObject.parseObject(root.getText()).get("CRS.CommandSet.FF");
			// 解析经停信息数组
			JSONArray arr = (JSONArray) obj.get("Items");
			if (arr != null) {
				for (Object o : arr) {
					
					FlightStopInfoDTO  dto  = new FlightStopInfoDTO();
					JSONArray oa = (JSONArray) o;
					dto.setAirportCode(oa.get(0).toString());//机场代码
					dto.setArriveTime(oa.get(1).toString());// 抵达时间
					dto.setDepartureTime(oa.get(2).toString());// 起飞时间
					dto.setAircraftCode(oa.get(3).toString());//机型代码
					list.add(dto);
				}
			}
		}
HgLogger.getInstance().info("yangkang","QueryFlightStop_1_0--(易购-运价云-查询航班经停信息接口)结束,dto=="+ JSON.toJSONString(list));
		return list;
	}

	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public HashMap<String, ABEPatFlightDTO> patByFlights(PatFlightQO qo) {
HgLogger.getInstance().info("yangkang","PatByFlights_1_0--(易购-WebABE-PAT报价接口)开始,qo=="+ JSON.toJSONString(qo));
		ABEPatFlightDTO patDTO = new ABEPatFlightDTO();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<PatByFlights_1_0>");
		sb.append("<PNR/>");
		sb.append("<Flights>");
		sb.append("<Flight>");
		sb.append("<BoardPoint>"+(qo.getStartPort() == null ? "" : qo.getStartPort())+"</BoardPoint>");
		sb.append("<OffPoint>"+(qo.getEndPort() == null ? "" : qo.getEndPort())+"</OffPoint>");
		sb.append("<Carrier>"+(qo.getCarrier() == null ? "" : qo.getCarrier())+"</Carrier>");
		sb.append("<FlightNo>"+(qo.getFlightNo() == null ? "" : qo.getFlightNo().substring(2,qo.getFlightNo().length()))+"</FlightNo>");
		sb.append("<DepartureDate>"+(qo.getStartDate() == null ? "" : qo.getStartDate())+"</DepartureDate>");
		sb.append("<DepartureTime>"+(qo.getStartTime() == null ? "" : qo.getStartTime())+"</DepartureTime>");
		sb.append("<ClassCode>"+(qo.getClassCode() == null ? "" : qo.getClassCode())+"</ClassCode>");
		sb.append("</Flight>");
		sb.append("</Flights>");
		sb.append("</PatByFlights_1_0>");
		
		PostMethod post = ygUtil.getYJPostMethod();
		post.setParameter("request", sb.toString());
		
HgLogger.getInstance().info("yangkang","PatByFlights_1_0--request参数=="+sb.toString());

		sb.setLength(0);
		sb.append("<Filter>");
		sb.append("<PsgTypes>ADT,CHD,INF</PsgTypes>");//成人 儿童 婴儿 三种乘客类型
		sb.append("<Office>"+ygUtil.getOffice()+"</Office>");
		sb.append("<CustomDepCode></CustomDepCode>");
		sb.append("</Filter>");
		post.setParameter("filter", sb.toString());
HgLogger.getInstance().info("yangkang","PatByFlights_1_0--identity参数=="+post.getParameter("identity")+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
HgLogger.getInstance().info("yangkang","PatByFlights_1_0返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
HgLogger.getInstance().error("yangkang","PatByFlights_1_0错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return null;
			
		}else if(root!=null){
			
			Element element = ygXmlUtil.getRootElement(root.getText());
			List<Node> nodes = element.selectNodes("/PatByFlights_1_0/PriceDetails/PriceDetail");
			
			HashMap<String, ABEPatFlightDTO> map = new HashMap<String, ABEPatFlightDTO>();
			for(Node node : nodes){
				patDTO.setFnno(node.selectSingleNode("FNNo").getText());
				patDTO.setPassangerType(node.selectSingleNode("PsgType").getText());
				patDTO.setAirportTax(Double.parseDouble(node.selectSingleNode("AirportTax").getText()));
				patDTO.setFuelSurTax(Double.parseDouble(node.selectSingleNode("FuelSurTax").getText()));
				patDTO.setTaxAmount(Double.parseDouble(node.selectSingleNode("TaxAmount").getText()));
				patDTO.setFacePar(Double.parseDouble(node.selectSingleNode("Fare").getText()));
				patDTO.setAmount(Double.parseDouble(node.selectSingleNode("Amount").getText()));
				map.put(patDTO.getPassangerType(), patDTO);
			}
HgLogger.getInstance().info("yangkang","PatByFlights_1_0--(易购-WebABE-PAT报价接口)结束,dto=="+ JSON.toJSONString(map));
			return map;
		}else{
HgLogger.getInstance().info("yangkang","PatByFlights_1_0--(易购-WebABE-PAT报价接口)结束,报价接口调用失败！");
			return null;
		}
	}

	
	@SuppressWarnings("static-access")
	@Override
	public ABEOrderFlightDTO abeOrderFlight(ABEOrderFlightCommand command) {
		
HgLogger.getInstance().info("yangkang","OrderFlight_2_0--(易购-WebABE-下单接口)开始,command=="+ JSON.toJSONString(command));
		ABEOrderFlightDTO dto = new ABEOrderFlightDTO();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<OrderFlight_2_0>");
			//sb.append("<Pnr/>");
			//航程列表
			sb.append("<Flights>")
				.append(ygXmlUtil.createABEFlightStr(command))
			  .append("</Flights>");
			
			//乘客列表
			sb.append("<Passengers>")
			     .append(ygXmlUtil.createABEPassengerStr(command))
		      .append("</Passengers>");
			
			//报价项列表
			sb.append("<Prices>")
			     .append(ygXmlUtil.createABEPriceStr(command, ygUtil))
			  .append("</Prices>");
			
			//订单信息描述
			sb.append("<OrderInfo>")
				 .append(ygXmlUtil.createABEOrderInfoStr(command));
			sb.append("</OrderInfo>");
			
			//联系信息
			sb.append("<LinkerInfo>")
			     .append(ygXmlUtil.createABELinkerInfoStr(command));//是否电子机票 Y/N
			sb.append("</LinkerInfo>");
			
			//保险信息
			sb.append("<InsuranceInfo><InsuranceId/><ShouldGath/><ShouldPay/><RetMoney/><Gain/><InsuranceCount/><InsuranceSummary/></InsuranceInfo>");
		sb.append("</OrderFlight_2_0>");
		
		PostMethod post = ygUtil.getYJPostMethod();
		post.setParameter("request", sb.toString());
		//System.out.println("request==========="+sb.toString());
		
		post.setParameter("filter", "<Filter_1_0><DataSource>ABE</DataSource><CustomDepCode/><EnableNoSeat/></Filter_1_0>");
		
//System.out.println("filter============"+"<Filter_1_0><DataSource>ABE</DataSource><CustomDepCode/><EnableNoSeat/></Filter_1_0>");
HgLogger.getInstance().info("yangkang","OrderFlight_2_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
HgLogger.getInstance().info("yangkang","OrderFlight_2_0--返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
HgLogger.getInstance().error("yangkang","OrderFlight_2_0错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return dto;
			
		}else if(root!=null){
			
			Element element = ygXmlUtil.getRootElement(root.getText());
			dto.setPnr(element.selectSingleNode("/OrderFlight_2_0/Pnr").getText());
			
			/*dto.setSubsOrderNo(element.selectSingleNode("/OrderFlight_2_0/SubsOrderNo").getText());
			dto.setPassengerCount(Integer.valueOf(element.selectSingleNode("/OrderFlight_2_0/PassengerCount").getText()));
			dto.setBalanceMoney(Double.valueOf(element.selectSingleNode("/OrderFlight_2_0/BalanceMoney").getText()));
			dto.setStatus(element.selectSingleNode("/OrderFlight_2_0/Status").getText());
			dto.setOrderSt(element.selectSingleNode("/OrderFlight_2_0/OrderSt").getText());
			dto.setFlowStep(element.selectSingleNode("/OrderFlight_2_0/FlowStep").getText());
			dto.setFlowStatus(element.selectSingleNode("/OrderFlight_2_0/FlowStatus").getText());
			dto.setModifyTag(element.selectSingleNode("/OrderFlight_2_0/ModifyTag").getText());
			
			String dateTimeStr = element.selectSingleNode("/OrderFlight_2_0/TicketLimitDt").getText()+" "+element.selectSingleNode("/OrderFlight_2_0/TicketLimitTime").getText();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			try {
				dto.setTicketLimitDateTime(sd.parse(dateTimeStr));
			} catch (ParseException e) {
				dto.setTicketLimitDateTime(null);
			}
			dto.setErrMsg(element.selectSingleNode("/OrderFlight_2_0/ErrMsg").getText());
			dto.setPlatformOrderNo(element.selectSingleNode("/OrderFlight_2_0/PlatformOrderNo").getText());
			dto.setPlatformTransID(element.selectSingleNode("/OrderFlight_2_0/PlatformTransID").getText());
			String totalPay = element.selectSingleNode("/OrderFlight_2_0/TotalPay").getText();
			dto.setTotalPay(totalPay.equals("")?null:Double.valueOf(totalPay));
			dto.setPlatformRemark(element.selectSingleNode("/OrderFlight_2_0/PlatformRemark").getText());
			 */
		}else{
			dto.setErrorCode("999999");
			dto.setErrorMsg("接口数据 XML解析失败");
			dto.setCallTime(new Date());
			dto.setInterfaceName("OrderFlight_2_0(ABE下单接口)");
		}
HgLogger.getInstance().info("yangkang","OrderFlight_2_0(易购-WebABE-下单接口)结束,dto=="+ JSON.toJSONString(dto));
		return dto;
	}

	
	@SuppressWarnings("static-access")
	@Override
	public YGAskOrderTicketDTO askOrderTicket(YGAskOrderTicketCommand command) {
		
		HgLogger.getInstance().info("yangkang","AskOrderTicket_1_0(易购-采购云-出票接口)开始,command=="+ JSON.toJSONString(command));
		
		YGAskOrderTicketDTO dto = new YGAskOrderTicketDTO();
		
		//组装请求参数
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<YeeGo.AskOrderTicket_1_0>");
		
		sb.append("<OrderNo>");
		sb.append((command.getOrderNo() == null ? "" : command.getOrderNo()));
		sb.append("</OrderNo>");
		
		sb.append("<PayType>");
		sb.append((command.getPayType() == null ? "" : command.getPayType()));
		sb.append("</PayType>");
		
		sb.append("<SupplierPayType>");
		sb.append((command.getSupplierPayType() == null ? "" : command.getSupplierPayType()));
		sb.append("</SupplierPayType>");
		
		sb.append("<Authoffice>");
		sb.append(command.getAuthoffice());
		sb.append("</Authoffice>");
		
		sb.append("<NotifyUrl>");
		sb.append((command.getNotifyUrl() == null ? "" : command.getNotifyUrl()));
		sb.append("</NotifyUrl>");
		
		sb.append("<PayReturnUrl>");
		sb.append((command.getPayReturnUrl() == null ? "" : command.getPayReturnUrl()));
		sb.append("</PayReturnUrl>");
		
		sb.append("</YeeGo.AskOrderTicket_1_0>");
		
		//http请求
		PostMethod post = ygUtil.getCGPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "");
		
		//获取返回结果并处理
		HgLogger.getInstance().info("yangkang","AskOrderTicket_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
		HgLogger.getInstance().info("yangkang","AskOrderTicket_1_0--返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		
		if(root!=null){
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			if(root2!=null){
				String tempErrorCode = root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/ErrorCode").getText().trim();
				if("0".equals(tempErrorCode)){
					dto.setErrorCode("0");
					dto.setOrderNo(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/OrderNo").getText());
					dto.setPaymentUrl(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/PaymentUrl").getText());
					dto.setPlatCode(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/PlatCode").getText());
					dto.setPlatOrderNo(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/PlatOrderNo").getText());
				}else{
					dto.setErrorCode(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/ErrorCode").getText());
					dto.setErrorMsg(root2.selectSingleNode("/YeeGo.AskOrderTicket_1_0/ErrorMsg").getText());
					dto.setInterfaceName("YeeGo.AskOrderTicket_1_0(出票接口)");
					dto.setCallTime(new Date());
					HgLogger.getInstance().error("yangkang","AskOrderTicket_1_0错误信息,ERROR CODE:=="+dto.getErrorCode()+",ERROR MSG=="+dto.getErrorMsg()+",调用时间=="+ new Date());			
				}
			}
		}
		HgLogger.getInstance().info("yangkang","AskOrderTicket_1_0(易购-采购云-出票接口)结束,dto=="+ JSON.toJSONString(dto));
		return dto;
	}

	
	@SuppressWarnings("static-access")
	@Override
	public YGRefundActionTypesDTO getRefundActionType(String platCode) {
		HgLogger.getInstance().info("yangkang","GetRefundActionType_1_0(易购-采购云-获取退废票种类接口)开始,qo=="+ JSON.toJSONString(platCode));	
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<GetRefundActionType_1_0>");
		sb.append("<PlatCode>"+(platCode == null ? "" : platCode)+"</PlatCode>");
		sb.append("</GetRefundActionType_1_0>");
		
		PostMethod post = this.ygUtil.getCGPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "");
		
		HgLogger.getInstance().info("yangkang","GetRefundActionType_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = this.ygUtil.sendPost(post);
		HgLogger.getInstance().info("yangkang","GetRefundActionType_1_0返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		
		if(root!=null){
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			if(root2!=null){
				String tempErrorCode = root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ErrorCode").getText().trim();
				//errorCode=0 成功
				if("0".equals(tempErrorCode)){
					
					YGRefundActionTypesDTO dto = new YGRefundActionTypesDTO();
					List<YGRefundActionTypeDTO> list = new ArrayList<YGRefundActionTypeDTO>();
					
					String refundType = root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ActionTypes/RefundType").getText();
					String typeCodes = root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ActionTypes/ActionTypeCode").getText();
					String typeTxts = root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ActionTypes/ActionTypeTxt").getText();
					
					String[] refundTypeArray = refundType.split("\\|");
					String[] typeCodeArray = typeCodes.split("\\|");
					String[] typeTxtArray = typeTxts.split("\\|");
					
					for(int i=0; i<typeCodeArray.length; i++){
						YGRefundActionTypeDTO tDto = new YGRefundActionTypeDTO();
						tDto.setRefundType(refundTypeArray[i]);
						tDto.setActionTypeCode(typeCodeArray[i]);
						tDto.setActionTypeTxt(typeTxtArray[i]);
						list.add(tDto);
					}
					dto.setActionTypeList(list);
					dto.setErrorCode(root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ErrorCode").getText());
					HgLogger.getInstance().info("yangkang","GetRefundActionType_1_0(易购查询航班)结束,dto=="+ JSON.toJSONString(dto));
					return dto;
				}else{
					YGRefundActionTypesDTO dto = new YGRefundActionTypesDTO();
					dto.setErrorCode(root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ErrorCode").getText());
					dto.setErrorMsg(root2.selectSingleNode("/YeeGo.GetRefundActionType_1_0/ErrorMsg").getText());
					dto.setCallTime(new Date());
					dto.setInterfaceName("YeeGo.GetRefundActionType_1_0(查询退废票种类接口)");
					HgLogger.getInstance().error("yangkang","GetRefundActionType_1_0错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));			
					return dto;
				}
			}else{
				YGRefundActionTypesDTO dto = new YGRefundActionTypesDTO();
				dto.setErrorCode("99999999");
				dto.setErrorMsg("接口数据 XML解析失败");
				dto.setCallTime(new Date());
				dto.setInterfaceName("YeeGo.GetRefundActionType_1_0(查询退废票种类接口)");
				HgLogger.getInstance().error("yangkang","GetRefundActionType_1_0--接口数据 XML解析失败");
				return dto;
			}
		}else{
			YGRefundActionTypesDTO dto = new YGRefundActionTypesDTO();
			dto.setErrorCode("99999999");
			dto.setErrorMsg("接口数据 XML解析失败");
			dto.setCallTime(new Date());
			dto.setInterfaceName("YeeGo.GetRefundActionType_1_0(查询退废票种类接口)");
			HgLogger.getInstance().error("yangkang","GetRefundActionType_1_0--接口数据 XML解析失败");
			return dto;
		}
		
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public YGFlightOrderDTO orderByPnr(YGOrderCommand yGOrderCommand) {

		HgLogger.getInstance().info("yangkang","OrderByPnr_1_0(易购-采购云-通过PNR创建订单)开始,command=="+ JSON.toJSONString(yGOrderCommand));
		
		YGFlightOrderDTO dto = new YGFlightOrderDTO();
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<YeeGo.OrderByPnr_1_0>");
			sb.append(ygXmlUtil.createYGFlightOrderStr(yGOrderCommand, ygUtil));
			sb.append(ygXmlUtil.createYGFlightStr(yGOrderCommand, ygUtil));
			sb.append(ygXmlUtil.createYGFlightPassengersStr(yGOrderCommand, ygUtil));
			sb.append("</YeeGo.OrderByPnr_1_0>");
			
			PostMethod post = ygUtil.getCGPostMethod();
			post.setParameter("request", sb.toString());
			post.setParameter("filter", "");
			HgLogger.getInstance().info("yangkang","OrderByPnr_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
			String result = ygUtil.sendPost(post);
			HgLogger.getInstance().info("yangkang","OrderByPnr_1_0返回=="+result);
			
			Element root =  ygXmlUtil.getRootElement(result);

			if(root!=null){
				Element root2 = ygXmlUtil.getRootElement(root.getText());
				if(root2 != null){
					String tempErrorCode = root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/ErrorCode").getText().trim();
					//errorCode=0 成功
					if("0".equals(tempErrorCode)){
						
						dto.setErrorCode(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/ErrorCode").getText());
						dto.setErrorMsg(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/ErrorMsg").getText());
						dto.setPlatCode(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/PlatCode").getText());
						dto.setSupplierOrderNo(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/PlatOrderNo").getText());
						dto.setYgOrderNo(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/OrderNo").getText());
						dto.setSupplierName(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/PlatformName").getText());
						dto.setPnr(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/Pnr").getText());
						dto.setTicketOffice(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/TicketOffice").getText());
						dto.setPsgCount(Integer.parseInt(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/PsgCount").getText()));
						dto.setBalanceMoney(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/BalanceMoney").getText()));
						dto.setFare(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/Fare").getText()));
						dto.setTaxAmount(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/TaxAmount").getText()));
						dto.setCommAmount(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/CommAmount").getText()));
						dto.setCommRate(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/CommRate").getText()));
						dto.setCommMoney(Double.parseDouble(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/CommMoney").getText()));
						
						//易购接口返回乘机人数为null或者为0，直接使用平台乘机人数
						if(dto.getPsgCount() == null || dto.getPsgCount() == 0){
							dto.setPsgCount(yGOrderCommand.getPassengers().size());
						}
						return dto;
					}else{
						dto.setErrorCode(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/ErrorCode").getText());
						dto.setErrorMsg(root2.selectSingleNode("/YeeGo.OrderByPnr_1_0/ErrorMsg").getText());
						dto.setCallTime(new Date());
						dto.setInterfaceName("YeeGo.OrderByPnr_1_0(采购云-创建订单)");
						HgLogger.getInstance().error("yangkang","OrderByPnr_1_0(易购-采购云-通过PNR创建订单) XML解析失败");
						return dto;
					}
				}else{
					dto.setErrorCode("99999999");
					dto.setErrorMsg("接口数据 XML解析失败");
					dto.setCallTime(new Date());
					dto.setInterfaceName("YeeGo.OrderByPnr_1_0(采购云-创建订单)");
					HgLogger.getInstance().error("yangkang","OrderByPnr_1_0(易购-采购云-通过PNR创建订单) XML解析失败");
					return dto;
				}
			}else{
				dto.setErrorCode("99999999");
				dto.setErrorMsg("采购云-创建订单获取返回xml失败");
				dto.setCallTime(new Date());
				dto.setInterfaceName("YeeGo.OrderByPnr_1_0(采购云-创建订单)");
				HgLogger.getInstance().error("yangkang","OrderByPnr_1_0(易购-采购云-通过PNR创建订单) XML解析失败");
				return dto;
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("yangkang","OrderByPnr_1_0(易购-采购云-通过PNR创建订单) XML解析失败");
		}
		
		return dto;		
	}

	
	@SuppressWarnings("static-access")
	@Override
    public YGQueryOrderStatusDTO queryOrderStatus(final String orderNo) {
HgLogger.getInstance().info("yangkang","QueryOrderStatus_1_0(易购-采购云-订单状态查询接口)开始,qo=="+ JSON.toJSONString(orderNo));
	   
		YGQueryOrderStatusDTO dto = new YGQueryOrderStatusDTO();
		
       //组装请求参数
       StringBuilder sb = new StringBuilder();
       sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
       sb.append("<YeeGo.QueryOrderStatus_1_0>");
       
       sb.append("<OrderNo>");
       sb.append((orderNo == null ? "" : orderNo));
       sb.append("</OrderNo>");
       
       sb.append("</YeeGo.QueryOrderStatus_1_0>");
       
       //http请求
       PostMethod post = ygUtil.getCGPostMethod();
       post.setParameter("request", sb.toString());
       post.setParameter("filter", "");
HgLogger.getInstance().info("yangkang","QueryOrderStatus_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
       //获取返回结果并处理
       String result = ygUtil.sendPost(post);
HgLogger.getInstance().info("yangkang","QueryOrderStatus_1_0返回=="+result);

       Element root =  ygXmlUtil.getRootElement(result);
       if(root!=null){
    	   Element root2 = ygXmlUtil.getRootElement(root.getText());
    	   if(root2!=null){
    		   String tempErrorCode = root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/ErrorCode").getText().trim();
    		   //调用成功
    		   if("0".equals(tempErrorCode)){
    			   dto.setErrorCode("0");
    			   dto.setStatus(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/Status").getText());
                   dto.setRemark(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/Remark").getText());
                   dto.setPlatCode(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/PlatCode").getText());
                   dto.setPlatOrderNo(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/PlatOrderNo").getText());
                   dto.setOrderNo(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/OrderNo").getText());
                   dto.setPnr(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/Pnr").getText());
HgLogger.getInstance().info("yangkang","QueryOrderStatus_1_0(易购-采购云-订单状态查询接口)结束,dto=="+ JSON.toJSONString(dto));
                   return dto;
    		   }else{
    			   dto.setErrorCode("0");
    			   dto.setErrorMsg(root2.selectSingleNode("/YeeGo.QueryOrderStatus_1_0/ErrorMsg").getText());
    			   dto.setCallTime(new Date());
    			   dto.setInterfaceName("YeeGo.QueryOrderStatus_1_0(预订单状态查询接口)");
HgLogger.getInstance().error("yangkang","QueryOrderStatus_1_0(易购-采购云-订单状态查询接口)结束,ERROR CODE: "+dto.getErrorCode()+"    ERROR MSG: "+dto.getErrorMsg()+"  调用时间："+ new Date());
                   return dto;
    		   }
    	   }else{
    		   	dto.setErrorCode("999999");
    		   	dto.setErrorMsg("接口数据 XML解析失败");
				dto.setCallTime(new Date());
				dto.setInterfaceName("YeeGo.QueryOrderStatus_1_0(预订单状态查询接口)");
HgLogger.getInstance().error("yangkang","QueryOrderStatus_1_0(易购-采购云-订单状态查询接口)结束--接口数据 XML解析失败");
				return dto;
    	   }
       }else{
    	   	dto.setErrorCode("999999");
    	   	dto.setErrorMsg("接口数据 XML解析失败");
			dto.setCallTime(new Date());
			dto.setInterfaceName("YeeGo.QueryOrderStatus_1_0(预订单状态查询接口)");
HgLogger.getInstance().error("yangkang","QueryOrderStatus_1_0(易购-采购云-订单状态查询接口)结束--接口数据 XML解析失败");
			return dto;
       }
   }

	
	@SuppressWarnings("static-access")
	@Override
	public YGApplyRefundDTO applyRefund(YGApplyRefundCommand command){
		
		HgLogger.getInstance().info("yangkang","ApplyRefund_1_0(易购-采购云-退废票接口)开始,command=="+ JSON.toJSONString(command));
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<YeeGo.ApplyRefund_1_0>");
		sb.append("<OrderNo>"+(command.getOrderNo() == null ? "" : command.getOrderNo())+"</OrderNo>");
		sb.append("<ActionType>"+(command.getActionType() == null ? "" : command.getActionType())+"</ActionType>");
		sb.append("<TicketNos>"+(command.getTicketNos() == null ? "" : command.getTicketNos())+"</TicketNos>");
		sb.append("<Segment/>");
		sb.append("<Reason>"+(command.getReason() == null ? "" : command.getReason())+"</Reason>");
		sb.append("<RefundType>"+(command.getRefundType() == null ? "" : command.getRefundType())+"</RefundType>");
		sb.append("<NoticeUrl>"+(command.getNoticeUrl() == null ? "" : command.getNoticeUrl())+"</NoticeUrl>");
		sb.append("</YeeGo.ApplyRefund_1_0>");
		
		PostMethod post = ygUtil.getCGPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "");
		HgLogger.getInstance().info("yangkang","ApplyRefund_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
		HgLogger.getInstance().info("yangkang","ApplyRefund_1_0返回=="+result);
		
		YGApplyRefundDTO dto = new YGApplyRefundDTO();
		
		Element root =  ygXmlUtil.getRootElement(result);
		if(root!=null){
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			if(root2!=null){
				String tempErrorCode = root2.selectSingleNode("/YeeGo.ApplyRefund_1_0/ErrorCode").getText().trim();
				//成功
				if("0".equals(tempErrorCode)){
					dto.setErrorCode("0");
					dto.setRefundOrderNo(root2.selectSingleNode("/YeeGo.ApplyRefund_1_0/RefundOrderNo").getText());
					dto.setRefundPlatformOrderNo(root2.selectSingleNode("/YeeGo.ApplyRefund_1_0/RefundPlatformOrderNo").getText());
					HgLogger.getInstance().info("yangkang","ApplyRefund_1_0(易购-采购云-退废票接口)结束,dto=="+ JSON.toJSONString(dto));
					return dto;
				}else{
					dto.setErrorCode(root2.selectSingleNode("/YeeGo.ApplyRefund_1_0/ErrorCode").getText());
					dto.setErrorMsg(root2.selectSingleNode("/YeeGo.ApplyRefund_1_0/ErrorMsg").getText());
					dto.setInterfaceName("YeeGo.ApplyRefund_1_0(申请退废票接口)");
					dto.setCallTime(new Date());
					HgLogger.getInstance().error("yangkang","ApplyRefund_1_0(易购-采购云-退废票接口)结束,ERROR CODE: "+dto.getErrorCode()+"    ERROR MSG: "+dto.getErrorMsg()+"  调用时间："+ new Date());
					return dto;
				}
			}else{
				dto.setErrorCode("999999");
				dto.setErrorMsg("接口数据 XML解析失败");
				dto.setCallTime(new Date());
				dto.setInterfaceName("YeeGo.ApplyRefund_1_0(申请退废票接口)");
				HgLogger.getInstance().error("yangkang","ApplyRefund_1_0(易购-采购云-退废票接口)结束--接口数据 XML解析失败");
				return dto;
			}
		}else{
			dto.setErrorCode("999999");
			dto.setErrorMsg("接口数据 XML解析失败");
			dto.setInterfaceName("YeeGo.ApplyRefund_1_0(申请退废票接口)");
			dto.setCallTime(new Date());
			HgLogger.getInstance().error("yangkang","ApplyRefund_1_0(易购-采购云-退废票接口)结束--接口数据 XML解析失败");
			return dto;
		}
	}
	
	public static void main(String[] args) {
		String temp = "3分钟150秒";
		String temp1 =temp.substring(0, temp.indexOf("分"));
		String temp2 =temp.substring(temp.indexOf("钟")+1, temp.indexOf("秒"));
		Integer efficiency = Integer.parseInt(temp1) * 60 + Integer.parseInt(temp2);
		System.out.println(efficiency);
	}

	@SuppressWarnings("static-access")
	@Override
	public ABEDeletePnrDTO deletePnr(ABEDeletePnrCommand command) {

HgLogger.getInstance().info("yangkang","DeletePnr_1_0(易购-WebABE-删除订座记录接口)开始,command=="+ JSON.toJSONString(command));
		
		ABEDeletePnrDTO dto =  new ABEDeletePnrDTO();
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<DeletePnr_1_0>");
		sb.append("<PNR>"+(command.getPnr() == null ? "" : command.getPnr())+"</PNR>");
		sb.append("<Office>"+(ygUtil.getOffice() == null ? "" : ygUtil.getOffice())+"</Office>");
		sb.append("<CheckDZ>N</CheckDZ>");
		sb.append("<OrderNo></OrderNo>");
		sb.append("<SPID></SPID>");
		sb.append("</DeletePnr_1_0>");
			
		PostMethod post = ygUtil.getYJPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "<Filter_1_0><DataSource>ABE</DataSource><CustomDepCode/></Filter_1_0>");
		
HgLogger.getInstance().info("yangkang","DeletePnr_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		
		String result = ygUtil.sendPost(post);
		
HgLogger.getInstance().info("yangkang","DeletePnr_1_0返回=="+result);
		
		Element element =  ygXmlUtil.getRootElement(result);
		if (element != null && element.getText().indexOf("<ErrorInfo_1_0>") > 0) {
			String code = element.selectSingleNode("/ErrorInfo_1_0/Code").getText();
			// PNR 已经被删除
			if("10021".equalsIgnoreCase(code)){
				dto.setStatus("Y");
				dto.setMsg(element.selectSingleNode("/ErrorInfo_1_0/Content").getText());
HgLogger.getInstance().info("yangkang","DeletePnr_1_0信息=="+JSON.toJSONString(dto));
				return dto;
			}
			
HgLogger.getInstance().error("yangkang","DeletePnr_1_0错误信息=="+ygXmlUtil.parseErrorInfo(element.getText()));
			return null;
			
		}else if(element!=null){
			Element element2 = ygXmlUtil.getRootElement(element.getText());
			dto.setStatus(element2.selectSingleNode("/DeletePnr_1_0/Status").getText());
			dto.setMsg(element2.selectSingleNode("/DeletePnr_1_0/Msg").getText());
HgLogger.getInstance().info("yangkang","DeletePnr_1_0(易购-WebABE-删除订座记录接口)结束,dto=="+ JSON.toJSONString(dto));
			return dto;
		}else{
			dto.setErrorCode("999999");
			dto.setErrorMsg("接口数据 XML解析失败");
			dto.setCallTime(new Date());
			dto.setInterfaceName("DeletePnr_1_0(ABE删除订座记录接口)");
HgLogger.getInstance().info("yangkang","DeletePnr_1_0(易购-WebABE-删除订座记录接口)结束,dto=="+ JSON.toJSONString(dto));		
			return dto;
		}
		
	}

	@SuppressWarnings("static-access")
	@Override
	public ABEXmlRtPnrDTO xmlRtPnr(String pnr) {
		HgLogger.getInstance().info("yangkang","XmlRtPnr_1_1(易购-WebABE-获取 XML格式 PNR接口)开始,qo=="+ JSON.toJSONString(pnr));
		ABEXmlRtPnrDTO dto = new ABEXmlRtPnrDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<XmlRtPnr_1_1>");
		sb.append("<Pnr>"+(pnr == null ? "" : pnr)+"</Pnr>");
		sb.append("<Office>"+(ygUtil.getOffice() == null ? "" : ygUtil.getOffice())+"</Office>");
		sb.append("<HostResult>Y</HostResult>");
		sb.append("</XmlRtPnr_1_1>");
		//System.out.println("xmlRtPnr-----"+sb.toString());
		PostMethod post = ygUtil.getYJPostMethod();   
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "");
		HgLogger.getInstance().info("yangkang","XmlRtPnr_1_1--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
		HgLogger.getInstance().info("yangkang","XmlRtPnr_1_1返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
			HgLogger.getInstance().error("yangkang","XmlRtPnr_1_1错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return null;
			
		}else if(root!=null){
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			dto.setHostText(root2.selectSingleNode("/CRS.CommandSet.PNR/HostText").getText());
			
			DefaultElement defaultElement = (DefaultElement)root2.selectSingleNode("/CRS.CommandSet.PNR/PNRs/PNR");
			dto.setPnrNo(defaultElement.attributeValue("No"));
			
			HgLogger.getInstance().info("yangkang","XmlRtPnr_1_1(易购-WebABE-获取 XML格式 PNR接口)结束,dto=="+ JSON.toJSONString(dto));
			return dto;
		}else{
			HgLogger.getInstance().error("yangkang","XmlRtPnr_1_1(易购-WebABE-获取 XML格式 PNR接口)结束--接口数据 XML解析失败");
			return null;
		}
	}

	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public YGQueryOrderDTO queryOrder(String orderNo) {
		
HgLogger.getInstance().info("yangkang","QueryOrder_1_0(易购-采购云-订单查询接口)开始,qo=="+ JSON.toJSONString(orderNo));
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<YeeGo.QueryOrder_1_0>");
		sb.append("<OrderNo>"+(orderNo == null ? "" : orderNo)+"</OrderNo>");
		sb.append("</YeeGo.QueryOrder_1_0>");
		
		PostMethod post = ygUtil.getCGPostMethod();
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "");

HgLogger.getInstance().info("yangkang","QueryOrder_1_0--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
		System.out.println("result======="+result);
HgLogger.getInstance().info("yangkang","QueryOrder_1_0返回=="+result);
		
		Element root =  ygXmlUtil.getRootElement(result);
		
		YGQueryOrderDTO dto = new YGQueryOrderDTO();
		
		if(root!=null){
			
			Element root2 = ygXmlUtil.getRootElement(root.getText());
			if(root2!=null){
				
				String tempErrorCode = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/ErrorCode").getText().trim();
				//成功
				if("0".equals(tempErrorCode)){
					
					dto.setErrorCode("0");
					
					String BalanceMoney = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/BalanceMoney").getText();
					Double balanceMoney = (BalanceMoney != null ? Double.parseDouble(BalanceMoney): 0D);
					dto.setBalanceMoney(balanceMoney);
					
					String CommAmount = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/CommAmount").getText();
					if(CommAmount != null && !"".equals(CommAmount)){
						dto.setCommAmount(Double.parseDouble(CommAmount));						
					}else{
						dto.setCommAmount(0.00D);			
					}
					
					String Fare = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/Fare").getText();
					Double fare = (Fare != null ? Double.parseDouble(Fare): 0D);
					dto.setFare(fare);
					
					dto.setOrderNo(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/OrderNo").getText());
					dto.setPayAccount(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/PayAccount").getText());
					
					String PayMoney = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/PayMoney").getText();
					Double pm = (PayMoney != null ? Double.parseDouble(PayMoney): 0D);
					dto.setPayMoney(pm);
					
					dto.setPayType(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/PayType").getText());
					dto.setPlatOrderNo(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/PlatOrderNo").getText());
					dto.setPnr(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/Pnr").getText());
					dto.setPolicyId(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/PolicyId").getText());
					//dto.setStatus(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/Status").getText());
					
					//String Status = root2.selectSingleNode("/YeeGo.QueryOrder_1_0/Status").getText();
					//Double t = (Status != null ? Double.parseDouble(Status):0D);
					//dto.setTaxAmount(t);
					
					dto.setTradeNo(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/TradeNo").getText());
					
					Map<String,String> ticket = new HashMap<String,String>();
					
					List<Node> nodes = root2.selectNodes("/YeeGo.QueryOrder_1_0/Tickets/Ticket");
					for (Node node : nodes) {
						//Node psgNameNode = node.selectSingleNode("/YeeGo.QueryOrder_1_0/Tickets/Ticket/Name");
						Node cardNode = node.selectSingleNode("/YeeGo.QueryOrder_1_0/Tickets/Ticket/CardNo");
						Node ticketNode = node.selectSingleNode("/YeeGo.QueryOrder_1_0/Tickets/Ticket/TicketNo");
						if (null != cardNode && null != ticketNode) {
							//String psgName = psgNameNode.getText();
							String cardNo = cardNode.getText();
							String ticketNo = ticketNode.getText();
							
							ticket.put(cardNo, ticketNo);
						}
					}
					dto.setTicket(ticket);
HgLogger.getInstance().info("yangkang","QueryOrder_1_0(易购-采购云-订单查询接口)结束,dto=="+ JSON.toJSONString(dto));
					return dto;
				}else{
					dto.setErrorCode(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/ErrorCode").getText());
					dto.setErrorMsg(root2.selectSingleNode("/YeeGo.QueryOrder_1_0/ErrorMsg").getText());
					dto.setInterfaceName("YeeGo.QueryOrder_1_0(订单查询接口)");
					dto.setCallTime(new Date());
HgLogger.getInstance().error("yangkang","QueryOrder_1_0--调用 订单查询接口失败,ERROR CODE: "+dto.getErrorCode()+"    ERROR MSG: "+dto.getErrorMsg()+"  调用时间："+ new Date());
					return dto;
				}
			}else{
				dto.setErrorCode("999999");
				dto.setErrorMsg("接口数据 XML解析失败");
				dto.setInterfaceName("YeeGo.QueryOrder_1_0(订单查询接口)");
				dto.setCallTime(new Date());
HgLogger.getInstance().error("yangkang","QueryOrder_1_0--接口数据 XML解析失败");
				return dto;
			}
		}else{
			dto.setErrorCode("999999");
			dto.setErrorMsg("接口数据 XML解析失败");
			dto.setInterfaceName("YeeGo.QueryOrder_1_0(订单查询接口)");
			dto.setCallTime(new Date());
HgLogger.getInstance().error("yangkang","QueryOrder_1_0--接口数据 XML解析失败");
			return dto;
		}
		
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public QueryWebFlightsDTO queryFlightsV2(QueryWebFlightsQO qo) {
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3(易购-运价云-航班查询接口)开始,qo=="+ JSON.toJSONString(qo));
		
		QueryWebFlightsDTO qoDto = new QueryWebFlightsDTO();//查询返回实体
	
		List<YGFlightDTO> list = null;
		StringBuffer sb = new StringBuffer();
		
		PostMethod post = ygUtil.getYJPostMethod();
		//设置参数
		sb.append("<QueryWebFlights_1_3>");
		sb.append("<From>"+(qo.getFrom() == null ? "" : qo.getFrom())+"</From>");
		sb.append("<Arrive>"+(qo.getArrive() == null ? "" : qo.getArrive())+"</Arrive>");
		sb.append("<Date>"+(qo.getDateStr() == null ? "" : qo.getDateStr())+"</Date>");
		sb.append("<Carrier>"+(qo.getCarrier() == null ? "" : qo.getCarrier())+"</Carrier>");
		sb.append("<Time>"+(qo.getTimeStr() == null ? "" : qo.getTimeStr())+"</Time>");
		sb.append("<StopType>"+(qo.getStopType() == null ? "" : qo.getStopType())+"</StopType>");
		sb.append("<CmdShare>"+(qo.getCmdShare() == null ? "" : qo.getCmdShare())+"</CmdShare>");
		sb.append("<MaxNum>0</MaxNum>");
		sb.append("<UserSource>1</UserSource>");
		sb.append("<PrdType>R</PrdType>"); //V2格式返回
		sb.append("<Policy>N</Policy>");
		sb.append("</QueryWebFlights_1_3>");
		
		post.setParameter("request", sb.toString());
		post.setParameter("filter", "<Filter_1_0><DataSource>SYS</DataSource><ReturnType>B</ReturnType><Compress>N</Compress></Filter_1_0>");
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3--identity参数=="+post.getParameter("identity")+"\n\r,request参数=="+sb.toString()+"\n\r,filter参数=="+post.getParameter("filter"));
		String result = ygUtil.sendPost(post);
HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3返回=="+result);
			
		Element root = ygXmlUtil.getRootElement(result);
		
		if (root != null && root.getText().indexOf("<ErrorInfo_1_0>") > 0) {
HgLogger.getInstance().error("yangkang","QueryWebFlights_1_3错误信息=="+ygXmlUtil.parseErrorInfo(root.getText()));
			return null;

		} else if (root != null) {
			list = new ArrayList<YGFlightDTO>();
			QueryWebFlightsJsonResult queryResult = JSONObject.parseObject(root.getText(), QueryWebFlightsJsonResult.class);

			// 解析航空公司信息A
			JSONObject aObj = JSONObject.parseObject(queryResult.getA());
			HashMap<String, String> aMap = new HashMap<String, String>();
			for (String ak : aObj.keySet()) {
				JSONArray akArr = (JSONArray) aObj.get(ak);
				aMap.put(ak, akArr.get(0).toString());
			}

			// 解析机场信息P
			JSONObject pObj = JSONObject.parseObject(queryResult.getP());
			//机场代码map
			HashMap<String, String> pMap = new HashMap<String, String>();
			//出发到达城市代码map
			HashMap<String, String> cityCodeMap = new HashMap<String, String>();
			for (String pk : pObj.keySet()) {
				JSONArray pArr = (JSONArray) pObj.get(pk);
				pMap.put(pk, pArr.get(0).toString());
				cityCodeMap.put(pk,  pArr.get(3).toString());
			}

			// 解析航班日期字典D
			JSONObject dObj = JSONObject.parseObject(queryResult.getD());

			// 解析机型列表J
			HashMap<String, JSONArray> jMap = JSONObject.parseObject(queryResult.getJ(), HashMap.class);
			HashMap<String, String> airTypeMap = new HashMap<String, String>();// 解析后的机型MAP  key为机型代码  value为机型名称
			for (String key : jMap.keySet()) {
				String s = jMap.get(key).get(0).toString();
				airTypeMap.put(key, s);
			}

			// 解析退改签T
			JSONObject tObj = JSONObject.parseObject(queryResult.getT());
			// 退改签信息
			HashMap<String, String> tgqMap = new HashMap<String, String>();
			for (String tk : tObj.keySet()) {
				JSONArray tArr = (JSONArray) tObj.get(tk);
				// 拼接退改签ID下的信息
				String tgq = "退票规定: " + tArr.get(0) + "  改期规定: " + tArr.get(1) + "  签转规定: " + tArr.get(1);
				tgqMap.put(tk, tgq);
			}
			qoDto.setTgNoteMap(tgqMap);

			//解析每趟航班单程航线价格列表H
			JSONObject hObj = JSONObject.parseObject(queryResult.getH());
			
			//所有航班舱位信息  航班号座位KEY
			HashMap<String, List<SlfxFlightClassDTO>> airMap = new HashMap<String, List<SlfxFlightClassDTO>>();
			//每趟航班最低价舱位信息   航班号座位KEY
			HashMap<String, SlfxFlightClassDTO> cheapestClassMap = new HashMap<String, SlfxFlightClassDTO>();
			
			for(String key : hObj.keySet()){
				JSONArray jsonArray = (JSONArray)hObj.get(key);
				
				List<SlfxFlightClassDTO> flightClassList = new ArrayList<SlfxFlightClassDTO>();
				for (Object obj : jsonArray) {
					JSONArray arr = (JSONArray) obj;
					// 创建舱位信息
					SlfxFlightClassDTO flightClass = new SlfxFlightClassDTO();
					flightClass.setOriginalPrice(Double.parseDouble(arr.get(0).toString().trim()));// 票面价
					flightClass.setDiscount(Integer.parseInt(arr.get(1).toString().trim()));// 折扣
					flightClass.setProxyCost(Double.parseDouble(arr.get(2).toString().trim()));// 代理费
					flightClass.setAwardCost(Double.parseDouble(arr.get(3).toString().trim()));//奖励
					flightClass.setSettleAccounts(Double.parseDouble(arr.get(4).toString().trim()));//结算
					flightClass.setLastSeat(arr.get(6).toString());// 剩余座位
					flightClass.setClassCode(arr.get(7).toString());// 舱位代码
					flightClass.setClassType(arr.get(8).toString());// 舱位类型
					flightClass.setTgNoteKey(arr.get(9).toString());//退改签ID
					flightClass.setAllowChildren("Y".equals(arr.get(11).toString())?true : false); //是否允许儿童预订 Y允许  N不允许
					flightClassList.add(flightClass);
				}
				// 航班号为key
				airMap.put(key, flightClassList);
				//最低价的舱位
				if(flightClassList==null||flightClassList.size()==0)
					cheapestClassMap.put(key, null);
				else
					cheapestClassMap.put(key, flightClassList.get(0));
			}
			
			
			//每趟航班列表信息 F
			JSONObject fObj = JSONObject.parseObject(queryResult.getF());
			for (String key : fObj.keySet()) {
				
				YGFlightDTO ygflightDto = new YGFlightDTO();
				ygflightDto.setFlightNo(key);
				String code = key.substring(0, 2);// 航空公司代码 CA1699
				ygflightDto.setAirCompName(aMap.get(code));
				ygflightDto.setCarrier(code);
				//对应航班舱位列表是否为空
				if(airMap.get(key).size()==0||airMap.get(key)==null){
					continue;
				}
				ygflightDto.setFlightClassList(airMap.get(key));
				ygflightDto.setCheapestFlightClass(cheapestClassMap.get(key));//最低价舱位
				
				// 航班信息
				String info = fObj.get(key).toString();
				JSONArray jasonArray = JSONArray.parseArray(info);
				for (int i = 0; i < jasonArray.size(); i++) {
					switch (i) {
					case 0:
						ygflightDto.setStartPort(jasonArray.get(i).toString());// 起飞机场代码
						ygflightDto.setStartPortName(pMap.get(jasonArray.get(i).toString()));// 出发机场简称
						ygflightDto.setStartCityCode(cityCodeMap.get(jasonArray.get(i).toString()));//出发城市代码
						break;
					case 1:
						ygflightDto.setEndPort(jasonArray.get(i).toString());// 到达机场代码
						ygflightDto.setEndPortName(pMap.get(jasonArray.get(i).toString()));// 到达机场简称
						ygflightDto.setEndCityCode(cityCodeMap.get(jasonArray.get(i).toString()));//到达城市代码
						break;
					case 2:
						ygflightDto.setStartDate(dObj.get(jasonArray.get(i).toString()).toString().replaceAll("/", "-"));
						break;// 起飞日期序号 转换为 出发日期 2014/06/16 格式化成 2014-06-16
					case 3:
						ygflightDto.setStartTime(jasonArray.get(i).toString());
						break;// 起飞时间
					case 4:
						ygflightDto.setEndDate(dObj.get(jasonArray.get(i).toString()).toString().replaceAll("/", "-"));
						break;// 到达日期序号 转换为 到达日期 2014/06/16 格式化成 2014-06-16
					case 5:
						ygflightDto.setEndTime(jasonArray.get(i).toString());
						break;// 到达时间
					case 6:
						ygflightDto.setFlyTime(Integer.valueOf(jasonArray.get(i).toString()));
						break;// 飞行时长（分钟）
					case 7:
						ygflightDto.setAircraftCode(jasonArray.get(i).toString());//机型代码
						ygflightDto.setAircraftName(airTypeMap.get(jasonArray.get(i).toString()));//机型名称
						break;
					case 8:
						ygflightDto.setShareFlightNum(jasonArray.get(i).toString());
						break;// 共享航班
					case 9:
						ygflightDto.setStopNum(Integer.valueOf(jasonArray.get(i).toString()));
						break;// 经停次数
					case 10:
						ygflightDto.setStartTerminal(jasonArray.get(i).toString());
						break;// 出发航站楼
					case 11:
						ygflightDto.setEndTerminal(jasonArray.get(i).toString());
						break;// 到达航站楼
					case 12:
						ygflightDto.setIsFood("1".equals(jasonArray.get(i).toString())?true : false);
						break;// 有无餐食
					case 13:
						Double airportFee = Double.parseDouble(jasonArray.get(i).toString());
						ygflightDto.setAirportTax(airportFee);
						break;// 机建费
					case 14:
						Double oilFee = Double.parseDouble(jasonArray.get(i).toString());
						ygflightDto.setFuelSurTax(oilFee);
						break;// 燃油费
					case 15:
						String mileageStr = jasonArray.get(i).toString();
						ygflightDto.setMileage(
									mileageStr == null?0:Integer.parseInt(mileageStr));
						break;// 里程
					case 16:
						ygflightDto.setYPrice(Double.parseDouble(jasonArray.get(i).toString()));
						break;// Y舱价格
					}
				}
				
				list.add(ygflightDto);
			}
			qoDto.setFlightList(list);
		}

HgLogger.getInstance().info("yangkang","QueryWebFlights_1_3(易购-运价云-航班查询接口)结束,dto=="+ JSON.toJSONString(qoDto));
		return qoDto;
	}
}
