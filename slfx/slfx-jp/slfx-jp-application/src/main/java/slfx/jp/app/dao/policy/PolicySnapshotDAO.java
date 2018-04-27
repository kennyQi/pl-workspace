package slfx.jp.app.dao.policy;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.qo.admin.policy.PolicySnapshotQO;

@Repository
public class PolicySnapshotDAO extends BaseDao<JPPlatformPolicySnapshot, PolicySnapshotQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PolicySnapshotQO qo) {
		return criteria;
	}

	@Override
	protected Class<JPPlatformPolicySnapshot> getEntityClass() {
		return JPPlatformPolicySnapshot.class;
	}

}
