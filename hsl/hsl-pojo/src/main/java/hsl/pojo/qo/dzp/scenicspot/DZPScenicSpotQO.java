package hsl.pojo.qo.dzp.scenicspot;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 电子票景区查询
 *
 * @author zhurz
 * @since 1.8
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpScenicSpotDAO")
public class DZPScenicSpotQO extends BaseQo {

	/**
	 * 景区名称或门票政策名称
	 */
	private String nameOrPolicyName;

	/**
	 * 是否查询所属门票政策
	 */
	private boolean queryTicketPolicy;

	/**
	 * 是否获取景区图片
	 */
	private boolean picsFetch;

	/**
	 * 省份ID
	 */
	@QOAttr(name = "baseInfo.province.id")
	private String provinceId;

	/**
	 * 城市ID
	 */
	@QOAttr(name = "baseInfo.city.id")
	private String cityId;

	/**
	 * 景区级别
	 */
	@QOAttr(name = "baseInfo.level")
	private Integer level;

	/**
	 * 最低价格区间 - 开始
	 */
	@QOAttr(name = "priceMin", type = QOAttrType.GE)
	private Double priceMinBegin;
	/**
	 * 最低价格区间 - 截止
	 */
	@QOAttr(name = "priceMin", type = QOAttrType.LE)
	private Double priceMinEnd;

	/**
	 * 修改时间排序（大于0升序，小于0降序）
	 */
	@QOAttr(name = "baseInfo.modifyDate", type = QOAttrType.ORDER)
	private Integer modifyDateOrder = 0;

	/**
	 * 最低价格排序（大于0升序，小于0降序）
	 */
	@QOAttr(name = "priceMin", type = QOAttrType.ORDER)
	private Integer priceMinOrder;

	public boolean isQueryTicketPolicy() {
		return queryTicketPolicy;
	}

	public void setQueryTicketPolicy(boolean queryTicketPolicy) {
		this.queryTicketPolicy = queryTicketPolicy;
	}

	public String getNameOrPolicyName() {
		return nameOrPolicyName;
	}

	public void setNameOrPolicyName(String nameOrPolicyName) {
		this.nameOrPolicyName = nameOrPolicyName;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getModifyDateOrder() {
		return modifyDateOrder;
	}

	public void setModifyDateOrder(Integer modifyDateOrder) {
		this.modifyDateOrder = modifyDateOrder;
	}

	public Integer getPriceMinOrder() {
		return priceMinOrder;
	}

	public void setPriceMinOrder(Integer priceMinOrder) {
		this.priceMinOrder = priceMinOrder;
	}

	public Double getPriceMinBegin() {
		return priceMinBegin;
	}

	public void setPriceMinBegin(Double priceMinBegin) {
		this.priceMinBegin = priceMinBegin;
	}

	public Double getPriceMinEnd() {
		return priceMinEnd;
	}

	public void setPriceMinEnd(Double priceMinEnd) {
		this.priceMinEnd = priceMinEnd;
	}

	public boolean isPicsFetch() {
		return picsFetch;
	}

	public void setPicsFetch(boolean picsFetch) {
		this.picsFetch = picsFetch;
	}
}
