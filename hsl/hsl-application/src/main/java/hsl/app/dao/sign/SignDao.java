package hsl.app.dao.sign;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.sign.Sign;
import hsl.pojo.qo.SignQo;

/**
 * 
 * @类功能说明：签到Dao
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月17日下午3:06:25
 *
 */
@Repository
public class SignDao extends BaseDao<Sign, SignQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, SignQo qo) {
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(),MatchMode.ANYWHERE));
			}
			
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.eq("mobile", qo.getMobile()));
			}
			
			if(StringUtils.isNotBlank(qo.getDepartment())){
				criteria.add(Restrictions.like("department", qo.getDepartment(),MatchMode.ANYWHERE));
			}
			
			if(StringUtils.isNotBlank(qo.getJob())){
				criteria.add(Restrictions.eq("job", qo.getJob()));
			}

			if (qo.getSigned() != null)
				if(qo.getSigned() == false){
					criteria.add(Restrictions.eq("signed", qo.getSigned()));
				}else{
					criteria.add(Restrictions.eq("signed", qo.getSigned()));
				}
			
			if(StringUtils.isNotBlank(qo.getsID())){
				criteria.add(Restrictions.eq("sID", qo.getsID()));
			}
			
			if(StringUtils.isNotBlank(qo.getActivityName())){
				criteria.add(Restrictions.eq("activityName", qo.getActivityName()));
			}
			
			criteria.addOrder(Order.desc("signTime"));
		}
		return null;
	}

	@Override
	protected Class<Sign> getEntityClass() {
		return Sign.class;
	}

}
