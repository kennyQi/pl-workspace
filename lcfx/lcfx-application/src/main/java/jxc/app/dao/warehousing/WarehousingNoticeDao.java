package jxc.app.dao.warehousing;

import jxc.domain.model.warehouseing.notice.WarehousingNotice;
import hg.common.component.BaseDao;
import hg.pojo.qo.WarehousingNoticeQo;

import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-17 16:59:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-17 16:59:24
 */
@Repository
public class WarehousingNoticeDao extends BaseDao<WarehousingNotice, WarehousingNoticeQo> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, WarehousingNoticeQo qo) {
		if (qo == null)
			return criteria;
		// 是否生成入库单
		if (qo.getGenerateWarehousingOrder() != null) {
			criteria.add(Restrictions.eq("generateWarehousingOrder", qo.getGenerateWarehousingOrder()));
		}
		// 入库仓库
		if (qo.getWarehouseId() != null) {
			criteria.add(Restrictions.eq("warehouse", qo.getWarehouseId()));
		}
		// 通知单编号
		if (StringUtils.isNotBlank(qo.getWarehousingNoticeCode())) {
			criteria.add(Restrictions.like("warehousingNoticeCode", qo.getWarehousingNoticeCode(), MatchMode.ANYWHERE));
		}
		// 项目名称
		if (qo.getProjectId() != null) {
			criteria.add(Restrictions.eq("project", qo.getProjectId()));
		}
		// 供应商
		if (qo.getSupplierId() != null) {
			criteria.add(Restrictions.eq("supplier", qo.getSupplierId()));
		}
		// 通知时间
		if (qo.getNoticeDateBegin() != null) {
			criteria.add(Restrictions.ge("noticeDate", qo.getNoticeDateBegin()));
		}
		if (qo.getNoticeDateEnd() != null) {
			criteria.add(Restrictions.le("noticeDate", qo.getNoticeDateEnd()));
		}
		// 采购单编号
		if (StringUtils.isNotBlank(qo.getPurchaseOrderCode())) {
			criteria.add(Restrictions.like("purchaseOrderCode", qo.getPurchaseOrderCode(), MatchMode.ANYWHERE));
		}
		return criteria;
	}

	@Override
	protected Class<WarehousingNotice> getEntityClass() {
		return WarehousingNotice.class;
	}
}
