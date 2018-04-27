package hsl.h5.base.result.jp;
import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.jp.JPOrderDTO;

/**
 * 查询机票订单响应
 * 
 * @author yuxx
 * 
 */
public class JPQueryOrderResult extends ApiResult {

	/**
	 * 机票订单
	 */
	private List<JPOrderDTO> jpOrders;
	
	/**
	 * 订单总条数
	 */
	private Integer totalCount;

	public List<JPOrderDTO> getJpOrders() {
		return jpOrders;
	}

	public void setJpOrders(List<JPOrderDTO> jpOrders) {
		this.jpOrders = jpOrders;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
