package plfx.gjjp.app.common.message;

import java.util.Date;

import hg.common.component.BaseAmqpMessage;
import plfx.api.client.common.publish.PublishEventRequest;

/**
 * @类功能说明：平台通知消息
 * @类修改者：
 * @修改日期：2015-7-16下午5:33:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午5:33:38
 */
@SuppressWarnings("serial")
public class PlatformAmqpMessage extends BaseAmqpMessage<PublishEventRequest> {

	/**
	 * 经销商代码
	 */
	private String toDealerCode;

	public PlatformAmqpMessage() {
	}

	public PlatformAmqpMessage(String toDealerCode, PublishEventRequest content) {
		setToDealerCode(toDealerCode);
		setContent(content);
		setSendDate(new Date());
	}

	public String getToDealerCode() {
		return toDealerCode;
	}

	public void setToDealerCode(String toDealerCode) {
		this.toDealerCode = toDealerCode;
	}

}
