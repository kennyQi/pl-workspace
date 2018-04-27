package lxs.app.dao.line;

import hg.common.component.BaseDao;
import lxs.domain.model.line.LineSnapshot;
import lxs.pojo.qo.line.LineSnapshotQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class LxsLineSnapshotDAO extends BaseDao<LineSnapshot, LineSnapshotQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSnapshotQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			
			if(qo.getIsNew()!=null&&qo.getIsNew()==true){
				criteria.addOrder(Order.desc("createDate"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineSnapshot> getEntityClass() {
		return LineSnapshot.class;
	}

}
