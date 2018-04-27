package lxs.app.dao.line;

import hg.common.component.BaseDao;
import lxs.domain.model.line.LineImage;
import lxs.pojo.qo.line.LineImageQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LxsLineImageDao extends BaseDao<LineImage, LineImageQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineImageQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			if(StringUtils.isNotBlank(qo.getLineDayRouteId())){
				criteria.add(Restrictions.eq("dayRoute.id", qo.getLineDayRouteId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineImage> getEntityClass() {
		return LineImage.class;
	}

}
