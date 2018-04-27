package plfx.mp.tcclient.tc.pojo.order.response;

import java.util.List;

import plfx.mp.tcclient.tc.domain.order.Order;
import plfx.mp.tcclient.tc.pojo.Result;
/**
 * 景区同步单列表返回信息
 * @author zhangqy
 *
 */
public class ResultSceneryOrderList extends Result {
	private Integer page;
	private Integer pageCount;
	private  Integer pageSize;
	private List<Order> order;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
	
}
