package hg.framework.common.model;

import hg.framework.common.base.BaseObject;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class Pagination<T> extends BaseObject implements Serializable {

	/**
	 * 默认分页大小
	 */
	public static final int DEF_COUNT = 20;

	/**
	 * 查询总数
	 */
	private int totalCount = 0;

	/**
	 * 分页大小
	 */
	private int pageSize = 20;

	/**
	 * 页码
	 */
	private int pageNo = 1;

	/**
	 * 数据开始索引
	 */
	private int startIndex = 0;

	/**
	 * 分页结果集
	 */
	private List<T> list;

	public Pagination() {

	}

	/**
	 * @param pageNo     页码
	 * @param pageSize   分页大小
	 * @param totalCount 查询总数
	 */
	public Pagination(Integer pageNo, Integer pageSize, int totalCount) {
		this(pageNo, pageSize, totalCount, null);
	}

	/**
	 * @param pageNo     页码
	 * @param pageSize   分页大小
	 * @param totalCount 查询总数
	 * @param list       查询结果
	 */
	public Pagination(Integer pageNo, Integer pageSize, int totalCount, List<T> list) {
		if (pageNo == null) pageNo = 1;
		if (pageSize == null) pageSize = DEF_COUNT;
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
		setList(list);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 调整页码，使其不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int totalPage = getTotalPage();
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}

	/**
	 * 得到总页码
	 */
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	/**
	 * 是否第一页
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * 是否最后一页
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * 下一页页码
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 上一页页码
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 设置查询总数
	 *
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * 设置分页大小
	 *
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}

	/**
	 * 设置页码
	 *
	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		if (pageNo == null || pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}


}
