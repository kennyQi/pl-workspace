package hg.hjf.domain.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import hg.common.component.BaseModel;

@Entity
@Table(name = "JF_" + "OPERATION_LOG")
/**
 * 操作日志表
 * @author xinglj
 *
 */
public class OperationLog extends BaseModel {
	private static final int FAILREASON_255 = 255;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4385645340274314087L;

	/**
	 * 线程变量，以便在controller传递到service层
	 */
	public static ThreadLocal<OperationLog> threadLocal = new ThreadLocal<OperationLog>();
	
	private static final int returnDataLen = 512;

	private static final int operDataLen = 255;
	
	@Column(length = 30)
	private String loginName;
	
	@Column
	private Date operTime;
	
	@Column
	private String operType;
	
	@Column(length=operDataLen)
	private String operData;
	
	@Column(length=returnDataLen)
	private String returnData;
	
	@Column(length=20)
	private String ip;
//	@Column(length=100)
	@Transient
	private String uri;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	
	/**
	 * 入口。如管理端，个人端，移动端，商户端
	 */
	@Column(length=10)
	private String entry; 
	public static String entryPC="pc";
	public static String entryh5="h5";
	public static String entryApp="app";
	public static String entryWePlatfom="wp";
	public static String entryAdmin="admin";
	public static String entryEntAdmin="ent-admin";
	
	public String getReturnData() {
		return returnData;
	}

	public void setReturnData(String returnData) {
		if(returnData.length()>returnDataLen)
		{	returnData = returnData.substring(0,returnDataLen-3)+"...";
			System.out.println("too long returnData:"+returnData);
		}	
		this.returnData = returnData;
	}

	public String getOperData() {
		return operData;
	}

	public void setOperData(String operData) {
		if(operData.length()>operDataLen)
			operData = operData.substring(0,operDataLen-3)+"...";
		this.operData = operData;
	}

	String truncate(String str,int len){
		if(str!=null&&str.length()>len)
			str = str.substring(0,len-3)+"...";
		
		return str;
	}
	
	@Column
	private boolean operOk;
	@Column(length = FAILREASON_255)
	private String failReason;

	@Transient
	/**
	 * 在这个时刻到了之后再进行活动积分计算
	 */
	public long toCalTimeAt=-1;
		
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String longinName) {
		this.loginName = longinName;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public boolean isOperOk() {
		return operOk;
	}

	public void setOperOk(boolean operOk) {
		this.operOk = operOk;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = truncate(failReason, FAILREASON_255);
	}
	
	public String toString() {
		return this.loginName+" "+operTime +" "+ operType +" "+ operData +" "+(operOk?"yes":failReason);
	};
}
