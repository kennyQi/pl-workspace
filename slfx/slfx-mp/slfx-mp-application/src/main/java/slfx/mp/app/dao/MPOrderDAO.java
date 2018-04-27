package slfx.mp.app.dao;

import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_CANCEL;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_OUTOFDATE;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_PREPARED;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_USED;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import slfx.mp.app.pojo.qo.MPOrderQO;
import slfx.mp.app.pojo.qo.OrderUserInfoQO;
import slfx.mp.domain.model.order.MPOrder;

@Repository
public class MPOrderDAO extends BaseDao<MPOrder, MPOrderQO> {
	
	@Autowired
	private OrderUserInfoDAO orderUserInfoDAO;

	@Override
	protected Criteria buildCriteria(Criteria criteria, MPOrderQO qo) {
		if (qo != null) {
			// 排序
			if (qo.getCreateDateAsc() != null) {
				criteria.addOrder(qo.getCreateDateAsc() ? Order.asc("createDate") : Order.desc("createDate"));
			}
			// 供应商订单号能否为空
			if (qo.getSupplierOrderCodeNotNull() != null) {
				criteria.add(
						qo.getSupplierOrderCodeNotNull() ? 
						Restrictions.isNotNull("supplierOrderCode") : 
						Restrictions.isNull("supplierOrderCode")
				);
			}
			// 订单状态
			if (qo.getOrderStatus() != null) {
				if (ORDER_STATUS_PREPARED.intValue() == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.prepared", Boolean.TRUE));
				} else if (ORDER_STATUS_CANCEL.intValue() == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.cancel", Boolean.TRUE));
				} else if (ORDER_STATUS_USED.intValue() == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.used", Boolean.TRUE));
				} else if (ORDER_STATUS_OUTOFDATE.intValue() == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.outOfDate", Boolean.TRUE));
				}
			}
			// 按用户id查询
			if (StringUtils.isNotBlank(qo.getUserId())) {
				OrderUserInfoQO orderUserInfoQO = new OrderUserInfoQO();
				orderUserInfoQO.setId(qo.getUserId());
				Criteria userCriteria = criteria.createCriteria("orderUserInfo", JoinType.LEFT_OUTER_JOIN);
				orderUserInfoDAO.buildCriteriaOut(userCriteria, orderUserInfoQO);
			}
			// 显示全部订单明细(意义不明...)
			// 经销商渠道
			if (StringUtils.isNotBlank(qo.getDealerId())) {
				criteria.add(Restrictions.eq("dealerId", qo.getDealerId()));
			}
			// 平台订单号
			if (StringUtils.isNotBlank(qo.getPlatformOrderCode())) {
				criteria.add(Restrictions.eq("platformOrderCode", qo.getPlatformOrderCode()));
			}
			// 供应商订单号
			if (StringUtils.isNotBlank(qo.getSupplierOrderCode())) {
				criteria.add(Restrictions.eq("supplierOrderCode", qo.getSupplierOrderCode()));
			}
			// 经销商订单号
			if (StringUtils.isNotBlank(qo.getDealerOrderCode())) {
				criteria.add(Restrictions.eq("dealerOrderCode", qo.getDealerOrderCode()));
			}
			// 供应商
			if (StringUtils.isNotBlank(qo.getSupplierId())) {
				criteria.add(Restrictions.eq("supplierId", qo.getSupplierId()));
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(qo.getCreateDateFrom() != null&&qo.getCreateDateTo() != null){
				criteria.add(Restrictions.between("createDate",qo.getCreateDateFrom(), qo.getCreateDateTo()));
			}else if (qo.getCreateDateFrom() != null) {
				// 下单起始时间
				String s=sdf.format(qo.getCreateDateFrom());
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(s), DateUtil.dateStr2EndDate(s)));
			}else if (qo.getCreateDateTo() != null) {
				// 下单终止时间
				String s=sdf.format(qo.getCreateDateTo());
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(s), DateUtil.dateStr2EndDate(s)));
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<MPOrder> getEntityClass() {
		return MPOrder.class;
	}
	
