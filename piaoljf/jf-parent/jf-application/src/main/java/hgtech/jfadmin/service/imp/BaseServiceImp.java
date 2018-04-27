/**
 * @文件名称：BaseServiceImp.java
 * @类路径：hgtech.jfadmin.service.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月15日下午4:00:07
 */
package hgtech.jfadmin.service.imp;

import java.util.Collection;

import hg.common.page.Pagination;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfadmin.service.BaseService;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfadmin.util.PageUtil;

/**
 * @类功能说明：基本的service
 * @类修改者：
 * @修改日期：2014年10月15日下午4:00:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月15日下午4:00:07
 *
 */
public class BaseServiceImp implements BaseService{

	protected EntityDao entityDao;

	@Override
	public Entity get(EntityUK uk) {
		return entityDao.get(uk);
	}

	/**
	 * @类名：BaseServiceImp.java
	 * @
	 */
	public BaseServiceImp() {
		super();
	}

	@Override
	public Pagination findPagination(Pagination pagination) {
		//entityDao.refresh();
		Collection values = entityDao.getEntities().values();
		return PageUtil.getPage(values, pagination.getPageNo(), pagination.getPageSize()); 
	}

	@Override
	public void add(Entity entity) {
		entityDao.saveEntity(entity);
		entityDao.flush();
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.BaseService#delete(hgtech.jf.entity.EntityUK)
	 */
	@Override
	public void  delete(EntityUK uk) {
		entityDao.delete(uk);
		entityDao.flush();
	}
	
	@Override
	public void refresh(){
		entityDao.refresh();
	}

	
}