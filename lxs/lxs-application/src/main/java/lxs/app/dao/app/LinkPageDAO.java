package lxs.app.dao.app;

import hg.common.component.BaseDao;
import lxs.domain.model.app.LinkPage;
import lxs.pojo.qo.app.LinkPageQO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：引导页DAO
 * @类修改者：
 * @修改日期：2015年9月18日上午10:51:13
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午10:51:13
 */
@Repository
public class LinkPageDAO extends BaseDao<LinkPage, LinkPageQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LinkPageQO qo) {
		criteria.addOrder(Order.asc("sort"));
		return criteria;
	}

	@Override
	protected Class<LinkPage> getEntityClass() {
		return LinkPage.class;
	}

}
