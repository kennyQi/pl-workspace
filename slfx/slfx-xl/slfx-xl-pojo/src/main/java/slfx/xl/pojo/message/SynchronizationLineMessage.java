package slfx.xl.pojo.message;

import hg.common.component.BaseAmqpMessage;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;

/**
 * @类功能说明：同步线路基础消息队列
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：tandeng
 * @创建时间：2015-2-3下午2:10:54
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class SynchronizationLineMessage extends BaseAmqpMessage<XLUpdateLineMessageApiCommand>{
	
	/**
	 * 设置消息内容
	 * @param command
	 */
	public void setMessageContent(XLUpdateLineMessageApiCommand command){
		setContent(command);
	}
	
	
	public static void main(String[] args) {
		RabbitTemplate template = new RabbitTemplate();
		
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		
		XLUpdateLineMessageApiCommand command=new XLUpdateLineMessageApiCommand();
		command.setLineId("aaaa");
		command.setStatus("1");
		
		baseAmqpMessage.setContent(command);
		baseAmqpMessage.setType(1);
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		template.convertAndSend("slfx.synchronizationLine",baseAmqpMessage);
	}
}
