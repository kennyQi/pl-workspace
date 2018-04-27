package hsl.app.service.local.line;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.line.DateSalePriceDAO;
import hsl.domain.model.xl.DateSalePrice;
import hsl.pojo.qo.line.DateSalePriceQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：价格日历服务
 * @类修改者：
 * @修改日期：2015-10-9下午3:02:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-9下午3:02:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DateSalePriceLocalService extends BaseServiceImpl<DateSalePrice, DateSalePriceQO, DateSalePriceDAO> {

	@Autowired
	private DateSalePriceDAO dao;

	@Override
	protected DateSalePriceDAO getDao() {
		return dao;
	}

}
