package plfx.gjjp.app.common.message;

import java.util.Date;
import java.util.Map;

import hg.common.component.BaseAmqpMessage;

/**
 * @类功能说明：易行国际机票通知消息
 * @类修改者：
 * @修改日期：2015-7-16下午2:26:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午2:26:37
 */
@SuppressWarnings("serial")
public class YXGJJPAmqpMessage extends BaseAmqpMessage<Map<String, String>> {

	public YXGJJPAmqpMessage() {
	}

	public YXGJJPAmqpMessage(Map<String, String> paramMap) {
		setContent(paramMap);
		try {
			setType(Integer.valueOf(paramMap.get("type")));
		} catch (Exception e) {
			setType(0);
		}
		setSendDate(new Date());
	}

}
