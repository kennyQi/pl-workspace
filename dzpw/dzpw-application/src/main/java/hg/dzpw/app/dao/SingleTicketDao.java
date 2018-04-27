package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.domain.model.ticket.SingleTicket;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：单票DAO
 * @类修改者：
 * @修改日期：2014-11-27下午1:55:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-27下午1:55:04
 */
@Repository
public class SingleTicketDao extends BaseDao<SingleTicket, SingleTicketQo> {
	
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private GroupTicketDao groupTicketDao;
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	@Autowired
	private TouristDao touristDao;
	@Autowired
	private UseRecordDao useRecordDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, SingleTicketQo qo) {
		ticketDao.buildCriteriaOut(criteria, qo);
		if(qo!=null){
			// 所属套票
			if (qo.getGroupTicketQo() != null) {
				Criteria groupTicketCriteria = criteria.createCriteria("groupTicket", qo.getGroupTicketQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
				groupTicketDao.buildCriteriaOut(groupTicketCriteria, qo.getGroupTicketQo());
			}
			// 对应景区
			if (qo.getScenicSpotQo() != null) {
				Criteria scenicSpotCriteria = criteria.createCriteria("scenicSpot", qo.getScenicSpotQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
				scenicSpotDao.buildCriteriaOut(scenicSpotCriteria, qo.getScenicSpotQo());
			}
			// 游客
//			if(qo.getTourQo()!=null)
//			{
//				Criteria touristCriteria = criteria.createCriteria("tourist", qo.getTourQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
//				touristDao.buildCriteriaOut(touristCriteria, qo.getTourQo());
//			}
			if(qo.getStatus()!=null)
			{
				criteria.add(Restrictions.eq("status.current", qo.getStatus()));
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<SingleTicket> getEntityClass() {
		return SingleTicket.class;
	}
}
