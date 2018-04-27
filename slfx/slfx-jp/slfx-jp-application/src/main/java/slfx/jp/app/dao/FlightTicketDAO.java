package slfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.qo.admin.PlatformOrderQO;

/**
 * 
 * @类功能说明：机票DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:40:50
 * @版本：V1.0
 *
 */
@Repository
public class FlightTicketDAO extends BaseDao<FlightTicket, PlatformOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PlatformOrderQO qo) {
		
		if (qo != null) {
		}
		return criteria;
	}

	@Override
	protected Class<FlightTicket> getEntityClass() {
		return FlightTicket.class;
	}

}
