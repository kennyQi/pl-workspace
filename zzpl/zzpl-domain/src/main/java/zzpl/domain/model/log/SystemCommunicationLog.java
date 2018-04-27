package zzpl.domain.model.log;

import hg.common.component.BaseCommand;
import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import zzpl.domain.model.M;
import zzpl.pojo.command.log.SystemCommunicationLogHTTPCommand;
import zzpl.pojo.command.log.SystemCommunicationLogNotifyCommand;

@Entity
@Table(name="SYETEM_COMMUNICATION_LOG")
@SuppressWarnings("serial")
public class SystemCommunicationLog extends BaseModel{

	/**
	 * 请求发送地址
	 */
	@Column(name="URL",length=512)
	private String url ;
	
	/**
	 * 此系统在经销商系统中key
	 */
	@Column(name="DEALER_KEY",length=512)
	private String dealerKey;
	
	/**
	 * 密匙
	 */
	@Column(name="SECRET_KEY",length=512)
	private String secretKey;
	
	/**
	 * 请求体
	 */
	@Column(name="REQUEST",columnDefinition="LONGTEXT")
	private String requset;
	
	/**
	 * 请求结果
	 */
	@Column(name="RESPONSE",columnDefinition="LONGTEXT")
	private String response;
	
	/**
	 * 通知发送方
	 */
	@Column(name="NOTIFY_HOST",length=512)
	private String notifyHost;
	
	/**
	 * 通知内容
	 */
	@Column(name="NOTIFY_CONTENT",columnDefinition="LONGTEXT")
	private String notifyContent;
	
	/**
	 * 通信类型
	 * 1：发出http请求
	 * 2：收到通知
	 */
	public final static String SEND_HTTP = "SEND_HTTP";
	public final static String RECEIVED_NOTIFY = "RECEIVED_NOTIFY";
	@Column(name="COMMUNICTAION_TYPE",length=512)
	private String communicationType;
	
	/**
	 * 创建时间
	 */
	@Column(name="time",columnDefinition=M.DATE_COLUM)
	private Date time;

	/**
	 * 
	 * @类名：SystemCommunicationLog.java
	 * @描述：TODO
	 * @@param command
	 */
	public SystemCommunicationLog(BaseCommand command,String id){
		if(command instanceof SystemCommunicationLogNotifyCommand){
			SystemCommunicationLogNotifyCommand notfiyCommand = BeanMapperUtils.getMapper().map(command, SystemCommunicationLogNotifyCommand.class);
			this.setCommunicationType(SEND_HTTP);
			this.setTime(new Date());
			if(StringUtils.isNotBlank(notfiyCommand.getNotifyHost())){
				this.setNotifyHost(notfiyCommand.getNotifyHost());
			}
			if(StringUtils.isNotBlank(notfiyCommand.getNotifyContent())){
				this.setNotifyContent(notfiyCommand.getNotifyContent());
			}
			this.setId(id);
		}
		if(command instanceof SystemCommunicationLogHTTPCommand){
			SystemCommunicationLogHTTPCommand httpcCommand = BeanMapperUtils.getMapper().map(command, SystemCommunicationLogHTTPCommand.class);
			this.setCommunicationType(SEND_HTTP);
			this.setTime(new Date());
			if(StringUtils.isNotBlank(httpcCommand.getUrl())){
				this.setUrl(httpcCommand.getUrl());
			}
			if(StringUtils.isNotBlank(httpcCommand.getSecretKey())){
				this.setSecretKey(httpcCommand.getSecretKey());
			}
			if(StringUtils.isNotBlank(httpcCommand.getDealerKey())){
				this.setDealerKey(httpcCommand.getDealerKey());
			}
			if(StringUtils.isNotBlank(httpcCommand.getRequset())){
				this.setRequset(httpcCommand.getRequset());
			}
			if(StringUtils.isNotBlank(httpcCommand.getResponse())){
				this.setResponse(httpcCommand.getResponse());
			}
			this.setId(id);
		}
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRequset() {
		return requset;
	}

	public void setRequset(String requset) {
		this.requset = requset;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getNotifyHost() {
		return notifyHost;
	}

	public void setNotifyHost(String notifyHost) {
		this.notifyHost = notifyHost;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
