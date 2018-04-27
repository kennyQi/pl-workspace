package hsl.app.service.spi.hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import hg.common.util.BeanMapperUtils;
import hsl.app.component.config.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.api.base.ApiResponse;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.hotel.CustomerLocalService;
import hsl.app.service.local.hotel.HslHotelOrderLocalService;
import hsl.app.service.local.hotel.OrderCancelLocationService;
import hsl.domain.model.hotel.order.Customer;
import hsl.domain.model.hotel.order.HotelOrder;
import hsl.domain.model.hotel.order.OrderCancel;
import hsl.pojo.command.hotel.CheckCreditCardNoCommand;
import hsl.pojo.command.hotel.JDOrderCancelCommand;
import hsl.pojo.command.hotel.JDOrderCreateCommand;
import hsl.pojo.dto.hotel.order.CheckCreditCardNoDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDetailDTO;
import hsl.pojo.dto.hotel.order.OrderCancelResultDTO;
import hsl.pojo.dto.hotel.order.OrderCreateResultDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.qo.hotel.HotelOrderDetailQO;
import hsl.pojo.qo.hotel.HotelOrderQO;
import hsl.spi.inter.hotel.HslHotelOrderService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.command.jd.JDOrderCancelApiCommand;
import slfx.api.v1.request.command.jd.JDOrderCreateApiCommand;
import slfx.api.v1.request.command.jd.JDValidateCreditCardApiCommand;
import slfx.api.v1.request.qo.jd.JDOrderApiQO;
import slfx.api.v1.response.jd.JDOrderCancelResponse;
import slfx.api.v1.response.jd.JDOrderCreateResponse;
import slfx.api.v1.response.jd.JDOrderDetailResponse;
import slfx.api.v1.response.jd.JDValidateCreditCardResponse;
import slfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import slfx.jd.pojo.dto.ylclient.order.CustomerDTO;
import slfx.jd.pojo.dto.ylclient.order.OrderDetailResultDTO;
import slfx.jd.pojo.system.enumConstants.EnumGender;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：酒店订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月6日上午10:15:50
 * @版本：V1.1
 *
 */
@Service
public class HslHotelOrderServiceImpl extends BaseSpiServiceImpl<HotelOrderDTO, HotelOrderQO, HslHotelOrderLocalService> implements HslHotelOrderService{
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private HslHotelOrderLocalService hslHotelOrderLocalService;
	@Autowired
	private CustomerLocalService customerLocalService;
	@Autowired
    private OrderCancelLocationService orderCancelLocationService;
	
	@Override
	protected HslHotelOrderLocalService getService() {
		return this.hslHotelOrderLocalService;
	}

	@Override
	protected Class<HotelOrderDTO> getDTOClass() {
		return HotelOrderDTO.class;
	}

