package plfx.mp.app.service.spi;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.mp.app.component.manager.DateSalePriceCacheManager;
import plfx.mp.app.component.manager.TCSupplierPolicySnapshotManager;
import plfx.mp.spi.inter.DeleteCacheService;
@Service("deleteCacheService")
public class DeleteCacheServiceImpl implements DeleteCacheService {
	
	@Autowired
	private DateSalePriceCacheManager dateSalePriceCacheManager;
	
	@Autowired
	private TCSupplierPolicySnapshotManager tcSupplierPolicySnapshotManager;
	
	/**
	 * 清除所有价格日历
	 */
	public void clear() {
		
		try {
			// 清空价格日历缓存
			dateSalePriceCacheManager.clearAll();
			HgLogger.getInstance().info(this.getClass(), "zhurz", "清空价格日历缓存", "清除缓存 定时任务");
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error(this.getClass(), "zhurz", "清空价格日历缓存失败", e, "清除缓存 定时任务");
		}
		
		try {
			// 清空同程政策缓存
			tcSupplierPolicySnapshotManager.clearAll();
			HgLogger.getInstance().info(this.getClass(), "zhurz", "清空同程政策缓存", "清除缓存 定时任务");
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error(this.getClass(), "zhurz", "清空同程政策缓存失败", e, "清除缓存 定时任务");
		}
		
	}
	
	@Override
	public void deleteCacheJob() {
		clear();
	}

}
