package jxc.app.dao.image;

import hg.common.component.BaseDao;
import hg.pojo.qo.SupplierAptitudeImageQO;
import jxc.domain.model.image.SupplierAptitudeImage;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
@Repository
public class SupplierAptitudeImageDao extends BaseDao<SupplierAptitudeImage,SupplierAptitudeImageQO>{
 
	@Override
	protected Criteria buildCriteria(Criteria criteria, SupplierAptitudeImageQO qo) {
		criteria.createCriteria("supplier",JoinType.LEFT_OUTER_JOIN);
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getSupplierId())){
				criteria.add(Restrictions.eq("supplier.id", qo.getSupplierId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<SupplierAptitudeImage> getEntityClass() {
		return SupplierAptitudeImage.class;
	}

}
