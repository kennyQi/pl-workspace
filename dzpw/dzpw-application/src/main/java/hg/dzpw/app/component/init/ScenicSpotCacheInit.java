package hg.dzpw.app.component.init;

import hg.common.component.RedisLock;
import hg.dzpw.app.component.manager.ScenicSpotCacheManager;
import hg.dzpw.app.pojo.qo.ClientDeviceQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.ClientDeviceLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.domain.model.scenicspot.ClientDevice;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.system.common.init.InitBean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @类功能说明：景区相关缓存初始化
 * @类修改者：
 * @修改日期：2014-12-17下午4:28:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-17下午4:28:02
 */
@Component
public class ScenicSpotCacheInit implements InitBean {

	private final static Logger logger = LoggerFactory.getLogger(ScenicSpotCacheInit.class);
	
	@Autowired
	private ClientDeviceLocalService deviceLocalService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	@Autowired
	private ScenicSpotCacheManager cacheManager;
	
	private RedisLock lock;
	
	public ScenicSpotCacheInit() {
		lock = new RedisLock("dzpwScenicSpotCacheInit");
	}

	@Override
	public void springContextStartedRun() throws Exception {
		if (lock.tryLock()) {
			try {
				long time = System.currentTimeMillis();
				logger.info("--------------------- 景区相关缓存初始化开始 ---------------------");
				
				ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
				scenicSpotQo.setActivated(true);
				scenicSpotQo.setRemoved(false);
				
				List<ScenicSpot> scenicSpots = scenicSpotLocalService.queryList(scenicSpotQo);

				ClientDeviceQo deviceQo = new ClientDeviceQo();
				deviceQo.setScenicSpotQo(scenicSpotQo);
				List<ClientDevice> devices = deviceLocalService.queryList(deviceQo);

				cacheManager.clearAll();
				
				for (ScenicSpot scenicSpot : scenicSpots)
					cacheManager.refreshScenicSpotSecretKey(scenicSpot.getId(), scenicSpot.getSuperAdmin().getSecretKey());
				
				for (ClientDevice device : devices)
					cacheManager.refreshClientDevice(device.getScenicSpot().getId(), device.getId());

				logger.info("--------------------- 景区相关缓存初始化结束 --------------------->>" + (System.currentTimeMillis() - time) + "ms");
				
			} finally {
				lock.unlock();
			}
		}
	}

}
