package hg.system.cache;

import hg.system.model.meta.AddrProjection;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @类功能说明：使用静态变量缓存省市区数据
 * @类修改者：
 * @修改日期：2014-9-26上午10:05:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-9-26上午10:05:18
 */
@Component
public class AddrProjectionCacheManager2 extends AddrProjectionCacheManager {

	private final static Map<String, Province> PMAP = new HashMap<String, Province>();
	private final static Map<String, City> CMAP = new HashMap<String, City>();
	private final static Map<String, Area> AMAP = new HashMap<String, Area>();
	private final static Map<String, String> PROJECTION_MAP = new HashMap<String, String>();

	@Override
	public void refreshProvince(List<Province> provinces) {
		synchronized (PMAP) {
			PMAP.clear();
			for (Province province : provinces) {
				PMAP.put(province.getCode(), province);
			}
		}
	}

	@Override
	public void refreshCity(List<City> cities) {
		synchronized (CMAP) {
			CMAP.clear();
			for (City city : cities) {
				CMAP.put(city.getCode(), city);
			}
		}
	}

	@Override
	public void refreshArea(List<Area> areas) {
		synchronized (AMAP) {
			AMAP.clear();
			for (Area area : areas) {
				AMAP.put(area.getCode(), area);
			}
		}
	}

	@Override
	public void refreshAddrProjection(List<AddrProjection> addrProjections) {
		synchronized (PROJECTION_MAP) {
			PROJECTION_MAP.clear();
			for (AddrProjection projection : addrProjections) {
				PROJECTION_MAP.put(getChannelKey(projection), projection.getAddrCode());
				PROJECTION_MAP.put(getLocalKey(projection), projection.getChannelAddrCode());
			}
		}
	}

	@Override
	public Province getProvince(String code) {
		return PMAP.get(code);
	}

	@Override
	public List<Province> getProvinces() {
		List<Province> provinces = new ArrayList<Province>(PMAP.values());
		return Collections.unmodifiableList(provinces);
	}

	@Override
	public City getCity(String code) {
		return CMAP.get(code);
	}

	@Override
	public List<City> getCitys() {
		List<City> cities = new ArrayList<City>(CMAP.values());
		return Collections.unmodifiableList(cities);
	}

	@Override
	public Area getArea(String code) {
		return AMAP.get(code);
	}
	
	@Override
	public String getLocalProvinceCode(String channelAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getChannelKey(channelType, AddrProjection.ADDR_TYPE_PROV,
				channelAddrCode));
	}

	@Override
	public String getLocalCityCode(String channelAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getChannelKey(channelType, AddrProjection.ADDR_TYPE_CITY, channelAddrCode));
	}
	
	@Override
	public String getLocalAreaCode(String channelAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getChannelKey(channelType, AddrProjection.ADDR_TYPE_AREA, channelAddrCode));
	}

	@Override
	public String getChannelProvinceCode(String localAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getLocalKey(channelType, AddrProjection.ADDR_TYPE_PROV, localAddrCode));
	}

	@Override
	public String getChannelCityCode(String localAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getLocalKey(channelType, AddrProjection.ADDR_TYPE_CITY, localAddrCode));
	}
	
	@Override
	public String getChannelAreaCode(String localAddrCode, Integer channelType) {
		return PROJECTION_MAP.get(getLocalKey(channelType, AddrProjection.ADDR_TYPE_AREA, localAddrCode));
	}

}
