package hgtech.jfcal.model;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;
import hgtech.jfcal.dao.RuledSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：积分规则
 * @类修改者：
 * @修改日期：2014年11月4日上午11:11:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月4日上午11:11:36
 *
 */
public class Rule implements Entity<StringUK>{
	/**
     * @FieldsINT_ONEDAY:TODO
     */
    public static final int INT_ONEDAY = 24*60*60*1000;
	public String name,code;
	/**
	 * 实现逻辑的类
	 */
	public Class logic;
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @param logicClass the logicClass to set
	 */
	public void setLogicClass(String logicClass) {
		this.logicClass = logicClass;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(RuledSession session) {
		this.session = session;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(Object accountType) {
		if(accountType==null)
			throw new RuntimeException("accountType can not be null!");
		this.accountType = accountType;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param sTATUS_Y the sTATUS_Y to set
	 */
	public static void setSTATUS_Y(String sTATUS_Y) {
		STATUS_Y = sTATUS_Y;
	}
	/**
	 * @param sTATUS_N the sTATUS_N to set
	 */
	public static void setSTATUS_N(String sTATUS_N) {
		STATUS_N = sTATUS_N;
	}

	public String logicClass;
	public Map<String, String> parameters=new HashMap<String, String>();
	public RuledSession session;
	public Project project;
	private Object accountType;
	public Date startDate,endDate;
	public String status;
	public int jfValidYear;
	public static String STATUS_Y="y";
	/**
	 * 作废
	 */
	public static String STATUS_N="n";
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#getUK()
	 */
	@Override
	public StringUK readUK() {
		return new StringUK(code);
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
		code=uk.toString();
	}
	
	/**
	 * 
	 * @方法功能说明：在期限、且不作废
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月21日上午10:46:42
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public boolean isValid(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		long now=0;
		try {
			now = df.parse( df.format(new Date())).getTime();
		} catch (ParseException e) { e.printStackTrace();}
		return (!STATUS_N.equalsIgnoreCase(status)) 
				&& (startDate.getTime()<=now && now<endDate.getTime()+INT_ONEDAY);
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	/**
	 * @return the logic
	 */
	public Class getLogic() {
	    return logic;
	}
	/**
	 * @param logic the logic to set
	 */
	public void setLogic(Class logic) {
	    this.logic = logic;
	}
	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
	    return code;
	}
	/**
	 * @return the logicClass
	 */
	public String getLogicClass() {
	    return logicClass;
	}
	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
	    return parameters;
	}
	/**
	 * @return the session
	 */
	public RuledSession getSession() {
	    return session;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
	    return project;
	}
	/**
	 * @return the accountType
	 */
	public Object getAccountType() {
	    return accountType;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
	    return startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
	    return endDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
	    return status;
	}
 
	public int getJfValidYear() {
		return jfValidYear;
	}
	public void setJfValidYear(int jfValidYear) {
		this.jfValidYear = jfValidYear;
	}
}
