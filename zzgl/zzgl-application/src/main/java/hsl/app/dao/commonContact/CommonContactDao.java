package hsl.app.dao.commonContact;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.commonContact.CommonContact;
import hsl.pojo.qo.CommonContact.CommonContactQO;
@Repository
public class CommonContactDao extends BaseDao<CommonContact,CommonContactQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, CommonContactQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(),MatchMode.START));
			}
			if(StringUtils.isNotBlank(qo.getUserId())){
				Criteria criteria1=criteria.createCriteria("user");
				criteria1.add(Restrictions.eq("id", qo.getUserId()));
			}
			if(StringUtils.isNotBlank(qo.getCardNo())){
				criteria.add(Restrictions.eq("cardNo", qo.getCardNo()));
			}
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.eq("mobile", qo.getMobile()));
			}
			if(qo.getType()!=null){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			criteria.addOrder(Order.desc("createDate"));
			
		}
		return criteria;
	}

	@Override
	protected Class<CommonContact> getEntityClass() {
		return CommonContact.class;
	}

}
