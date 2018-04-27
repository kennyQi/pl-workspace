package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryTicketNoResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询订单票号queryTicketNo结果实体类
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryTicketNoRS")
public class QueryTicketNoRS extends BaseResult{
	private QueryTicketNoResult queryTicketNoResult;

	public QueryTicketNoResult getQueryTicketNoResult() {
		return queryTicketNoResult;
	}

	public void setQueryTicketNoResult(QueryTicketNoResult queryTicketNoResult) {
		this.queryTicketNoResult = queryTicketNoResult;
	}
}
