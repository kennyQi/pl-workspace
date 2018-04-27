package plfx.gjjp.app.service.local;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.component.cache.AirlineCompanyCacheManager;
import plfx.jp.app.component.cache.AirportCacheManager;
import plfx.jp.domain.model.AirlineCompany;
import plfx.jp.domain.model.Airport;

/**
 * 数据转换工具
 * @author guotx
 * @创建时间 2015-07-30 10:30:25
 */
@Service
public class DataTransService {

	//机场缓存管理
	@Autowired
	private AirportCacheManager airportCacheManager;
	//航空公司缓存管理
	@Autowired
	private AirlineCompanyCacheManager airlineCompanyCacheManager;
	/**
	 * 根据三字码获取机场名称
	 * @param code 机场三字码
	 */
	public String getAirportName(String code){
		Airport airport=airportCacheManager.getAirport(code);
		if (airport==null) {
			return code;
		}
		return airport.getName();
	}
	/**
	 * Map类型转换
	 * 解决freemarker无法从Map<Integer,String>取值
	 * @return
	 */
	public Map<String, String> getIntMap(Map<Integer, String> map){
		
		HashMap<String, String> newMap=new HashMap<>();
		for (Entry<Integer, String> entry : map.entrySet()) {
			newMap.put(entry.getKey().toString(), entry.getValue());
		}
		return newMap;
	}
	/**
	 * 根据航空公司二字码获取航空公司名称
	 * @param code
	 * @return
	 */
	public String getAirLineNameByCode(String code){
		AirlineCompany airlineCompany=airlineCompanyCacheManager.getAirlineCompany(code);
		if (airlineCompany==null) {
			//获取不到依然返回二字码
			return code+"(无此航空公司)";
		}
		return airlineCompany.getName();
	}
}
