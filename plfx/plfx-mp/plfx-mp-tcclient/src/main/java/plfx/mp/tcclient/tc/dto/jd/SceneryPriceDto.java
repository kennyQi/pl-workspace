package plfx.mp.tcclient.tc.dto.jd;


/**
 * 景点明细
 * @author zhangqy
 *
 */
public class SceneryPriceDto extends JdDto{
	/**
	 * 景点Id
	 */
	private String sceneryIds;
	/**
	 * 影响返回内容节点数量1、	简单 2、详细 默认为1
	 */
	private Integer showDetail;
	/**
	 * 是否使用缓存0:不使用    1使用;默认为0
	 */
	private Integer useCache;
	
	
	public SceneryPriceDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamSceneryPrice");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultSceneryPrice");
		this.setServiceName("GetSceneryPrice");
	}
	public String getSceneryIds() {
		return sceneryIds;
	}
	public void setSceneryIds(String sceneryIds) {
		this.sceneryIds = sceneryIds;
	}
	public Integer getShowDetail() {
		return showDetail;
	}
	public void setShowDetail(Integer showDetail) {
		this.showDetail = showDetail;
	}
	public Integer getUseCache() {
		return useCache;
	}
	public void setUseCache(Integer useCache) {
		this.useCache = useCache;
	}
	
	
	
}
