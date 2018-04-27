package plfx.xl.app.dao;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.salepolicy.SalePolicy;
import plfx.xl.pojo.qo.SalePolicyLineQO;

/**
 * @类功能说明：价格政策DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午10:17:49
 * @版本：V1.0
 */
@Repository
public class SalePolicyLineDAO extends BaseDao<SalePolicy, SalePolicyLineQO> {
	
	@Autowired
	private  LineDAO  lineDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, SalePolicyLineQO qo) {
		
		if(qo != null){
			
			//政策id
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id",qo.getId()));
			}
			
			//政策编号
			if(StringUtils.isNotBlank(qo.getSalePolicyLineNumber())){
				criteria.add(Restrictions.eq("salePolicyLineNumber",qo.getSalePolicyLineNumber()));
			}
			
			//创建人
			if(StringUtils.isNotBlank(qo.getCreateName())){
				criteria.add(Restrictions.eq("createName",qo.getCreateName()));
			}
			//政策状态
			if(qo.getSalePolicyLineStatus() != null){
				criteria.add(Restrictions.eq("salePolicyLineStatus.salePolicyLineStatus", qo.getSalePolicyLineStatus()));
			}
			
			//生效开始日期大于
			if(qo.getStartDate()!=null){
				criteria.add(Restrictions.ge("startDate", qo.getStartDate()));
			}
			
			//生效开始日期小于
			if(qo.getEndDate()!=null){
				criteria.add(Restrictions.le("startDate", qo.getEndDate()));
			}
			
			//经销商
			if(StringUtils.isNotBlank(qo.getLineDealerID()) ){
				Disjunction dis = Restrictions.disjunction();
				dis.add(Restrictions.eq("lineDealer.id", qo.getLineDealerID()));
				//查询适用某个经销商的价格政策时，同时也要查询适用全站经销商的价格政策
				dis.add(Restrictions.isNull("lineDealer.id")); 
				criteria.add(dis);
			}
			
			//政策类型
			if(qo.getPriceType() != null){
				Disjunction dis = Restrictions.disjunction();
				dis.add(Restrictions.eq("priceType", qo.getPriceType()));
				dis.add(Restrictions.eq("priceType", 0)); //0表示政策类型成人和儿童都适用
				criteria.add(dis);
			}
			
			//查询最高优先级
			if(qo.getMaxPriority() != null && qo.getMaxPriority()){
				criteria.addOrder(Order.desc("priority"));
				criteria.setMaxResults(1);
			}
			
			
			//是否隐藏
			if(qo.getIsHide() != null){
				criteria.add(Restrictions.eq("hide", qo.getIsHide()));
			}
			
			//当前时间价格政策是否在有效时间内
			if(qo.getIsVaild() != null){
				String dateStr = DateUtil.formatDate(new Date());
				if(qo.getIsVaild()){
					criteria.add(Restrictions.le("startDate", DateUtil.dateStr2BeginDate(dateStr))).
					add(Restrictions.ge("endDate", DateUtil.dateStr2BeginDate(dateStr)));
				}else{
					criteria.add(Restrictions.le("endDate", DateUtil.dateStr2BeginDate(dateStr)));
				}
			}
			
			//创建日期排序
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("salePolicyLineCreateDate"):Order.desc("salePolicyLineCreateDate"));
			}
			
			DetachedCriteria salePolicyCriteria = DetachedCriteria.forClass(SalePolicy.class);
			salePolicyCriteria.createAlias("lines", "line");
			
			//适用线路类别：是跟团游还是自助游
			if(qo.getType() != null && qo.getType() != -1){
				salePolicyCriteria.add(Restrictions.eq("line.baseInfo.type", qo.getType()));
			}
			
			//适用线路类别城市
			if(StringUtils.isNotBlank(qo.getCityOfType())){
				salePolicyCriteria.add(Restrictions.eq("line.baseInfo.cityOfType", qo.getCityOfType()));
			}
			
			//线路编号
			if(StringUtils.isNotBlank(qo.getLineID())){
				salePolicyCriteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			//国内线和国外线
			if(StringUtils.isNotBlank(qo.getDomesticLine())){
				salePolicyCriteria.add(Restrictions.eq("line.baseInfo.typeGrade", qo.getDomesticLine()));
			}
			salePolicyCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			salePolicyCriteria.setProjection(Projections.id());
			//多对多连接查询会产生重复数据，hibernate分页是先分页再去除重复数据，会造成分页数量和显示记录数不一致情况；此处采用子查询解决此问题
			criteria.add(Subqueries.propertyIn("id",salePolicyCriteria));
			
		}
		return criteria;
	}

	@Override
	protected Class<SalePolicy> getEntityClass() {
		return SalePolicy.class;
	}
}