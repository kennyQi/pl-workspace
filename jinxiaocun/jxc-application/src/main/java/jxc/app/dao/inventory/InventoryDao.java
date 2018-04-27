package jxc.app.dao.inventory;

import jxc.domain.model.inventory.Inventory;
import hg.common.component.BaseDao;
import hg.pojo.qo.InventoryQo;

import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：库存
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-18 10:58:44
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-18 10:58:44
 */
@Repository
public class InventoryDao extends BaseDao<Inventory, InventoryQo> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, InventoryQo qo) {
		if (qo == null) return criteria;
		// 仓库
		if (qo.getWarehouseId() != null) {
			criteria.add(Restrictions.eq("warehouse", qo.getWarehouseId()));
		}
		// sku商品信息
		if (qo.getSkuProductId() != null) {
			criteria.add(Restrictions.eq("skuProduct", qo.getSkuProductId()));
		}
		// 供应商
		if (qo.getSupplierId() != null) {
			criteria.add(Restrictions.eq("supplier", qo.getSupplierId()));
		}
		return criteria;
	}

	@Override
	protected Class<Inventory> getEntityClass() {
		return Inventory.class;
	}
}
