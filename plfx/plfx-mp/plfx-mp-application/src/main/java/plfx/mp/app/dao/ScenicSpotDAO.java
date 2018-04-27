package plfx.mp.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import plfx.mp.app.pojo.qo.ScenicSpotQO;
import plfx.mp.domain.model.scenicspot.ScenicSpot;

@Repository
public class ScenicSpotDAO extends BaseDao<ScenicSpot, ScenicSpotQO> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotQO qo) {
		if (qo != null) {
			Criteria tcScenicSpotInfoCriteria = criteria.createCriteria("tcScenicSpotInfo", "tcScenicSpotInfo",  JoinType.INNER_JOIN);
			// 是否加载政策须知
			if (qo.isTcPolicyNoticeFetchAble()) {
				tcScenicSpotInfoCriteria.setFetchMode("tcPolicyNotice", FetchMode.JOIN);
			}
			// 景区所在地模糊查询
			if (StringUtils.isNotBlank(qo.getArea())) {
				criteria.add(Restrictions.like("scenicSpotGeographyInfo.address", qo.getArea(), MatchMode.ANYWHERE));
			}
			// 供应商
			if (StringUtils.isNotBlank(qo.getSupplierId())) {
				criteria.add(Restrictions.eq("supplierId", qo.getSupplierId()));
			}
			// 景点名称
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.getScenicSpotsNameLike()) {
					criteria.add(Restrictions.ilike("scenicSpotBaseInfo.name", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("scenicSpotBaseInfo.name", qo.getName()));
				}
			}

			// 同程景区ID
			if(StringUtils.isNotBlank(qo.getTcScenicSpotId())){
				tcScenicSpotInfoCriteria.add(Restrictions.eq("id", qo.getTcScenicSpotId()));
			}
				
			// 景点级别(格式：A*)
			if (StringUtils.isNotBlank(qo.getGrade())) {
				criteria.add(Restrictions.eq("scenicSpotBaseInfo.grade", qo.getGrade()));
			}
			// 最低价区间（最小值）
			if (qo.getAmountAdviceMin() != null) {
				tcScenicSpotInfoCriteria.add(Restrictions.ge("amountAdvice", qo.getAmountAdviceMin()));
			}
			// 最低价区间（最大值）
			if (qo.getAmountAdviceMax() != null) {
				tcScenicSpotInfoCriteria.add(Restrictions.le("amountAdvice", qo.getAmountAdviceMin()));
			}
			// 主题ID
			if (StringUtils.isNotBlank(qo.getThemeId())) {
				tcScenicSpotInfoCriteria.add(Restrictions.like(
						"themesIds", "," + qo.getAmountAdviceMin() + ",", MatchMode.ANYWHERE));
			}
			// 最低价格排序 <0 DESC >0 ASC
			if (qo.getAmountAdviceSort() != 0) {
				tcScenicSpotInfoCriteria
						.addOrder(qo.getAmountAdviceSort() < 0 ? Order
								.desc("amountAdvice") : Order
								.asc("amountAdvice"));
			}
			// 景区级别排序 <0 DESC >0 ASC
			if (qo.getGradeSort() != 0) {
				criteria.addOrder(qo.getGradeSort() < 0 ? Order
						.desc("scenicSpotBaseInfo.grade") : Order
						.asc("scenicSpotBaseInfo.grade"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ScenicSpot> getEntityClass() {
		return ScenicSpot.class;
	}

	@Override
	public List<ScenicSpot> queryList(ScenicSpotQO qo, Integer offset, Integer maxSize) {
		List<ScenicSpot> list = super.queryList(qo, offset, maxSize);
		if(qo.isImagesFetchAble()){
			for(ScenicSpot ss:list){
				Hibernate.initialize(ss.getImages());
			}
		}
		return super.queryList(qo, offset, maxSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		ScenicSpotQO qo = (ScenicSpotQO) pagination.getCondition();
		if (qo != null && qo.isImagesFetchAble()) {
			List<ScenicSpot> list = (List<ScenicSpot>) super.queryPagination(pagination).getList();
			for (ScenicSpot ss : list) {
				Hibernate.initialize(ss.getImages());
			}
		}
		return super.queryPagination(pagination);
	}
	
}