	/**
	 * 构建HQL FROM以后的查询,不包含ORDER BY 
	 * 
	 * @param qo
	 * @param params
	 * @return
	 */
	protected String buildHqlFrom(MPOrderQO qo, List<Object> params){
		String fromHql = "from MPOrder mpo left join mpo.salePolicySnapshot sps " +
				"left join mpo.supplierPolicySnapshot tps left join mpo.orderUserInfo oui, TCOrder tco";
		StringBuilder whereBuf = new StringBuilder(" where");
		whereBuf.append(" mpo.platformOrderCode = tco.platformOrderId");
		if (qo != null) {
			// ID
			if (StringUtils.isNotBlank(qo.getId())) {
				whereBuf.append(" and mpo.id = ?");
				params.add(qo.getId());
			}
			// 渠道用户ID
			if (StringUtils.isNotBlank(qo.getUserId())) {
				whereBuf.append(" and oui.channelUserId = ?");
				params.add(qo.getUserId());
			}
			// 平台订单号
			if (StringUtils.isNotBlank(qo.getPlatformOrderCode())) {
				whereBuf.append(" and mpo.platformOrderCode = ?");
				params.add(qo.getPlatformOrderCode());
			}
			// 经销商渠道
			if (StringUtils.isNotBlank(qo.getDealerId())) {
				whereBuf.append(" and mpo.dealerId = ?");
				params.add(qo.getDealerId());
			}
			// 供应商订单号
			if (StringUtils.isNotBlank(qo.getSupplierOrderCode())) {
				whereBuf.append(" and mpo.supplierOrderCode = ?");
				params.add(qo.getSupplierOrderCode());
			}
			// 经销商订单号
			if (StringUtils.isNotBlank(qo.getDealerOrderCode())) {
				whereBuf.append(" and mpo.dealerOrderCode = ?");
				params.add(qo.getDealerOrderCode());
			}
			// 供应商
			if (StringUtils.isNotBlank(qo.getSupplierId())) {
				whereBuf.append(" and mpo.supplierId = ?");
				params.add(qo.getSupplierId());
			}
			// 下单起始时间
			if (qo.getCreateDateFrom() != null) {
				whereBuf.append(" and mpo.createDate >= ?");
				params.add(qo.getCreateDateFrom());
			}
			// 下单终止时间
			if (qo.getCreateDateTo() != null) {
				whereBuf.append(" and mpo.createDate <= ?");
				params.add(qo.getCreateDateTo());
			}
			// 预定人
			if (StringUtils.isNotBlank(qo.getBookMan())) {
				whereBuf.append(" and tco.bookName like ?");
				params.add("%" + qo.getBookMan() + "%");
			}
			// 订单状态
			if (qo.getOrderStatus() != null) {
				if (ORDER_STATUS_PREPARED.intValue() == qo.getOrderStatus().intValue()) {
					whereBuf.append(" and mpo.status.prepared = ?");
					params.add(true);
				} else if (ORDER_STATUS_CANCEL.intValue() == qo.getOrderStatus().intValue()) {
					whereBuf.append(" and mpo.status.cancel = ?");
					params.add(true);
				} else if (ORDER_STATUS_USED.intValue() == qo.getOrderStatus().intValue()) {
					whereBuf.append(" and mpo.status.used = ?");
					params.add(true);
				} else if (ORDER_STATUS_OUTOFDATE.intValue() == qo.getOrderStatus().intValue()) {
					whereBuf.append(" and mpo.status.outOfDate = ?");
					params.add(true);
				}
			}
			// 景点名称
			if (StringUtils.isNotBlank(qo.getScenicSpotsName())) {
				if (qo.getScenicSpotsNameLike()) {
					whereBuf.append(" and tps.scenicSpotSnapshot.tcScenicSpotsName like ?");
					params.add("%" + qo.getScenicSpotsName() + "%");
				} else {
					whereBuf.append(" and tps.scenicSpotSnapshot.tcScenicSpotsName = ?");
					params.add(qo.getScenicSpotsName());
				}
			}
			// 游玩人
			if (StringUtils.isNotBlank(qo.getTravelerName())) {
				whereBuf.append(" and tco.travelerJson like ?");
				params.add("%" + qo.getTravelerName() + "%");
			}
			// 支付类型
			if (qo.getPaymentType() != null) {
				whereBuf.append(" and tps.pMode = ?");
				params.add(qo.getPaymentType());
			}
			// 省
			if (StringUtils.isNotBlank(qo.getProvince())) {
				whereBuf.append(" and tps.scenicSpotSnapshot.provinceCode = ?");
				params.add(qo.getProvince());
			}
			// 市
			if (StringUtils.isNotBlank(qo.getCity())) {
				whereBuf.append(" and tps.scenicSpotSnapshot.cityCode = ?");
				params.add(qo.getCity());
			}
			// 区
			if (StringUtils.isNotBlank(qo.getArea())) {
				whereBuf.append(" and tps.scenicSpotSnapshot.areaCode = ?");
				params.add(qo.getArea());
			}
		}
		whereBuf.insert(0, fromHql);
		return whereBuf.toString();
	}
	
