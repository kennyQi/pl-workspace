package lxs.app.service.line;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lxs.app.dao.app.OrderNoticeDao;
import lxs.app.dao.line.LineOrderDAO;
import lxs.app.dao.line.LineOrderTravelerDAO;
import lxs.app.dao.line.LxsDateSalePriceDAO;
import lxs.app.dao.line.LxsLineDAO;
import lxs.app.dao.line.LxsLineSnapshotDAO;
import lxs.app.util.line.ClientKeyUtil;
import lxs.app.util.line.OrderUtil;
import lxs.domain.model.app.OrderNotice;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.line.LineOrderStatus;
import lxs.domain.model.line.LineOrderTraveler;
import lxs.domain.model.line.LineSnapshot;
import lxs.pojo.command.line.AlipayCommand;
import lxs.pojo.command.line.CancleLineOrderCommand;
import lxs.pojo.command.line.CreateLineOrderCommand;
import lxs.pojo.command.line.UpdateLineOrderStatusCommand;
import lxs.pojo.dto.line.LineOrderBaseInfoDTO;
import lxs.pojo.dto.line.LineOrderDTO;
import lxs.pojo.dto.line.LineOrderStatusDTO;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.exception.line.LineException;
import lxs.pojo.qo.app.OrderNoticeQO;
import lxs.pojo.qo.line.LineOrderQO;
import lxs.pojo.qo.line.LineOrderTravelerQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.command.xl.XLCreateLineOrderApiCommand;
import slfx.api.v1.request.command.xl.XLModifyLineOrderTravelerApiCommand;
import slfx.api.v1.request.command.xl.XLPayLineOrderApiCommand;
import slfx.api.v1.response.xl.XLCreateLineOrderResponse;
import slfx.api.v1.response.xl.XLModifyLineOrderTravelerResponse;
import slfx.api.v1.response.xl.XLPayLineOrderResponse;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.dto.line.LineSnapshotDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.dto.order.XLOrderStatusDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路service
 * @类修改者：
 * @修改日期：2015年5月19日下午1:29:56
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月19日下午1:29:56
 */
@Service
@Transactional
public class LineOrderLocalService extends BaseServiceImpl<LineOrder, LineOrderQO, LineOrderDAO>{

	/**
	 * 订单dao
	 */
	@Autowired
	private LineOrderDAO lineOrderDAO;
	
	/**
	 * 订单游客dao
	 */
	@Autowired
	private LineOrderTravelerDAO lineOrderTravelerDAO;
	
	/**
	 * 线路dao
	 */
	@Autowired
	private LxsLineDAO lineDAO;
	
	/**
	 * 线路价格日历dao
	 */
	@Autowired
	private LxsDateSalePriceDAO dateSalePriceDAO;
	
	/**
	 * 线路快照dao
	 */
	@Autowired
	private LxsLineSnapshotDAO lineSnapshotDAO;
	
	/**
	 * 线路service
	 */
	@Autowired
	private LineService lineService;
	
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private OrderNoticeDao orderNoticeDao;
	
	private static final Integer ZHIFUBAO=1;
	
	private static final Integer DING_JIN=1;
	private static final Integer WEI_KUAN=2;
	private static final Integer QUAN_KUAN=3;
	@Override
	protected LineOrderDAO getDao() {
		return this.lineOrderDAO;
	}

