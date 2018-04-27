package hg.framework.service.component.base.hibernate;

import hg.framework.service.component.base.BaseDomainQO;

/**
 * Hibernate DAO 专用的查询对象
 *
 * @param <T> 主键类型
 * @author zhurz
 */
@SuppressWarnings("serial")
public class BaseHibernateQO<T> extends BaseDomainQO {


	/**
	 * 实体ID
	 */
	private T id;

	/**
	 * 别名
	 */
	private String alias;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
