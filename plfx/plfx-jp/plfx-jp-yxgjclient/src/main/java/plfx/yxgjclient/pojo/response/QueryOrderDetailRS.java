package plfx.yxgjclient.pojo.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import plfx.yxgjclient.pojo.param.QueryOrderDetailResult;

/**
 * 查询订单信息queryOrderDetail返回结果实体
 * @author guotx
 * 2015-07-13
 */
@XStreamAlias("queryOrderDetailRS")
public class QueryOrderDetailRS extends BaseResult{
	/**
	 * 查询订单信息返回参数列表
	 */
	private QueryOrderDetailResult queryOrderDetailResult;
	
	public QueryOrderDetailResult getQueryOrderDetailResult() {
		return queryOrderDetailResult;
	}

	public void setQueryOrderDetailResult(
			QueryOrderDetailResult queryOrderDetailResult) {
		this.queryOrderDetailResult = queryOrderDetailResult;
	}

}
