package hg.system.qo;

public class BaseAuthQo {

	private int pageNo = 1;
	private int pageSize = 20;
	private int offset = 0;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		offset = pageSize * (pageNo - 1);
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
		this.pageNo = offset % pageSize > 0 ? offset / pageSize + 1 : offset
				/ pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
