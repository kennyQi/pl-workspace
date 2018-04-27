package pay.record.app.component.init;

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

import pay.record.app.component.cache.AuthIPCacheManager;
import pay.record.app.service.local.authip.AuthIPLocalService;
import pay.record.domain.model.authip.AuthIP;
import pay.record.pojo.qo.authip.AuthIPQO;

@Component
public class CacheInit implements InitBean {

	private final static Logger logger = LoggerFactory.getLogger(CacheInit.class);

	public final static String LOCK_KEY = "PAY_RECORD:CACHE_INIT";

	@Resource
	private Redisson redisson;
	
	@Autowired
	private AuthIPLocalService authIPLocalService;
	
	@Autowired
	private AuthIPCacheManager authIPCacheManager;

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
				logger.info("授权IP缓存加载中...");
				AuthIPQO authIPQO = new AuthIPQO();
				authIPQO.setStatus("1");
				List<AuthIP> authIPs = authIPLocalService.queryList(authIPQO);
				authIPCacheManager.reflushAuthIPMap(authIPs);
				logger.info("授权IP缓存加载完毕");
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
