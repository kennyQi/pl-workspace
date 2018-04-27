package plfx.jp.app.service.local.supplier;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.supplier.SupplierDAO;
import plfx.jp.command.admin.supplier.SupplierCommand;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.jp.pojo.system.SupplierConstants;
import plfx.jp.qo.admin.supplier.SupplierQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:08:23
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class SupplierLocalService extends BaseServiceImpl<Supplier, SupplierQO, SupplierDAO>{

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	SupplierDAO supplierDAO;
	
	@Override
	protected SupplierDAO getDao() {
		return supplierDAO;
	}
	
	public boolean useSupplier(SupplierCommand command){
		SupplierQO qo=new SupplierQO();
		qo.setId(command.getId());
		Supplier entity=this.queryUnique(qo);
		entity.updateStatus(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.supplier.Supplier","useSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "SupplierLocalService->useSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean saveSupplier(SupplierCommand command){
		Supplier entity=new Supplier(command);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.supplier.Supplier","saveSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "SupplierLocalService->saveSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean deleteSupplier(SupplierCommand command){
		try {
			this.deleteById(command.getId());
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.supplier.Supplier","deleteSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "SupplierLocalService->deleteSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean updateSupplier(SupplierCommand command){
		SupplierQO qo=new SupplierQO();
		qo.setId(command.getId());
		Supplier entity=this.queryUnique(qo);
		entity.update(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.supplier.Supplier","updateSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
	} catch (Exception e) {
		HgLogger.getInstance().error("yuqz", "SupplierLocalService->updateSupplier->exception:" + HgLogger.getStackTrace(e));
		return false;
	}
		return true;
	}
	
	public boolean multiUse(SupplierCommand command){
		for (String id1 : command.getIds()) {
			SupplierQO qo=new SupplierQO();
			qo.setId(id1);
			Supplier entity=this.queryUnique(qo);
			SupplierCommand command1=new SupplierCommand();
			if("use".equals(command.getFlag())){
				command1.setStatus(SupplierConstants.USE);
			}else{
				command1.setStatus(SupplierConstants.PRE_USE);
			}
		    entity.updateStatus(command1);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.supplier.Supplier","multiUse",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "SupplierLocalService->multiUse->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	 }
	return true;
	}
}
