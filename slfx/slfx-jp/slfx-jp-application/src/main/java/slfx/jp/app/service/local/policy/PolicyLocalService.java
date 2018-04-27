package slfx.jp.app.service.local.policy;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.policy.PolicyDAO;
import slfx.jp.app.service.local.dealer.DealerLocalService;
import slfx.jp.app.service.local.supplier.SupplierLocalService;
import slfx.jp.command.admin.policy.PolicyCommand;
import slfx.jp.domain.model.dealer.Dealer;
import slfx.jp.domain.model.policy.JPPlatformPolicy;
import slfx.jp.domain.model.supplier.Supplier;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.pojo.system.PolicyConstants;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.inter.policy.PolicyService;

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
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.policy.JPPlatformPolicy","savePolicy",JSON.toJSONString(command));
			domainEventRepository.save(event);
		} catch (Exception e) {
			HgLogger.getInstance().error("tandeng", "PolicyServiceImpl->savePolicy->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	public boolean publicPolicy(PolicyCommand command) {
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.policy.JPPlatformPolicy","publicPolicy",JSON.toJSONString(command));
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
			HgLogger.getInstance().error("tandeng", "PolicyServiceImpl->updateStatusPolicy->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		return true;
	}
	
	public boolean cancelPolicy(PolicyCommand command) {
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.policy.JPPlatformPolicy","cancelPolicy",JSON.toJSONString(command));
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
