package plfx.jp.app.service.local.dealer;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.dealer.DealerDAO;
import plfx.jp.command.DealerCommand;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.pojo.system.DealerConstants;
import plfx.jp.qo.admin.dealer.DealerQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:19:05
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class DealerLocalService extends BaseServiceImpl<Dealer, DealerQO, DealerDAO>{
	
	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	DealerDAO dealerDAO;
	
	@Override
	protected DealerDAO getDao() {
		return dealerDAO;
	}
	
	public boolean useDealer(DealerCommand command){
		DealerQO qo=new DealerQO();
		qo.setId(command.getId());
		Dealer entity=this.queryUnique(qo);
		entity.updateStatus(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.dealer.Dealer","useDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "DealerLocalService->useDealer->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean saveDealer(DealerCommand command){
		Dealer entity=new Dealer(command);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.dealer.Dealer","saveDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "DealerLocalService->saveDealer->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean deleteDealer(DealerCommand command){
		try {
			this.deleteById(command.getId());
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.dealer.Dealer","deleteDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "DealerLocalService->deleteDealer->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean updateDealer(DealerCommand command){
		DealerQO qo=new DealerQO();
		qo.setId(command.getId());
		Dealer entity=this.queryUnique(qo);
		entity.update(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.dealer.Dealer","updateDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
	} catch (Exception e) {
		HgLogger.getInstance().error("yuqz", "DealerLocalService->updateDealer->exception:" + HgLogger.getStackTrace(e));
		return false;
	}
		return true;
	}
	
	public boolean multiUse(DealerCommand command){
		for (String id1 : command.getIds()) {
			DealerQO qo=new DealerQO();
			qo.setId(id1);
			Dealer entity=this.queryUnique(qo);
			DealerCommand command1=new DealerCommand();
			if("use".equals(command.getFlag())){
				command1.setStatus(DealerConstants.USE);
			}else{
				command1.setStatus(DealerConstants.PRE_USE);
			}
		    entity.updateStatus(command1);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.dealer.Dealer","multiUse",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "DealerLocalService->multiUse->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	 }
	return true;
	}
	
}
