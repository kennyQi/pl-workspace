package plfx.yxgjclient.pojo.param;

import java.util.List;
/**
 * 获取政策返回参数列表
 * @author guotx
 * 2015-07-11
 */
public class QueryPoliciesResult {
	/**
	 * 请求参数对象
	 */
	private QueryPoliciesParams queryPoliciesParams;
	/**
	 * 有奖励政策信息
	 */
	private List<RewPolicyInfo> rewPolicyInfos;
	/**
	 * 文件政策信息
	 */
	private List<FilePolicyInfo> filePolicyInfos;
	
	public QueryPoliciesParams getQueryPoliciesParams() {
		return queryPoliciesParams;
	}
	public void setQueryPoliciesParams(QueryPoliciesParams queryPoliciesParams) {
		this.queryPoliciesParams = queryPoliciesParams;
	}
	public List<RewPolicyInfo> getRewPolicyInfos() {
		return rewPolicyInfos;
	}
	public void setRewPolicyInfos(List<RewPolicyInfo> rewPolicyInfos) {
		this.rewPolicyInfos = rewPolicyInfos;
	}
	public List<FilePolicyInfo> getFilePolicyInfos() {
		return filePolicyInfos;
	}
	public void setFilePolicyInfos(List<FilePolicyInfo> filePolicyInfos) {
		this.filePolicyInfos = filePolicyInfos;
	}
}
