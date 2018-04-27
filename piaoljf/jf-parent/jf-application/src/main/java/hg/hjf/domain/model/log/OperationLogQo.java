package hg.hjf.domain.model.log;

import java.util.Date;

import javax.persistence.Column;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;

public class OperationLogQo extends BaseQo {
	private String longinName;
	private Date operTime;
	private String startDate;
	private String endDate;
	private String operType;
	private String operData;
	

	public String getOperData() {
		return operData;
	}

	public void setOperData(String operData) {
		this.operData = operData;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getLonginName() {
		return longinName;
	}

	public void setLonginName(String longinName) {
		this.longinName = longinName;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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
