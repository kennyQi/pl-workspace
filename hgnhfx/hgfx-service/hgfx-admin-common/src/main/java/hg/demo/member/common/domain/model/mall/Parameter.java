package hg.demo.member.common.domain.model.mall;

import javax.persistence.*;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.common.base.BaseStringIdModel;

/**
 * 商城参数表
 * @author guok
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "PARAMETER")
public class Parameter extends BaseStringIdModel {

	/**
	 * 参数名
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	/**
	 * 参数值
	 */
	@Column(name = "VALUE", length = 1024)
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
