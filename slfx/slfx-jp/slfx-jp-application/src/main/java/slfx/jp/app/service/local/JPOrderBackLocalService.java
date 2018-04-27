package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.ABEOrderDAO;
import slfx.jp.app.dao.ComparePriceDAO;
import slfx.jp.app.dao.FlightPolicyDAO;
import slfx.jp.app.dao.FlightTicketDAO;
import slfx.jp.app.dao.JPOrderBackDAO;
import slfx.jp.app.dao.JPOrderDAO;
import slfx.jp.app.dao.PassengerDAO;
import slfx.jp.app.dao.YGOrderDAO;
import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.command.client.ABEPassengerCommand;
import slfx.jp.command.client.ABEPriceDetailCommand;
import slfx.jp.command.client.YGFlightCommand;
import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.domain.model.order.ABEOrder;
import slfx.jp.domain.model.order.ABEOrderInfoDetail;
import slfx.jp.domain.model.order.ABEPassangerInfo;
import slfx.jp.domain.model.order.ComparePrice;
import slfx.jp.domain.model.order.FlightPolicy;
import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.domain.model.order.JPOrderBack;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.domain.model.order.PriceItem;
import slfx.jp.domain.model.order.YGFlight;
import slfx.jp.domain.model.order.YGOrder;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEXmlRtPnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
import slfx.jp.qo.admin.PlatformOrderBackQO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.YGOrderQO;
import slfx.jp.spi.inter.WebFlightService;
import slfx.yg.open.inter.YGFlightService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL退废订单SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:42:43
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class JPOrderBackLocalService extends BaseServiceImpl<JPOrderBack, PlatformOrderBackQO, JPOrderBackDAO>{
	
	/**
	 * 易购远程调用接口
	 */
	@Autowired
	private YGFlightService ygFlightService;
	
	/**
	 * 平台航班查询接口
	 */
	@Autowired
	private WebFlightService webFlightService;
	
	
	@Autowired
	private JPOrderDAO jpOrderDAO;
	
	@Autowired
	private JPOrderBackDAO jpOrderBackDAO;
	
	@Autowired
	private ABEOrderDAO abeOrderDAO;
	
	@Autowired
	private YGOrderDAO ygOrderDAO;
	@Autowired
	private ComparePriceDAO comparePriceDAO;
	@Autowired
	private FlightPolicyDAO flightPolicyDAO;
	@Autowired
	private FlightTicketDAO flightTicketDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Override
	protected JPOrderBackDAO getDao() {
	
		return jpOrderBackDAO;
	}
	
	//abe下单入库的实体
	private ABEOrder abeOrder;
	//易购下单入库的实体
	private YGOrder ygOrder;
	
	
	
	/**
	 * 查询平台机票的订单信息
	 * @param qo
	 * @return
	 */
	public JPOrder queryJPOrderUnique(PlatformOrderQO qo) {
		return jpOrderDAO.queryUnique(qo);
	}
	/**
	 * 查询易购机票的订单信息
	 * @param qo
	 * @return
	 */
	public YGOrder queryYGOrderUnique(YGOrderQO qo) {
		return ygOrderDAO.queryUnique(qo);
	}
	/**
	 * 查询ABE机票的订单信息
	 * @param qo
	 * @return
	 */
	public ABEOrder queryABEOrderUnique(PlatformOrderQO qo) {
		return abeOrderDAO.queryUnique(qo);
	}
	

	
	/**
	 * 通过 {flightNo-yyyyMMdd},从缓存获取航班信息(FlightDTO的返回格式)
	 * @param flightNo
	 * @param flightDateStr
	 * @return
	 */
	public YGFlightDTO queryCacheFlightInfo(String flightNo,String flightDateStr){
		return this.webFlightService.queryCacheFlightInfo(flightNo,flightDateStr);
	}
	
	/**
	 * abe 下单
	 * @param abeCommand
	 * @return
	 */
	public ABEOrderFlightDTO abeOrderFlight(ABEOrderFlightCommand abeCommand){
		//abe下单,（返回PNR码）
		ABEOrderFlightDTO abeOrderDto=this.ygFlightService.abeOrderFlight(abeCommand);
		
		//abe订单入库
		abeOrder=setABEOrderInfoFromABEOrderCommand(abeCommand);
		abeOrder.updateAbe(abeOrderDto);
		abeOrderDAO.save(abeOrder);
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","abeOrderFlight",JSON.toJSONString(abeCommand));
		domainEventRepository.save(event);
		if(abeOrderDto != null){
			abeOrderDto.setId(abeOrder.getId());			
		}
		return abeOrderDto;
	}
	
	/**
	 * 易购下单
	 * @param yGOrderCommand
	 * @return
	 */
	public YGFlightOrderDTO ygOrderByPnr(YGOrderCommand yGOrderCommand){
		
		//易购下单
		YGFlightOrderDTO yGFlightOrderDTO=this.ygFlightService.orderByPnr(yGOrderCommand);
	
		//易购订单入库
		
		ygOrder=this.setYGOrderInfoFromYGOrderCommand(yGOrderCommand,yGFlightOrderDTO);
		ygOrder.updateYg();
		this.ygOrderDAO.save(ygOrder);
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","ygOrderByPnr",JSON.toJSONString(yGOrderCommand));
		domainEventRepository.save(event);
		yGFlightOrderDTO.setId(ygOrder.getId());
		return yGFlightOrderDTO;
	}
	
	
	
	/**
	 * 设置abe订单入库实体类
	 * @param abeCommand
	 * @return
	 */
	private ABEOrder setABEOrderInfoFromABEOrderCommand(ABEOrderFlightCommand abeCommand){
		ABEOrder abeOrder =new ABEOrder();
		//拷贝基本属性
		String[] ignoreProperties =new String[]{"abePsgList","abePriceDetailList","abeLinkerInfoCommand"};
		BeanUtils.copyProperties(abeCommand, abeOrder,ignoreProperties);
		
		//拷贝旅客信息
		List<ABEPassangerInfo> abePsgInfoList =new ArrayList<ABEPassangerInfo>();
		List<ABEPassengerCommand> abePsgCommandList =abeCommand.getAbePsgList();
		if(abePsgCommandList!=null&&abePsgCommandList.size()>0){
			for(ABEPassengerCommand abePsgCommand:abePsgCommandList){
				ABEPassangerInfo abePassangerInfo =new ABEPassangerInfo();
				BeanUtils.copyProperties(abePsgCommand, abePassangerInfo);
				abePsgInfoList.add(abePassangerInfo);
			}
		}
		abeOrder.setAbePsgList(abePsgInfoList);
		
		
		//拷贝航班信息
		List<ABEPriceDetailCommand> abePriceDetailList=abeCommand.getAbePriceDetailList();
		List<PriceItem> abePriceItemList=new ArrayList<PriceItem>();
		
		if(abePriceDetailList!=null){
			for(ABEPriceDetailCommand abePriceDetail:abePriceDetailList){
				PriceItem priceItem =new PriceItem();
				BeanUtils.copyProperties(abePriceDetail, priceItem);
				abePriceItemList.add(priceItem);
			}
		}
		abeOrder.setAbePriceDetailList(abePriceItemList);
		
		/*//拷贝联系人
		Linker abeLinkerInfo =new Linker();
		ABELinkerInfoCommand abeLinker=abeCommand.getAbeLinkerInfoCommand();
		BeanUtils.copyProperties(abeLinker, abeLinkerInfo);
		abeOrder.setAbeLinkerInfo(abeLinkerInfo);*/
		
		
		return abeOrder;
	}
	
	/**
	 * 设置易购订单入库实体类
	 * @param abeCommand
	 * @return
	 */
	private YGOrder setYGOrderInfoFromYGOrderCommand(
			YGOrderCommand ygOrderCommand,
			YGFlightOrderDTO yGFlightOrderDTO){
		
		YGOrder ygOrder =new YGOrder();
		String[] ignoreProperties= new String[]{"flight","passengers"}; 
		BeanUtils.copyProperties(ygOrderCommand, ygOrder, ignoreProperties);
		
		//拷贝航班信息
		YGFlight ygFlight =new YGFlight();
		YGFlightCommand ygCommand=ygOrderCommand!=null?ygOrderCommand.getFlight():null;
		if(ygCommand!=null){
			BeanUtils.copyProperties("ygCommand", ygFlight);
		}
		ygOrder.setFlight(ygFlight);
		
		/*//拷贝旅客信息
		List<YGPassenger> passengers=new ArrayList<YGPassenger>();
		List<YGPassengerCommand> passengers_=ygOrderCommand.getPassengers();
		if(passengers_!=null&&passengers_.size()>0){
			for(YGPassengerCommand ygPsgCommand:passengers_){
				YGPassenger ygPassenger =new YGPassenger();
				BeanUtils.copyProperties(ygPsgCommand, ygPassenger);
				passengers.add(ygPassenger);
			}
		}
		ygOrder.setPassengers(passengers);*/
		
		//整合易购下单返回DTO中属性
		if(yGFlightOrderDTO!=null){
			ygOrder.setSupplierOrderNo(yGFlightOrderDTO.getSupplierOrderNo());
			ygOrder.setFare(yGFlightOrderDTO.getFare());
			ygOrder.setCommAmount(yGFlightOrderDTO.getCommAmount());
			//ygOrder.setCommMoney(yGFlightOrderDTO.getCommMoney());
			ygOrder.setCommRate(yGFlightOrderDTO.getCommRate());
			ygOrder.setTaxAmount(yGFlightOrderDTO.getTaxAmount());
		}
		
		
		return ygOrder;
	}
	
	
	/**
	 * 设置平台订单入库实体类
	 * @param jpOrder
	 * @param abeId 表主键关联
	 * @param ygId  表主键关联
	 * @return
	 */
	@Transactional
	public JPOrder saveJPOrderInfo(JPOrder jpOrder) {

		// 旅客信息列表
		Set<Passenger> passangerList = jpOrder.getPassangerList();
		for (Passenger passenger : passangerList) {
			passenger.getTicket().setId(UUID.randomUUID().toString());
			//1.保存机票信息
			this.saveFlightTicket(passenger.getTicket());
			passenger.setId(UUID.randomUUID().toString());
			//2.保存旅客信息
			this.savePassenger(passenger);
		}
		/*//3. 保存政策信息
		jpOrder.getComparePrice().getCompareResultPolicy()
				.setId(UUID.randomUUID().toString());
		this.saveFlightPolicy(jpOrder.getComparePrice()
				.getCompareResultPolicy());
		//4. 保存比价信息
		jpOrder.getComparePrice().setId(UUID.randomUUID().toString());
		this.saveComparePrice(jpOrder.getComparePrice());*/
		jpOrder.setCreateDate(new Date());
		this.jpOrderDAO.save(jpOrder);
		return jpOrder;
	}
	
	
	/**
	 * 单元测试
	 * @param ygOrder
	 */
	public void saveYGOrder(YGOrder ygOrder) {
		this.ygOrderDAO.save(ygOrder);
	}
	
	/**
	 * 单元测试
	 * @param jpOrder
	 */
	public void saveJPOrder(JPOrder jpOrder) {
		this.jpOrderDAO.save(jpOrder);
	}
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveABEOrder(ABEOrder abeOrder) {
		this.abeOrderDAO.save(abeOrder);
	}
	/**
	 * 单元测试
	 * @param flightTicket
	 */
	public void savePassenger(Passenger passenger) {
		this.passengerDAO.save(passenger);
	}
	
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveComparePrice(ComparePrice comparePrice) {
		this.comparePriceDAO.save(comparePrice);
	}
	
	/**
	 * 单元测试
	 * @param abeOrder
	 */
	public void saveFlightPolicy(FlightPolicy flightPolicy) {
		this.flightPolicyDAO.save(flightPolicy);
	}
	
	/**
	 * 单元测试
	 * @param flightTicket
	 */
	public void saveFlightTicket(FlightTicket flightTicket) {
		this.flightTicketDAO.save(flightTicket);
	}
	
	public void testABEOrderSave(){
		ABEOrder abeOrder = new ABEOrder();
		abeOrder.setId("123456789321654987");
		//abeOrder.setYprice(880.25);
		/*
		abeOrder.setStartTime("08:28");
		abeOrder.setStartDate("2014-05-06");
		abeOrder.setStartCityCode("ABC");
		abeOrder.setMileage(22);
		abeOrder.setFuelSurTax(222.30);
		abeOrder.setFlightNo("MH370");
		abeOrder.setEndTime("11:23");
		abeOrder.setEndDate("2014-05-06");
		abeOrder.setEndCityCode("XYZ");
		abeOrder.setClassRebate("40");
		abeOrder.setClassPrice(257.35);
		abeOrder.setClassCode("Y");
		abeOrder.setCarrier("MH");
		abeOrder.setAirportTax(50.5);
		abeOrder.setAircraftCode("737");
		
		Linker  linker = new Linker();
		linker.setMobilePhone("13755555555");
		linker.setPayType("支付宝");
		
		 * linker.setAddress("浙江杭州滨江");
		linker.setInvoicesSendType("A");
		linker.setIsETiket("Y");
		linker.setIsPrintSerial("Y");
		linker.setLinkerEmail("1212@163.com");
		linker.setLinkerName("大妈大叔");
		linker.setNeedInvoices("Y");
		linker.setSendTime(null);
		linker.setSendTktsTypeCode("ZQ");
		linker.setTelphone("13755555555");
		linker.setZip("314400");
		abeOrder.setAbeLinkerInfo(linker);
		 */
		
		//abeOrder.setAbePriceDetailListJson("[{a:'aaa',ab:null,abc:254}");
		
		ABEOrderInfoDetail oid = new ABEOrderInfoDetail();
		oid.setAddress("杭州市西湖区文三路222号");
		oid.setBalanceMoney(2221.00);
		oid.setBankCode("BADA0161");
		oid.setDomc("D");
		oid.setLinker("哈哈哈");
		oid.setPayPlatform("了解的垃圾垃圾");
		oid.setRemark("备注备注");
		oid.setTel("0571-87452145");
		oid.setTicketLimitDate(new Date());
		//abeOrder.setOrderInfo(oid);
		
		//abeOrder.setAbePsgListJson("ssssssssssssss");
		
		this.abeOrderDAO.save(abeOrder);
	}
	
	public ABEDeletePnrDTO testDeletePnr(String pnr){
		ABEDeletePnrCommand command = new ABEDeletePnrCommand();
		command.setPnr(pnr);
		return this.ygFlightService.deletePnr(command);
	}
	
	
	public ABEXmlRtPnrDTO xmlRtPnr(String pnr){
		return ygFlightService.xmlRtPnr(pnr);
	}
	
}


