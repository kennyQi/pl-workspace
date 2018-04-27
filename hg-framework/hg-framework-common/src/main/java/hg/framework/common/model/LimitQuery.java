package hg.framework.common.model;

import java.io.Serializable;

/**
 * 分页查询
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class LimitQuery implements Serializable {

	/**
	 * 分页大小
	 */
	private Integer pageSize;

	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 数据开始索引
	 */
	private Integer startIndex;

	/**
	 * 是否按照pageNo查询
	 * <pre>
	 *     为true时通过pageNo计算startIndex，
	 *     为false时通过startIndex计算pageNo。
	 * </pre>
	 */
	private boolean byPageNo = true;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		if (byPageNo) return pageNo;
		if (pageSize == null || startIndex == null || pageSize < 1 || startIndex < 0) return null;
		return pageNo = (startIndex / pageSize) + 1;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getStartIndex() {
		if (!byPageNo) return startIndex;
		if (pageNo == null || pageSize == null || pageSize < 1 || pageNo < 1) return null;
		return startIndex = pageSize * (pageNo - 1);
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public boolean isByPageNo() {
		return byPageNo;
	}

	public void setByPageNo(boolean byPageNo) {
		this.byPageNo = byPageNo;
	}

}
