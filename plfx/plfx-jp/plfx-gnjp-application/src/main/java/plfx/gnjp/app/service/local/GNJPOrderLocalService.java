package plfx.gnjp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

import plfx.api.client.common.util.HttpUtils;
import plfx.gnjp.app.component.FlightCacheManager;
import plfx.gnjp.app.dao.FlightTicketDAO;
import plfx.gnjp.app.dao.GNJPOrderDAO;
import plfx.gnjp.app.dao.PassengerDAO;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.gnjp.domain.model.order.GNJPOrderStatus;
import plfx.gnjp.domain.model.order.GNJPPassenger;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.app.dao.AirCompanyDAO;
import plfx.jp.app.dao.AirPortCodeDAO;
import plfx.jp.app.dao.CityAirCodeDAO;
import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.domain.model.AirPortCode;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.pojo.dto.pay.balances.PayBalancesDTO;
import plfx.jp.pojo.dto.policy.PolicyDTO;
import plfx.jp.pojo.system.OrderLogConstants;
import plfx.jp.pojo.system.PolicyConstants;
import plfx.jp.qo.admin.policy.PolicyQO;
import plfx.jp.qo.api.AirPortCodeQO;
import plfx.jp.qo.pay.balances.PayBalancesQO;
import plfx.jp.spi.common.JpEnumConstants;
import plfx.jp.spi.inter.pay.balances.PayBalancesService;
import plfx.jp.spi.inter.policy.PolicyService;
import plfx.yeexing.constant.JPNofityMessageConstant;
import plfx.yeexing.inter.IBEServicePortType;
import plfx.yeexing.pojo.command.message.JPNotifyMessageApiCommand;
import plfx.yeexing.pojo.command.order.JPAutoOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPBookTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPOrderCancelCommand;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefundCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefuseCommand;
import plfx.yeexing.pojo.command.order.JPPayNotifyCommand;
import plfx.yeexing.pojo.command.order.JPPayOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPPayOutCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.JPPlatPriceDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingPayJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingPayOutResponseDTO;
import plfx.yeexing.pojo.message.SynchronizationJPNotifyMessage;
import plfx.yeexing.qo.admin.PlatformOrderQO;
import plfx.yeexing.util.IBEService;
import plfx.yeexing.util.SignTool;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月29日下午5:19:34
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class GNJPOrderLocalService extends BaseServiceImpl<GNJPOrder, PlatformOrderQO, GNJPOrderDAO>{
	//JPConstant jPConstant=new JPConstant();
	IBEService client=new IBEService();
	IBEServicePortType ibeServicePortType=client.getIBEServiceHttpPort();
	
	@Autowired
	private GNJPOrderDAO jpOrderDAO;
	
	@Autowired
	private PassengerDAO passengerDAO;
	
	@Autowired
	private FlightTicketDAO flightTicketDAO;
	
	@Resource
	private CityAirCodeDAO cityAirCodeDAO;
	
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Resource
	private RabbitTemplate template;
	
	@Autowired
	private JPOrderLogLocalService jpOrderLogLocalService;
	
	@Autowired
	private PolicyService policyService;
	
	private AirPortCodeDAO airPortCodeDAO;
	
	private AirCompanyDAO airCompanyDAO;
	/**
	 * 平台缓存航班信息
	 */
	@Autowired
	private FlightCacheManager flightCacheManager;
	
	/**
	 * 经销商缓存
	 */
	@Autowired
	private DealerCacheManager dealerCacheManager;
	
	@Autowired
	private PayBalancesService payBalancesService;
	
	@Override
	protected GNJPOrderDAO getDao() {
		// TODO Auto-generated method stub
		return jpOrderDAO;
	}
	
	/***
	 * 
	 * @方法功能说明：生成订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月19日下午3:57:49
	 * @修改内容：
	 * @参数：@param jPOrderCreateSpiCommand
	 * @参数：@return
	 * @return:JPOrderDTO
	 * @throws
	 */
	public YeeXingJPOrderDTO shopCreateOrder(JPBookTicketSpiCommand command) {
		//设置外部订单号(也就是分销平台的订单号)
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->command:"+ JSON.toJSONString(command));
		command.setOutOrderId(command.getFromDealerId()+command.getDealerOrderId());
		YeeXingJPOrderDTO yeeXingJPOrderDTO = new YeeXingJPOrderDTO();
		try{
			String result = null;
			Map<String, String> params = new HashMap<String, String>();
			//获取cc配置用户名和密码
			command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
			command.setKey(SysProperties.getInstance().get("yeeXing_key"));
			params.put("getUserName", command.getUserName() == null ? "":command.getUserName());
			params.put("getEncryptString", command.getEncryptString() == null ? "":command.getEncryptString());
			params.put("getPassengerInfo", command.getPassengerInfo() == null ? "":command.getPassengerInfo());
			params.put("getOrderid", command.getOrderId() == null ? "":command.getOrderId());
			params.put("getOutOrderid", command.getOutOrderId() == null ? "":command.getOutOrderId());
			String sign = SignTool.sign(params, command.getKey());
			HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->shopCreateOrder->[易行生成订单参数]:params="
					+ JSON.toJSONString(params) + ",sign=" + sign + ",key=" + command.getKey());
		    result = ibeServicePortType.bookTicket(command.getUserName(), 
		    		command.getEncryptString(), command.getPassengerInfo(), command.getOrderId() == null ? "":command.getOrderId(), command.getOutOrderId() == null ? "":command.getOutOrderId(), sign);
			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->[易行生成订单结束result]=:"+ JSON.toJSONString(result));
			Document document = DocumentHelper.parseText(result);
	 	    Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			List<YeeXingPassengerDTO> plist = new ArrayList<YeeXingPassengerDTO>();
			for(Element e:childrenList){
				if(e.getName().equals("is_success")){
					if(e.getText().equals("T")){
						yeeXingJPOrderDTO.setIs_success(e.getText());
					}else {
						yeeXingJPOrderDTO.setIs_success(e.getText());
						HgLogger.getInstance().error("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->该次操作是否成功(T:成功F:失败)"+e.getText());
					}	
				}else if(e.getName().equals("error")){ //如果请求要求没按接口参数传,易行会返回错误信息
					if(StringUtils.isNotBlank(e.getText())){
						yeeXingJPOrderDTO.setError(e.getText().trim());
					}	
				}else if(e.getName().equals("orderInfo")){
					yeeXingJPOrderDTO.setOrderid(e.attributeValue("orderid"));
					yeeXingJPOrderDTO.setOut_orderid(e.attributeValue("out_orderid"));
					yeeXingJPOrderDTO.setCreateTime(e.attributeValue("createTime"));	
				}else if(e.getName().equals("price")){
					YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
					yeeXingPriceDTO.setChangePnr(e.attributeValue("changePnr"));
					yeeXingPriceDTO.setDisc(Double.parseDouble(e.attributeValue("disc")));
					yeeXingPriceDTO.setExtReward(Double.parseDouble(e.attributeValue("extReward")));
					yeeXingPriceDTO.setIbePrice(Double.parseDouble(e.attributeValue("ibePrice")));
					yeeXingPriceDTO.setIsSphigh(e.attributeValue("isSphigh"));
					yeeXingPriceDTO.setMemo(e.attributeValue("memo"));
					yeeXingPriceDTO.setOutTime(e.attributeValue("outTime"));
					yeeXingPriceDTO.setPayType(Integer.parseInt(e.attributeValue("payType")));
					yeeXingPriceDTO.setPlcid(Integer.parseInt(e.attributeValue("plcid")));
					yeeXingPriceDTO.setTickPrice(Double.parseDouble(e.attributeValue("tickPrice")));
					yeeXingPriceDTO.setTickType(Integer.parseInt(e.attributeValue("tickType")));
					//有婴儿的时候会有(目前暂不支持婴儿) 设置婴儿价全部为0.00
					yeeXingPriceDTO.setInfPrice(Double.parseDouble(e.attributeValue("infPrice").toString()==""?"0.00":e.attributeValue("infPrice")));
					yeeXingPriceDTO.setOilFee(Double.parseDouble(e.attributeValue("oilFee")));
					yeeXingPriceDTO.setBuildFee(Double.parseDouble(e.attributeValue("buildFee")));
					yeeXingPriceDTO.setProfits(Double.parseDouble(e.attributeValue("profits")));
					yeeXingPriceDTO.setRestWorkTime(e.attributeValue("restWorkTime"));
					yeeXingPriceDTO.setTotalPrice(Double.parseDouble(e.attributeValue("totalPrice")));
					yeeXingPriceDTO.setWorkTime(e.attributeValue("workTime"));
					yeeXingJPOrderDTO.setPriceDTO(yeeXingPriceDTO);	
	            	HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->[易行天下生成订单接口开始]-->YeeXingJPOrderDTO"+ JSON.toJSONString(yeeXingJPOrderDTO));
	            }else if(e.getName().equals("passengerinfos")){
					for (Iterator i = e.elementIterator(); i.hasNext();) { 
						YeeXingPassengerDTO  yeeXingPassengerDTO = new YeeXingPassengerDTO();
			            Element passengerElement = (Element) i.next(); 
			            yeeXingPassengerDTO.setPassengerName(passengerElement.attributeValue("passengerName"));
			            yeeXingPassengerDTO.setPassengerType(passengerElement.attributeValue("passengerType"));
			            plist.add(yeeXingPassengerDTO);      
				     }
					HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->易行天下生成订单接口开始-->List<YeeXingPassengerDTO>"+ JSON.toJSONString(plist));
	            }
				yeeXingJPOrderDTO.setPassengerList(plist);
			 } 
			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->[易行天下生成订单接口结束]->yeeXingFlightOrderDTO"+ JSON.toJSONString(yeeXingJPOrderDTO));
		    if(yeeXingJPOrderDTO != null && StringUtils.isNotBlank(yeeXingJPOrderDTO.getIs_success()) && yeeXingJPOrderDTO.getIs_success().equals("T")){
		    	//1从缓存中查航班信息
				YeeXingFlightDTO yeeXingFlightDTO = flightCacheManager.getYXFlightDTO(command.getFlightNo(), command.getStartDate());
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingFlightDTO:"+ JSON.toJSONString(yeeXingFlightDTO));
				//2持久化化订单 订单状态设置
				GNJPOrderStatus status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_PAY_WAIT),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_NO_PAY));
				//计算平台价格
				YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
				yeeXingPriceDTO.setIbePrice(yeeXingJPOrderDTO.getPriceDTO().getIbePrice());
				yeeXingPriceDTO.setTotalPrice(yeeXingJPOrderDTO.getPriceDTO().getTotalPrice());
				yeeXingPriceDTO.setBuildFee(yeeXingJPOrderDTO.getPriceDTO().getBuildFee());
				yeeXingPriceDTO.setOilFee(yeeXingJPOrderDTO.getPriceDTO().getOilFee());
				JPPlatPriceDTO jpPlatPriceDTO = dealPlatPrice(yeeXingPriceDTO, command.getFromDealerId());
				//设置平台价格
				yeeXingJPOrderDTO.getPriceDTO().setPlatTotalPrice(jpPlatPriceDTO.getPlatTotalPrice());
				yeeXingJPOrderDTO.getPriceDTO().setPlatPolicyPirce(jpPlatPriceDTO.getPlatPolicyPirce());
				yeeXingJPOrderDTO.getPriceDTO().setServiceCharge(jpPlatPriceDTO.getServiceCharge());
				//3创建订单
				GNJPOrder jpOrder = new GNJPOrder(command,yeeXingJPOrderDTO,status,yeeXingFlightDTO);
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->GNJPOrder:"+ JSON.toJSONString(jpOrder));
				//4持久化下单成功后，返回DTO
				yeeXingJPOrderDTO = this.saveJPOrderInfo(jpOrder);
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->yeeXingJPOrderDTO:"+ JSON.toJSONString(yeeXingJPOrderDTO));
				return yeeXingJPOrderDTO; 
		    }
