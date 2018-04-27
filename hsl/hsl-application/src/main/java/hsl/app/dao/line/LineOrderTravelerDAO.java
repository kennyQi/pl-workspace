package hsl.app.dao.line;

import hg.common.component.BaseDao;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.pojo.qo.line.LineOrderTravelerQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：线路游玩人DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月18日下午3:26:57
 * @版本：V1.0
 *
 */
@Repository
public class LineOrderTravelerDAO extends BaseDao<LineOrderTraveler, LineOrderTravelerQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineOrderTravelerQO qo) {
		//根据订单id查询游玩人
		if (StringUtils.isNotBlank(qo.getLineOrderId())) {
			criteria.add(Restrictions.eq("lineOrder.id", qo.getLineOrderId()));
		}
		
		//根据游玩人状态查询
		if (qo.getLineOrderStatusDTO() != null) {
			if (qo.getLineOrderStatusDTO().getOrderStatus() != null && qo.getLineOrderStatusDTO().getOrderStatus() != -1) {
				criteria.add(Restrictions.eq("lineOrderStatus.orderStatus", qo.getLineOrderStatusDTO().getOrderStatus()));
			}
			if (qo.getLineOrderStatusDTO().getPayStatus() != null && qo.getLineOrderStatusDTO().getPayStatus() != -1) {
				criteria.add(Restrictions.eq("lineOrderStatus.payStatus", qo.getLineOrderStatusDTO().getPayStatus()));
			}
			
		}
		
		return criteria;
	}

	@Override
	protected Class<LineOrderTraveler> getEntityClass() {
		return LineOrderTraveler.class;
	}

}
