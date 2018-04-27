package jxc.app.service.inventory;

import jxc.app.dao.inventory.InventoryDao;
import jxc.domain.model.inventory.Inventory;
import org.springframework.transaction.annotation.Transactional;
import hg.common.component.BaseServiceImpl;
import hg.pojo.qo.InventoryQo;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @类功能说明：库存
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-18 11:07:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-18 11:07:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryService extends BaseServiceImpl<Inventory, InventoryQo, InventoryDao> {

	@Autowired
	private InventoryDao dao;

	@Override
	protected InventoryDao getDao() {
		return dao;
	}

}
