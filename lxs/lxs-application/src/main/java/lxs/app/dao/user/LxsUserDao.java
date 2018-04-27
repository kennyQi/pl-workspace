package lxs.app.dao.user;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LxsUserDao extends BaseDao<LxsUser,LxsUserQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LxsUserQO qo) {
		
		if(qo!=null){

			//账户名
			if(StringUtils.isNotBlank(qo.getLoginName())){
				//判断用户名是否模糊查询
				criteria.add(Restrictions.eq("authInfo.loginName", qo.getLoginName()));
			}

			//昵称
			if(StringUtils.isNotBlank(qo.getNickName())){
				//判断用户名是否模糊查询
				if(qo.getNickNameLike()){
					criteria.add(Restrictions.like("baseInfo.nickName", qo.getNickName(), MatchMode.ANYWHERE));
				}else{
					criteria.add(Restrictions.eq("baseInfo.nickName", qo.getNickName()));
				}
			}
			
			//登录
			if(StringUtils.isNotBlank(qo.getLoginName())&&StringUtils.isNotBlank(qo.getPassword())){
				//登录查询
					criteria.add(Restrictions.eq("authInfo.loginName", qo.getLoginName()));
					criteria.add(Restrictions.eq("authInfo.password", qo.getPassword()));
			}
			
			//手机号
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.like("contactInfo.mobile", qo.getMobile(),MatchMode.ANYWHERE));
			}
			
			//邮件
			if(StringUtils.isNotBlank(qo.getEmail())){
				criteria.add(Restrictions.eq("contactInfo.email", qo.getEmail()));
			}
			
			//来源
			if(StringUtils.isNotBlank(qo.getSource())){
				criteria.add(Restrictions.eq("baseInfo.source", qo.getSource()));
			}

			//注册时间 两个都有值查询区间,否则查询当天
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
	protected Class<LxsUser> getEntityClass() {
		return LxsUser.class;
	}

}
