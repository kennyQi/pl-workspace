package plfx.gjjp.domain.model;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：国际机票订单操作日志
 * @类修改者：
 * @修改日期：2015-7-13下午3:13:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-13下午3:13:01
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX_GJ + "JP_ORDER_LOG")
public class GJJPOrderLog extends BaseModel {

	/**
	 * 日志操作时间
	 */
	@Column(name = "LOG_DATE", columnDefinition = J.DATE_COLUM)
	private Date logDate;

	/**
	 * 操作名称
	 */
	@Column(name = "LOG_NAME", length = 64)
	private String logName;

	/**
	 * 操作人
	 */
	@Column(name = "LOG_WORKER", length = 64)
	private String logWorker;

	/**
	 * 操作人类型
	 */
	@Column(name = "LOG_WORKER_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer logWorkerType;

	/**
	 * 操作信息
	 */
	@Column(name = "LOG_INFO", length = 1024)
	private String logInfo;

	/**
	 * 机票订单基本信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JP_ORDER_ID")
	private GJJPOrder jpOrder;

	/**
	 * @方法功能说明：创建国际机票订单日志
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-22上午10:24:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param jpOrder
	 * @return:void
	 * @throws
	 */
	public void create(CreateGJJPOrderLogCommand command, GJJPOrder jpOrder, String logWorker) {
		setId(UUIDGenerator.getUUID());
		setLogDate(new Date());
		setLogWorkerType(command.getLogWorkerType());
		setLogName(command.getLogName());
		setLogWorker(logWorker);
		setLogInfo(command.getLogInfo());
		setJpOrder(jpOrder);
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

	public Integer getLogWorkerType() {
		return logWorkerType;
	}

	public void setLogWorkerType(Integer logWorkerType) {
		this.logWorkerType = logWorkerType;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public GJJPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(GJJPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

}
