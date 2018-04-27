/**
 * @文件名称：Domain.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午2:27:30
 */
package hgtech.jfaccount;

import hgtech.jf.tree.WithChildren;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 积分单位
 * @类修改者：
 * @修改日期：2014-9-5下午2:27:30
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:27:30
 *
 */
public class Domain implements WithChildren<Domain>, Serializable{
	/**
	 */
	private static final long serialVersionUID = 7360826938940924197L;

	/**
	 * 编码，唯一性约束
	 */
	public String code;
	
	public String name;
	
	/**
	 * 系统设置是否验证签名
	 */
	public Boolean isDetectionSignature = false;
	
	/**
	 * 访问系统约定的密
	 */
	public String passK;
	
	//ip，只有这个ip才可以访问服务
	public String ip;
	
	
	/**
	 * 行业类别
	 */
	public TradeType type;
	/**
	 * 是否共享会员.默认是。
	 */
	public Boolean shareUser= true;;
	/**
	 * 预付费警戒线
	 */
	public int prepaidCordon;
	/**
	 * 停发积分线
	 */
	public int suspendedLine;
	/**
	 * 状态
	 */
	public String status;
	public static String STATUS_ALARM="alarm";
	public static String STATUS_stop="stop";
	private static Map<String,String> statusMap=new HashMap<>();
	/**
	 * 上级机构
	 */
	public Domain upperDomain;
	/**
	 * 与汇购积分比率
	 * @deprecated 使用积分类型的比率
	 */
	public float jfRate;
	
	/**
	 * 默认积分类型
	 */
	private JfAccountType defaultAccountType;
	/**
	 * 默认绑定的一个用户
	 */
	public String defaultBindUser;
	/**
	 * 其他属性
	 */
	public Map<String,Object> properties = new HashMap<>();

	public LinkedList<WithChildren<Domain>> sublist=new LinkedList<WithChildren<Domain>>();

	/**
	 * 注册地址
	 */
	public String registerUrl;
	/**
	 * 积分对接 服务的 url.
	 */
	public String jfAdapterUrl;

	/**
	 * @FieldsCHECK_BIND_USER_SMS:绑定渠道的账户时候验证手机地址
	 */
	public  String  checkBindUserSms;
	
	public String bindUserUrl;

	/**
	 * @FieldsSEND_BIND_USER_SMS:绑定渠道的账户时候验证手机地址
	 */
	public  String sendBindUserSms;
	
	/**
	 * 可转入属性
	 */
	public boolean canIn, canOut; 	
	
	//是否必须绑定账户才可互转积分
	public boolean mustBind = true;
	//是否在客户端显示
	public boolean canShow = true;
	
	static{
		statusMap.put(STATUS_ALARM, "预付金预警");
		statusMap.put(STATUS_stop, "积分停发");
		statusMap.put(null, "积分正常发放");
	}
	
