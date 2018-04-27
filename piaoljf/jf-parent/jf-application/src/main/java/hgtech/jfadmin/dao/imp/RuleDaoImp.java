/**
 * @文件名称：RuleDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:36:07
 */
package hgtech.jfadmin.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hgtech.jfadmin.dto.*;import hgtech.jfadmin.dao.RuleDao;
import hgtech.jfadmin.hibernate.RuleHiberEntity;

/**
 * @类功能说明：hibernate实现的rule dao
 * @类修改者：
 * @修改日期：2014年10月22日上午9:36:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:36:07
 *
 */
@Repository("ruleDao")
public class RuleDaoImp  extends HibernateSimpleDao implements RuleDao{

	@Override
	public Pagination findPagination(Pagination pagination)     {
		
		Criteria criteria=super.getSession().createCriteria(RuleHiberEntity.class);
		if(pagination.getCondition() != null && pagination.getCondition() instanceof RuleDto){
			RuleDto condition = (RuleDto) pagination.getCondition();
			//增加规则查询条件，code
			if(null != condition.getCode() &&  !("".equals(condition.getCode())) ){
				criteria.add(Restrictions.eq("code", condition.getCode()));
			}
			//增加规则查询条件，作废状态
			if(null != condition.getStatus() &&  !("".equals(condition.getStatus())) ){
				criteria.add(Restrictions.eq("status", condition.getStatus()));
			}
			//增加规则查询条件，规则名称
			if(null != condition.getName() && !("".equals(condition.getName()))){
				criteria.add(Restrictions.like("name", "%"+condition.getName()+"%"));
			}
			if(null != condition.getLogicClass() && !("".equals(condition.getLogicClass()))){
				criteria.add(Restrictions.like("logicClass", "%"+condition.getLogicClass()+"%"));
			}
			//增加规则查询条件，期限状态，此字段数据库没有，需要根据时间来判断
			if(null != condition.getIsLimit() &&  !("".equals(condition.getIsLimit()))){
				 String flag = condition.getIsLimit();
				 //当前时间
				 SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
				 String d = sdf.format(new Date());   
				try {
					 Date nowDate = sdf.parse(d);
					 //开始时间小于当前时间  为 未到期   标识：0
					 if("0".equals(flag))
					 {
						 criteria.add(Restrictions.ge("startDate",  new Date()));
					 }
					 //结束时间小于当前时间 为过期  标识：1
					 else if("1".equals(flag))
					 {
						 criteria.add(Restrictions.le("endDate", new Date()));
					 }
					 //开始时间小于当前时间，结束时间大于当前时间  为  在期内   标识：2
					 else if("2".equals(flag))
					 {
						 criteria.add(Restrictions.le("startDate", nowDate));
						 criteria.add(Restrictions.ge("endDate", nowDate));
					 }
				} catch (ParseException e) {
					e.printStackTrace();
				}
				  
				
			}
		}
		//关闭session
		//super.getSession().close();
//		criteria.addOrder(Order.asc("id"));
		return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
	}

	
	@Override
	public void saveRule(RuleHiberEntity  RuleEntity) {
/*		List find = super.find("from RuleHiberEntity where logicClass=? and startDate=? and endDate=? and accountType=? and status=?", RuleEntity.logicClass,RuleEntity.startDate,RuleEntity.endDate,RuleEntity.accountType,RuleEntity.status);
		if(find.size()>0)
			throw new RuntimeException("相同模版，起止日期，积分类型的规则已经存在！");
*/		super.save(RuleEntity);
	}

	@Override
	public void delete(RuleHiberEntity ruleEntity)
	{
		super.delete(ruleEntity);
	}
	
	@Override
	public void update(RuleHiberEntity ruleEntity)
	{
		super.update(ruleEntity);
	}
	
	public RuleHiberEntity queryByCode(String code)
	{
		String hql="from RuleHiberEntity where code=?";
		return (RuleHiberEntity) super.findUnique(hql, code);
	}
	
	public RuleHiberEntity update(String code)
	{
		String hql="from RuleHiberEntity where code=?";
		return (RuleHiberEntity) super.findUnique(hql, code);
	}

	
	public List<RuleHiberEntity> queryByTemplate(String templateCode)
	{
		String hql="from RuleHiberEntity where logicClass=?";
		 return super.find(hql, templateCode);
	}
	
	public void upgrade (RuleHiberEntity exRule,RuleHiberEntity newRule)
	{
		 this.delete(exRule);
		 this.save(newRule);
	}
	
	public List<RuleHiberEntity> findAll(){
		List list = getSession().createCriteria(RuleHiberEntity.class).list();
		return list;
	}
	
	
}
