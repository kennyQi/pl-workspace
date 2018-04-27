package plfx.yxgjclient.pojo.request;

import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import plfx.yxgjclient.common.util.NullConverter;
import plfx.yxgjclient.pojo.param.BackAvailJourney;
import plfx.yxgjclient.pojo.param.TakeoffAvailJourney;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.Flight;
import plfx.yxgjclient.pojo.param.QueryMoreCabinsParams;

/**
 * 更多舱位queryMoreCabins查询实体
 * @author guotx
 * 2015-07-12
 */
@XStreamAlias("queryMoreCabinsRQ")
public class QueryMoreCabinsRQ {
	private String sign;
	/**
	 * 请求参数列表
	 */
	private QueryMoreCabinsParams queryMoreCabinsParams;
	public QueryMoreCabinsRQ() {
		queryMoreCabinsParams=new QueryMoreCabinsParams();
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		//TODO 根据userName和signUserName生成
		
		this.sign = sign;
	}

	public QueryMoreCabinsParams getQueryMoreCabinsParams() {
		return queryMoreCabinsParams;
	}

	public void setQueryMoreCabinsParams(QueryMoreCabinsParams queryMoreCabinsParams) {
		this.queryMoreCabinsParams = queryMoreCabinsParams;
	}
	public static void main(String[] args) {
		QueryMoreCabinsRQ rq=new QueryMoreCabinsRQ();
		rq.queryMoreCabinsParams=new QueryMoreCabinsParams();
		rq.queryMoreCabinsParams.setServiceName("this is service name");
		rq.queryMoreCabinsParams.setSignUserName("admin");
		rq.queryMoreCabinsParams.setUserName("userName");
		AvailableJourney availableJourney=new AvailableJourney();
		availableJourney.setBackAvailJourney(new BackAvailJourney());
		availableJourney.setTakeoffAvailJourney(new TakeoffAvailJourney());
		rq.queryMoreCabinsParams.setAvailableJourney(availableJourney);
		rq.queryMoreCabinsParams.getAvailableJourney().getBackAvailJourney().setFlights(new ArrayList<Flight>());
		rq.queryMoreCabinsParams.getAvailableJourney().getTakeoffAvailJourney().setFlights(new ArrayList<Flight>());
		Flight flight=new Flight();
		flight.setDstCity("杭州");
		flight.setEndTime("2015-10-10");
		flight.setStartTime("2015-10-09");
		flight.setPlaneType("经济");
		Flight flight2=new Flight();
		flight2.setDstCity("广州");
		flight2.setEndTime("2015-06-08");
		flight2.setFlightNo("jbk-2201");
		rq.queryMoreCabinsParams.getAvailableJourney().getBackAvailJourney().getFlights().add(flight);
		rq.queryMoreCabinsParams.getAvailableJourney().getBackAvailJourney().getFlights().add(flight2);
		XStream xStream=new XStream();
		NullConverter nullConverter=new NullConverter();
		xStream.registerConverter(nullConverter);
		//xStream.processAnnotations(rq.getClass());
		//自动应用注解
		xStream.autodetectAnnotations(true);
		//System.out.println(xStream.toXML(rq));
	}
}
