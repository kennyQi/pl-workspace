package slfx.xl.pojo.message;

import hg.common.component.BaseAmqpMessage;
import slfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;

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
