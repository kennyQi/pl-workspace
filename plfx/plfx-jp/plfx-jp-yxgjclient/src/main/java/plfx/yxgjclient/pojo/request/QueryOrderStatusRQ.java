package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryOrderParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询订单状态请求参数实体
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryOrderStatusRQ")
public class QueryOrderStatusRQ {
	public QueryOrderStatusRQ(){
		queryOrderParams=new QueryOrderParams();
	}
	private String sign;
	/**
	 * 查询订单状态从参数
	 */
	private QueryOrderParams queryOrderParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public QueryOrderParams getQueryOrderParams() {
		return queryOrderParams;
	}

	public void setQueryOrderParams(QueryOrderParams queryOrderParams) {
		this.queryOrderParams = queryOrderParams;
	}

}
