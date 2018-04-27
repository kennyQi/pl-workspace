package plfx.mp.tcclient.tc.pojo;

import java.util.List;

import plfx.mp.tcclient.tc.domain.ZbScenery;

/**
 * 用于调用同城获取周边景点请求返回结果
 * @author zhangqy
 *
 */
public class ResultNearbyScenery extends Result {
	/**
	 * 页数
	 */
	private Integer page;
	/**
	 * 分页大小
	 */
	private Integer pageSize;
	/**
	 * 总页数
	 */
	private Integer totalPage;
	/**
	 * 总记录数
	 */
	private Integer totalCount;
	/**
	 * 周边景点信息
	 */
	private List<ZbScenery> scenery;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<ZbScenery> getScenery() {
		return scenery;
	}
	public void setScenery(List<ZbScenery> scenery) {
		this.scenery = scenery;
	}
	
}	
