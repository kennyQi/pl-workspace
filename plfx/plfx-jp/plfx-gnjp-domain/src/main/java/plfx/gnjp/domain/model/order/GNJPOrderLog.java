package plfx.gnjp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.domain.model.J;
/****
 * 
 * @类功能说明：机票日志实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年8月5日下午2:29:37
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX_GN + "ORDER_LOG")
public class GNJPOrderLog extends BaseModel{
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
	 * 操作人类型
	 */
	@Column(name = "LOG_WORKER_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer logWorkerType;
	
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
	private  GNJPOrder jpOrder;

	/****
	 * 
	 * @方法功能说明：创建机票日志
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年8月5日下午2:29:52
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param order
	 * @参数：@param logWorker
	 * @return:void
	 * @throws
	 */
	public void create(CreateJPOrderLogCommand command,GNJPOrder jpOrder,String logWorker){

		setId(UUIDGenerator.getUUID());
		setLogDate(new Date());
		setLogWorkerType(command.getLogWorkerType());
		setLogName(command.getLogName());
		setLogWorker(logWorker);
		setLogInfo(command.getLogInfo());
		setJpOrder(jpOrder);
		
		//		setId(UUIDGenerator.getUUID());
//		setLogDate(new Date());
//		//操作名称
//		setLogName(OrderLogConstants.NAME_MAP.get(command.getLogName()));
//		//操作人
//		String worker = OrderLogConstants.WORKER_MAP.get(command.getWorker());
//		if(StringUtils.isNotBlank(worker)){
//			setLogWorker(worker);
//		}else{
//			setLogWorker(command.getWorker());
//		}
//		//操作信息
//		setLogInfo(OrderLogConstants.INFO_MAP.get(command.getLogInfo()));
//		//机票订单
//		setJpOrder(order);
		
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


	public GNJPOrder getJpOrder() {
		return jpOrder;
	}


	public void setJpOrder(GNJPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}


	public Integer getLogWorkerType() {
		return logWorkerType;
	}


	public void setLogWorkerType(Integer logWorkerType) {
		this.logWorkerType = logWorkerType;
	}	
	
}
