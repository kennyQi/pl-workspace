package plfx.jd.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.app.common.util.DESEncryptUtil;
import plfx.jd.app.common.util.EntityConvertUtils;
import plfx.jd.app.dao.HotelOrderDAO;
import plfx.jd.app.dao.OrderCancelDAO;
import plfx.jd.domain.model.order.HotelOrder;
import plfx.jd.domain.model.order.Invoice;
import plfx.jd.domain.model.order.OrderCancel;
import plfx.jd.pojo.command.plfx.order.OrderCancelAdminCommand;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCreateCommand;
import plfx.jd.pojo.dto.plfx.order.HotelOrderDTO;
import plfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;
import plfx.jd.pojo.qo.HotelOrderQO;
import plfx.jd.pojo.system.HotelOrderConstants;
import plfx.yl.ylclient.yl.command.CheckCreditCardNoCommand;
import plfx.yl.ylclient.yl.command.OrderCancelCommand;
import plfx.yl.ylclient.yl.command.OrderCreateCommand;
import plfx.yl.ylclient.yl.dto.CheckCreditCardNoDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCancelDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCreateDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderDetailDTO;
import plfx.yl.ylclient.yl.inter.YLHotelService;
import plfx.yl.ylclient.yl.qo.OrderQO;
import plfx.yl.ylclient.yl.util.HttpsUtil;
import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import elong.BaseNightlyRate;
import elong.CreateOrderRoom;

/**
 * 
 *@类功能说明：线路订单LOCALSERVICE(操作数据库)实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2015年01月27日下午3:22:46
 *
 */
@Service
@Transactional
public class HotelOrderLocalService extends BaseServiceImpl<HotelOrder, HotelOrderQO, HotelOrderDAO>{

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	private HotelOrderDAO hotelOrderDAO;
	@Autowired
	private CustomerLocalService customerLocalService;
	@Autowired
	private OrderCancelDAO orderCancelDAO;
	@Autowired
	private YLHotelService ylHotelService;
	

	@Override
	protected HotelOrderDAO getDao() {
		return hotelOrderDAO;
	}
	
	public OrderCreateResultDTO createHotelOrder(JDOrderCreateCommand command){
		OrderCreateCommand orderCreateCommand = null;
		List<CreateOrderRoom> rooms = new ArrayList<CreateOrderRoom>();
		List<BaseNightlyRate> rates = new ArrayList<BaseNightlyRate>();
		try{
			orderCreateCommand =  JSON.parseObject(JSON.toJSONString(command.getOrderCreateDTO(),SerializerFeature.DisableCircularReferenceDetect),OrderCreateCommand.class);
			//类型转换异常先用手动转换
			HgLogger.getInstance().info("yaosanfeng", "HotelOrderLocalService->createHotelOrder->orderCreateCommand" + JSON.toJSONString(orderCreateCommand));
			for(CreateOrderRoomDTO room:command.getOrderCreateDTO().getOrderRoomsDTO()){
				rooms.add(JSON.parseObject(JSON.toJSONString(room,SerializerFeature.DisableCircularReferenceDetect),CreateOrderRoom.class));
			}
			orderCreateCommand.setOrderRooms(rooms);
//			for(BaseNightlyRateDTO rate:command.getOrderCreateDTO().getNightlyRatesDTO()){
//				rates.add(JSON.parseObject(JSON.toJSONString(rate,SerializerFeature.DisableCircularReferenceDetect),BaseNightlyRate.class));
//			}
//			orderCreateCommand.setNightlyRates(rates);
		}catch(Exception e){
			e.printStackTrace();
		}
		OrderCreateResultDTO dto = null;
		//信用卡号加密
		if(orderCreateCommand.getCreditCard()!=null&&StringUtils.isNotBlank(orderCreateCommand.getCreditCard().getIdNo())){
			String cardNo=orderCreateCommand.getCreditCard().getNumber();
			 cardNo = encryptCreditCard(cardNo,  HttpsUtil.getAppKey());
			 orderCreateCommand.getCreditCard().setIdNo(cardNo);
		}
		
		HotelOrderCreateDTO result = ylHotelService.createOrder(orderCreateCommand);
		if(result != null){
			String a = JSON.toJSONString(result);
			dto = JSON.parseObject(a,OrderCreateResultDTO.class);
//			HotelOrder order = new HotelOrder();
//			order.createOrder(command,dto);
//			order = save(order);
//			customerLocalService.batchSaveCustomer(command.getOrderCreateDTO().getOrderRoomsDTO(),order);
//			customerLocalService.saveContact(command.getOrderCreateDTO().getContact(),order);
		}
		return dto;
	}

	public HotelOrderCancelDTO cancelHotelOrder(OrderCancelCommand command) {
		HotelOrderCancelDTO result = ylHotelService.cancelOrder(command);
		return result;
	}
	
