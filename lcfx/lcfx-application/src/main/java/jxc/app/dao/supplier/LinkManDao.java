package jxc.app.dao.supplier;

import hg.common.component.BaseDao;
import hg.pojo.qo.LinkManQO;
import jxc.domain.model.supplier.SupplierLinkMan;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
@Repository
public class LinkManDao extends BaseDao<SupplierLinkMan,LinkManQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LinkManQO qo) {
		criteria.createCriteria("supplier",JoinType.LEFT_OUTER_JOIN);
		if (qo != null) {
			if(StringUtils.isNotBlank(qo.getSupplierId())){
				criteria.add(Restrictions.eq("supplier.id",qo.getSupplierId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<SupplierLinkMan> getEntityClass() {
		return SupplierLinkMan.class;
	}

}