	public   String getStatusText(){
		return statusMap.get(status);
	}
	public boolean isStatusAlarm(){
		return STATUS_ALARM.equals(status);
	}
	public boolean isStatusStop(){
		return STATUS_stop.equals(status);
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getSubList()
	 */
	@Override
	public LinkedList<WithChildren<Domain>> getSubList() {
		return sublist;
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public Domain getMe() {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TradeType getType() {
		return type;
	}
	public void setType(TradeType type) {
		this.type = type;
	}
	public Domain getUpperDomain() {
		return upperDomain;
	}
	public void setUpperDomain(Domain upperDomain) {
		this.upperDomain = upperDomain;
	}
	public float getJfRate() {
		return jfRate;
	}
	public void setJfRate(float jfRate) {
		this.jfRate = jfRate;
	}
	public LinkedList<WithChildren<Domain>> getSublist() {
		return sublist;
	}
	public void setSublist(LinkedList<WithChildren<Domain>> sublist) {
		this.sublist = sublist;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPassK() {
		return passK;
	}
	public void setPassK(String passK) {
		this.passK = passK;
	}
	public Boolean getIsDetectionSignature() {
		return isDetectionSignature;
	}
	public void setIsDetectionSignature(Boolean isDetectionSignature) {
		this.isDetectionSignature = isDetectionSignature;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * @return the registerUrl
	 */
	public String getRegisterUrl() {
	    return registerUrl;
	}
	/**
	 * @param registerUrl the registerUrl to set
	 */
	public void setRegisterUrl(String registerUrl) {
	    this.registerUrl = registerUrl;
	}
	/**
	 * @return the defaultBindUser
	 */
	public String getDefaultBindUser() {
	    return defaultBindUser;
	}
	/**
	 * @param defaultBindUser the defaultBindUser to set
	 */
	public void setDefaultBindUser(String defaultBindUser) {
	    this.defaultBindUser = defaultBindUser;
	}
	/**
	 * @return the jfAdapterUrl
	 */
	public String getJfAdapterUrl() {
	    return jfAdapterUrl;
	}
	/**
	 * @param jfAdapterUrl the jfAdapterUrl to set
	 */
	public void setJfAdapterUrl(String jfAdapterUrl) {
	    this.jfAdapterUrl = jfAdapterUrl;
	}
	/**
	 * @return the checkBindUserSms
	 */
	public String getCheckBindUserSms() {
	    return checkBindUserSms;
	}
	/**
	 * @param checkBindUserSms the checkBindUserSms to set
	 */
	public void setCheckBindUserSms(String checkBindUserSms) {
	    this.checkBindUserSms = checkBindUserSms;
	}
	/**
	 * @return the sendBindUserSms
	 */
	public String getSendBindUserSms() {
	    return sendBindUserSms;
	}
	/**
	 * @param sendBindUserSms the sendBindUserSms to set
	 */
	public void setSendBindUserSms(String sendBindUserSms) {
	    this.sendBindUserSms = sendBindUserSms;
	}
	/**
	 * @return the canIn
	 */
	public boolean isCanIn() {
	    return canIn;
	}
	/**
	 * @param canIn the canIn to set
	 */
	public void setCanIn(boolean canIn) {
	    this.canIn = canIn;
	}
	/**
	 * @return the canOut
	 */
	public boolean isCanOut() {
	    return canOut;
	}
	/**
	 * @param canOut the canOut to set
	 */
	public void setCanOut(boolean canOut) {
	    this.canOut = canOut;
	}
	/**
	 * @return the defaultAccountType
	 */
	public JfAccountType getDefaultAccountType() {
	    return defaultAccountType;
	}
	/**
	 * @param defaultAccountType the defaultAccountType to set
	 */
	public void setDefaultAccountType(JfAccountType defaultAccountType) {
	    this.defaultAccountType = defaultAccountType;
	}
	/**
	 * @return the bindUserUrl
	 */
	public String getBindUserUrl() {
	    return bindUserUrl;
	}
	/**
	 * @param bindUserUrl the bindUserUrl to set
	 */
	public void setBindUserUrl(String bindUserUrl) {
	    this.bindUserUrl = bindUserUrl;
	}
	/**
	 * @return the mustBind
	 */
	public boolean isMustBind() {
	    return mustBind;
	}
	/**
	 * @param mustBind the mustBind to set
	 */
	public void setMustBind(boolean mustBind) {
	    this.mustBind = mustBind;
	}
	public boolean isCanShow() {
		return canShow;
	}
	public void setCanShow(boolean canShow) {
		this.canShow = canShow;
	}
	/**
	 *获取共享用户
	 */
	public Boolean isShareUser() {
		if(shareUser==null){
			return false;
		}
		return shareUser;
	}
	/**
	 *设置共享用户
	 */
	public void setShareUser(Boolean shareUser) {
		this.shareUser = shareUser;
	}
	/**
	 * 获取预付费警戒线
	 */
	public int getPrepaidCordon() {
		return prepaidCordon;
	}
	/**
	 * 设置预付费警戒线
	 */
	public void setPrepaidCordon(int prepaidCordon) {
		this.prepaidCordon = prepaidCordon;
	}
	/**
	 * 获取停发积分线
	 */
	public int getSuspendedLine() {
		return suspendedLine;
	}
	/**
	 * 设置停发积分线
	 */
	public void setSuspendedLine(int suspendedLine) {
		this.suspendedLine = suspendedLine;
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	public Boolean getShareUser() {
		return shareUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
