package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.HJBTransferRecordQo;
import hg.dzpw.domain.model.pay.HJBTransferRecord;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：汇金宝转账记录DAO
 * @类修改者：
 * @修改日期：2015-5-6下午4:08:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午4:08:15
 */
@Repository
public class HJBTransferRecordDao extends BaseDao<HJBTransferRecord, HJBTransferRecordQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HJBTransferRecordQo qo) {
		
		if(null == qo)
			return criteria;
		
		if(qo.getType()!=null){
			criteria.add(Restrictions.eq("type", qo.getType()));
		}
		
		if(qo.getRecordDateBegin()!=null){
			criteria.add(Restrictions.ge("recordDate", qo.getRecordDateBegin()));
		}
		
		if(qo.getRecordDateEnd()!=null){
			criteria.add(Restrictions.le("recordDate", qo.getRecordDateEnd()));
		}
		
		if(qo.getHasResponse()!=null){
			criteria.add(Restrictions.eq("hasResponse", qo.getHasResponse()));
		}
		
		if(StringUtils.isNotBlank(qo.getStatus())){
			criteria.add(Restrictions.eq("status", qo.getStatus()));
		}
		
		if(StringUtils.isNotBlank(qo.getPayCstNo())){
			criteria.add(Restrictions.eq("payCstNo", qo.getPayCstNo()));
		}
		
		if(StringUtils.isNotBlank(qo.getRcvCstNo())){
			criteria.add(Restrictions.eq("rcvCstNo", qo.getRcvCstNo()));
		}
		
		if(StringUtils.isNotBlank(qo.getUserId())){
			criteria.add(Restrictions.eq("userId", qo.getUserId()));
		}
		
		if(StringUtils.isNotBlank(qo.getHjbOrderNo())){
			criteria.add(Restrictions.eq("hjbOrderNo", qo.getHjbOrderNo()));
		}
		
		if(StringUtils.isNotBlank(qo.getErrorCode())){
			criteria.add(Restrictions.eq("errorCode", qo.getErrorCode()));
		}
		
		
		
		if(qo.getRecordDateOrder()!=0){
			
			criteria.addOrder(qo.getRecordDateOrder() > 0 ? Order.asc("recordDate") : Order.desc("recordDate"));
			
		}
		
		return criteria;
	}

	@Override
	protected Class<HJBTransferRecord> getEntityClass() {
		return HJBTransferRecord.class;
	}

}
