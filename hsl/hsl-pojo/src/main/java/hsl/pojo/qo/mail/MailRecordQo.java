package hsl.pojo.qo.mail;

import java.util.List;
import hg.log.base.BaseLogQo;

/**
 * @类功能说明：Mail邮件记录Qo
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午4:43:12
 */
public class MailRecordQo extends BaseLogQo {
	/**邮箱服务器地址**/
	private String host;
	/**邮件协议*/
	private String protocol;
	/**用户名 **/
	private String userName;
    /**是否安全连接 SSL*/
    private Boolean isSSL;
    /**标题**/
	private String title;
	/**发送人**/
	private String sender;
	/**接收人**/
	private List<String> accept;
	/**创建时间*/
	private String timeBefore;
	private String timeAfter;
	/**状态  0：成功，1：失败*/
	private int status = -1;
	/**错误信息*/
	private String errorStack;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public Boolean isSSL() {
		return isSSL;
	}
	public void setSSL(Boolean isSSL) {
		this.isSSL = isSSL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public List<String> getAccept() {
		return accept;
	}
	public String getAcceptStr(){
		return (null == this.accept)?null:this.accept.toString().replaceAll("(\\[|\\])+","");
	}
	public void setAccept(List<String> accept) {
		this.accept = accept;
	}
	public String getErrorStack() {
		return errorStack;
	}
	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTimeBefore() {
		return timeBefore;
	}
	public void setTimeBefore(String timeBefore) {
		this.timeBefore = timeBefore;
	}
	public String getTimeAfter() {
		return timeAfter;
	}
	public void setTimeAfter(String timeAfter) {
		this.timeAfter = timeAfter;
	}
}