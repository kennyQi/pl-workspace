package lxs.app.service.mp;

import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDatePriceDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.app.dao.mp.TicketOrderDAO;
import lxs.app.dao.mp.TouristDAO;
import lxs.app.util.alipay.refund.AlipayService;
import lxs.app.util.line.OrderUtil;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.command.mp.CreateTicketOrderCommand;
import lxs.pojo.dto.mp.TouristDTO;
import lxs.pojo.exception.mp.CreateTicketOrderException;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.qo.mp.TouristQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;

@Transactional
@Service
public class AppService {

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private DZPWService dzpwService;
	@Autowired
	private TicketOrderDAO ticketOrderDAO;
	@Autowired
	private TouristDAO touristDAO;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	/**
	 * 
	 * @方法功能说明：创建订单 
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:13:18
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws CreateTicketOrderException
	 * @return:String
	 * @throws
	 */
	public String createTicketOrder(CreateTicketOrderCommand command) throws CreateTicketOrderException{
		HgLogger.getInstance().info("lxs_dev", "进入【AppService】【createTicketOrder】");
		HgLogger.getInstance().info("lxs_dev", "【AppService】【command】"+JSON.toJSONString(command));
		//设置本地订单号
		String orderNO = "MP"+OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 4);
		HgLogger.getInstance().info("lxs_dev", "【AppService】【orderNO】"+orderNO);
		/**                     1创建远程订单                           */
		hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand dzpwCreateTicketOrderCommand = new hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand();
		List<TouristDTO> touristDTOs = command.getTourists();
		List<hg.dzpw.dealer.client.dto.tourist.TouristDTO> dzpwTouristDTOs = new ArrayList<hg.dzpw.dealer.client.dto.tourist.TouristDTO>();
		for (TouristDTO touristDTO : touristDTOs) {
			hg.dzpw.dealer.client.dto.tourist.TouristDTO dzpwTouristDTO =JSON.parseObject(JSON.toJSONString(touristDTO),hg.dzpw.dealer.client.dto.tourist.TouristDTO.class); 
			dzpwTouristDTO.setAge(null);
			dzpwTouristDTO.setBirthday(null);
			dzpwTouristDTOs.add(dzpwTouristDTO);
		}
		//游客信息
		dzpwCreateTicketOrderCommand.setTourists(dzpwTouristDTOs);
		//预订人
		dzpwCreateTicketOrderCommand.setBookMan(command.getBookMan());
		//预订人预留手机
		dzpwCreateTicketOrderCommand.setBookManMobile(command.getBookManMobile());
		//预订人预留支付宝账号
		if(StringUtils.isNotBlank(command.getBookManAliPayAccount())){
			dzpwCreateTicketOrderCommand.setBookManAliPayAccount(command.getBookManAliPayAccount());
		}
		//购买数量
		dzpwCreateTicketOrderCommand.setBuyNum(command.getTourists().size());
		//获取电子票务远程政策信息
		String ticketPolicyID = command.getTicketPolicyId();
		String[] ticketPolicyIds = {ticketPolicyID};
		TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
		ticketPolicyQO.setTicketPolicyIds(ticketPolicyIds);
		ticketPolicyQO.setCalendarFetch(true);
		TicketPolicyResponse ticketPolicyResponse = new TicketPolicyResponse();
		try{
			ticketPolicyResponse = dzpwService.queryTicketPolicy(ticketPolicyQO);
		}catch(DZPWException dzpwException){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(dzpwException));
			throw new CreateTicketOrderException(dzpwException.getMessage());
		}
		TicketPolicyDTO ticketPolicyDTO = ticketPolicyResponse.getTicketPolicies().get(0);
		dzpwCreateTicketOrderCommand.setTicketPolicyId(ticketPolicyID);
		dzpwCreateTicketOrderCommand.setTicketPolicyVersion(ticketPolicyDTO.getVersion());
		//获取出行日期价格
		Double price = 0.0;
		//获取价格
		if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_SINGLE){
			//如果是单票 查询价格日历
			List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs = ticketPolicyDTO.getCalendar().getPrices();
			for (TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO : ticketPolicyDatePriceDTOs) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				String travelDateStr = simpleDateFormat.format(command.getTravelDate());
				if(StringUtils.equals(travelDateStr,ticketPolicyDatePriceDTO.getDate())){
					price=ticketPolicyDatePriceDTO.getPrice();
					break;
				}
			}
			if(price==0.0){
				throw(new CreateTicketOrderException(CreateTicketOrderException.GET_PRICE_FAIL));
			}else{
				dzpwCreateTicketOrderCommand.setPrice(price*command.getTourists().size());
				dzpwCreateTicketOrderCommand.setTravelDate(command.getTravelDate());
			}
		}else{
			//如果是联票 直接从政策里获取
			dzpwCreateTicketOrderCommand.setPrice(ticketPolicyDTO.getBaseInfo().getPlayPrice()*command.getTourists().size());
		}
		dzpwCreateTicketOrderCommand.setDealerOrderId(orderNO);
		CreateTicketOrderResponse createTicketOrderResponse = new CreateTicketOrderResponse();
		try{
			createTicketOrderResponse = dzpwService.createTicketOrder(dzpwCreateTicketOrderCommand);
		}catch(DZPWException dzpwException){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(dzpwException));
			throw(new CreateTicketOrderException(dzpwException.getMessage()));
		}
		
		/**                     2 创建本地订单                           */
		//设置本地订单ID
		String orderID = createTicketOrderResponse.getTicketOrder().getId();
		try{
			//*******创建订单
			TicketOrder ticketOrder = new TicketOrder();
			ticketOrder.setUserID(command.getUserID());
			ticketOrder.setId(orderID);
			ticketOrder.setOrderNO(orderNO);
			ticketOrder.setScenicSpotID(ticketPolicyDTO.getScenicSpotId());
			ticketOrder.setTicketPolicyId(command.getTicketPolicyId());
			ticketOrder.setTicketPolicyVersion(ticketPolicyDTO.getVersion());
			ticketOrder.setType(ticketPolicyDTO.getType());
			ticketOrder.setName(ticketPolicyDTO.getBaseInfo().getName());
			ticketOrder.setScenicSpotNameStr(ticketPolicyDTO.getBaseInfo().getScenicSpotNameStr());
			ticketOrder.setNotice(ticketPolicyDTO.getBaseInfo().getNotice());
			ticketOrder.setReturnRule(ticketPolicyDTO.getSellInfo().getReturnRule());
			if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_SINGLE){
				ticketOrder.setTravelDate(command.getTravelDate());
				ticketOrder.setPlayPrice(price);
				ticketOrder.setPlayPriceSUM(command.getTourists().size()*price);
			}else{
				ticketOrder.setValidDays(ticketPolicyDTO.getUseInfo().getValidDays());
				ticketOrder.setPlayPrice(ticketPolicyDTO.getBaseInfo().getPlayPrice());
				ticketOrder.setPlayPriceSUM(command.getTourists().size()*ticketPolicyDTO.getBaseInfo().getPlayPrice());
			}
			ticketOrder.setBuyNum(command.getTourists().size());
			ticketOrder.setBookMan(command.getBookMan());
			ticketOrder.setBookManMobile(command.getBookManMobile());
			if(StringUtils.isNotBlank(command.getBookManAliPayAccount())){
				ticketOrder.setBookManAliPayAccount(command.getBookManAliPayAccount());
			}
			TicketOrderQO ticketOrderQO = new TicketOrderQO();
			ticketOrderQO.setOrderId(orderID);
			ticketOrderQO.setGroupTicketsFetch(true);
			ticketOrderQO.setSingleTicketsFetch(true);
			ticketOrderQO.setTouristFetch(true);
			TicketOrderResponse ticketOrderResponse = new TicketOrderResponse();
			try{
				ticketOrderResponse = dzpwService.queryTicketOrder(ticketOrderQO);
			}catch(DZPWException dzpwException){
				HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(dzpwException));
				throw new CreateTicketOrderException(dzpwException.getMessage());
			}
			TicketOrderDTO ticketOrderDTO = ticketOrderResponse.getTicketOrders().get(0);
			ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_PAY_WAIT);
			ticketOrder.setDZPWOrderSnapShot(JSON.toJSONString(ticketOrderDTO));
			ScenicSpot scenicSpot = scenicSpotDAO.get(ticketPolicyDTO.getScenicSpotId());
			if(scenicSpot!=null){
				ticketOrder.setScenicSpotSnapShot(JSON.toJSONString(scenicSpot));
			}
			ticketOrder.setTicketPolicySnapShot(JSON.toJSONString(ticketPolicyDTO));
			ticketOrder.setCreatDate(new Date());
			ticketOrder.setLocalPayStatus(TicketOrder.WAIT_TO_PAY);
			ticketOrder.localstatus(TicketOrder.ORDER_STATUS_PAY_WAIT, TicketOrder.WAIT_TO_PAY, Tourist.NOTHING);;
			ticketOrderDAO.save(ticketOrder);
			//*******创建游玩人
			for (TouristDTO touristDTO : touristDTOs) {
				List<GroupTicketDTO> groupTicketDTOs = ticketOrderResponse.getTicketOrders().get(0).getGroupTickets();
				Tourist tourist = JSON.parseObject(JSON.toJSONString(touristDTO), Tourist.class);
				tourist.setId(UUIDGenerator.getUUID());
				tourist.setOrderID(orderID);
				for (GroupTicketDTO groupTicketDTO : groupTicketDTOs) {
					if(StringUtils.equals(groupTicketDTO.getSingleTickets().get(0).getTourist().getIdNo(), touristDTO.getIdNo())){
						tourist.setCurrent(groupTicketDTO.getStatus().getCurrent());
						tourist.setTicketNo(groupTicketDTO.getTicketNo());
					}
				}
				tourist.setLocalStatus(Tourist.NOTHING);
				touristDAO.save(tourist);
			}
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			throw new CreateTicketOrderException(CreateTicketOrderException.CREAT_LOCAL_ORDER_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【AppService】【orderID】"+orderID);
		return orderID;
	}
	
	/**
	 * 
	 * @方法功能说明：退款
	 * @修改者名字：cangs
	 * @修改时间：2016年4月15日下午2:28:44
	 * @修改内容：
	 * @参数：@param tradeNO
	 * @参数：@param refundMoney
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	public String alipayRefund(String tradeNO,double refundMoney) throws Exception{

		HgLogger.getInstance().info("lxs_dev", "【AppService】【退款】【开始退款操作】");
		// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
		String batch_no = this.getBatchNo();
		// 退款请求时间
		String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
		// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
		String batch_num = "1";
		// 单笔数据集
		String detail_data = "";
		//计算出实际的退款金额
		Double tureNum = refundMoney;
		HgLogger.getInstance().info("lxs_dev", "【AppService】【退款】【请求】【退款金额】"+"--------------"+tureNum+"--------------");
		//单次批量退款最多1000笔
		StringBuilder sb = new StringBuilder();
		sb.append(tradeNO);
		sb.append("^");
//		sb.append(df.format(Math.abs(tureNum)));
		sb.append(tureNum);
		sb.append("^");
		sb.append("原路退款");
		detail_data = sb.toString();
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        HgLogger.getInstance().info("lxs_dev", "【AppService】【退款】"+ "退款单信息"+sParaTemp);
		String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
		return sHtmlText;
	}
	
	
	/**
	 * 
	 * @方法功能说明：收到退款通知
	 * @修改者名字：cangs
	 * @修改时间：2016年4月15日下午2:50:23
	 * @修改内容：
	 * @参数：@param result_details
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:boolean
	 * @throws
	 */
	public boolean refundSuccess(String result_details) throws Exception{
			if(StringUtils.isBlank(result_details))
				return false;
			String[] trades=result_details.split("#");
			for(String trade:trades){
				String str=trade.split("\\$")[0];//获得交易退款数据集
				String[] data=str.split("\\^");//按照 "原付款支付宝交易号^退款总金额^退款状态" 格式分割为数组
				String tradeno=data[0];
				String sum=data[1];
				String status=data[2];
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】付款支付宝交易号:"+tradeno);
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】退款总金额:"+sum);
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】退款状态"+status);
				lxs.pojo.qo.mp.TicketOrderQO ticketOrderQO = new lxs.pojo.qo.mp.TicketOrderQO();
				ticketOrderQO.setSerialNumber(tradeno);
				TicketOrder ticketOrder = ticketOrderDAO.queryUnique(ticketOrderQO);
				if(ticketOrder!=null){
					int localStatus  = ticketOrder.getLocalStatus();
					String localStatus_str = String.valueOf(localStatus);
					if(localStatus_str.endsWith("1")||localStatus_str.endsWith("3")){
						ticketOrder.setLocalStatus(localStatus+1);
						ticketOrderDAO.update(ticketOrder);
					}
					TouristQO touristQO = new TouristQO();
					touristQO.setOrderID(ticketOrder.getId());
					touristQO.setLocalStatus(localStatus);
					List<Tourist> tourists = touristDAO.queryList(touristQO);
					if(tourists!=null&&tourists.size()>0){
						if(Double.valueOf(sum)==ticketOrder.getPrice()){
							for (Tourist tourist : tourists) {
								tourist.setLocalStatus(tourist.getLocalStatus()+1);
								touristDAO.update(tourist);
							}
						}else{
							Tourist tourist = tourists.get(0);
							tourist.setLocalStatus(tourist.getLocalStatus()+1);
							touristDAO.update(tourist);
						}
					}else{
						throw new Exception();
					}
				}else{
					throw new Exception();
				}
			}
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
	public int getOrderSequence() {
		Jedis jedis = null;
		try {
	
			jedis = jedisPool.getResource();
			String value = jedis.get("suixinyou_menpiao_sequence");
			String date = jedis.get("suixinyou_menpiao_sequence_date");
//			HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【getOrderSequence】"+"value:"+JSON.toJSONString(value));
//			HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【getOrderSequence】"+"时间比较"+JSON.toJSONString(date)+String.valueOf(Calendar.getInstance().getTime().getTime()));
			if (StringUtils.isBlank(value)|| StringUtils.isBlank(date)||date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
//				HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【getOrderSequence】"+"重置value");
				value = "0";
			}
	
			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
//		HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"设置下一个流水号");
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();
	
			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}
	
//			HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"value:"+JSON.toJSONString(value));
//			HgLogger.getInstance().info("lxs_dev", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"Calendar.getInstance().getTime().getTime():"+String.valueOf(Calendar.getInstance().getTime().getTime()));
			jedis.set("lxs_MPOrder_sequence", String.valueOf(value));
			jedis.set("lxs_MPOrder_sequence_date",String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
	
	// 获取批次号
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		private String getBatchNo() {
			String refundDate = df.format(Calendar.getInstance().getTime());
			String sequence = String.format("%012d", this.getSequence());
			return refundDate + SysProperties.getInstance().get("alipay_prefix") + sequence;
		}
		
		// 获取给定格式的日期字符串
		private String getDateString(String format) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(Calendar.getInstance().getTime());
		}
		// 获取流水号
		public long getSequence() {
			Jedis jedis = null;
			String value = "1";
			try {
				jedis = jedisPool.getResource();
				value = jedis.get("zzpl_jp_tk_sequence");
				String date = jedis.get("zzpl_jp_tk_sequence_date");

				if (StringUtils.isBlank(value)
						|| StringUtils.isBlank(date)
						|| date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
					value = "1";
					HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法流水数字重置");
				}
				
				long v = Long.parseLong(value);
				v++;
				
				jedis.set("zzpl_jp_tk_sequence", String.valueOf(v));
				jedis.set("zzpl_jp_tk_sequence_date", String.valueOf(Calendar.getInstance().getTime().getTime()));
			} catch(RuntimeException e){
				HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法"+ "获取流水号异常"+HgLogger.getStackTrace(e));
			} finally {
				jedisPool.returnResource(jedis);
			}

			return Long.parseLong(value);
		}
}
