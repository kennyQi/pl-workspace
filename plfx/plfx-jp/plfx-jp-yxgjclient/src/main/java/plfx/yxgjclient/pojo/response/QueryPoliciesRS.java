package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryPoliciesResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 获取政策queryPolicies结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("queryPoliciesRS")
public class QueryPoliciesRS extends BaseResult{
	private QueryPoliciesResult queryPoliciesResult;

	public QueryPoliciesResult getQueryPoliciesResult() {
		return queryPoliciesResult;
	}

	public void setQueryPoliciesResult(QueryPoliciesResult queryPoliciesResult) {
		this.queryPoliciesResult = queryPoliciesResult;
	}

}
