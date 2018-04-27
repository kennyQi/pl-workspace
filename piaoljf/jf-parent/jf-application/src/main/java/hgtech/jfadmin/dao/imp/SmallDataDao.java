/**
 * @文件名称：SmallDataDao.java
 * @类路径：hgtech.jfaddmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午4:44:20
 */
package hgtech.jfadmin.dao.imp;

import hg.common.page.Pagination;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfadmin.util.PageUtil;
import hgtech.jfcal.model.RuleTemplate;

import java.util.Collection;
import java.util.HashMap;

/**
 * @类功能说明：小数据量的 dao
 * @类修改者：
 * @param <EnUk>
 * @修改日期：2014年10月13日下午4:44:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午4:44:20
 *
 */
public class SmallDataDao implements ISmallDataDao {
	public EntityDao home=
			new BaseEntityFileDao<StringUK, RuleTemplate>(RuleTemplate.class);
	
	/* (non-Javadoc)
	 * @see hgtech.jfaddmin.dao.imp.ISmallDataDao#findAllwithPage(int, int)
	 */
	@Override
	public Pagination findAllwithPage( int pageNo, int pageSize) {
		assert home!=null;
		
		HashMap entities = home.getEntities();

		Collection values = entities.values();
		return PageUtil.getPage(values, pageNo, pageSize);
	}
}
