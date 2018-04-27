package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.CityOfCountry;
import plfx.xl.pojo.qo.LineCityQo;

/****
 * 
 * @类功能说明：国家城市DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日下午1:58:43
 * @版本：V1.0
 *
 */
@Repository
public class CityOfCountryDAO extends BaseDao<CityOfCountry, LineCityQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineCityQo qo) {
		if (qo != null) {
            //城市代码查询
            if(StringUtils.isNotBlank(qo.getCode())){
            	criteria.add(Restrictions.eq("code", qo.getCode()));
            }
            //城市名称查询
            if(StringUtils.isNotBlank(qo.getName())){
            	criteria.add(Restrictions.eq("name", qo.getName()));
            }
            //国家代码查询
            if(StringUtils.isNotBlank(qo.getParentCode())){
            	criteria.add(Restrictions.eq("parentCode", qo.getParentCode()));
            }
            //国家名称查询
            if(StringUtils.isNotBlank(qo.getCountryName())){
            	criteria.add(Restrictions.eq("countryName", qo.getCountryName()));
            }
            
		}
		return criteria;
	}

	@Override
	protected Class<CityOfCountry> getEntityClass() {
		
		return CityOfCountry.class;
	}
}
