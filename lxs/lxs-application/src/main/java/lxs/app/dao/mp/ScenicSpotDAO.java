package lxs.app.dao.mp;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.qo.mp.ScenicSpotPicQO;
import lxs.pojo.qo.mp.ScenicSpotQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

@Repository
public class ScenicSpotDAO extends BaseDao<ScenicSpot, ScenicSpotQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotQO qo) {
		if(qo!=null){
			if(qo.getLocalStatus()!=null){
				criteria.add(Restrictions.eq("localStatus", qo.getLocalStatus()));
			}
			if(qo.getVersionNO()!=null){
				
				if(qo.getVersionType()==ScenicSpotPicQO.GREATER_THAN){
					criteria.add(Restrictions.gt("versionNO", qo.getVersionNO()));
				}else if(qo.getVersionType()==ScenicSpotPicQO.LESS_THAN){
					criteria.add(Restrictions.lt("versionNO", qo.getVersionNO()));
				}else{
					criteria.add(Restrictions.eq("versionNO", qo.getVersionNO()));
				}
			}
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("baseInfo.name", qo.getName(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getLevel())){
				criteria.add(Restrictions.eq("baseInfo.level", qo.getLevel()));
			}
			if(StringUtils.isNotBlank(qo.getTheme())){
				criteria.add(Restrictions.like("baseInfo.theme", qo.getTheme(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getProvinceId())){
				String[] provinces = qo.getProvinceId().split(":");
				if(provinces.length==0){
					criteria.add(Restrictions.eq("baseInfo.provinceId", qo.getProvinceId()));
				}else{
					criteria.add(Restrictions.in("baseInfo.provinceId",provinces));
				}
			}
			if(StringUtils.isNotBlank(qo.getCityId())){
				String[] cities = qo.getCityId().split(":");
				if(cities.length==0){
					criteria.add(Restrictions.eq("baseInfo.cityId", qo.getCityId()));
				}else{
					criteria.add(Restrictions.in("baseInfo.cityId",cities));
				};
			}
			if(StringUtils.isNotBlank(qo.getAreaId())){
				String[] areas = qo.getAreaId().split(":");
				if(areas.length==0){
					criteria.add(Restrictions.eq("baseInfo.areaId", qo.getAreaId()));
				}else{
					criteria.add(Restrictions.in("baseInfo.areaId",areas));
				}
			}
			if(qo.getPlayPriceLow()!=null&&qo.getPlayPriceHigh()!=0.0&&qo.getPlayPriceLow()!=null&&qo.getPlayPriceLow()!=0.0){
				criteria.add(Restrictions.between("playPrice", qo.getPlayPriceLow(), qo.getPlayPriceHigh()));
			}
			if(StringUtils.isNotBlank(qo.getOrderBy())&&StringUtils.isNotBlank(qo.getSort())){
				if(StringUtils.equals(qo.getSort(), "l2h")){
					if(StringUtils.equals(qo.getOrderBy(), "sales")){
						criteria.addOrder(Order.asc("sales"));
					}else if(StringUtils.equals(qo.getOrderBy(), "price")){
						criteria.addOrder(Order.asc("playPrice"));
					}
				}else if(StringUtils.equals(qo.getSort(), "h2l")){
					if(StringUtils.equals(qo.getOrderBy(), "sales")){
						criteria.addOrder(Order.desc("sales"));
					}else if(StringUtils.equals(qo.getOrderBy(), "price")){
						criteria.addOrder(Order.desc("playPrice"));
					}
				}
			}else{
				criteria.addOrder(Order.desc("sort"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ScenicSpot> getEntityClass() {
		return ScenicSpot.class;
	}

	public int maxProperty(String propertyName, ScenicSpotQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
	
}
