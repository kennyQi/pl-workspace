package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.common.util.XStreamUtil;
import plfx.yxgjclient.pojo.param.QueryFlightParams;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 航班查询参数
 * @author guotx
 * 2015-07-17
 */
@XStreamAlias("queryFlightRQ")
public class QueryFlightRQ {
	private String sign;
	/**
	 * 航班查询请求参数列表
	 */
	private QueryFlightParams queryFlightParams;
	public QueryFlightRQ(){
		queryFlightParams=new QueryFlightParams();
	}
	
	public QueryFlightParams getQueryFlightParams() {
		return queryFlightParams;
	}

	public void setQueryFlightParams(QueryFlightParams queryFlightParams) {
		this.queryFlightParams = queryFlightParams;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public static void main(String[] args) {
		QueryFlightRQ rq=new QueryFlightRQ();
		rq.queryFlightParams.setServiceName("queryFlight");
		rq.queryFlightParams.setDstCity("HKG");
		rq.queryFlightParams.setIsDirect("1");
		rq.queryFlightParams.setOrgCity("SHA");
		rq.queryFlightParams.setOrgDate("2014-10-10");
		rq.queryFlightParams.setUserName("qyduan");
		//String xml=rq.queryFlightParams.toXML();
		String xml=null;
		//System.out.println("----------------------");
		
		xml=XStreamUtil.objectToXML(rq);
		//System.out.println(xml);
/*		
		xml="<airComp />"+
"<airportOnly />"+
"<dstCity>HKG</dstCity>"+
"<dstDate />"+
"<isDirect>1</isDirect>"+
"<midAirline />"+
"<orgCity>SHA</orgCity>"+
"<orgDate>2014-10-10<orgDate/>"+
"<passType />"+
"<serviceName>queryFlight</serviceName>"+
"<signUserName />"+
"<userName>qyduan</userName>";
		//TODO 测试发现md5加密和文档中给的不一样 
		System.out.println(Md5Util.MD5(xml));
*/
	}
}
