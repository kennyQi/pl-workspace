package hsl.app.dao.dzp.scenicspot;

import hg.common.annotation.QOAttr;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.app.dao.dzp.policy.DZPTicketPolicyDAO;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dzpScenicSpotDAO")
public class DZPScenicSpotDAO extends BaseDao<DZPScenicSpot, DZPScenicSpotQO> {

	@Autowired
	private DZPTicketPolicyDAO policyDAO;

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPScenicSpotQO qo) {

		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getNameOrPolicyName())) {
				// 模糊查询的名字
				String name = qo.getNameOrPolicyName();

				// 关联的门票政策子查询
				DetachedCriteria dc = DetachedCriteria.forClass(DZPTicketPolicy.class, "gtp");
				DetachedCriteria singleDc = dc.createCriteria("singleTicketPolicies", "stp");
				singleDc.add(Restrictions.eqProperty("stp.scenicSpot.id", Criteria.ROOT_ALIAS + ".id"));
				// 没下架
				dc.add(Restrictions.eq("status.finished", false));
				// 没关闭
				dc.add(Restrictions.eq("status.closed", false));
				dc.add(Restrictions.or(
						Restrictions.like("baseInfo.name", name, MatchMode.ANYWHERE),
						Restrictions.like("baseInfo.shortName", name, MatchMode.ANYWHERE)
				));
				dc.setProjection(Projections.property("gtp.id"));

				// 景区名称模糊匹配
				LogicalExpression nameLike = Restrictions.or(
						Restrictions.like("baseInfo.name", name, MatchMode.ANYWHERE),
						Restrictions.like("baseInfo.shortName", name, MatchMode.ANYWHERE)
				);

				// 门票政策名称模糊匹配
				Criterion policyNameLike = Subqueries.exists(dc);

				// 名称模糊匹配
				criteria.add(Restrictions.or(nameLike, policyNameLike));
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
	private void checkCondition(List<DZPScenicSpot> list, DZPScenicSpotQO qo) {

		if (list != null && qo != null) {

			// 查询所属门票政策
			if (qo.isQueryTicketPolicy()) {
				String[] scenicSpotIds = new String[list.size()];
				for (int i = 0; i < list.size(); i++)
					scenicSpotIds[i] = list.get(i).getId();
				DZPTicketPolicyQO policyQO = new DZPTicketPolicyQO();
				policyQO.setSingleScenicSpotIds(scenicSpotIds);
				policyQO.setType(DZPTicketPolicy.TICKET_POLICY_TYPE_GROUP, DZPTicketPolicy.TICKET_POLICY_TYPE_SINGLE);
				List<DZPTicketPolicy> policies = policyDAO.queryList(policyQO);
				for (DZPScenicSpot scenicSpot : list)
					for (DZPTicketPolicy policy : policies)
						if (policy.hasScenicSpot(scenicSpot.getId()))
							scenicSpot.getTicketPolicies().add(policy);
			}

			// 获取景区图片
			if (qo.isPicsFetch()) {
				for (DZPScenicSpot scenicSpot : list)
					Hibernate.initialize(scenicSpot.getPics());
			}
		}
	}

	@Override
	public List<DZPScenicSpot> queryList(DZPScenicSpotQO qo, Integer offset, Integer maxSize) {
		List<DZPScenicSpot> list = super.queryList(qo, offset, maxSize);
		checkCondition(list, qo);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		checkCondition((List<DZPScenicSpot>) resultPagination.getList(), (DZPScenicSpotQO) resultPagination.getCondition());
		return resultPagination;
	}

	@Override
	protected Class<DZPScenicSpot> getEntityClass() {
		return DZPScenicSpot.class;
	}

}
