package hsl.app.dao.dzp.region;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.meta.DZPArea;
import hsl.pojo.qo.dzp.region.DZPAreaQO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 电子票务-地区Dao
 * Created by hgg on 2016/3/7.
 */
@Repository("dzpAreaDAO")
public class DZPAreaDAO extends BaseDao<DZPArea, DZPAreaQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPAreaQO qo) {
		criteria.addOrder(Order.asc("id"));
		return criteria;
	}

	@Override
	protected Class<DZPArea> getEntityClass() {
		return DZPArea.class;
	}
}
