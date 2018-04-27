package hg.dzpw.app.component.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.service.local.AliPayTransferRecordLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.domain.model.dealer.DealerRefundNotifyRecord;
import hg.dzpw.domain.model.ticket.GroupTicket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-24上午9:27:21
 * @版本：
 */
@Component
public class DealerRefundNotifyCacheManager {
	private static final String DEALER_REFUND_RECORD="dealer_refund_record";
	
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private AliPayTransferRecordLocalService transferRecordLocalService;
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;

	/**
	 * 
	 * @描述： 根据经销商key分组，按票号存入json串
	 * @author: guotx 
	 * @version: 2016-3-24 上午11:03:10
	 */
	public void addRecord(DealerRefundNotifyRecord record){
		String jsonString=JSON.toJSONString(record);
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(DEALER_REFUND_RECORD+record.getDealerKey(), record.getTicketNo(), jsonString);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnResource(jedis);
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @描述： 返回指定经销商的申请记录map，key为票号
	 * @author: guotx 
	 * @version: 2016-3-24 上午11:27:43
	 */
	public Map<String, DealerRefundNotifyRecord> getRecordByDealer(String dealerKey){
		Jedis jedis=jedisPool.getResource();
		Map<String, DealerRefundNotifyRecord> refundMap= null;
		try {
			Map<String, String> records = jedis.hgetAll(DEALER_REFUND_RECORD+dealerKey);
			if (records.size()>0) {
				refundMap=new HashMap<String, DealerRefundNotifyRecord>();
				for (Entry<String, String> entry : records.entrySet()) {
					String ticketNo=entry.getKey();
					String jsonString=entry.getValue();
					DealerRefundNotifyRecord record=JSON.parseObject(jsonString, DealerRefundNotifyRecord.class);
					refundMap.put(ticketNo, record);
				}
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return refundMap;
	}
	
	/**
	 * 
	 * @描述： 获取通知记录中的经销商keys
	 * @author: guotx 
	 * @version: 2016-3-24 下午12:11:13
	 */
	public List<String> getAllDealerKeys(){
		Jedis jedis=jedisPool.getResource();
		List<String> dealerKeys = null;
		try {
			Set<String> keys = jedis.keys(DEALER_REFUND_RECORD+"*");
			if (keys.size()>0) {
				dealerKeys=new ArrayList<String>();
				for (String key : keys) {
					String dealerKey=key.substring(DEALER_REFUND_RECORD.length());
					dealerKeys.add(dealerKey);
				}
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return dealerKeys;
	}
	/**
	 * 
	 * @描述： 获取单条退款记录
	 * @author: guotx 
	 * @version: 2016-3-24 下午2:11:59
	 */
	public DealerRefundNotifyRecord get(String dealerKey,String ticketNo){
		Jedis jedis=jedisPool.getResource();
		DealerRefundNotifyRecord record;
		try {
			String jsonString = jedis.hget(DEALER_REFUND_RECORD+dealerKey, ticketNo);
			record = JSON.parseObject(jsonString,DealerRefundNotifyRecord.class);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return record;
	}
	/**
	 * 
	 * @描述： 删除记录
	 * @author: guotx 
	 * @version: 2016-3-24 下午2:19:46
	 */
	public void del(String dealerKey,String ticketNo){
		Jedis jedis=jedisPool.getResource();
		jedis.hdel(DEALER_REFUND_RECORD+dealerKey, ticketNo);
		jedisPool.returnResource(jedis);
	}
	/**
	 * 
	 * @描述： 通知不成功修改记录或是成功后删除
	 * @param dealerKey 经销商key
	 * @param ticketNo 票号,可能逗号分隔的多个，若是，必须是同一个经销商下的
	 * @param isSuccess 是否成功 
	 * @author: guotx 
	 * @version: 2016-3-24 下午2:00:57
	 */
	public void updateOrDel(String dealerKey,String ticketNo,boolean isSuccess){
//		String[] ticketNos = ticketNo.split(",");
		DealerRefundNotifyRecord dealerRefundNotifyRecord = get(dealerKey, ticketNo);
		if (isSuccess || dealerRefundNotifyRecord.getHasNotifyNum()==2) {
			del(dealerKey, ticketNo);
			//更新退款记录通知状态
			GroupTicketQo groupTicketQo=new GroupTicketQo();
			groupTicketQo.setTicketNo(ticketNo);
			GroupTicket groupTicket = groupTicketLocalService.queryUnique(groupTicketQo);
			if (groupTicket!=null) {
				transferRecordLocalService.updateRecordPublicStatus(groupTicket.getId(),isSuccess);
			}
		}else {
			//已经通知次数
			int num=dealerRefundNotifyRecord.getHasNotifyNum()+1;
			dealerRefundNotifyRecord.setHasNotifyNum(num);
			switch (num) {
			case 1:
				Calendar calendar=Calendar.getInstance();
				calendar.add(Calendar.MINUTE, 30);
				dealerRefundNotifyRecord.setNotifyDate(calendar.getTime());
				break;
			case 2:
				Calendar calendar2=Calendar.getInstance();
				calendar2.add(Calendar.MINUTE, 60);
				dealerRefundNotifyRecord.setNotifyDate(calendar2.getTime());
				break;
			default:
				break;
			}
			//修改
			addRecord(dealerRefundNotifyRecord);
		}
	}
}
