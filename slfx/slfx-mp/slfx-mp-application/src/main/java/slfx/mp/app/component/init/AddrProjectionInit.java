package slfx.mp.app.component.init;

import java.util.List;

import hg.system.cache.AddrProjectionCacheManager;
import hg.system.model.meta.AddrProjection;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AddrProjectionQo;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.AddrProjectionService;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("addrProjectionInit_mp")
public class AddrProjectionInit implements InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(AddrProjectionInit.class);
	
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private AddrProjectionService addrProjectionService;
	
	@Autowired
	private AddrProjectionCacheManager cacheManager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("省市区数据缓存加载...");
		List<Province> plist = provinceService.queryList(new ProvinceQo());
		List<City> clist = cityService.queryList(new CityQo());
		List<Area> alist = areaService.queryList(new AreaQo());
		List<AddrProjection> aplist = addrProjectionService.queryList(new AddrProjectionQo());
		cacheManager.refreshProvince(plist);
		cacheManager.refreshCity(clist);
		cacheManager.refreshArea(alist);
		cacheManager.refreshAddrProjection(aplist);
		logger.info("省市区数据缓存加载完毕");
	}

}
