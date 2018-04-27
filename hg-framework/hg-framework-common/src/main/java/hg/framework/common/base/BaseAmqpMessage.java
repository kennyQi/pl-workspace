package hg.framework.common.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 队列消息基类
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class BaseAmqpMessage<T> implements Serializable {

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

	public BaseAmqpMessage(Integer type, T content, Date sendDate, Map<String, Object> args) {
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
