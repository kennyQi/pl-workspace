package slfx.jp.app.service.spi.policy;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.policy.PolicyLocalService;
import slfx.jp.command.admin.policy.PolicyCommand;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.spi.inter.policy.PolicyService;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月4日下午3:39:50
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
	
	
	
	

}
