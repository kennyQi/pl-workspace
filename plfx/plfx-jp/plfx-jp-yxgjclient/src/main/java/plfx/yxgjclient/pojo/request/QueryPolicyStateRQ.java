package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.QueryPolicyStateParams;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 查询政策信息queryPolicyState请求实体类
 * @author guotx
 * 2015-07-01
 */
@XStreamAlias("queryPolicyStateRQ")
public class QueryPolicyStateRQ {
	public QueryPolicyStateRQ(){
		queryPolicyStateParams=new QueryPolicyStateParams();
	}
	private String sign;
	private QueryPolicyStateParams queryPolicyStateParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public QueryPolicyStateParams getQueryPolicyStateParams() {
		return queryPolicyStateParams;
	}

	public void setQueryPolicyStateParams(
			QueryPolicyStateParams queryPolicyStateParams) {
		this.queryPolicyStateParams = queryPolicyStateParams;
	}

	public static void main(String[] args) {
		QueryPolicyStateRQ queryPolicyStateRQ=new QueryPolicyStateRQ();
		queryPolicyStateRQ.queryPolicyStateParams=new QueryPolicyStateParams();
		//queryPolicyStateRQ.setQueryPolicyStateParams(queryPolicyStateParams);
		queryPolicyStateRQ.setSign("gfjsikawu980t43u94039t43jiwut");
		queryPolicyStateRQ.queryPolicyStateParams.setUserName("gongying");
		queryPolicyStateRQ.queryPolicyStateParams.setSignUserName("");
		queryPolicyStateRQ.queryPolicyStateParams.setPolicyId("1863");
		queryPolicyStateRQ.queryPolicyStateParams.setPolicyType("1");
		queryPolicyStateRQ.queryPolicyStateParams.setServiceName("queryPolicyState");
		XStream xstream=new XStream();
		xstream.autodetectAnnotations(true);
		//System.out.println(xstream.toXML(queryPolicyStateRQ));
	}
}
