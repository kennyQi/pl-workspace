package hsl.app.service.local.jp;
import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.app.common.util.EntityConvertUtils;
import hsl.app.common.util.OrderUtil;
import hsl.app.dao.CityAirCodeDAO;
import hsl.app.dao.CouponDao;
import hsl.app.dao.JPPassengerDao;
import hsl.app.dao.jp.FlightOrderDAO;
import hsl.app.service.local.coupon.CouponLocalService;
import hsl.domain.model.coupon.Coupon;
import hsl.domain.model.jp.FlightOrder;
import hsl.domain.model.jp.Passenger;
import hsl.domain.model.sys.CityAirCode;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.command.jp.JPOrderCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.spi.qo.sys.CityAirCodeQO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
@Transactional
public class FlightOrderService extends BaseServiceImpl<FlightOrder, FlightOrderQO, FlightOrderDAO> {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Resource
	private CouponLocalService couponLocalService;
	@Resource
	private CouponDao couponDao;
	@Autowired
	private JPPassengerDao jPPassengerDao;
	@Resource
	private CityAirCodeDAO cityAirCodeDAO;
	@Resource
	private SMSUtils smsUtils;
	@Resource
	private JedisPool jedisPool;
	/**
	 * 
	 * @方法功能说明：保存本地订单
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-30上午8:29:22
	 * @参数：@param bookGNFlightCommand
	 * @return:void
	 * @throws
	 */
	public FlightOrderDTO createFlightOrder(BookGNFlightCommand bookGNFlightCommand){
		HgLogger.getInstance().info("cs", "【FlightOrderService>>createFlightOrder】" + "bookGNFlightCommand:" + JSON.toJSONString(bookGNFlightCommand));
		
		Date nowDate = new Date();
		String dealerOrderCode = OrderUtil.createOrderNo(nowDate, this.getOrderSequence(), 0, 0);//订单号
		bookGNFlightCommand.setOrderNO(dealerOrderCode);
		//1:保存订单信息
		FlightOrder flightOrder = new FlightOrder();
		flightOrder.createFlightOrder(bookGNFlightCommand);
		/*List<Passenger> passengerList=new ArrayList<Passenger>();
		for(PassengerGNDTO passengerDTO:bookGNFlightCommand.getPassengers()){//使用懒加载后只能遍历保存
			Passenger passenger=JSON.parseObject(JSON.toJSONString(passengerDTO), Passenger.class);
			passenger.setId(UUIDGenerator.getUUID());
			passenger.setOrder(flightOrder);
			passengerList.add(passenger);
			jPPassengerDao.save(passenger);
		}*/
		flightOrderDAO.save(flightOrder);
		FlightOrderDTO flightOrderDTO=EntityConvertUtils.convertEntityToDto(flightOrder, FlightOrderDTO.class);
		return flightOrderDTO;
		
	}
	public boolean askOrderTicket(JPPayOrderGNCommand command,boolean result){
		HgLogger.getInstance().info("zhaows", "askOrderTicket-->查询条件" + JSON.toJSONString(command));
		FlightOrderQO qo = new FlightOrderQO();
		qo.setOrderNO(command.getDealerOrderId());
		qo.setOrderType("1");
		FlightOrder jpOrder = flightOrderDAO.queryUnique(qo);
		HgLogger.getInstance().info("zhaows", "askOrderTicket-->查询结果" + JSON.toJSONString(jpOrder));
		//查不到订单，直接返回
		if (jpOrder == null ) {
			return false;	
		}
		jpOrder.setBuyerEmail(command.getBuyerEmail());
		jpOrder.setPayTradeNo(command.getPayTradeNo());
		//处理请求出票结果
		if (result){
			//出票处理中
			//机票订单支付状态为   支付成功， 故不需要变化
			jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));
		} else{ 
			//出票失败
			jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
			jpOrder.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
			jpOrder.getFlightPriceInfo().setReturnedPrice(command.getPayPrice());
//			jpOrder.setOrderType("4");
			//请求出票失败，将卡券退还用户
			OrderRefundCommand cmd=new OrderRefundCommand();
			cmd.setOrderId(command.getDealerOrderId());
			try {
				couponLocalService.orderRefund(cmd);
			} catch (CouponException e) {
				HgLogger.getInstance().error("chenxy", "请求出票失败>>>退还卡券出错"+HgLogger.getStackTrace(e));
				e.printStackTrace();
			}
		}
		flightOrderDAO.update(jpOrder);
		return true;
	}
	public boolean updateOrderStatus(UpdateJPOrderStatusCommand command){
		if(command==null)
			return false;
		FlightOrderQO qo = new FlightOrderQO();
		qo.setOrderNO(command.getDealerOrderCode());
		qo.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
		FlightOrder jpOrder = flightOrderDAO.queryUnique(qo);
		if(jpOrder==null)
			return false;
		HgLogger.getInstance().info("wuyg", "JPOrderLocalService->updateOrderStatus机票订单更新：" + JSON.toJSONString(command));
		if(command.getPayStatus()!=null)
			jpOrder.setPayStatus(command.getPayStatus());
		if(command.getStatus()!=null)
			jpOrder.setStatus(command.getStatus());
		if(command.getBackPrice()!=null&&command.getBackPrice()>=0){
			jpOrder.getFlightPriceInfo().setReturnedPrice(command.getBackPrice());
		}
		if(command.getPayCash()!=null&&command.getPayCash()>=0){
			jpOrder.getFlightPriceInfo().setPayCash(command.getPayCash());
		}
		if(command.getPayBalance()!=null&&command.getPayBalance()>=0){
			jpOrder.getFlightPriceInfo().setPayBalance(command.getPayBalance());
		}
		if(StringUtils.isNotBlank(command.getPayTradeNo())){
			jpOrder.setPayTradeNo(command.getPayTradeNo());
		}
		if(StringUtils.isNotBlank(command.getBuyerEmail())){
			jpOrder.setBuyerEmail(command.getBuyerEmail());
		}
		flightOrderDAO.update(jpOrder);
		return true;
	}
	public  boolean updateOrderStatusByID(UpdateJPOrderStatusCommand command){
		if(command==null)
			return false;
		FlightOrderQO qo = new FlightOrderQO();
		qo.setId(command.getId());
		FlightOrder jpOrder = flightOrderDAO.queryUnique(qo);
		if(jpOrder==null)
			return false;
		HgLogger.getInstance().info("chenxy", "JPOrderLocalService->updateOrderStatusById机票订单更新：" + JSON.toJSONString(command));
		if(command.getPayStatus()!=null)
			jpOrder.setPayStatus(command.getPayStatus());
		if(command.getStatus()!=null)
			jpOrder.setStatus(command.getStatus());
		if(command.getBackPrice()!=null&&command.getBackPrice()>=0){
			jpOrder.getFlightPriceInfo().setReturnedPrice(command.getBackPrice());
		}
		if(command.getPayCash()!=null&&command.getPayCash()>=0){
			jpOrder.getFlightPriceInfo().setPayCash(command.getPayCash());
		}
		flightOrderDAO.update(jpOrder);
		return true;
	}
	public boolean orderRefund(String resultDetails){
		if(StringUtils.isBlank(resultDetails))
			return false;
		boolean rt=true;
		String[] trades=resultDetails.split("#");
		for(String trade:trades){
			String str=trade.split("\\$")[0];//获得交易退款数据集
			String[] data=str.split("\\^");//按照 "原付款支付宝交易号^退款总金额^退款状态" 格式分割为数组
			String tradeno=data[0];
			String sum=data[1];
			String status=data[2];
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->付款支付宝交易号:"+tradeno);
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->退款总金额:"+sum);
			HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->退款状态"+status);
			FlightOrderQO qo=new FlightOrderQO();
			qo.setPayTradeNo(tradeno);
			qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_DEALING));
			try {
				FlightOrder jporder=flightOrderDAO.queryUnique(qo);
				
				if(jporder==null||!status.trim().equals("SUCCESS")){
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->jporder is null?"+JSON.toJSONString(jporder)+"======status:"+status);
					continue;
				}
				jporder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC));//已退款
				jporder.getFlightPriceInfo().setReturnedPrice(Double.parseDouble(sum));
				HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->jporder:"+JSON.toJSONString(jporder));
				flightOrderDAO.update(jporder);
				//退还卡券
				HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券");
				HslCouponQO couponqo=new HslCouponQO();
				couponqo.setOrderId(jporder.getOrderNO());
				List<Coupon> list=couponDao.queryList(couponqo);
				if(list.isEmpty())
					continue;
				Coupon c=list.get(0);
				double value=c.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
				//卡券面额大于退款金额，不返还卡券
				if(jporder.getFlightPriceInfo().getPayAmount()-value>0){
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券->退款金额大于卡券面额，返还卡券");
					//返还卡券
					OrderRefundCommand cmd=new OrderRefundCommand();
					cmd.setOrderId(jporder.getOrderNO());
//					couponLocalService.orderRefund(cmd);暂时屏蔽退款不退卡券
				}else{
					HgLogger.getInstance().info("wuyg", "JPOrderLocalService->OrderRefund->开始退还卡券->卡券面额大于退款金额，不返还卡券");
				}
			} catch (NumberFormatException e) {
				HgLogger.getInstance().error("wuyg", "JPOrderLocalService->OrderRefund->NumberFormatException:"+HgLogger.getStackTrace(e));
				rt=false;
			}catch (Exception e) {
				HgLogger.getInstance().error("wuyg", "JPOrderLocalService->OrderRefund->Exception:"+HgLogger.getStackTrace(e));
				rt=false;
			}
		}
		return rt;
	}
	/**
	 * @方法功能说明：通知后修改机票订单
	 * @修改者名字：chenxy
	 * @修改时间：2015年7月31日下午4:21:25
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateJpOrder(JPOrderCommand command){
		//空指针，直接返回
		if (command == null || StringUtils.isBlank(command.getDealerOrderCode())){
			return false;
		}
		try{
			FlightOrderQO qo = new FlightOrderQO();
			qo.setOrderNO(command.getDealerOrderCode());
			qo.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
			FlightOrder jpOrder = getDao().queryUnique(qo);
			HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->查询原始订单jpOrder:" + JSON.toJSONString(jpOrder));
			switch (Integer.parseInt(command.getType())) {
			/*出票成功通知*/
			case 1:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->分销出票成功通知直销");
				List<Passenger> passengers=jpOrder.getPassengers();
				//将乘客信息和票号信息对应
				String passangerNames=command.getPassengerName();
				String airIds=command.getAirId();
				String[] passer=passangerNames.split("\\^");
				String[] airs=airIds.split("\\^");
				Map<String,String> passangerMap=new HashMap<String,String>();
				for(int i=0; i<passer.length;i++){
					passangerMap.put(passer[i],airs[i]);
				}
				jpOrder.setPnr(command.getNewPnr());
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC));//已出票
				this.flightOrderDAO.update(jpOrder);
				for(Passenger passenger:passengers){
					String airId=passangerMap.get(passenger.getPassengerName());
					if(StringUtils.isNotBlank(airId)){
						passenger.setAirId(airId);
					}
					StringBuffer sb=new StringBuffer();
					//发送短信
					try {
						sb.setLength(0);//清空sb
						if (jpOrder.getStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_SUCC)) {
							sb.append("【"+SysProperties.getInstance().get("sms_sign")+"】").append(jpOrder.getFlightBaseInfo().getStartTime()).append(jpOrder.getFlightBaseInfo().getOrgCity()+jpOrder.getFlightBaseInfo().getDepartTerm()).append("到");
							sb.append(jpOrder.getFlightBaseInfo().getDstCity()+jpOrder.getFlightBaseInfo().getArrivalTerm()).append("的 ").append(jpOrder.getFlightBaseInfo().getFlightNO()).append(" 航班（");
							sb.append(jpOrder.getFlightBaseInfo().getStartTime()+"起飞");
							sb.append("/").append(jpOrder.getFlightBaseInfo().getEndTime()+"到达");
							sb.append("）已出票。").append(passenger.getPassengerName()).append("票号：").append(airId);
							sb.append("。请提前2个小时到机场办理乘机手续。客服电话：010—65912283。");
						}
						smsUtils.sendSms(jpOrder.getJpLinkInfo().getLinkTelephone(), sb.toString());
						HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->正在发送出票完成短信->mobile:" + jpOrder.getJpLinkInfo().getLinkTelephone());
					}catch(Exception e){
						HgLogger.getInstance().error("chenxy", "发送短信失败>>>"+HgLogger.getStackTrace(e));
						e.printStackTrace();
					}
				}
				break;
				/*取消成功通知(该取消通知只是分销取消后通知直销，直销主动申请取消时，如果调通接口直接改为已取消。因此查询时只查询正常订单)*/
			case 3:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->分销取消成功通知直销"+JSON.toJSONString(jpOrder));
				String[] names=command.getPassengerName().split("\\^");
				/*取消成功通知时判断该订单的支付状态是否是未支付状态，如果不是，不做已取消状态设置*/
				if(jpOrder.getPayStatus()!=Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY)){
					HgLogger.getInstance().info("chenxy", "取消成功通知但是支付状态不是未支付，取消更新");
					return false;
				}
				
				//生成旅客取消订单
				Double tickPrice=jpOrder.getDealerReturnInfo().getTickPrice();
				Double buildFee=jpOrder.getFlightPriceInfo().getBuildFee();//机建费
				Double oilFee=jpOrder.getFlightPriceInfo().getOilFee();//燃油费
				
				double onePsgPrice=tickPrice+(buildFee+oilFee);
				double onePsgMoney=jpOrder.getFlightPriceInfo().getSinglePlatTotalPrice();;
				
				jpOrder.getDealerReturnInfo().setRefuseMemo(command.getRefuseMemo());

				//查询出的正常订单中，将旅客列表与command中旅客名称对比，
				//如果存在，说明是分销申请取消清单，直销这边要做取消操作；如果不存在，说明是直销这边申请取消，分销返回成功取消的通知
				FlightOrder cancelOrder=new FlightOrder();
				cancelOrder.cancelFlightOrder(jpOrder, names);
				cancelOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney));
				cancelOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice));
				
				this.flightOrderDAO.save(cancelOrder);
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder>>>新生成取消订单cancelOrder:"+JSON.toJSONString(cancelOrder));
				//将取消订单的乘客从原订单中去除
				jpOrder.getPassengers().removeAll(cancelOrder.getPassengers());
							
				//设置取消订单后的支付总价:将取消订单的乘客从原订单中去除
				//乘客人数
				int num=jpOrder.getPassengers().size();
				jpOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice*num));
				jpOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney*num));
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder>>>更新后原订单order:"+JSON.toJSONString(jpOrder));
				//原始订单没有乘客的时候，修改原始订单为记录订单，orderType为“4”
				if(num==0){
					jpOrder.setOrderType(FlightOrder.ORDERTYPE_RECORD);
				}
				this.flightOrderDAO.update(jpOrder);	
					
				break;
				/*退废票通知*/
			case 4:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder-退废票通知直销");
				FlightOrderQO orderQo = new FlightOrderQO();
				orderQo.setOrderNO(command.getDealerOrderCode());
				orderQo.setOrderType(FlightOrder.ORDERTYPE_REFUND);
				List<FlightOrder> jpOrderList = getDao().queryList(orderQo);
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder>>>查询退费订单记录jpOrderList:"+JSON.toJSONString(jpOrderList));
				for(FlightOrder order:jpOrderList){
					
					String[] aStrings=command.getAirId().split("\\^");
					for(String airId:aStrings){
						//每个 退费记录订单里面可能有多个乘客
						for(Passenger psg:order.getPassengers()){
							if(psg.getAirId().equals(airId)){
								if(command.getRefundStatus()==1){
									order.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC));
									order.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
								}else if(command.getRefundStatus()==2){
									order.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_FAIL));
									order.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
								}
								psg.setAirId(airId);
								order.getFlightPriceInfo().setReturnedPrice(command.getRefundPrice());
								order.getDealerReturnInfo().setRefuseMemo(command.getRefuseMemo());
								order.getDealerReturnInfo().setProcedureFee(command.getProcedures());
							}
						}
						
					}
					HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder>>>退废票通知修改订单状态"+JSON.toJSONString(order));
					this.flightOrderDAO.update(order);	
				}
				break;
				/*分销申请退费处理中*/
			case 5:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->分销申请退费处理中通知"+JSON.toJSONString(jpOrder));
				
				String[] psgNames=command.getPassengerName().split("\\^");
				
				Double tickPrice2=jpOrder.getDealerReturnInfo().getTickPrice();
				Double buildFee2=jpOrder.getFlightPriceInfo().getBuildFee();//机建费
				Double oilFee2=jpOrder.getFlightPriceInfo().getOilFee();//燃油费
				
				double onePsgPrice2=tickPrice2+(buildFee2+oilFee2);
				double onePsgMoney2=jpOrder.getFlightPriceInfo().getSinglePlatTotalPrice();
				
				List<Passenger> psgList=new ArrayList<Passenger>();
				psgList.addAll(jpOrder.getPassengers());
				int refundNum=psgNames.length;
				FlightOrder refundOrder=new FlightOrder();
				refundOrder.refundFlightOrder(jpOrder, psgNames);
				refundOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney2*refundNum));
				refundOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice2*refundNum));
				this.flightOrderDAO.save(refundOrder);
				//将退费订单的乘客从原订单中去除
				jpOrder.getPassengers().removeAll(refundOrder.getPassengers());
				//设置退费订单后的支付总价:将退费订单的乘客从原订单中去除
				//乘客人数
				int pNum=jpOrder.getPassengers().size();
				jpOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney2*pNum));
				jpOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice2*pNum));
				jpOrder.getDealerReturnInfo().setRefuseMemo(command.getRefuseMemo());
				
				//原始订单没有乘客的时候，删除原始订单，否则，更新原始订单		
				if(pNum==0){
					jpOrder.setOrderType(FlightOrder.ORDERTYPE_RECORD);
				}
				this.flightOrderDAO.update(jpOrder);		
				break;
				/*拒绝出票通知*/
			case 6:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->分销拒绝出票通知直销"+JSON.toJSONString(jpOrder));
				jpOrder.getDealerReturnInfo().setRefuseMemo(command.getRefuseMemo());
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));//出票失败
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
				StringBuffer sb=new StringBuffer();
				this.flightOrderDAO.update(jpOrder);
				//拒绝出票时，将卡券退还用户
				OrderRefundCommand cmd=new OrderRefundCommand();
				cmd.setOrderId(jpOrder.getOrderNO());
				try {
					couponLocalService.orderRefund(cmd);
				} catch (CouponException e) {
					HgLogger.getInstance().error("chenxy", "请求出票失败>>>退还卡券出错"+HgLogger.getStackTrace(e));
					e.printStackTrace();
				}
				//发送短信
				try {
					sb.setLength(0);//清空sb
					//出票失败待退款
					sb.append("【"+SysProperties.getInstance().get("sms_sign")+"】对不起，您的订单:").append(jpOrder.getOrderNO());
					sb.append("出票失败，已申请退款，具体到账时间请咨询相应银行，客服电话：010—65912283。");
					smsUtils.sendSms(jpOrder.getJpLinkInfo().getLinkTelephone(), sb.toString());
					HgLogger.getInstance().info("zhangka", "JPOrderLocalService->orderTicketNotify->正在发送出票完成短信->mobile:" + jpOrder.getJpLinkInfo().getLinkTelephone());
				}catch(Exception e){
					HgLogger.getInstance().error("chenxy", "发送短信失败>>>"+HgLogger.getStackTrace(e));
					e.printStackTrace();
				}
				break;
			default:
				HgLogger.getInstance().info("renfeng", "FlightOrderService->updateJpOrder->查询出错！" );
				return false;
			}
		}catch(Exception e){
			HgLogger.getInstance().error("chenxy", "FlightOrderService->updateJpOrder>>>错误信息:"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	protected FlightOrderDAO getDao() {
		return flightOrderDAO;
	}
	/**
	 * 
	 * @方法功能说明：查询订单
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-30上午8:30:08
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<FlightOrderDTO>
	 * @throws
	 */
	public List<FlightOrderDTO> queryOrder(FlightOrderQO qo) {
		// 查询机票订单
		List<FlightOrder> jpOrders = flightOrderDAO.queryList(qo);
		List<FlightOrderDTO> jpOrderDTOList = EntityConvertUtils.convertEntityToDtoList(jpOrders, FlightOrderDTO.class);
		return jpOrderDTOList;
	}
	/**
	 * 
	 * @方法功能说明：查询机场编码信息
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-6上午9:27:36
	 * @参数：@param cityAirCodeQO
	 * @参数：@return
	 * @return:CityAirCode
	 * @throws
	 */
	public CityAirCode queryLocalCityAirCode(CityAirCodeQO cityAirCodeQO) {
		return cityAirCodeDAO.queryUnique(cityAirCodeQO);
	}
	
	
	public void updates(FlightOrder flightOrder) {
		flightOrderDAO.update(flightOrder);
	}
	/**
	 * @方法功能说明：返回差旅dto
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-19上午9:38:53
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryTravelDtoPagination(Pagination pagination){
		Pagination p1=queryPagination(pagination);
		p1.setList(changeJPToTravelDto((List<FlightOrder>) p1.getList()));
		return p1;
	}
	/**
	 * @方法功能说明：转化机票为travelDTO，没有设置职务
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-19上午9:39:31
	 * @参数：@param jplist
	 * @参数：@return
	 * @return:List<TravelDTO>
	 * @throws
	 */
	private List<TravelDTO> changeJPToTravelDto(List<FlightOrder> jplist){
		String orderNo="";
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		for(FlightOrder jpOrder:jplist){
			List<Passenger> jpPassangers=jpOrder.getPassengers();
			//FlightOrderDTO flightDTO=JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightOrderDTO.class);
			for(Passenger jpPassanger:jpPassangers){
				if(!orderNo.equals(jpOrder.getOrderNO())||orderNo.equals("")){
					TravelDTO travelDTO=new TravelDTO();
					travelDTO.setCompanyName(jpPassanger.getCompanyName());
					travelDTO.setDeptName(jpPassanger.getDepartmentName());
					travelDTO.setDestination(jpOrder.getFlightBaseInfo().getDstCity());//目的地 
//					HslMemberQO qo=new HslMemberQO();
//					qo.setId(jpPassanger.getMemeberId());
//					Member member=companyLocalService.getMember(qo);
					//travelDTO.setJob(member.getJob());//职务
					travelDTO.setId(jpPassanger.getMemeberId());//设置成员id
					travelDTO.setMemberName(jpPassanger.getPassengerName());//乘客姓名
					travelDTO.setOrderNum(jpOrder.getOrderNO());//订单号
					travelDTO.setPrice(jpOrder.getFlightPriceInfo().getPayAmount());//票面价+税款=总价
					travelDTO.setProjectType(1);
					travelDTO.setTarvelDate(jpOrder.getFlightBaseInfo().getStartTime());
					orderNo=jpOrder.getOrderNO();
					list.add(travelDTO);
				}
			}
		}
		return list;
	}

	/**
	 * 获取机票订单流水号
	 * @return
	 */
	public int getOrderSequence() {
		Jedis jedis = null;
		String value;
		int v=0;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get("zzpl_jp_sequence");
			String date = jedis.get("zzpl_jp_sequence_date");

			if (StringUtils.isBlank(value) || StringUtils.isBlank(date)
					|| !date.equals(String.valueOf(this.getDateString("yyyyMMdd")))) {
				value = "0";
			}
			v = Integer.parseInt(value);
			if (v >= 9999) {
				v = 0;
			}
			v++;
			jedis.set("zzpl_jp_sequence", String.valueOf(v));
			jedis.set("zzpl_jp_sequence_date", String.valueOf(this.getDateString("yyyyMMdd")));
		}catch (Exception e){
			HgLogger.getInstance().error("chenxy","创建机票订单流水号出错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}finally {
			jedisPool.returnResource(jedis);
		}
		return v;
	}
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
}
