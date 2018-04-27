package hg.dzpw.admin.component.job;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import hg.common.component.RedisLock;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.log.util.HgLogger;
import hg.system.common.quartz.QuartzTask;

/**
 * @类功能说明：订单状态更新通知推送
 * @公司名称：浙江汇购科技有限公司
 * @作者：yangkang
 * @创建时间：2015-12-28上午10:56:00
 */
@Component
public class PushOrderNoTask extends QuartzTask {
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	
	@Autowired
	private DealerLocalService dealerLocalService;

	private RedisLock lock = new RedisLock("PushOrderNoTask");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public PushOrderNoTask(){
		setGroupName("电子票务");
		setTaskName("订单状态更新通知待推送");
		// 每10分启动
		setCronExpression("0 0/10 * * * ?");
	}
	
	
	@Override
	public void execute() {
		
		if (lock.tryLock()) {
			
			Date now = new Date();
			List<String> list = new ArrayList<String>();
			
			try {
				HgLogger.getInstance().info("yangk", "订单状态更新通知待推送---开始");
				// 获取待推送的订单号
				Map<String, String> map = ticketOrderManager.getPushOrder();
				
				if (map!=null){
					Iterator it = map.keySet().iterator();
					// 遍历记录更新的时间
					while(it.hasNext()){
							// key为订单号
							String key = (String)it.next();
							// 获取订单所属经销商代码和 放入时间
							String dealerKey = (String)JSON.parseObject(map.get(key)).get("dealerKey");
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis((Long)JSON.parseObject(map.get(key)).get("time"));
							Date time = c.getTime();
							
							// 时间差 --毫秒
							Long m = now.getTime() - time.getTime();
							// 10分钟内 则推送
							if (m>=0 && m<=600000){
								DealerApiEventPublisher.publish(new PublishEventRequest(
																	PublishEventRequest.EVENT_TICKET_ORDER_STATUS_CHANGE, key, dealerKey));
								// 待删除
								list.add(key);
							}
					}
				}
				
				ticketOrderManager.delPushOrderRecord(list, now);
				
				HgLogger.getInstance().info("yangk", "订单状态更新通知待推送---结束");
			} finally {
				lock.unlock();
			}
		}else {
			HgLogger.getInstance().info("yangk", "订单状态更新通知待推送已有其他实例在执行");
		}
	}
	
}
