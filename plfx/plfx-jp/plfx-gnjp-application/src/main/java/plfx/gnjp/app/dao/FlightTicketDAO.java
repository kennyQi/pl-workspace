package plfx.gnjp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.gnjp.domain.model.order.GNFlightTicket;
import plfx.yeexing.qo.admin.PlatformOrderQO;


/***
 * 
 * @类功能说明：机票信息DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月13日下午1:31:28
 * @版本：V1.0
 *
 */
@Repository
public class FlightTicketDAO extends BaseDao<GNFlightTicket, PlatformOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PlatformOrderQO qo) {
		
		return criteria;
	}

	@Override
	protected Class<GNFlightTicket> getEntityClass() {
		return GNFlightTicket.class;
	}

}
