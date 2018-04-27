package hsl.h5.base.result.mp;
import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.mp.MPOrderDTO;

/**
 * 门票订单查询返回
 * 
 * @author yuxx
 * 
 */
public class MPQueryOrderResult extends ApiResult{

	private List<MPOrderDTO> orders;

	private Integer totalCount;


	/**
	 * 订单查询无结果
	 */
	public final static String RESULT_ORDER_NOTFOUND = "-1";
	
	public List<MPOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<MPOrderDTO> orders) {
		this.orders = orders;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
