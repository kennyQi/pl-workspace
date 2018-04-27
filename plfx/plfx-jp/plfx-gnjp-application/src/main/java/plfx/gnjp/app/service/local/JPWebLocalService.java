package plfx.gnjp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.SysProperties;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.gnjp.app.common.util.EntityConvertUtils;
import plfx.gnjp.app.component.FlightCacheManager;
import plfx.gnjp.app.dao.FlightTicketDAO;
import plfx.gnjp.app.dao.GNJPOrderDAO;
import plfx.gnjp.app.dao.PassengerDAO;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.gnjp.domain.model.order.GNJPOrderStatus;
import plfx.gnjp.domain.model.order.GNJPPassenger;
import plfx.jp.app.dao.AirCompanyDAO;
import plfx.jp.app.dao.AirPortCodeDAO;
import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.domain.model.AirCompany;
import plfx.jp.domain.model.AirPortCode;
import plfx.jp.pojo.system.OrderLogConstants;
import plfx.jp.qo.api.AirCompanyQO;
import plfx.jp.qo.api.AirPortCodeQO;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.yeexing.constant.JPNofityMessageConstant;
import plfx.yeexing.inter.IBEServicePortType;
import plfx.yeexing.pojo.command.message.JPNotifyMessageApiCommand;
import plfx.yeexing.pojo.command.order.JPAutoOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPBookTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPCancelTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundQueryOrderStatusSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundTicketSpiCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingCabinDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingCancelTicketDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightPolicyDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerInfoDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceInfoDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingQueryWebFlightsDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingRefundTicketDTO;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingRefundQueryOrderDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;
import plfx.yeexing.qo.api.JPFlightSpiQO;
import plfx.yeexing.qo.api.JPPolicySpiQO;
import plfx.yeexing.util.IBEService;
import plfx.yeexing.util.SignTool;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class JPWebLocalService extends
		BaseServiceImpl<GNJPOrder, PlatformOrderQO, GNJPOrderDAO> {

	IBEService client = new IBEService();
	IBEServicePortType ibeServicePortType = client.getIBEServiceHttpPort();

	@Autowired
	private GNJPOrderDAO jpOrderDAO;
	
	@Autowired
	private AirPortCodeDAO airPortCodeDAO;
	
	@Autowired
	private AirCompanyDAO airCompanyDAO;

	@Autowired
	private PassengerDAO passengerDAO;
		
	@Autowired
	private FlightTicketDAO flightTicketDAO;

	@Autowired
	private GNJPOrderLocalService jpOrderLocalService;

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	private JPOrderLogLocalService jPOrderLogLocalService;
	/**
	 * 平台缓存航班信息
	 */
	@Autowired
	private FlightCacheManager flightCacheManager;

	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Resource
	private RabbitTemplate template;

	@Override
	protected GNJPOrderDAO getDao() {
		// TODO Auto-generated method stub
		return jpOrderDAO;
	}

	/****
	 * 查询航班列表
	 */
	public YeeXingQueryWebFlightsDTO queryFlightList(JPFlightSpiQO qo) {
		String result = null;
		HgLogger.getInstance().info(
				"yaosanfeng",
				"JPWebLocalService->queryFlightList->JPFlightQO[查询航班列表]:"
						+ JSON.toJSONString(qo));
		if (qo == null || null == qo.getOrgCity() || null == qo.getDstCity()
				|| null == qo.getStartDate()) {
			HgLogger.getInstance().error("yaosanfeng",
					"机票航班列表查询参数(出发地,目的地,出发时间)不能为空");
		}
//		//将出发机场名称转成三字码
//		AirPortCodeQO orgAirPortCodeQO1 = new AirPortCodeQO();
//		orgAirPortCodeQO1.setAirName(qo.getOrgCity());
//		AirPortCode orgAirPortCode1 = airPortCodeDAO.queryUnique(orgAirPortCodeQO1);
//		if(orgAirPortCode1 != null){
//			qo.setOrgCity(orgAirPortCode1.getAirCode());
//		}else{
//			HgLogger.getInstance().info("yuqz",
//					"JPWebLocalService->queryFlightList->出发机场名称转成三字码失败->[没有该机场]");
//			return null;
//		}
		//将出发航空公司名字改成二字码
		if(StringUtils.isNotBlank(qo.getAirCompany()) || StringUtils.isNotBlank(qo.getAirCompanyShotName())){
			AirCompanyQO airCompanyQO1 = new AirCompanyQO();
			if(StringUtils.isNotBlank(qo.getAirCompany())){
				airCompanyQO1.setAirCompanyName(qo.getAirCompany());
			}
			if(StringUtils.isNotBlank(qo.getAirCompanyShotName())){
				airCompanyQO1.setAirCompanyShotName(qo.getAirCompanyShotName());
			}
			AirCompany airCompany = airCompanyDAO.queryUnique(airCompanyQO1);
			if(airCompany != null){
				qo.setAirCompany(airCompany.getAirCompanyCode());
			}else{
				HgLogger.getInstance().info("yuqz",
						"JPWebLocalService->queryFlightList->出发航空名称转成二字码失败->[没有该航空公司]");
			}
		}
//		//将目的地机场名称转成三字码
//		AirPortCodeQO dstAirPortCodeQO1 = new AirPortCodeQO();
//		dstAirPortCodeQO1.setAirName(qo.getDstCity());
//		AirPortCode dstAirPortCode1 = airPortCodeDAO.queryUnique(dstAirPortCodeQO1);
//		if(dstAirPortCode1 != null){
//			qo.setDstCity(dstAirPortCode1.getAirCode());
//		}else{
//			HgLogger.getInstance().info("yuqz",
//					"JPWebLocalService->queryFlightList->目的地机场名称转成三字码失败->[没有该机场]");
//			return null;
//		}
		Map<String, String> params = new HashMap<String, String>();
		//获取cc配置的用户名和密码
		qo.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
		qo.setKey(SysProperties.getInstance().get("yeeXing_key"));
		params.put("getUserName", qo.getUserName() == null ? "" : qo.getUserName());
		params.put("getOrgCity", qo.getOrgCity() == null ? "" : qo.getOrgCity());
		params.put("getDstCity", qo.getDstCity() == null ? "" : qo.getDstCity());
		params.put("getStartDate", qo.getStartDate() == null ? "" : qo.getStartDate());
		params.put("getStartDateTime",
				qo.getStartTime() == null ? "" : qo.getStartTime());
		params.put("getAirCompany",
				qo.getAirCompany() == null ? "" : qo.getAirCompany());
		String sign = SignTool.sign(params, qo.getKey());
		HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->queryFlightList->[易行航班查询参数]:param="
				+ params + ",sign=" + sign + ",key=" + qo.getKey());
		// 调用易行天下查询航班接口查询数据
		result = ibeServicePortType.queryFlight(qo.getOrgCity(), qo
				.getDstCity(), qo.getStartDate(),
				qo.getStartTime() == null ? "" : qo.getStartTime(), qo
						.getAirCompany() == null ? "" : qo.getAirCompany(),
				qo.getUserName(), sign);
		// result = ibeServicePortType.queryFlight("SHA", "PEK", "2015-07-20",
		// "", "", "ply365", sign);
		HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->queryFlightList->[易行航班查询result]=" + JSON.toJSONString(result));
		// 解析result放入dto做相应的业务处理 解析xml
		YeeXingQueryWebFlightsDTO dto = new YeeXingQueryWebFlightsDTO();// 查询返回实体
		List<YeeXingFlightDTO> flightList = new ArrayList<YeeXingFlightDTO>();
		try {
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			for (Element e : childrenList) {
				if (e.getName().equals("is_success")) {
					dto.setIs_success(e.getText());
				} else if (e.getName().equals("error")) { // 如果请求要求没按接口参数传,易行会返回错误信息
					if (StringUtils.isNotBlank(e.getText())) {
						dto.setError(e.getText().trim());
					}
				}else if (e.getName().equals("buildFee")) {
					if (StringUtils.isBlank(e.getText())) {
						dto.setBuildFee(0.00d);
					} else {
						// 参考机场建设费：50.00 截取数字
						String[] buildFreeStr = e.getText().split("：");
						Double buildFeee = Double.parseDouble(buildFreeStr[1]);
						dto.setBuildFee(buildFeee);
						HgLogger.getInstance()
								.info("yaosanfeng",
										"JPWebLocalService->queryFlightList->[易行天下航班查询接口开始]->机场建设费"
												+ buildFeee);
					}
				} else if (e.getName().equals("oilFee")) {
					if (StringUtils.isBlank(e.getText())) {
						dto.setOilFee(0.00d);
					} else {
						String[] oilFeeStr = e.getText().split("：");
						Double oilFee = Double.parseDouble(oilFeeStr[1]);
						dto.setOilFee(oilFee);
						HgLogger.getInstance().info("yaosanfeng",
								"JPWebLocalService->queryFlightList->[易行天下航班查询接口开始]->燃油费" + oilFee);
					}
				} else if (e.getName().equals("flights")) {
					for (Iterator i = e.elementIterator(); i.hasNext();) {
						YeeXingFlightDTO flightDTO = new YeeXingFlightDTO();
						Element flightElement = (Element) i.next();
						if (flightElement.getName().equals("flight")) {
							flightDTO.setOrgCity(flightElement
									.attributeValue("orgCity"));
							flightDTO.setDepartTerm(flightElement
									.attributeValue("departTerm"));
							flightDTO.setDstCity(flightElement
									.attributeValue("dstCity"));
							flightDTO.setArrivalTerm(flightElement
									.attributeValue("arrivalTerm"));
							flightDTO.setAirComp(flightElement
									.attributeValue("airComp"));
							flightDTO.setFlightno(flightElement
									.attributeValue("flightno"));
							flightDTO.setPlaneType(flightElement
									.attributeValue("planeType"));
							flightDTO.setStartTime(flightElement
									.attributeValue("startTime"));
							flightDTO.setEndTime(flightElement
									.attributeValue("endTime"));
							flightDTO.setStopNumber(flightElement
									.attributeValue("stopNumber"));
							flightDTO.setMealCode(flightElement
									.attributeValue("mealCode"));
							Element cabinsElement = flightElement
									.element("cabins");
							List<YeeXingCabinDTO> cabinList = new ArrayList<YeeXingCabinDTO>();
							for (Iterator i_cabin = cabinsElement
									.elementIterator(); i_cabin.hasNext();) {
								YeeXingCabinDTO yxCabinDTO = new YeeXingCabinDTO();
								Element e_cabin = (Element) i_cabin.next();
								if (e_cabin.getName().equals("cabin")) {
									yxCabinDTO.setCabinCode(e_cabin
											.attributeValue("cabinCode"));
									yxCabinDTO.setCabinType(e_cabin
											.attributeValue("cabinType"));
									yxCabinDTO.setCabinDiscount(e_cabin
											.attributeValue("cabinDiscount"));
									yxCabinDTO.setCabinName(e_cabin
											.attributeValue("cabinName"));
									yxCabinDTO.setCabinSales(e_cabin
											.attributeValue("cabinSales"));
									yxCabinDTO.setIbePrice(e_cabin
											.attributeValue("ibePrice"));
									yxCabinDTO.setEncryptString(e_cabin
											.attributeValue("encryptString"));
									cabinList.add(yxCabinDTO);
									HgLogger.getInstance()
											.info("yaosanfeng",
													"JPWebLocalService-->queryFlightList->[易行天下航班查询接口开始]->YXCabinDTO"
															+ JSON.toJSONString(yxCabinDTO));
								}
							}
							flightDTO.setCabinList(cabinList);
							HgLogger.getInstance().info(
									"yaosanfeng",
									"JPWebLocalService-->queryFlightList->[易行天下航班查询接口开始]->cabinList"
											+ JSON.toJSONString(cabinList));
							//设置出发机场名称
							AirPortCodeQO orgAirPortCodeQO = new AirPortCodeQO();
							orgAirPortCodeQO.setAirCode(flightDTO.getOrgCity());
							AirPortCode orgAirPortCode = airPortCodeDAO.queryUnique(orgAirPortCodeQO);
							if(orgAirPortCode != null){
								flightDTO.setOrgCityName(orgAirPortCode.getAirName());
							}
							//设置到达机场名称
							AirPortCodeQO dstAirPortCodeQO = new AirPortCodeQO();
							dstAirPortCodeQO.setAirCode(flightDTO.getDstCity());
							AirPortCode dstAirPortCode = airPortCodeDAO.queryUnique(dstAirPortCodeQO);
							if(dstAirPortCode != null){
								flightDTO.setDstCityName(dstAirPortCode.getAirName());
							}
							//设置航空公司名称
							AirCompanyQO airCompanyQO = new AirCompanyQO();
							airCompanyQO.setAirCompanyCode(flightDTO.getAirComp());
							AirCompany airCompany = airCompanyDAO.queryUnique(airCompanyQO);
							if(airCompany != null){
								flightDTO.setAirCompanyName(airCompany.getAirCompanyName());
								flightDTO.setAirCompanyShotName(airCompany.getAirCompanyShotName());
							}
							
							flightList.add(flightDTO);
							HgLogger.getInstance().info(
									"yaosanfeng",
									"JPWebLocalService-->queryFlightList->[易行天下航班查询接口开始]-->YXFlightDTO"
											+ JSON.toJSONString(flightDTO));
						}
					}
					//System.out.println("flightList:" + JSON.toJSONString(flightList));
					dto.setFlightList(flightList);
				}
			}
			HgLogger.getInstance().info(
					"yaosanfeng",
					"JPWebLocalService-->queryFlightList->[易行天下航班列表查询结束]->YeeXingQueryWebFlightsDTO"
							+ JSON.toJSONString(dto));
			// 先将航班信息存入缓存
			this.flightListPutCache(dto);
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng",
					"JPWebLocalService-->queryFlightList->[易行天下航班列表查询异常]" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return dto;
	}

	/***
	 * 
	 * @方法功能说明：将航班信息存入缓存
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月30日下午3:46:00
	 * @修改内容：
	 * @参数：@param qoDto
	 * @return:void
	 * @throws
	 */
	private void flightListPutCache(YeeXingQueryWebFlightsDTO dto) {
		List<YeeXingFlightDTO> tempFlightList = dto.getFlightList();
		if (null != tempFlightList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date current = new Date();
			Date start = null;
			YeeXingFlightDTO yxFlightDTO = null;
			for (int i = tempFlightList.size() - 1; i >= 0; i--) {
				yxFlightDTO = tempFlightList.get(i);
				try {
					if (null != yxFlightDTO) {
						String startTime = yxFlightDTO.getStartTime();// 时间格式:
																		// 2012-07-20
																		// 07:30
						start = sdf.parse(startTime);
						if (start.before(current)) {
							tempFlightList.remove(i);
						}
					}
				} catch (Exception e) {
					HgLogger.getInstance().error(
							"yaosanfeng",
							"JPWebLocalService->flightListPutCache->exception[查询航班列表]:"
									+ HgLogger.getStackTrace(e));
				}
			}
		}
		dto.setFlightList(tempFlightList);
		// 将查询的航班信息缓存到redis
		if (dto != null && dto.getFlightList() != null
				&& dto.getFlightList().size() > 0) {
			flightCacheManager.refresh(dto.getFlightList());
		}

	}

	/****
	 * 查询政策
	 */
	public YeeXingFlightPolicyDTO queryPolicy(JPPolicySpiQO qo) {
		YeeXingFlightPolicyDTO policyDTO = new YeeXingFlightPolicyDTO();
		try {
				String result = null;
				HgLogger.getInstance().info(
						"yaosanfeng",
						"JPWebLocalService->queryPolicy->JPPolicySpiQO[平台价格政策查询开始]:"
								+ JSON.toJSONString(qo));
				if (null == qo || null == qo.getTickType()
						|| null == qo.getEncryptString()) {
					HgLogger.getInstance().error("yaosanfeng",
							"机票政策查询参数(票号类型 ,加密字符串)不能为空");
				}

				Map<String, String> params = new HashMap<String, String>();
				//获取cc配置的用户名和密码
				qo.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
				qo.setKey(SysProperties.getInstance().get("yeeXing_key"));
				params.put("getUserName", qo.getUserName() == null ? "" : qo.getUserName());
				params.put("getEncryptString", qo.getEncryptString() == null ? "" : qo.getEncryptString());
				params.put("getAirpGet",
						qo.getAirpGet() == null ? "" : qo.getAirpGet());
				params.put("getAirpSource",
						qo.getAirpSource() == null ? "" : qo.getAirpSource());
				params.put("getTickType", qo.getTickType() == null ? "" : qo.getTickType() + "");

				String sign = SignTool.sign(params, qo.getKey());
				// 调用易行天下查询航班接口查询数据
				HgLogger.getInstance().info(
						"yaosanfeng",
						"JPWebLocalService->queryPolicy->[易行政策查询参数]:params="
							+ params + ",sign=" + sign + ",key=" + qo.getKey());
				result = ibeServicePortType.queryAirpolicy(qo.getUserName(),
						qo.getEncryptString(),
						qo.getAirpGet() == null ? "" : qo.getAirpGet(),
						qo.getAirpSource() == null ? "" : qo.getAirpSource(),
						qo.getTickType().toString(), sign);
				HgLogger.getInstance().info(
						"yaosanfeng",
						"JPWebLocalService->queryPolicy->[易行政策查询result]="
								+ JSON.toJSONString(result));
				Document document = DocumentHelper.parseText(result);
				Element root = document.getRootElement();
				List<Element> childrenList = root.elements();
				for (Element e : childrenList) {
					if (e.getName().equals("is_success")) {
						if (e.getText().equals("T")) {
							policyDTO.setIs_success(e.getText());
						} else {
							policyDTO.setIs_success(e.getText());
							HgLogger.getInstance().error(
									"yaosanfeng",
									"JPWebLocalService->queryPolicy->[该次操作是否成功(T:成功F:失败)]"
											+ e.getText());
						}
					} else if (e.getName().equals("error")) { // 如果请求要求没按接口参数传,易行会返回错误信息
						if (StringUtils.isNotBlank(e.getText())) {
							policyDTO.setError(e.getText().trim());
						}
					} else if (e.getName().equals("priceList")) {
						for (Iterator i = e.elementIterator(); i.hasNext();) {
							Element pricelisElement = (Element) i.next();
								policyDTO.setBuildFee(Double
										.parseDouble(pricelisElement
												.attributeValue("buildFee")));
							policyDTO.setIsHighestPrice(pricelisElement
									.attributeValue("isHighestPrice"));
							policyDTO.setIsOneCabinManyPrice(pricelisElement
									.attributeValue("isOneCabinManyPrice"));
								policyDTO.setOilFee(Double.parseDouble(pricelisElement
										.attributeValue("oilFee")));
							Element priceElement = pricelisElement
									.element("priceinfos");
							List<YeeXingPriceInfoDTO> priceInfoList = new ArrayList<YeeXingPriceInfoDTO>();
							for (Iterator i_price = priceElement.elementIterator(); i_price
									.hasNext();) {
								YeeXingPriceInfoDTO yeeXingPriceInfoDTO = new YeeXingPriceInfoDTO();
								Element price = (Element) i_price.next();
								yeeXingPriceInfoDTO.setChangePnr(price
										.attributeValue("changePnr"));
									yeeXingPriceInfoDTO.setCommPayPrice(Double
											.parseDouble(price
													.attributeValue("commPayPrice")));	
								yeeXingPriceInfoDTO.setCredit(price
										.attributeValue("credit"));
									yeeXingPriceInfoDTO.setDisc(Double
											.parseDouble(price.attributeValue("disc")));
								yeeXingPriceInfoDTO.setEncryptString(price
										.attributeValue("encryptString"));
									yeeXingPriceInfoDTO.setExtReward(Double
											.parseDouble(price
													.attributeValue("extReward")));
									yeeXingPriceInfoDTO.setIbePrice(Double
											.parseDouble(price
													.attributeValue("ibePrice")));
									yeeXingPriceInfoDTO.setIbePricec(Double
											.parseDouble(price
													.attributeValue("ibePricec")));
								yeeXingPriceInfoDTO.setIndemnity(price
										.attributeValue("indemnity"));
								yeeXingPriceInfoDTO.setInvalid(price
										.attributeValue("invalid"));
									yeeXingPriceInfoDTO.setIsSphigh(Double
											.parseDouble(price
													.attributeValue("isSphigh")));
								yeeXingPriceInfoDTO.setMemo(price
										.attributeValue("memo"));
								yeeXingPriceInfoDTO.setOutTime(price
										.attributeValue("outTime"));
								yeeXingPriceInfoDTO.setPayType(price
										.attributeValue("payType"));
									yeeXingPriceInfoDTO.setPlcid(Integer.parseInt(price
											.attributeValue("plcid")));
								yeeXingPriceInfoDTO.setRefund(price
										.attributeValue("refund"));
								yeeXingPriceInfoDTO.setRestReturnTime(price
										.attributeValue("restReturnTime"));
								yeeXingPriceInfoDTO.setRestWorkTime(price
										.attributeValue("restWorkTime"));
									yeeXingPriceInfoDTO.setTickPrice(Double
											.parseDouble(price
													.attributeValue("tickPrice")));
								yeeXingPriceInfoDTO.setTickType(price
										.attributeValue("tickType"));
								yeeXingPriceInfoDTO.setWorkReturnTime(price
										.attributeValue("workReturnTime"));
								yeeXingPriceInfoDTO.setWorkTime(price
										.attributeValue("workTime"));
								//如果政策支持支付宝支付，并且当前时间是该供应商的上班时间才加到政策里面
								boolean b = isWorkTime(yeeXingPriceInfoDTO.getWorkTime(), yeeXingPriceInfoDTO.getRestWorkTime());
								if(yeeXingPriceInfoDTO.getPayType().contains("1") && b){
									priceInfoList.add(yeeXingPriceInfoDTO);
								}
							}
							HgLogger.getInstance().info(
									"yaosanfeng",
									"JPWebLocalService->queryPolicy->[易行天下政策查询]-->priceInfoList"
											+ JSON.toJSONString(priceInfoList));
							policyDTO.setPricesList(priceInfoList);
						}
					}
				}
				HgLogger.getInstance().info(
							"yaosanfeng",
							"JPWebLocalService->queryPolicy->[易行天下政策查询结束]->易行政策:YeeXingFlightPolicyDTO"
									+ JSON.toJSONString(policyDTO));

				} catch (Exception e) {
					HgLogger.getInstance().error("yaosanfeng",
							"JPWebLocalService->queryPolicy->[易行天下政策查询异常]" + HgLogger.getStackTrace(e));
					return null;
				}
				return policyDTO;
	}

	/**
	 * @方法功能说明：判断当前时间是否是供应商的上班时间
	 * workTime--供应商周一至周五工作时间
	 * restWorkTime--供应商周六、周日工作时间
	 * @修改者名字：yuqz
	 * @修改时间：2015年9月6日上午11:09:42
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	private boolean isWorkTime(String workTime, String restWorkTime) {
		boolean b = false;
		Calendar now=Calendar.getInstance();
		int day_of_week = now.get(Calendar.DAY_OF_WEEK);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		//当前是周末
		if(day_of_week == 7 || day_of_week ==1){
			String startTime = restWorkTime.split("-")[0];
			String endTime = restWorkTime.split("-")[1];
			int restWorkHourStart = Integer.parseInt(startTime.split(":")[0]);
			int restWorkMinuteStart = Integer.parseInt(startTime.split(":")[1]);
			int restWorkHourEnd = Integer.parseInt(endTime.split(":")[0]);
			int restWorkMinuteEnd = Integer.parseInt(endTime.split(":")[1]);
			//如果当前时间在供应商工作时间之内
			if(hour > restWorkHourStart && hour < restWorkHourEnd || (hour == restWorkHourStart && minute > restWorkMinuteStart)
					|| (hour == restWorkHourEnd && minute < restWorkMinuteEnd)){
				b = true;
			}
		}else{
			//当前时间是周一到周五
			String startTime = workTime.split("-")[0];
			String endTime = workTime.split("-")[1];
			int workHourStart = Integer.parseInt(startTime.split(":")[0]);
			int workMinuteStart = Integer.parseInt(startTime.split(":")[1]);
			int workHourEnd = Integer.parseInt(endTime.split(":")[0]);
			int workMinuteEnd = Integer.parseInt(endTime.split(":")[1]);
			//如果当前时间在供应商工作时间之内
			if(hour > workHourStart && hour < workHourEnd || (hour == workHourStart && minute > workMinuteStart)
					|| (hour == workHourEnd && minute < workMinuteEnd)){
				b = true;
			}
		}
		return b;
	}

	/**
	 * 机票生成订单
	 */
	public YeeXingJPOrderDTO shopCreateJPOrder(JPBookTicketSpiCommand command) {
		HgLogger.getInstance().info(
				"yaosanfeng",
				"JPWebLocalService->shopCreateJPOrder->command:"
						+ JSON.toJSONString(command));
		YeeXingJPOrderDTO plfxJPOrderDTO = null;
		String passengerInfoXml = null;
		try {
			if (null == command || null == command.getEncryptString()
					|| null == command.getYeeXingpassengerInfoDTO()) {
				HgLogger.getInstance().info("yaosanfeng",
						"机票生成订单参数(乘客信息集合,加密字符串)不能为空");
			}
			// 组装乘客信息成xml
			if (null != command.getYeeXingpassengerInfoDTO().getTelephone()
					|| null != command.getYeeXingpassengerInfoDTO().getName()
					|| null != command.getYeeXingpassengerInfoDTO()
							.getPassengerList()) {
				passengerInfoXml = this.getPassengerInfoXML(command
						.getYeeXingpassengerInfoDTO());
			}
			command.setPassengerInfo(passengerInfoXml);
			plfxJPOrderDTO = jpOrderLocalService.shopCreateOrder(command);
			HgLogger.getInstance().info(
					"yaosanfeng",
					"JPWebLocalService->shopCreateJPOrder->plfxJPOrderDTO:"
							+ JSON.toJSONString(plfxJPOrderDTO));
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng",
					"机票生成订单有错误" + HgLogger.getStackTrace(e));
		}
		return plfxJPOrderDTO;
	}

	/***
	 * 根据航班号查询
	 */
	public YeeXingQueryWebFlightsDTO queryYXFlightsByFlightNo(JPFlightSpiQO qo) {
		HgLogger.getInstance().info("yaosanfeng",
				"JPQueryFlightController->[查询缓存信息开始]:" + JSON.toJSONString(qo));
		if (qo == null || qo.getFlightNo() == null || qo.getStartDate() == null) {
			HgLogger.getInstance().error("yaosanfeng",
					"JPWebLocalService->queryYXFlightsByFlightNo->查询条件为空");

		}
		YeeXingQueryWebFlightsDTO yeeXingQueryWebFlightsDTO = new YeeXingQueryWebFlightsDTO();
		List<YeeXingFlightDTO> flightList = new ArrayList<YeeXingFlightDTO>();
		YeeXingFlightDTO yeeXingFlightDTO = null;

		String flightNo = qo.getFlightNo();
		String flightDateStr = qo.getStartDate();
		if (flightCacheManager.checkBYFlightKey(flightNo, flightDateStr)) {
			try {
				// 指定航班号查询
				yeeXingFlightDTO = flightCacheManager.getYXFlightDTO(flightNo,
						flightDateStr);
				flightList.add(yeeXingFlightDTO);
				yeeXingQueryWebFlightsDTO.setFlightList(flightList);
			} catch (Exception e) {
				HgLogger.getInstance().error("yaosanfeng",
						"redis服务器中没有这个条记录：" + flightNo + "-" + flightDateStr);
			}

		}
		return yeeXingQueryWebFlightsDTO;
	}

	/***
	 * 
	 * @方法功能说明：组装乘客信息成XML格式
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月2日下午3:38:31
	 * @修改内容：
	 * @参数：@param passengerInfoDTO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getPassengerInfoXML(YeeXingPassengerInfoDTO passengerInfoDTO) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
		sb.append("<input>");
		sb.append("<passengers>");
		for (YeeXingPassengerDTO p : passengerInfoDTO.getPassengerList()) {
			sb.append("<passenger Name=\"" + p.getPassengerName()
					+ "\" Type=\"" + p.getPassengerType() + "\" IdType=\""
					+ p.getIdType() + "\" IdNo=\"" + p.getIdNo() + "\"/>");
		}
		sb.append("</passengers>");
		sb.append("<contacter Name=\"" + passengerInfoDTO.getName()
				+ "\" Telephone=\"" + passengerInfoDTO.getTelephone() + "\"/>");
		sb.append("</input>");
		return sb.toString();
	}

	/**
	 * 
	 * @方法功能说明：ADMIN申请取消
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月16日下午5:54:00
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingCancelTicketDTO
	 * @throws
	 */
	public YeeXingCancelTicketDTO plfxCancelTicket(
			JPCancelTicketSpiCommand command) {
		// 取消机票通知地址
		command.setCancel_notify_url(SysProperties.getInstance().get(
				"http_domain")
				+ "/airTicket/notify");
		String result = "";
		YeeXingCancelTicketDTO yeeXingCancelTicketDTO = new YeeXingCancelTicketDTO();
		try {
			HgLogger.getInstance().info(
					"yaosanfeng",
					"JPWebLocalService->CancelTicket->JPCancelTicketSpiCommand[申请取消]:"
							+ JSON.toJSONString(command));
			if (null == command.getOrderid()
					|| null == command.getPassengerName()
					|| null == command.getCancel_notify_url()) {
				HgLogger.getInstance().error("yaosanfeng", "机票申请取消参数不能为空");
			}
			Map<String, String> params = new HashMap<String, String>();
			//获取cc配置用户名和密码
			command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
			command.setKey(SysProperties.getInstance().get("yeeXing_key"));
			params.put("getUserName", command.getUserName() == null ? "" : command.getUserName());
			params.put("getOrderid", command.getOrderid() == null ? "" : command.getOrderid());
			params.put("getPassengerName", command.getPassengerName() == null ? "" : command.getPassengerName());
			params.put("getCancel_notify_url", command.getCancel_notify_url() == null ? "" : command.getCancel_notify_url());
			String sign = SignTool.sign(params, command.getKey());
			HgLogger.getInstance().info("yaosanfeng",
					"JPWebLocalService->cancelTicket->[易行申请取消参数]params=" + JSON.toJSONString(params) + ",sign=" + sign);
			//调用易行天下机票申请取消接口
			result = ibeServicePortType.cancelTicket(command.getUserName(),
					command.getOrderid(), command.getPassengerName(),
					command.getCancel_notify_url(), sign);
			HgLogger.getInstance().info("yaosanfeng",
					"JPWebLocalService->cancelTicket->[易行申请取消result]=" + result);
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			for (Element e : childrenList) {
				if (e.getName().equals("is_success")) {
					yeeXingCancelTicketDTO.setIs_success(e.getText());
				} else if (e.getName().equals("orderId")) {
					yeeXingCancelTicketDTO.setOrderid(e.getText());
				}else if (e.getName().equals("error")) { // 如果请求要求没按接口参数传,易行会返回错误信息
					if (StringUtils.isNotBlank(e.getText())) {
						yeeXingCancelTicketDTO.setError(e.getText().trim());
					}
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng",
					"机票申请取消异常" + HgLogger.getStackTrace(e));
		}
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setRefundType("A");// 查询原始订单
	//	platformOrderQO.setYeeXingOrderId(yeeXingCancelTicketDTO.getOrderid());
		platformOrderQO.setYeeXingOrderId(command.getOrderid());
		JPOrderDTO jpOrderDTO = jpPlatformOrderService
				.queryUnique(platformOrderQO);
		GNJPOrder jpOrder = EntityConvertUtils.convertDtoToEntity(jpOrderDTO,
				GNJPOrder.class);
		if(yeeXingCancelTicketDTO.getIs_success().equals("T")){
			//更新机票订单状态
			this.plfxUpdateCancelJPOrder(jpOrder, command);
			//机票日志保存
			DomainEvent event = new DomainEvent(
					"plfx.gnjp.domain.model.order.GNJPOrder", "cancelTicket",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			//判断经销商id存不存在(存在就保存机票日志,不存在说明是admin进行保存日志)(原始订单上的日志)
			if(StringUtils.isNotBlank(command.getFromDealerId())){
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "申请取消机票订单", command.getFromDealerId(),
						OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请取消的订单成功"));		
			}else{
				 //保存日志
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "管理员申请取消机票订单", command.getFromDealerId(),	
				OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消订单成功"));
			}
		} else {
			//添加操作日志(原始订单上的日志)
			//判断经销商id存不存在(存在就保存机票日志,不存在说明是admin进行保存日志)
			if(StringUtils.isNotBlank(command.getFromDealerId())){
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "申请取消机票订单", command.getFromDealerId(),
						OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请取消的订单失败"));		
			}else{
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "管理员申请取消机票订单", command.getFromDealerId(),	
				OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消订单失败"));
			}
		}
		return yeeXingCancelTicketDTO;
	}

	/**
	 * 
	 * @方法功能说明：新增取消的订单记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月16日下午5:22:42
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	private void plfxUpdateCancelJPOrder(GNJPOrder jpOrder,
			JPCancelTicketSpiCommand command) {
		//获取原订单的状态
		GNJPOrderStatus oldOrderStatus = this.getGNJPOrderStatus(jpOrder);
		HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateCancelJPOrder->[原始订单状态]" + JSON.toJSONString(oldOrderStatus));
		if (jpOrder != null) {
			List<GNJPPassenger> newPassengers = new ArrayList<GNJPPassenger>();
			//List<GNJPPassenger> newOldPassengers = jpOrder.getPassengerList();//不能对同一个list又遍历,又修改,否则会报错(java.util.ConcurrentModificationException)
			List<GNJPPassenger> newOldPassengers=JSON.parseArray(JSON.toJSONString(jpOrder.getPassengerList()), GNJPPassenger.class);	
			List<GNJPPassenger> passengers = jpOrder.getPassengerList();	
//			for(GNJPPassenger passenger: jpOrder.getPassengerList()){
//				getDao().evict(passenger);
//			}
			String passengerName = command.getPassengerName();
			String[] passengerNames = passengerName.split("\\^");
			for(int j = passengers.size()-1;j >= 0;j--){
				for (int i = 0; i < passengerNames.length; i++) {
					if (passengers.get(j).getName() != null
							&& passengers.get(j).getName().equals(passengerNames[i])) {
						newPassengers.add(passengers.get(j));
						newOldPassengers.remove(j);
					}
				}
			}
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateCancelJPOrder->[取消订单的乘客列表]" + JSON.toJSONString(newPassengers));
			// 每个人一条取消的订单记录
			try{
				for (GNJPPassenger passenger : newPassengers) {
					GNJPOrder newJPOrder = new GNJPOrder(jpOrder, passenger);
					HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateCancelJPOrder->[取消订单的乘客]" + JSON.toJSONString(newJPOrder));
					jpOrderDAO.merge(newJPOrder);
				
					
					//添加取消订单的日志(根据经销商id有无,判断是谁取消的订单)(重新生成取消订单上面的日志)
					if(StringUtils.isNotBlank(command.getFromDealerId())){
						jPOrderLogLocalService.create(new CreateJPOrderLogCommand(newJPOrder
								.getId(), "申请取消机票订单", command.getFromDealerId(),
								OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请取消的订单成功"));		
					}else{
						 //保存日志
						jPOrderLogLocalService.create(new CreateJPOrderLogCommand(newJPOrder
								.getId(), "管理员申请取消机票订单", command.getFromDealerId(),	
						OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消订单成功"));
					}
				}
				
			    //对原有订单做处理(乘机人数,平台所得佣金,订单支付总价,用户支付总价)
				//未取消订单的乘机人数
				int remainPassengerCount = passengers.size()-newPassengers.size();
				//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
				Double plfxCommAmount = (jpOrder.getProfits() + jpOrder.getPlatPolicyPirce())* remainPassengerCount;
				//单张票的总价=单张票价+基建+燃油
				Double oneTotalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
				//未取消订单人机票的总价(订单支付总价)((单张票面价 + 机建燃油) * 数量（向上取整）)
				Double allTotalPrice = Math.ceil(oneTotalPrice * remainPassengerCount);
				//重新设置订单支付总价
				jpOrder.setTotalPrice(allTotalPrice);
				//重新设置平台所得佣金
				jpOrder.setCommAmount(plfxCommAmount);
				//用户支付总价=订单支付总价+平台所得佣金
				//重新设置用户支付总价
				jpOrder.setUserPayAmount(allTotalPrice + plfxCommAmount);
				//重新设置乘机人人数
				jpOrder.setPassangerCount(remainPassengerCount);
				//将订单的乘客设置为空
				jpOrder.setPassengerList(null);
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateCancelJPOrder->[未取消订单的乘客列表]" + JSON.toJSONString(newOldPassengers));
				//这个很重要:每一个为取消的乘客必须关联原始订单
				for(GNJPPassenger passenger:newOldPassengers){
					passenger.setJpOrder(jpOrder);
				}
				//设置原始订单状态
				jpOrder.setOrderStatus(oldOrderStatus);
				//将未取消的乘机人设置到原始订单中
			    jpOrder.setPassengerList(newOldPassengers);
			    jpOrderDAO.merge(jpOrder);
			    HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateCancelJPOrder->[原始订单]" + JSON.toJSONString(jpOrder));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/***
	 * 申请退废票
	 */
	public YeeXingRefundTicketDTO refundTicket(JPRefundTicketSpiCommand command) {
		// 申请退废票通知地址
		command.setRefundNotifyUrl(SysProperties.getInstance().get(
				"http_domain")
				+ "/airTicket/notify");
		String result = "";
		YeeXingRefundTicketDTO yeeXingRefundTicketDTO = new YeeXingRefundTicketDTO();
		try {
			HgLogger.getInstance().info(
					"yuqz",
					"JPWebLocalService->refundTicket->JPRefundTicketSpiCommand[申请退废]:"
							+ JSON.toJSONString(command));
			if (null == command.getOrderid()
					|| null == command.getPassengerName()
					|| null == command.getAirId()
					|| null == command.getRefundMemo()
					|| null == command.getRefundNotifyUrl()) {
				HgLogger.getInstance().error("yuqz", "JPWebLocalService->refundTicket->机票申请退废票参数不能为空");
				yeeXingRefundTicketDTO.setIs_success("F");
				return yeeXingRefundTicketDTO;
			}
			Map<String, String> params = new HashMap<String, String>();
			//获取cc配置用户名和密码
			command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
			command.setKey(SysProperties.getInstance().get("yeeXing_key"));
			params.put("getUserName", command.getUserName() == null ? "" : command.getUserName());
			params.put("getOrderid", command.getOrderid() == null ? "" : command.getOrderid());
			params.put("getPassengerName", command.getPassengerName() == null ? "" : command.getPassengerName());
			params.put("getAirId", command.getAirId() == null ? "" : command.getAirId());
			params.put("getRefundType", command.getRefundType() == null ? "" : command.getRefundType());
			params.put("getRefundMemo", command.getRefundMemo() == null ? "" : command.getRefundMemo());
			params.put(
					"getRefundSegment",
					command.getRefundSegment() == null ? "" : command
							.getRefundSegment());
			params.put("getRefundNotifyUrl", command.getRefundNotifyUrl() == null ? "" : command.getRefundNotifyUrl());
			String sign = SignTool.sign(params, command.getKey());
			HgLogger.getInstance().info("yuqz", "JPWebLocalService->refundTicket->"
					+ "[易行申请退废票参数]:params=" + JSON.toJSONString(params) + ",sign=" + sign + ",key=" + command.getKey());
			//调用易行天下机票申请退废接口
			result = ibeServicePortType.refundTicket(
					command.getUserName(),
					command.getOrderid(),
					command.getPassengerName(),
					command.getAirId(),
					command.getRefundType(),
					command.getRefundMemo(),
					command.getRefundSegment() == null ? "" : command
							.getRefundSegment(), command.getRefundNotifyUrl(),
					sign);
			HgLogger.getInstance().info("yuqz", "JPWebLocalService->refundTicket->"
					+ "[易行申请退废票result]="+result);
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			for (Element e : childrenList) {
				if (e.getName().equals("is_success")) {
					if (e.getText().equals("T")) {
						yeeXingRefundTicketDTO.setIs_success(e.getText());
					} else {
						yeeXingRefundTicketDTO.setIs_success(e.getText());
						HgLogger.getInstance().error(
								"yuqz",
								"JPWebLocalService->refundTicket->该次操作是否成功(T:成功F:失败)"
										+ e.getText());
					}
				} else if (e.getName().equals("orderId")) {
					yeeXingRefundTicketDTO.setOrderid(e.getText());
				}else if(e.getName().equals("error")){
					if(StringUtils.isNotBlank(e.getText())){
						yeeXingRefundTicketDTO.setError(e.getText());
					}
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz",
					"JPWebLocalService->refundTicket->机票申请退废异常" + HgLogger.getStackTrace(e));
			yeeXingRefundTicketDTO.setIs_success("F");
			return yeeXingRefundTicketDTO;
		}
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
	//	platformOrderQO.setYeeXingOrderId(yeeXingRefundTicketDTO.getOrderid());
		platformOrderQO.setYeeXingOrderId(command.getOrderid());
		platformOrderQO.setRefundType("A");
		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		GNJPOrder jpOrder = EntityConvertUtils.convertDtoToEntity(jpOrderDTO,
				GNJPOrder.class);
		// 更新机票订单状态
		if (yeeXingRefundTicketDTO.getIs_success().equals("T")) {
			this.plfxUpdateRefundJPOrder(jpOrder, command);
			//判断经销商id存不存在(存在就保存机票日志,不存在说明是admin进行保存日志)(原始订单上的日志)
			//添加操作日志
			if(StringUtils.isNotBlank(command.getFromDealerId())){
				//记录操作日志
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "申请退废票", command.getFromDealerId(),
						OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请退废票成功"));
			}else{
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "管理员申请退废票", command.getFromDealerId(),	
				OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请退废票成功"));
			}
		} else {
			if(StringUtils.isNotBlank(command.getFromDealerId())){
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "申请退废票", command.getFromDealerId(),
						OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请退废票失败"));
			}else{
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
						.getId(), "管理员申请退废票", command.getFromDealerId(),	
				OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请退废票失败"));
			}
		}
		return yeeXingRefundTicketDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：新增退废票订单,并把passenger关联到新订单上
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月15日下午3:27:57
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	private void plfxUpdateRefundJPOrder(GNJPOrder jpOrder,
			JPRefundTicketSpiCommand command) {
		//获取原订单的状态
		GNJPOrderStatus oldOrderStatus = this.getGNJPOrderStatus(jpOrder);
		HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateRefundJPOrder->[原始订单状态]" + JSON.toJSONString(oldOrderStatus));
		if (jpOrder != null) {
			List<GNJPPassenger> passengers = jpOrder.getPassengerList();
			List<GNJPPassenger> newOldPassengers = JSON.parseArray(JSON.toJSONString(jpOrder.getPassengerList()), GNJPPassenger.class);
			String airId = command.getAirId();
			String[] airIds = airId.split("\\^");
			List<GNJPPassenger> newPassengers = new ArrayList<GNJPPassenger>();
			for (int j = passengers.size()-1 ; j >= 0 ; j--) {
				for (int i = 0; i < airIds.length; i++) {
					if (passengers.get(j).getTicket() != null && passengers.get(j).getTicket().getTicketNo() != null
							&& passengers.get(j).getTicket().getTicketNo().equals(airIds[i])) {
						newPassengers.add(passengers.get(j));
						newOldPassengers.remove(j);
					}
				}
			}
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateRefundJPOrder->[申请退废订单的乘客列表]" + newPassengers.size());
			//每个人一条退废票的订单记录
			for(GNJPPassenger passenger : newPassengers){
				GNJPOrder newJPOrder = new GNJPOrder(jpOrder, passenger,command);
				getDao().merge(newJPOrder);
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateRefundJPOrder->[申请退废订单的乘]" + JSON.toJSONString(newJPOrder.getOrderStatus()));
				//System.out.println("success");
				//添加操作日志
				if(StringUtils.isNotBlank(command.getFromDealerId())){
					//记录操作日志
					jPOrderLogLocalService.create(new CreateJPOrderLogCommand(newJPOrder
							.getId(), "申请退废票", command.getFromDealerId(),
							OrderLogConstants.LOG_WORKER_TYPE_DEALER, "申请退废票成功"));
				}else{
					jPOrderLogLocalService.create(new CreateJPOrderLogCommand(newJPOrder
							.getId(), "管理员申请退废票", command.getFromDealerId(),	
					OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请退废票成功"));
				}
			}
			//对原有订单做处理(乘机人数,平台所得佣金,订单支付总价,用户支付总价)
			//(排除申请退废订单人数)
			int remainPassengerCount = passengers.size()-newPassengers.size();
			//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
			Double plfxCommAmount = (jpOrder.getProfits() + jpOrder.getPlatPolicyPirce())* remainPassengerCount;
			//单张票的总价=单张票价+基建+燃油
			Double oneTotalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
			//未退废票人机票的总价(订单支付总价)((单张票面价 + 机建燃油) * 数量（向上取整）)
			Double allTotalPrice = Math.ceil(oneTotalPrice * remainPassengerCount);
			//重新设置订单支付总价
			jpOrder.setTotalPrice(allTotalPrice);
			//重新设置平台所得佣金
			jpOrder.setCommAmount(plfxCommAmount);
			//用户支付总价=订单支付总价+平台所得佣金
			//重新设置用户支付总价
			jpOrder.setUserPayAmount(allTotalPrice + plfxCommAmount);
			//重新设置乘机人人数
			jpOrder.setPassangerCount(remainPassengerCount);
			//将订单的乘客设置为空
			jpOrder.setPassengerList(null);
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateRefundJPOrder->[未申请退废订单的乘客列表]" + newOldPassengers.size());
			//这个很重要:每一个为取消的乘客必须关联原始订单
			for(GNJPPassenger passenger:newOldPassengers){
				passenger.setJpOrder(jpOrder);
			}
			//设置原始订单状态
			jpOrder.setOrderStatus(oldOrderStatus);
			//将未申请退废的乘机人设置到原始订单中
		    jpOrder.setPassengerList(newOldPassengers);
			//更新操作
		    jpOrderDAO.merge(jpOrder);
		    HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->plfxUpdateRefundJPOrder->[原始订单]" + JSON.toJSONString(jpOrder.getOrderStatus()));
		    //通知经销商已经申请退废票,分销admin端申请退废票的时候才通知经销商
		    if(StringUtils.isBlank(command.getDealerOrderId())){
				JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
				jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
				jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
				jpNotifyMessageApiCommand.setPassengerName(command.getPassengerName());
				jpNotifyMessageApiCommand.setAirId(command.getAirId());
				jpNotifyMessageApiCommand.setRefundType(command.getRefundType());
				jpNotifyMessageApiCommand.setRefuseMemo(command.getRefundMemo());
				jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_REFUSE_DEAL);
				jpOrderLocalService.sendMessage(jpNotifyMessageApiCommand);
		    }
		}
	}

	/****
	 * 
	 * @方法功能说明：查询航空公司名称
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月27日下午3:31:56
	 * @修改内容：
	 * @参数：@param airPortCodeQO
	 * @参数：@return
	 * @return:AirPortCode
	 * @throws
	 */
	public  AirCompany queryLocalAirCompany(AirCompanyQO airCompanyQO) {
		return airCompanyDAO.queryUnique(airCompanyQO);
	}

	/***
	 * 
	 * @方法功能说明：获取原始订单状态
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年8月19日下午12:41:24
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@return
	 * @return:GNJPOrderStatus
	 * @throws
	 */
	public GNJPOrderStatus getGNJPOrderStatus(GNJPOrder jpOrder){
		GNJPOrderStatus oldeOrderStatus = new GNJPOrderStatus();
		oldeOrderStatus.setPayStatus(jpOrder.getOrderStatus().getPayStatus());
		oldeOrderStatus.setStatus(jpOrder.getOrderStatus().getStatus());
		return oldeOrderStatus;
	}

	/****
	 * 
	 * @方法功能说明：组装乘客信息生成订单并自动扣款
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午10:12:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingJPAutoOrderDTO
	 * @throws
	 */
	public YeeXingJPAutoOrderDTO apiAutoJPOrder(JPAutoOrderSpiCommand command) {
		HgLogger.getInstance().info(
				"yaosanfeng",
				"JPWebLocalService->apiAutoJPOrder->command:"
						+ JSON.toJSONString(command));
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = null;
		String passengerInfoXml = null;
		try {
			if (null == command || null == command.getEncryptString()
					|| null == command.getYeeXingpassengerInfoDTO()) {
				HgLogger.getInstance().error("yaosanfeng",
						"机票生成订单参数(乘客信息集合,加密字符串)不能为空");
			}
			// 组装乘客信息成xml
			if (null != command.getYeeXingpassengerInfoDTO().getTelephone()
					|| null != command.getYeeXingpassengerInfoDTO().getName()
					|| null != command.getYeeXingpassengerInfoDTO()
							.getPassengerList()) {
				passengerInfoXml = this.getPassengerInfoXML(command
						.getYeeXingpassengerInfoDTO());
			}
			command.setPassengerInfo(passengerInfoXml);
			yeeXingJPAutoOrderDTO = jpOrderLocalService.apiAutoOrder(command);
			HgLogger.getInstance().info(
					"yaosanfeng",
					"JPWebLocalService->apiAutoJPOrder->yeeXingJPAutoOrderDTO:"
							+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng",
					"机票生成订单有错误" + HgLogger.getStackTrace(e));
		}
		return yeeXingJPAutoOrderDTO;
	}

	/****
	 * 
	 * @方法功能说明：查询退票状态
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月16日上午11:02:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingRefundQueryOrderDTO
	 * @throws
	 */
	public YeeXingRefundQueryOrderDTO  refundQueryOrder(JPRefundQueryOrderStatusSpiCommand command){
 		HgLogger.getInstance().info(
				"yaosanfeng",
				"JPWebLocalService->refundQueryOrder->command:"
						+ JSON.toJSONString(command));
		YeeXingRefundQueryOrderDTO yeeXingRefundQueryOrderDTO = new YeeXingRefundQueryOrderDTO();
		String result = "";
		//只传乘客姓名，不传票号
		if (null == command.getId() || null == command.getOrderid() || null == command.getPassengername()) {
			HgLogger.getInstance().error("yaosanfeng", "机票查询退票状态参数不能为空"+JSON.toJSONString(command));
		}
		try{
		Map<String, String> params = new HashMap<String, String>();
		//获取cc配置用户名和密码
		command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
		params.put("getUserName", command.getUserName() == null ? "" : command.getUserName());
		params.put("getOrderid", command.getOrderid() == null ? "" : command.getOrderid());
		params.put("getPassengername", command.getPassengername() == null ? "" : command.getPassengername());
		params.put("getAirId", command.getAirId() == null ? "" : command.getAirId());
		String sign = SignTool.sign(params, SysProperties.getInstance().get("yeeXing_key"));
		HgLogger.getInstance().info("yaosanfeng",
				"JPWebLocalService->refundQueryOrder->[易行查询退票状态参数]params=" + JSON.toJSONString(params) + ",sign=" + sign);
		//调用易行天下机票查询退票状态
		result = ibeServicePortType.refundQuery(command.getUserName(), command.getAppUserName() , command.getOrderid(), command.getPassengername() == null ? "" : command.getPassengername(), command.getAirId() == null ? "" : command.getAirId(), sign);
		HgLogger.getInstance().info("yaosanfeng",
				"JPWebLocalService->refundQueryOrder->[易行查询退票状态result]=" + result);
		Document document = DocumentHelper.parseText(result);
		Element root = document.getRootElement();
		List<Element> childrenList = root.elements();
		for (Element e : childrenList) {
			if (e.getName().equals("is_success")) {
				if(StringUtils.isNotBlank(e.getText())){
			       yeeXingRefundQueryOrderDTO.setIs_success(e.getText());
				}
			} else if (e.getName().equals("orderid")) {
				if(StringUtils.isNotBlank(e.getText())){
				   yeeXingRefundQueryOrderDTO.setOrderid(e.getText());
				}
			}else if(e.getName().equals("refundstate")){
				if(StringUtils.isNotBlank(e.getText())){
				   yeeXingRefundQueryOrderDTO.setRefundstate(e.getText());
				}
			}else if(e.getName().equals("passengername")){
				if(StringUtils.isNotBlank(e.getText())){
				   yeeXingRefundQueryOrderDTO.setPassengername(e.getText());
				}
			}else if(e.getName().equals("airid")){
				if(StringUtils.isNotBlank(e.getText())){
				   yeeXingRefundQueryOrderDTO.setAirId(e.getText());
				}
			}else if(e.getName().equals("ticketprice")){
				if(StringUtils.isNotBlank(e.getText())){
					HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->refundQueryOrder->ticketprice:" +e.getText());
					yeeXingRefundQueryOrderDTO.setTicketprice(Double.parseDouble(e.getText()));
				}
			}else if(e.getName().equals("handling")){
				if(StringUtils.isNotBlank(e.getText())){
				   HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->refundQueryOrder->handling:" +e.getText());
				   yeeXingRefundQueryOrderDTO.setHandling(Double.parseDouble(e.getText()));
				}
			}else if(e.getName().equals("refunddate")){
				if(StringUtils.isNotBlank(e.getText())){
				   yeeXingRefundQueryOrderDTO.setRefunddate(e.getText());
				}
			}else if(e.getName().equals("refundMemo")){
				if(StringUtils.isNotBlank(e.getText())){
				   HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->refundQueryOrder->refundMemo:" +e.getText());
				   yeeXingRefundQueryOrderDTO.setRefuseMemo(URLDecoder.decode(e.getText(), "utf-8"));
				}
			}else if (e.getName().equals("error")) { // 如果请求要求没按接口参数传,易行会返回错误信息
				if (StringUtils.isNotBlank(e.getText())) {
					yeeXingRefundQueryOrderDTO.setError(e.getText().trim());
				}
			}
		}
	
		HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->refundQueryOrder->yeeXingRefundQueryOrderDTO:" + JSON.toJSONString(yeeXingRefundQueryOrderDTO));
		if(yeeXingRefundQueryOrderDTO != null && yeeXingRefundQueryOrderDTO.getIs_success().equals("T")){//为T才同步退票状态

			GNJPOrder jpOrder = getDao().get(command.getId());//主键查询唯一
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->refundQueryOrder->GNJPOrder:" + JSON.toJSONString(jpOrder));
			if(jpOrder != null){
				//同步退票状态
				this.synRefundOrderStatus(jpOrder, yeeXingRefundQueryOrderDTO, command);
			
			}
			return yeeXingRefundQueryOrderDTO;
		}
	} catch (Exception e) {
		HgLogger.getInstance().error("yaosanfeng",
				"易行查询退票状态异常" + HgLogger.getStackTrace(e));
	}
		return yeeXingRefundQueryOrderDTO;
	}

	/***
	 * 
	 * @方法功能说明：同步退票状态
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月16日下午3:57:26
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param yeeXingRefundQueryOrderDTO
	 * @return:void
	 * @throws
	 */
	private void synRefundOrderStatus(GNJPOrder jpOrder,YeeXingRefundQueryOrderDTO yeeXingRefundQueryOrderDTO ,JPRefundQueryOrderStatusSpiCommand command) {
		//获取原订单的状态
		GNJPOrderStatus oldOrderStatus = this.getGNJPOrderStatus(jpOrder);
		//同步易行的退票状态
		GNJPOrderStatus status = null;
		if(command != null && StringUtils.isNotBlank(command.getRefundType()) && command.getRefundType().equals("T")){//说明是记录订单,不用在分离订单直接更新操作 (对退废处理中订单而言)
			//1-退票成功  返回1代表退票成功,就更新本地订单状态
			if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("1")){
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->退票成功1:" + yeeXingRefundQueryOrderDTO.getRefundstate());
				//退票成功,已退款
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_SUCC),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
				//设置实际退款金额=支付供应商总价-手续费
				jpOrder.setRefundPrice(yeeXingRefundQueryOrderDTO.getTicketprice() - yeeXingRefundQueryOrderDTO.getHandling());
				//设置手续费
				jpOrder.setProcedures(yeeXingRefundQueryOrderDTO.getHandling());
				//发送通知
				sendTicketRefundMessage(jpOrder, yeeXingRefundQueryOrderDTO);
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->退票成功订单信息:" + "实退金额:" + jpOrder.getRefundPrice() + "手续费:" + jpOrder.getProcedures());
			}else if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("2")){//2-退票被拒绝 也就是退票失败   ,已支付
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->退票失败2:" + yeeXingRefundQueryOrderDTO.getRefundstate());
				//退票失败,已支付
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_FAIL),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));
				//发送通知
				sendTicketRefundMessage(jpOrder, yeeXingRefundQueryOrderDTO);
			}else if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("4")){//4-退费票审核中 也就是退废处理中,待退款
				HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->退废处理中4:" + yeeXingRefundQueryOrderDTO.getRefundstate());
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_DEALING),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_WAIT));
				//发送通知
				sendTicketRefundDealingMessage(jpOrder, command);
			}
			//乘机人关联订单
			for(GNJPPassenger passenger:jpOrder.getPassengerList()){
				passenger.setJpOrder(jpOrder);
			}
			//设置易行同步的订单状态
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->订单状态:" + JSON.toJSONString(status));
			jpOrder.setOrderStatus(status);
			//更新机票
			jpOrderDAO.merge(jpOrder);
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->同步订单信息:" + JSON.toJSONString(jpOrder.getOrderStatus()));
		}else{//说明是原始订单,需要分离订单(对已出票订单而言)
			if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("1")){//1-退废票成功,已退款
				//退票成功,已退款
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_SUCC),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
				//设置 单张总价      机票的价格(实退金额)
				jpOrder.setRefundPrice(yeeXingRefundQueryOrderDTO.getTicketprice());
				//设置手续费
				jpOrder.setProcedures(yeeXingRefundQueryOrderDTO.getHandling());
				//发送通知
				sendTicketRefundMessage(jpOrder, yeeXingRefundQueryOrderDTO);
			}else if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("2")){//2-退票被拒绝 也就是退票失败   ,已支付
				//退票失败,待退款
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_FAIL),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));
				//发送通知
				sendTicketRefundMessage(jpOrder, yeeXingRefundQueryOrderDTO);
			}else if(yeeXingRefundQueryOrderDTO.getRefundstate().equals("4")){//4-退费票审核中 也就是退废处理中
				status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_DEALING),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_WAIT));
				//发送通知
				sendTicketRefundDealingMessage(jpOrder, command);
			}
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->订单状态:" + JSON.toJSONString(status));
			List<GNJPPassenger> passengers = jpOrder.getPassengerList();
			List<GNJPPassenger> newOldPassengers = JSON.parseArray(JSON.toJSONString(jpOrder.getPassengerList()), GNJPPassenger.class);
			List<GNJPPassenger> newPassengers = new ArrayList<GNJPPassenger>();
			for (int j = passengers.size()-1 ; j >= 0 ; j--) {
				if (command.getPassengername().equals(passengers.get(j).getName())) {
					newPassengers.add(passengers.get(j));
					newOldPassengers.remove(j);
				}
			}
			
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->[同步退票状态的乘客列表]" + newPassengers.size());
			//每个人一条退废票的订单记录
			for(GNJPPassenger passenger : newPassengers){
				GNJPOrder newJPOrder = new GNJPOrder(jpOrder, passenger,command ,status);
				getDao().merge(newJPOrder);
				//HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->[同步退票状态订单]" + JSON.toJSONString(newJPOrder));
				//添加操作日志	
				jPOrderLogLocalService.create(new CreateJPOrderLogCommand(newJPOrder
						.getId(), "管理员同步退票状态", "",	
				OrderLogConstants.LOG_WORKER_TYPE_PLATFORM, "管理员同步退票状态成功"));
			}
			//对原有订单做处理(乘机人数,平台所得佣金,订单支付总价,用户支付总价)
			//(排除申请退废订单人数)
			int remainPassengerCount = passengers.size()-newPassengers.size();
			//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
			Double plfxCommAmount = (jpOrder.getProfits() + jpOrder.getPlatPolicyPirce())* remainPassengerCount;
			//单张票的总价=单张票价+基建+燃油
			Double oneTotalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
			//未退废票人机票的总价(订单支付总价)((单张票面价 + 机建燃油) * 数量（向上取整）)
			Double allTotalPrice = Math.ceil(oneTotalPrice * remainPassengerCount);
			//重新设置订单支付总价
			jpOrder.setTotalPrice(allTotalPrice);
			//重新设置平台所得佣金
			jpOrder.setCommAmount(plfxCommAmount);
			//用户支付总价=订单支付总价+平台所得佣金
			//重新设置用户支付总价
			jpOrder.setUserPayAmount(allTotalPrice + plfxCommAmount);
			//重新设置乘机人人数
			jpOrder.setPassangerCount(remainPassengerCount);
			//将订单的乘客设置为空
			jpOrder.setPassengerList(null);
			HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->[未同步退票订单的乘客列表]" + newOldPassengers.size());
			//这个很重要:每一个乘客必须关联原始订单
			for(GNJPPassenger passenger:newOldPassengers){
				passenger.setJpOrder(jpOrder);
			}
			//将未申请退废的乘机人设置到原始订单中
		    jpOrder.setPassengerList(newOldPassengers);
		    HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->订单状态:" + JSON.toJSONString(oldOrderStatus));
		    //设置原始订单状态
			jpOrder.setOrderStatus(oldOrderStatus);
			//将原始订单(已出票)的退款金额设空,手续费设置空
			jpOrder.setRefundPrice(null);
			jpOrder.setProcedures(null);
	
		    jpOrderDAO.merge(jpOrder);
		    HgLogger.getInstance().info("yaosanfeng","JPWebLocalService->synRefundOrderStatus->同步订单信息:" + JSON.toJSONString(jpOrder.getOrderStatus()));
		}
		
