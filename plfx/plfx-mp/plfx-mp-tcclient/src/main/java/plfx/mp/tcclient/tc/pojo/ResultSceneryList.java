package plfx.mp.tcclient.tc.pojo;

import java.util.List;

import plfx.mp.tcclient.tc.domain.Scenery;

/**
 * 用于调用同城获取景区请求返回结果
 * @author zhangqy
 *
 */
public class ResultSceneryList extends Result {
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
	 * 图片URL前缀
	 */
	private String imgbaseURL;
	/**
	 * 景点列表
	 */
	private List<Scenery> scenery;
//	
//	public static Result getResult(String resultCode,String resultMessage,Integer page,Integer pageSize,
//			Integer totalPage,Integer totalCount,String baseUrl,List<Scenery> scenery){
//		ResultSceneryList result=new  ResultSceneryList();
//		result.setBaseUrl(baseUrl);
//		result.setPage(page);
//		result.setPageSize(pageSize);
//		result.setResultCode(resultCode);
//		result.setResultMessage(resultMessage);
//		result.setScenery(scenery);
//		result.setTotalCount(totalCount);
//		result.setTotalPage(totalPage);
//		return result;
//	}
	
	
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
	public String getImgbaseURL() {
		return imgbaseURL;
	}
	public void setImgbaseURL(String imgbaseURL) {
		this.imgbaseURL = imgbaseURL;
	}
	public List<Scenery> getScenery() {
		return scenery;
	}
	public void setScenery(List<Scenery> scenery) {
		this.scenery = scenery;
	}
	
}
