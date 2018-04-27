package pay.record.app.dao.pay;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pay.record.domain.model.pay.AirPayRecord;
import pay.record.pojo.qo.pay.AirPayRecordQO;

/**
 * 
 * @类功能说明：机票支付记录DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月16日下午3:13:27
 * @版本：V1.0
 *
 */
@Repository
public class AirPayRecordDAO extends BaseDao<AirPayRecord, AirPayRecordQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AirPayRecordQO qo) {
		if(qo != null){
			//经销商订单号查询
			if(StringUtils.isNotBlank(qo.getDealerOrderNo())){
				criteria.add(Restrictions.eq("dealerOrderNo", qo.getDealerOrderNo()));
			}
			//平台订单号查询
			if(StringUtils.isNotBlank(qo.getPlatOrderNo())){
				criteria.add(Restrictions.eq("platOrderNo", qo.getPlatOrderNo()));
			}
			
			//供应商订单号
			if(StringUtils.isNotBlank(qo.getSupplierNo())){
				criteria.add(Restrictions.eq("supplier", qo.getSupplierNo()));
			}
			//订购人
			if(StringUtils.isNotBlank(qo.getBooker())){
				criteria.add(Restrictions.eq("booker", qo.getBooker()));
			}
			
			//订单状态查询
			if(StringUtils.isNotBlank(qo.getOrderStatus())){
				criteria.add(Restrictions.eq("orderStatus", qo.getOrderStatus()));
			}
			//支付状态查询
			if(StringUtils.isNotBlank(qo.getPayStatus())){
				criteria.add(Restrictions.eq("payStatus", qo.getPayStatus()));
			}
			//来源项目
			if(qo.getFromProjectCode() != null){
				criteria.add(Restrictions.eq("fromProjectCode", qo.getFromProjectCode()));
			}
			//记录类型
			if(qo.getRecordType() != null){
				criteria.add(Restrictions.eq("recordType", qo.getRecordType()));
			}
			//交易流水号
			if(StringUtils.isNotBlank(qo.getPaySerialNumber())){
				criteria.add(Restrictions.eq("paySerialNumber", qo.getPaySerialNumber()));
			}
			//按时间排序查询
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			
			//支付时间区间查询
			if(qo.getPayDateFrom() != null){
				criteria.add(Restrictions.ge("payTime", qo.getPayDateFrom()));
			}
			if(qo.getPayDateTo() != null){
				criteria.add(Restrictions.le("payTime", qo.getPayDateTo()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<AirPayRecord> getEntityClass() {
		return AirPayRecord.class;
	}

}
