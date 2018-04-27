package plfx.jp.app.component.init;

import hg.system.common.init.InitBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.jp.app.component.cache.AirlineCompanyCacheManager;
import plfx.jp.app.component.cache.AirportCacheManager;
import plfx.jp.app.component.cache.CityAirCodeCacheManager;
import plfx.jp.app.component.cache.CountryCacheManager;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.app.service.local.AirlineCompanyLocalService;
import plfx.jp.app.service.local.AirportLocalService;
import plfx.jp.app.service.local.CityAirCodeLocalService;
import plfx.jp.app.service.local.CountryLocalService;
import plfx.jp.app.service.local.dealer.DealerLocalService;
import plfx.jp.domain.model.AirlineCompany;
import plfx.jp.domain.model.Airport;
import plfx.jp.domain.model.CityAirCode;
import plfx.jp.domain.model.Country;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.qo.AirlineCompanyQo;
import plfx.jp.qo.AirportQo;
import plfx.jp.qo.CountryQo;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.qo.api.CityAirCodeQO;

@Component
public class CacheInit implements InitBean {

	private final static Logger logger = LoggerFactory.getLogger(CacheInit.class);

	public final static String LOCK_KEY = "PLFX:CACHE_INIT";

	@Resource
	private Redisson redisson;

	@Autowired
	private DealerLocalService dealerLocalService;
	@Autowired
	private CityAirCodeLocalService cityAirCodeLocalService;
	@Autowired
	private CountryLocalService countryLocalService;
	@Autowired
	private AirlineCompanyLocalService airlineCompanyLocalService;
	@Autowired
	private AirportLocalService airportLocalService;

	@Autowired
	private DealerCacheManager dealerCacheManager;
	@Autowired
	private CityAirCodeCacheManager cityAirCodeCacheManager;
	@Autowired
	private CountryCacheManager countryCacheManager;
	@Autowired
	private AirlineCompanyCacheManager airlineCompanyCacheManager;
	@Autowired
	private AirportCacheManager airportCacheManager;

	private synchronized RLock getLock() {
		return redisson.getLock(LOCK_KEY);
	}

	@Override
	public void springContextStartedRun() throws Exception {
		RLock lock = getLock();
		logger.info("缓存加载中...");
		if (lock.tryLock(0, 10, TimeUnit.MINUTES)) {
			try {
				// -------------------------------
				logger.info("经销商缓存加载中...");
				List<Dealer> dealers = dealerLocalService.queryList(new DealerQO());
				dealerCacheManager.reflushDealerMap(dealers);
				logger.info("经销商缓存加载完毕");
				// -------------------------------
				logger.info("城市机场三字码缓存加载中...");
				List<CityAirCode> cityAirCodes = cityAirCodeLocalService.queryList(new CityAirCodeQO());
				cityAirCodeCacheManager.reflushCityAirCode(cityAirCodes);
				logger.info("城市机场三字码缓存加载完毕");
				// -------------------------------
				logger.info("机场缓存加载中...");
				List<Airport> airports = airportLocalService.queryList(new AirportQo());
				airportCacheManager.reflushAirport(airports);
				logger.info("机场缓存加载完毕");
				// -------------------------------
				logger.info("国家缓存加载中...");
				List<Country> countries = countryLocalService.queryList(new CountryQo());
				countryCacheManager.reflushCountry(countries);
				logger.info("国家缓存加载完毕");
				// -------------------------------
				logger.info("航空公司缓存加载中...");
				List<AirlineCompany> airlineCompanies = airlineCompanyLocalService.queryList(new AirlineCompanyQo());
				airlineCompanyCacheManager.reflushAirlineCompany(airlineCompanies);
				logger.info("航空公司缓存加载完毕");
				// -------------------------------
				logger.info("全部缓存加载完毕");
			} finally {
				lock.unlock();
			}
		} else {
			logger.info("加载缓存在其它实例执行中...");
		}
	}

}
