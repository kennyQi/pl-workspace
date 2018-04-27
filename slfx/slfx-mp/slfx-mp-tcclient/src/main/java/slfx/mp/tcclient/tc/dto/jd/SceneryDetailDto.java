package slfx.mp.tcclient.tc.dto.jd;


/**
 * 景点明细
 * @author zhangqy
 *
 */
public class SceneryDetailDto extends JdDto{
	/**
	 * 景点Id
	 */
	private String sceneryId;
	/**
	 * 坐标系统1.mapbar
	 * 2.百度；不传默认为1
	 */
	private String cs;
	
	public SceneryDetailDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamSceneryDetail");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultSceneryDetail");
		this.setServiceName("GetSceneryDetail");
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	
}
