package hg.dzpw.app.service.local;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.app.dao.GroupTicketDao;
import hg.dzpw.app.dao.UseRecordDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.log.util.HgLogger;

import org.hibernate.Hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：套票（联票）服务
 * @类修改者：
 * @修改日期：2014-11-26下午3:34:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-26下午3:34:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTicketLocalService extends BaseServiceImpl<GroupTicket, GroupTicketQo, GroupTicketDao> {
	@Autowired
	private GroupTicketDao dao;
	
	@Autowired
	private UseRecordDao useRecordDao;
	
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected GroupTicketDao getDao() {
		return dao;
	}

	
/*    @SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination p = dao.queryPagination(pagination);
		List<GroupTicket> groupList = (List<GroupTicket>) p.getList();
		for(GroupTicket group: groupList)
		{
			Hibernate.initialize(group.getSingleTickets());
			for(SingleTicket singleTicket:group.getSingleTickets())
			{
				Hibernate.initialize(singleTicket.getScenicSpot());
			}
		}
		return p;
		
	}*/

	
//	@Override
//	@Transactional(readOnly = true)
//	public List<GroupTicket> queryList(GroupTicketQo qo) {
//		List<GroupTicket> list = super.queryList(qo);
//		for(GroupTicket groupTicket : list){
//			List<SingleTicket> singleTickets = groupTicket.getSingleTickets();
//			if(singleTickets!=null && singleTickets.size()>0){
//				int singleTicketSize = singleTickets.size();
//				int useRecordCount= 0;
//				for(SingleTicket singleTicket : singleTickets){
//					UseRecordQo urQo = new UseRecordQo();
//					urQo.setSingleTicketId(singleTicket.getId());
//					List<UseRecord> useRecordList = useRecordDao.queryList(urQo);
////					List<UseRecord> useRecordList = singleTicket.getUseRecordList();
//					if(useRecordList!=null && useRecordList.size()>0){
//						//游玩一个景区+1
//						useRecordCount++;
//					}	
//				}
//				if(useRecordCount==0){
//					if(new Date().after(groupTicket.getUseDateEnd())){
//						//已全部失效
//						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_INVALID);
//					}else{
//						if(groupTicket.getStatus().getCurrent()!=GroupTicketStatus.GROUP_TICKET_STATUS_UNACTIVE)
//							//未游玩
//							groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_UNUSE);
//					}
//				}else{
//					 //判断是否全部游玩
//					if(singleTicketSize==useRecordCount){
//						//全部游玩
//						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_USED);
//					}else{
//						if(new Date().after(groupTicket.getUseDateEnd())){
//							//部分失效
//							groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_PARTINVALID);
//						}else{
//							//部分游玩
//							groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_PARTUSE);
//						}
//					}
//				}
//			}
//		}
//		return list;
//	}
	
	
	/**
	 * @方法功能说明：定时任务--自动过期
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-7下午12:03:54
	 * @return:void
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	public void ticketExpiryDateTimeOut(){
		
		// 前一天的23:59:59 (即门票结束时间)
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		
		GroupTicketQo qo = new GroupTicketQo();
		qo.setStatus(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);
		qo.setUseDateEnd(c.getTime()); //结束时间小于前一天23:59:59的票
		qo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
		qo.setTicketOrdeQo(new TicketOrderQo());
		qo.getTicketOrdeQo().setDealerQo(new DealerQo());
		
		Integer size = 10000;
		Integer count = 0;
		Integer total = dao.queryCount(qo);
		if (total % size ==0){
			count = total / size;
		}else {
			count = total / size + 1;
		}
		
		int n = 0;
		
		// 每次查询10000
		for(int x=1; x<=count; x++){
			// list中的groupTicket已都过期
			List<GroupTicket> gl = dao.queryList(qo, size);
			
			for (GroupTicket gt : gl){
				
				SingleTicketQo sqo = new SingleTicketQo();
				sqo.setGroupTicketQo(new GroupTicketQo());
				sqo.getGroupTicketQo().setId(gt.getId());
				List<SingleTicket> sl = singleTicketLocalService.queryList(sqo);
				
				//是否有过期未使用
				Boolean b = false;
				// 判断singleTicket是否过期
				for (SingleTicket st : sl){
					// 已过期
					if (st.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_TOBE_ACTIVE 
							|| st.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE){
						// 未游玩失效
						st.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_USE_CURRENT_INVALID);
						singleTicketLocalService.update(st);
						HgLogger.getInstance().info("yangk", String.format("更新singleTicket 票号[%s] 状态为:[已失效(未游玩)]", st.getTicketNo()));
						b = true;
					}
				}
				
				// 有未使用景区  判断门票是否支持过期退
				if (b){
					if (gt.getTicketPolicySnapshot().getSellInfo().getAutoMaticRefund()){// 可以退款
						
						gt.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_CAN);
						dao.update(gt);
						HgLogger.getInstance().info("yangk", String.format("更新groupTicket 票号[%s] 退款状态为:[可退款]", gt.getTicketNo()));
						// 设置待推送
						ticketOrderManager.setPushOrder(gt.getTicketOrder().getId(), 
															gt.getTicketOrder().getBaseInfo().getFromDealer().getClientInfo().getKey());
//						// 通知经销商
//						DealerApiEventPublisher.publish(new PublishEventRequest(
//										PublishEventRequest.EVENT_TICKET_CAN_APPLY_REFUND,
//										gt.getTicketNo(), gt.getTicketOrder()
//												.getBaseInfo().getFromDealer()
//												.getClientInfo().getKey()));
					}
				}
				
				// 毎条强制刷新
				getDao().flush();
				getDao().clear();
				
				singleTicketLocalService.updateGroupTicketAndOrderStatus(gt.getId());
			}
			
			// 毎50条强制刷新
			if (++n % 50 == 0) {
				getDao().flush();
				getDao().clear();
			}
		}
	}
	
	
	/**
	 * @方法功能说明：定时任务--自动退款
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-7下午12:03:27
	 * @return:void
	 * @throws
	 
	@Transactional(rollbackFor = Exception.class)
	public void groupTicketRefund(){
		// 查询待退款
		GroupTicketQo gqo = new GroupTicketQo();
		gqo.setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE);
		List<GroupTicket> gl = dao.queryList(gqo, 8000);
					
		for (GroupTicket gt : gl){
					
			SingleTicketQo sqo = new SingleTicketQo();
			sqo.setGroupTicketQo(new GroupTicketQo());
			sqo.getGroupTicketQo().setId(gt.getId());
						
			List<SingleTicket> sl = singleTicketLocalService.queryList(sqo);
						
			int refundSize = 0;
			String text = "";
						
			// 退款
			for (SingleTicket st : sl){
				// 不用退款
				if (st.getStatus().getCurrent() != SingleTicketStatus.SINGLE_TICKET_CURRENT_WAIT_REFUND)
					continue;
					
				refundSize = refundSize + 1;
								
				//TODO 计算退款金额
				//TODO 调用接口退款
							
				// 修改singleTicket状态
				st.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED);
				singleTicketLocalService.update(st);
			}
						
						
			// 退款成功 设置退款时间
			if (refundSize == sl.size()) {
				gt.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_ALL_SUCC);
				text = "退款成功";
			}else {
				gt.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_SOME_SUCC);
				text = "部分退款成功";
			}
						
			gt.getStatus().setRefundDate(new Date());
			dao.update(gt);
						
			HgLogger.getInstance().info("yangk", String.format("更新groupTicket 票号[%s] 退款状态为:[%s]", 
																	gt.getTicketNo(), text));
						
			singleTicketLocalService.updateGroupTicketAndOrderStatus(gt.getId());
		}
	}
	*/
	
	public void saveList(List<GroupTicket> l ){
		getDao().saveList(l);
	}
	
}