	@Override
	public OrderCreateResultDTO createHotelOrder(JDOrderCreateCommand command) throws HotelException{
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）酒店下单（command）:"+JSON.toJSONString(command));
		OrderCreateResultDTO resultDto=null;
		if(command==null){
			throw new HotelException(HotelException.COMMAND_ERROR,JSON.toJSONString(command));
		}
		//经销商平台id
		String lineDealerId=SysProperties.getInstance().get("clientKey");
		command.setDealerProjcetId("hsl");
		//生成经销商订单号
		String lineDealerOrderId = OrderUtil.createOrderNo(new Date(), this.hslHotelOrderLocalService.getOrderSequence(), 1, 1);
		command.setDealerOrderNO(lineDealerOrderId);
		
		//设置订单确认号
		command.getOrderCreateDTO().setAffiliateConfirmationId("123456789");
		
		JDOrderCreateApiCommand apiCommand=new JDOrderCreateApiCommand();
		apiCommand=BeanMapperUtils.map(command, JDOrderCreateApiCommand.class);
		
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）酒店下单：通知分销下单"+JSON.toJSONString(apiCommand));	
		ApiRequest apiRequest = new ApiRequest("JDOrderCreate",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		JDOrderCreateResponse response = slfxApiClient.send(apiRequest, JDOrderCreateResponse.class);
		
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）酒店下单：分销返回下单结果（response）"+JSON.toJSONString(response));	
		
		if(response!=null&&StringUtils.isNotBlank(response.getResult())){
			if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){
				HgLogger.getInstance().info("renfeng", "HslHotelOrderLocalService->createHotelOrder（直销）酒店下单：分销下单失败！");
				//创建失败。返回异常信息
				throw new HotelException(HotelException.CREATE_ORDER_FAIL, response.getMessage());
			}else{
				HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）酒店下单：分销下单成功！直销开始持久化订单。。。。。。。。。");
				
				HotelOrder hotelOrder=new HotelOrder();
				resultDto=BeanMapperUtils.getMapper().map(response.getOrderCreateResultDTO(),OrderCreateResultDTO.class);
				hotelOrder.createOrder(command, resultDto);
				
				//订单编号
				long orderId=response.getOrderCreateResultDTO().getOrderId();
				hotelOrder.setOrderNo(orderId+"");
				
				
				//保存订单
				this.hslHotelOrderLocalService.save(hotelOrder);
				
				//持久化酒店客人
				List<CreateOrderRoomDTO>  orderRoomsDtoList =command.getOrderCreateDTO().getOrderRoomsDTO();
				
				if(orderRoomsDtoList!=null&&orderRoomsDtoList.size()>0){
					for(CreateOrderRoomDTO roomDto:orderRoomsDtoList){
						List<CustomerDTO> customerDtos=roomDto.getCustomers();
						if(customerDtos!=null&&customerDtos.size()>0){
							List<Customer> customers = new ArrayList<Customer>();
							//List<Customer> customers=BeanMapperUtils.getMapper().mapAsList(customerDtos,Customer.class);
							for(CustomerDTO customerDTO : customerDtos){
								Customer customer = new Customer();
								
								EnumGender gender=customerDTO.getGender();
								customerDTO.setGender(null);
								customer = JSON.parseObject(JSON.toJSONString(customerDTO),Customer.class);
								if(gender == EnumGender.Female){
									customer.setGender(0);
								}else if(customerDTO.getGender() == EnumGender.Maile){
									customer.setGender(1);
								}else{
									customer.setGender(2);
								}
								customer.setId(UUID.randomUUID().toString());
								customers.add(customer);
							}
							for(Customer customer:customers){
								customer.setHotelOrder(hotelOrder);
							}
							
							this.customerLocalService.saveCustomerList(customers);
							
						}
					}
					
				}
				
				//根绝订单id查询订单明细,设置订单状态
				HotelOrderDetailQO qo=new HotelOrderDetailQO();
				qo.setOrderId(orderId);
				HotelOrderDetailDTO dto=this.queryHotelOrderDetail(qo);
				
				if(dto!=null){
					hotelOrder.setStatus(dto.getStatus());
					hotelOrder.setShowStatus(dto.getShowStatus());
				}
			    //更新订单状态
				this.hslHotelOrderLocalService.update(hotelOrder);
			}
			
			
		}
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）酒店下单：直销创建订单成功！");
		//创建下一个序列号
		this.hslHotelOrderLocalService.setNextOrderSequence();
		
		return resultDto;
		
	}

