package lxs.app.dao.mp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.DzpwArea;
import lxs.pojo.qo.mp.DZPWAreaQO;
import hg.common.component.BaseDao;

@Repository
public class DZPWAreaDAO extends BaseDao<DzpwArea, DZPWAreaQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPWAreaQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getCode())){
				criteria.add(Restrictions.eq("parentCode", qo.getCode()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<DzpwArea> getEntityClass() {
		return DzpwArea.class;
	}

}
