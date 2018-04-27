package slfx.xl.app.listener;

import hg.common.component.BaseAmqpMessage;
import hg.common.util.HttpUtil;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.dto.LineDealerDTO;
import slfx.xl.pojo.qo.LineDealerQO;
import slfx.xl.pojo.system.DealerConstants;
import slfx.xl.spi.inter.LineDealerService;
import slfx.xl.spi.inter.LineService;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：线路同步消息监听器
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年5月13日下午3:30:46
 * @版本：V1.0
 *
 */
public class AmqpMessageSlfxListener {
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private LineDealerService lineDealerService;
	

	@SuppressWarnings("unchecked")
	public void listen(Object message) {
		if(!(message instanceof BaseAmqpMessage)){
			return;			
		}
		String name = message.getClass().getName();//获取消息类名字
		HgLogger.getInstance().info("yuqz","线路监听开始name:" + name);
		//线路同步
		if(name.equals("slfx.xl.pojo.message.SynchronizationLineMessage")){
			HgLogger.getInstance().info("yuqz","线路同步监听器>>监听到消息：" + JSON.toJSONString(message, true));
			BaseAmqpMessage<XLUpdateLineMessageApiCommand> msg = (BaseAmqpMessage<XLUpdateLineMessageApiCommand>)message;
			
			if(msg != null){
				LineDealerQO qo = new LineDealerQO();
				qo.setStatus(DealerConstants.USE);
				List<LineDealerDTO> lineDealerList = lineDealerService.queryList(qo);
				for (LineDealerDTO dto : lineDealerList) {
					HgLogger.getInstance().info("yuqz", "通知线路同步经销商地址=" + dto.getDealerUrl());
					HttpUtil.reqForPost(dto.getDealerUrl() + "/api/sync/line","variableLine=" + JSON.toJSONString(msg.getContent()), 30000);
				}
	
			}
			HgLogger.getInstance().info("yuqz","线路同步直销结束");
		}

		//通知线路订单状态改变
		if(name.equals("slfx.xl.pojo.message.NoticeUpdateLineOrderStatusMessage")){
			HgLogger.getInstance().info("yuqz","通知线路订单状态改变监听器>>监听到消息：" + JSON.toJSONString(message, true));
			BaseAmqpMessage<XLUpdateOrderStatusMessageApiCommand> msg = (BaseAmqpMessage<XLUpdateOrderStatusMessageApiCommand>)message;
			
			if(msg != null){
				LineDealerQO qo = new LineDealerQO();
				qo.setStatus(DealerConstants.USE);
				qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
				List<LineDealerDTO> lineDealerList = lineDealerService.queryList(qo);
				for (LineDealerDTO dto : lineDealerList) {
					HgLogger.getInstance().info("yuqz", "通知修改订单状态经销商地址=" + dto.getDealerUrl());
					HttpUtil.reqForPost(dto.getDealerUrl() + "/api/sync/lineOrderStatus","variableLine=" + JSON.toJSONString(msg.getContent()), 30000);
				}
	
			}
			HgLogger.getInstance().info("yuqz","通知线路订单状态改变结束");
		}
		
		//通知线路订单金额改变
		if(name.equals("slfx.xl.pojo.message.NoticeUpdateLineOrderSalePriceMessage")){
			HgLogger.getInstance().info("yuqz","通知线路订单金额改变>>监听到消息：" + JSON.toJSONString(message, true));
			BaseAmqpMessage<XLUpdateOrderSalePriceMessageApiCommand> msg = (BaseAmqpMessage<XLUpdateOrderSalePriceMessageApiCommand>)message;
			
			if(msg != null){
				LineDealerQO qo = new LineDealerQO();
				qo.setStatus(DealerConstants.USE);
				qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
				List<LineDealerDTO> lineDealerList = lineDealerService.queryList(qo);
				for (LineDealerDTO dto : lineDealerList) {
					HgLogger.getInstance().info("yuqz", "通知线路订单金额改变经销商地址=" + dto.getDealerUrl());
					HttpUtil.reqForPost(dto.getDealerUrl() + "/api/sync/lineOrderSalePrice","variableLine=" + JSON.toJSONString(msg.getContent()), 30000);
				}
	
			}
			HgLogger.getInstance().info("yuqz","通知线路订单金额改变结束");
		}
		
	}
}