	/**
	 * 
	 * @方法功能说明：支付成功修改订单状态
	 * @修改者名字：cangs
	 * @修改时间：2015年5月21日下午3:10:40
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public boolean payLineOrder(AlipayCommand command){
		HgLogger.getInstance().info("lxs_dev",  "【payLineOrder】"+JSON.toJSONString(command));
		final String  dealerOrderNo=command.getDealerOrderNo().split("_")[0];
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setDealerOrderNo(dealerOrderNo);
		LineOrder lineOrder = lineOrderDAO.queryUnique(lineOrderQO);
		boolean isNotQUAN_KUAN = true;
		if(lineOrder==null){
			HgLogger.getInstance().info("lxs_dev",  "【payLineOrder】"+"订单为空");
			return false;
		}else{
			LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
			lineOrderTravelerQO.setLineOrderId(lineOrder.getId());
			List<LineOrderTraveler> lineOrderTravelers = lineOrderTravelerDAO.queryList(lineOrderTravelerQO);
			int i=0;
			for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
				if(lineOrderTraveler.getLineOrderStatus().getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE)){
					//订单状态为   预定成功
					//获取当前时间
					Date daydate=new Date();
					long payTotalDaysBeforeStart=lineOrder.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
					//出发时间
					long date=lineOrder.getBaseInfo().getTravelDate().getTime();
					//定金or全额支付 1为支付定金2为全额支付
					if(daydate.getTime()+payTotalDaysBeforeStart<date){
						//支付定金
						HgLogger.getInstance().info("lxs_dev",  "【payLineOrder】"+"支付宝通知此次支付的金额或APP内部支付金额"+command.getPrice());
						HgLogger.getInstance().info("lxs_dev",  "【payLineOrder】"+"该订单总额为"+lineOrder.getBaseInfo().getSalePrice());
						if(lineOrder.getBaseInfo().getSalePrice().equals(command.getPrice()-lineOrder.getInsurancePrice())){
							//支付定金(定金为全额)
							//订单状态改为----》预订成功
							//支付状态改为----》全款支付成功
							LineOrderStatus lineOrderStatus = new LineOrderStatus();
							lineOrderStatus=lineOrderTraveler.getLineOrderStatus();
							lineOrderStatus.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS));
							HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"支付状态由："+lineOrderStatus.getPayStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_PAY_SUCCESS);
							lineOrderStatus.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE));
							HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"订单状态由："+lineOrderStatus.getOrderStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
							lineOrderTravelers.get(i).setLineOrderStatus(lineOrderStatus);
							if(isNotQUAN_KUAN){
								isNotQUAN_KUAN=false;
							}
						}else{
							//支付定金(定金非全额)
							//订单状态不变仍为----》下单成功未定位
							//支付状态改为----》已收到定金
							LineOrderStatus lineOrderStatus = new LineOrderStatus();
							lineOrderStatus=lineOrderTraveler.getLineOrderStatus();
							lineOrderStatus.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX));
							HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"支付状态由："+lineOrderStatus.getPayStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX);
							lineOrderTravelers.get(i).setLineOrderStatus(lineOrderStatus);
						}
					}else{
						//支付全款
						//订单状态改为----》预订成功
						//支付状态改为----》全款支付成功
						LineOrderStatus lineOrderStatus = new LineOrderStatus();
						lineOrderStatus=lineOrderTraveler.getLineOrderStatus();
						lineOrderStatus.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS));
						HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"支付状态由："+lineOrderStatus.getPayStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_PAY_SUCCESS);
						lineOrderStatus.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE));
						HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"订单状态由："+lineOrderStatus.getOrderStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
						lineOrderTravelers.get(i).setLineOrderStatus(lineOrderStatus);
						if(isNotQUAN_KUAN){
							isNotQUAN_KUAN=false;
						}
					}
				}else if(lineOrderTraveler.getLineOrderStatus().getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT)){
					//订单状态为   下单成功已锁定位置
					//订单状态改为----》预订成功
					//支付状态改为----》全款支付成功
					LineOrderStatus lineOrderStatus = new LineOrderStatus();
					lineOrderStatus=lineOrderTraveler.getLineOrderStatus();
					lineOrderStatus.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS));
					HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"支付状态由："+lineOrderStatus.getPayStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_PAY_SUCCESS);
					lineOrderStatus.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS));
					HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"【支付宝】支付宝订单号为："+lineOrder.getBaseInfo().getDealerOrderNo()+"订单状态由："+lineOrderStatus.getOrderStatus()+"---->改为:"+XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
					lineOrderTravelers.get(i).setLineOrderStatus(lineOrderStatus);
					if(isNotQUAN_KUAN){
						isNotQUAN_KUAN=false;
					}
				}
				i++;
			}
			//本地游客状态持久化，将游客list转为分销游客list
			List<LineOrderTravelerDTO> orderTravelerDTOs =new ArrayList<LineOrderTravelerDTO>();
			for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
				lineOrderTravelerDAO.update(lineOrderTraveler);
				//开搞TOT
				String travelerJSON=JSON.toJSONString(lineOrderTraveler);
				LineOrderTravelerDTO lineOrderTravelerDTO= JSON.parseObject(travelerJSON,LineOrderTravelerDTO.class);
				orderTravelerDTOs.add(lineOrderTravelerDTO);
			}
			//--------------------------------------------开始更改订单表中，订单状态和支付状态----------------------------------------------------------------------
			LineOrderQO lineOrderQO2 = new LineOrderQO();
			lineOrderQO2.setDealerOrderNo(dealerOrderNo);
			LineOrder lineOrder2 = lineOrderDAO.queryUnique(lineOrderQO);
			LineOrderTravelerQO lineOrderTravelerQO2 = new LineOrderTravelerQO();
			lineOrderTravelerQO2.setLineOrderId(lineOrder2.getId());
			List<LineOrderTraveler> lineOrderTravelers3 = lineOrderTravelerDAO.queryList(lineOrderTravelerQO);
			String lineOrderStautes="";
			String payOrderStautes="";
			for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers3){
				lineOrderStautes=lineOrderStautes+lineOrderTraveler.getLineOrderStatus().getOrderStatus()+",";
				payOrderStautes=payOrderStautes+lineOrderTraveler.getLineOrderStatus().getPayStatus()+",";
			}
			lineOrder2.setOrderStautes(lineOrderStautes);
			lineOrder2.setPayStautes(payOrderStautes);
			lineOrderDAO.update(lineOrder2);
			HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"本地游客状态持久化");
			//通知分销更改游客状态
			XLModifyLineOrderTravelerApiCommand apiCommand=new XLModifyLineOrderTravelerApiCommand();
			apiCommand.setDealerOrderNo(lineOrder.getBaseInfo().getDealerOrderNo());
			int j=0;
			//写懵了 都不知道这个循环干啥delete
			for(LineOrderTravelerDTO traveler:orderTravelerDTOs){
				XLOrderStatusDTO xlOrderStatus=new XLOrderStatusDTO();
				xlOrderStatus.setStatus(lineOrderTravelers.get(j).getLineOrderStatus().getOrderStatus());
				xlOrderStatus.setPayStatus(lineOrderTravelers.get(j).getLineOrderStatus().getPayStatus());
				orderTravelerDTOs.get(j).setXlOrderStatus(xlOrderStatus);
				orderTravelerDTOs.get(j).setLineOrder(null);
				j++;
			}
			//由于分销和直销的线路定单状态命名不同,设置分销线路订单状态
			apiCommand.setTravelers(orderTravelerDTOs);
			HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"LineOrderLocalService->通知分销修改线路订单游客订单状态："+JSON.toJSONString(apiCommand));
			ApiRequest apiRequest = new ApiRequest("XLModifyLineOrderTraveler",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
			XLModifyLineOrderTravelerResponse response = slfxApiClient.send(apiRequest, XLModifyLineOrderTravelerResponse.class);
			HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"LineOrderLocalService->通知分销修改线路订单游客状态：返回结果"+JSON.toJSONString(response));
			if(response!=null&&StringUtils.isNotBlank(response.getResult())){
				if(StringUtils.equals(String.valueOf(-1), response.getResult())){
					//创建失败。返回异常信息
					HgLogger.getInstance().info("lxs_dev","【payLineOrder】"+"通知分销更改状态失败");
					return false;
				}
			}
			HgLogger.getInstance().info("lxs_dev","【payLineOrder】"+"支付成功后更改状态成功");
			//更新订单的支付状态
			XLPayLineOrderApiCommand xlPayLineOrderApiCommand = new XLPayLineOrderApiCommand();
			xlPayLineOrderApiCommand.setDealerOrderNo(lineOrder.getBaseInfo().getDealerOrderNo());
			//支付方式
			xlPayLineOrderApiCommand.setPaymentType(ZHIFUBAO);
			//支付账号
			xlPayLineOrderApiCommand.setPaymentAccount(command.getPaymentAccount());
			//支付人姓名
			xlPayLineOrderApiCommand.setPaymentName(lineOrder.getLinkInfo().getLinkName());
			//支付宝流水号
			xlPayLineOrderApiCommand.setSerialNumber(command.getSerialNumber());
			//支付金额
			xlPayLineOrderApiCommand.setPaymentAmount(command.getPrice());
			//支付时间
			xlPayLineOrderApiCommand.setPaymentTime(new Date());
			//款项说明
			if(command.getDealerOrderNo().split("_").length==1){
				if(isNotQUAN_KUAN){
					xlPayLineOrderApiCommand.setShopPayType(DING_JIN);
				}else{
					xlPayLineOrderApiCommand.setShopPayType(QUAN_KUAN);
				}
			}else{
				xlPayLineOrderApiCommand.setShopPayType(WEI_KUAN);
			}
			HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"LineOrderLocalService->通知分销修改线路订单支付信息："+JSON.toJSONString(xlPayLineOrderApiCommand));
			apiRequest = new ApiRequest("XLPayLineOrder",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), xlPayLineOrderApiCommand);
			XLPayLineOrderResponse xlPayLineOrderResponse = slfxApiClient.send(apiRequest, XLPayLineOrderResponse.class);
			HgLogger.getInstance().info("lxs_dev", "【payLineOrder】"+"LineOrderLocalService->通知分销修改线路订单支付信息：返回结果"+JSON.toJSONString(xlPayLineOrderResponse));
			if(response!=null&&StringUtils.isNotBlank(xlPayLineOrderResponse.getResult())){
				if(!StringUtils.equals(String.valueOf(1), xlPayLineOrderResponse.getResult())){
					//创建失败。返回异常信息
					HgLogger.getInstance().info("lxs_dev","【payLineOrder】"+"通知分销修改线路订单支付信息失败");
					return false;
				}
			}
			HgLogger.getInstance().info("lxs_dev","【payLineOrder】"+"通知分销修改线路订单支付信息成功");
			lineOrderQO = new LineOrderQO();
			lineOrderQO.setDealerOrderNo(dealerOrderNo);
			HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"订单号：" + dealerOrderNo);
			final LineOrder myLineOrder = lineOrderDAO.queryUnique(lineOrderQO);
			final String dealerOrderNO= command.getDealerOrderNo();
			OrderNotice orderNotice = orderNoticeDao.queryUnique(new OrderNoticeQO());
			String string ="";
			if(orderNotice!=null&&orderNotice.getPhonoNumber()!=null&&StringUtils.isNotBlank(orderNotice.getPhonoNumber())){
				string = orderNotice.getPhonoNumber();
			}
			final String adminPhone  = string; 
			if(!StringUtils.equals(command.getRequestType(), AlipayCommand.LOCAL)){
				new Thread(){
					public void run(){
						if(myLineOrder!=null&&myLineOrder.getLinkInfo()!=null&&myLineOrder.getLinkInfo().getLinkMobile()!=null){
							try {
								if(dealerOrderNO.split("_").length==1){
									//定金
									String text ="";
									text = "【票量旅游】亲爱的用户，您的订单"+dealerOrderNo+"已经成功提交预约，客服将在2小时内与您进行电话确认，请您耐心等待。客服专线0571-28280815。";
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，电话"+myLineOrder.getLinkInfo().getLinkMobile());
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，内容"+text);
									smsUtils.sendSms(myLineOrder.getLinkInfo().getLinkMobile(),text);
									//--------给后台运营人员发短信-------
									if(adminPhone!=null&&StringUtils.isNotBlank(adminPhone)){
										text = "【票量旅游】随心游有订单"+dealerOrderNo+"生成，请及时登录后台查看，并在1小时内处理进行处理。";
										HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，电话"+adminPhone);
										HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，内容"+text);
										smsUtils.sendSms(adminPhone,text);
									}
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容【成功】");
								}else{
									//尾款
									String text ="";
									text = "【票量旅游】亲爱的用户，您的订单"+dealerOrderNo+"已生效，请您在预订时间内出行，祝您旅途愉快！客服专线0571-28280815。";
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，电话"+myLineOrder.getLinkInfo().getLinkMobile());
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容，内容"+text);
									smsUtils.sendSms(myLineOrder.getLinkInfo().getLinkMobile(),text);
									HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发送短信内容【成功】");
								}
							} catch (Exception e) {
								HgLogger.getInstance().info("lxs_dev","【支付成功收到支付宝通知】"+"【短信通知】"+"发生异常" +HgLogger.getStackTrace(e));
							}
						}
					}
				}.start();
			}
			return true;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:33:19
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean cancelLineOrder(CancleLineOrderCommand command) {
		HgLogger.getInstance().info("lxs_dev", "【cancelLineOrder】"+"后台取消线路订单："+JSON.toJSONString(command));
		if(command==null||StringUtils.isEmpty(command.getLineOrderID())||command.getTravelerIDs()==null){
			return false;
		}
		HgLogger.getInstance().info("lxs_dev", "【cancelLineOrder】"+"开始修改游客订单状态和支付状态。。。。。。。。。");
		try{
			LineOrder lineOrder=lineOrderDAO.get(command.getLineOrderID());
			//根据分销传过来的订单状态，重新设置本地订单状态
			String[] travelerIDs =command.getTravelerIDs();
			if(travelerIDs!=null){
				List<lxs.pojo.dto.line.LineOrderTravelerDTO> travelerList=new ArrayList<lxs.pojo.dto.line.LineOrderTravelerDTO>();
				//修改订单游客状态为"取消订单"
				int payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_REFUND);
				for(String travelID:travelerIDs){
					for(LineOrderTraveler travle:lineOrder.getTravelers()){
						if(travle.getId().equals(travelID)){
							travle.getLineOrderStatus().changeOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL),travle.getLineOrderStatus().getPayStatus());
							travelerList.add(BeanMapperUtils.getMapper().map(travle, lxs.pojo.dto.line.LineOrderTravelerDTO.class));
							//支付状态这里暂传游客原来状态。在localservice里分别更改游客状态时处理（支付状态若为 "待支付订金"，支付状态不变；否则，支付状态修改为"等待退款"）
							payStatus=travle.getLineOrderStatus().getPayStatus();
						}
					}
				}
				UpdateLineOrderStatusCommand updateCmd=new UpdateLineOrderStatusCommand();
				updateCmd.setDealerOrderCode(command.getLineOrderID());
				updateCmd.setTravelerList(travelerList);
				updateCmd.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL));
				updateCmd.setPayStatus(payStatus);
				HgLogger.getInstance().info("lxs_dev", "【cancelLineOrder】"+"修改游客订单状态和支付状态："+JSON.toJSONString(updateCmd));
				return this.updateOrderStatus(updateCmd);
			}else{
				return false;
			}
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【cancelLineOrder】"+"异常，"+HgLogger.getStackTrace(e));
		}
		return false;
	}

	/**
	 * 
	 * @方法功能说明：修改订单状态并通知分销
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:33:34
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws LineException
	 * @return:boolean
	 * @throws
	 */
	public boolean updateOrderStatus(UpdateLineOrderStatusCommand command)throws LineException{
		HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"线路订单状态更新  开始。。。。。。。。。。。"+JSON.toJSONString(command));
		if(command==null||command.getOrderStatus()==null){
			HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"线路订单状态更新:command参数为空");
			return false;
		}
		LineOrder lineOrder = getDao().get(command.getDealerOrderCode());
		if(lineOrder==null){
			HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"线路订单状态更新：（订单不存在）"+JSON.toJSONString(command));
			return false;
		}
		List<LineOrderTraveler> travelers=null;
		if(command.getTravelerList()!=null&&command.getTravelerList().size()>0){
			travelers=BeanMapperUtils.getMapper().mapAsList(command.getTravelerList(), LineOrderTraveler.class);
			HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"LineOrderLocalService->updateOrderStatus线路订单状态更新：（针对订单中部分游客）"+JSON.toJSONString(command));
		}else{
			travelers=	new ArrayList<LineOrderTraveler>(lineOrder.getTravelers());
			HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"LineOrderLocalService->updateOrderStatus线路订单状态更新：（针对订单中所有游客）"+JSON.toJSONString(command));
		}
		//更新本地线路游客订单状态
		if(travelers==null){
			HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"LineOrderLocalService->updateOrderStatus线路订单更新：游客为空"+JSON.toJSONString(command));
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
		//--------------------------------------------开始更改订单表中，订单状态和支付状态----------------------------------------------------------------------
				LineOrder lineOrder2=getDao().get(command.getDealerOrderCode());
				LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
				lineOrderTravelerQO.setLineOrderId(lineOrder2.getId());
				List<LineOrderTraveler> lineOrderTravelers = lineOrderTravelerDAO.queryList(lineOrderTravelerQO);
				String lineOrderStautes="";
				String payOrderStautes="";
				for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
					lineOrderStautes=lineOrderStautes+lineOrderTraveler.getLineOrderStatus().getOrderStatus()+",";
					payOrderStautes=payOrderStautes+lineOrderTraveler.getLineOrderStatus().getPayStatus()+",";
				}
				lineOrder2.setOrderStautes(lineOrderStautes);
				lineOrder2.setPayStautes(payOrderStautes);
				lineOrderDAO.update(lineOrder2);
				//--------------------------------------------结束更改订单表中，订单状态和支付状态----------------------------------------------------------------------
		//通知分销修改订单状态
		XLModifyLineOrderTravelerApiCommand apiCommand=new XLModifyLineOrderTravelerApiCommand();
		apiCommand.setDealerOrderNo(lineOrder.getBaseInfo().getDealerOrderNo());
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
		HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"LineOrderLocalService->通知分销修改线路订单游客订单状态："+JSON.toJSONString(apiCommand));
		ApiRequest apiRequest = new ApiRequest("XLModifyLineOrderTraveler",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		XLModifyLineOrderTravelerResponse response = slfxApiClient.send(apiRequest, XLModifyLineOrderTravelerResponse.class);
		HgLogger.getInstance().info("lxs_dev", "【updateOrderStatus】"+"LineOrderLocalService->通知分销修改线路订单游客状态：返回结果"+JSON.toJSONString(response));
		if(response!=null&&StringUtils.isNotBlank(response.getResult())){
			if("-1".equals(response.getResult())){
				//创建失败。返回异常信息
				throw new LineException(LineException.UPDATE_ORDER_STATUS, response.getMessage());
			}
		}
		return true;
	}

	/**
	 * 
	 * @方法功能说明：修改订单状态
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:35:38
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws LineException
	 * @return:boolean
	 * @throws
	 */
	public boolean updateLineOrderStatus(XLUpdateOrderStatusMessageApiCommand command)
			throws LineException {
		if(command==null||StringUtils.isEmpty(command.getLineOrderID())){
			return false;
		}
		HgLogger.getInstance().info("lxs_dev", "【updateLineOrderStatus】"+"线路订单更新："+JSON.toJSONString(command));
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setDealerOrderNo(command.getLineOrderID());
		LineOrder lineOrder=getDao().queryUnique(lineOrderQO);
		if(lineOrder!=null){
			//根据分销传过来的订单状态，重新设置本地订单状态
			Set< slfx.xl.pojo.dto.order.LineOrderTravelerDTO> travelers =command.getTravelers();
			for( slfx.xl.pojo.dto.order.LineOrderTravelerDTO travelDto:travelers){
				for(LineOrderTraveler travle:lineOrder.getTravelers()){
					if(travle.getId().equals(travelDto.getId())){
						travle.getLineOrderStatus().changeOrderStatus(travelDto.getXlOrderStatus().getStatus(),travelDto.getXlOrderStatus().getPayStatus());
					}
				}
			}
			getDao().update(lineOrder);
			//--------------------------------------------开始更改订单表中，订单状态和支付状态----------------------------------------------------------------------
			LineOrderQO lineOrderQO2 = new LineOrderQO();
			lineOrderQO2.setDealerOrderNo(command.getLineOrderID());
			LineOrder lineOrder2=getDao().queryUnique(lineOrderQO2);
			LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
			lineOrderTravelerQO.setLineOrderId(lineOrder2.getId());
			List<LineOrderTraveler> lineOrderTravelers = lineOrderTravelerDAO.queryList(lineOrderTravelerQO);
			String lineOrderStautes="";
			String payOrderStautes="";
			for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
				lineOrderStautes=lineOrderStautes+lineOrderTraveler.getLineOrderStatus().getOrderStatus()+",";
				payOrderStautes=payOrderStautes+lineOrderTraveler.getLineOrderStatus().getPayStatus()+",";
			}
			lineOrder2.setOrderStautes(lineOrderStautes);
			lineOrder2.setPayStautes(payOrderStautes);
			lineOrderDAO.update(lineOrder2);
			//--------------------------------------------结束更改订单表中，订单状态和支付状态----------------------------------------------------------------------
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：创建订单，为了不干扰分销创建订单 增加payment参数
	 * @修改者名字：cangs
	 * @修改时间：2015年5月8日上午10:28:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param payment
	 * @参数：@return
	 * @参数：@throws LineException
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO createLineOrder(CreateLineOrderCommand command,String payment)  {
		LineOrderDTO lineOrderDto =new LineOrderDTO();
		HgLogger.getInstance().info("lxs_dev","【createLineOrder】"+ "开始创建旅行社线路订单："+JSON.toJSONString(command));
		//生成经销商订单号
		String lineDealerOrderId = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, command.getSource());
		//查询线路快照
		Line line=lineService.get(command.getLineID());
		if(line==null){
			return null;
		}
		LineSnapshot lineSnapshot=lineSnapshotDAO.get(line.getLineSnapshotId());
		if(lineSnapshot==null){
			return null;
		}
		XLCreateLineOrderApiCommand apiCommand = EntityConvertUtils.convertDtoToEntity(command, XLCreateLineOrderApiCommand.class);
		//补充一些没有映射到的数据
		apiCommand.setLineDealerID(SysProperties.getInstance().get("slfx_secretKey"));
		apiCommand.getBaseInfo().setDealerOrderNo(lineDealerOrderId);
		LineSnapshotDTO lineSnapshotDTO = new LineSnapshotDTO();
		lineSnapshotDTO.setId(lineSnapshot.getId());
		apiCommand.setLineSnapshot(lineSnapshotDTO);
		HgLogger.getInstance().info("lxs_dev", "【createLineOrder】"+"通知分销下单："+JSON.toJSONString(apiCommand));	
		ApiRequest apiRequest = new ApiRequest("XLCreateLineOrder",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiCommand);
		XLCreateLineOrderResponse response = slfxApiClient.send(apiRequest, XLCreateLineOrderResponse.class);
		HgLogger.getInstance().info("lxs_dev", "【createLineOrder】"+"通知分销下单，返回结果："+JSON.toJSONString(response));
		if(response!=null&&StringUtils.isNotBlank(response.getResult())){
			if(StringUtils.equals("-1",response.getResult())){
				//创建失败。返回异常信息
				return null;
			}else{
				//分销返回请求成功，创建本地订单（暂时没有数据）
				//补充command信息:(创建日期；)
				command.getBaseInfo().setCreateDate(new Date());
				command.getBaseInfo().setDealerOrderNo(lineDealerOrderId);
				LineOrder lineOrder = new LineOrder();
				lineOrder.create(command);
				lineOrder.setLineSnapshot(lineSnapshot);
				lineOrder.setPayment(payment);
				String lineOrderStautes="";
				String payOrderStautes="";
				List<LineOrderTraveler> travelers =new ArrayList<LineOrderTraveler>();
				for(LineOrderTravelerDTO l:response.getLineOrderDTO().getTravelers()){
					LineOrderTraveler lineOrderTraveler = new LineOrderTraveler();
					lineOrderTraveler.setId(l.getId());
					lineOrderTraveler.setIdNo(l.getIdNo());
					lineOrderTraveler.setMobile(l.getMobile());
					lineOrderTraveler.setName(l.getName());
					lineOrderTraveler.setSingleBargainMoney(l.getSingleBargainMoney());
					lineOrderTraveler.setSingleSalePrice(l.getSingleSalePrice());
					lineOrderTraveler.setType(l.getType());
					lineOrderTraveler.setIdType(l.getIdType());
					travelers.add(lineOrderTraveler);
				}
				for(LineOrderTraveler traveler : travelers){
					//用分销返回的ID
					//traveler.setId(UUIDGenerator.getUUID());
					traveler.setLineOrder(lineOrder);
					traveler.setLineOrderStatus(lineOrder.getStatus());
					lineOrderStautes=lineOrderStautes+lineOrder.getStatus().getOrderStatus()+",";
					payOrderStautes=payOrderStautes+lineOrder.getStatus().getPayStatus()+",";
				}
				lineOrder.setOrderStautes(lineOrderStautes);
				lineOrder.setPayStautes(payOrderStautes);
				lineOrder.setIsBuyInsurance(command.getIsBuyInsurance());
				lineOrder.setInsurancePrice(command.getInsurancePrice());
				lineOrder.setHaveSendedSMS(LineOrder.NO);
				lineOrderDAO.save(lineOrder);
				//持久化游客
				this.lineOrderTravelerDAO.saveList(travelers);
				//将分销线路订单DTO转换为本地DTO
				lineOrderDto.setTravelerList(command.getTravelerList());
				lineOrderDto.setId(lineOrder.getId());
				LineOrderStatusDTO lineOrderStatusDTO = new LineOrderStatusDTO();
				lineOrderStatusDTO.setOrderStatus(lineOrder.getStatus().getOrderStatus());
				lineOrderStatusDTO.setPayStatus(lineOrder.getStatus().getPayStatus());
				lineOrderDto.setStatus(lineOrderStatusDTO);
				LineOrderBaseInfoDTO lineOrderBaseInfoDTO = new LineOrderBaseInfoDTO();
				lineOrderBaseInfoDTO.setBargainMoney(lineOrder.getBaseInfo().getBargainMoney());
				lineOrderBaseInfoDTO.setSalePrice(lineOrder.getBaseInfo().getSalePrice());
				lineOrderBaseInfoDTO.setDealerOrderNo(lineOrder.getBaseInfo().getDealerOrderNo());
				lineOrderDto.setBaseInfo(lineOrderBaseInfoDTO);
			}
		}
		//创建下一个序列号
		this.setNextOrderSequence();
		HgLogger.getInstance().info("lxs_dev", "【createLineOrder】"+"旅行社创建线路订单结果"+JSON.toJSONString(lineOrderDto));
		return lineOrderDto;
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
			String value = jedis.get("line_sequence");
			String date = jedis.get("line_sequence_date");
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(String.valueOf(Calendar.getInstance()
							.getTime().getTime()))) {
				value = "0";
			}

			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();

			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}

			jedis.set("line_sequence", String.valueOf(value));
			jedis.set("line_sequence_date",
					String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}

}
