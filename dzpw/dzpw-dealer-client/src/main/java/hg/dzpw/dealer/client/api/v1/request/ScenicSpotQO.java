package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientPageQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

import java.util.Date;

/**
 * @类功能说明：景区查询对象
 * @类修改者：
 * @修改日期：2014-11-21下午5:05:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21下午5:05:36
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryScenicSpot)
public class ScenicSpotQO extends BaseClientPageQO {

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 景区名称（模糊查询）
	 */
	private String scenicSpotName;

	/**
	 * 景区名称是否模糊查询
	 */
	private Boolean nameLike = false;

	/**
	 * 省ID
	 */
	private String provinceId;

	/**
	 * 市ID
	 */
	private String cityId;

	/**
	 * 修改时间开始
	 */
	private Date modifyDateBegin;

	/**
	 * 修改时间截止
	 */
	private Date modifyDateEnd;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

}
