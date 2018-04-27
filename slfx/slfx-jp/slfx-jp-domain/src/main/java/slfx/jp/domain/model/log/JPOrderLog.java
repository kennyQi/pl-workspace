package slfx.jp.domain.model.log;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.domain.model.J;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.system.OrderLogConstants;

@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "ORDER_LOG")
public class JPOrderLog extends BaseModel{
	/**
	 *日志操作时间
	 */
	@Column(name = "LOG_DATE", columnDefinition = J.DATE_COLUM)
	private Date logDate;
	
	/**
	 * 操作名称
	 */
	@Column(name = "LOG_NAME", length = 64)
	private   String   logName;
	
	/**
	 * 操作人
	 */
	@Column(name = "LOG_WORKER", length = 64)
	private    String     logWorker;
	
	/**
	 * 操作信息
	 */
	@Column(name = "LOG_INFO", length = 512)
	private    String     logInfo;
	
	/**
	 * 机票订单基本信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JP_ORDER_ID")
	private  JPOrder jpOrder;

	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年1月21日上午10:46:32
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void create(CreateJPOrderLogCommand command,JPOrder order){
		setId(UUIDGenerator.getUUID());
		setLogDate(new Date());
		//操作名称
		setLogName(OrderLogConstants.NAME_MAP.get(command.getLogName()));
		//操作人
		String worker = OrderLogConstants.WORKER_MAP.get(command.getWorker());
		if(StringUtils.isNotBlank(worker)){
			setLogWorker(worker);
		}else{
			setLogWorker(command.getWorker());
		}
		//操作信息
		setLogInfo(OrderLogConstants.INFO_MAP.get(command.getLogInfo()));
		//机票订单
		setJpOrder(order);
	}
	
	
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	

	public String getLogName() {
		return logName;
	}


	public void setLogName(String logName) {
		this.logName = logName;
	}




	public String getLogWorker() {
		return logWorker;
	}


	public void setLogWorker(String logWorker) {
		this.logWorker = logWorker;
	}


	public String getLogInfo() {
		return logInfo;
	}


	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}


	public JPOrder getJpOrder() {
		return jpOrder;
	}


	public void setJpOrder(JPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}


	
	
}
