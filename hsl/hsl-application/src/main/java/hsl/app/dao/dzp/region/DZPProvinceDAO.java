package hsl.app.dao.dzp.region;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.meta.DZPProvince;
import hsl.pojo.qo.dzp.region.DZPProvinceQO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 电子票务-省份Dao
 * Created by hgg on 2016/3/7.
 */
@Repository("DZPProvinceDAO")
public class DZPProvinceDAO extends BaseDao<DZPProvince,DZPProvinceQO> {

    @Override
    protected Criteria buildCriteria(Criteria criteria, DZPProvinceQO qo) {
        criteria.addOrder(Order.asc("id"));
        return criteria;
    }

    @Override
    protected Class<DZPProvince> getEntityClass() {
        return DZPProvince.class;
    }
}
