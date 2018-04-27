/**
 * @文件名称：SessionDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月24日下午12:36:17
 */
package hgtech.jfadmin.dao.imp;

import org.springframework.stereotype.Repository;

import hg.common.component.hibernate.HibernateSimpleDao;
import hgtech.jfcal.dao.RuledSession;
import hgtech.jfcal.dao.SessionDao;


/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月24日下午12:36:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月24日下午12:36:17
 *
 */
@Repository("sessionDao")
public class SessionDaoImp  extends HibernateSimpleDao implements SessionDao{

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.SessionDao#getRuleSession(java.lang.String)
	 */
	@Override
	public RuledSession getRuleSession(String rule) {
		RuleSessionHiberImp rulese = new RuleSessionHiberImp(rule);
		rulese.setSessionFactory(sessionFactory);
		return rulese;
	}

}
