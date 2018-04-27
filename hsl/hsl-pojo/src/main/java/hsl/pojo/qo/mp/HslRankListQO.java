package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

/**
 * 热门票排行的QO，估计没有参数
 * @author zhuxy
 *
 */

@SuppressWarnings("serial")
public class HslRankListQO extends BaseQo{
	/**
	 * 返回条目
	 */
	private Integer pageSize=1;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
