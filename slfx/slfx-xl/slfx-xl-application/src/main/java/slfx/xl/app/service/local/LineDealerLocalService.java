package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.pojo.system.DealerConstants;
import slfx.xl.app.dao.LineDealerDAO;
import slfx.xl.domain.model.crm.LineDealer;
import slfx.xl.pojo.command.DealerCommand;
import slfx.xl.pojo.command.dealer.AuditLineDealerCommand;
import slfx.xl.pojo.command.dealer.CreateLineDealerCommand;
import slfx.xl.pojo.command.dealer.ModifyLineDealerCommand;
import slfx.xl.pojo.qo.LineDealerQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月5日上午9:32:12
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class LineDealerLocalService extends BaseServiceImpl<LineDealer, LineDealerQO, LineDealerDAO>{
	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	LineDealerDAO lineDealerDAO;
	
	@Override
	protected LineDealerDAO getDao() {
		return lineDealerDAO;
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
	public boolean useDealer(AuditLineDealerCommand command){
		LineDealerQO qo=new LineDealerQO();
		qo.setId(command.getId());
		LineDealer entity=this.queryUnique(qo);
		entity.updateStatus(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineDealer","useDealer",JSON.toJSONString(command));
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
	public boolean saveDealer(CreateLineDealerCommand command){
		LineDealer entity=new LineDealer(command);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineDealer","saveDealer",JSON.toJSONString(command));
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
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineDealer","deleteDealer",JSON.toJSONString(command));
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
	public boolean updateDealer(ModifyLineDealerCommand command){
		LineDealerQO qo=new LineDealerQO();
		qo.setId(command.getId());
		LineDealer entity=this.queryUnique(qo);
		entity.update(command);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineDealer","updateDealer",JSON.toJSONString(command));
			domainEventRepository.save(event);
	} catch (Exception e) {
		HgLogger.getInstance().error("tuhualiang", "DealerLocalService->updateDealer->exception:" + HgLogger.getStackTrace(e));
		return false;
	}
		return true;
	}
	
	public boolean multiUse(AuditLineDealerCommand command){
		for (String id1 : command.getIds()) {
			LineDealerQO qo=new LineDealerQO();
			qo.setId(id1);
			LineDealer entity=this.queryUnique(qo);
			AuditLineDealerCommand command1=new AuditLineDealerCommand();
			if("use".equals(command.getFlag())){
				command1.setStatus(DealerConstants.USE);
			}else{
				command1.setStatus(DealerConstants.PRE_USE);
			}
		    entity.updateStatus(command1);
		try {
			this.update(entity);
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineDealer","multiUse",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "DealerLocalService->multiUse->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	 }
	return true;
	}
}
