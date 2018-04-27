package hsl.app.dao.account;

import hg.common.component.BaseDao;
import hsl.domain.model.user.account.BusinessPartners;
import hsl.pojo.qo.account.BusinessPartnersQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：保存公司信息
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-8-31上午10:55:12
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@Repository
public class BusinessPartnersDao extends BaseDao<BusinessPartners,BusinessPartnersQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BusinessPartnersQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(StringUtils.isNotBlank(qo.getCompanyName())){
				criteria.add(Restrictions.like("companyName", qo.getCompanyName(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getCompanyLinkName())){
				criteria.add(Restrictions.eq("companyLinkName", qo.getCompanyLinkName()));
			}
			if(StringUtils.isNotBlank(qo.getCompanyLinkTel())){
				criteria.add(Restrictions.eq("companyLinkTel", qo.getCompanyLinkTel()));
			}
			
			criteria.addOrder(Order.desc("createTime"));
		}
		return criteria;
	}

	@Override
	protected Class<BusinessPartners> getEntityClass() {
		return BusinessPartners.class;
	}

}
