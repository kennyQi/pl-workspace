package slfx.mp.qo;

import hg.common.component.BaseQo;

/**
 * 平台景点查询
 * 
 * @author liujz
 */
public class PlatformSpotsQO extends BaseQo {
	private static final long serialVersionUID = -4022979283550230116L;

	/**
	 * 景区名称模糊查询
	 */
	private String name;

	/**
	 * 景区所在地模糊查询
	 */
	private String area;

	/**
	 * 供应商
	 */
	private String supplierId;

	/**
	 * 景点ID
	 */
	private String scenicSpotId;

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
	 * 景点名称是否支持模糊查询
	 */
	private Boolean scenicSpotsNameLike = true;

	/**
	 * 是否加载政策须知
	 */
	private boolean tcPolicyNoticeFetchAble = false;

	/**
	 * 是否加载相关图片
	 */
	private boolean imagesFetchAble = false;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Boolean getScenicSpotsNameLike() {
		return scenicSpotsNameLike;
	}

	public void setScenicSpotsNameLike(Boolean scenicSpotsNameLike) {
		this.scenicSpotsNameLike = scenicSpotsNameLike;
	}

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
