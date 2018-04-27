package slfx.jp.app.service.local.supplier;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.supplier.SupplierDAO;
import slfx.jp.command.admin.supplier.SupplierCommand;
import slfx.jp.domain.model.supplier.Supplier;
import slfx.jp.pojo.system.SupplierConstants;
import slfx.jp.qo.admin.supplier.SupplierQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:42:43
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
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月15日下午4:48:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean useSupplier(SupplierCommand command){
		SupplierQO qo=new SupplierQO();
		qo.setId(command.getId());
		Supplier entity=this.queryUnique(qo);
		entity.updateStatus(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.supplier.Supplier","useSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "SupplierLocalService->useSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月15日下午4:48:33
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean saveSupplier(SupplierCommand command){
		Supplier entity=new Supplier(command);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.supplier.Supplier","saveSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "SupplierLocalService->saveSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月15日下午4:48:47
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deleteSupplier(SupplierCommand command){
		try {
			this.deleteById(command.getId());
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.supplier.Supplier","deleteSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "SupplierLocalService->deleteSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月15日下午4:48:52
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateSupplier(SupplierCommand command){
		SupplierQO qo=new SupplierQO();
		qo.setId(command.getId());
		Supplier entity=this.queryUnique(qo);
		entity.update(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.supplier.Supplier","updateSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
	} catch (Exception e) {
		HgLogger.getInstance().error("tandeng", "SupplierLocalService->updateSupplier->exception:" + HgLogger.getStackTrace(e));
		return false;
	}
		return true;
	}
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月17日上午11:21:19
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
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
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.supplier.Supplier","multiUse",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "SupplierLocalService->multiUse->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	 }
	return true;
	}
}
