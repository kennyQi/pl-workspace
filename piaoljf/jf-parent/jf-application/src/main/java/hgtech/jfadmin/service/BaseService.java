/**
 * @文件名称：BaseService.java
 * @类路径：hgtech.jfaddmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月14日下午3:56:07
 */
package hgtech.jfadmin.service;

import hg.common.page.Pagination;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月14日下午3:56:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月14日下午3:56:07
 *
 */
public interface BaseService {

	/** 获取Template分页 */
	public abstract Pagination findPagination(Pagination pagination);
	public void add(Entity entity);
	public abstract Entity get(EntityUK uk);
	public abstract void delete(EntityUK uk);
	void refresh();

}