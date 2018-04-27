package plfx.xl.app.dao;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.DateSalePrice;
import plfx.xl.pojo.qo.DateSalePriceQO;

/**
 * 
 * @类功能说明：价格日历DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午4:29:38
 * @版本：V1.0
 *
 */
@Repository("dateSalePriceDAO_xl")
public class DateSalePriceDAO extends BaseDao<DateSalePrice, DateSalePriceQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DateSalePriceQO qo) {
		
		if(qo != null){
		
			
			if(StringUtils.isNotBlank(qo.getDateSalePriceID())){
				criteria.add(Restrictions.eq("id", qo.getDateSalePriceID()));
			}
			
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			
			if(qo.getSaleDateAsc() != null){
				criteria.addOrder(qo.getSaleDateAsc()?Order.asc("saleDate"):Order.desc("saleDate"));
			}
			
			if(qo.getSaleDate() != null){
				Date date = DateUtil.dateStr2BeginDate(qo.getSaleDate());
				criteria.add(Restrictions.eq("saleDate", date));
			}
			
			//日期是星期几
			if(StringUtils.isNotBlank(qo.getWeekDay())){
				criteria.add(Restrictions.sqlRestriction("DAYOFWEEK(SALE_DATE) in (" + qo.getWeekDay() + ")"));
			}
			
			if(StringUtils.isNotBlank(qo.getBeginDate())){
				Date begin = DateUtil.dateStr2BeginDate(qo.getBeginDate());
				criteria.add(Restrictions.ge("saleDate",begin));
			}
			
			if(StringUtils.isNotBlank(qo.getEndDate())){
				Date end = DateUtil.dateStr2EndDate(qo.getEndDate());
				criteria.add(Restrictions.le("saleDate", end));
			}
			
			
			
			
		}
		return criteria;
	}

	@Override
	protected Class<DateSalePrice> getEntityClass() {
		return DateSalePrice.class;
	}

}
