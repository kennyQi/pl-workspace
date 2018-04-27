package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.user.User;
import hsl.pojo.qo.user.HslUserQO;

@Repository
public class UserDao extends BaseDao<User,HslUserQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslUserQO qo) {
		
		if(qo!=null){

			//账户名
			if(StringUtils.isNotBlank(qo.getLoginName())){
				//判断用户名是否模糊查询
				if(qo.getLoginNameLike()){
					criteria.add(Restrictions.like("authInfo.loginName", qo.getLoginName(), MatchMode.ANYWHERE));
				}else{
					criteria.add(Restrictions.eq("authInfo.loginName", qo.getLoginName()));
				}
			}

			//手机号
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.eq("contactInfo.mobile", qo.getMobile()));
			}
			
			//邮件
			if(StringUtils.isNotBlank(qo.getEmail())){
				criteria.add(Restrictions.eq("contactInfo.email", qo.getEmail()));
			}
			
			//来源
			if(StringUtils.isNotBlank(qo.getSource())){
				criteria.add(Restrictions.eq("baseInfo.source", qo.getSource()));
			}
			//类型
			if(qo.getType()!=null&&qo.getType()!=0){
				criteria.add(Restrictions.eq("baseInfo.type", qo.getType()));
			}
			//注册时间
//			if(StringUtils.isNotBlank(qo.getBeginTime())){
//				criteria.add(Restrictions.and(Restrictions.ge("baseInfo.createTime",
//						DateUtil.dateStr2BeginDate(qo.getBeginTime())), Restrictions.le("baseInfo.createTime", DateUtil.dateStr2EndDate(qo.getEndTime()))));
//			}
			//两个都有值查询区间,否则查询当天
			if(StringUtils.isNotBlank(qo.getBeginTime())&&StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}else if(StringUtils.isNotBlank(qo.getBeginTime())){
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getBeginTime())));
			}else if(StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getEndTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			criteria.addOrder(Order.desc("baseInfo.createTime"));
		}
		return criteria;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}
