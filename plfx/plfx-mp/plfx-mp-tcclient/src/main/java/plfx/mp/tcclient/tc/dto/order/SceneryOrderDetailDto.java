package plfx.mp.tcclient.tc.dto.order;


/**
 * 景区同步单列表接口
 * @author zhangqy
 *
 */
public class SceneryOrderDetailDto extends OrderDto{
	/**
	 * 数据源库 0读库 1写库 默认为0
	 */
	private Integer writeDB;
	/**
	 * 订单流水号 逗号分隔，最多20个
	 */
	private String serialIds;
	
	public SceneryOrderDetailDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.order.request.ParamSceneryOrderDetail");
		this.setResult("slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderDetail");
		this.setServiceName("GetSceneryOrderDetail");
	}
	public Integer getWriteDB() {
		return writeDB;
	}
	public void setWriteDB(Integer writeDB) {
		this.writeDB = writeDB;
	}
	public String getSerialIds() {
		return serialIds;
	}
	public void setSerialIds(String serialIds) {
		this.serialIds = serialIds;
	}
	
}
