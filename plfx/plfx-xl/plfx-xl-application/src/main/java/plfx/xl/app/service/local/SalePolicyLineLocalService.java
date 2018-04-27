package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.EntityConvertUtils;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.component.Assert;
import plfx.xl.app.dao.SalePolicyLineDAO;
import plfx.xl.app.dao.SalePolicySnapshotDAO;
import plfx.xl.app.dao.SalePolicyWorkLogDAO;
import plfx.xl.domain.model.crm.LineDealer;
import plfx.xl.domain.model.line.Line;
import plfx.xl.domain.model.log.SalePolicyWorkLog;
import plfx.xl.domain.model.salepolicy.SalePolicy;
import plfx.xl.domain.model.salepolicy.SalePolicySnapshot;
import plfx.xl.pojo.command.salepolicy.CancelSalePolicyCommand;
import plfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import plfx.xl.pojo.command.salepolicy.CreateSalePolicyWorkLogCommand;
import plfx.xl.pojo.command.salepolicy.IssueSalePolicyCommand;
import plfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;
import plfx.xl.pojo.exception.SlfxXlException;
import plfx.xl.pojo.qo.LineDealerQO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.pojo.qo.SalePolicyLineQO;
import plfx.xl.pojo.system.SalePolicyConstants;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：价格政策本地Service
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午10:15:02
 * @版本：V1.0
 */
@Service
@Transactional
public class SalePolicyLineLocalService extends BaseServiceImpl<SalePolicy, SalePolicyLineQO, SalePolicyLineDAO> {
	
	@Autowired
	private SalePolicyLineDAO dao;
	@Autowired
	private LineLocalService lineSer;
	@Autowired
	private LineDealerLocalService dealerSer;
	@Autowired
	private DomainEventRepository domainEvent;
	@Autowired
	private SalePolicyWorkLogDAO salePolicyWorkLogDAO;
	@Autowired
	private SalePolicySnapshotDAO salePolicySnapshotDAO;
	
