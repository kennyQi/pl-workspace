package slfx.jp.app.component;

import static slfx.jp.spi.common.JpEnumConstants.CacheKeyEnum.CACHE_KEY_FLIGHT_INFO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：缓存航班信息（[航班号]:[航班信息DTO]）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:38:02
 * @版本：V1.0
 *
 */
@Component
public class FlightCacheManager {
	
	@Autowired
	private JedisPool jedisPool;

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

	/**
	 * 刷新航班信息
	 * @param flightDTO
	 */
	public void refresh(YGFlightDTO flightDTO){
		if (flightDTO == null){
			return;
		}
		Jedis jedis = jedisPool.getResource();
		String flightNo = flightDTO.getFlightNo();
		String json = JSON.toJSONString(flightDTO);
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
	public void refresh(List<YGFlightDTO> flightList){
		if (flightList == null){
			return;
		}
		Jedis jedis = jedisPool.getResource();
		Transaction t = jedis.multi();
		for (YGFlightDTO flightDTO : flightList) {
			
			String flightNo = (flightDTO.getFlightNo() == null?"":flightDTO.getFlightNo());
			String startDateStr = (flightDTO.getStartDate() == null?"":flightDTO.getStartDate());
			//yyyyMMdd
			String tempDateStr =null;
			String json = null;
			
			json = JSON.toJSONString(flightDTO);
			
			//航班日期  2011/09/12 或 2011-09-12 接口不同格式会有差异
			if(startDateStr != null && startDateStr.indexOf("-") != -1){
				
				tempDateStr = flightNo + "-" + startDateStr.replaceAll("-", "");
			
			}else if(startDateStr != null && startDateStr.indexOf("/") != -1){
				
				tempDateStr = flightNo + "-" + startDateStr.replaceAll("/", "");
			
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
	public YGFlightDTO getFlightDTO(String flightNo,String flightDateStr) {
		
		flightDateStr = (flightDateStr == null?"":flightDateStr);
		
		Jedis jedis = jedisPool.getResource();
		String flightKey = "";
		
		if(flightDateStr != null && flightDateStr.indexOf("-") != -1){
			flightKey = flightNo + "-" + flightDateStr.replaceAll("-", "");
		}else if(flightDateStr.indexOf("/") != -1){
			flightKey = flightNo + "-" + flightDateStr.replaceAll("/", "");
		}else{
			flightKey = flightNo + "-" + flightDateStr;
		}
		YGFlightDTO flightDTO = null;

		String json = jedis.hget(CACHE_KEY_FLIGHT_INFO, flightKey);
		if (json != null && json.length() > 0) {
			flightDTO = JSON.parseObject(json, YGFlightDTO.class);
		}
		jedisPool.returnResource(jedis);
		return flightDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：检查一个key是否存在
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月5日下午2:37:59
	 * @修改内容：
	 * @参数：@param flightNo
	 * @参数：@param flightDateStr
	 * @参数：@return
	 * @return:boolean
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
		String s = "20140804";
		System.out.println(s.indexOf("-"));
		String s1 = "2014/08/04";
		System.out.println(s1.replaceAll("/", ""));
		System.out.println(s1.indexOf("/"));
		
	}
}
