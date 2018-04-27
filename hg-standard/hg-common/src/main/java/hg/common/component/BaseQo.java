package hg.common.component;

import java.io.Serializable;
import java.util.List;

/**
 * 基础查询类 名词解释：query object 简称 qo
 * 
 * @author zhurz
 */
public class BaseQo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 别名
	 */
	private String alias;
	/**
	 * 需要保留的字段
	 */
	private String[] projectionPropertys;
	// ------------------延迟加载条件------------------
	// ------------------排序条件------------------
	// ------------------属性条件------------------
	/**
	 * 实体ID
	 */
	private String id;
	/**
	 * 实体ID集合
	 */
	private List<String> ids;
	// ------------------不包含的属性条件------------------
	/**
	 * 不包含的ID集合
	 */
	private String[] idNotIn;

	// ------------------状态类条件------------------

	// 分页条件
	private Integer pageNo;

	private Integer pageSize;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(String[] idNotIn) {
		this.idNotIn = idNotIn;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String[] getProjectionPropertys() {
		return projectionPropertys;
	}

	public void setProjectionPropertys(String[] projectionPropertys) {
		this.projectionPropertys = projectionPropertys;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}