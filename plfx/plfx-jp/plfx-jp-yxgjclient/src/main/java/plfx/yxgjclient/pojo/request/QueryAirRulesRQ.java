package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryAirRulesParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 出票规则queryAirrules
 * @author guotx
 * 2015-06-29
 */
@XStreamAlias("queryAirRulesRQ")
public class QueryAirRulesRQ {
	public QueryAirRulesRQ(){
		queryAirRulesParams=new QueryAirRulesParams();
	}
	private String sign;
	private QueryAirRulesParams queryAirRulesParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public QueryAirRulesParams getQueryAirRulesParams() {
		return queryAirRulesParams;
	}

	public void setQueryAirRulesParams(QueryAirRulesParams queryAirRulesParams) {
		this.queryAirRulesParams = queryAirRulesParams;
	}
}
