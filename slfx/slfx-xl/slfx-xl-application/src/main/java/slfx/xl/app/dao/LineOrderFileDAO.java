package slfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.xl.domain.model.order.LineOrderFile;
import slfx.xl.pojo.qo.LineOrderFileQO;

/**
 * 
 * @类功能说明：线路订单文件DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午5:45:20
 * @版本：V1.0
 *
 */
@Repository
public class LineOrderFileDAO extends BaseDao<LineOrderFile, LineOrderFileQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineOrderFileQO qo) {
		
		if(qo != null){
			
			if(StringUtils.isNotBlank(qo.getLineOrderFileID())){
				criteria.add(Restrictions.eq("id", qo.getLineOrderFileID()));
			}
			
			if(StringUtils.isNotBlank(qo.getLineOrderID())){
				criteria.add(Restrictions.eq("lineOrder.id", qo.getLineOrderID()));
			}
			
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineOrderFile> getEntityClass() {
		// TODO Auto-generated method stub
		return LineOrderFile.class;
	}

}
