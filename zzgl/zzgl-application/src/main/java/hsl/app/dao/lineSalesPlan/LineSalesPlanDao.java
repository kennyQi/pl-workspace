package hsl.app.dao.lineSalesPlan;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hsl.domain.model.lineSalesPlan.LineSalesPlan;
import hsl.domain.model.xl.DateSalePrice;
import hsl.domain.model.xl.DayRoute;
import hsl.domain.model.xl.LineRouteInfo;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:22
 */
@Repository
public class LineSalesPlanDao extends BaseDao<LineSalesPlan,LineSalesPlanQO> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSalesPlanQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getPlanName())){
				criteria.add(Restrictions.like("baseInfo.planName",qo.getPlanName(),MatchMode.ANYWHERE));
			}
			if(qo.isFetchLine()){
				Criteria lineCriteria1=criteria.createCriteria("line");
				Criteria  imagecCriteria=lineCriteria1.createCriteria("lineImageList", JoinType.LEFT_OUTER_JOIN);
				imagecCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
				if(StringUtils.isNotBlank(qo.getLineName())){
					lineCriteria1.add(Restrictions.like("baseInfo.name", qo.getLineName(), MatchMode.ANYWHERE));
				}
			}
			if(qo.getShowStatus()!=null){
				criteria.add(Restrictions.eq("lineSalesPlanStatus.showStatus", qo.getShowStatus()));
			}
			if(qo.getStatus()!=null){
				criteria.add(Restrictions.eq("lineSalesPlanStatus.status", qo.getStatus()));
			}
			if(qo.getStatusArray()!=null&&qo.getStatusArray().length>0){
				criteria.add(Restrictions.in("lineSalesPlanStatus.status", qo.getStatusArray()));
			}
			if(qo.getPlanType()!=null){
				criteria.add(Restrictions.eq("baseInfo.planType", qo.getPlanType()));
			}
			//出发时间 查询
			if(StringUtils.isNotBlank(qo.getBeginDate())&&StringUtils.isNotBlank(qo.getEndDate())){
				criteria.add(Restrictions.between("lineSalesPlanSalesInfo.travelDate", DateUtil.parseDateTime(qo.getBeginDate()),DateUtil.parseDateTime(qo.getEndDate())));
			}else if (StringUtils.isNotBlank(qo.getBeginDate())){
				criteria.add(Restrictions.ge("lineSalesPlanSalesInfo.travelDate", DateUtil.parseDateTime(qo.getBeginDate())));
			}else if(StringUtils.isNotBlank(qo.getEndDate())){
				criteria.add(Restrictions.le("lineSalesPlanSalesInfo.travelDate",DateUtil.parseDateTime(qo.getEndDate())));
			}
			if(qo.isLock()){
				criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
			}
			if(qo.isOrderByPriority()){
				criteria.addOrder(Order.desc("lineSalesPlanSalesInfo.priority"));
			}
			criteria.addOrder(Order.desc("baseInfo.createDate"));
		}
		return criteria;
	}

	@Override
	protected Class<LineSalesPlan> getEntityClass() {
		return LineSalesPlan.class;
	}

	@Override
	public LineSalesPlan queryUnique(LineSalesPlanQO qo) {
		LineSalesPlan lissalesplan=super.queryUnique(qo);
		if(lissalesplan.getLine()!=null){
			LineRouteInfo info = lissalesplan.getLine().getRouteInfo();
			if(info!=null){
				Hibernate.initialize(info.getDayRouteList());
				for(DayRoute dayRoute:info.getDayRouteList()){
					Hibernate.initialize(dayRoute.getLineImageList());

				}
			}
		}
		return lissalesplan;
	}

	@Override
	public List<LineSalesPlan> queryList(LineSalesPlanQO qo) {
		List<LineSalesPlan> lineSalesPlans=super.queryList(qo);
		for(LineSalesPlan lineSalesPlan:lineSalesPlans){
			if(lineSalesPlan.getLine()!=null){
				LineRouteInfo info = lineSalesPlan.getLine().getRouteInfo();
				if(info!=null){
					Hibernate.initialize(info.getDayRouteList());
					for(DayRoute dayRoute:info.getDayRouteList()){
						Hibernate.initialize(dayRoute.getLineImageList());

					}
				}
			}
		}
		return lineSalesPlans;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination1=super.queryPagination(pagination);
		List<LineSalesPlan> lineSalesPlans= (List<LineSalesPlan>) pagination1.getList();
		for(LineSalesPlan lineSalesPlan:lineSalesPlans){
			if(lineSalesPlan.getLine()!=null){
				LineRouteInfo info = lineSalesPlan.getLine().getRouteInfo();
				if(info!=null){
					Hibernate.initialize(info.getDayRouteList());
					for(DayRoute dayRoute:info.getDayRouteList()){
						Hibernate.initialize(dayRoute.getLineImageList());

					}
				}
			}
		}
		return pagination1;
	}
}
