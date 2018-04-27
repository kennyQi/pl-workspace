package slfx.jp.app.service.spi;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.util.HttpUtil;
import slfx.jp.app.service.local.JPOrderLocalService;
import slfx.jp.app.service.local.JPOrderLogLocalService;
import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.system.OrderLogConstants;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.TimeoutCancelJPOrderService;
@Service("timeoutCancelJPOrderService")
public class TimeoutCancelJPOrderServiceImpl implements
		TimeoutCancelJPOrderService {
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private JPOrderLogLocalService jpOrderLogLocalService;
	@Autowired
	private JPOrderLocalService jpOrderLocalService;
	
	public void run() {
		HgLogger.getInstance().debug("qiuxianxiang", "TimeoutCancelJPOrderTask[检查订单超时定时任务开始执行]->时间："+new java.util.Date());
		try{
			//1.查询所有未支付的机票订单
			PlatformOrderQO jpOrderQO =new PlatformOrderQO();

			jpOrderQO.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_PAY_WAIT));
			List<JPOrderDTO> orderList=this.jpPlatformOrderService.queryErrorJPOrderList(jpOrderQO);
			//2.遍历查询结果，调用service 取消订单
			
			if(orderList!=null&&orderList.size()>0){
				for(JPOrderDTO jpOrderDTO:orderList){
					//创建时间超过20分钟的，执行取消订单操作
					Date createDate=jpOrderDTO.getCreateDate();
					long min=(new Date().getTime()-createDate.getTime())/60000;
					if(min>20){
						AdminCancelOrderCommand command=new AdminCancelOrderCommand();
						command.setId(jpOrderDTO.getId());
						command.setOrderId(jpOrderDTO.getOrderNo());
						command.setCancelStatus(JPOrderStatusConstant.SLFX_TICKET_CANCEL);
						
						this.jpPlatformOrderService.adminCancelOrder(command);
						JPOrder jpOrder = jpOrderLocalService.get(command.getOrderId());
						if(jpOrder != null){
							//添加操作日志
							CreateJPOrderLogCommand logCommand = new CreateJPOrderLogCommand();
							logCommand.setLogName(OrderLogConstants.CANCEL_LOG_NAME);
							logCommand.setWorker(OrderLogConstants.TIMER_WORKER);
							logCommand.setLogInfo(OrderLogConstants.CANCEL_TIMER_LOG_INFO);
							logCommand.setJpOrderId(command.getOrderId());
							jpOrderLogLocalService.create(logCommand,jpOrder);
						}
						//同步给商城
						String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
						HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrderDTO.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_CANCEL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_NO_PAY, 60000);
					
					}
				}
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("qiuxianxiang", "TimeoutCancelJPOrderTask->run->exception[取消订单操作]:" + HgLogger.getStackTrace(e));
		}
		
		HgLogger.getInstance().debug("qiuxianxiang", "TimeoutCancelJPOrderTask[检查订单超时定时任务结束执行]->时间："+new java.util.Date());
	}
	
	@Override
	public void timeoutCancelJPOrderTask() {
		run();
	}

}
