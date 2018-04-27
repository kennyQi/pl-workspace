package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryAirRulesResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 出票规则queryAirRules
 * @author guotx
 * 2015-06-29
 */
@XStreamAlias("queryAirRulesRS")
public class QueryAirRulesRS extends BaseResult{
	private QueryAirRulesResult queryAirRulesResult;

	public QueryAirRulesResult getQueryAirRulesResult() {
		return queryAirRulesResult;
	}

	public void setQueryAirRulesResult(QueryAirRulesResult queryAirRulesResult) {
		this.queryAirRulesResult = queryAirRulesResult;
	}

}
