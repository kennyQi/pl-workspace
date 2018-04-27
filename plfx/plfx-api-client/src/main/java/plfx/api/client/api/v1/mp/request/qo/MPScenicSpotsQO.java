package plfx.api.client.api.v1.mp.request.qo;

import plfx.api.client.base.slfx.ApiPayload;

/**
 * 景区查询
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPScenicSpotsQO extends ApiPayload {

	/**
	 * 景区id精确查询
	 */
	private String scenicSpotId;

	/**
	 * 景区名称模糊查询
	 */
	private String name;

	/**
	 * 景区所在地模糊查询
	 */
	private String area;

	/**
	 * 景点级别(格式：A*)
	 */
	private String grade;

	/**
	 * 最低价区间（最小值）
	 */
	private Double amountAdviceMin;

	/**
	 * 最低价区间（最大值）
	 */
	private Double amountAdviceMax;

	/**
	 * 主题ID
	 */
	private String themeId;

	/**
	 * 最低价格排序 <0 DESC >0 ASC
	 */
	private int amountAdviceSort;

	/**
	 * 景区级别排序 <0 DESC >0 ASC
	 */
	private int gradeSort;

	/**
	 * 是否加载政策须知
	 */
	private boolean tcPolicyNoticeFetchAble = false;

	/**
	 * 是否加载相关图片
	 */
	private boolean imagesFetchAble = false;

	/**
	 * 返回页码
	 */
	private Integer pageNo;

	/**
	 * 返回条目
	 */
	private Integer pageSize;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Double getAmountAdviceMin() {
		return amountAdviceMin;
	}

	public void setAmountAdviceMin(Double amountAdviceMin) {
		this.amountAdviceMin = amountAdviceMin;
	}

	public Double getAmountAdviceMax() {
		return amountAdviceMax;
	}

	public void setAmountAdviceMax(Double amountAdviceMax) {
		this.amountAdviceMax = amountAdviceMax;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public int getAmountAdviceSort() {
		return amountAdviceSort;
	}

	public void setAmountAdviceSort(int amountAdviceSort) {
		this.amountAdviceSort = amountAdviceSort;
	}

	public int getGradeSort() {
		return gradeSort;
	}

	public void setGradeSort(int gradeSort) {
		this.gradeSort = gradeSort;
	}

}
