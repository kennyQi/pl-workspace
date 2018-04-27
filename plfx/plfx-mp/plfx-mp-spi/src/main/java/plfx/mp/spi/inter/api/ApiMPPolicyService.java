package plfx.mp.spi.inter.api;

import plfx.api.client.api.v1.mp.request.qo.MPPolicyQO;
import plfx.api.client.api.v1.mp.response.MPQueryPolicyResponse;


public interface ApiMPPolicyService {
	
	/**
	 * 查询供应商政策
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryPolicyResponse apiQueryPolicy(MPPolicyQO qo);
	
}
