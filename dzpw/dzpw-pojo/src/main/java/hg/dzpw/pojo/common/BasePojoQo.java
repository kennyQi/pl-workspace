package hg.dzpw.pojo.common;

/**
 * @类功能说明：POJO基础查询对象
 * @类修改者：
 * @修改日期：2014-11-14下午4:00:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14下午4:00:04
 */
public class BasePojoQo extends BaseParam {
	
	private static final long serialVersionUID = 1L;

	public final static int PAGE_SIZE_DEFAULT = 20;
	public final static int PAGE_SIZE_MAX = 1000;
	
	private int pageNo = 1;
	private int pageSize = PAGE_SIZE_DEFAULT;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo < 1 ? 1 : pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > PAGE_SIZE_MAX ? PAGE_SIZE_MAX : pageSize;
	}

}
