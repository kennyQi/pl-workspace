package plfx.api.client.common;

/**
 * @类功能说明：分页查询结果
 * @类修改者：
 * @修改日期：2014-11-24上午10:48:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:48:39
 */
public class ApiPageResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 页码
	 */
	private int pageNo;

	/**
	 * 分页大小
	 */
	private int pageSize;

	/**
	 * 总条目数
	 */
	private int totalCount;

	public ApiPageResponse() {
	}

	public ApiPageResponse(int pageNo, int pageSize, int totalCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}