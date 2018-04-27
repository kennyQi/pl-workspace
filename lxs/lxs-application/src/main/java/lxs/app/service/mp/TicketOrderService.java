package lxs.app.service.mp;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.app.dao.mp.TicketOrderDAO;
import lxs.app.dao.mp.TouristDAO;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.command.mp.AlipayCommand;
import lxs.pojo.exception.mp.CreateTicketOrderException;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.qo.mp.TicketOrderQO;
import lxs.pojo.qo.mp.TouristQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class TicketOrderService extends BaseServiceImpl<TicketOrder, TicketOrderQO, TicketOrderDAO>{

	@Autowired
	private TicketOrderDAO ticketOrderDAO;
	
	@Autowired
	private TouristDAO touristDAO;

	@Autowired
	private DZPWService dzpwService;
	
	@Autowired
	private AppService appService;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	
	@Override
	protected TicketOrderDAO getDao() {
		return ticketOrderDAO;
	}
	
	/**
	 * 
	 * @方法功能说明：收到支付宝通知
	 * @修改者名字：cangs
	 * @修改时间：2016年3月11日上午9:31:11
	 * @修改内容：
	 * @参数：@param alipayCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean receviedAlipayNotify(AlipayCommand alipayCommand){
			if(StringUtils.isNotBlank(alipayCommand.getDealerOrderNo())){
				TicketOrderQO ticketOrderQO = new TicketOrderQO();
				ticketOrderQO.setOrderNO(alipayCommand.getDealerOrderNo());
				TicketOrder ticketOrder = ticketOrderDAO.queryUnique(ticketOrderQO);
				if(ticketOrder!=null){
					try{
						ticketOrder.setPrice(alipayCommand.getPrice());
						ticketOrder.setPaymentAccount(alipayCommand.getPaymentAccount());
						ticketOrder.setSerialNumber(alipayCommand.getSerialNumber());
						ticketOrder.setRequestType(alipayCommand.getRequestType());
						//开始请求电子票务支付
						PayToTicketOrderCommand payToTicketOrderCommand = new PayToTicketOrderCommand();
						payToTicketOrderCommand.setDealerOrderId(alipayCommand.getDealerOrderNo());
						dzpwService.payToOrder(payToTicketOrderCommand);
						hg.dzpw.dealer.client.api.v1.request.TicketOrderQO DZPWTicketOrderQO = new hg.dzpw.dealer.client.api.v1.request.TicketOrderQO();
						DZPWTicketOrderQO.setOrderId(ticketOrder.getId());
						DZPWTicketOrderQO.setGroupTicketsFetch(true);
						DZPWTicketOrderQO.setSingleTicketsFetch(true);
						DZPWTicketOrderQO.setTouristFetch(true);
						TicketOrderResponse ticketOrderResponse = new TicketOrderResponse();
						try{
							ticketOrderResponse = dzpwService.queryTicketOrder(DZPWTicketOrderQO);
						}catch(DZPWException dzpwException){
							HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(dzpwException));
							throw new CreateTicketOrderException(dzpwException.getMessage());
						}
						TicketOrderDTO ticketOrderDTO = ticketOrderResponse.getTicketOrders().get(0);
						ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_PAY_WAIT);
						ticketOrder.setDZPWOrderSnapShot(JSON.toJSONString(ticketOrderDTO));
						ticketOrder.setLocalPayStatus(TicketOrder.WAIT_TO_PAY);
						ticketOrder.localstatus(TicketOrder.ORDER_STATUS_PAY_WAIT, TicketOrder.WAIT_TO_PAY, Tourist.NOTHING);
						ticketOrder.setPrintTicketDate(new Date());
						ticketOrderDAO.save(ticketOrder);
						TouristQO touristQO = new TouristQO();
						touristQO.setOrderID(ticketOrder.getId());
						List<Tourist> tourists = touristDAO.queryList(touristQO);
						//*******更新游玩人二维码
						for (Tourist tourist : tourists) {
							List<GroupTicketDTO> groupTicketDTOs = ticketOrderResponse.getTicketOrders().get(0).getGroupTickets();
							for (GroupTicketDTO groupTicketDTO : groupTicketDTOs) {
								if(StringUtils.equals(groupTicketDTO.getSingleTickets().get(0).getTourist().getIdNo(), tourist.getIdNo())){
									tourist.setQrCodeUrl(groupTicketDTO.getQrCodeUrl());
								}
							}
							touristDAO.update(tourist);
						}
						//更改本地支付状态
						ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_OUT_SUCC);
						ticketOrder.setLocalStatus(TicketOrder.PAYED);
						ticketOrder.localstatus(TicketOrder.ORDER_STATUS_OUT_SUCC, TicketOrder.PAYED, Tourist.NOTHING);
						ticketOrderDAO.update(ticketOrder);
						//更新销量
						if(StringUtils.isNotBlank(ticketOrder.getScenicSpotID())){
							ScenicSpot scenicSpot = scenicSpotDAO.get(ticketOrder.getScenicSpotID());
							if(scenicSpot!=null){
								scenicSpot.setSales(scenicSpot.getSales()+1);
								scenicSpotDAO.update(scenicSpot);
							}
						}
					}catch(Exception e){
						HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
						if(e instanceof DZPWException){
							//去支付电子票务失败 更改本地状态
							ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_OUT_FAIL);
							ticketOrder.setLocalStatus(TicketOrder.PAYED);
							ticketOrder.localstatus(TicketOrder.ORDER_STATUS_OUT_FAIL, TicketOrder.PAYED, Tourist.NOTHING);
							ticketOrderDAO.update(ticketOrder);
						}
						//本地更改状态失败 重新通知
						return false;
					}
				}else{
					return false;
				}
			}
		return true;
	}
	
	/**
	 * @Title: refundTicketOrder 
	 * @author guok
	 * @Description: 去电子票务申请退票
	 * @Time 2016年4月18日下午2:45:09
	 * @param applyRefundCommand void 设定文件
	 * @throws
	 */
	public boolean refundTicketOrder(String orderID) {
		HgLogger.getInstance().info("lxs_dev", "申请退票订单号："+orderID);
		TicketOrder ticketOrder = ticketOrderDAO.get(orderID);
		TouristQO touristQO = new TouristQO();
		touristQO.setOrderID(orderID);
		Tourist tourist = new Tourist();
		int touristSum = 0;
		if (ticketOrder != null) {
			try {
				ApplyRefundCommand applyRefundCommand = new ApplyRefundCommand();
				applyRefundCommand.setOrderId(ticketOrder.getId());
				touristSum = touristDAO.queryCount(touristQO);
				List<Tourist> tourists = touristDAO.queryList(touristQO);
				String ticketNo = "";
				//查看游客状态是否有用户手动退不掉的
				for (Tourist tourist2 : tourists) {
					if (tourist2.getLocalStatus()==Tourist.WAIT_TO_REFUND_USER) {
						ticketNo += tourist2.getTicketNo()+",";
						tourist = tourist2;
					}
				}
				String[] ticketNos = ticketNo.split(",");
				applyRefundCommand.setTicketNos(ticketNos);
				//申请退票
				dzpwService.refundTicketOrder(applyRefundCommand);
				/**
				 * 调用退款
				 */
				double refundMoney = 0.0;
				if(touristSum==0){
					throw new Exception();
				}else{
					refundMoney = ticketOrder.getPrice()/touristSum;
				}
				appService.alipayRefund(ticketOrder.getSerialNumber(), refundMoney);
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_CANCEL_SUCC);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_CANCEL_SUCC, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
				ticketOrderDAO.update(ticketOrder);
				tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
				touristDAO.update(tourist);
				
			} catch (Exception e) {
				HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * @Title: toOrder 
	 * @author guok
	 * @Description: 去电子票务申请出票
	 * @Time 2016年4月19日上午10:34:08
	 * @param orderID void 设定文件
	 * @throws
	 */
	public boolean toOrder(String orderID) {
		HgLogger.getInstance().info("lxs_dev", "申请出票订单号："+orderID);
		TicketOrder ticketOrder = ticketOrderDAO.get(orderID);
		try {
			//开始请求电子票务支付
			PayToTicketOrderCommand payToTicketOrderCommand = new PayToTicketOrderCommand();
			payToTicketOrderCommand.setDealerOrderId(ticketOrder.getOrderNO());
			dzpwService.payToOrder(payToTicketOrderCommand);
			//更改本地支付状态
			ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_OUT_SUCC);
			ticketOrder.setLocalStatus(TicketOrder.PAYED);
			ticketOrder.localstatus(TicketOrder.ORDER_STATUS_OUT_SUCC, TicketOrder.PAYED, Tourist.NOTHING);
			ticketOrderDAO.update(ticketOrder);
			return true;
		} catch (Exception e) {
			if(e instanceof DZPWException){
				//去支付电子票务失败 更改本地状态
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_OUT_FAIL);
				ticketOrder.setLocalStatus(TicketOrder.PAYED);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_OUT_FAIL, TicketOrder.PAYED, Tourist.NOTHING);
				ticketOrderDAO.update(ticketOrder);
			}
			return false;
		}
	}
}
