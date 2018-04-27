package plfx.yxgjclient.pojo.param;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 更多仓位结果参数
 * @author guotx
 * 2015-07-10
 */
public class QueryMoreCabinsResult {
	/**
	 * 航班信息
	 */
	@XStreamImplicit
	private List<AvailableJourney> availableJourney;
	/**
	 * 合作伙伴用户名
	 */
	private String userName;
	/**
	 * 服务名
	 */
	private String serviceName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<AvailableJourney> getAvailableJourney() {
		return availableJourney;
	}

	public void setAvailableJourney(List<AvailableJourney> availableJourney) {
		this.availableJourney = availableJourney;
	}
}
