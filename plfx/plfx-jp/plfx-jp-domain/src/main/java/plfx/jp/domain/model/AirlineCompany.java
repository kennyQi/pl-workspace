package plfx.jp.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hg.common.component.BaseModel;

/**
 * @类功能说明：航空公司
 * @类修改者：
 * @修改日期：2015-7-3上午10:23:47
 * @修改说明：ID为航空公司二字码
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-3上午10:23:47
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX + "AIRLINE_COMPANY")
public class AirlineCompany extends BaseModel {

	/**
	 * 全称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 简称
	 */
	@Column(name = "SIMPLE_NAME", length = 128)
	private String simpleName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

}
