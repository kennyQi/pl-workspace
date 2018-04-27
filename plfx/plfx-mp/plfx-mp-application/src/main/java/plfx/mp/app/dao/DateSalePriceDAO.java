package plfx.mp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.mp.app.pojo.qo.DateSalePriceQO;
import plfx.mp.domain.model.platformpolicy.DateSalePrice;

@Repository("dateSalePriceDAO_mp")
public class DateSalePriceDAO extends BaseDao<DateSalePrice, DateSalePriceQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DateSalePriceQO qo) {
		return criteria;
	}

	@Override
	protected Class<DateSalePrice> getEntityClass() {
		return DateSalePrice.class;
	}

}
