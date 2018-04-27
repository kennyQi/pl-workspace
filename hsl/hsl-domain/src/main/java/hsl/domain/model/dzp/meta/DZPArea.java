package hsl.domain.model.dzp.meta;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.*;

/**
 * 地区
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "AREA")
public class DZPArea extends BaseModel {

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 所在市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private DZPCity fromCity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DZPCity getFromCity() {
		return fromCity;
	}

	public void setFromCity(DZPCity fromCity) {
		this.fromCity = fromCity;
	}
}