	@Override
	public OrderCancelResultDTO cancelHotelOrder(JDOrderCancelCommand command) throws HotelException{
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->cancelHotelOrder（直销）取消酒店订单（command）:"+JSON.toJSONString(command));
		if(command==null){
			throw new HotelException(HotelException.COMMAND_ERROR,JSON.toJSONString(command));
		}
		OrderCancelResultDTO resultDto=new OrderCancelResultDTO();
		JDOrderCancelApiCommand apiCommand=BeanMapperUtils.map(command, JDOrderCancelApiCommand.class);
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->createHotelOrder（直销）取消订单：通知分销取消订单"+JSON.toJSONString(apiCommand));	
		ApiRequest apiRequest = new ApiRequest("JDOrderCancel",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		JDOrderCancelResponse response = slfxApiClient.send(apiRequest, JDOrderCancelResponse.class);
		
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->cancelHotelOrder（直销）取消订单：分销返回取消结果（response）"+JSON.toJSONString(response));	
		
		if(response!=null&&StringUtils.isNotBlank(response.getResult())){
			if(response.getResult().equals(ApiResponse.RESULT_CODE_OK)){
				// 更新订单状态
				long orderId=command.getOrdercancel().getOrderId();
				
				//查询订单状态
				HotelOrderDetailQO qo=new HotelOrderDetailQO();
				qo.setOrderId(orderId);
				HotelOrderDetailDTO orderDto=this.queryHotelOrderDetail(qo);
				
				//更新本地订单状态
				HotelOrderQO orderQo=new HotelOrderQO();
				orderQo.setOrderId(orderId);
				HotelOrderDTO o=this.queryUnique(orderQo);
				HotelOrder order=BeanMapperUtils.map(o, HotelOrder.class);
				order.setStatus(orderDto.getStatus());
				order.setShowStatus(orderDto.getShowStatus());
				order.setIsCancelable(true);
				this.hslHotelOrderLocalService.update(order);
				
				//保存取消原因
				OrderCancel reason=new OrderCancel();
				reason.setId(UUID.randomUUID().toString());
				reason.setReason(response.getReason());
				reason.setCancelDate(new Date());
				reason.setHotelOrder(order);
				this.orderCancelLocationService.save(reason);
				
				
				resultDto.setSuccesss(true);
			}else{
				resultDto.setSuccesss(false);
			}
			
			
		}
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->cancelHotelOrder（直销）取消订单：resultDto"+JSON.toJSONString(resultDto));	
		return resultDto;
	}

	

	@Override
	public HotelOrderDetailDTO queryHotelOrderDetail(HotelOrderDetailQO qo)
			throws HotelException {
		if(qo==null){
			throw new HotelException(HotelException.QO_ERROR,JSON.toJSONString(qo));
		}
		HotelOrderDetailDTO dto=null;
		JDOrderApiQO apiQo=new JDOrderApiQO();
		apiQo.setOrderId(qo.getOrderId());
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->queryHotelOrderDetail（直销）酒店下单成功后，查询订单明细："+JSON.toJSONString(apiQo));	
		JDOrderDetailResponse response=null;
		ApiRequest apiRequest = new ApiRequest("JDOrderDetail",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiQo);
	    response = slfxApiClient.send(apiRequest, JDOrderDetailResponse.class);
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->queryHotelOrderDetail（直销）酒店明细查询分销返回（response）"+JSON.toJSONString(response));	
		if(response!=null&&ApiResponse.RESULT_CODE_OK.equals(response.getResult())){
			
			OrderDetailResultDTO resultDto=response.getOrderDetailResultDTO();
			dto=BeanMapperUtils.map(resultDto,HotelOrderDetailDTO.class);
		}else{
			HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->queryHotelOrderDetail（直销）酒店订单明细查询失败！");
		}
		return dto;
	}
	
	@Override
	public CheckCreditCardNoDTO checkCreditCardNo(CheckCreditCardNoCommand command) throws HotelException {
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->checkCreditCardNo（直销）校验信用卡是否有效（command）:"+JSON.toJSONString(command));
		if(command==null){
			throw new HotelException(HotelException.COMMAND_ERROR,JSON.toJSONString(command));
		}
		CheckCreditCardNoDTO dto=new CheckCreditCardNoDTO();
		JDValidateCreditCardApiCommand apiCommand =BeanMapperUtils.map(command, JDValidateCreditCardApiCommand.class);
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->checkCreditCardNo（直销）（直销）校验信用卡是否有效："+JSON.toJSONString(apiCommand));
		ApiRequest apiRequest = new ApiRequest("JDValidateCreditCardNo",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		JDValidateCreditCardResponse response=slfxApiClient.send(apiRequest, JDValidateCreditCardResponse.class);
		HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->checkCreditCardNo（直销）（直销）校验信用卡是否有效分销返回（response）"+JSON.toJSONString(response));	
		if(response!=null&&ApiResponse.RESULT_CODE_OK.equals(response.getResult())){
			
			dto=BeanMapperUtils.map(response.getResultDto(),CheckCreditCardNoDTO.class);
		}else{
			HgLogger.getInstance().info("renfeng", "HslHotelOrderServiceImpl->checkCreditCardNo（直销）（直销）校验信用卡是否有效失败！");
		}
		return dto;
	}
	
}
