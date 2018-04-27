package slfx.xl.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import slfx.xl.app.component.Assert;
import slfx.xl.app.dao.SalePolicyWorkLogDAO;
import slfx.xl.domain.model.log.SalePolicyWorkLog;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyWorkLogCommand;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.SalePolicyLineQO;
import slfx.xl.pojo.qo.SalePolicyWorkLogQO;
import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

/**
 * @类功能说明：价格政策操作日志内层Service
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:27:31
 * @版本：V1.0
 */
@Service
@Transactional
public class SalePolicyWorkLogLocalService extends BaseServiceImpl<SalePolicyWorkLog,SalePolicyWorkLogQO,SalePolicyWorkLogDAO>{

	@Autowired
	private  SalePolicyWorkLogDAO dao;
	@Autowired
	private SalePolicyLineLocalService policySer;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Override
	protected SalePolicyWorkLogDAO getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：创建价格政策日志
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean create(CreateSalePolicyWorkLogCommand command){
		try {
			Assert.notCommand(command,SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"创建价格政策日志指令");
			Assert.notEmpty(command.getPolicyId(),SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"价格政策ID");
			
			//检查价格政策
			SalePolicyLineQO policyQo = new SalePolicyLineQO();
			policyQo.setId(command.getPolicyId());
			if(policySer.queryCount(policyQo) < 1)
				throw new SlfxXlException(SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"价格政策不存在或已删除");
			
			SalePolicyWorkLog polLog = new SalePolicyWorkLog();
			polLog.create(command);
			dao.save(polLog);
			
			//领域日志
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.log.SalePolicyWorkLog","create",JSON.toJSONString(command));
			domainEvent.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("chenyanshuo", "SalePolicyWorkLogLocalService->create->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
}