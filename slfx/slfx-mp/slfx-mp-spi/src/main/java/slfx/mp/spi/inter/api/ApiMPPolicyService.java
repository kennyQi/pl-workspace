package slfx.mp.spi.inter.api;

import slfx.api.v1.request.qo.mp.MPPolicyQO;
import slfx.api.v1.response.mp.MPQueryPolicyResponse;

public interface ApiMPPolicyService {
	
	/**
	 * 查询供应商政策
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryPolicyResponse apiQueryPolicy(MPPolicyQO qo);
	
}
