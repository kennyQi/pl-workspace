package hg.common.model.qo;

import hg.common.page.Pagination;

/**
 * 基础查询类
 * 
 * @author zhurz
 */
public class DwzPaginQo {
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