//		GNJPSyncRefundStateCommand jPSyncRefundStateCommand = new GNJPSyncRefundStateCommand();
//		jPSyncRefundStateCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
//		jPSyncRefundStateCommand.setOrderNo(jpOrder.getOrderNo());
//		jPSyncRefundStateCommand.setRefundstate(yeeXingRefundQueryOrderDTO.getRefundstate());
//		jPSyncRefundStateCommand.setPassengername(yeeXingRefundQueryOrderDTO.getPassengername());
//		jPSyncRefundStateCommand.setTicketprice(yeeXingRefundQueryOrderDTO.getTicketprice());
//		jPSyncRefundStateCommand.setHandling(yeeXingRefundQueryOrderDTO.getHandling());
//		jPSyncRefundStateCommand.setAirId(yeeXingRefundQueryOrderDTO.getAirId());
//		jPSyncRefundStateCommand.setRefuseMemo(yeeXingRefundQueryOrderDTO.getRefuseMemo());
//		jPSyncRefundStateCommand.setError(yeeXingRefundQueryOrderDTO.getError());
//		sendMessage(jPSyncRefundStateCommand);
		
	}

	/**
	 * @方法功能说明：通知经销商修改订单状态为退废票成功/失败
	 * @修改者名字：yuqz
	 * @修改时间：2015年9月25日上午10:12:08
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	private void sendTicketRefundMessage(GNJPOrder jpOrder, YeeXingRefundQueryOrderDTO yeeXingRefundQueryOrderDTO) {
		JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
		jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
		jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
		if(yeeXingRefundQueryOrderDTO.getAirId() != null){
			jpNotifyMessageApiCommand.setAirId(yeeXingRefundQueryOrderDTO.getAirId());
		}
		if(yeeXingRefundQueryOrderDTO.getTicketprice() != null && yeeXingRefundQueryOrderDTO.getHandling() != null){
			jpNotifyMessageApiCommand.setRefundPrice(yeeXingRefundQueryOrderDTO.getTicketprice() - yeeXingRefundQueryOrderDTO.getHandling());
		}
		if(yeeXingRefundQueryOrderDTO.getRefundstate() != null){
			jpNotifyMessageApiCommand.setRefundStatus(Integer.parseInt(yeeXingRefundQueryOrderDTO.getRefundstate()));
		}
		if(yeeXingRefundQueryOrderDTO.getRefuseMemo() != null){
			jpNotifyMessageApiCommand.setRefuseMemo(yeeXingRefundQueryOrderDTO.getRefuseMemo());
		}
		if(yeeXingRefundQueryOrderDTO.getHandling() != null){
			jpNotifyMessageApiCommand.setProcedures(yeeXingRefundQueryOrderDTO.getHandling());
		}
		jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_REFUSE_SUCCESS);
		jpOrderLocalService.sendMessage(jpNotifyMessageApiCommand);
	}

	/**
	 * 
	 * @方法功能说明：通知经销商修改订单状态为退废票处理中
	 * @修改者名字：yuqz
	 * @修改时间：2015年9月25日上午9:38:22
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	private void sendTicketRefundDealingMessage(GNJPOrder jpOrder, JPRefundQueryOrderStatusSpiCommand command) {
		//通知经销商已经申请退废票,分销admin端申请退废票的时候才通知经销商
		JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
		jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
		jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
		jpNotifyMessageApiCommand.setPassengerName(command.getPassengername());
		jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_REFUSE_DEAL);
		jpOrderLocalService.sendMessage(jpNotifyMessageApiCommand);
	}

//	/**
//	 * 
//	 * @方法功能说明：通知发送失败3次就不再发送
//	 * @修改者名字：yuqz
//	 * @修改时间：2015年7月8日上午10:19:57
//	 * @修改内容：
//	 * @参数：@param jpNotifyMessageApiCommand
//	 * @参数：@return
//	 * @return:boolean
//	 * @throws
//	 */
//	public boolean sendMessage(GNJPSyncRefundStateCommand command){
//		HgLogger.getInstance().info("yuqz", "发送通知:command=" + JSON.toJSONString(command));
//		boolean bool = sendNotifyMessage(command);
//		int notifyCount = 1;
//		HgLogger.getInstance().info("yuqz", "发送通知第" + notifyCount + "次" + bool);
//		//通知3次都失败就不通知了
//		while(!bool && notifyCount < 3){
//			bool = sendNotifyMessage(command);
//			notifyCount++;
//			HgLogger.getInstance().info("yuqz", "发送通知第" + notifyCount + "次" + bool);
//		}
//		return bool;
//	}
//	/**
//	 * @方法功能说明：向经销商发送通知消息
//	 * @修改者名字：yuqz
//	 * @修改时间：2015年7月7日下午5:14:59
//	 * @修改内容：
//	 * @参数：
//	 * @return:
//	 * @throws
//	 */
//	private boolean sendNotifyMessage(GNJPSyncRefundStateCommand command) {
//		boolean bool = false;
//		SynchronizationJPRefundStateMessage baseAmqpMessage = new SynchronizationJPRefundStateMessage();
//		baseAmqpMessage.setContent(command);
//		baseAmqpMessage.setType(null);
//		baseAmqpMessage.setSendDate(new Date());
//		baseAmqpMessage.setArgs(null);
//		try{
//			HgLogger.getInstance().info("yuqz","JPWebLocalService->sendNotifyMessage:"+ JSON.toJSONString(baseAmqpMessage));
//			template.convertAndSend("plfx.gnjp.sendNotifyMessage",baseAmqpMessage);			
//			HgLogger.getInstance().info("yuqz","JPWebLocalService->sendNotifyMessage-成功");
//			bool = true;
//		}catch(Exception e){
//			HgLogger.getInstance().error("yuqz","JPWebLocalService->sendNotifyMessage-失败:"+ HgLogger.getStackTrace(e));
//			return bool;
//		}
//		return bool;
//	}
   

}
