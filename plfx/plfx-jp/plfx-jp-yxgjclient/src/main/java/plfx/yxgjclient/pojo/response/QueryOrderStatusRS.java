package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryOrderStatusResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询订单状态queryOrderState返回参数实体类
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryOrderStatusRS")
public class QueryOrderStatusRS extends BaseResult{
	private QueryOrderStatusResult queryOrderStatusResult;

	public QueryOrderStatusResult getQueryOrderStatusResult() {
		return queryOrderStatusResult;
	}

	public void setQueryOrderStatusResult(
			QueryOrderStatusResult queryOrderStatusResult) {
		this.queryOrderStatusResult = queryOrderStatusResult;
	}
}
