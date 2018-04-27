package slfx.api.v1.response.mp;

import java.util.List;
import slfx.api.base.ApiResponse;
import slfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;

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
