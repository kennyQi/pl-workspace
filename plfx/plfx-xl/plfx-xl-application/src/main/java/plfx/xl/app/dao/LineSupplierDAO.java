package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.crm.LineSupplier;
import plfx.xl.pojo.qo.LineSupplierQO;

/**
 * 
 * 
 *@类功能说明：线路供应商DAO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日上午10:19:22
 *
 */
@Repository
public class LineSupplierDAO extends BaseDao<LineSupplier, LineSupplierQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSupplierQO qo) {
		
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(qo.getNameLike()?Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE)
						:Restrictions.eq("name", qo.getName()));
			}
			
			if(qo.getStatus() != null){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id",qo.getId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineSupplier> getEntityClass() {
		return LineSupplier.class;
	}

}
