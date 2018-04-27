/**
 * @文件名称：BaseSmallService.java
 * @类路径：hgtech.jfaddmin.service.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月14日下午4:21:50
 */
package hgtech.jfadmin.service.imp;

import java.util.Collection;

import javax.annotation.Resource;

import hg.common.page.Pagination;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfadmin.util.PageUtil;

/**
 * @类功能说明：小数据的基本service
 * @类修改者：
 * @修改日期：2014年10月14日下午4:21:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月14日下午4:21:50
 *
 */
public class BaseSmallService {

	@Resource
	private EntityDao templateDao;

	/**
	 * @类名：BaseSmallService.java
	 * @描述： 
	 * @
	 */
	public BaseSmallService() {
		super();
	}

	public Pagination findPagination(Pagination pagination) {
		Collection values = templateDao.getEntities().values();
		return PageUtil.getPage(values, pagination.getPageNo(), pagination.getPageSize()); 
	}

}