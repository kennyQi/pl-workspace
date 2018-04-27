package hsl.app.service.local.line;
import com.alibaba.fastjson.JSON;

import hg.common.component.BaseServiceImpl;
import hg.common.util.*;
import hg.log.util.HgLogger;
import hsl.api.base.ApiResponse;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.task.LineOrderSendSmsTask;
import hsl.app.component.task.SysProperties;
import hsl.app.dao.UserDao;
import hsl.app.dao.commonContact.CommonContactDao;
import hsl.app.dao.line.*;
import hsl.domain.model.commonContact.CommonContact;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.user.User;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.domain.model.user.traveler.TravelerBaseInfo;
import hsl.domain.model.xl.DateSalePrice;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.order.LineOrder;
import hsl.domain.model.xl.order.LineOrderStatus;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.domain.model.xl.order.LineSnapshot;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.line.*;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.coupon.ConsumeOrderSnapshotDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.line.order.*;
import hsl.pojo.exception.AccountException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.LineException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.line.DateSalePriceQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.line.LineSnapshotQO;
import hsl.pojo.util.HSLConstants.LineOrderPayType;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.xl.request.command.XLCancelLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLModifyLineOrderTravelerApiCommand;
import plfx.api.client.api.v1.xl.response.XLCancelLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLCreateLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLModifyLineOrderTravelerResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.dto.line.LineSnapshotDTO;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import plfx.xl.pojo.dto.order.XLOrderStatusDTO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @类功能说明：线路订单本地服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2015年2月26日
 */
@Service
@Transactional
public class LineOrderLocalService extends BaseServiceImpl<LineOrder, HslLineOrderQO, LineOrderDAO>{
	@Autowired
	private HslLineService hslLineService;
	@Autowired
	private LineOrderDAO lineOrderDAO;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private HslLineOrderService lineOrderService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;

	/**
	 * 线路快照dao
	 */
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	@Autowired
	private CouponService couponService;
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private LineOrderTravelerDAO lineOrderTravelerDAO;
	@Autowired
	private DateSalePriceDAO dateSalePriceDAO;
	@Autowired
	private LineOrderSendSmsTask sendSmsTask;
	@Autowired
	private RabbitTemplate template;
	@Resource
	private SMSUtils smsUtils;
	@Autowired
	private UserDao userDao;
	@Autowired
	private LineDAO lineDAO;
	@Autowired
	private CommonContactDao commonContactDao;

	protected LineOrderDAO getDao() {
		return this.lineOrderDAO;
	}

