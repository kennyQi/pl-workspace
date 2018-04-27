package plfx.api.client.common;

/**
 * @类功能说明：分页查询对象
 * @类修改者：
 * @修改日期：2014-11-24上午10:47:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:47:24
 */
public class BaseClientPageQO extends BaseClientQO {
	private static final long serialVersionUID = 1L;

	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 页大小
	 */
	private Integer pageSize;

	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}