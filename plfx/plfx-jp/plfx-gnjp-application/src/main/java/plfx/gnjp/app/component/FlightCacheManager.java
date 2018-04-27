package plfx.gnjp.app.component;



import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：缓存航班信息（[航班号]:[航班信息DTO]）
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月1日下午12:03:28
 * @版本：V1.0
 *
 */
@Component
public class FlightCacheManager {
	
	@Autowired
	private JedisPool jedisPool;

	public final static String CACHE_KEY_FLIGHT_INFO = "cache_key_flight_info";
	/**
	 * 清空所有航班信息
	 */
	public void clearAll() {
		Jedis jedis = jedisPool.getResource();
		jedis.hdel(CACHE_KEY_FLIGHT_INFO);
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 清除指定航班信息
	 * @param flightNo
	 */
	public void clear(String flightNo) {
		Jedis jedis = jedisPool.getResource();
		jedis.hdel(CACHE_KEY_FLIGHT_INFO, flightNo);
		jedisPool.returnResource(jedis);
	}

//易行天下开始了----------------------------------------------------
	/**
	 * 刷新航班信息
	 * @param flightDTO
	 */
	public void refresh(YeeXingFlightDTO yeeXingFlightDTO){
		if (yeeXingFlightDTO == null){
			return;
		}
		Jedis jedis = jedisPool.getResource();
		String flightNo = yeeXingFlightDTO.getFlightno();
		String json = JSON.toJSONString(yeeXingFlightDTO);
		Transaction t = jedis.multi();
		t.hdel(CACHE_KEY_FLIGHT_INFO, flightNo);
		t.hset(CACHE_KEY_FLIGHT_INFO, flightNo, json);
		t.exec();
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 刷新多条航班信息
	 * @param List<FlightDTO>
	 */
	public void refresh(List<YeeXingFlightDTO> yeeXingFlightList){
		HgLogger.getInstance().info("yaosanfeng","FlightCacheManager->refresh->List<YeeXingFlightDTO>:"+ JSON.toJSONString(yeeXingFlightList));
		if (yeeXingFlightList == null){
			return;
		}
		Jedis jedis = jedisPool.getResource();
		Transaction t = jedis.multi();
		for (YeeXingFlightDTO yeeXingFlightDTO : yeeXingFlightList) {
			String flightNo = (yeeXingFlightDTO.getFlightno() == null?"":yeeXingFlightDTO.getFlightno());
			String startDateStr = (yeeXingFlightDTO.getStartTime() == null?"":yeeXingFlightDTO.getStartTime());
			String startDate=startDateStr.substring(0, 10);//从2012-07-20 07:30截取2012-07-20
			//yyyyMMdd HH:mm
			String tempDateStr =null;
			String json = null;
			json = JSON.toJSONString(yeeXingFlightDTO);
			if(startDate != null && startDate.indexOf("-") != -1){
				tempDateStr = flightNo + "-" + startDate.replaceAll("-", "");
			
			}else{
				tempDateStr = flightNo + "-" + "00000000";
			}
			t.hdel(CACHE_KEY_FLIGHT_INFO, tempDateStr);
			t.hset(CACHE_KEY_FLIGHT_INFO, tempDateStr, json);
			flightNo = null;
			startDateStr = null;
			json = null;			
		}
		t.exec();
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 根据航班号获取航班信息
	 * @param flightNo
	 * @param flightDateStr[yyyyMMdd]
	 * @return FlightDTO
	 */
	public YeeXingFlightDTO getYXFlightDTO(String flightNo,String flightDateStr) {
		Jedis jedis = null;
		YeeXingFlightDTO yeeXingFlightDTO = null;
		try{
		flightDateStr = (flightDateStr == null?"":flightDateStr);
		//String starteDate=flightDateStr.substring(0, 10);
		String starteDate = flightDateStr;
		jedis = jedisPool.getResource();
		String flightKey = "";
		
		if(starteDate != null && starteDate.indexOf("-") != -1){
			flightKey = flightNo + "-" + starteDate.replaceAll("-", "");
		}else if(starteDate.indexOf("/") != -1){
			flightKey = flightNo + "-" + starteDate.replaceAll("/", "");
		}else{
			flightKey = flightNo + "-" + starteDate;
		}
	  //YeeXingFlightDTO yeeXingFlightDTO = null;
		String json = jedis.hget(CACHE_KEY_FLIGHT_INFO, flightKey);
		HgLogger.getInstance().info("yaosanfeng","FlightCacheManager->getYXFlightDTO->json:"+ JSON.toJSONString(json));
		if (json != null && json.length() > 0) {
			yeeXingFlightDTO = JSON.parseObject(json, YeeXingFlightDTO.class);
			HgLogger.getInstance().info("yaosanfeng","FlightCacheManager->getYXFlightDTO->YeeXingFlightDTO:"+ JSON.toJSONString(yeeXingFlightDTO));
		}
		jedisPool.returnResource(jedis);
		}catch(Exception e){
			HgLogger.getInstance().error("yaosanfeng","FlightCacheManager->getYXFlightDTO->:redis获取链接异常:"+ HgLogger.getStackTrace(e));
			jedisPool.returnBrokenResource(jedis);
		}
		return yeeXingFlightDTO;
	}
	

	/****
	 * 
	 * @方法功能说明：检查一个key是否存在
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月2日下午2:25:06
	 * @修改内容：
	 * @参数：@param flightNo
	 * @参数：@param flightDateStr
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean checkBYFlightKey(String flightNo,String flightDateStr) {
		
		flightDateStr = flightDateStr==null ? "":flightDateStr;
		
		Jedis jedis = jedisPool.getResource();
		
		String flightKey = "";
		
		if(flightDateStr != null && flightDateStr.indexOf("-") != -1){
			flightKey = flightNo + "-" + flightDateStr.replaceAll("-", "");
		}else if(flightDateStr.indexOf("/") != -1){
			flightKey = flightNo + "-" + flightDateStr.replaceAll("/", "");
		}else{
			flightKey = flightNo + "-" + flightDateStr;
		}
		Boolean bool =jedis.hexists(CACHE_KEY_FLIGHT_INFO,flightKey.trim());
		
		jedisPool.returnResource(jedis);
		
		return bool;
		
	}
	
	
	public static void main(String[] args) {
		FlightCacheManager f=new FlightCacheManager();
		YeeXingFlightDTO yx=f.getYXFlightDTO("MU5301","2015-07-20 07:30");
//		System.out.println("999999999999999");
//		System.out.println(JSON.toJSONString(yx));
//		System.out.println("TTTTTTTTTTTTTTTT");
	}
	
	
}
