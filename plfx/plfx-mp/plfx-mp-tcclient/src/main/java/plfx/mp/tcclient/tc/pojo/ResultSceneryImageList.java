package plfx.mp.tcclient.tc.pojo;

import java.util.List;

import plfx.mp.tcclient.tc.domain.ImagePath;
import plfx.mp.tcclient.tc.domain.ImageSizeList;

/**
 * 用于调用同城获取景区交通信息请求返回结果
 * @author zhangqy
 *
 */
public class ResultSceneryImageList extends Result {
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
	 * 图片信息
	 */
	private List<ImagePath> image;
	/**
	 * 图片基本URL
	 */
	private String imageBaseUrl ;
	/**
	 * 图片信息
	 */
	private ImageSizeList sizeCodeList;
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
	public List<ImagePath> getImage() {
		return image;
	}
	public void setImage(List<ImagePath> image) {
		this.image = image;
	}
	public String getImageBaseUrl() {
		return imageBaseUrl;
	}
	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = imageBaseUrl;
	}
	public ImageSizeList getSizeCodeList() {
		return sizeCodeList;
	}
	public void setSizeCodeList(ImageSizeList sizeCodeList) {
		this.sizeCodeList = sizeCodeList;
	}
	
	
}	
