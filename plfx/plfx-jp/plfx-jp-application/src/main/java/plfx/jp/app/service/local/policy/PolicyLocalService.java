package plfx.jp.app.service.local.policy;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.policy.PolicyDAO;
import plfx.jp.app.service.local.dealer.DealerLocalService;
import plfx.jp.app.service.local.supplier.SupplierLocalService;
import plfx.jp.command.admin.policy.PolicyCommand;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.policy.JPPlatformPolicy;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.jp.pojo.dto.policy.PolicyDTO;
import plfx.jp.pojo.system.PolicyConstants;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.qo.admin.policy.PolicyQO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.policy.PolicyService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:21:52
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PolicyLocalService extends BaseServiceImpl<JPPlatformPolicy, PolicyQO, PolicyDAO>{
	@Autowired
	SupplierLocalService supplierLocalService;
	@Autowired
	DealerLocalService dealerLocalService;
	@Autowired
	PolicyService policyService;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Autowired
	PolicyDAO policyDAO;
	
	@Override
	protected PolicyDAO getDao() {
		return policyDAO;
	}
	public boolean savePolicy(PolicyCommand command) {
		SupplierQO suppQo = new SupplierQO();
		suppQo.setId(command.getSuppId());
		Supplier supplier = supplierLocalService.queryUnique(suppQo);
		
		DealerQO qo = new DealerQO(); 
		qo.setId(command.getDealerId());;
		Dealer dealer = dealerLocalService.queryUnique(qo);
		
		JPPlatformPolicy entity = new JPPlatformPolicy(command,supplier,dealer);
		try {
			this.save(entity);
			DomainEvent event = new DomainEvent("plfx.jp.domain.model.policy.JPPlatformPolicy","savePolicy",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "PolicyServiceImpl->savePolicy->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	public boolean publicPolicy(PolicyCommand command) {
		DomainEvent event = new DomainEvent("plfx.jp.domain.model.policy.JPPlatformPolicy","publicPolicy",JSON.toJSONString(command));
		domainEventRepository.save(event);
		return updateStatusPolicy(command.getId(),PolicyConstants.PUBLIC);
	}
	
	private boolean updateStatusPolicy(String id,String status) {
		PolicyQO qo = new PolicyQO();
		qo.setId(id);
		try {
			JPPlatformPolicy entity = this.queryUnique(qo);
			entity.setStatus(status);
			this.update(entity);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "PolicyServiceImpl->updateStatusPolicy->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean cancelPolicy(PolicyCommand command) {
		DomainEvent event = new DomainEvent("plfx.jp.domain.model.policy.JPPlatformPolicy","cancelPolicy",JSON.toJSONString(command));
		domainEventRepository.save(event);
		return updateStatusPolicy(command.getId(),PolicyConstants.CANCEL);
	}
	
	public PolicyDTO getEffectPolicy(PolicyQO qo) {
		List<PolicyDTO> policyList = policyService.queryList(qo);
		if (policyList != null && policyList.size() > 0) {
			return policyList.get(0);
		}else{
			return null;			
		}
	}
}
