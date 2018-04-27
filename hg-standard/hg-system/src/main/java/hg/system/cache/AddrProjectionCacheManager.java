package hg.system.cache;

import hg.system.model.meta.AddrProjection;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;


/**
 * 省市区数据映射与缓存
 * 
 * @author zhurz
 */
//@Component
public class AddrProjectionCacheManager extends RedisCacheManager {
	
	public final static String PROVINCE_CACHE = "province_cache";
	public final static String CITY_CACHE = "city_cache";
	public final static String AREA_CACHE = "area_cache";
	public final static String ADDR_PROJECTION_CACHE = "addr_projection_cache";
	
	public void refreshProvince(List<Province> provinces) {
		Jedis jedis = getJedis();
		jedis.del(PROVINCE_CACHE);
		for (Province p : provinces) {
			jedis.hset(PROVINCE_CACHE, p.getCode(), JSON.toJSONString(p));
		}
	}

	public void refreshCity(List<City> cities) {
		Jedis jedis = getJedis();
		jedis.del(CITY_CACHE);
		for (City c : cities) {
			jedis.hset(CITY_CACHE, c.getCode(), JSON.toJSONString(c));
		}
	}

	public void refreshArea(List<Area> areas){
		Jedis jedis = getJedis();
		jedis.del(AREA_CACHE);
		for (Area a : areas) {
			jedis.hset(AREA_CACHE, a.getCode(), JSON.toJSONString(a));
		}
	}
	
	public Province getProvince(String code) {
		if (code == null) return null;
		Jedis jedis = getJedis();
		String json = jedis.hget(PROVINCE_CACHE, code);
		return json != null ? JSON.parseObject(json, Province.class) : null;
	}
	/**
	 * 得到缓存中所有的省份
	 * @方法功能说明：
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月22日下午4:26:45
	 * @修改内容：
	 * @参数：@return
	 * @return:List<Province>
	 * @throws
	 */
	public List<Province> getProvinces() {
		Jedis jedis = getJedis();
		Map<String, String> provinces = jedis.hgetAll(PROVINCE_CACHE);
		Set<String> keys=provinces.keySet();
		List<Province> p=new ArrayList<Province>();
		for (String key:keys) {
			String json=provinces.get(key);
			 p.add(json!= null ? JSON.parseObject(json, Province.class) : null);
		}
		return p;
	}
	public City getCity(String code) {
		if (code == null) return null;
		Jedis jedis = getJedis();
		String json = jedis.hget(CITY_CACHE, code);
		return json != null ? JSON.parseObject(json, City.class) : null;
	}
	/**
	 * 得到缓存中所有的城市
	 * @方法功能说明：
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月22日下午4:26:45
	 * @修改内容：
	 * @参数：@return
	 * @return:List<Province>
	 * @throws
	 */
	public List<City> getCitys() {
		Jedis jedis = getJedis();
		Map<String, String> provinces = jedis.hgetAll(CITY_CACHE);
		Set<String> keys=provinces.keySet();
		List<City> cList=new ArrayList<City>();
		for (String key:keys) {
			String json=provinces.get(key);
			cList.add(json!= null ? JSON.parseObject(json, City.class) : null);
		}
		return cList;
	}
	public Area getArea(String code) {
		if (code == null) return null;
		Jedis jedis = getJedis();
		String json = jedis.hget(AREA_CACHE, code);
		return json != null ? JSON.parseObject(json, Area.class) : null;
	}
	
	protected String getChannelKey(Integer channelType, Integer addrType, String addrCode) {
		return channelType + "_" + addrType + "_" + addrCode;
	}
	
	protected String getLocalKey(Integer channelType, Integer addrType, String addrCode) {
		return "_" + channelType + "_" + addrType + "_" + addrCode;
	}

	protected String getChannelKey(AddrProjection projection) {
		return getChannelKey(projection.getChannelType(),
				projection.getAddrType(), projection.getChannelAddrCode());
	}

	protected String getLocalKey(AddrProjection projection) {
		return getLocalKey(projection.getChannelType(),
				projection.getAddrType(), projection.getAddrCode());
	}
	
	public void refreshAddrProjection(List<AddrProjection> addrProjections) {
		Jedis jedis = getJedis();
		jedis.del(ADDR_PROJECTION_CACHE);
		for (AddrProjection projection : addrProjections) {
			jedis.hset(ADDR_PROJECTION_CACHE, getChannelKey(projection), projection.getAddrCode());
			jedis.hset(ADDR_PROJECTION_CACHE, getLocalKey(projection), projection.getChannelAddrCode());
		}
	}
	
	/**
	 * 根据渠道代码和省份代码获取对应本地代码
	 * 
	 * @param channelAddrCode
	 * @param channelType
	 * @return
	 */
	public String getLocalProvinceCode(String channelAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getChannelKey(channelType, AddrProjection.ADDR_TYPE_PROV,
						channelAddrCode));
		return code;
	}

	/**
	 * 根据渠道代码和城市代码获取对应本地代码
	 * 
	 * @param channelAddrCode
	 * @param channelType
	 * @return
	 */
	public String getLocalCityCode(String channelAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getChannelKey(channelType, AddrProjection.ADDR_TYPE_CITY, channelAddrCode));
		return code;
	}
	
	/**
	 * 根据渠道代码和地区代码获取对应本地代码
	 * 
	 * @param channelAddrCode
	 * @param channelType
	 * @return
	 */
	public String getLocalAreaCode(String channelAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getChannelKey(channelType, AddrProjection.ADDR_TYPE_AREA, channelAddrCode));
		return code;
	}

	/**
	 * 根据渠道代码和省份代码获取对应渠道代码
	 * 
	 * @param localAddrCode
	 * @param channelType
	 * @return
	 */
	public String getChannelProvinceCode(String localAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getLocalKey(channelType, AddrProjection.ADDR_TYPE_PROV, localAddrCode));
		return code;
	}

	/**
	 * 根据渠道代码和城市代码获取对应渠道代码
	 * 
	 * @param localAddrCode
	 * @param channelType
	 * @return
	 */
	public String getChannelCityCode(String localAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getLocalKey(channelType, AddrProjection.ADDR_TYPE_CITY, localAddrCode));
		return code;
	}
	
	/**
	 * 根据渠道代码和地区代码获取对应渠道代码
	 * 
	 * @param localAddrCode
	 * @param channelType
	 * @return
	 */
	public String getChannelAreaCode(String localAddrCode, Integer channelType) {
		Jedis jedis = getJedis();
		String code = jedis.hget(ADDR_PROJECTION_CACHE,
				getLocalKey(channelType, AddrProjection.ADDR_TYPE_AREA, localAddrCode));
		return code;
	}
}
