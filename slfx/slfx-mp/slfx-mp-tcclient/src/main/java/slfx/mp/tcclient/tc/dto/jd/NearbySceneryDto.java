package slfx.mp.tcclient.tc.dto.jd;


/**
 * 景点明细
 * @author zhangqy
 *
 */
public class NearbySceneryDto extends JdDto{
	/**
	 * 景点Id
	 */
	private String sceneryId;
	/**
	 * 页码
	 */
	private Integer page;
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	
	
	public NearbySceneryDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamNearbyScenery");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultNearbyScenery");
		this.setServiceName("GetNearbyScenery");
	}
	
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
