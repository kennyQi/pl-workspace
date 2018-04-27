package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

/**
 * 
 * @类功能说明：区域
 * @类修改者：
 * @修改日期：2016年2月29日下午1:34:46
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2016年2月29日下午1:34:46
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_MP+"AREA")
public class DzpwArea extends BaseModel{

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 父级编码
	 */
	private String parentCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
