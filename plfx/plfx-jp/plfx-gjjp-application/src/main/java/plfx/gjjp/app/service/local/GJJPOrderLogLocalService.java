package plfx.gjjp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.StaffDao;
import hg.system.model.staff.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import plfx.gjjp.app.dao.GJJPOrderDao;
import plfx.gjjp.app.dao.GJJPOrderLogDao;
import plfx.gjjp.app.pojo.qo.GJJPOrderLogQo;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJJPOrderLog;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.app.dao.supplier.SupplierDAO;
import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.jp.qo.admin.supplier.SupplierQO;

@Service
@Transactional(rollbackFor = Exception.class)
public class GJJPOrderLogLocalService extends BaseServiceImpl<GJJPOrderLog, GJJPOrderLogQo, GJJPOrderLogDao> {

	@Autowired
	private GJJPOrderLogDao gjjpOrderLogDao;
	
	@Autowired
	private StaffDao staffDao;

	@Autowired
	private GJJPOrderDao jpOrderDao;
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	@Autowired
	private DealerCacheManager dealerCacheManager;
	

	@Override
	protected GJJPOrderLogDao getDao() {
		return gjjpOrderLogDao;
	}

	/**
	 * @方法功能说明：记录国际机票订单操作日志(新开事务执行)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-22上午11:09:24
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordLog(CreateGJJPOrderLogCommand command) {
		GJJPOrder jpOrder = jpOrderDao.get(command.getPlatformOrderId());
		if (jpOrder == null)
			return;
		String logWorker = "未知";
		if (command.getLogWorkerId() != null) {
			if (GJJPConstants.LOG_WORKER_TYPE_PLATFORM.equals(command.getLogWorkerType())) {
				Staff staff = staffDao.get(command.getLogWorkerId());
				if (staff != null)
					logWorker = staff.getInfo().getRealName();
			} else if (GJJPConstants.LOG_WORKER_TYPE_DEALER.equals(command.getLogWorkerType())) {
				Dealer dealer = dealerCacheManager.getDealer(command.getLogWorkerId());
				if (dealer != null)
					logWorker = dealer.getName();
			} else if (GJJPConstants.LOG_WORKER_TYPE_SUPPLIER.equals(command.getLogWorkerType())) {
				SupplierQO supplierQO = new SupplierQO();
				supplierQO.setCode(command.getLogWorkerId());
				Supplier supplier = supplierDAO.queryUnique(supplierQO);
				if (supplier != null)
					logWorker = supplier.getName();
			}
		} else if (GJJPConstants.LOG_WORKER_TYPE_SCHEDULER.equals(command.getLogWorkerType())) {
			logWorker = "系统调度";
		}
		GJJPOrderLog log = new GJJPOrderLog();
		log.create(command, jpOrder, logWorker);
		getDao().save(log);
	}
}
