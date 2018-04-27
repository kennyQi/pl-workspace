package hg.dzpw.app.service.local;

import java.io.File;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hg.common.component.BaseServiceImpl;
//import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.app.dao.SingleTicketDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TouristQo;
//import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.domain.model.order.OrderStatus;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.command.platform.ticket.singleTicket.PlatformModifySingleTicketCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformCreateTouristCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformModifyTouristCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Service
@Transactional(rollbackFor = Exception.class)
public class SingleTicketLocalService extends BaseServiceImpl<SingleTicket, SingleTicketQo, SingleTicketDao> {

	@Autowired
	private SingleTicketDao dao;
	
	@Autowired
	private TouristLocalService touristService;
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private TicketOrderLocalService ticketOrderLocalService;
	
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	
	@Override
	protected SingleTicketDao getDao() {
		return dao;
	}

	/**
	 * 激活singleTicket
	 * @author CaiHuan
	 * @param command
	 * @param price
	 * @throws DZPWException
	 */
	public void platformModifySingleTicket(PlatformModifySingleTicketCommand command, Double price) throws DZPWException{
		
		if(StringUtils.isBlank(command.getSingleTicketId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少单票ID");
		if(StringUtils.isBlank(command.getTouristName()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少游客姓名");
		if(command.getIdType()==null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少证件类型");
		if(StringUtils.isBlank(command.getIdNo()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少证件号");
		
		SingleTicket singleTicket = getDao().get(command.getSingleTicketId());
		if (singleTicket == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "单票不存在");
		TouristQo touristQo = new TouristQo();
		touristQo.setName(command.getTouristName());
		touristQo.setIdType(command.getIdType());
		touristQo.setIdNo(command.getIdNo());
		Tourist tourist = touristService.queryUnique(touristQo);
		if(tourist!=null)
		{
			PlatformModifyTouristCommand mCommand = new PlatformModifyTouristCommand();
			mCommand.setBuyAmountTotal(price);
			tourist.modify(mCommand);
			getDao().update(tourist);
		}
		else
		{
			tourist = new Tourist();
			PlatformCreateTouristCommand tCommand = new PlatformCreateTouristCommand();
			tCommand.setTouristId(UUIDGenerator.getUUID());
			tCommand.setName(command.getTouristName());
			tCommand.setIdType(command.getIdType());
			tCommand.setIdNo(command.getIdNo());
			tCommand.setBuyAmountTotal(price);
			tourist.create(tCommand);
			getDao().save(tourist);
		}
		
		
		singleTicket.setTourist(tourist);
		singleTicket.platformModifySingleTicket(command);
		getDao().update(singleTicket);
	}

	/**
	 * 导出待激活订单
	 * @author CaiHuan
	 * @param singleTicketQo
	 * @return
	 * @throws Exception 
	 */
	public File exportActiveOrderToExcel(SingleTicketQo singleTicketQo) throws Exception {
		List<SingleTicket> singleTicketList = this.queryList(singleTicketQo);
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "编号", "票务编号","票务名称", "票务类型", "景区", "预定人", "预定手机号","下单时间","游玩人","证件类型","证件号码"};
		String[][] body = new String[singleTicketList.size()][title.length];
		for (int i = 0; i < singleTicketList.size(); i++) {
			SingleTicket singleTicket = singleTicketList.get(i);
			body[i][0] = singleTicket.getGroupTicket().getTicketOrder().getId();
			body[i][1] = singleTicket.getGroupTicket().getTicketNo();
			body[i][2] = singleTicket.getGroupTicket().getTicketPolicySnapshot().getBaseInfo().getName();
			body[i][3] = singleTicket.getGroupTicket().getTicketPolicySnapshot().getType()==1?"单票":"联票";
			body[i][4] = singleTicket.getTicketPolicySnapshot().getScenicSpot().getBaseInfo().getName();
			body[i][5] = singleTicket.getGroupTicket().getTicketOrder().getBaseInfo().getLinkMan();
			body[i][6] = singleTicket.getGroupTicket().getTicketOrder().getBaseInfo().getTelephone();
			body[i][7] = DateUtil.formatDate(singleTicket.getGroupTicket().getTicketOrder().getBaseInfo().getCreateDate(),"yyyy-MM-dd HH:mm:ss");
			body[i][9] = "身份证";
		}
		return ExportExcelUtils.createExcelFile("待激活票务导出", title, body, out);
	}

	/**
	 * 批量激活票务
	 * @author CaiHuan
	 * @param singleTicketList
	 * @throws DZPWException 
	 */
	public void batchActive(List<SingleTicket> singleTicketList) throws DZPWException {
		PlatformModifySingleTicketCommand command = new PlatformModifySingleTicketCommand();
		
		for(SingleTicket singleTicket:singleTicketList)
		{
			command.setSingleTicketId(singleTicket.getId());
			command.setTouristName(singleTicket.getTourist().getName());
			command.setIdType(singleTicket.getTourist().getIdType());
			command.setIdNo(singleTicket.getTourist().getIdNo());
			command.setStatus(SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE);
			Double price = singleTicket.getTicketPolicySnapshot().getBaseInfo().getPlayPrice();
			if(price==null)
				price=0.0d;
		    platformModifySingleTicket(command, price);
		}
		
	}

	
	/**
	 * @方法功能说明：更新singleTicket所属groupTicket和order的状态
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-28下午5:24:43
	 * @参数：@param groupTicket
	 * @return:void
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateGroupTicketAndOrderStatus(String groupTicketId){
		
		if (StringUtils.isNotBlank(groupTicketId)){
			
			GroupTicketQo gqo = new GroupTicketQo();
			gqo.setId(groupTicketId);
			gqo.setTicketOrdeQo(new TicketOrderQo());
			gqo.getTicketOrdeQo().setDealerQo(new DealerQo());
			GroupTicket groupTicket = groupTicketLocalService.queryUnique(gqo);
			
			TicketOrder order = groupTicket.getTicketOrder();
			
			// 查询 groupTicket 下的singleTicket
			SingleTicketQo sqo = new SingleTicketQo();
			sqo.setGroupTicketQo(gqo);
			gqo.setTicketOrdeQo(null);
			List<SingleTicket> sl = dao.queryList(sqo);
			
			Integer x = 0;
			// 遍历groupTicket下的singleTicket 状态归类
			HashMap<Integer, Object> statusMap = new HashMap<Integer, Object>();
			for (SingleTicket s : sl){
				statusMap.put(s.getStatus().getCurrent(), s);
				// 判断门票景区退款状态
				if (SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED == s.getStatus().getCurrent())
					x = x + 1;
			}
			
			// 是否有修改状态
			Boolean statusFlag = false;
			String text = "";
			
			if (x==sl.size()){
				groupTicket.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_ALL_SUCC);
				 text = "退款状态 = 退款成功";
				statusFlag = true;
			}
			
			if (x>0 && x<sl.size()){
				groupTicket.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_SOME_SUCC);
				text = "退款状态 = 部分退款成功";
				statusFlag = true;
			}
			
			// 按SingleTicket状态种类 设置groupTicket状态
			switch (statusMap.size()){
				case 1:
					// 只有一种状态     且是无需退款     设置groupTicket成交易成功
					if (statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_NO_REFUND)!=null){
						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC);
						text = text +"   当前状态 = 交易成功";
						statusFlag = true;
					}
					
					// 只有一种状态     且是已退款     设置groupTicket成交易关闭
					if (statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED)!=null){
						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE);
						groupTicket.getStatus().setCloseDate(new Date());//交易关闭时间
						text = text + "   当前状态 = 交易关闭";
						statusFlag = true;
					}
					
					// 只有一种状态     且是已结算     设置groupTicket成交易成功
					if (statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED)!=null){
						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC);
						text = text + "   当前状态 = 交易成功";
						statusFlag = true;
					}
					break;
				case 2:
					// 只有2种状态  且是已结算、无需退款   设置groupTicket成交易成功
					if (statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED)!=null 
							&& statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_NO_REFUND)!=null){
						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC);
						text = text + "   当前状态 = 交易成功";
						statusFlag = true;
					}
					
					// 只有2种状态  且是已结算、已退款    设置groupTicket成交易成功
					if (statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED)!=null 
							&& statusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED)!=null){
						groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC);
						text = text + "   当前状态 = 交易成功";
						statusFlag = true;
					}
			}
			// 更新groupTicket的状态
			if (statusFlag || x>0) {
				// 设置待推送
				ticketOrderManager.setPushOrder(order.getId(), groupTicket
						.getTicketOrder().getBaseInfo().getFromDealer()
						.getClientInfo().getKey());
				
				groupTicketLocalService.update(groupTicket);
				HgLogger.getInstance().info("yangk", String.format("更新groupTicket 票号[%s] 状态为:[%s]", 
																		groupTicket.getTicketNo(), text));
				
				statusFlag = false;
			}
			
			
			// 查询同订单下的门票
			gqo.setId(null);
			gqo.setTicketOrdeQo(new TicketOrderQo());
			gqo.getTicketOrdeQo().setId(order.getId());
			List<GroupTicket> gl = groupTicketLocalService.queryList(gqo);
			
			statusMap.clear();
			// 所有groupTicket状态归类
			for (GroupTicket g : gl){
				statusMap.put(g.getStatus().getCurrent(), gl);
			}
			
			text = "";
			
			switch(statusMap.size()){
				case 1:
					// 只有1种状态   且是交易关闭  设置订单状态 交易关闭
					if (statusMap.get(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE)!=null){
						order.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_DEAL_CLOSE);
						text = "当前状态 = 交易关闭";
						statusFlag = true;
					}
					
					// 只有1种状态   且是交易成功  设置订单状态 交易成功
					if (statusMap.get(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC)!=null){
						order.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_DEAL_SUCC);
						text = "当前状态 = 交易成功";
						statusFlag = true;
					}
					break;
				case 2:
					// 只有2种状态   且是交易成功、交易关闭    设置订单状态 交易成功
					if (statusMap.get(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC)!=null 
							&& GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE!=null){
						order.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_DEAL_SUCC);
						text = "当前状态 = 交易成功";
						statusFlag = true;
					}
			}
			
			// 状态修改时 更新
			if (statusFlag) {
				// 设置待推送
				ticketOrderManager.setPushOrder(order.getId(), groupTicket
						.getTicketOrder().getBaseInfo().getFromDealer()
						.getClientInfo().getKey());
				
				ticketOrderLocalService.update(order);
				HgLogger.getInstance().info("yangk", String.format("更新ticketOrder 订单号[%s] 状态为:[%s]", 
																		order.getId(), text));
			}
		}
	}

	/**
	 * 设置结算
	 * @author CaiHuan
	 * @param singleTicketId
	 */
	public void settle(String singleTicketId) throws Exception{
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		singleTicketQo.setId(singleTicketId);
		SingleTicket singleTicket = queryUnique(singleTicketQo);
		if(singleTicket==null)
		    throw new Exception("该票务不存在!!");
		if(singleTicket.getStatus().getCurrent()!=SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)
			throw new Exception("该票务不可以结算!请检查");
		Date date = new Date();
		if(singleTicket.getUseDateEnd().after(date))
		{
			throw new Exception("该票务游玩时间未结束，暂时无法结算!");
		}
		singleTicket.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED);
		singleTicket.getSettlementInfo().setSettlementDate(new Date());
		getDao().update(singleTicket);
		this.updateGroupTicketAndOrderStatus(singleTicket.getGroupTicket().getId());
	}


	/**
	 * 
	 * @描述： 修改申请退款后门票状态和门票下景区状态为待退款
	 * @author: guotx 
	 * @version: 2016-3-28 下午3:57:41
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateCanRefundSingleStatus(String[] refundTicketNos) {
		SingleTicketQo qo=new SingleTicketQo();
		GroupTicketQo groupTicketQo=new GroupTicketQo();
		qo.setGroupTicketQo(groupTicketQo);
		for (int i = 0; i < refundTicketNos.length; i++) {
			groupTicketQo.setTicketNo(refundTicketNos[i]);
			//更新门票状态
			GroupTicket groupTicket = groupTicketLocalService.queryUnique(groupTicketQo);
			if (groupTicket!=null) {
				HgLogger.getInstance().info("guotx", "申请退款后修改门票退款状态，票号："+groupTicket.getTicketNo()+"状态："+groupTicket.getStatus().getRefundCurrent()+"->"+GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE);
				groupTicket.getStatus().setRefundCurrent(GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE);
				groupTicketLocalService.update(groupTicket);
			}
			//更新景区状态
			List<SingleTicket> singleTickets = getDao().queryList(qo);
			for (SingleTicket singleTicket : singleTickets) {
				if (singleTicket.getStatus().getCurrent()!=SingleTicketStatus.SINGLE_TICKET_CURRENT_USED) {
					HgLogger.getInstance().info("guotx", "更新单票状态，票号"+singleTicket.getTicketNo()+",状态："+singleTicket.getStatus().getCurrent()+"->"+SingleTicketStatus.SINGLE_TICKET_CURRENT_WAIT_REFUND);
					singleTicket.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_CURRENT_WAIT_REFUND);
					getDao().update(singleTicket);
				}
			}
		}
	}

	/**
	 * 
	 * @描述：退款成功后更新景区状态 ，该为退款成功
	 * @author: guotx 
	 * @version: 2016-3-28 下午4:34:45
	 */
	public void updateRefundSuccessStatus(String groupTicketId) {
		HgLogger.getInstance().info("guotx", "【支付宝退款异步通知后续操作，修改关联景区状态】门票id："+groupTicketId);
		SingleTicketQo qo=new SingleTicketQo();
		GroupTicketQo groupTicketQo=new GroupTicketQo();
		qo.setGroupTicketQo(groupTicketQo);
			groupTicketQo.setId(groupTicketId);
			List<SingleTicket> singleTickets = getDao().queryList(qo);
			for (SingleTicket singleTicket : singleTickets) {
				HgLogger.getInstance().info("guotx", "【支付宝退款异步通知接口后续操作，修改景区状态】票号"+singleTicket.getTicketNo()+"当前状态值："+singleTicket.getStatus().getCurrent());
				if (singleTicket.getStatus().getCurrent()==SingleTicketStatus.SINGLE_TICKET_CURRENT_WAIT_REFUND) {
					singleTicket.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED);
					getDao().update(singleTicket);
				}
			}
		
	}
	
}
