package hsl.app.dao.dzp.policy;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.policy.DZPTicketPolicySnapshot;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicySnapshotQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 电子票-门票政策快照DAO
 * Created by hgg on 2016/3/8.
 */
@Repository(value = "dzpTicketPolicySnapshotDAO")
public class DZPTicketPolicySnapshotDAO extends BaseDao<DZPTicketPolicySnapshot,DZPTicketPolicySnapshotQO>{


    @Override
    protected Criteria buildCriteria(Criteria criteria, DZPTicketPolicySnapshotQO qo) {
        return null;
    }

    @Override
    protected Class<DZPTicketPolicySnapshot> getEntityClass() {
        return DZPTicketPolicySnapshot.class;
    }
}
