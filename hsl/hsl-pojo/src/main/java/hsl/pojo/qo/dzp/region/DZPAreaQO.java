package hsl.pojo.qo.dzp.region;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 电子票务-地区QO
 * Created by hgg on 2016/3/7.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpAreaDAO")
public class DZPAreaQO extends BaseQo {

	/**
	 * 地区名称（模糊匹配）
	 */
	@QOAttr(name = "name", type = QOAttrType.LIKE_ANYWHERE)
	private String name;

	/**
	 * 所属城市ID
	 */
	@QOAttr(name = "fromCity.id")
	private String cityId;

	/**
	 * ID排序（大于0升序，小于0降序）
	 */
	@QOAttr(name = "id", type = QOAttrType.ORDER)
	private Integer idOrder = 1;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
}
