package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryFlightResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 航班查询返回结果
 * @author guotx
 * 2015-07-15
 */
@XStreamAlias("queryFlightRS")
public class QueryFlightRS extends BaseResult{
	/**
	 * 航班查询返回参数列表
	 */
	private QueryFlightResult queryFlightResult;

	public QueryFlightResult getQueryFlightResult() {
		return queryFlightResult;
	}

	public void setQueryFlightResult(QueryFlightResult queryFlightResult) {
		this.queryFlightResult = queryFlightResult;
	}
}
