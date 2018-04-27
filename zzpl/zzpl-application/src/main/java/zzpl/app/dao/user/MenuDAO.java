package zzpl.app.dao.user;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Menu;
import zzpl.pojo.qo.user.MenuQO;
import hg.common.component.BaseDao;
@Repository
public class MenuDAO extends BaseDao<Menu, MenuQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, MenuQO qo) {
		if (qo!=null) {
			if(qo.getAuthority() != null && qo.getAuthority() != 0){
				criteria.add(Restrictions.eq("authority", qo.getAuthority()));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<Menu> getEntityClass() {
		return Menu.class;
	}

}
