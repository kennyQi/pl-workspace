package plfx.jp.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import plfx.jp.domain.model.CityAirCode;

/**
 * @类功能说明：城市机场三字码缓存管理
 * @类修改者：
 * @修改日期：2015-7-20上午10:36:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20上午10:36:52
 */
@Component
public class CityAirCodeCacheManager {

	@Resource
	private Redisson redisson;

	public static final String CITY_AIR_CODE_MAP = "PLFX:CITY_AIR_CODE_MAP";

	/**
	 * @方法功能说明：刷新城市机场三字码缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:43
	 * @修改内容：
	 * @参数：@param cityAirCodes
	 * @return:void
	 * @throws
	 */
	public void reflushCityAirCode(List<CityAirCode> cityAirCodes) {
		RMap<String, CityAirCode> map = redisson.getMap(CITY_AIR_CODE_MAP);
		Map<String, CityAirCode> cityAirCodeMap = new HashMap<String, CityAirCode>();
		for (CityAirCode cityAirCode : cityAirCodes)
			cityAirCodeMap.put(cityAirCode.getAirCode(), cityAirCode);
		map.clear();
		map.putAll(cityAirCodeMap);
	}

	/**
	 * @方法功能说明：获取城市机场三字码
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:52
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:CityAirCode
	 * @throws
	 */
	public CityAirCode getCityAirCode(String code) {
		if (code == null)
			return null;
		RMap<String, CityAirCode> map = redisson.getMap(CITY_AIR_CODE_MAP);
		return map.get(code);
	}

}
