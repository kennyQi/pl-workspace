package hsl.app.dao.dzp.policy;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.policy.DZPTicketPolicyDatePrice;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyDatePriceQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 电子票务-价格日历Dao
 * Created by hgg on 2016/3/7.
 */
@Repository("dzpTicketPolicyDatePriceDAO")
public class DZPTicketPolicyDatePriceDAO extends BaseDao<DZPTicketPolicyDatePrice, DZPTicketPolicyDatePriceQO>{

    @Override
    protected Criteria buildCriteria(Criteria criteria, DZPTicketPolicyDatePriceQO qo) {
        return null;
    }

    @Override
    protected Class<DZPTicketPolicyDatePrice> getEntityClass() {
        return null;
    }
}
