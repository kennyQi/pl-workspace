package hg.common.component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @类功能说明：基础队列消息
 * @类修改者：
 * @修改日期：2014-10-20下午2:10:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-20下午2:10:54
 *
 * @param <T>
 */
public class BaseAmqpMessage<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息类型
	 */
	private Integer type;
	
	/**
	 * 消息内容
	 */
	private T content;

	/**
	 * 发送时间
	 */
	private Date sendDate;
	
	/**
	 * 额外参数
	 */
	private Map<String, Object> args;

	public BaseAmqpMessage() {
	}

	public BaseAmqpMessage(Integer type, T content) {
		this(type, content, null, null);
	}

	public BaseAmqpMessage(Integer type, T content, Date sendDate) {
		this(type, content, sendDate, null);
	}

	public BaseAmqpMessage(Integer type, T content, Date sendDate,
			Map<String, Object> args) {
		this.type = type;
		this.content = content;
		this.sendDate = sendDate;
		this.args = args;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Map<String, Object> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}

}
