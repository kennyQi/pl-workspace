package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryRefundStatesResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询退废票信息queryRefundState返回实体
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryRefundStatusRS")
public class QueryRefundStatusRS extends BaseResult{
	private QueryRefundStatesResult queryRefundStatesResult;

	public QueryRefundStatesResult getQueryRefundStatesResult() {
		return queryRefundStatesResult;
	}

	public void setQueryRefundStatesResult(
			QueryRefundStatesResult queryRefundStatesResult) {
		this.queryRefundStatesResult = queryRefundStatesResult;
	}

}
