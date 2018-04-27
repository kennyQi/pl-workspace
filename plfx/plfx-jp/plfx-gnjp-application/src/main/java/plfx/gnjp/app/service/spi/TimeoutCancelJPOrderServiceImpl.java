package plfx.gnjp.app.service.spi;

import hg.common.util.DateUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.gnjp.app.service.local.JPOrderLogLocalService;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.JPWebService;
import plfx.yeexing.inter.TimeoutCancelJPOrderService;
import plfx.yeexing.pojo.command.order.JPCancelTicketSpiCommand;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.JPPassengerDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

import com.alibaba.fastjson.JSON;

@Service("timeoutCancelJPOrderService")
public class TimeoutCancelJPOrderServiceImpl implements
		TimeoutCancelJPOrderService {
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private JPOrderLogLocalService jpOrderLogLocalService;
	@Autowired
	private JPWebService jPWebService;
	
	@Override
	public void timeoutCancelJPOrderTask() {
		HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask[检查订单超时定时任务开始执行]->时间："+new java.util.Date());
		//1.查询所有未支付的机票订单
		PlatformOrderQO jpOrderQO =new PlatformOrderQO();
		jpOrderQO.setRefundType("A");
		jpOrderQO.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_PAY_WAIT));
		List<JPOrderDTO> orderList = jpPlatformOrderService.queryList(jpOrderQO);
		//2.遍历查询结果，调用service 取消订单
		HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask[检查订单超时定时任务]->orderList：" + JSON.toJSONString(orderList));
		for(JPOrderDTO jpOrderDTO : orderList){
			HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask[检查订单超时定时任务]->要取消的订单,jpOrderDTO=" + JSON.toJSONString(jpOrderDTO));
			//创建时间超过20分钟的，执行取消订单操作
			Date createDate= DateUtil.parseDateTime(jpOrderDTO.getCreateTime());
			long min=(new Date().getTime()-createDate.getTime())/60000;
			HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask,=min" + min);
			if(min>20){
				JPCancelTicketSpiCommand command = new JPCancelTicketSpiCommand();
				command.setUserName(SysProperties.getInstance().get("yeeXing_userName"));
				command.setOrderid(jpOrderDTO.getYeeXingOrderId());
				String passengerName = "";
				for(JPPassengerDTO jpPassengerDTO : jpOrderDTO.getPassengerList()){
					if(jpPassengerDTO.getName() != null){
						if(passengerName.equals("")){
							passengerName = passengerName + jpPassengerDTO.getName();
						}else{
							passengerName = passengerName + "^" + jpPassengerDTO.getName();
						}
					}
				}
				if(StringUtils.isNotBlank(passengerName) && StringUtils.isNotBlank(command.getOrderid())){
					command.setPassengerName(passengerName);
					HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask[检查订单超时定时任务]->订单取消开始,command=" + JSON.toJSONString(command));
					//admin端取消订单
					jPWebService.plfxCancelTicket(command);
					HgLogger.getInstance().info("yuqz", "TimeoutCancelJPOrderTask[检查订单超时定时任务]->订单取消完成,jpOrderDTO=" + JSON.toJSONString(jpOrderDTO));
				}
			}
		}
		
	}

}
