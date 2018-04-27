package hg.dzpw.app.component.init;

import hg.common.component.RedisLock;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.system.common.init.InitBean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @类功能说明：经销商相关缓存初始化
 * @类修改者：
 * @修改日期：2014-12-17下午4:27:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-17下午4:27:57
 */
@Component
public class DealerCacheInit implements InitBean {

	private final static Logger logger = LoggerFactory.getLogger(DealerCacheInit.class);

	@Autowired
	private DealerLocalService dealerLocalService;

	@Autowired
	private DealerCacheManager cacheManager;

	private RedisLock lock;
	
	public DealerCacheInit() {
		lock = new RedisLock("dzpwDealerCacheInit");
	}

	@Override
	public void springContextStartedRun() throws Exception {

		if (lock.tryLock()) {

			long time = System.currentTimeMillis();
			logger.info("--------------------- 经销商相关缓存初始化开始 ---------------------");

			try {
				DealerQo qo = new DealerQo();
				qo.setStatus(1);

				List<Dealer> dealers = dealerLocalService.queryList(qo);

				cacheManager.clearAll();

				for (Dealer dealer : dealers){
					cacheManager.setSecretKey(dealer.getId(), dealer.getClientInfo().getKey(), dealer.getClientInfo().getSecretKey());
					if (dealer.getClientInfo().getPublishAble() != null && dealer.getClientInfo().getPublishAble())
						cacheManager.setDealerPushlishUrl(dealer.getClientInfo().getKey(), dealer.getClientInfo().getPublishUrl());
				}

			} finally {
				lock.unlock();
			}
			
			logger.info("--------------------- 经销商相关缓存初始化结束 --------------------->>" + (System.currentTimeMillis() - time) + "ms");
		}
	}

}