	public HotelOrderDetailDTO queryHotelOrder(OrderQO qo){
		HotelOrderDetailDTO result = ylHotelService.queryOrderDetail(qo);
		return result;
	}
	
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = hotelOrderDAO.queryPagination(pagination);
		List<HotelOrder> orders = (List<HotelOrder>) pagination2.getList();
		for (HotelOrder hotelOrder : orders) {
			Hibernate.initialize(hotelOrder.getCustomers());
			Hibernate.initialize(hotelOrder.getInvoice());
			Hibernate.initialize(hotelOrder.getDealer());
			Hibernate.initialize(hotelOrder.getSupplier());
		}
		List<HotelOrderDTO> list = new ArrayList<HotelOrderDTO>();
		try{
			list = EntityConvertUtils.convertEntityToDtoList(orders,HotelOrderDTO.class);
		}catch(Exception e){
			//枚举值用上面的方式无法转换
			for (HotelOrder hotelOrder : orders) {
				HotelOrderDTO dto = JSON.parseObject(JSON.toJSONString(hotelOrder),HotelOrderDTO.class);
				list.add(dto);
			}
		}
		pagination2.setList(list);
		return pagination2;
	}
	@Override
	public HotelOrder queryUnique(HotelOrderQO qo) {
		HotelOrder order = hotelOrderDAO.queryUnique(qo);
		if(order != null){
			Hibernate.initialize(order.getCustomers());
			Hibernate.initialize(order.getInvoice());
			Hibernate.initialize(order.getDealer());
			Hibernate.initialize(order.getSupplier());
			Invoice invoice = order.getInvoice();
			if(invoice != null){
				Hibernate.initialize(invoice.getRecipient());
			}
		}
		return order;
	}

	public Boolean cancelOrder(
			OrderCancelAdminCommand command) {
		OrderCancelCommand command1 = new OrderCancelCommand();
		command1.setOrderId(Long.valueOf(command.getSupplierOrderNo()));
		//测试接口无法测试取消订单等正式接口再调试
		HotelOrderCancelDTO result = ylHotelService.cancelOrder(command1);
		if(result.isSuccesss()){
			HotelOrder order = get(command.getOrderId());
			order.setStatus(HotelOrderConstants.ORDER_CANCEL);
			update(order);
			OrderCancel orderCancel = new OrderCancel();
			orderCancel.createOrderCancel(command);
			orderCancelDAO.save(orderCancel);
		}
		return result.isSuccesss();
	}
	
	 public CheckCreditCardNoDTO validateCreditCard(CheckCreditCardNoCommand command){
		 String cardNo=command.getCreditCardNo();
		 cardNo = encryptCreditCard(cardNo,  HttpsUtil.getAppKey());
		 command.setCreditCardNo(cardNo);
    	 return ylHotelService.validateCreditCard(command);
	 }
	 
	 /**
	  * 1、如果是担保订单，则Creditcard下的所有字段都必须赋有效值
		2、测试环境信用卡卡号可填4033910000000000， 1234456789098711
		三位数字，主要指信用卡
		3、如是身份证号码，请填写有效身份证信息，艺龙系统有对应身份证号的校验
		4、VerifyCode（CVV）需要先调用信用卡验证接口确认该信用卡是否需要提供CVV；如果卡不需要CVV而提供了CVV会拒绝成单；如果卡需要CVV没有提供CVV也会拒绝成单5、卡号加密方式（必须）
		使用DES对称加密法中cbc模式（key值和iv值一致）
		加密内容=当前时间戳+#+信用卡号
		密钥为appkey的后8位 例如：
		$CreditCardNO=  des_encrypt(time(). '#4033910000000000', substr($appkey,-8));
		测试加密方法 Xcrypt::encrypt( '12345#6789012345', '12345678') == 8e519cf90bf4240f7f653af ff4f6d658f5e402a4ff2581a7
	  * @方法功能说明：
	  * @修改者名字：renfeng
	  * @修改时间：2015年7月15日下午1:56:58
	  * @修改内容：
	  * @参数：@param creditCardNo：要加密的数据
	  * @参数：@return
	  * @return:String
	  * @throws
	  */
	private String encryptCreditCard(String creditCardNo,String appKey){
		//当前时间戳
		long time=System.currentTimeMillis()/1000;
		//卡号
		creditCardNo=time+"#"+creditCardNo;
		
		try {
			
			appKey=appKey.substring(appKey.length()-8,appKey.length());
			
			//des 对称方式cbc加密
			creditCardNo= DESEncryptUtil.encrypt(creditCardNo, appKey);
			
			//System.out.println("加密时间戳:" +time); 
			//System.out.println("加密后:" +creditCardNo); 
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return creditCardNo;
	}
	
	
	/**
	 * @方法功能说明：解密
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月15日下午5:20:00
	 * @修改内容：
	 * @参数：@param creditCardNo：加密后的数据
	 * @参数：@param appKey
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("restriction")
	public static String decryptCreditCard(String creditCardNo,String appKey){
		
	  try {
		byte[]  key = appKey.getBytes("utf-8");
		byte[]  iv = appKey.substring(appKey.length()-8,appKey.length()).getBytes("utf-8");
		BASE64Decoder dec = new BASE64Decoder(); 
		 String aaa= java.net.URLDecoder.decode(java.net.URLEncoder.encode(creditCardNo,"utf-8"),"utf-8");
         byte[] resultArr;
		
			resultArr = dec.decodeBuffer(aaa);
			creditCardNo=new String(DESEncryptUtil.CBCDecrypt(resultArr, key, iv));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("解密后:" +creditCardNo); 
		return creditCardNo;
	}
	public static void main(String[] args) {
		Date s=new Date();
		s.setTime(1437036122);
		//System.out.println(s);
		
	}
}
