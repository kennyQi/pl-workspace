package jxc.app.dao.warehousing;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.warehouseing.notice.WarehousingNoticeDetail;
import hg.common.component.BaseDao;
import hg.pojo.qo.WarehousingNoticeDetailQo;

@Repository
public class WarehousingNoticeDetailDao extends BaseDao<WarehousingNoticeDetail, WarehousingNoticeDetailQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehousingNoticeDetailQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getWarehousingNoticeId())) {
				criteria.add(Restrictions.eq("warehousingNotice.id", qo.getWarehousingNoticeId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<WarehousingNoticeDetail> getEntityClass() {
		return WarehousingNoticeDetail.class;
	}

}