	/**
	 * @方法功能说明：创建线路订单
	 * @修改者名字：renfeng
	 * @修改时间：2015年5月14日下午2:41:26
	 * @修改内容：通知分销下单成功后，返回订单DTO本地创建线路订单。分别自己生产订单ID，采用经销商订单号（dealerOrderCode）通信。游客ID相同。
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws LineException
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO createLineOrder(HslCreateLineOrderCommand command) throws LineException {
		LineOrderDTO lineOrderDto =null;
		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：" + JSON.toJSONString(command));
		//生成经销商订单号
		String lineDealerOrderId = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, command.getSource());
		//查询线路快照
		LineSnapshotQO lineSnapshotQO = new LineSnapshotQO();
		lineSnapshotQO.setLineID(command.getLineID());
		lineSnapshotQO.setIsNew(true);
		LineSnapshot lineSnapshot = lineSnapshotDAO.queryUnique(lineSnapshotQO);
		if(lineSnapshot.getLine().getForSale()==Line.NOT_SALE){
			throw new LineException(LineException.CREATE_ORDER_FAIL, "下单失败，该线路已下线！");
		}
		//向分销发起创建订单的请求，创建本地订单号
		XLCreateLineOrderApiCommand apiCommand = BeanMapperUtils.map(command, XLCreateLineOrderApiCommand.class);

		String lineDealerId=SysProperties.getInstance().get("clientKey");
		apiCommand.setLineDealerID(lineDealerId);
		//补充一些没有映射到的数据
		apiCommand.getBaseInfo().setDealerOrderNo(lineDealerOrderId);
		LineSnapshotDTO lineSnapshotDTO = new LineSnapshotDTO();
		lineSnapshotDTO.setId(lineSnapshot.getId());
		apiCommand.setLineSnapshot(lineSnapshotDTO);

		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：通知分销下单" + JSON.toJSONString(apiCommand));
		apiCommand.setClientUserKey(ClientKeyUtil.FROM_USER_KEY);
		ApiRequest apiRequest = new ApiRequest("XLCreateLineOrder",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		XLCreateLineOrderResponse response = slfxApiClient.send(apiRequest, XLCreateLineOrderResponse.class);

		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：分销下单返回结果"+JSON.toJSONString(response));

		if(response!=null&&StringUtils.isNotBlank(response.getResult())){
			if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){

				HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：分销下单失败！！！！");
				//创建失败。返回异常信息
				throw new LineException(LineException.CREATE_ORDER_FAIL, response.getMessage());
			}else{

				HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：分销下单成功！开始本地持久化订单。。。。。。。。。");
				//分销返回请求成功，创建本地订单（暂时没有数据）

				//补充command信息:(创建日期；)
				command.getBaseInfo().setCreateDate(new Date());
				command.getBaseInfo().setDealerOrderNo(lineDealerOrderId);

				//根据分销返回数据，设置供应商价格信息
				command.getBaseInfo().setSupplierAdultUnitPrice(response.getLineOrderDTO().getBaseInfo().getSupplierAdultUnitPrice());
				command.getBaseInfo().setSupplierUnitChildPrice(response.getLineOrderDTO().getBaseInfo().getSupplierUnitChildPrice());
				command.getBaseInfo().setSupplierPrice(response.getLineOrderDTO().getBaseInfo().getSupplierPrice());

				//根据分销返回信息，设置订单支付订金
				double bargainMoney=response.getLineOrderDTO().getBaseInfo().getBargainMoney();
				DecimalFormat df=new DecimalFormat("#.00");
				bargainMoney=Double.parseDouble(df.format(bargainMoney));
				command.getBaseInfo().setBargainMoney(bargainMoney);

				//持久化线路订单
				LineOrder lineOrder = new LineOrder();
				lineOrder.create(command);
				lineOrder.setLineSnapshot(lineSnapshot);

				lineOrderDAO.save(lineOrder);

				//持久化游客
				List<LineOrderTraveler> travelers = BeanMapperUtils.getMapper().mapAsList(response.getLineOrderDTO().getTravelers(), LineOrderTraveler.class);
				for(LineOrderTraveler traveler : travelers){
					//用分销返回的ID
					traveler.setLineOrder(lineOrder);
					traveler.setLineOrderStatus(lineOrder.getStatus());

				}
				this.lineOrderTravelerDAO.saveList(travelers);

				//将分销线路订单DTO转换为本地DTO
				lineOrderDto=BeanMapperUtils.getMapper().map(response.getLineOrderDTO(),LineOrderDTO.class);
				lineOrderDto.setTravelerList(command.getTravelerList());

				lineOrderDto.setId(lineOrder.getId());
				
				/*
				 * 更新本地团期剩余人数（分销下单后修改团期剩余人数通知直销，本地不用此操作）
				
				HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：（更新本地团期剩余人数）"+JSON.toJSONString(command));
				
				List<DateSalePrice> dateSalePriceList=lineOrder.getLineSnapshot().getLine().getDateSalePriceList();
				if(dateSalePriceList!=null){
					for(DateSalePrice price:dateSalePriceList){
						if(!price.getSaleDate().before(lineOrder.getBaseInfo().getTravelDate())){
							
							price.setNumber(price.getNumber()-travelers.size());
							this.getDao().update(price);
							
						}
					}
				}*/


			}
		}
		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->createLineOrder（直销）创建线路订单：返回结果"+JSON.toJSONString(lineOrderDto));
		return lineOrderDto;
	}

	/**
	 * @方法功能说明：修改线路订单状态：修改本地线路订单游客状态，并通知分销更新状态
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月3日下午4:26:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateOrderStatus(UpdateLineOrderStatusCommand command)throws LineException{
		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单状态更新  开始。。。。。。。。。。。" + JSON.toJSONString(command));

		if(command==null||command.getOrderStatus()==null){
			HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单状态更新:command参数为空");
			return false;
		}

		HslLineOrderQO qo = new HslLineOrderQO();
		qo.setDealerOrderNo(command.getDealerOrderCode());
		LineOrder lineOrder = getDao().queryUnique(qo);

		if(lineOrder==null){
			HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单状态更新：（订单不存在）"+JSON.toJSONString(command));
			return false;
		}


		List<LineOrderTraveler> travelers=null;
		if(command.getTravelerList()!=null&&command.getTravelerList().size()>0){
			travelers=BeanMapperUtils.getMapper().mapAsList(command.getTravelerList(), LineOrderTraveler.class);
			HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单状态更新：（针对订单中部分游客）"+JSON.toJSONString(command));
		}else{

			travelers=	new ArrayList<LineOrderTraveler>(lineOrder.getTravelers());
			HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单状态更新：（针对订单中所有游客）"+JSON.toJSONString(command));
		}

		//更新本地线路游客订单状态
		if(travelers==null){
			HgLogger.getInstance().info("renfeng", "LineOrderLocalService->updateOrderStatus线路订单更新：游客为空"+JSON.toJSONString(command));
			return false;
		}else{
			LineOrderStatus os = new LineOrderStatus();
			os.changeOrderStatus(command.getOrderStatus(), command.getPayStatus());
			getDao().clear();
			for(LineOrderTraveler traveler:travelers){

				int payStatus=command.getPayStatus()==null?traveler.getLineOrderStatus().getPayStatus():command.getPayStatus();

				//取消订单操作时，游客支付状态若为 "待支付订金"，支付状态不变，还是"待支付订金"；否则，支付状态修改为"等待退款"
				if(command.getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL)){

					if(payStatus==Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)){

						payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY);
					}else{
						payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_REFUND);
					}
				}
				traveler.getLineOrderStatus().changeOrderStatus(command.getOrderStatus(), payStatus);
				traveler.setLineOrder(lineOrder);

				this.getDao().update(traveler);

			}

		}


		//通知分销修改订单状态
		XLModifyLineOrderTravelerApiCommand apiCommand=new XLModifyLineOrderTravelerApiCommand();
		apiCommand.setDealerOrderNo(command.getDealerOrderCode());

		//由于分销和直销的线路定单状态命名不同,设置分销线路订单状态
		Set<LineOrderTravelerDTO> travelerSet=BeanMapperUtils.getMapper().mapAsSet(travelers, LineOrderTravelerDTO.class);

		XLOrderStatusDTO xlOrderStatus=new XLOrderStatusDTO();
		xlOrderStatus.setStatus(command.getOrderStatus());
		xlOrderStatus.setPayStatus(command.getPayStatus());

		List<LineOrderTravelerDTO> orderTravelerDTOs =new ArrayList<LineOrderTravelerDTO>();
		for(LineOrderTravelerDTO traveler:travelerSet){

			int payStatus=xlOrderStatus.getPayStatus()==null?traveler.getXlOrderStatus().getPayStatus():xlOrderStatus.getPayStatus();

			//取消订单操作时，游客支付状态若为 "待支付订金"，支付状态不变，还是"待支付订金"；否则，支付状态修改为"等待退款"
			if(command.getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL)){

				if(payStatus==Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)){

					payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY);
				}else{
					payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_REFUND);
				}
			}

			xlOrderStatus.setPayStatus(payStatus);
			traveler.setXlOrderStatus(xlOrderStatus);
			traveler.setLineOrder(null);
			orderTravelerDTOs.add(traveler);
		}
		apiCommand.setTravelers(orderTravelerDTOs);

		HgLogger.getInstance().info("renfeng", "LineOrderLocalService->通知分销修改线路订单游客订单状态：" + JSON.toJSONString(apiCommand));
		ApiRequest apiRequest =null;
		if(command.getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL)){
			List<LineOrderTravelerDTO> travels=apiCommand.getTravelers();
			XLCancelLineOrderApiCommand apiCommands=new XLCancelLineOrderApiCommand();
			StringBuilder lineOrderTravelers=new StringBuilder();
			for(LineOrderTravelerDTO travel:travels){
				if(StringUtils.isBlank(lineOrderTravelers.toString())){
					lineOrderTravelers.append(travel.getId());
				}else{
					lineOrderTravelers.append(","+travel.getId());
				}
			}
			apiCommands.setLineOrderID(command.getDealerOrderCode());
			apiCommands.setLineOrderTravelers(lineOrderTravelers.toString());
			apiRequest = new ApiRequest("XLCancleLineOrder",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommands);
			XLCancelLineOrderResponse response = slfxApiClient.send(apiRequest, XLCancelLineOrderResponse.class);
			if(response!=null&&StringUtils.isNotBlank(response.getResult())){
				HgLogger.getInstance().info("renfeng", "LineOrderLocalService->通知分销取消线路订单游客状态：返回结果"+response.getLineOrderID());
				if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){
					//创建失败。返回异常信息
					throw new LineException(LineException.UPDATE_ORDER_STATUS, response.getMessage());
				}
			}
		}else{
			XLModifyLineOrderTravelerResponse response=null;
			apiRequest = new ApiRequest("XLModifyLineOrderTraveler",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
			response = slfxApiClient.send(apiRequest, XLModifyLineOrderTravelerResponse.class);
			if(response!=null&&StringUtils.isNotBlank(response.getResult())){
				HgLogger.getInstance().info("renfeng", "LineOrderLocalService->通知分销修改线路订单游客状态：返回结果"+JSON.toJSONString(response.getLineOrderDTO()));
				if(ApiResponse.RESULT_CODE_FAIL.equals(response.getResult())){
					//创建失败。返回异常信息
					throw new LineException(LineException.UPDATE_ORDER_STATUS, response.getMessage());
				}
			}
		}


		return true;
	}

	public boolean cancelLineOrder(){



		return true;
	}

	/**
	 * @功能说明：从redis拿取序列号，也许该加个同步锁
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月28日
	 */
	private int getOrderSequence() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value = jedis.get("zzpl_line_sequence");
			String date = jedis.get("zzpl_line_sequence_date");
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(this.getDateString("yyyyMMdd"))) {
				value = "0";
			}
			int v=Integer.parseInt(value);
			if (v >= 9999) {
				v = 0;
			}
			v++;
			jedis.set("zzpl_line_sequence", String.valueOf(v));
			jedis.set("zzpl_line_sequence",String.valueOf(this.getDateString("yyyyMMdd")));
			return v;
		}catch (Exception e){
			HgLogger.getInstance().error("chenxy","创建Line订单流水号出错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			return 0;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
	/**
	 * @方法功能说明：线路订单支付成功（复制的是LineController.asyncPaymentRequest()方法）
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-14下午4:02:20
	 * @修改内容：
	 * @参数：@param dealerOrderNo
	 * @参数：@param isBalance			是否是尾款
	 * @return:boolean					是否执行
	 * @throws
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean lineOrderPaySuccess(String dealerOrderNo, boolean isBalance) {
		HgLogger.getInstance().info("renfeng", "lineOrderPaySuccess-->截取订单号：" + dealerOrderNo);
		try {
			String orderNo = dealerOrderNo;/* 获取商城订单编号*/
			String[] orderNos = orderNo.split("_");
			if (orderNos.length > 1) {
				orderNo = orderNos[0];
				HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->截取订单号：" + orderNo);
			}

			// 检查订单号
			if (StringUtils.isBlank(orderNo)) return false;

			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->异步通知方法订单号：" + orderNo);

			// 查询订单
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(orderNo);
			LineOrderDTO lineOrderdto = lineOrderService.queryUnique(hslLineOrderQO);

			// 订单状态检查
			if (lineOrderdto == null) {
				HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->订单不存在，该订单号：" + orderNo);
				return false;
			}

			// 检查是否需要支付预订金
			else if (!isBalance && !lineOrderdto.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)) {
				HgLogger.getInstance().info("zhurz", "lineOrderPaySuccess-->订单已支付预定金，该订单号：" + orderNo);
				return false;
			}
			// 检查是否需要支付尾款
			else if (isBalance && !lineOrderdto.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)) {
				HgLogger.getInstance().info("zhurz", "lineOrderPaySuccess-->订单已支付尾款，该订单号：" + orderNo);
				return false;
			}

			// 移除提醒短信任务
			sendSmsTask.removeCheckLineOrder(lineOrderdto.getId());

			HgLogger.getInstance().info("zhaows","lineOrderPaySuccess-->查询订单成功"+ lineOrderdto.getId());

			// 计算是否付全款
			int frontmoney = 1;
			LineOrder lineOrder = SpringContextUtil.getApplicationContext().getBean(LineOrderLocalService.class).queryUnique(hslLineOrderQO);
			if (lineOrder.needPayAll()) {
				// 全款支付
				frontmoney = 2;
			}

			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->当前订单状态：" + lineOrderdto.getStatus().getOrderStatus());
			HslLineOrderQO hslLineOrderQOone = new HslLineOrderQO();
			hslLineOrderQOone.setDealerOrderNo(orderNo);
			LineOrderDTO lineOrderdtoone = lineOrderService.queryUnique(hslLineOrderQOone);
			String OrderStatus = "";
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess调用支付平台>>支付订单" + JSON.toJSONString(lineOrderdtoone));
			Set<hsl.pojo.dto.line.order.LineOrderTravelerDTO> travelers = lineOrderdtoone.getTravelers();
			for (hsl.pojo.dto.line.order.LineOrderTravelerDTO travelerss : travelers) {
				if (travelerss.getLineOrderStatus().getOrderStatus() == Integer.parseInt((XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT))) {
					HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess调用支付平台>>支付尾款修改订单号");
					OrderStatus = "1";
					break;
				}
			}
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess支付状态" + OrderStatus);
			//判断订单状态是否是下单成功已锁定位置
			if (OrderStatus.equals("1")) {
				//支付尾款
				frontmoney = 3;
			}

			Integer status = null;
			Integer payStatus = null;
			//判断是支付定金还是支付全款或者是支付尾款
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->判断是支付定金还是支付全款或者是支付尾款：" + frontmoney);
			HslLineQO newHslLineQO = new HslLineQO();
			newHslLineQO.setId(lineOrderdto.getLineSnapshot().getLine().getId());
			LineDTO newLineDTO = hslLineService.queryLine(newHslLineQO);
			HgLogger.getInstance().info("yuqz", "newLineDTO-647"+JSON.toJSONString(newLineDTO));
			if (newLineDTO.getPayInfo().getDownPayment() * 100.0 / 100 == 100.00d) {
				status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
				payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//全款支付
			} else {
				if (frontmoney == 1) {
					status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
					payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX);
				} else if (frontmoney == 2) {
					status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
					payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//全款支付
				} else if (frontmoney == 3) {
					status = Integer.parseInt(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
					payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//支付尾款
				}
			}

			UpdateLineOrderStatusCommand updateLineOrderStatusCommand = new UpdateLineOrderStatusCommand();
			updateLineOrderStatusCommand.setOrderStatus(status);
			updateLineOrderStatusCommand.setPayStatus(payStatus);
			updateLineOrderStatusCommand.setDealerOrderCode(orderNo);
			updateLineOrderStatusCommand.setOrderId(lineOrderdto.getId());
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess>修改订单状态command：" + JSON.toJSONString(updateLineOrderStatusCommand));
			lineOrderService.updateLineOrderStatus(updateLineOrderStatusCommand);
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->修改订单状态成功：" + orderNo);
			// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
			HslCouponQO hslCouponQO = new HslCouponQO();
			hslCouponQO.setOrderId(orderNo);
			// 设置查询条件卡券状态为占用
			// 根据订单号查询一个与订单绑定的卡券快照
			List<CouponDTO> coupondtos = couponService.queryList(hslCouponQO);
			// 标识卡券是否使用
			int couponstatus=0;
			if (coupondtos != null && coupondtos.size() > 0) {
				ConsumeOrderSnapshotDTO consumeOrderSnapshotDTO = new ConsumeOrderSnapshotDTO();
				HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess根据订单号查询一个与订单绑定的卡券快照" + JSON.toJSONString(coupondtos));
				String couponid = "";
				for (CouponDTO coupondto : coupondtos) {
					if (coupondto != null) {
						couponid = coupondto.getId() + ",";
						consumeOrderSnapshotDTO.setPayPrice(coupondto.getConsumeOrder().getPayPrice());
						consumeOrderSnapshotDTO.setOrderId(coupondto.getConsumeOrder().getOrderId());
						if (coupondto.getStatus().getCurrentValue() == CouponStatus.TYPE_ISUSED) {
							couponstatus = 1;
							break;
						}
					}
				}
				String[] couponids = couponid.split(",");
				// 修改卡券的状态
				HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess得到卡券状态 1为已使用" + couponstatus);
				if (couponstatus != 1) {
					BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
					couponCommand.setCouponIds(couponids);
					couponCommand.setOrderId(consumeOrderSnapshotDTO.getOrderId());
					couponCommand.setPayPrice(consumeOrderSnapshotDTO.getPayPrice());
					try {
						couponService.confirmConsumeCoupon(couponCommand);
					} catch (CouponException e) {
						e.printStackTrace();
						HgLogger.getInstance().error("zhaows", "lineOrderPaySuccess>订单状态修改失败：" + e.getMessage() + HgLogger.getStackTrace(e));
					}
				}
			}

			//检查是否使用账户余额，如果使用，就做消费余额操作
			AccountConsumeSnapshotQO accountConsumeSnapshotQO=new AccountConsumeSnapshotQO();
			accountConsumeSnapshotQO.setOrderId(orderNo);//根据订单id查询余额订单快照
			accountConsumeSnapshotQO.setAccountstatus(AccountConsumeSnapshot.STATUS_ZY);
			HgLogger.getInstance().info("renfeng","lineOrderPaySuccess使用余额，查询余额qo"+JSON.toJSONString(accountConsumeSnapshotQO));
			AccountConsumeSnapshotDTO accDTO=accountConsumeSnapshotService.queryUnique(accountConsumeSnapshotQO);
			HgLogger.getInstance().info("renfeng","lineOrderPaySuccess>返回余额快照DTO"+JSON.toJSONString(accDTO));
			//使用账户余额快照
			if(accDTO!=null){
				consumptionAccount(accDTO.getAccount().getUser().getId(),accDTO.getPayPrice(),orderNo,AccountConsumeSnapshot.STATUS_SY,true);
			}
			
			
			
			/*end 修改订单状态*/
			// 发放卡券：订单满
			CreateCouponCommand cmd = new CreateCouponCommand();
			cmd.setSourceDetail("订单满送");
			//按总金额满多少发送
			cmd.setPayPrice(lineOrderdto.getBaseInfo().getSalePrice());
			cmd.setMobile(lineOrderdto.getLineOrderUser().getMobile());
			cmd.setUserId(lineOrderdto.getLineOrderUser().getUserId());
			cmd.setLoginName(lineOrderdto.getLineOrderUser().getLoginName());
			CouponMessage baseAmqpMessage = new CouponMessage();
			baseAmqpMessage.setMessageContent(cmd);
			String issue = SysProperties.getInstance().get("issue_on_full");
			int type = 0;
			if (StringUtils.isBlank(issue))
				type = 2;// 默认为2
			else {
				type = Integer.parseInt(issue);
			}
			baseAmqpMessage.setType(type);
			baseAmqpMessage.setSendDate(new Date());
			baseAmqpMessage.setArgs(null);
			template.convertAndSend("plzx.order", baseAmqpMessage);
			HgLogger.getInstance().info("zhaows", "lineOrderPaySuccess-->卡券发放成功" + baseAmqpMessage);
			// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了			
			try {
				//查询一条订单记录
				HslLineOrderQO hslLineOrderQOs = new HslLineOrderQO();
				hslLineOrderQOs.setDealerOrderNo(orderNo);
				hslLineOrderQOs.setOrderId(lineOrderdto.getId());
				LineOrderDTO lineOrderdtos = lineOrderService.queryUnique(hslLineOrderQOs);
				// 发送短信
				sendSms(lineOrderdtos);
			} catch (Exception e) {
				HgLogger.getInstance().error("zhaows", "lineOrderPaySuccess-->用户付款成功短信发送异常" + HgLogger.getStackTrace(e));
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "lineOrderPaySuccess-->支付失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}

		return true;
	}
	/**
	 * <pre>
	 * 提交订单后需要支付订金的短信内容：即订单状态为“待支付定金”
	 * 【中智票量】亲，您的订单*******已提交成功！请尽快完成支付，以免耽误行程。票量，开启旅行新时代！客服：010—65912283
	 *
	 * 提交订单后无需支付订金或者成功支付定金的短信：即订单状态为“待订位”
	 * 【中智票量】亲，您的订单*******已成功预定，平台将会2小时内联系您进行确认，请耐心等待。客服：010—65912283
	 *
	 * 订单支付尾款后或者100%支付定金后成功锁定位置后短信：即订单状态变为“交易成功”
	 * 【中智票量】亲，您的订单*******已生效。票量祝您旅行愉快，期待与您的下一次相遇！客服：010—65912283
	 *
	 * 【中智票量】已有线路订单生成，请在1小时内处理。
	 * 18655223676
	 * </pre>
	 *
	 * @param lineOrderDTO			线路订单DTO
	 */
	private void sendSms(LineOrderDTO lineOrderDTO) throws Exception {
		String toMobile = lineOrderDTO.getLinkInfo().getLinkMobile();
		String dealerOrderNo = lineOrderDTO.getBaseInfo().getDealerOrderNo();
		String smsSign = SysProperties.getInstance().get("sms_sign", "中智票量");
		String notifyMobile = SysProperties.getInstance().get("line_notify_mobile");
		LineOrderStatusDTO status = lineOrderDTO.getTravelers().iterator().next().getLineOrderStatus();
		if ((Integer.valueOf(XLOrderStatusConstant.SHOP_PAY_SUCCESS).equals(status.getPayStatus())
				|| Integer.valueOf(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX).equals(status.getPayStatus()))
				&& Integer.valueOf(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE).equals(status.getOrderStatus())){
			// 条件：（全款支付成功 或 已收到订金）与 下单成功未订位
			smsUtils.sendSms(toMobile, String.format("【%s】亲，您的订单[%s]已成功预定，平台将会2小时内联系您进行确认，请耐心等待。客服：400—660—6660", smsSign, dealerOrderNo));
			// 通知运营人员
			if (StringUtils.isNotBlank(notifyMobile)) {
				smsUtils.sendSms(notifyMobile, String.format("【%s】已有线路订单生成，请在1小时内处理。", smsSign));
			}
		}
		else if ((Integer.valueOf(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT).equals(status.getOrderStatus())
				|| Integer.valueOf(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS).equals(status.getOrderStatus()))
				&& Integer.valueOf(XLOrderStatusConstant.SHOP_PAY_SUCCESS).equals(status.getPayStatus())) {
			// 条件：（下单成功已锁定位置 或 预订成功）与 全款支付成功
			smsUtils.sendSms(toMobile, String.format("【%s】亲，您的订单[%s]已生效。票量祝您旅行愉快，期待与您的下一次相遇！客服：400—660—6660", smsSign, dealerOrderNo));
		}
	}
	/**
	 * @方法功能说明：查询线路订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-15上午9:58:05
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param offset
	 * @参数：@param fetchSize
	 * @参数：@return
	 * @return:List<LineOrder>
	 * @throws
	 */
	public List<LineOrder> queryList(String userId, Integer offset, Integer fetchSize) {
		return getDao().queryListNoJoinOrderByCreateDateDesc(userId, offset, fetchSize);
	}
	/**
	 * @throws LineException
	 * @方法功能说明：创建线路订单(H5)
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-12下午4:46:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrder
	 * @throws
	 */
	public LineOrderDTO createLineOrders(HslH5CreateLineOrderCommand command) throws LineException {

		if (command.getTravelerIds() == null || command.getTravelerIds().size() == 0)
			throw new ShowMessageException("未指定游玩人");
		if (command.getTravelDate() == null)
			throw new ShowMessageException("未指定游玩日期");
		if (command.getUserId() == null)
			throw new ShowMessageException("登录超时");

		User user = userDao.get(command.getUserId());
		if (user == null)
			throw new ShowMessageException("操作用户异常");

		//检查游玩人
		CommonContactQO commonContactQO=new CommonContactQO();
		commonContactQO.setUserId(command.getUserId());
		List<String> travelerIds=command.getTravelerIds();

		List<CommonContact> travelers =new ArrayList<CommonContact>();
		for(String id:travelerIds){
			commonContactQO.setId(id);
			CommonContact contact=this.commonContactDao.queryUnique(commonContactQO);
			travelers.add(contact);
		}

		if (travelers == null || travelers.size() == 0)
			throw new ShowMessageException("游玩人已被删除");

		Line line = lineDAO.get(command.getLineId());
		if (line == null)
			throw new ShowMessageException("选择的线路已下架或被删除");

		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		dateSalePriceQO.setLineId(command.getLineId());
		dateSalePriceQO.setSaleDate(command.getTravelDate());

		DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
		if (dateSalePrice == null)
			throw new ShowMessageException("选择的日期未开放");

		// 成人人数
		int adultNo = 0;
		// 儿童人数
		int childNo = 0;
		for (CommonContact traveler : travelers) {
			if (TravelerBaseInfo.TYPE_ADULT.equals(traveler.getType()))
				adultNo++;
			else if (TravelerBaseInfo.TYPE_CHILD.equals(traveler.getType()))
				childNo++;
		}

		if (command.getAdultNo() != adultNo || command.getChildNo() != childNo)
			throw new ShowMessageException(String.format("游玩人和预定人数不符，实际游玩人，成人%d人，儿童%d人。", adultNo, childNo));

		double adultPrice = dateSalePrice.getAdultPrice();
		double childPrice = dateSalePrice.getChildPrice();
		double salePrice = MoneyUtil.round(adultNo * adultPrice + childNo * childPrice, 2);

		// 组装web端下单command
		HslCreateLineOrderCommand webCommand = new HslCreateLineOrderCommand();
		{
			webCommand.setLineID(command.getLineId());
			webCommand.setSource(0);
			// 基本信息
			{
				LineOrderBaseInfoDTO baseInfo = new LineOrderBaseInfoDTO();
				webCommand.setBaseInfo(baseInfo);
				baseInfo.setAdultNo(adultNo);
				baseInfo.setChildNo(childNo);
				baseInfo.setAdultUnitPrice(adultPrice);
				baseInfo.setChildUnitPrice(childPrice);
				baseInfo.setSalePrice(salePrice);
				baseInfo.setCreateDate(new Date());
				baseInfo.setTravelDate(command.getTravelDate());
			}
			// 联系人信息
			{
				LineOrderLinkInfoDTO linkInfo = new LineOrderLinkInfoDTO();
				webCommand.setLinkInfo(linkInfo);
				linkInfo.setLinkName(command.getLinkName());
				linkInfo.setLinkMobile(command.getLinkMobile());
				linkInfo.setEmail(command.getLinkEmail());
			}
			// 游客
			{
				List<hsl.pojo.dto.line.order.LineOrderTravelerDTO> travelerDTOs = new ArrayList<hsl.pojo.dto.line.order.LineOrderTravelerDTO>();
				webCommand.setTravelerList(travelerDTOs);
				for (CommonContact traveler : travelers) {
					hsl.pojo.dto.line.order.LineOrderTravelerDTO travelerDTO = new hsl.pojo.dto.line.order.LineOrderTravelerDTO();
					travelerDTO.setName(traveler.getName());
					travelerDTO.setMobile(traveler.getMobile());
					travelerDTO.setType(traveler.getType());
					travelerDTO.setIdNo(traveler.getCardNo());
					travelerDTO.setIdType(traveler.getCardType());
					travelerDTOs.add(travelerDTO);
				}
			}
			// 下单用户
			{
				LineOrderUserDTO orderUser = new LineOrderUserDTO();
				webCommand.setLineOrderUser(orderUser);
				orderUser.setUserId(user.getId());
				orderUser.setLoginName(user.getAuthInfo().getLoginName());
				orderUser.setMobile(user.getContactInfo().getMobile());
			}
		}


		LineOrderLocalService self = SpringContextUtil.getApplicationContext().getBean(LineOrderLocalService.class);
		LineOrderDTO lineOrderDTO = self.createWebLineOrder(webCommand);
		// 如果预付款为0则立即更改支付状态
		if (lineOrderDTO.getBaseInfo().getBargainMoney() == 0d)
			self.lineOrderPaySuccess(lineOrderDTO.getBaseInfo().getDealerOrderNo(), false);
		else
			sendSmsTask.putCheckLineOrder(lineOrderDTO.getId(), lineOrderDTO.getBaseInfo().getCreateDate());

		return lineOrderDTO;
	}
	/**
	 * @throws LineException
	 * @方法功能说明：创建线路订单(H5)
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-12下午4:46:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrder
	 * @throws
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LineOrderDTO createWebLineOrder(HslCreateLineOrderCommand command) throws LineException {
		return createLineOrder(command);
	}
	/**
	 * 确认支付
	 *
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ApplyToPayLineOrderResult applyToPayLineOrder(ApplyToPayLineOrderCommand command,LineOrder lineOrder,String suffix) throws Exception {

		if (StringUtils.isBlank(command.getLineOrderId()))
			throw new ShowMessageException("缺少订单ID");

		LineOrderLocalService self = SpringContextUtil.getApplicationContext().getBean(LineOrderLocalService.class);
		double payAmount = 0d;
		double bargainMoney=0d;
		String title = "";
		
		//计算订单中要支付金额
		List<LineOrderTraveler> travelerList=lineOrder.getTravelerList();
		for(LineOrderTraveler traveler:travelerList){
			if(!traveler.getLineOrderStatus().getOrderStatus().equals(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL))){
				payAmount+=traveler.getSingleSalePrice();
				bargainMoney+=traveler.getSingleBargainMoney();
			}
		}

		ApplyToPayLineOrderResult result = new ApplyToPayLineOrderResult();
		// 状态检查
		if (lineOrder.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)) {
			// 等待支付预订金，并检查是否需要全款支付
			if (lineOrder.needPayAll()) {
				// 需要全款支付
				title = "全款";
				//payAmount = lineOrder.getBaseInfo().getSalePrice();
			} else {
				title = "预订金";
				//payAmount = lineOrder.getBaseInfo().getBargainMoney();
				payAmount=bargainMoney;
			}
			result.setResultCode(ApplyToPayLineOrderResult.RESULT_DOWN_PAYMENT_FORM_HTML);
		} else if (lineOrder.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)) {
			// 等待支付尾款
			//payAmount = MoneyUtil.round(lineOrder.getBaseInfo().getSalePrice() - lineOrder.getBaseInfo().getBargainMoney(), 2);
			payAmount=payAmount-bargainMoney;
			title = "尾款";
			result.setResultCode(ApplyToPayLineOrderResult.RESULT_FINAL_PAYMENT_FORM_HTML);
		} else if (!LineOrderPayType.PAY_TYPE_FREE.equals(command.getPayType())) {
			// 无需支付
			throw new ShowMessageException("无需支付");
		}

		/**检查是否使用卡券*/
		if (command.getCouponIds().size() > 0) {
			BatchConsumeCouponCommand consumeCouponCommand = null;
			consumeCouponCommand = new BatchConsumeCouponCommand();
			consumeCouponCommand.setCouponIds(command.getCouponIds().toArray(new String[command.getCouponIds().size()]));
			consumeCouponCommand.setOrderId(lineOrder.getBaseInfo().getDealerOrderNo());
			consumeCouponCommand.setPayPrice(payAmount);
			consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_XL);
			// 占用卡券，保存订单快照
			List<CouponDTO> couponDTOs = couponService.occupyCoupon(consumeCouponCommand);
			for (CouponDTO couponDTO : couponDTOs) {
				payAmount = MoneyUtil.round(payAmount - couponDTO.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue(), 2);
				if (payAmount < 0) {
					payAmount = 0d;
					break;
				}
			}
		}
		/**检查是否使用余额*/
		if(command.getBalanceMoney()!=null&&command.getBalanceMoney()>0){
			double balanceM=command.getBalanceMoney();
			//计算使用余额后，订单支付总价
			if(payAmount>0){
				payAmount=MoneyUtil.round((payAmount-balanceM)>0?(payAmount-balanceM):0d, 2);
			}
			//占用账户余额快照
			consumptionAccount(command.getUserId()+"",balanceM,lineOrder.getBaseInfo().getDealerOrderNo(),AccountConsumeSnapshot.STATUS_ZY,false);
		}

		UpdateLineOrderPayInfoCommand payCommand=new UpdateLineOrderPayInfoCommand();
		payCommand.setLineId(lineOrder.getId());
		payCommand.setCashPrice(payAmount);
		lineOrderService.updateLineOrderPayCash(payCommand);//保存现金支付金额
		HgLogger.getInstance().info("zhaows", "saveOrder-->调用支付宝"+payAmount);
		result = self.applyToPayLineOrderBefore(command,payAmount,title,result,suffix);
		HgLogger.getInstance().info("renfeng", "H5线路支付applyToPayLineOrder()-->result:"+JSON.toJSONString(result));
		// 未抛出异常则检查是否无需支付
		if (LineOrderPayType.PAY_TYPE_FREE.equals(command.getPayType())) {
			self.lineOrderPaySuccess(lineOrder.getBaseInfo().getDealerOrderNo(), lineOrder.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY));

		}
		return result;
	}
	/**
	 * @throws Exception
	 * @throws CouponException
	 * @方法功能说明：确认支付之前
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-12下午5:56:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyToPayLineOrderResult
	 * @throws
	 */
	public ApplyToPayLineOrderResult applyToPayLineOrderBefore(ApplyToPayLineOrderCommand command,double payAmount,String title,
			ApplyToPayLineOrderResult result,String suffix) throws Exception {
		HgLogger.getInstance().info("renfeng", "applyToPayLineOrderBefore-->:支付command:"+JSON.toJSONString(command));
		if (command.getPaymentFormHtmlBuilder() == null)
			throw new ShowMessageException("支付处理异常");

		if (command.getUserId() == null)
			throw new ShowMessageException("登录超时");
		if (command.getLineOrderId() == null)
			throw new ShowMessageException("非法请求:缺少线路订单ID");

		User user = userDao.get(command.getUserId());
		if (user == null)
			throw new ShowMessageException("操作用户异常");

		LineOrder lineOrder = getDao().get(command.getLineOrderId());
		if (lineOrder == null)
			throw new ShowMessageException("线路订单丢失");
		if (!command.getUserId().equals(lineOrder.getLineOrderUser().getUserId()))
			throw new ShowMessageException("无权操作");

		if (command.getCouponIds().size() > 1)
			throw new ShowMessageException("暂时只支持单张卡券");

		String subject = "“" + lineOrder.getLineSnapshot().getLineName() + "”" + title;

		if(LineOrderPayType.PAY_TYPE_ALIPAY.equals(command.getPayType())){
			// 支付宝
			if (payAmount == 0)
				throw new ShowMessageException("无需支付");
			String travelerHasPayStatus=lineOrder.travelerHasPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY) ? "-2" : "";
			result.setFormHtml(command.getPaymentFormHtmlBuilder().buildAlipayFormHtml(lineOrder.getBaseInfo().getDealerOrderNo()
					+travelerHasPayStatus, subject, payAmount,suffix));
		} else if (LineOrderPayType.PAY_TYPE_FREE.equals(command.getPayType())) {
			// 使用卡券 或余额 后金额为0的情况不用支付
			if (payAmount != 0)
				throw new ShowMessageException("请先支付" + title);
			result.setResultCode(ApplyToPayLineOrderResult.RESULT_FREE);
		} else {
			throw new ShowMessageException("支付参数错误");
		}

		return result;
	}

	/** @throws AccountException
	 * @方法功能说明：消费账户余额
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-7下午1:43:27
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void consumptionAccount(String userID,double accountBalance,String orderNo,int stutas,boolean consumption) throws AccountException{
		HgLogger.getInstance().info("zhaows","线路支付--->consumptionAccount=" + userID+"="+accountBalance+"="+orderNo+"="+stutas);
		AccountCommand accountCommand=new AccountCommand();
		accountCommand.setCommandId(userID);//余额账户ID
		accountCommand.setCurrentMoney(accountBalance);//本次消费金额
		accountCommand.setConsumption(consumption);
		AccountConsumeSnapshotCommand snapshotCommand=new AccountConsumeSnapshotCommand();
		snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_XL);
		snapshotCommand.setOrderId(orderNo);
		snapshotCommand.setStatus(stutas);
		snapshotCommand.setPayPrice(accountBalance);
		accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
		accountService.consumptionAccount(accountCommand);
	}

	/**
	 * 更新线路支付信息
	 * @param command
	 */
	public void updateLineOrderPayInfo(UpdateLineOrderPayInfoCommand command) {
		HslLineOrderQO lineOrderQO=new HslLineOrderQO();
		lineOrderQO.setId(command.getLineId());
		LineOrder lineOrder=lineOrderDAO.queryUnique(lineOrderQO);
		lineOrder.updateLineOrderPayInfo(command);
		lineOrderDAO.update(lineOrder);
	}
	public void updateLineOrderPayCash(UpdateLineOrderPayInfoCommand command){
		HslLineOrderQO lineOrderQO=new HslLineOrderQO();
		if(StringUtils.isNotBlank(command.getLineId())&&command.getCashPrice()!=null){
			lineOrderQO.setId(command.getLineId());
			LineOrder lineOrder=lineOrderDAO.queryUnique(lineOrderQO);
			lineOrder.getLineOrderPayInfo().setCashPrice(command.getCashPrice());
			lineOrderDAO.update(lineOrder);
		}

	}
}
