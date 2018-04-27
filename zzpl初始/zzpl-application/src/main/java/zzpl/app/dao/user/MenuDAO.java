package zzpl.app.dao.user;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Menu;
import zzpl.pojo.qo.user.MenuQO;
import hg.common.component.BaseDao;
@Repository
public class MenuDAO extends BaseDao<Menu, MenuQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, MenuQO qo) {
		return criteria;
	}

	@Override
	protected Class<Menu> getEntityClass() {
		return Menu.class;
	}

}