//			JPPayOutCommand jPPayOutCommand = new JPPayOutCommand();
//			jPPayOutCommand.setOrderid(yeeXingJPOrderDTO.getOrderid());
//			//扣款成功通知地址
//			jPPayOutCommand.setOut_notify_url(SysProperties.getInstance().get("http_domain")+"/airTicket/notify");
//			//出票成功/拒绝出票通知地址
//			jPPayOutCommand.setPay_notify_url(SysProperties.getInstance().get("http_domain")+"/airTicket/notify");
//			jPPayOutCommand.setPayPlatform(yeeXingJPOrderDTO.getPriceDTO().getPayType()==null?1:yeeXingJPOrderDTO.getPriceDTO().getPayType());
//			jPPayOutCommand.setTotalPrice(this.doubleToString(yeeXingJPOrderDTO.getPriceDTO().getTotalPrice()));
//			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingPayOutResponseDTO->[自动扣开始]" + JSON.toJSONString(jPPayOutCommand));
//			//6调自动扣款接口
//			YeeXingPayOutResponseDTO yeeXingPayOutResponseDTO = this.autoPayOut(jPPayOutCommand);
//			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingPayOutResponseDTO->[自动扣款结束]" + JSON.toJSONString(yeeXingPayOutResponseDTO));
//			//7更新分销平台机票订单信息
//			if(null != yeeXingPayOutResponseDTO && StringUtils.isNotBlank(yeeXingPayOutResponseDTO.getIs_success()) && yeeXingPayOutResponseDTO.getIs_success().equals("T") && StringUtils.isNotBlank(yeeXingPayOutResponseDTO.getPay_status()) && yeeXingPayOutResponseDTO.getPay_status().equals("T")){//如果Is_success返回T并且Pay_status也返回T代表扣款成功
//				//自动扣款成功后更新机票最新信息
//				this.updateJPOrderInfo(yeeXingPayOutResponseDTO, jPPayOutCommand, yeeXingJPOrderDTO);
//			}
//			return yeeXingJPOrderDTO; 
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->[易行天下生成订单接口或自动扣款接口异常]"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return yeeXingJPOrderDTO;
		  
	}

	/**
	 * 
	 * @方法功能说明：计算平台加价政策、手续费、平台支付总价
	 * @修改者名字：yuqz
	 * @修改时间：2015年10月8日下午1:14:30
	 * @修改内容：
	 * @参数：@param yeeXingPriceDTO
	 * @参数：@param fromDealerId
	 * @参数：@return
	 * @return:JPPlatPriceDTO
	 * @throws
	 */
    public JPPlatPriceDTO dealPlatPrice(YeeXingPriceDTO yeeXingPriceDTO,String fromDealerCode) {
    	HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->dealPlatPrice->开始计算价格：yeeXingPriceDTO="
    			+ JSON.toJSONString(yeeXingPriceDTO) + ",fromDealerCode=" + fromDealerCode);
    	JPPlatPriceDTO jpPlatPriceDTO = new JPPlatPriceDTO();
    	Dealer dealer = dealerCacheManager.getDealer(fromDealerCode);
    	PolicyDTO policyDTO = null;
    	if(dealer != null){
	    	PolicyQO policyQO = new PolicyQO();
	    	//表未设置关联，不能通过dealerCode去查
			policyQO.setDealerId(dealer.getId());
			policyQO.setSuppId(SysProperties.getInstance().get("yeexing_gn_suppId"));
			policyQO.setCurrentTime(new Date());
			policyQO.setStatus(PolicyConstants.PUBLIC);
			HgLogger.getInstance().info("yuqz", "生成订单->平台加价政策->policyQO:" + JSON.toJSONString(policyQO));
			policyDTO = policyService.queryUnique(policyQO);
			HgLogger.getInstance().info("yuqz", "生成订单->平台加价政策->policyDTO:" + JSON.toJSONString(policyDTO));
    	}
		//票面价
		Double ibePrice = yeeXingPriceDTO.getIbePrice();	
		//分销平台价格政策价格
		Double platPolicyPirce = null;
		//分销平台计算的支付总价   
		//价格政策=票面价-价格政策的数字+支付宝手续费+基建燃油，最后十位取整
		Double platTotalPrice = null;
		/**
		 * 手续费
		 * 手续费=（票面价+税款）*0.008（支付宝千分之8手续费）
		 */
		Double serviceCharge = null;
		Double singleIbePrice = ibePrice + yeeXingPriceDTO.getBuildFee() + yeeXingPriceDTO.getOilFee();
		DecimalFormat df = new DecimalFormat("#.00"); 
		serviceCharge = Double.valueOf(df.format(singleIbePrice * 0.008));
		if(policyDTO != null){
			
			if(policyDTO.getPricePolicy() != null){
				platPolicyPirce = policyDTO.getPricePolicy();
				platTotalPrice = ibePrice + platPolicyPirce + serviceCharge + yeeXingPriceDTO.getBuildFee() + yeeXingPriceDTO.getOilFee();
			}
			if(policyDTO.getPercentPolicy() != null){
				platPolicyPirce = ibePrice * policyDTO.getPercentPolicy();
				platTotalPrice = ibePrice + platPolicyPirce + serviceCharge + yeeXingPriceDTO.getBuildFee() + yeeXingPriceDTO.getOilFee();
			}
		}else{
			//如果没有加价政策，加价为0
			platPolicyPirce = 0.0;
			platTotalPrice = ibePrice + platPolicyPirce + serviceCharge + yeeXingPriceDTO.getBuildFee() + yeeXingPriceDTO.getOilFee();
		}
		//平台价格不能低于支付供应商的钱　＋ 手续费
		if(platTotalPrice < (yeeXingPriceDTO.getTotalPrice() + serviceCharge)){
			platTotalPrice = yeeXingPriceDTO.getTotalPrice() + serviceCharge;
		}
		//平台价格不能超过票面价+机建燃油,platTotalPrice价格已加上机建+燃油
		if(platTotalPrice > singleIbePrice){
			platTotalPrice = singleIbePrice;
		}
		//价格个位数都进到十位数
		platTotalPrice = (Math.ceil(platTotalPrice/10)) * 10;
		HgLogger.getInstance().info("yuqz", "生成订单->平台加价政策->"
				+ "支付给供应商的价格=" + yeeXingPriceDTO.getTotalPrice() + 
				",加价政策价格=" + platPolicyPirce + 
				",分销平台的价格=" + platTotalPrice + 
				",手续费=" + serviceCharge +
				",票面价=" + yeeXingPriceDTO.getIbePrice()+
				",票面价(包括基建燃油)="+singleIbePrice);
		jpPlatPriceDTO.setPlatTotalPrice(platTotalPrice);
		jpPlatPriceDTO.setServiceCharge(serviceCharge);
		jpPlatPriceDTO.setPlatPolicyPirce(platPolicyPirce);
		return jpPlatPriceDTO;
	}

	/****
     * 
     * @方法功能说明:持久化订单
     * @修改者名字：yaosanfeng
     * @修改时间：2015年6月30日下午2:43:15
     * @修改内容：
     * @参数：@param jpOrder
     * @参数：@return
     * @return:PlfxJPOrderDTO
     * @throws
     */
	public YeeXingJPOrderDTO saveJPOrderInfo(GNJPOrder jpOrder) {
		jpOrder.setId(UUIDGenerator.getUUID());
		this.saveJPOrder(jpOrder);
		//设置操作日志(先放着)
		// 旅客信息列表
		List<GNJPPassenger> passengerList = jpOrder.getPassengerList();
		for (GNJPPassenger passenger : passengerList) {
			//2.保存旅客信息
			passenger.setJpOrder(jpOrder);
			//保存乘客别的信息
			this.savePassenger(passenger);
		}
		//dto转化
		YeeXingJPOrderDTO yeeXingJPOrderDTO = revernt(jpOrder);
		return yeeXingJPOrderDTO;
	}
	 
	
	/****
	 * 
	 * @方法功能说明：自动扣款后更新订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日下午1:39:57
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@return
	 * @return:YeeXingJPOrderDTO
	 * @throws
	 */
	public void updateJPOrderInfo(YeeXingPayOutResponseDTO yeeXingPayOutResponseDTO,JPPayOutCommand jPPayOutCommand,YeeXingJPOrderDTO yeeXingJPOrderDTO) {
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		//易行订单号
		platformOrderQO.setYeeXingOrderId(yeeXingJPOrderDTO.getOrderid());
		GNJPOrder jpOrder = this.queryUnique(platformOrderQO);
		//支付方式
	//	jpOrder.setPayType(jPPayOutCommand.getPayPlatform());
		//支付方式-1支付宝
		jpOrder.setPayType(1);
		//第三方支付单号(支付公司流水号)
		jpOrder.setPayTradeNo(yeeXingPayOutResponseDTO.getPayid());
		//订单状态:出票处理中,支付状态:已支付
		GNJPOrderStatus orderStatus = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_DEALING),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));
		//更新订单状态
		jpOrder.setOrderStatus(orderStatus);
		//设置订单总价
		jpOrder.setTotalPrice(yeeXingPayOutResponseDTO.getTotalPrice());
		//保存机票订单信息
		getDao().update(jpOrder);
		//设置操作日志(先放着)
		// 旅客信息列表