	@Override
	protected SalePolicyLineDAO getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：创建价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public SalePolicyLineDTO create(CreateSalePolicyCommand command){
		SalePolicyLineDTO result = null;//返回值
		try {
			
			//检查经销商
			LineDealer dealer = null;
			if(StringUtils.isNotBlank(command.getDealerId())){
				LineDealerQO dealerQO = new LineDealerQO(command.getDealerId());
				dealer = dealerSer.queryUnique(dealerQO);
				if(null == dealer)
					throw new SlfxXlException(SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"经销商不存在或已删除");
			}
			
			List<Line> lineList = new ArrayList<Line>();
			LineQO lineQO = new LineQO();
			
			//按线路名称
			if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_LINENAME || (command.getLineIds() != null && command.getLineIds().size() >0)){
				lineQO.setIds(command.getLineIds());
				
			}else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_LINETYPE){  //按线路类别
				lineQO.setType(command.getLineType());
				lineQO.setCityOfType(command.getCityOfLineType());
				
			}else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_PRICE){ //按成人价格
				lineQO.setAdultPriceMax(command.getAdultPriceMax());
				lineQO.setAdultPriceMin(command.getAdultPriceMin());
				lineQO.setBeginDate(command.getStartDate());
				lineQO.setEndDate(command.getEndDate());
				
			}else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_START){
				lineQO.setStartingCityID(command.getStartingCityID());
			}else if(command.getSelectLineType() == SalePolicyConstants.TYPE_BY_ALL){
				//选择全部则不设置线路查询条件
			}
			
			lineList = lineSer.queryList(lineQO);
			
			//查询编号并设置
			SalePolicyLineQO policyQo = new SalePolicyLineQO();
			int count = dao.queryCount(policyQo);
			command.setNumber(count + SalePolicyConstants.POLICY_NUMBER_START + "");
			
			SalePolicy salePolicy = new SalePolicy();
			salePolicy.create(command, lineList, dealer);
			dao.save(salePolicy);
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","create",JSON.toJSONString(command));
			domainEvent.save(event);
			
			//添加快照
			SalePolicySnapshot salePolicySnapshot = new SalePolicySnapshot();
			salePolicySnapshot.create(salePolicy);
			salePolicySnapshotDAO.save(salePolicySnapshot);
			DomainEvent snapshotEvent = new DomainEvent("slfx.xl.domain.model.salepolicy.salePolicySnapshot","create",JSON.toJSONString(salePolicySnapshot));
			domainEvent.save(snapshotEvent);
			
			
			//添加操作日志
			CreateSalePolicyWorkLogCommand logCommand = new CreateSalePolicyWorkLogCommand();
			logCommand.setWorkerName(command.getCreateName());
			logCommand.setPolicyId(salePolicy.getId());
			logCommand.setLogName("新增");
			logCommand.setLogInfoName("新增价格政策");
			SalePolicyWorkLog salePolicyWorkLog = new SalePolicyWorkLog();
			salePolicyWorkLog.create(logCommand);
			salePolicyWorkLogDAO.save(salePolicyWorkLog);
			
			DomainEvent logEvent = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(logCommand));
			domainEvent.save(logEvent);
			
			
			result = EntityConvertUtils.convertEntityToDto(salePolicy, SalePolicyLineDTO.class);
		} catch (Exception e) {
			HgLogger.getInstance().error("chenyanshuo", "SalePolicyLineLocalService->create->exception:" + HgLogger.getStackTrace(e));
		}
		return result;
	}
	
	/**
	 * @方法功能说明：发布价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean isssue(IssueSalePolicyCommand command){
		try {
			
			SalePolicy policy = dao.queryUnique(new SalePolicyLineQO(command.getPolicyId()));
			policy.issue();
			dao.update(policy);
			
			//领域日志
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","isssue",JSON.toJSONString(command));
			domainEvent.save(event);
			
			//添加操作日志
			CreateSalePolicyWorkLogCommand logCommand = new CreateSalePolicyWorkLogCommand();
			logCommand.setWorkerName(command.getWorkerName());
			logCommand.setPolicyId(policy.getId());
			logCommand.setLogName("发布");
			logCommand.setLogInfoName("发布价格政策");
			SalePolicyWorkLog salePolicyWorkLog = new SalePolicyWorkLog();
			salePolicyWorkLog.create(logCommand);
			salePolicyWorkLogDAO.save(salePolicyWorkLog);
			
			DomainEvent logEvent = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(logCommand));
			domainEvent.save(logEvent);
			
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("chenyanshuo", "SalePolicyLineLocalService->isssue->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * @方法功能说明：取消价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean cancel(CancelSalePolicyCommand command){
		try {
			
			SalePolicy policy = dao.queryUnique(new SalePolicyLineQO(command.getPolicyId()));
			policy.cancel(command);
			dao.update(policy);
			
			//领域日志
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","cancel",JSON.toJSONString(command));
			domainEvent.save(event);
			
			//添加操作日志
			CreateSalePolicyWorkLogCommand logCommand = new CreateSalePolicyWorkLogCommand();
			logCommand.setWorkerName(command.getWorkerName());
			logCommand.setPolicyId(policy.getId());
			logCommand.setLogName("取消");
			logCommand.setLogInfoName("取消价格政策");
			SalePolicyWorkLog salePolicyWorkLog = new SalePolicyWorkLog();
			salePolicyWorkLog.create(logCommand);
			salePolicyWorkLogDAO.save(salePolicyWorkLog);
			
			DomainEvent logEvent = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(logCommand));
			domainEvent.save(logEvent);
			
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("chenyanshuo", "SalePolicyLineLocalService->cancel->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * @方法功能说明：更新价格政策状态
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean updateStatus(SalePolicyLineQO policyQo){
		try {
			Assert.notQo(policyQo,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"价格政策查询QO");
			
			List<SalePolicy> policyList = dao.queryList(policyQo);
			for (SalePolicy policy : policyList) {
				policy.updateStatus();
			}
			dao.updateList(policyList);
			
			//领域日志
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","updateStatus",JSON.toJSONString(policyList));
			domainEvent.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("chenyanshuo", "SalePolicyLineLocalService->updateStatus->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：定时开始价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:41:29
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void startSalePolicy(){
		
		try{
			
			//价格政策为已发布且当前日期价格政策是在有效期时间内，则价格政策状态更新为已开始
			SalePolicyLineQO salePolicyLineQO = new SalePolicyLineQO();
			salePolicyLineQO.setIsVaild(true);
			salePolicyLineQO.setSalePolicyLineStatus(SalePolicyConstants.SALE_SUCCESS);
			
			List<SalePolicy> salePolicylist = dao.queryList(salePolicyLineQO);
			List<SalePolicyWorkLog> logList = new ArrayList<SalePolicyWorkLog>();
			if(salePolicylist != null && salePolicylist.size() != 0){
				for(SalePolicy salePolicy:salePolicylist){
					
					salePolicy.getSalePolicyLineStatus().startSalePolicy();
					
					//添加操作日志
					CreateSalePolicyWorkLogCommand createSalePolicyWorkLogCommand = new CreateSalePolicyWorkLogCommand();
					createSalePolicyWorkLogCommand.setLogName("开始");
					createSalePolicyWorkLogCommand.setLogInfoName("开始价格政策");
					createSalePolicyWorkLogCommand.setPolicyId(salePolicy.getId());
					createSalePolicyWorkLogCommand.setWorkerName("定时器");
					SalePolicyWorkLog salePolicyWorkLog = new SalePolicyWorkLog();
					salePolicyWorkLog.create(createSalePolicyWorkLogCommand);
					logList.add(salePolicyWorkLog);
					
				}
				
				dao.updateList(salePolicylist);
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","startSalePolicy",JSON.toJSONString(salePolicylist));
				domainEvent.save(event);
				
				salePolicyWorkLogDAO.saveList(logList);
				DomainEvent logEvent = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(logList));
				domainEvent.save(logEvent);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun","SalePolicyLineLocalService->startSalePolicy->exception:" + HgLogger.getStackTrace(e));
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：定时下架价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:42:02
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void downSalePolicy(){
		
		try{
			
			//价格政策为已开始且当前日期价格政策不在有效期时间内，则价格政策状态更新为已下架
			SalePolicyLineQO salePolicyLineQO = new SalePolicyLineQO();
			salePolicyLineQO.setIsVaild(false);
			salePolicyLineQO.setSalePolicyLineStatus(SalePolicyConstants.SALE_START);
			
			List<SalePolicy> salePolicylist = dao.queryList(salePolicyLineQO);
			List<SalePolicyWorkLog> logList = new ArrayList<SalePolicyWorkLog>();
			if(salePolicylist != null && salePolicylist.size() != 0){
				for(SalePolicy salePolicy:salePolicylist){
					
					salePolicy.getSalePolicyLineStatus().downSalePolicy();
					
					//添加操作日志
					CreateSalePolicyWorkLogCommand createSalePolicyWorkLogCommand = new CreateSalePolicyWorkLogCommand();
					createSalePolicyWorkLogCommand.setLogName("下架");
					createSalePolicyWorkLogCommand.setLogInfoName("下架价格政策");
					createSalePolicyWorkLogCommand.setPolicyId(salePolicy.getId());
					createSalePolicyWorkLogCommand.setWorkerName("定时器");
					SalePolicyWorkLog salePolicyWorkLog = new SalePolicyWorkLog();
					salePolicyWorkLog.create(createSalePolicyWorkLogCommand);
					logList.add(salePolicyWorkLog);
				}
				
				dao.updateList(salePolicylist);
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.salepolicy.SalePolicy","downSalePolicy",JSON.toJSONString(salePolicylist));
				domainEvent.save(event);
				
				salePolicyWorkLogDAO.saveList(logList);
				DomainEvent logEvent = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(logList));
				domainEvent.save(logEvent);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun","SalePolicyLineLocalService->downSalePolicy->exception:" + HgLogger.getStackTrace(e));
		}
		
	}
	
	
	
}