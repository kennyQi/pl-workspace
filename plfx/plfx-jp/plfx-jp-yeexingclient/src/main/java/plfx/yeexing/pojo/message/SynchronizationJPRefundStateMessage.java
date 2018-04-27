package plfx.yeexing.pojo.message;

import hg.common.component.BaseAmqpMessage;
import plfx.yeexing.pojo.command.message.GNJPSyncRefundStateCommand;

/**
 * 
 * @类功能说明：同步机票信息基础消息队列
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月7日下午5:52:42
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SynchronizationJPRefundStateMessage extends BaseAmqpMessage<GNJPSyncRefundStateCommand>{
	
	/**
	 * 设置消息内容
	 * @param command
	 */
	public void setMessageContent(GNJPSyncRefundStateCommand command){
		setContent(command);
	}
	
	
	public static void main(String[] args) {
//		RabbitTemplate template = new RabbitTemplate();
//		
//		SynchronizationJPNotifyMessage baseAmqpMessage=new SynchronizationJPNotifyMessage();
//		
//		XLUpdateLineMessageApiCommand command=new XLUpdateLineMessageApiCommand();
//		command.setLineId("aaaa");
//		command.setStatus("1");
//		
//		baseAmqpMessage.setContent(command);
//		baseAmqpMessage.setType(1);
//		baseAmqpMessage.setSendDate(new Date());
//		baseAmqpMessage.setArgs(null);
//		template.convertAndSend("slfx.synchronizationLine",baseAmqpMessage);
	}
}
