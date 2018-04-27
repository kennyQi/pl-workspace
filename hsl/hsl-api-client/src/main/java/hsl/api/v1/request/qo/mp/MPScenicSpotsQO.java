package hsl.api.v1.request.qo.mp;

import hsl.api.base.ApiPayload;

/**
 * 景区查询
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class MPScenicSpotsQO extends ApiPayload {

	/**
	 * 景区id精确查询
	 */
	private String scenicSpotId;

	/**
	 * 查询内容
	 */
	private String content;
	
	/**
	 * 是否按景区名称模糊查询
	 */
	private Boolean byName=false;

	/**
	 * 是否按景区所在地模糊查询
	 */
	private Boolean byArea=false;

	/**
	 * 查询热门景区，除分页外，其余所有条件为空
	 */
	private Boolean hot=false;

	/**
	 * 返回页码
	 */
	private Integer pageNo=1;

	/**
	 * 返回条目
	 */
	private Integer pageSize=1;
	
	/**
	 * 是否加载政策须知
	 */
	private boolean tcPolicyNoticeFetchAble = false;
	
	/**
	 * 是否加载相关图片
	 */
	private boolean imagesFetchAble = false;
	
	/**
	 * 是否是当前热门景点
	 */
	private Boolean isOpen=true;
	
	/**
	 * 热门景点id
	 */
	private String id;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getByName() {
		return byName;
	}

	public void setByName(Boolean byName) {
		this.byName = byName;
	}

	public Boolean getByArea() {
		return byArea;
	}

	public void setByArea(Boolean byArea) {
		this.byArea = byArea;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getHot() {
		return hot;
	}

	public void setHot(Boolean hot) {
		this.hot = hot;
	}

	public boolean isTcPolicyNoticeFetchAble() {
		return tcPolicyNoticeFetchAble;
	}

	public void setTcPolicyNoticeFetchAble(boolean tcPolicyNoticeFetchAble) {
		this.tcPolicyNoticeFetchAble = tcPolicyNoticeFetchAble;
	}

	public boolean isImagesFetchAble() {
		return imagesFetchAble;
	}

	public void setImagesFetchAble(boolean imagesFetchAble) {
		this.imagesFetchAble = imagesFetchAble;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
