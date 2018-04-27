package plfx.jp.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import plfx.jp.domain.model.Airport;

/**
 * @类功能说明：机场缓存管理
 * @类修改者：
 * @修改日期：2015-7-28下午4:23:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-28下午4:23:04
 */
@Component
public class AirportCacheManager {

	@Resource
	private Redisson redisson;

	public static final String AIRPORT_MAP = "PLFX:AIRPORT_MAP";

	/**
	 * @方法功能说明：刷新机场缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-28下午4:23:52
	 * @修改内容：
	 * @参数：@param airports
	 * @return:void
	 * @throws
	 */
	public void reflushAirport(List<Airport> airports) {
		RMap<String, Airport> map = redisson.getMap(AIRPORT_MAP);
		Map<String, Airport> airportMap = new HashMap<String, Airport>();
		for (Airport airport : airports)
			airportMap.put(airport.getId(), airport);
		map.clear();
		map.putAll(airportMap);
	}

	/**
	 * @方法功能说明：获取机场
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-28下午4:24:38
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:Airport
	 * @throws
	 */
	public Airport getAirport(String code) {
		if (code == null)
			return null;
		RMap<String, Airport> map = redisson.getMap(AIRPORT_MAP);
		return map.get(code);
	}

}
