package hsl.app.dao.dzp.policy;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 电子票务 门票政策DAO
 * Created by huanggg on 2016/3/3.
 */
@Repository("dzpTicketPolicyDAO")
public class DZPTicketPolicyDAO extends BaseDao<DZPTicketPolicy, DZPTicketPolicyQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPTicketPolicyQO qo) {

		if (qo != null) {

			// 间接关联的景区（比如联票含多个景区，有1个符合就算）
			if (ArrayUtils.isNotEmpty(qo.getSingleScenicSpotIds())) {
				DetachedCriteria dc = DetachedCriteria.forClass(DZPTicketPolicy.class, "stp");
				dc.add(Restrictions.in("stp.scenicSpot.id", qo.getSingleScenicSpotIds()));
				dc.add(Restrictions.eqProperty("stp.parent.id", (qo.getAlias() == null ? Criteria.ROOT_ALIAS : qo.getAlias()) + ".id"));
				dc.setProjection(Projections.property("stp.id"));
				criteria.add(Subqueries.exists(dc));
			}

		}

		return criteria;
	}

	/**
	 * 检查查询条件
	 *
	 * @param list 查询列表
	 * @param qo   查询对象
	 */
	private void checkCondition(List<DZPTicketPolicy> list, DZPTicketPolicyQO qo) {
		if (list != null && qo != null) {
			for (DZPTicketPolicy policy : list) {
				if (qo.isPriceFetch()) {
					Hibernate.initialize(policy.getPrice());
				}
				if (qo.isSingleTicketPoliciesFetch()) {
					Hibernate.initialize(policy.getSingleTicketPolicies());
				}
			}
		}
	}

	@Override
	public List<DZPTicketPolicy> queryList(DZPTicketPolicyQO qo, Integer offset, Integer maxSize) {
		List<DZPTicketPolicy> list = super.queryList(qo, offset, maxSize);
		checkCondition(list, qo);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		checkCondition((List<DZPTicketPolicy>) resultPagination.getList(), (DZPTicketPolicyQO) resultPagination.getCondition());
		return resultPagination;
	}

	@Override
	protected Class<DZPTicketPolicy> getEntityClass() {
		return DZPTicketPolicy.class;
	}
}
