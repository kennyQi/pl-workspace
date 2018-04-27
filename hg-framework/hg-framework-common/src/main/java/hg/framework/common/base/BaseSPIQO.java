package hg.framework.common.base;

import hg.framework.common.model.LimitQuery;

/**
 * 基础服务提供者接口查询对象
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public abstract class BaseSPIQO extends BaseSPIRequest {

	/**
	 * 分页限制条件
	 */
	private LimitQuery limit;

	public LimitQuery getLimit() {
		if (limit == null)
			limit = new LimitQuery();
		return limit;
	}

	public void setLimit(LimitQuery limit) {
		this.limit = limit;
	}
}