//		List<GNJPPassenger> passengerList = jpOrder.getpassengerList();
//		for (GNJPPassenger passenger : passengerList) {
//			//2.保存旅客信息
//			passenger.setId(UUID.randomUUID().toString());
//			passenger.setJpOrder(jpOrder);
//			passengerDAO.update(passenger);
//		}
	
	}
	
	/***
	 * 
	 * @方法功能说明：dto转化
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月23日上午10:18:39
	 * @修改内容：
	 * @参数：@param yxFlightOrderDTO
	 * @参数：@return
	 * @return:PlfxJPOrderDTO
	 * @throws
	 */
	private YeeXingJPOrderDTO revernt(GNJPOrder jpOrder) {
	    YeeXingJPOrderDTO yeeXingJPOrderDTO = new YeeXingJPOrderDTO();
	    yeeXingJPOrderDTO.setCreateTime(jpOrder.getCreateTime());
	    yeeXingJPOrderDTO.setIs_success(jpOrder.getIs_success());
	    yeeXingJPOrderDTO.setOrderid(jpOrder.getYeeXingOrderId());//易行天下订单号
	    yeeXingJPOrderDTO.setOut_orderid(jpOrder.getOrderNo());//平台订单号
	    yeeXingJPOrderDTO.setDealerOrderId(jpOrder.getDealerOrderCode());//经销商订单号
	    List<YeeXingPassengerDTO> passengerList = new ArrayList<YeeXingPassengerDTO>();
	    for(GNJPPassenger psg:jpOrder.getPassengerList()){
	    	YeeXingPassengerDTO yeeXingPassengerDTO = new YeeXingPassengerDTO();
	    	yeeXingPassengerDTO.setIdNo(psg.getIdNo());//证件号
	    	yeeXingPassengerDTO.setIdType(psg.getIdType());//证件类型
	    	yeeXingPassengerDTO.setPassengerName(psg.getName());//姓名
	    	yeeXingPassengerDTO.setPassengerType(psg.getPsgType());//乘客类型
	    	passengerList.add(yeeXingPassengerDTO);
	    }
	    yeeXingJPOrderDTO.setPassengerList(passengerList);
	    YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
	    yeeXingPriceDTO.setChangePnr(jpOrder.getChangePnr());
	    yeeXingPriceDTO.setDisc(jpOrder.getDisc());
	    yeeXingPriceDTO.setExtReward(jpOrder.getExtReward());
	    yeeXingPriceDTO.setIbePrice(jpOrder.getIbePrice());
	    yeeXingPriceDTO.setIsSphigh(jpOrder.getIsSphigh());
	    yeeXingPriceDTO.setMemo(jpOrder.getMemo());
	    yeeXingPriceDTO.setOutTime(jpOrder.getOutTime());
	    yeeXingPriceDTO.setPayType(jpOrder.getPayPlatform());
	    yeeXingPriceDTO.setPlcid(jpOrder.getPlcid());
	    yeeXingPriceDTO.setTickPrice(jpOrder.getTickPrice());
	    yeeXingPriceDTO.setTickType(jpOrder.getTickType());
	    yeeXingPriceDTO.setInfPrice(jpOrder.getInfPrice());
	    yeeXingPriceDTO.setOilFee(jpOrder.getOilFee());
	    yeeXingPriceDTO.setBuildFee(jpOrder.getBuildFee());
	    yeeXingPriceDTO.setProfits(jpOrder.getProfits());
	    yeeXingPriceDTO.setRestWorkTime(jpOrder.getRestWorkTime());
	    yeeXingPriceDTO.setTotalPrice(jpOrder.getTotalPrice());
	    yeeXingPriceDTO.setWorkTime(jpOrder.getWorkTime());
//	    yeeXingPriceDTO.setPlatPolicyPirce(jpOrder.getPlatPolicyPirce());
	    yeeXingPriceDTO.setTotalPrice(jpOrder.getTotalPrice());
	    yeeXingPriceDTO.setPlatTotalPrice(jpOrder.getPlatTotalPrice());
	    yeeXingJPOrderDTO.setPriceDTO(yeeXingPriceDTO);
		return yeeXingJPOrderDTO;
	}
	/**
	 * 
	 * @方法功能说明：保存机票信息
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月30日下午2:43:44
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @return:void
	 * @throws
	 */
	public void saveJPOrder(GNJPOrder jpOrder) {
		this.jpOrderDAO.save(jpOrder);
	}
		
	/***
	 * 
	 * @方法功能说明：保存乘客信息
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月30日下午2:43:57
	 * @修改内容：
	 * @参数：@param passenger
	 * @return:void
	 * @throws
	 */
	public void savePassenger(GNJPPassenger passenger) {
		this.passengerDAO.save(passenger);
	}
	/**
	 * @方法功能说明：更新机票订单信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月29日下午5:45:32
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public Boolean updateJPOrder(JPOrderCommand command) {
		HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->updateJPOrder->[更新机票订单信息开始]");
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYeeXingOrderId())) {
			return bool;// 空指针，直接返回
		}
		
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setRefundType("A");
		platformOrderQO.setYeeXingOrderId(command.getYeeXingOrderId());
		GNJPOrder jpOrder = this.queryUnique(platformOrderQO);
		HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->updateJPOrder->出票完成的原始订单：" + JSON.toJSONString(jpOrder));
		if(jpOrder != null){
			try{
				GNJPOrderStatus orderStatus = new GNJPOrderStatus();
				orderStatus.setStatus(command.getStatus());
				orderStatus.setPayStatus(command.getPayStatus());
				jpOrder.setOrderStatus(orderStatus);
				jpOrder.updateJPOrder(command);
				//先保存乘机人底下关联的机票实体
				for(GNJPPassenger passenger:jpOrder.getPassengerList()){
					if(passenger.getTicket() != null){
						flightTicketDAO.save(passenger.getTicket());
					}
				}
				//然后保存乘机人信息
				passengerDAO.updateList(jpOrder.getPassengerList());
				//最后保存机票订单信息
				getDao().update(jpOrder);
				bool = true;
				// 通知经销商更新机票订单信息
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				HttpUtils.reqForPost(command.getNotifyUrl(),
						"msg=" + JSON.toJSONString(command), 60000);
				JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
				if(jpOrder.getDealerOrderCode() != null){
					jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
				}
				if(jpOrder.getOrderNo() != null){
					jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
				}
				if(command.getType() != null){
					
					jpNotifyMessageApiCommand.setType(command.getType());
				}
				if(command.getPassengerName() != null){
					jpNotifyMessageApiCommand.setPassengerName(command.getPassengerName());
				}
				if(command.getAirId() != null){
					jpNotifyMessageApiCommand.setAirId(command.getAirId());
				}
				if(command.getNewPnr() != null){
					jpNotifyMessageApiCommand.setNewPnr(command.getNewPnr());
				}
				jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_SUCCESS);
				sendMessage(jpNotifyMessageApiCommand);
			}catch(Exception e){
				HgLogger.getInstance().error("yuqz", "GNJPOrderLocalService->updateJPOrder->[更新机票订单信息异常]:" + HgLogger.getStackTrace(e));
				bool = false;
			}
		}
		return bool;
	}

	/**
	 * @方法功能说明：更新机票订单支付信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午1:54:55
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public boolean updatePayStatus(JPPayNotifyCommand command) {
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYeeXingOrderId())) {
			return bool;// 空指针，直接返回
		}
		
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setRefundType("A");
		platformOrderQO.setYeeXingOrderId(command.getYeeXingOrderId());
		HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->updatePayStatus->platformOrderQO=" + JSON.toJSONString(platformOrderQO));
		GNJPOrder jpOrder = getDao().queryUnique(platformOrderQO);
		HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->updatePayStatus->初始jpOrder=" + JSON.toJSONString(jpOrder));
		if(jpOrder != null){
			GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
			orderStatus.setPayStatus(command.getPayStatus());
			jpOrder.setPlatPaySerialNumber(command.getPayId());
			jpOrder.setPayPlatform(command.getPayplatform());
			jpOrder.setPayType(command.getPayType());
			HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->updatePayStatus->修改后jpOrder=" + JSON.toJSONString(jpOrder));
			getDao().update(jpOrder);
			bool = true;
			
			// 通知经销商更新机票订单信息
			command.setDealerOrderCode(jpOrder.getDealerOrderCode());
			HttpUtils.reqForPost(command.getNotifyUrl(),
					"msg=" + JSON.toJSONString(command), 60000);
			JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
			if(jpOrder.getDealerOrderCode() != null){
				jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
			}
			if(jpOrder.getOrderNo() != null){
				jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
			}
			if(command.getPayId() != null){
				jpNotifyMessageApiCommand.setPayId(command.getPayId());
			}
			if(command.getTotalPrice() != null){
				jpNotifyMessageApiCommand.setTotalPrice(command.getTotalPrice());
			}
			if(command.getPayplatform() != null){
				jpNotifyMessageApiCommand.setPayPlatform(command.getPayplatform());
			}
			if(command.getPayType() != null){
				jpNotifyMessageApiCommand.setPayType(command.getPayType());
			}
			jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_PAY_SUCCESS);
			sendMessage(jpNotifyMessageApiCommand);
		}
		return bool;
	}

	/**
	 * 
	 * @方法功能说明：订单取消成功修改状态
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午2:40:11
	 * @修改内容：
	 * @参数：@param jpOrderCancelCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean jpOrderCancel(JPOrderCancelCommand command) {
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYeeXingOrderId())) {
			return bool;// 空指针，直接返回
		}
		
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setYeeXingOrderId(command.getYeeXingOrderId());
		platformOrderQO.setRefundType("C");
		List<GNJPOrder> jpOrderList = this.queryList(platformOrderQO);
		out:
		for(GNJPOrder order : jpOrderList){
			for(GNJPPassenger passenger : order.getPassengerList()){
				if(passenger.getName() != null && passenger.getName().equals(command.getPassengerName())){
					GNJPOrderStatus orderStatus = order.getOrderStatus();
					//如果支付状态为待退款-> 已退款   
					if(orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_WAIT)){
						orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
					}
					passengerDAO.update(order);
					bool = true;
					JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
					if(order.getDealerOrderCode() != null){
						jpNotifyMessageApiCommand.setDealerOrderCode(order.getDealerOrderCode());
					}
					if(order.getOrderNo() != null){
						jpNotifyMessageApiCommand.setOrderNo(order.getOrderNo());
					}
					if(command.getPassengerName() != null){
						jpNotifyMessageApiCommand.setPassengerName(command.getPassengerName());
					}
					jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_CANCEL_SUCCESS);
					boolean b = sendMessage(jpNotifyMessageApiCommand);
					HgLogger.getInstance().info("yuqz", "发送取消订单通知结果=" + b);
					break out;
				}
			}
		}
		return bool;
	}

	/**
	 * 
	 * @方法功能说明：处理退废票通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月8日上午10:42:50
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean jpOrderRefund(JPOrderRefundCommand command) {
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYeeXingOrderId())) {
			return bool;// 空指针，直接返回
		}
		String airId = command.getAirId();
		String [] airIds = airId.split("\\^");
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setRefundType("T");//申请退废票的时候就生成了退废票单
		platformOrderQO.setYeeXingOrderId(command.getYeeXingOrderId());
		GNJPOrder jpOrder = new GNJPOrder();
		List<GNJPOrder> jpOrderList = jpOrderDAO.queryList(platformOrderQO);
		HgLogger.getInstance().info("yuqz", "GNJPOrderLocalService->jpOrderRefund->jpOrderList:" + JSON.toJSONString(jpOrderList));
		out:
		for(GNJPOrder order : jpOrderList){
			for(GNJPPassenger passenger : order.getPassengerList()){
				for(int i = 0; i < airIds.length; i++){
					if(passenger.getTicket() != null && passenger.getTicket().getTicketNo() != null
							&& passenger.getTicket().getTicketNo().equals(airIds[i])){
						jpOrder = order;
						break out;
					}
				}
			}
		}
		if(jpOrder != null){
			//退废票成功
			if(command.getRefundStatus() == 1){
				//更改订单状态为退废票成功，支付状态为已退款
				GNJPOrderStatus orderStatus = new GNJPOrderStatus();
				orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_SUCC));
				orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
				jpOrder.setOrderStatus(orderStatus);
				jpOrder.setRefundPrice(command.getRefundPrice());
				jpOrder.setProcedures(command.getProcedures());
				bool = true;
			}
			//拒绝退废票
			else if(command.getRefundStatus() == 2){
				//更改订单状态为退废票失败，支付状态为已支付
				GNJPOrderStatus orderStatus = new GNJPOrderStatus();
				orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_FAIL));
				orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));
				jpOrder.setOrderStatus(orderStatus);
				bool = true;
			}
			
		}
		getDao().update(jpOrder);
		HgLogger.getInstance().info("yuqz", "退废票更新订单结束->jpOrder:" + JSON.toJSONString(jpOrder));
		//给经销商发送通知
		JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
		if(jpOrder.getDealerOrderCode() != null){
			jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
		}
		if(jpOrder.getOrderNo() != null){
			jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
		}
		jpNotifyMessageApiCommand.setAirId(airId);
		if(command.getRefundPrice() != null){
			jpNotifyMessageApiCommand.setRefundPrice(command.getRefundPrice());
		}
		if(command.getRefundStatus() != null){
			jpNotifyMessageApiCommand.setRefundStatus(command.getRefundStatus());
		}
		if(command.getRefuseMemo() != null){
			jpNotifyMessageApiCommand.setRefuseMemo(command.getRefuseMemo());
		}
		if(command.getProcedures() != null){
			jpNotifyMessageApiCommand.setProcedures(command.getProcedures());
		}
		jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_REFUSE_SUCCESS);
		sendMessage(jpNotifyMessageApiCommand);
		return bool;
//		if(jpOrder != null){
//			List<GNJPPassenger> passengers = jpOrder.getPassengerList();
//			String airId = command.getAirId();
//			String [] airIds = airId.split("\\^");
//			List<GNJPPassenger> newPassengers = new ArrayList<GNJPPassenger>();
//			for(GNJPPassenger passenger : passengers){
//				for(int i = 0; i < airIds.length; i++){
//					if(command.getRefundStatus() == 1 && passenger.getTicket() != null 
//							&& passenger.getTicket().getTicketNo()!= null && 
//							passenger.getTicket().getTicketNo().equals(airIds[i])){
//						//删除取消订单的乘机人记录
//						newPassengers.add(passenger);
//						//给经销商发送通知
//						JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
//						jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerCode());
//						jpNotifyMessageApiCommand.setAirId(airIds[i]);
//						jpNotifyMessageApiCommand.setRefundStatus(command.getRefundStatus());
//						jpNotifyMessageApiCommand.setRefuseMemo(command.getRefuseMemo());
//						jpNotifyMessageApiCommand.setProcedures(command.getProcedures());
//						jpNotifyMessageApiCommand.setType(command.getType());
//						sendMessage(jpNotifyMessageApiCommand);
//					}else if(command.getRefundStatus() == 2){
//						passenger.setRefuseMemo(command.getRefuseMemo());
//						newPassengers.add(passenger);
//						//给经销商发送通知
//						JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
//						jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerCode());
//						jpNotifyMessageApiCommand.setAirId(airIds[i]);
//						jpNotifyMessageApiCommand.setRefundStatus(command.getRefundStatus());
//						jpNotifyMessageApiCommand.setRefuseMemo(command.getRefuseMemo());
//						jpNotifyMessageApiCommand.setProcedures(command.getProcedures());
//						sendMessage(jpNotifyMessageApiCommand);
//					}
//				}
//			}
//			//增加一条退废票记录
//			try{
//				GNJPOrder newJPOrder = new GNJPOrder(jpOrder,newPassengers,command);
//				//退废单更改状态
//				GNJPOrderStatus orderStatus = new GNJPOrderStatus();
//				//退废票成功,订单状态变为退废票成功，支付状态变为已退款（易行只有在退款成功的时候才会发通知）
//				orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_SUCC));
//				orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
//				newJPOrder.setOrderStatus(orderStatus);
//				List<GNJPPassenger> passengerList = newJPOrder.getPassengerList();
//				getDao().save(newJPOrder);
//				passengerDAO.saveList(passengerList);
//				bool = true;
//			}catch(Exception e){
//				e.printStackTrace();
//				bool = false;
//				return bool;
//			}
//		}
	}

	/**
	 * @方法功能说明：处理拒绝出票通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午5:19:27
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public boolean jpOrderRefuse(JPOrderRefuseCommand command) {
		boolean bool = false;
		if (command == null || StringUtils.isBlank(command.getYeeXingOrderId())) {
			return bool;// 空指针，直接返回
		}
		
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setYeeXingOrderId(command.getYeeXingOrderId());
		GNJPOrder jpOrder = this.queryUnique(platformOrderQO);
		GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
		orderStatus.setStatus(command.getStatus());//设置订单状态为出票失败
		orderStatus.setPayStatus(command.getPayStatus());//设置支付状态为已退款
		if(jpOrder != null){
			List<GNJPPassenger> passengers = jpOrder.getPassengerList();
			String passengerName = command.getPassengerName();
			String[] passengerNames = passengerName.split("\\^");
			for(GNJPPassenger passenger : passengers){
				for(int i = 0; i < passengerNames.length; i++){
					if(passenger.getName().equals(passengerNames[i])){
						passenger.setRefuseMemo(command.getRefuseMemo());
						//给经销商发送通知
						JPNotifyMessageApiCommand jpNotifyMessageApiCommand = new JPNotifyMessageApiCommand();
						if(jpOrder.getDealerOrderCode() != null){
							jpNotifyMessageApiCommand.setDealerOrderCode(jpOrder.getDealerOrderCode());
						}
						if(jpOrder.getOrderNo() != null){
							jpNotifyMessageApiCommand.setOrderNo(jpOrder.getOrderNo());
						}
						jpNotifyMessageApiCommand.setPassengerName(passengerNames[i]);
						if(command.getRefuseMemo() != null){
							jpNotifyMessageApiCommand.setRefuseMemo(command.getRefuseMemo());
						}
						jpNotifyMessageApiCommand.setType(JPNofityMessageConstant.TICKET_REFUSE_FAIL);
						sendMessage(jpNotifyMessageApiCommand);
					}
					
				}
			}
			passengerDAO.save(jpOrder);
			bool = true;
		}
		return bool;
	}

	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand) {
		HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->saveErrorJPOrder->[保存异常订单开始]"+ JSON.toJSONString(jpOrderCommand));
		
		if (jpOrderCommand == null) {
			return false;
		}
		
		/*PlatformOrderQO qo = new PlatformOrderQO();
		qo.setId(jpOrderCommand.getId());
		// 查询机票订单
		GNJPOrder GNJPOrder = this.queryUnique(qo);

		// 设置异常订单的调整属性
		GNJPOrder.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
		GNJPOrder.setAdjustAmount(jpOrderCommand.getAdjustAmount());
		GNJPOrder.setVoucherPicture(jpOrderCommand.getVoucherPicture());
		GNJPOrder.setAdjustReason(jpOrderCommand.getAdjustReason());
		this.update(GNJPOrder);*/

		//将退废单的状态也修改成异常订单
		PlatformOrderQO qo2 = new PlatformOrderQO();
		qo2.setOrderNo(jpOrderCommand.getOrderNo());
		qo2.setRefundType("A");//排除取消和退废票的订单记录
		// 查询机票订单
		List<GNJPOrder> JPOrderTempList = this.queryList(qo2);
		for (GNJPOrder o : JPOrderTempList) {
			// 设置异常订单的调整属性
			o.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
			o.setAdjustAmount(jpOrderCommand.getAdjustAmount());
			o.setVoucherPicture(jpOrderCommand.getVoucherPicture());
			o.setAdjustReason(jpOrderCommand.getAdjustReason());
			this.update(o);
		}
		
		DomainEvent event = new DomainEvent("plfx.jp.domain.model.order.JPOrder","saveErrorJPOrder",JSON.toJSONString(jpOrderCommand));
		domainEventRepository.save(event);
		
		HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->saveErrorJPOrder->[保存异常订单结束]");
		return true;
	}
	
	/**
	 * 
	 * @方法功能说明：通知发送失败3次就不再发送
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月8日上午10:19:57
	 * @修改内容：
	 * @参数：@param jpNotifyMessageApiCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean sendMessage(JPNotifyMessageApiCommand jpNotifyMessageApiCommand){
		HgLogger.getInstance().info("yuqz", "发送通知:jpNotifyMessageApiCommand=" + JSON.toJSONString(jpNotifyMessageApiCommand));
		boolean bool = sendNotifyMessage(jpNotifyMessageApiCommand);
		int notifyCount = 1;
		HgLogger.getInstance().info("yuqz", "发送通知第" + notifyCount + "次" + bool);
		//通知3次都失败就不通知了
		while(!bool && notifyCount < 3){
			bool = sendNotifyMessage(jpNotifyMessageApiCommand);
			notifyCount++;
			HgLogger.getInstance().info("yuqz", "发送通知第" + notifyCount + "次" + bool);
		}
		return bool;
	}
	/**
	 * @方法功能说明：向经销商发送通知消息
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月7日下午5:14:59
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	private boolean sendNotifyMessage(JPNotifyMessageApiCommand jpNotifyMessageApiCommand) {
		boolean bool = false;
		SynchronizationJPNotifyMessage baseAmqpMessage = new SynchronizationJPNotifyMessage();
		baseAmqpMessage.setContent(jpNotifyMessageApiCommand);
		baseAmqpMessage.setType(Integer.parseInt(jpNotifyMessageApiCommand.getType()));
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->sendNotifyMessage:"+ JSON.toJSONString(baseAmqpMessage));
			template.convertAndSend("plfx.gnjp.sendNotifyMessage",baseAmqpMessage);			
			HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->sendNotifyMessage-成功");
			bool = true;
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz","GNJPOrderLocalService->sendNotifyMessage-失败:"+ HgLogger.getStackTrace(e));
			return bool;
		}
		return bool;
	}
	
	/**
	 * 查询机场编码信息
	 * @param cityAirCodeQO
	 * @return
	 */
	public  AirPortCode queryLocalAirPortCode(AirPortCodeQO airPortCodeQO) {
		return airPortCodeDAO.queryUnique(airPortCodeQO);
	}
	
	/****
	 * 
	 * @方法功能说明：自动扣款接口
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月13日下午2:02:49
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingPayOutResponseDTO
	 * @throws
	 */
	public  YeeXingPayOutResponseDTO autoPayOut(JPPayOutCommand command){
		YeeXingPayOutResponseDTO yeeXingPayOutResponseDTO = new YeeXingPayOutResponseDTO();
		try{
			if(null == command.getOrderid()||null == command.getPay_notify_url()||null == command.getOut_notify_url()||null == command.getTotalPrice()){
				HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->autoPayOut->[自动扣款失败(参数为空)]:"+JSON.toJSONString(command));
			}
			String payTypeTemp = command.getPayPlatform().toString();
			
			//截取第一位判断是不是1
			String payType = payTypeTemp.substring(0, 1);
			HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->autoPayOut->[自动扣款失败(公司只支持支付宝支付)1代表支付宝支付]:"+payType);
			//对创建订单易行返回的支付方式进行判断(1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267)
			//公司只支持支付宝支付,机票支付方式返回含1的都能支付,非1支付失败
			if(!payType.equals("1")){
				HgLogger.getInstance().error("yaosanfeng", "GNJPOrderLocalService->autoPayOut->[自动扣款失败(公司只支持支付宝支付)]:"+JSON.toJSONString(command));
				throw new Exception("目前机票只支持支付宝支付");
			}
			//设置支付方式为支付宝支付(1代表支付宝)
			if(StringUtils.isBlank(payTypeTemp)||payType.equals("1")){
			   command.setPayPlatform(JPOrderStatusConstant.ZFFS_ZFB);
			}
			String result=null;
			Map<String, String> params = new HashMap<String, String>();
			//获取cc配置的用户名和密码
			command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
			command.setKey(SysProperties.getInstance().get("yeeXing_key"));
			params.put("getUserName", command.getUserName());
			params.put("getOrderid", command.getOrderid());
			params.put("getTotalPrice", command.getTotalPrice());
			params.put("getPayPlatform", command.getPayPlatform().toString());
			params.put("getPay_notify_url", command.getPay_notify_url());
			params.put("getOut_notify_url", command.getOut_notify_url());
			String sign = SignTool.sign(params, command.getKey());
			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->autoPayOut->[自动扣款开始]:"+ JSON.toJSONString(command));
		    result = ibeServicePortType.payOut(command.getUserName(), command.getOrderid(), command.getTotalPrice().toString(), command.getPayPlatform().toString(), command.getPay_notify_url(), command.getOut_notify_url(), sign);
		    HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->autoPayOut->[自动扣款结束]:"+ JSON.toJSONString(result));
			Document document = DocumentHelper.parseText(result);
	 	    Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			for(Element e:childrenList){
				if(e.getName().equals("orderid")){
					yeeXingPayOutResponseDTO.setOrderid(e.getText());
				}
				if(e.getName().equals("totalPrice")){
					if(StringUtils.isNotBlank(e.getText())){
						 yeeXingPayOutResponseDTO.setTotalPrice(Double.parseDouble(e.getText()));
					} 
				}
				if(e.getName().equals("payid")){
					yeeXingPayOutResponseDTO.setPayid(e.getText());
				}
				if(e.getName().equals("pay_status")){
					yeeXingPayOutResponseDTO.setPay_status(e.getText());
				}
                if(e.getName().equals("is_success")){
            		yeeXingPayOutResponseDTO.setIs_success(e.getText());
				}
                if(e.getName().equals("error")){ //如果请求要求没按接口参数传,易行会返回错误信息
					if(StringUtils.isNotBlank(e.getText())){
						yeeXingPayOutResponseDTO.setError(e.getText().trim());
					}	
				}
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("yaosanfeng","PayOut-->[自动扣款异常]"+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->autoPayOut->yeeXingPayOutResponseDTO:"+ JSON.toJSONString(yeeXingPayOutResponseDTO));
		return yeeXingPayOutResponseDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：国内机票自动扣款
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月21日下午5:38:07
	 * @修改内容：
	 * @参数：@param spiCommand
	 * @参数：@return
	 * @return:YeeXingPayJPOrderDTO
	 * @throws
	 */
	public YeeXingPayJPOrderDTO apiPayJPOrder(JPPayOrderSpiCommand command) {
		HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->apiPayJPOrder->:JPPayOrderSpiCommand"+JSON.toJSONString(command));
		YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO = new YeeXingPayJPOrderDTO();
		try {
		if(null == command.getOrderid()||null == command.getTotalPrice()){
			HgLogger.getInstance().error("yaosanfeng", "GNJPOrderLocalService->apiPayJPOrder->[自动扣款失败(参数为空)]:"+JSON.toJSONString(command));
		}

		String result = "";
		Map<String, String> params = new HashMap<String, String>();
		command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
		command.setPayPlatform(1);//支付宝支付
		command.setPay_notify_url(SysProperties.getInstance().get("http_domain")+ "/airTicket/notify");
		command.setOut_notify_url(SysProperties.getInstance().get("http_domain")+ "/airTicket/notify");
		params.put("getUserName", command.getUserName() == null ? "" : command.getUserName());
		params.put("getOrderid", command.getOrderid()== null ? "" : command.getOrderid());
		params.put("getTotalPrice", this.doubleToString(command.getTotalPrice())== null ? "" : this.doubleToString(command.getTotalPrice()));
		params.put("getPayPlatform", command.getPayPlatform()== null ? "" : command.getPayPlatform() + "");
		params.put("getPay_notify_url", command.getPay_notify_url()== null ? "" : command.getPay_notify_url());
		params.put("getOut_notify_url", command.getOut_notify_url()== null ? "" : command.getOut_notify_url());
		String sign = SignTool.sign(params, SysProperties.getInstance().get("yeeXing_key"));
		//调用易行天下机票自动扣款接口(对totalPrice传过来的金额做处理 （如订单金额）以元为单位，必须带两位小数点，如3.50元，1200.00元。)
		HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->apiPayJPOrder->[自动扣款参数]:params="
				+ params + ",sign=" + sign + ",key=" + SysProperties.getInstance().get("yeeXing_key"));
		result = ibeServicePortType.payOut(command.getUserName(), command.getOrderid(), this.doubleToString(command.getTotalPrice()), command.getPayPlatform().toString(), command.getPay_notify_url(), command.getOut_notify_url(), sign);
		HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->apiPayJPOrder->[自动扣款resut]="+JSON.toJSONString(result));
		Document document = DocumentHelper.parseText(result);
 	    Element root = document.getRootElement();
		List<Element> childrenList = root.elements();
		for(Element e:childrenList){
			if(e.getName().equals("orderid")){
				yeeXingPayJPOrderDTO.setOrderid(e.getText());
			}
			if(e.getName().equals("totalPrice")){
				if(StringUtils.isNotBlank(e.getText())){
					yeeXingPayJPOrderDTO.setTotalPrice(Double.parseDouble(e.getText()));
				}
			}
			if(e.getName().equals("payid")){
				yeeXingPayJPOrderDTO.setPayid(e.getText());
			}
			if(e.getName().equals("pay_status")){
				yeeXingPayJPOrderDTO.setPay_status(e.getText());
			}
            if(e.getName().equals("is_success")){
        	   yeeXingPayJPOrderDTO.setIs_success(e.getText());
			}
            if(e.getName().equals("error")){ //如果请求要求没按接口参数传,易行会返回错误信息
				if(StringUtils.isNotBlank(e.getText())){
					yeeXingPayJPOrderDTO.setError(e.getText().trim());
				}	
			}
		}
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
	  //platformOrderQO.setYeeXingOrderId(yeeXingPayJPOrderDTO.getOrderid());
		//最好是用command里面的易行订单号,如果调自动扣款接口失败,就不会返回易行订单号,取就取不到,后面的日志也无法记录
		platformOrderQO.setYeeXingOrderId(command.getOrderid());
		platformOrderQO.setRefundType("A");
		GNJPOrder jpOrder = this.queryUnique(platformOrderQO);
		//更新订单信息
		jpOrder = this.updatePayOrder(jpOrder, yeeXingPayJPOrderDTO);
		HgLogger.getInstance().info("yuqz","GNJPOrderLocalService->apiPayJPOrder->[自动扣款后更新订单jpOrder]:"
				+JSON.toJSONString(jpOrder));
		getDao().update(jpOrder);
		yeeXingPayJPOrderDTO.setDealerOrderCode(jpOrder.getDealerOrderCode());
		//添加日志记录
		if(yeeXingPayJPOrderDTO != null && StringUtils.isNotBlank(yeeXingPayJPOrderDTO.getIs_success()) && yeeXingPayJPOrderDTO.getIs_success().equals("T")){
			
			jpOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
					.getId(), "申请国内机票订单创建并付款", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "国内机票订单创建并付款成功"));
			
		}else{
			
			jpOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrder
					.getId(), "申请国内机票订单创建并付款", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "国内机票订单创建成功但付款失败"));
		}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("yaosanfeng","apiPayJPOrder-->[自动扣款异常]"+HgLogger.getStackTrace(e));
			return null;
		}
		return yeeXingPayJPOrderDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：自动扣款成功更新订单信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月23日上午11:13:38
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param yeeXingPayJPOrderDTO
	 * @参数：@return
	 * @return:GNJPOrder
	 * @throws
	 */
	private GNJPOrder updatePayOrder(GNJPOrder jpOrder,
			YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO) {
		HgLogger.getInstance().info("yuqz", "自动扣款成功更新订单信息->jpOrder:" + JSON.toJSONString(jpOrder));
		HgLogger.getInstance().info("yuqz", "自动扣款成功更新订单信息->yeeXingPayJPOrderDTO:" + JSON.toJSONString(yeeXingPayJPOrderDTO));
		jpOrder.setIs_success(yeeXingPayJPOrderDTO.getIs_success());
		jpOrder.setTotalPrice(yeeXingPayJPOrderDTO.getTotalPrice());
		jpOrder.setPlatPaySerialNumber(yeeXingPayJPOrderDTO.getPayid());
		if(yeeXingPayJPOrderDTO.getIs_success() != null && yeeXingPayJPOrderDTO.getIs_success().equals("T") &&
				yeeXingPayJPOrderDTO.getPay_status() != null && yeeXingPayJPOrderDTO.getPay_status().equals("T")){
			GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
			orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));//已支付
			orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_DEALING));//出票处理中
			HgLogger.getInstance().info("yuqz", "自动扣款成功更新订单信息->自动扣款成功");
		}
		//在最外层处理
