package hg.dzpw.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.domain.model.tourist.Tourist;

@Repository
public class TouristDao extends BaseDao<Tourist, TouristQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, TouristQo qo) {
		if(qo!=null){
			
			//手机号码
			if(StringUtils.isNotBlank(qo.getTelephone())){
				criteria.add(Restrictions.eq("telephone", qo.getTelephone()));
			}
			
			//姓名是否模糊查询
			if (StringUtils.isNotBlank(qo.getName())) {
				if(qo.getNameLike()){
					criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
				}else{
					criteria.add(Restrictions.eq("name", qo.getName()));
				}
			}
			//证件类型
			if(qo.getIdType()!=null && qo.getIdType()!=0){
				criteria.add(Restrictions.eq("idType", qo.getIdType()));
			}
			
			//年龄查询
			if(qo.getAgeStart()!=null){
				criteria.add(Restrictions.ge("age", qo.getAgeStart()));
			}
			if(qo.getAgeEnd()!=null){
				criteria.add(Restrictions.le("age", qo.getAgeEnd()));
			}
			
			//证件号是否模糊查询
			if (StringUtils.isNotBlank(qo.getIdNo())) {
				if(qo.getIdNoLike()){
					criteria.add(Restrictions.like("idNo", qo.getIdNo(), MatchMode.ANYWHERE));
				}else{
					criteria.add(Restrictions.eq("idNo", qo.getIdNo()));
				}
			}
			//性别查询
			if(qo.getGender()!=null  && qo.getGender()!=0){
				criteria.add(Restrictions.eq("gender", qo.getGender()));
			}
			
			//首次购买查询
			if(qo.getFirstBuyDateStart()!=null){
				criteria.add(Restrictions.ge("firstBuyDate", qo.getFirstBuyDateStart()));
			}
			if(qo.getFirstBuyDateEnd()!=null){
				criteria.add(Restrictions.le("firstBuyDate", qo.getFirstBuyDateEnd()));
			}
			
			//首次购买时间排序
			if(null != qo.getFirstBuyDateSort() && qo.getFirstBuyDateSort()>0){
				criteria.addOrder(Order.asc("firstBuyDate"));
			}
			if(null != qo.getFirstBuyDateSort() && qo.getFirstBuyDateSort()<0){
				criteria.addOrder(Order.desc("firstBuyDate"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Tourist> getEntityClass() {
		return Tourist.class;
	}
}