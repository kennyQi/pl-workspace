package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryPoliciesParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 获取政策queryPolicies
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("queryPoliciesRQ")
public class QueryPoliciesRQ {
	public QueryPoliciesRQ(){
		queryPoliciesParams=new QueryPoliciesParams();
	}
	private String sign;
	private QueryPoliciesParams queryPoliciesParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public QueryPoliciesParams getQueryPoliciesParams() {
		return queryPoliciesParams;
	}

	public void setQueryPoliciesParams(QueryPoliciesParams queryPoliciesParams) {
		this.queryPoliciesParams = queryPoliciesParams;
	}

}
