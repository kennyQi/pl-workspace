package plfx.gnjp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;
import hg.system.dao.StaffDao;
import hg.system.model.staff.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.gnjp.app.dao.GNJPOrderDAO;
import plfx.gnjp.app.dao.JPOrderLogDAO;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.gnjp.domain.model.order.GNJPOrderLog;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.app.dao.supplier.SupplierDAO;
import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.jp.pojo.system.OrderLogConstants;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.yeexing.qo.admin.JPOrderLogQO;

import com.alibaba.fastjson.JSON;


/****
 * 
 * @类功能说明：机票订单日志操作日志内层Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午3:03:42
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class JPOrderLogLocalService extends BaseServiceImpl<GNJPOrderLog,JPOrderLogQO,JPOrderLogDAO>{

	@Autowired
	private  JPOrderLogDAO dao;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Autowired
	private GNJPOrderDAO gNJPOrderDAO;
	
	@Autowired
	private StaffDao staffDao;

	@Autowired
	private SupplierDAO supplierDAO;
	
	@Autowired
	private DealerCacheManager dealerCacheManager;
	
	
	@Override
	protected JPOrderLogDAO getDao() {
		return dao;
	}
	

	/****
	 * 
	 * @方法功能说明：创建机票订单日志
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日下午3:03:31
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param order
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean create(CreateJPOrderLogCommand command){
		try {
			GNJPOrder jpOrder = gNJPOrderDAO.get(command.getPlatformOrderId());
			if (jpOrder == null){
				HgLogger.getInstance().error("yaosanfeng","JPOrderLogLocalService->create->[不存在机票订单]");
			}
			String logWorker = "分销管理员";	
			if (command.getLogWorkerId() != null) {
				if (OrderLogConstants.LOG_WORKER_TYPE_PLATFORM.equals(command.getLogWorkerType())) {
					Staff staff = staffDao.get(command.getLogWorkerId());
					if (staff != null)
						logWorker = staff.getInfo().getRealName();
				} else if (OrderLogConstants.LOG_WORKER_TYPE_DEALER.equals(command.getLogWorkerType())) {
					Dealer dealer = dealerCacheManager.getDealer(command.getLogWorkerId());
					if (dealer != null)
						logWorker = dealer.getName();
				} else if (OrderLogConstants.LOG_WORKER_TYPE_SUPPLIER.equals(command.getLogWorkerType())) {
					SupplierQO supplierQO = new SupplierQO();
					supplierQO.setCode(command.getLogWorkerId());
					Supplier supplier = supplierDAO.queryUnique(supplierQO);
					if (supplier != null)
						logWorker = supplier.getName();
				}
			} else if (OrderLogConstants.LOG_WORKER_TYPE_SCHEDULER.equals(command.getLogWorkerType())) {
				logWorker = "系统调度";
			}
			GNJPOrderLog log = new GNJPOrderLog();
			log.create(command,jpOrder,logWorker);
			dao.save(log);
			getDao().flush();
			HgLogger.getInstance().info("yaosanfeng", "JPOrderLogLocalService->create->[机票日志保存]" + JSON.toJSONString(log));
			//领域日志
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.log.GNJPOrderLog","create",JSON.toJSONString(command));
			domainEvent.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng", "JPOrderLogLocalService->create->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}

}