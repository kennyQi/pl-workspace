package slfx.mp.tcclient.tc.dto.jd;


/**
 * 景点明细
 * @author zhangqy
 *
 */
public class SceneryTrafficInfoDto extends JdDto{
	/**
	 * 景点Id
	 */
	private String sceneryId;
	
	public SceneryTrafficInfoDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamSceneryTrafficInfo");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultSceneryTrafficInfo");
		this.setServiceName("GetSceneryTrafficInfo");
	}
	
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	
}
