package hg.common.model.qo;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;

/**
 * 
 * @类功能说明：dwzQo基础baseQo
 * @类修改者：zzb
 * @修改日期：2014年9月11日上午9:06:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月11日上午9:06:35
 *
 */
public class DwzBasePaginQo extends BaseQo {

	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;

	protected final int DEFAULT_PAGE_SIZE = getPagination().getPageSize();

	/**
	 * 页码
	 */
	protected Integer pageNum = 1;
	
	/**
	 * 分页大小
	 */
	protected Integer numPerPage = DEFAULT_PAGE_SIZE;

	protected Pagination pagination;
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Pagination getPagination() {
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setPageNo(pageNum);
		pagination.setPageSize(numPerPage);
		return pagination;
	}

}
