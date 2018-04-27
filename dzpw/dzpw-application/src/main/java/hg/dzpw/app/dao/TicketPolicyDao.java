package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.pojo.vo.SettleDetailVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：门票政策Dao
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-14下午3:32:28
 * @版本：V1.0
 */
@Repository
public class TicketPolicyDao extends BaseDao<TicketPolicy, TicketPolicyQo>{
	
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketPolicyQo qo) {
		if (qo != null) {
			//排序
			if (qo.getCreateDateSort() != 0) {
				criteria.addOrder(qo.getCreateDateSort() > 0 ? Order.asc("baseInfo.createDate") : Order.desc("baseInfo.createDate"));
			}
			if (qo.getModifyDateSort() != 0) {
				criteria.addOrder(qo.getModifyDateSort() > 0 ? Order.asc("baseInfo.modifyDate") : Order.desc("baseInfo.modifyDate"));
			}
			
			// 联票名称是否模糊查询
			if(StringUtils.isNotBlank(qo.getTicketPolicyName())){
				if (qo.getTicketPolicyNameLike())
					criteria.add(Restrictions.like("baseInfo.name", qo.getTicketPolicyName(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("baseInfo.name", qo.getTicketPolicyName()));
			}
			
			//创建时间 范围
			if(qo.getCreateDateBegin()!=null){
				criteria.add(Restrictions.ge("baseInfo.createDate", qo.getCreateDateBegin()));
			}
			if(qo.getCreateDateEnd()!=null){
				criteria.add(Restrictions.le("baseInfo.createDate", qo.getCreateDateEnd()));
			}
			
			//销量 范围
			if(qo.getSoldQtyMin()!=null){
				criteria.add(Restrictions.ge("sellInfo.soldQty", qo.getSoldQtyMin()));
			}
			if(qo.getSoldQtyMax()!=null){
				criteria.add(Restrictions.le("sellInfo.soldQty", qo.getSoldQtyMax()));
			}
			
			if(StringUtils.isNotBlank(qo.getKey())){
				criteria.add(Restrictions.eq("baseInfo.key",qo.getKey()));
			}
			// 状态
			if (qo.getStatus() != null&&qo.getStatus().length>0) {
				criteria.add(Restrictions.in("status.current", qo.getStatus()));
			}
			// 类型
			if (qo.getType() != null && qo.getType().length > 0) {
				criteria.add(Restrictions.or(eachEqProperty(qo.getType(), "type")));
			}
			
			//是否逻辑删除
			if(qo.getRemoved() != null){
				criteria.add(Restrictions.eq("status.removed",qo.getRemoved()));
			}
			
			//套票
			if(qo.isGroupTicketPolicyAble()){
				criteria.add(Restrictions.isNull("parent"));
			}
			// 景区
			if (qo.getScenicSpotQo() != null) {
				Criteria scenicSpotCriteria = criteria.createCriteria(
						"scenicSpot", qo.getScenicSpotQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				scenicSpotDao.buildCriteriaOut(scenicSpotCriteria, qo.getScenicSpotQo());
			}
			
			if(StringUtils.isNotBlank(qo.getScenicSpotStr())){
				criteria.add(Restrictions.like("baseInfo.scenicSpotNameStr", qo.getScenicSpotStr(), MatchMode.ANYWHERE));
			}

			// 基准价
			if (qo.getPriceMin() != null) {
				criteria.add(Restrictions.ge("baseInfo.price", qo.getPriceMin()));
			}
			if (qo.getPriceMax() != null) {
				criteria.add(Restrictions.le("baseInfo.price", qo.getPriceMax()));
			}

			// 修改时间
			if (qo.getModifyDateBegin() != null) {
				criteria.add(Restrictions.ge("baseInfo.modifyDate", qo.getModifyDateBegin()));
			}
			if (qo.getModifyDateEnd() != null) {
				criteria.add(Restrictions.le("baseInfo.modifyDate", qo.getModifyDateEnd()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<TicketPolicy> getEntityClass() {
		return TicketPolicy.class;
	}

	@Override
	public List<TicketPolicy> queryList(TicketPolicyQo qo, Integer offset,
			Integer maxSize) {
		List<TicketPolicy> list = super.queryList(qo, offset, maxSize);
		if (qo != null) {
			for (TicketPolicy o : list) {
				if(qo.isSingleTicketPoliciesFetchAble())
					Hibernate.initialize(o.getSingleTicketPolicies());
				
				if(qo.isParentTicketPolicyAble())
					Hibernate.initialize(o.getParent());
				
				if(qo.isScenicFetchAble()){
					List<TicketPolicy> policyList = o.getSingleTicketPolicies();
					for (TicketPolicy policy : policyList) {
						Hibernate.initialize(policy.getScenicSpot());
					}
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		TicketPolicyQo qo = (TicketPolicyQo) pagination.getCondition();
		if (qo != null) {
			List<TicketPolicy> list = (List<TicketPolicy>) resultPagination.getList();
			for (TicketPolicy o : list) {
				if(qo.isSingleTicketPoliciesFetchAble())
					Hibernate.initialize(o.getSingleTicketPolicies());
				
				if(qo.isParentTicketPolicyAble())
					Hibernate.initialize(o.getParent());
				
				if(qo.isScenicFetchAble()){
					List<TicketPolicy> policyList = o.getSingleTicketPolicies();
					for (TicketPolicy policy : policyList) {
						Hibernate.initialize(policy.getScenicSpot());
					}
				}
			}
		}
		return resultPagination;
	}
	
	@Override
	public TicketPolicy queryUnique(TicketPolicyQo qo) {
		TicketPolicy policy = super.queryUnique(qo);
		if(null == qo)
			return policy;
		
		if(qo.isSingleTicketPoliciesFetchAble())
			Hibernate.initialize(policy.getSingleTicketPolicies());
		
		if(qo.isParentTicketPolicyAble())
			Hibernate.initialize(policy.getParent());
		
		if(qo.isScenicFetchAble()){
			List<TicketPolicy> policyList = policy.getSingleTicketPolicies();
			for (TicketPolicy policy2 : policyList) {
				Hibernate.initialize(policy2.getScenicSpot());
			}
		}
		return policy;
	}
	
	/**
	 * @方法功能说明：根据销售日期更新门票状态
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-1上午10:51:51
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void updateTicketPolicyStatus() {

		String hql = "update " +
				"			TicketPolicy " +
				"		set " +
				"			status.current = ? " +
				"		where " +
				"			status.current = ? " +
				"			and type = ? " +
				"			and sellInfo.sellDateEnd <= ? ";
		
		// 联票下的单票长处理
		executeHql(hql, TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH,
				TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE,
				TicketPolicy.TICKET_POLICY_TYPE_GROUP, new Date());
	}
	
	
	
//	/**
//	 * @方法功能说明：景区
//	 * @修改者名字：yangkang
//	 * @修改时间：2015-12-4下午3:51:04
//	 * @参数：@return
//	 * @return:Pagination
//	 */
//	public Pagination querySelfTickePolicy(TicketPolicyQo ticketPolicyQo, boolean selectAll){
//		
//		Pagination pagination = new Pagination(ticketPolicyQo.getPageNo(), ticketPolicyQo.getPageSize(), 0);
//		pagination.setList(new ArrayList<TicketPolicy>());
//		
//		
//		
//		return pagination;
//	}
	
	/**
	 * 接口
	 * @author CaiHuan
	 * @param pagination
	 * @return
	 */
	public Pagination queryPaginationApi(Pagination pagination)
	{
		TicketPolicyQo qo = (TicketPolicyQo) pagination.getCondition();
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		
		
		qsb.append(" select policy");
		
		pub.append(" from ");
		pub.append(" TicketPolicy policy ");
		//查询条件景区id不为空的情况
		if(qo.getScenicSpotQo()!=null)
		{
		pub.append(" where 1=1 and policy.status.removed != 'Y' and policy.status.current = 3");
		pub.append(" and policy.id in (");
		pub.append(" select distinct policy1.parent.id");
		pub.append(" from ");
		pub.append(" TicketPolicy policy1 ");
		pub.append(" join policy1.scenicSpot sc");
		pub.append(" where sc.id = ? and policy1.type in (3,4)) ");
		params.add(qo.getScenicSpotQo().getId());
		}
		//查询条件景区id为空的情况
		else
		{
			pub.append(" left join policy.scenicSpot sc");
			pub.append(" where 1=1 and policy.status.removed != 'Y' and policy.status.current = 3");
		}
		//门票政策
		if(qo.getIds()!=null)
		{
			pub.append(" and policy.id in (?");
			
			for(int i = 0;i<qo.getIds().size();i++)
			{
			params.add(qo.getIds().get(i));
			if(i<qo.getIds().size()-1)
				pub.append(",?");
			}
			pub.append(")");
		}
		
		//政策类型
		if(qo.getType()!=null)
		{
			pub.append(" and policy.type =?");
		    params.add(qo.getType()[0]);
			
		}
		
		//修改开始时间
		if(qo.getModifyDateBegin()!=null)
		{
			pub.append(" and  policy.baseInfo.modifyDate >= ? ");
			params.add(qo.getModifyDateBegin());
		}
		
		//修改结束时间
		if(qo.getModifyDateEnd()!=null)
		{
			pub.append(" and  policy.baseInfo.modifyDate <= ? ");
			params.add(qo.getModifyDateEnd());
		}
		csb.append("select ");
		csb.append("count(distinct policy.id) ");
		
		csb.append(pub);
		qsb.append(pub);
		
		
		Query countQuery = getSession().createQuery(csb.toString());
		for (int i = 0; i < params.size(); i++) {
			countQuery.setParameter(i, params.get(i));
		}
		Long totalCount = (Long) countQuery.uniqueResult();
//		Pagination newpagination = new Pagination(pagination.getPageNo(), qo.getPageSize(), totalCount.intValue());
		pagination.setTotalCount(totalCount.intValue());
		if(totalCount > 0)
		{
			Query query = getSession().createQuery(qsb.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
//			query.setResultTransformer(new AliasToBeanResultTransformer(TicketPolicy.class));
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pagination.getPageSize());
			@SuppressWarnings("unchecked")
			List<TicketPolicy> list = query.list();
			for (TicketPolicy o : list) {
				if(qo.isSingleTicketPoliciesFetchAble())
					Hibernate.initialize(o.getSingleTicketPolicies());
				
				if(qo.isParentTicketPolicyAble())
					Hibernate.initialize(o.getParent());
				
				if(qo.isScenicFetchAble()){
					List<TicketPolicy> policyList = o.getSingleTicketPolicies();
					for (TicketPolicy policy : policyList) {
						Hibernate.initialize(policy.getScenicSpot());
					}
				}
			}
			
			pagination.setList(list);
		}
		return pagination;
	}
}