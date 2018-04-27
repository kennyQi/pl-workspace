package hg.log.po.normal;

import hg.log.po.base.BaseLog;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @类功能说明：汇购Mail邮件
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月23日 下午6:21:15
 */
@Document
public class HgMail extends BaseLog {
	/**邮箱服务器地址**/
	private String host;
	/**邮件协议*/
	private String protocol;
	/**用户名 **/
	private String userName;
	/**密码**/
    private String password;
    /**是否安全连接 SSL*/
    private boolean isSSL;
    //--------------INFO
    /**标题**/
	private String title;
	/**内容**/
	private String content;
	/**发送人**/
	private String sender;
	/**接收人**/
	private List<String> accept;
	/**抄送地址**/
	private List<String> notice;
	/**附件   (对象类型可以是File、URL、InputStream、byte数组或是表示byte数组的字符串)**/
	private List<Object> annex;
	/**结束时间*/
	private Date endTime;
	/**状态  0：成功，1：失败*/
	private int status;
	//--------------LOG
	/**错误信息*/
	private String errorStack;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol == null ? null : protocol.trim();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender == null ? null : sender.trim();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host == null ? null : host.trim();
	}
	public boolean isSSL() {
		return isSSL;
	}
	public void setSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}
	public List<String> getAccept() {
		return accept;
	}
	public void setAccept(List<String> accept) {
		this.accept = accept;
	}
	public List<Object> getAnnex() {
		return annex;
	}
	/**
	 * @param annex 附件列表   (对象类型可以是File、URL、InputStream、byte数组或是表示byte数组的字符串)
	 * 注意：如果附件内容是URL的话，URL不能由域名+路径表示的文件或者带有查询参数，因为本身通过URL获取文件时，这种URL获得的文件是没有后缀名或者后缀名错误。所以请注意这种情况！
	 */
	public void setAnnex(List<Object> annex) {
		this.annex = annex;
	}
	public List<String> getNotice() {
		return notice;
	}
	public void setNotice(List<String> notice) {
		this.notice = notice;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getErrorStack() {
		return errorStack;
	}
	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}