package lxs.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.List;

import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineRouteInfo;
import lxs.domain.model.line.LineSelective;
import lxs.pojo.qo.line.LineSelectiveQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

@Repository
public class LxsLineSelectiveDAO extends BaseDao<LineSelective, LineSelectiveQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSelectiveQO qo) {
		// TODO Auto-generated method stub
		Criteria criteria2=criteria.createCriteria("line");
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			if(qo.getSort()!=0){
				criteria.add(Restrictions.eq("sort", qo.getSort()));
			}
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getType())){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
			//注册时间 两个都有值查询区间,否则查询当天
			if(StringUtils.isNotBlank(qo.getBeginTime())&&StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}else if(StringUtils.isNotBlank(qo.getBeginTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getBeginTime())));
			}else if(StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getEndTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			if (StringUtils.isNotBlank(qo.getStartingCity())) {
				criteria2.add(Restrictions.eq("baseInfo.starting",qo.getStartingCity()));
			}
			if(qo.getStaringCities()!=null&&qo.getStaringCities().size()!=0){
				criteria2.add(Restrictions.in("baseInfo.starting",qo.getStaringCities()));
			}
			if(qo.getForSale()!=null&&qo.getForSale()!=0){
				criteria2.add(Restrictions.eq("forSale", qo.getForSale()));
			}
		}
		criteria.addOrder(Order.desc("sort"));
		return criteria;
	}

	@Override
	protected Class<LineSelective> getEntityClass() {
		return LineSelective.class;
	}

	public int maxProperty(String propertyName, LineSelectiveQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
	@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		List<LineSelective> list = (List<LineSelective>) pagination.getList();
		for (LineSelective l : list) {
			Hibernate.initialize(l.getLine());
			Line line = l.getLine();
			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());
			Hibernate.initialize(line.getRouteInfo());
			LineRouteInfo info = line.getRouteInfo();
			if(info!=null){
				Hibernate.initialize(info.getDayRouteList());
				for(DayRoute dayRoute:info.getDayRouteList()){
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
		}
		return pagination;
	}
	
	@Override
	public LineSelective queryUnique(LineSelectiveQO qo) {
		LineSelective lineSelective = super.queryUnique(qo);
		if(lineSelective!=null){
			Hibernate.initialize(lineSelective.getLine());
			Line line = lineSelective.getLine();
			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());
			Hibernate.initialize(line.getRouteInfo());
			LineRouteInfo info = line.getRouteInfo();
			if(info!=null){
				Hibernate.initialize(info.getDayRouteList());
				for(DayRoute dayRoute:info.getDayRouteList()){
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
			return lineSelective;
		}else{
			return null;
		}
		
	}
}
