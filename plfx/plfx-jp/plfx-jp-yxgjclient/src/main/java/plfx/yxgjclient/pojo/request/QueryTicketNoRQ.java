package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryOrderParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询订单票号queryTicketNo查询实体
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryTicketNoRQ")
public class QueryTicketNoRQ {
	public QueryTicketNoRQ(){
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
