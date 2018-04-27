package lxs.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lxs.domain.model.line.LineOrder;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.qo.line.LineOrderQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LineOrderDAO extends BaseDao<LineOrder, LineOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineOrderQO qo) {
		// 快照查询明细
		if (qo != null) {
			Criteria lineSnapshotCriteria=null;
			// 订单号查询
			if (StringUtils.isNotBlank(qo.getOrderId())) {
				criteria.add(Restrictions.eq("id",	qo.getOrderId()));
			}
			if(StringUtils.isNotBlank(qo.getDealerOrderNo())){
				criteria.add(Restrictions.like("baseInfo.dealerOrderNo",qo.getDealerOrderNo(),MatchMode.ANYWHERE));
			}
			if(qo.getPayStatusList()!=null){
				//待支付
//				criteria.add(Restrictions.like("orderStautes",qo.getOrderStatus(),MatchMode.ANYWHERE));
				criteria.add(Restrictions.or(Restrictions.like("payStautes",qo.getPayStatusList().get(0).toString(),MatchMode.ANYWHERE), Restrictions.like("payStautes",qo.getPayStatusList().get(1).toString(),MatchMode.ANYWHERE)));
				criteria.add(Restrictions.sqlRestriction(" ORDER_STATUES not like '%" + XLOrderStatusConstant.SHOP_ORDER_CANCEL + "%' "));
			}
			if(qo.getPayStatusList()==null&&StringUtils.isNotBlank(qo.getOrderStatus())){
				criteria.add(Restrictions.like("orderStautes",qo.getOrderStatus(),MatchMode.ANYWHERE));
			}
			if(qo.getPayStatusList()==null&&StringUtils.isNotBlank(qo.getPayStatus())){
				criteria.add(Restrictions.like("payStautes",qo.getPayStatus(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("lineOrderUser.userId",qo.getUserId()));
			}
			if(StringUtils.isNotBlank(qo.getBooker())){
				criteria.add(Restrictions.like("linkInfo.linkName",qo.getBooker(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getLineNumber())){
				if (lineSnapshotCriteria == null) {
					lineSnapshotCriteria = criteria.createCriteria("lineSnapshot");
				}
				Criteria lineCriteria = lineSnapshotCriteria.createCriteria("line");
//				lineSnapshotCriteria.add(Restrictions.like("lineName",qo.getLineName(),MatchMode.ANYWHERE));
				lineCriteria.add(Restrictions.like("baseInfo.number",qo.getLineNumber(),MatchMode.ANYWHERE));
			}
			// 下单时间
			if (StringUtils.isNotBlank(qo.getStartTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getStartTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getStartTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getStartTime()),DateUtil.dateStr2EndDate(qo.getStartTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getEndTime()),DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			// 线路名称
			if (StringUtils.isNotBlank(qo.getLineName())) {
				if (lineSnapshotCriteria == null) {
					lineSnapshotCriteria = criteria.createCriteria("lineSnapshot");
				}
				lineSnapshotCriteria.add(Restrictions.like("lineName",qo.getLineName(),MatchMode.ANYWHERE));
			}
			//定时查询 下单10分钟后未支付订单
			if(StringUtils.isNotBlank(qo.getHaveSendedSMSFlag())&&StringUtils.equals(qo.getHaveSendedSMSFlag(), "yes")){
				//未通知过
				criteria.add(Restrictions.eq("haveSendedSMS",LineOrder.NO));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, -10);
				//十分钟之前的单子
				criteria.add(Restrictions.le("baseInfo.createDate", calendar.getTime()));
				//订单状态是未支付
				criteria.add(Restrictions.like("payStautes",XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY,MatchMode.ANYWHERE));
			}
		}
		//按上架时间倒序排序
		criteria.addOrder(Order.desc("baseInfo.createDate"));
		return criteria;
	}

	
	@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		List<LineOrder> list = (List<LineOrder>) pagination.getList();
		LineOrderQO qo = (LineOrderQO) pagination.getCondition();
		for (LineOrder l : list) {
			init(l, qo);
		}
		return pagination;
	}
	@Override
	public LineOrder queryUnique(LineOrderQO qo) {
		LineOrder lineOrder = super.queryUnique(qo);
		if (lineOrder != null) {
			init(lineOrder, qo);
		}
		return lineOrder;
	}
	
	
	private void init(LineOrder lineOrder,LineOrderQO qo){
		Hibernate.initialize(lineOrder.getTravelers());
	}
	
	
	@Override
	protected Class<LineOrder> getEntityClass() {
		return LineOrder.class;
	}
	
}
