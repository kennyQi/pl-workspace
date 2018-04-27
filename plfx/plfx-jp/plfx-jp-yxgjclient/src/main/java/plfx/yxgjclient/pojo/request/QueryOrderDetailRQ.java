package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryOrderParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询订单信息queryOrderDetail请求实体类
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryOrderDetailRQ")
public class QueryOrderDetailRQ {
	public QueryOrderDetailRQ(){
		queryOrderParams=new QueryOrderParams();
	}
	private String sign;
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
