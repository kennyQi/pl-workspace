package plfx.jp.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import plfx.jp.domain.model.Country;

@Component
public class CountryCacheManager {

	@Resource
	private Redisson redisson;

	public static final String COUNTRY_CODE_MAP = "PLFX:COUNTRY_CODE_MAP";

	/**
	 * @方法功能说明：刷新国家代码缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:43
	 * @修改内容：
	 * @参数：@param cityAirCodes
	 * @return:void
	 * @throws
	 */
	public void reflushCountry(List<Country> countries) {
		RMap<String, Country> map = redisson.getMap(COUNTRY_CODE_MAP);
		Map<String, Country> countryMap = new HashMap<String, Country>();
		for (Country country : countries)
			countryMap.put(country.getId(), country);
		map.clear();
		map.putAll(countryMap);
	}

	/**
	 * @方法功能说明：获取国家
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:52
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:Country
	 * @throws
	 */
	public Country getCountry(String code) {
		if (code == null)
			return null;
		RMap<String, Country> map = redisson.getMap(COUNTRY_CODE_MAP);
		return map.get(code);
	}
}
