package hsl.domain.model.xl;
import hg.system.model.meta.Address;
/**
 * @类功能说明：线路目的地
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午1:45:08
 */
public class LineDestinationCity{

	/**
	 * 省市
	 */
	private Address address;

	/**
	 * 顺序，从1开始，代表第几站城市
	 */
	private Integer sort;
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}