	/**
	 * 查询平台订单
	 * 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param pagination
	 * @return
	 */
	public Pagination queryOrderPagination(Pagination pagination) {
		MPOrderQO qo = (MPOrderQO) pagination.getCondition();
		String selectHql = "select mpo,tco,sps,tps,oui ";
		String selectCntHql = "select count(*) ";
		StringBuilder orderHqlBuf = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		String fromHql = buildHqlFrom(qo, params);
		// 下单时间排序
		if (qo.getCreateDateAsc() != null) {
			orderHqlBuf.append(" order by mpo.createDate ").append(qo.getCreateDateAsc() ? "asc" : "desc");
		}
		
		StringBuilder cntHqlBuf = new StringBuilder();
		cntHqlBuf.append(selectCntHql).append(fromHql);

		StringBuilder hqlBuf = new StringBuilder();
		hqlBuf.append(selectHql).append(fromHql).append(orderHqlBuf);
		
		Pagination pagination2 = findPagination(hqlBuf.toString(),
				cntHqlBuf.toString(), pagination.getPageNo(),
				pagination.getPageSize(), params.toArray());
		pagination2.setCondition(qo);

		return pagination2;
	}
	
	/**
	 * 查询平台订单
	 * 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param qo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryOrderList(MPOrderQO qo) {
		String selectHql = "select mpo,tco,sps,tps,oui ";
		StringBuilder orderHqlBuf = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		String fromHql = buildHqlFrom(qo, params);
		// 下单时间排序
		if (qo.getCreateDateAsc() != null) {
			orderHqlBuf.append(" order by mpo.createDate ").append(qo.getCreateDateAsc() ? "asc" : "desc");
		}
		StringBuilder hqlBuf = new StringBuilder();
		hqlBuf.append(selectHql).append(fromHql).append(orderHqlBuf);
		List<Object[]> list = find(hqlBuf.toString(), params.toArray());
		return list;
	}

	/**
	 * 查询平台订单
	 * 结果集：Object[平台订单,供应商同程订单,平台政策,供应商同程政策,下单用户信息]
	 * 
	 * @param qo
	 * @return
	 */
	public Object[] queryOrderUnique(MPOrderQO qo) {
		String selectHql = "select mpo,tco,sps,tps,oui ";
		StringBuilder orderHqlBuf = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		String fromHql = buildHqlFrom(qo, params);
		// 下单时间排序
		if (qo.getCreateDateAsc() != null) {
			orderHqlBuf.append(" order by mpo.createDate ").append(qo.getCreateDateAsc() ? "asc" : "desc");
		}
		StringBuilder hqlBuf = new StringBuilder();
		hqlBuf.append(selectHql).append(fromHql).append(orderHqlBuf);
		List<?> list = find(hqlBuf.toString(), 0, 1, params.toArray());
		if (list.size() > 0) {
			if (list.get(0) instanceof Object[]) {
				return (Object[]) list.get(0);
			}
		}
		return null;
	}
	

}
