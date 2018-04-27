/**
 * @文件名称：UseDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月26日下午4:55:55
 */
package hgtech.jfadmin.dao.imp;

import java.util.List;

import hg.common.component.hibernate.HibernateSimpleDao;
import hgtech.jfaccount.JfUseDetail;
import hgtech.jfaccount.dao.UseDao;

import org.springframework.stereotype.Repository;

/**
 * @类功能说明：积分使用明细dao
 * @类修改者：
 * @修改日期：2014年11月26日下午4:55:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月26日下午4:55:55
 *
 */
@Repository("useDao")
public class UseDaoImp extends HibernateSimpleDao implements UseDao {

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.dao.UseDao#save(hgtech.jfaccount.JfUseDetail)
	 */
	@Override
	public void save(JfUseDetail useDetail) {
		getSession().save(useDetail);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.dao.UseDao#getUseList(java.lang.String)
	 */
	@Override
	public List<JfUseDetail> getUseList(String useFlowId) {
		return getSession().createQuery("from "+JfUseDetail.class.getName()+" o where o.useFlowId='"+useFlowId+"'").list();
	}

}