//		else{
//			GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
//			//支付状态未支付不变
//			orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_FAIL));//出票失败
//			HgLogger.getInstance().info("yuqz", "自动扣款成功更新订单信息->自动扣款失败");
//		}
		return jpOrder;
	}

	/***
	 * 
	 * @方法功能说明：double转String保留两位小数
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日上午11:20:15
	 * @修改内容：
	 * @参数：@param totalPrice
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String doubleToString(Double totalPrice){
		DecimalFormat  formate = new DecimalFormat("#.00");
		return formate.format(totalPrice);
	}

	/****
	 * 
	 * @方法功能说明：生成订单并调用自动扣款
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午10:20:08
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingJPAutoOrderDTO
	 * @throws
	 */
	public YeeXingJPAutoOrderDTO apiAutoOrder(JPAutoOrderSpiCommand command) {
		//设置外部订单号(也就是分销平台的订单号)
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->command:"+ JSON.toJSONString(command));
		command.setOutOrderId(command.getFromDealerId()+command.getDealerOrderId());
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = new YeeXingJPAutoOrderDTO();
		try{
			String result = null;
			Map<String, String> params = new HashMap<String, String>();
			//获取cc配置用户名和密码
			command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
			command.setKey(SysProperties.getInstance().get("yeeXing_key"));
			params.put("getUserName", command.getUserName() == null ? "":command.getUserName());
			params.put("getEncryptString", command.getEncryptString() == null ? "":command.getEncryptString());
			params.put("getPassengerInfo", command.getPassengerInfo() == null ? "":command.getPassengerInfo());
			params.put("getOrderid", command.getOrderId() == null ? "":command.getOrderId());
			params.put("getOutOrderid", command.getOutOrderId() == null ? "":command.getOutOrderId());
			String sign = SignTool.sign(params, command.getKey());
			HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->apiAutoOrder->[易行生成订单参数]:params="
					+ JSON.toJSONString(params) + ",sign=" + sign + ",key=" + command.getKey());
		    result = ibeServicePortType.bookTicket(command.getUserName(), 
		    		command.getEncryptString(), command.getPassengerInfo(), command.getOrderId() == null ? "":command.getOrderId(), command.getOutOrderId() == null ? "":command.getOutOrderId(), sign);
			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->[易行生成订单结束result]=:"+ JSON.toJSONString(result));
			Document document = DocumentHelper.parseText(result);
	 	    Element root = document.getRootElement();
			List<Element> childrenList = root.elements();
			List<YeeXingPassengerDTO> plist = new ArrayList<YeeXingPassengerDTO>();
			for(Element e:childrenList){
				if(e.getName().equals("is_success")){
					if(e.getText().equals("T")){
						yeeXingJPAutoOrderDTO.setIs_success(e.getText());
					}else {
						yeeXingJPAutoOrderDTO.setIs_success(e.getText());
						HgLogger.getInstance().error("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->该次操作是否成功(T:成功F:失败)"+e.getText());
					}	
				}else if(e.getName().equals("error")){ //如果请求要求没按接口参数传,易行会返回错误信息
					if(StringUtils.isNotBlank(e.getText())){
						yeeXingJPAutoOrderDTO.setError(e.getText().trim());
					}	
				}else if(e.getName().equals("orderInfo")){
					yeeXingJPAutoOrderDTO.setOrderid(e.attributeValue("orderid"));
					yeeXingJPAutoOrderDTO.setOut_orderid(e.attributeValue("out_orderid"));
					yeeXingJPAutoOrderDTO.setCreateTime(e.attributeValue("createTime"));	
				}else if(e.getName().equals("price")){
					YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
					yeeXingPriceDTO.setChangePnr(e.attributeValue("changePnr"));
					yeeXingPriceDTO.setDisc(Double.parseDouble(e.attributeValue("disc")));
					yeeXingPriceDTO.setExtReward(Double.parseDouble(e.attributeValue("extReward")));
					yeeXingPriceDTO.setIbePrice(Double.parseDouble(e.attributeValue("ibePrice")));
					yeeXingPriceDTO.setIsSphigh(e.attributeValue("isSphigh"));
					yeeXingPriceDTO.setMemo(e.attributeValue("memo"));
					yeeXingPriceDTO.setOutTime(e.attributeValue("outTime"));
					yeeXingPriceDTO.setPayType(Integer.parseInt(e.attributeValue("payType")));
					yeeXingPriceDTO.setPlcid(Integer.parseInt(e.attributeValue("plcid")));
					yeeXingPriceDTO.setTickPrice(Double.parseDouble(e.attributeValue("tickPrice")));
					yeeXingPriceDTO.setTickType(Integer.parseInt(e.attributeValue("tickType")));
					//有婴儿的时候会有(目前暂不支持婴儿) 设置婴儿价全部为0.00
					yeeXingPriceDTO.setInfPrice(Double.parseDouble(e.attributeValue("infPrice").toString()==""?"0.00":e.attributeValue("infPrice")));
					yeeXingPriceDTO.setOilFee(Double.parseDouble(e.attributeValue("oilFee")));
					yeeXingPriceDTO.setBuildFee(Double.parseDouble(e.attributeValue("buildFee")));
					yeeXingPriceDTO.setProfits(Double.parseDouble(e.attributeValue("profits")));
					yeeXingPriceDTO.setRestWorkTime(e.attributeValue("restWorkTime"));
					yeeXingPriceDTO.setTotalPrice(Double.parseDouble(e.attributeValue("totalPrice")));
					yeeXingPriceDTO.setWorkTime(e.attributeValue("workTime"));
					yeeXingJPAutoOrderDTO.setPriceDTO(yeeXingPriceDTO);	
	            	HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->[易行天下生成订单接口开始]-->YeeXingJPOrderDTO"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
	            }else if(e.getName().equals("passengerinfos")){
					for (Iterator i = e.elementIterator(); i.hasNext();) { 
						YeeXingPassengerDTO  yeeXingPassengerDTO = new YeeXingPassengerDTO();
			            Element passengerElement = (Element) i.next(); 
			            yeeXingPassengerDTO.setPassengerName(passengerElement.attributeValue("passengerName"));
			            yeeXingPassengerDTO.setPassengerType(passengerElement.attributeValue("passengerType"));
			            plist.add(yeeXingPassengerDTO);      
				     }
					HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->易行天下生成订单接口开始-->List<YeeXingPassengerDTO>"+ JSON.toJSONString(plist));
	            }
				yeeXingJPAutoOrderDTO.setPassengerList(plist);
			 } 
			HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->[易行天下生成订单接口结束]->yeeXingFlightOrderDTO"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
		    if(yeeXingJPAutoOrderDTO != null && StringUtils.isNotBlank(yeeXingJPAutoOrderDTO.getIs_success()) && yeeXingJPAutoOrderDTO.getIs_success().equals("T")){
		    	//1从缓存中查航班信息
				YeeXingFlightDTO yeeXingFlightDTO = flightCacheManager.getYXFlightDTO(command.getFlightNo(), command.getStartDate());
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->YeeXingFlightDTO:"+ JSON.toJSONString(yeeXingFlightDTO));
				//2持久化化订单 订单状态设置
				GNJPOrderStatus status = new GNJPOrderStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_PAY_WAIT),Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_NO_PAY));
				//计算平台价格
				YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
				yeeXingPriceDTO.setIbePrice(yeeXingJPAutoOrderDTO.getPriceDTO().getIbePrice());
				yeeXingPriceDTO.setTotalPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice());
				yeeXingPriceDTO.setBuildFee(yeeXingJPAutoOrderDTO.getPriceDTO().getBuildFee());
				yeeXingPriceDTO.setOilFee(yeeXingJPAutoOrderDTO.getPriceDTO().getOilFee());
				JPPlatPriceDTO jpPlatPriceDTO = dealPlatPrice(yeeXingPriceDTO, command.getFromDealerId());
				//设置平台价格
				yeeXingJPAutoOrderDTO.getPriceDTO().setPlatTotalPrice(jpPlatPriceDTO.getPlatTotalPrice());
				yeeXingJPAutoOrderDTO.getPriceDTO().setPlatPolicyPirce(jpPlatPriceDTO.getPlatPolicyPirce());
				yeeXingJPAutoOrderDTO.getPriceDTO().setServiceCharge(jpPlatPriceDTO.getServiceCharge());
				//3创建订单
				GNJPOrder jpOrder = new GNJPOrder(command,yeeXingJPAutoOrderDTO,status,yeeXingFlightDTO);
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->GNJPOrder:"+ JSON.toJSONString(jpOrder));
				//4持久化下单成功后，返回DTO
				yeeXingJPAutoOrderDTO = this.saveJPAutoOrderInfo(jpOrder);
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->yeeXingJPOrderDTO:"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
				
				//支付宝余额进行计算
				PayBalancesQO payBalancesQO = new PayBalancesQO();
				payBalancesQO.setType(0);
				PayBalancesDTO payBalancesDTO = payBalancesService.queryUnique(payBalancesQO);
				payBalancesDTO.setBalances(payBalancesDTO.getBalances() + command.getUserPayCash());
				boolean bool = payBalancesService.updatePayBalances(payBalancesDTO);
				HgLogger.getInstance().info("yuqz", "国内机票生成订单更新支付宝余额返回值=" + bool);
				
				//5调用自动扣款接口
				JPPayOrderSpiCommand jpPayOrderSpiCommand = new JPPayOrderSpiCommand();
				//易行订单号
				jpPayOrderSpiCommand.setOrderid(yeeXingJPAutoOrderDTO.getOrderid());
				//支付易行的价格
				jpPayOrderSpiCommand.setTotalPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice());
				//经销商代码形式如F001
				jpPayOrderSpiCommand.setFromDealerId(command.getFromDealerId());
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->JPPayOrderSpiCommand:"+ JSON.toJSONString(jpPayOrderSpiCommand));
				//调用易行国内机票自动扣款接口
				YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO = this.apiPayJPOrder(jpPayOrderSpiCommand);
				HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->YeeXingPayJPOrderDTO:"+ JSON.toJSONString(yeeXingPayJPOrderDTO));
				//Is_success为T代表扣款成功
				if(yeeXingPayJPOrderDTO != null && StringUtils.isNotBlank(yeeXingPayJPOrderDTO.getIs_success()) && yeeXingPayJPOrderDTO.getIs_success().equals("T")){
					//代扣状态
					yeeXingJPAutoOrderDTO.setPay_status(yeeXingPayJPOrderDTO.getPay_status());
					//支付公司流水号
					yeeXingJPAutoOrderDTO.setPayid(yeeXingPayJPOrderDTO.getPayid());
					//支付易行总价
					yeeXingJPAutoOrderDTO.setTotalPrice(yeeXingPayJPOrderDTO.getTotalPrice());
					//操作成功T
					yeeXingJPAutoOrderDTO.setIs_success(yeeXingPayJPOrderDTO.getIs_success());
					HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->yeeXingJPAutoOrderDTO:"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
					
					//支付宝余额进行计算
					PayBalancesQO payBlQO = new PayBalancesQO();
					payBlQO.setType(0);
					PayBalancesDTO payBlDTO = payBalancesService.queryUnique(payBlQO);
					payBlDTO.setBalances(payBlDTO.getBalances() - yeeXingPayJPOrderDTO.getTotalPrice());
					boolean b = payBalancesService.warnPayBalances(payBlDTO);
					HgLogger.getInstance().info("yuqz", "国内机票自动扣款更新支付宝余额返回值=" + b);
					
					return yeeXingJPAutoOrderDTO;
					
				}else{
					//支付返回F
					yeeXingJPAutoOrderDTO.setIs_success(yeeXingPayJPOrderDTO.getIs_success());
					//支付错误返回错误信息(失败原因+创建订单失败)
					yeeXingJPAutoOrderDTO.setError(yeeXingPayJPOrderDTO.getError()+",创建订单失败");
					HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->yeeXingJPAutoOrderDTO:"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
					return yeeXingJPAutoOrderDTO;
				}
			}else{
				//返回错误信息(失败原因+创建订单失败)
				yeeXingJPAutoOrderDTO.setError(yeeXingJPAutoOrderDTO.getError()+",创建订单失败");
			}
		    
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng","GNJPOrderLocalService->apiAutoOrder->[易行天下生成订单接口或自动扣款接口异常]"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return yeeXingJPAutoOrderDTO;
				  
	}
	
	
	 /****
     * 
     * @方法功能说明:持久化订单(生成订单并自动扣款)
     * @修改者名字：yaosanfeng
     * @修改时间：2015年6月30日下午2:43:15
     * @修改内容：
     * @参数：@param jpOrder
     * @参数：@return
     * @return:PlfxJPOrderDTO
     * @throws
     */
	public YeeXingJPAutoOrderDTO saveJPAutoOrderInfo(GNJPOrder jpOrder) {
		jpOrder.setId(UUIDGenerator.getUUID());
		this.saveJPOrder(jpOrder);
		//设置操作日志(先放着)
		// 旅客信息列表
		List<GNJPPassenger> passengerList = jpOrder.getPassengerList();
		for (GNJPPassenger passenger : passengerList) {
			//2.保存旅客信息
			passenger.setJpOrder(jpOrder);
			//保存乘客别的信息
			this.savePassenger(passenger);
		}
		//dto转化
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = jpOrderModuleTojpOrderDTO(jpOrder);
		return yeeXingJPAutoOrderDTO;
	}
	
	/***
	 * 
	 * @方法功能说明：dto转化
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月23日上午10:18:39
	 * @修改内容：
	 * @参数：@param yxFlightOrderDTO
	 * @参数：@return
	 * @return:PlfxJPOrderDTO
	 * @throws
	 */
	private YeeXingJPAutoOrderDTO jpOrderModuleTojpOrderDTO(GNJPOrder jpOrder) {
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = new YeeXingJPAutoOrderDTO();
		yeeXingJPAutoOrderDTO.setCreateTime(jpOrder.getCreateTime());
		yeeXingJPAutoOrderDTO.setIs_success(jpOrder.getIs_success());
		yeeXingJPAutoOrderDTO.setOrderid(jpOrder.getYeeXingOrderId());//易行天下订单号
		yeeXingJPAutoOrderDTO.setOut_orderid(jpOrder.getOrderNo());//平台订单号
		yeeXingJPAutoOrderDTO.setDealerOrderId(jpOrder.getDealerOrderCode());//经销商订单号
	    List<YeeXingPassengerDTO> passengerList = new ArrayList<YeeXingPassengerDTO>();
	    for(GNJPPassenger psg:jpOrder.getPassengerList()){
	    	YeeXingPassengerDTO yeeXingPassengerDTO = new YeeXingPassengerDTO();
	    	yeeXingPassengerDTO.setIdNo(psg.getIdNo());//证件号
	    	yeeXingPassengerDTO.setIdType(psg.getIdType());//证件类型
	    	yeeXingPassengerDTO.setPassengerName(psg.getName());//姓名
	    	yeeXingPassengerDTO.setPassengerType(psg.getPsgType());//乘客类型
	    	passengerList.add(yeeXingPassengerDTO);
	    }
	    yeeXingJPAutoOrderDTO.setPassengerList(passengerList);
	    YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
	    yeeXingPriceDTO.setChangePnr(jpOrder.getChangePnr());
	    yeeXingPriceDTO.setDisc(jpOrder.getDisc());
	    yeeXingPriceDTO.setExtReward(jpOrder.getExtReward());
	    yeeXingPriceDTO.setIbePrice(jpOrder.getIbePrice());
	    yeeXingPriceDTO.setIsSphigh(jpOrder.getIsSphigh());
	    yeeXingPriceDTO.setMemo(jpOrder.getMemo());
	    yeeXingPriceDTO.setOutTime(jpOrder.getOutTime());
	    yeeXingPriceDTO.setPayType(jpOrder.getPayPlatform());
	    yeeXingPriceDTO.setPlcid(jpOrder.getPlcid());
	    yeeXingPriceDTO.setTickPrice(jpOrder.getTickPrice());
	    yeeXingPriceDTO.setTickType(jpOrder.getTickType());
	    yeeXingPriceDTO.setInfPrice(jpOrder.getInfPrice());
	    yeeXingPriceDTO.setOilFee(jpOrder.getOilFee());
	    yeeXingPriceDTO.setBuildFee(jpOrder.getBuildFee());
	    yeeXingPriceDTO.setProfits(jpOrder.getProfits());
	    yeeXingPriceDTO.setRestWorkTime(jpOrder.getRestWorkTime());
	    yeeXingPriceDTO.setTotalPrice(jpOrder.getTotalPrice());
	    yeeXingPriceDTO.setWorkTime(jpOrder.getWorkTime());
//	    yeeXingPriceDTO.setPlatPolicyPirce(jpOrder.getPlatPolicyPirce());
	    yeeXingPriceDTO.setTotalPrice(jpOrder.getTotalPrice());
	    yeeXingPriceDTO.setPlatTotalPrice(jpOrder.getPlatTotalPrice());
	    yeeXingJPAutoOrderDTO.setPriceDTO(yeeXingPriceDTO);
		return yeeXingJPAutoOrderDTO;
	}
}


