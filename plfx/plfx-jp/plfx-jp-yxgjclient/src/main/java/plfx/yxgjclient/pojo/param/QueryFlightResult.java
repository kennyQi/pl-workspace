package plfx.yxgjclient.pojo.param;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 航班查询返回参数
 * @author guotx
 * 2015-07-15
 */
public class QueryFlightResult {
	/**
	 * 请求参数列表
	 */
	private QueryFlightParams queryFlightParams;
	/**
	 * 返回的航班信息
	 */
	@XStreamImplicit
	private List<AvailableJourney> availableJourney;
	
	public QueryFlightParams getQueryFlightParams() {
		return queryFlightParams;
	}

	public void setQueryFlightParams(QueryFlightParams queryFlightParams) {
		this.queryFlightParams = queryFlightParams;
	}


	public List<AvailableJourney> getAvailableJourney() {
		return availableJourney;
	}

	public void setAvailableJourney(List<AvailableJourney> availableJourney) {
		this.availableJourney = availableJourney;
	}
}
