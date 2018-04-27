package plfx.jp.app.service.spi.policy;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.policy.PolicyLocalService;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.admin.policy.PolicyCommand;
import plfx.jp.pojo.dto.policy.PolicyDTO;
import plfx.jp.qo.admin.policy.PolicyQO;
import plfx.jp.spi.inter.policy.PolicyService;

/**
 * 
 * @类功能说明：政策service实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:26:17
 * @版本：V1.0
 *
 */
@Service("policyService")
public class PolicyServiceImpl extends BaseJpSpiServiceImpl<PolicyDTO, PolicyQO, PolicyLocalService>  implements PolicyService{
	
	@Autowired
	PolicyLocalService policyLocalService;
	@Override
	protected PolicyLocalService getService() {
		return policyLocalService;
	}

	@Override
	protected Class<PolicyDTO> getDTOClass() {
		return PolicyDTO.class;
	}
	
	@Override
	public Pagination queryPolicyList(Pagination pagination) {
		return queryPagination(pagination);
	}

	@Override
	public PolicyDTO queryPolicyDetail(PolicyQO qo) {
		return queryUnique(qo);
	}

	@Override
	public boolean savePolicy(PolicyCommand command) {
		return policyLocalService.savePolicy(command);
	}
	
	@Override
	public boolean publicPolicy(PolicyCommand command) {
		return policyLocalService.publicPolicy(command);
	}
	
	@Override
	public boolean cancelPolicy(PolicyCommand command) {
		return policyLocalService.cancelPolicy(command);
	}
	
	
	
	@Override
	public PolicyDTO getEffectPolicy(PolicyQO qo) {
		return policyLocalService.getEffectPolicy(qo);
		/*
		//去除非法的 价格政策
		for (int i = policyList.size() -1 ; i >= 0 ; i--) {
			String status = policyList.get(i).getStatus();
			if (PolicyConstants.PUBLIC != status &&
					PolicyConstants.EFFECT != status) {
				policyList.remove(i);
			}
		}
		if (policyList.size() > 0) {
			return policyList.get(0);
		}
		return null;
		*/
	}

	@Override
	public boolean deletePolicy(PolicyCommand command) {
		boolean b = true;
		policyLocalService.deleteById(command.getId());
		return b;
	}
	
	
	
	

}
