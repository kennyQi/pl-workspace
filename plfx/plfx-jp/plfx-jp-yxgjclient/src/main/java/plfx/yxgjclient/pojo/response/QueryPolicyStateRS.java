package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryPolicyStateResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询政策信息queryPolicyState返回实体类
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryPolicyStateRS")
public class QueryPolicyStateRS extends BaseResult{
	private QueryPolicyStateResult queryPolicyStateResult;

	public QueryPolicyStateResult getQueryPolicyStateResult() {
		return queryPolicyStateResult;
	}

	public void setQueryPolicyStateResult(
			QueryPolicyStateResult queryPolicyStateResult) {
		this.queryPolicyStateResult = queryPolicyStateResult;
	}

}
