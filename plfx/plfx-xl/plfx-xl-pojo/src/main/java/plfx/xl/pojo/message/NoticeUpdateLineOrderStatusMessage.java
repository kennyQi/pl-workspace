package plfx.xl.pojo.message;

import hg.common.component.BaseAmqpMessage;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;

@SuppressWarnings("serial")
public class NoticeUpdateLineOrderStatusMessage extends BaseAmqpMessage<XLUpdateOrderStatusMessageApiCommand>{
	/**
	 * 设置消息内容
	 * @param command
	 */
	public void setMessageContent(XLUpdateOrderStatusMessageApiCommand command){
		setContent(command);
	}
	
	public static void main(String[] args) {
		RabbitTemplate template = new RabbitTemplate();
		
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		
		XLUpdateLineMessageApiCommand command=new XLUpdateLineMessageApiCommand();
		command.setLineId("6ecbda41296346eb9ed1db547eec3c84");
		command.setStatus("1");
		
		baseAmqpMessage.setContent(command);
		baseAmqpMessage.setType(1);
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		template.convertAndSend("plfx.xl.noticeUpdateLineOrderStatus",baseAmqpMessage);
	}
}
