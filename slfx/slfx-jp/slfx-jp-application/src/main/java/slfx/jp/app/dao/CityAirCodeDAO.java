package slfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.CityAirCode;
import slfx.jp.qo.api.CityAirCodeQO;

/**
 * 
 * @类功能说明：城市机场三字码DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午2:46:08
 * @版本：V1.0
 *
 */
@Repository
public class CityAirCodeDAO extends BaseDao<CityAirCode, CityAirCodeQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, CityAirCodeQO qo) {
		if (qo != null) {
			criteria.add(Restrictions.isNotNull("cityAirCode"));
		}
		return criteria;
	}

	@Override
	protected Class<CityAirCode> getEntityClass() {
		return CityAirCode.class;
	}

}
