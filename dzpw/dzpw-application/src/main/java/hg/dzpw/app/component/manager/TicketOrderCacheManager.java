package hg.dzpw.app.component.manager;

import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 订单号流水号生成器
 * 
 * @author zzx
 */
@Component
public class TicketOrderCacheManager {
	@Autowired
	private JedisPool jedisPool;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 剩余票数HASH(key:ticketPolicyId)
	 */
	public final static String TICKET_ORDER_REMAINING_QTY_HASH = "ticket_order_remaining_qty_hash";
	
	/**
	 * 已售票数HASH(key:ticketPolicyId)
	 */
	public final static String TICKET_ORDER_SOLD_QTY_HASH = "ticket_order_sold_qty_hash";
	
	/**DZPW票号HASH*/
	public final static String DZPW_TICKET_NO_HASH = "DZPW_TICKET_NO";

	public static String TICKET_ORDER_NO_TYPE = "PL";

	private String getDateStr(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * @方法功能说明：1.4版本之前的订单生成规则
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-15下午4:32:26
	 * @参数：@param type
	 * @参数：@return
	 * @return:String
	 */
	public String getTicketOrderNO(String type) {
		String dateStr = getDateStr(new Date());
		long id = getGeneratorLongId(type, dateStr);
		return type + dateStr + String.format("%1$05d", id);
	}
	
	public String getTicketOrderNO(){
		long id = getGeneratorLongId();
		return String.format("%1$010d", id);//格式成10位数字 前面补零
	}

	/**
	 * 生成下一个订单号
	 * 长度：16
	 * 格式：(订单类型)+8(yyMMdd)+5(每天从1开始的序列)
	 * 事例：2014年12月12日第一次生成订单号号就是
	 * 		PL2014121200001
	 * @return
	 */
	private Long getGeneratorLongId(String type, String date) {
		Jedis jedis = jedisPool.getResource();
		String key = type + date;
		try {
			long id = jedis.incr(key);
			// 失效时间设置为1天
			jedis.expire(key, 24 * 3600);
			jedisPool.returnResource(jedis);
			return id;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	
	private Long getGeneratorLongId() {
		Jedis jedis = jedisPool.getResource();
		try {
			long id = jedis.incr("DZPW");
			// 失效时间设置为2年
			jedis.expire("DZPW", 730*24*3600);
			jedisPool.returnResource(jedis);
			return id;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	
	/**
	 * @方法功能说明： 已售数量
	 * @修改者名字：zzx
	 * @修改时间：2014-12-16下午5:45:24
	 * @修改内容：
	 * @参数：@param  key:ticketPolicyId门票策略Id
	 * @参数：@param  ticketOrderSize:订单中的票数量
	 * @参数：@return
	 * @return:Jedis
	 * @throws
	 */
	public Integer getSoldQty(String ticketPolicyId, Integer soldQty) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long newSoldQty = jedis.hincrBy(TICKET_ORDER_SOLD_QTY_HASH, ticketPolicyId, soldQty);
			jedisPool.returnResource(jedis);
			return newSoldQty.intValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	/**
	 * @方法功能说明： 已售数量返还
	 * @修改者名字：zzx
	 * @修改时间：2014-12-16下午5:45:24
	 * @修改内容：
	 * @参数：@param  key:ticketPolicyId门票策略Id
	 * @参数：@param  ticketOrderSize:订单中的票数量
	 * @参数：@return
	 * @return:Jedis
	 * @throws
	 */
	public Integer returnSoldQty(String ticketPolicyId, Integer soldQty) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long newSoldQty = jedis.hincrBy(TICKET_ORDER_SOLD_QTY_HASH, ticketPolicyId, -soldQty);
			jedisPool.returnResource(jedis);
			if (newSoldQty < 0)
				return 0;
			return newSoldQty.intValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	/**
	 * @方法功能说明： 剩余数量
	 * @修改者名字：zzx
	 * @修改时间：2014-12-16下午5:45:24
	 * @参数：@param  key:ticketPolicyId门票策略Id
	 * @参数：@param  ticketOrderSize:订单中的票数量
	 * @参数：@param  remainingQty:库存（如果缓存中没有该门票库存则设置的数量）
	 * @参数：@return
	 * @return:Jedis
	 */
	public Integer getRemainingQty(String ticketPolicyId,Integer ticketOrderSize, Integer remainingQty) throws DZPWDealerApiException {

		Jedis jedis = jedisPool.getResource();

		try {
			
			if (StringUtils.isBlank(jedis.hget(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId)))
				setTotalQty(ticketPolicyId, remainingQty);
			
			// 传入5>余票4
			if (ticketOrderSize > Integer.parseInt(jedis.hget(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId))) {
				throw new DZPWDealerApiException("剩余可售数量不足!");
			}
			
			Long newRemainingQty = jedis.hincrBy(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId, -ticketOrderSize);
			// 并发2线程传入5，余票6，完后为-4
			if (newRemainingQty < 0) {
				// 需要把多卖的票加回去
				jedis.hincrBy(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId, ticketOrderSize);
				throw new DZPWDealerApiException("剩余可售数量不足!");
			}
			
			jedisPool.returnResource(jedis);
			return newRemainingQty.intValue();
		
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			if (e instanceof DZPWDealerApiException)
				throw (DZPWDealerApiException)e;
		}
		
		return null;
	}

	/**
	 * @方法功能说明： 库存返还
	 * @修改者名字：zzx
	 * @修改时间：2014-12-16下午5:45:24
	 * @修改内容：
	 * @参数：@param  key:ticketPolicyId门票策略Id
	 * @参数：@param  ticketOrderSize:订单中的票数量
	 * @参数：@param  remainingQty:库存（如果缓存中没有该门票库存则设置的数量）
	 * @参数：@return
	 * @return:Jedis
	 * @throws
	 */
	public Integer returnRemainingQty(String ticketPolicyId,Integer ticketOrderSize, Integer remainingQty) {

		Jedis jedis = jedisPool.getResource();

		try {
			
			if (StringUtils.isBlank(jedis.hget(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId)))
				setTotalQty(ticketPolicyId, remainingQty);
			
			Long newRemainingQty = jedis.hincrBy(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId, ticketOrderSize);

			jedisPool.returnResource(jedis);
			return newRemainingQty.intValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		
		return null;
	}
	
	/**
	 * @方法功能说明：设置余票总数
	 * @修改者名字：zzx
	 * @修改时间：2014-12-17上午11:24:25
	 * @修改内容：
	 * @参数：@param key:ticketPolicyId门票策略Id
	 * @参数：@param remainingQty 剩余数量
	 * @return:void
	 * @throws
	 */
	public void setTotalQty(String ticketPolicyId, Integer remainingQty) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(TICKET_ORDER_REMAINING_QTY_HASH, ticketPolicyId, remainingQty + "");
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	
	public String getTicketNo(){
		Jedis jedis = jedisPool.getResource();
		//没有KEY 域下的值时 设置初始订单号
		if(StringUtils.isBlank(jedis.hget(DZPW_TICKET_NO_HASH, "ticket_no")))
			jedis.hset(DZPW_TICKET_NO_HASH, "ticket_no", "10052300796");
		
		String id = jedis.hget(DZPW_TICKET_NO_HASH, "ticket_no");
		//订单号+1
		jedis.hincrBy(DZPW_TICKET_NO_HASH, "ticket_no", 1);
		
		//奇数位的和
		Integer jiSum = 0;
		//偶数位的和
		Integer ouSum = 0;
		for(int i=0; i<=id.length()-1; i+=2){
			jiSum += Integer.valueOf(id.charAt(i)+"");
			if(i!=id.length()-1)
				ouSum += Integer.valueOf(id.charAt(i+1)+"");
		}
		
		//计算校验码
		String jyCode = null;
		jiSum = jiSum*2;
		if((jiSum+ouSum)%11>=10)
			id = id+"X";
		else
			id = id+(jiSum+ouSum)%11+"";
		
		return id;
	}
	
	
	/**
	 * @方法功能说明：设置订单状态已更新待推送的订单
	 * @修改者名字：yangkang
	 * @修改时间：2016-2-18上午10:06:13
	 * @参数：@param orderNo
	 * @return:void
	 */
	public void setPushOrder(String orderId, String dealerKey){
		Jedis jedis = jedisPool.getResource();
		try {
			if(jedis.exists("PUSH_ORDER_NO_MAP")){
				JSONObject o = new JSONObject();
				o.put("dealerKey", dealerKey);
				o.put("time", new Date());
				jedis.hset("PUSH_ORDER_NO_MAP", orderId, o.toJSONString());
//				jedis.hset("PUSH_ORDER_NO_MAP", orderId, sdf.format(new Date()));
			}else{
				JSONObject o = new JSONObject();
				o.put("dealerKey", dealerKey);
				o.put("time", new Date());
				Map<String, String> map = new HashMap<String, String>();
				map.put(orderId, o.toJSONString());
				jedis.hmset("PUSH_ORDER_NO_MAP", map);
			}
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：获取要推送订单的MAP
	 * @修改者名字：yangkang
	 * @修改时间：2016-2-18下午1:44:51
	 * @参数：@return
	 * @return:Map<String,String>
	 */
	public Map<String, String> getPushOrder(){
		Jedis jedis = jedisPool.getResource();
		try {
			if (jedis.exists("PUSH_ORDER_NO_MAP")) {
				Map<String, String> map = jedis.hgetAll("PUSH_ORDER_NO_MAP");
				jedisPool.returnResource(jedis);
				return map;
			} else {
				jedisPool.returnResource(jedis);
				return null;
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		}
	}
	
	
	/**
	 * @方法功能说明： 移除已推送，且未更新时间的记录
	 * @修改者名字：yangkang
	 * @修改时间：2016-2-18下午5:12:06
	 * @参数：@param list
	 * @参数：@param pushTime 推送记录的时间节点
	 * @return:void
	 * @throws
	 */
	public void delPushOrderRecord(List<String> list, Date pushTime){
		Jedis jedis = jedisPool.getResource();
		
		if (list!=null && list.size()>0){
			try {
				for (String orderNo : list){
					String s = jedis.hget("PUSH_ORDER_NO_MAP", orderNo);
					// 小于0 时  当前订单号再推送时被更新过  所以不删除记录  待下次再推送
					if (pushTime.getTime() - sdf.parse(s).getTime() < 0)
						continue;
					else
						jedis.hdel("PUSH_ORDER_NO_MAP", orderNo);
				}
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				jedisPool.returnBrokenResource(jedis);
			}
		}
	}
}
