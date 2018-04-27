package slfx.jp.app.service.local.dealer;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.dealer.DealerDAO;
import slfx.jp.command.admin.dealer.DealerCommand;
import slfx.jp.domain.model.dealer.Dealer;
import slfx.jp.pojo.system.DealerConstants;
import slfx.jp.qo.admin.dealer.DealerQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年7月31日下午4:42:43
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
	public boolean useDealer(DealerCommand command){
		DealerQO qo=new DealerQO();
		qo.setId(command.getId());
		Dealer entity=this.queryUnique(qo);
		entity.updateStatus(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.dealer.Dealer","useDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "DealerLocalService->useDealer->exception:" + HgLogger.getStackTrace(e));
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
	public boolean saveDealer(DealerCommand command){
		Dealer entity=new Dealer(command);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.dealer.Dealer","saveDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "DealerLocalService->saveDealer->exception:" + HgLogger.getStackTrace(e));
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
	public boolean deleteDealer(DealerCommand command){
		try {
			this.deleteById(command.getId());
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.dealer.Dealer","deleteDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "DealerLocalService->deleteDealer->exception:" + HgLogger.getStackTrace(e));
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
	public boolean updateDealer(DealerCommand command){
		DealerQO qo=new DealerQO();
		qo.setId(command.getId());
		Dealer entity=this.queryUnique(qo);
		entity.update(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.dealer.Dealer","updateDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
	} catch (Exception e) {
		HgLogger.getInstance().error("tuhualiang", "DealerLocalService->updateDealer->exception:" + HgLogger.getStackTrace(e));
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
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.dealer.Dealer","multiUse",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "DealerLocalService->multiUse->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	 }
	return true;
	}
	
}
