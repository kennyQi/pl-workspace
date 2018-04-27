package slfx.mp.tcclient.tc.pojo.order.response;

import java.util.List;

import slfx.mp.tcclient.tc.domain.order.Order;
import slfx.mp.tcclient.tc.pojo.Result;
/**
 *	景区同步单列表返回信息
 * @author zhangqy
 *
 */
public class ResultSceneryOrderDetail extends Result {
	private List<Order> order;

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
	
}
