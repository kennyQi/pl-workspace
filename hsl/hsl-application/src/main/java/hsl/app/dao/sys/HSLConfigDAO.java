package hsl.app.dao.sys;

import hg.common.component.BaseDao;
import hsl.domain.model.sys.HSLConfig;
import hsl.pojo.qo.sys.HSLConfigQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @author zhurz
 */
@Repository
public class HSLConfigDAO extends BaseDao<HSLConfig, HSLConfigQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HSLConfigQO qo) {
		return criteria;
	}

	@Override
	protected Class<HSLConfig> getEntityClass() {
		return HSLConfig.class;
	}

}
