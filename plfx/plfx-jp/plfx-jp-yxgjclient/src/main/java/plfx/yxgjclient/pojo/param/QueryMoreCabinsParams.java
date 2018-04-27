package plfx.yxgjclient.pojo.param;

/**
 * 更多舱位查询请求参数列表内部类
 * @author guotx
 */
public class QueryMoreCabinsParams extends BaseParam{
	/**
	 * 行程信息
	 */
	private AvailableJourney availableJourney;
	public AvailableJourney getAvailableJourney() {
		return availableJourney;
	}
	public void setAvailableJourney(AvailableJourney availableJourney) {
		this.availableJourney = availableJourney;
	}
	
}
