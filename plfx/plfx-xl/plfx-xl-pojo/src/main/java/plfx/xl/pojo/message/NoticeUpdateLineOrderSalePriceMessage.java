package plfx.xl.pojo.message;

import hg.common.component.BaseAmqpMessage;
import plfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;

@SuppressWarnings("serial")
public class NoticeUpdateLineOrderSalePriceMessage extends BaseAmqpMessage<XLUpdateOrderSalePriceMessageApiCommand>{
	/**
	 * 设置消息内容
	 * @param command
	 */
	public void setMessageContent(XLUpdateOrderSalePriceMessageApiCommand command){
		setContent(command);
	}
	
}
