package slfx.jp.app.service.spi;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import slfx.api.util.HttpUtil;
import slfx.jp.app.service.local.JPFlightTicketLocalService;
import slfx.jp.app.service.local.JPOrderLocalService;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.domain.model.order.JPOrderStatus;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.spi.inter.UpdateJPOrderStatusService;
import slfx.yg.open.inter.YGFlightService;
@Service("updateJPOrderStatusService")
public class UpdateJPOrderStatusServiceImpl implements
		UpdateJPOrderStatusService {
	@Resource
	private JPOrderLocalService jpOrderLocalService;
	@Resource
	private JPFlightTicketLocalService jpFlightTicketLocalService;
	
	/**
	 * 易购的机票服务类
	 */
	@Autowired
	private YGFlightService ygFlightService;
	
	public void run() {
		HgLogger.getInstance().debug("qiuxianxiang", "UpdateJPOrderStatusTask[更新机票订单状态定时任务开始执行]->时间："+new java.util.Date());
		try {
			// 查询所有未支付的机票订单
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_PAY_SUCC));// 已支付
			qo.setType(JPOrderStatusConstant.COMMON_TYPE);// 正常订单
			List<JPOrder> orderList = jpOrderLocalService.queryList(qo);
			if (orderList == null) 
				return;
			
			for (JPOrder jpOrder : orderList) {

				if (jpOrder != null && jpOrder.getYgOrderNo() != null
						&& jpOrder.getPassangerList().size() > 0) {
					Set<Passenger> passengers = jpOrder.getPassangerList();
					//如果有票号，就不需要同步易购数据
					if (null != passengers) {
						Iterator<Passenger> iterator = passengers.iterator();
						boolean boo = true;
						while (iterator.hasNext()) {
							Passenger passenger = iterator.next();
							FlightTicket flightTicket = passenger.getTicket();
							if (StringUtils.isBlank(flightTicket.getTicketNo())) {
								boo = false;
							}
						}
						if (boo) {
							continue;
						}
						
					}
						
					String ygOrderNo = jpOrder.getYgOrderNo();
					YGQueryOrderDTO ygOrder =  ygFlightService.queryOrder(ygOrderNo);
					Map<String,String> ticket = ygOrder.getTicket();//身份证号码和机票的key-value对应
//					ticket = new HashMap<String,String>();
//					ticket.put("421081198612121111", "xxxxx12312312");
					//更新分销订单
					injectTicktNo(jpOrder,ticket);
					
					//订单状态同步给商城
					try{
						//同步给商城
						String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/ticket/notify";
						JPOrderCommand command = new JPOrderCommand();
						command.setDealerOrderCode(jpOrder.getDealerOrderCode());
						Set<String> tktNoSet = ticket.keySet();
						String[] tktNos = new String[ticket.size()];
						int index = 0;
						for (String tktNo : tktNoSet) {
							tktNos[index] = tktNo;
							index++;
						}
						//command.setTktNo(new String[]{"731239153333"});
						command.setTktNo(tktNos);
						//商城通知地址
						command.setNotifyUrl(notifyUrl);
						command.setFlag("Y");
						command.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_SUCC));
						
						HttpUtil.reqForPost(notifyUrl,"msg=" + JSON.toJSONString(command), 60000);
						//通知商城更改订单状态
						command.setDealerOrderCode(jpOrder.getDealerOrderCode());
						HttpUtil.reqForPost(command.getNotifyUrl(),"msg=" + JSON.toJSONString(command), 60000);
						
					}catch(Exception e){
						HgLogger.getInstance().error("qiuxianxiang","UpdateJPOrderStatusTask-->定时任务同步机票订单状态给商城失败："+JSONObject.toJSONString(e));
					}

				}
			}

		} catch (Exception e) {
			HgLogger.getInstance().error("qiuxianxiang","更新机票订单状态失败："+JSONObject.toJSONString(e));
		}
		HgLogger.getInstance().debug("qiuxianxiang", "UpdateJPOrderStatusTask[更新机票订单状态定时任务结束执行]->时间："+new java.util.Date());
	}
	
	
	private void injectTicktNo(JPOrder jpOrder,Map<String,String> ticketMap) {
		if (null == jpOrder) {
			return;
		}
		try {
			JPOrderStatus orderStatus = new JPOrderStatus();
			orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_SUCC));
			jpOrder.setOrderStatus(orderStatus);
			jpOrderLocalService.update(jpOrder);
			
			Set<Passenger> passengers = jpOrder.getPassangerList();
			if (passengers.size() == ticketMap.size()) {//机票数量等于乘客数量
				Iterator<Passenger> iterator = passengers.iterator();
				while (iterator.hasNext()) {
					Passenger passenger = iterator.next();
					String cardNo = passenger.getCardNo();
					String ticketNo = ticketMap.get(cardNo);
					ticketMap.remove(cardNo);
					FlightTicket flightTicket = passenger.getTicket();
					if (StringUtils.isBlank(flightTicket.getTicketNo())) {
						flightTicket.setTicketNo(ticketNo);
						jpFlightTicketLocalService.update(flightTicket);
					}
				}
			}
			
			assert ticketMap.isEmpty():"有"+ticketMap.size()+"张机票号并为更新成功";
		} catch (Exception e) {
			HgLogger.getInstance().error("qiuxianxiang", "UpdateJPOrderStatusTask->injectTicktNo->exception:" + HgLogger.getStackTrace(e));
		}
	}
	
	@Override
	public void updateJPOrderStatusTask() {
		run();
	}

}
