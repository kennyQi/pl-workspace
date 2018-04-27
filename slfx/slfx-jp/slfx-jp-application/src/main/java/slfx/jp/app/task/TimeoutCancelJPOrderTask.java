package slfx.jp.app.task;


import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import slfx.api.util.HttpUtil;
import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.spi.inter.JPPlatformOrderService;

/**
 * @类功能说明：计划任务：定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年8月21日上午10:28:42
 * @版本：V1.0
 *
 */
public class TimeoutCancelJPOrderTask {
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
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
}
