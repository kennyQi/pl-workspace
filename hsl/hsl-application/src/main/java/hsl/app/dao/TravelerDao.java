package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.user.traveler.Traveler;
import hsl.pojo.qo.user.TravelerQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：游客DAO
 * @类修改者：
 * @修改日期：2015-9-28下午5:43:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-28下午5:43:54
 */
@Repository
public class TravelerDao extends BaseDao<Traveler, TravelerQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TravelerQO qo) {
		return criteria;
	}

	@Override
	protected Class<Traveler> getEntityClass() {
		return Traveler.class;
	}
	
}
