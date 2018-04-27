package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.LineSupplierDAO;
import slfx.xl.domain.model.crm.LineSupplier;
import slfx.xl.pojo.command.supplier.AuditLineSupplierCommand;
import slfx.xl.pojo.command.supplier.CreateLineSupplierCommand;
import slfx.xl.pojo.command.supplier.ModifyLineSupplierCommand;
import slfx.xl.pojo.qo.LineSupplierQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 *@类功能说明：LOCAL线路供应商SERVICE实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日上午10:18:37
 *
 */
@Transactional
@Service
public class LineSupplierLocalService extends BaseServiceImpl<LineSupplier, LineSupplierQO, LineSupplierDAO>{

	@Autowired
	private LineSupplierDAO lineSupplierDAO;
	
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Override
	protected LineSupplierDAO getDao() {
		return lineSupplierDAO;
	}

	/**
	 * 新增线路供应商
	 * @param command
	 */
	public boolean createLineSupplier(CreateLineSupplierCommand command){
		
		try{
			LineSupplier lineSupplier = new LineSupplier();
			lineSupplier.create(command);
			lineSupplierDAO.save(lineSupplier);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineSupplier","createLineSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "LineSupplierLocalService->createLineSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		
	}
	
	/**
	 * 修改线路供应商信息
	 * @param command
	 * @return
	 */
	public boolean updateLineSupplier(ModifyLineSupplierCommand command){
		
		try{
			LineSupplier lineSupplier = lineSupplierDAO.get(command.getId());
			lineSupplier.update(command);
			lineSupplierDAO.update(lineSupplier);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineSupplier","updateLineSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "LineSupplierLocalService->updateLineSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * 审核线路供应商
	 * @param command
	 * @return
	 */
	public boolean auditLineSupplier(AuditLineSupplierCommand command){
		try{
			LineSupplier lineSupplier = lineSupplierDAO.get(command.getId());
			lineSupplier.audit(command);
			lineSupplierDAO.update(lineSupplier);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.crm.LineSupplier","auditLineSupplier",JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "LineSupplierLocalService->auditLineSupplier->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
}
