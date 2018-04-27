package slfx.admin.controller.timer;

import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.xl.pojo.command.line.UpdateLineOrderStatusCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.dto.order.XLOrderStatusDTO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.pojo.system.XLOrderStatusConstant;
import slfx.xl.spi.inter.LineOrderService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/timer")
public class TimerController {
	@Autowired
	private LineOrderService lineOrderService;
	
	/**
	 * 
	 * @方法功能说明：定时检查线路订单是否已过期
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月14日下午5:26:57
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkPastLineOrder")
	@ResponseBody
	public String checkPastLineOrder(){
		long t1 = System.currentTimeMillis();
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_WAIT_PAY_BARGAIN_MONEY));
		lineOrderQO.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
		List<LineOrderDTO> lineOrderDTOList = lineOrderService.queryList(lineOrderQO);
		for (LineOrderDTO lineOrderDTO : lineOrderDTOList) {
			if(null == lineOrderDTO.getBaseInfo() || null == lineOrderDTO.getBaseInfo().getTravelDate()){
				continue;
			}
			Date travelDate = lineOrderDTO.getBaseInfo().getTravelDate();
			HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->发团日期=" + travelDate);
			Long today= System.currentTimeMillis();
			HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->更新日期today=" + today);
			if(null != lineOrderDTO.getLineSnapshot() && null != lineOrderDTO.getLineSnapshot().getLine()
						&& null != lineOrderDTO.getLineSnapshot().getLine().getPayInfo()
						&& null != lineOrderDTO.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()){
				//最晚付款
//				int payTotalDaysBeforeStart = lineOrderDTO.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart();
//				long beforeStart_long = payTotalDaysBeforeStart * 24 * 3600 *1000;
				long travelDate_long = travelDate.getTime();
//				long lastPayDate = travelDate_long - beforeStart_long;
				if(today > travelDate_long){
					HgLogger.getInstance().info("yuqz","TimerController->checkPastLineOrder->检查线路订单定时器>>>修改订单状态："+lineOrderDTOList.size());
					UpdateLineOrderStatusCommand command=new UpdateLineOrderStatusCommand();
					command.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_ORDER_CANCEL));
					command.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_WAIT_PAY_BARGAIN_MONEY));
					command.setOrderId(lineOrderDTO.getId());
					command.setDealerOrderCode(lineOrderDTO.getBaseInfo().getDealerOrderNo());
					try {
						boolean bol = lineOrderService.updateLineOrderStatus(command);
						if(bol){
							//通知经销商更改订单状态
							//根据订单id获取线路订单并通知 
							HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->通知修改订单状态开始>>>>>");
							XLUpdateOrderStatusMessageApiCommand apiCommand = new XLUpdateOrderStatusMessageApiCommand();
							//设置线路id
							apiCommand.setLineOrderID(lineOrderDTO.getBaseInfo().getDealerOrderNo());
							//设置游玩人信息
							LineOrderQO newLineOrderQO = new LineOrderQO();
							HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->线路订单id=" + lineOrderDTO.getId());
							newLineOrderQO.setId(lineOrderDTO.getId());
							LineOrderDTO newLineOrderDTO = lineOrderService.queryUnique(newLineOrderQO);
							HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->newLineOrderDTO=" + JSON.toJSONString(newLineOrderDTO));
							if(null != newLineOrderDTO.getTravelers()){
							
								for(LineOrderTravelerDTO lineOrderTravelerDTO:newLineOrderDTO.getTravelers()){
									LineOrderDTO lineOrderDTO2=new LineOrderDTO();
									XLOrderStatusDTO xlOrderStatus = new XLOrderStatusDTO();
									xlOrderStatus.setStatus(lineOrderTravelerDTO.getXlOrderStatus().getStatus() - 1000);
									xlOrderStatus.setPayStatus(lineOrderTravelerDTO.getXlOrderStatus().getPayStatus() - 1000);
									lineOrderDTO2.setId(lineOrderTravelerDTO.getLineOrder().getId());
									lineOrderTravelerDTO.setXlOrderStatus(xlOrderStatus);
									lineOrderTravelerDTO.setLineOrder(lineOrderDTO2);
								}
								apiCommand.setTravelers(newLineOrderDTO.getTravelers());
								apiCommand.setOrderNo(newLineOrderDTO.getBaseInfo().getOrderNo());
								//通知经销商游玩人订单状态改变
								lineOrderService.sendLineOrderUpdateMessage(apiCommand);
								HgLogger.getInstance().info("yuqz","TimerController->checkPastLineOrder->检查线路订单定时器>>>修改订单成功："+JSON.toJSONString(lineOrderDTO));
							}
						}else{
							HgLogger.getInstance().info("yuqz","TimerController->checkPastLineOrder->检查线路订单定时器>>>修改订单失败："+JSON.toJSONString(lineOrderDTO));
						}
					} catch (Exception e) {
						HgLogger.getInstance().error("yuqz","TimerController->checkPastLineOrder->检查线路订单定时器>>>修改订单状态出错："+HgLogger.getStackTrace(e));
					}
				}
				
		}
				
		}
		long t2 = System.currentTimeMillis();
		HgLogger.getInstance().info("yuqz", "TimerController->checkPastLineOrder->花费时间：" + (t2 - t1));
		return "success";
	}
}
