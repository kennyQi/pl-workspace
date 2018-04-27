package plfx.jp.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import plfx.jp.domain.model.AirlineCompany;

@Component
public class AirlineCompanyCacheManager {

	@Resource
	private Redisson redisson;

	public static final String AIRLINE_COMPANY_CODE_MAP = "PLFX:AIRLINE_COMPANY_CODE_MAP";

	/**
	 * @方法功能说明：刷新航空公司缓存
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:43
	 * @修改内容：
	 * @参数：@param cityAirCodes
	 * @return:void
	 * @throws
	 */
	public void reflushAirlineCompany(List<AirlineCompany> airlineCompanies) {
		RMap<String, AirlineCompany> map = redisson.getMap(AIRLINE_COMPANY_CODE_MAP);
		Map<String, AirlineCompany> airlineCompanyMap = new HashMap<String, AirlineCompany>();
		for (AirlineCompany airlineCompany : airlineCompanies)
			airlineCompanyMap.put(airlineCompany.getId(), airlineCompany);
		map.clear();
		map.putAll(airlineCompanyMap);
	}

	/**
	 * @方法功能说明：获取航空公司
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14下午4:12:52
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:AirlineCompany
	 * @throws
	 */
	public AirlineCompany getAirlineCompany(String code) {
		if (code == null)
			return null;
		RMap<String, AirlineCompany> map = redisson.getMap(AIRLINE_COMPANY_CODE_MAP);
		return map.get(code);
	}
	
}
