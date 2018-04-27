package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.log.SalePolicyWorkLog;
import plfx.xl.pojo.qo.SalePolicyWorkLogQO;

/**
 * @类功能说明：价格政策操作日志DAO层
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:31:57
 * @版本：V1.0
 *
 */
@Repository
public class SalePolicyWorkLogDAO extends BaseDao<SalePolicyWorkLog, SalePolicyWorkLogQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SalePolicyWorkLogQO qo) {
		if(qo != null){
			//价格政策
			if(StringUtils.isNotBlank(qo.getSalePolicyId())){
				criteria.add(Restrictions.eq("salePolicy.id",qo.getSalePolicyId()));
			}
			
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("logDate"):Order.desc("logDate"));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<SalePolicyWorkLog> getEntityClass() {
		return SalePolicyWorkLog.class;
	}
}
