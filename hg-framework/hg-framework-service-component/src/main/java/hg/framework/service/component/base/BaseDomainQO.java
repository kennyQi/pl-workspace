package hg.framework.service.component.base;

import hg.framework.common.base.BaseObject;
import hg.framework.common.model.LimitQuery;

import java.io.Serializable;

/**
 * 领域查询对象基类（用于服务层内部调用）
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public abstract class BaseDomainQO extends BaseObject implements Serializable {

	/**
	 * 分页限制条件
	 */
	protected LimitQuery limit;

	public LimitQuery getLimit() {
		if (limit == null)
			limit = new LimitQuery();
		return limit;
	}

	public void setLimit(LimitQuery limit) {
		this.limit = limit;
	}
}
