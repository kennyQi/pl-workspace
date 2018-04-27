package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.company.Member;
import hsl.pojo.qo.company.HslMemberQO;
@Repository
public class MemberDao extends BaseDao<Member, HslMemberQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslMemberQO qo) {
		if(StringUtils.isNotBlank(qo.getDepartmentId())){
			criteria.add(Restrictions.eq("department.id", qo.getDepartmentId()));
		}
		if(StringUtils.isNotBlank(qo.getId())){
			criteria.add(Restrictions.eq("id", qo.getId()));
		}
		if(StringUtils.isNotBlank(qo.getSearchName())){
			criteria.add(Restrictions.like("name", qo.getSearchName(),MatchMode.ANYWHERE));
		}
		//为null查询所有，非空作为条件
		if(qo.getIsDel()==null){
			
		}else{
			if(qo.getIsDel()){
				
			}else{
				criteria.add(Restrictions.eq("isDel", 0));//0为在职，不显示已离职人员
			}
		}
		
		//按照成员名称排序
		criteria.addOrder(Order.asc("name"));
		return criteria;
	}

	@Override
	protected Class<Member> getEntityClass() {
		return Member.class;
	}

}
