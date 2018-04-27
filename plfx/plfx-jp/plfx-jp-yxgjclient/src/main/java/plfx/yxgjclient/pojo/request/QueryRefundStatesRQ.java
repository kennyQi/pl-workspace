package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryOrderParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询退废票信息queryRefundState请求实体
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryRefundStatesRQ")
public class QueryRefundStatesRQ {
	public QueryRefundStatesRQ(){
		setQueryRefundTktParams(new QueryOrderParams());
	}
	private String sign;
	private QueryOrderParams queryRefundTktParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public QueryOrderParams getQueryRefundTktParams() {
		return queryRefundTktParams;
	}

	public void setQueryRefundTktParams(QueryOrderParams queryRefundTktParams) {
		this.queryRefundTktParams = queryRefundTktParams;
	}



}
