package plfx.xl.app.task;



import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import plfx.xl.pojo.command.order.CancleLineOrderCommand;
import plfx.xl.pojo.dto.order.LineOrderDTO;
import plfx.xl.pojo.qo.LineOrderQO;
import plfx.xl.pojo.system.XLOrderStatusConstant;
import plfx.xl.spi.inter.LineOrderService;

/**
 * @类功能说明：计划任务：定时 扫描 线路订单，超过20分钟未支付状态的订单，执行取消订单操作
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年5月13日上午10:28:42
 * @版本：V1.0
 *
 */
public class TimeoutCancelXLOrderTask {
	@Autowired
	private LineOrderService lineOrderService;
	
	public void run() {
		HgLogger.getInstance().debug("yaosanfeng", "TimeoutCancelXLOrderTask[检查订单超时定时任务开始执行]->时间："+new java.util.Date());
		try{
			//1.查询所有未支付的机票订单
			
            LineOrderQO lineOrderQO=new LineOrderQO();
            lineOrderQO.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
			List<LineOrderDTO> orderList=this.lineOrderService.queryList(lineOrderQO);
			//2.遍历查询结果，调用service 取消订单
			
			if(orderList!=null&&orderList.size()>0){
				for(LineOrderDTO lineOrderDTO:orderList){
					//创建时间超过20分钟的，执行取消订单操作
					Date createDate=lineOrderDTO.getBaseInfo().getCreateDate();
					long min=(new Date().getTime()-createDate.getTime())/60000;
					if(min>20){
						CancleLineOrderCommand command=new CancleLineOrderCommand();
						command.setLineOrderID(lineOrderDTO.getBaseInfo().getOrderNo());
						//command.se
//						command.setId(jpOrderDTO.getId());
//						command.setOrderId(jpOrderDTO.getOrderNo());
//						command.setCancelStatus(JPOrderStatusConstant.SLFX_TICKET_CANCEL);
//						
//						this.jpPlatformOrderService.adminCancelOrder(command);
						
						//同步给商城
					//	String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
					//	HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + jpOrderDTO.getDealerOrderCode() + "&status=" + JPOrderStatusConstant.SHOP_TICKET_CANCEL+ "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_NO_PAY, 60000);
					
					}
				}
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("yaosanfeng", "TimeoutCancelXLOrderTask->run->exception[取消订单操作]:" + HgLogger.getStackTrace(e));
		}
		
		HgLogger.getInstance().debug("yaosanfeng", "TimeoutCancelXLOrderTask[检查订单超时定时任务结束执行]->时间："+new java.util.Date());
	}
}
