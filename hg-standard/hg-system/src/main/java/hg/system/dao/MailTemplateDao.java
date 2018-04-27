package hg.system.dao;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.system.model.mail.MailTemplate;
import hg.system.qo.MailTemplateQo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：邮件模板Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月28日 上午10:22:31
 */
@Repository
public class MailTemplateDao extends BaseDao<MailTemplate,MailTemplateQo> {
	/**
	 * 构建Hibernate的标准查询对象 Criteria
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, MailTemplateQo qo) {
		if(null == qo)
			return criteria;
		
		criteria.addOrder(Order.desc("createDate"));
		if(null != qo.isDefAble())
			criteria.add(Restrictions.eq("defAble",qo.isDefAble()));
		if (StringUtils.isNotBlank(qo.getName())) {
			criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
		}
		//创建时间(这里不考虑时间错误的情况，即开始时间大于结束时间)
		if(!StringUtils.isBlank(qo.getTimeBefore()))
			criteria.add(Restrictions.gt("createDate",DateUtil.parseDateTime1(qo.getTimeBefore())));
		if(!StringUtils.isBlank(qo.getTimeAfter()))
			criteria.add(Restrictions.lt("createDate",DateUtil.parseDateTime1(qo.getTimeAfter())));
		return criteria;
	}

	@Override
	protected Class<MailTemplate> getEntityClass() {
		return MailTemplate.class;
	}
}