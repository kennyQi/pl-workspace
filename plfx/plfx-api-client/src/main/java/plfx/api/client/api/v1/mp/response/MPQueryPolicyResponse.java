package plfx.api.client.api.v1.mp.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;

/**
 * 门票政策查询返回
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPQueryPolicyResponse extends ApiResponse {

	private List<TCSupplierPolicySnapshotDTO> policies;

	private Integer totalCount;

	public final static String MP_ORDER_FAIL_POLICY_OVERTIME = "-1"; // 无此景点

	public List<TCSupplierPolicySnapshotDTO> getPolicies() {
		return policies;
	}

	public void setPolicies(List<TCSupplierPolicySnapshotDTO> policies) {
		this.policies = policies;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
