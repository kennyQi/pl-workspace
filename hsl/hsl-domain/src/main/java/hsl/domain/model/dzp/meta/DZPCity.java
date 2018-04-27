package hsl.domain.model.dzp.meta;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.*;

/**
 * 城市
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "CITY")
public class DZPCity extends BaseModel {

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 所在省
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVINC_ID")
	private DZPProvince fromProvince;

	/**
	 * 获取所在省ID，避免延迟加载
	 *
	 * @return
	 */
	public String parentId() {
		return M.getModelObjectId(fromProvince);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DZPProvince getFromProvince() {
		return fromProvince;
	}

	public void setFromProvince(DZPProvince fromProvince) {
		this.fromProvince = fromProvince;
	}
}
