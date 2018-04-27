package pl.cms.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.ArrayList;
import java.util.List;

@QOConfig(daoBeanId="scenicDao")
@SuppressWarnings("serial")
public class ScenicQO extends BaseQo {

	public final static List<String> SCENIC_GRADE_LIST = new ArrayList<String>();
	static {
		SCENIC_GRADE_LIST.add("A");
		SCENIC_GRADE_LIST.add("AA");
		SCENIC_GRADE_LIST.add("AAA");
		SCENIC_GRADE_LIST.add("AAAA");
		SCENIC_GRADE_LIST.add("AAAAA");
	}
	/**
	 * 景区名称
	 */
	@QOAttr(name = "baseInfo.name", ifTrueUseLike = "nameLike")
	private String scenicName;
	/**
	 * 名称是否模糊查询
	 */
	private Boolean nameLike;
	/**
	 * 开始时间
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.LT)
	private String beginTime;
	/**
	 * 结束时间
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.GT)
	private String endTime;

	@QOAttr(name = "scenicSpotGeographyInfo.city", type = QOAttrType.FATCH_EAGER)
	private Boolean fetchCity = false;

	@QOAttr(name = "scenicSpotGeographyInfo.province", type = QOAttrType.FATCH_EAGER)
	private Boolean fetchProvince = false;

	// 排序字段
	@QOAttr(name = "scenicSpotBaseInfo.createDate", type = QOAttrType.ORDER)
	private int orderByCreateDate;

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public Boolean getFetchCity() {
		return fetchCity;
	}

	public void setFetchCity(Boolean fetchCity) {
		this.fetchCity = fetchCity;
	}

	public Boolean getFetchProvince() {
		return fetchProvince;
	}

	public void setFetchProvince(Boolean fetchProvince) {
		this.fetchProvince = fetchProvince;
	}

	public int getOrderByCreateDate() {
		return orderByCreateDate;
	}

	public void setOrderByCreateDate(int orderByCreateDate) {
		this.orderByCreateDate = orderByCreateDate;
	}

}
