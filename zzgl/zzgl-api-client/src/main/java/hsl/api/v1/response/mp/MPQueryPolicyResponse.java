package hsl.api.v1.response.mp;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.mp.PolicyDTO;

/**
 * 门票政策查询返回
 * 
 * @author yuxx
 * 
 */
public class MPQueryPolicyResponse extends ApiResponse {

	private List<PolicyDTO> policies;

	private Integer totalCount;

	public final static Integer MP_ORDER_FAIL_POLICY_OVERTIME = -1; // 无此景点

	public List<PolicyDTO> getPolicies() {
		return policies;
	}

	public void setPolicies(List<PolicyDTO> policies) {
		this.policies = policies;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
