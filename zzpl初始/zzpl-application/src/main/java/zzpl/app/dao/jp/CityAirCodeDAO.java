package zzpl.app.dao.jp;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.sys.CityAirCode;
import zzpl.pojo.qo.sys.CityAirCodeQO;

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
			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
			if (StringUtils.isNotBlank(qo.getCode())) {
				criteria.add(Restrictions.eq("code", qo.getCode()));
			}
			if (StringUtils.isNotBlank(qo.getParentCode())) {
				criteria.add(Restrictions.eq("parentCode", qo.getParentCode()));
			}
			if (StringUtils.isNotBlank(qo.getAirCode())) {
				criteria.add(Restrictions.eq("airCode", qo.getAirCode()));
			}
			if (StringUtils.isNotBlank(qo.getCityAirCode())) {
				criteria.add(Restrictions.eq("cityAirCode", qo.getCityAirCode()));
			}
			if (StringUtils.isNotBlank(qo.getCityJianPin())) {
				criteria.add(Restrictions.eq("cityJianPin", qo.getCityJianPin()));
			}
			if (StringUtils.isNotBlank(qo.getCityQuanPin())) {
				criteria.add(Restrictions.eq("cityQuanPin", qo.getCityQuanPin()));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<CityAirCode> getEntityClass() {
		return CityAirCode.class;
	}

}
