package hsl.app.dao.dzp.region;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.pojo.qo.dzp.region.DZPCityQO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 电子票务-城市DAO
 * Created by hgg on 2016/3/7.
 */
@Repository("dzpCityDAO")
public class DZPCityDAO extends BaseDao<DZPCity, DZPCityQO> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPCityQO qo) {
		criteria.addOrder(Order.asc("id"));
		return criteria;
	}

	@Override
	protected Class<DZPCity> getEntityClass() {
		return DZPCity.class;
	}
}
