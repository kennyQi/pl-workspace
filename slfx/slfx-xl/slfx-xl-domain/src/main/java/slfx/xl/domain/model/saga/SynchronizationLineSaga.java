package slfx.xl.domain.model.saga;

import hg.common.component.BaseEvent;
import hg.common.component.BaseSaga;

import java.util.Date;

import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;

import slfx.xl.domain.model.M;
import slfx.xl.domain.model.event.ChangeLineEvent;
import slfx.xl.domain.model.event.SynchronizationLineEvent;
import slfx.xl.pojo.command.line.SynchronizationLineCommand;

/**
 * 
 * @类功能说明：线路同步流程（分销同步直销）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年2月3日下午2:44:05
 * 
 */
@SuppressWarnings("serial")
//@Entity
//@Table(name = M.TABLE_PREFIX_SLFX_XL_SAGA + "LINE_CHANGE")
public class SynchronizationLineSaga extends BaseSaga {

	/**
	 * 需要同步的线路id
	 */
	@Column(name = "LINE_ID", length = 64)
	private String lineId;
	
	/**
	 * 同步经销商id
	 */
	@Column(name = "LINE_DEALER_ID", length = 64)
	private String LineDealerId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "MOBILE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 流程状态
	 */
	@Column(name = "MOBILE", length = 4)
	private Integer status;

	public final static Integer STATUS_CREATE_OR_UPDATE = 1; // 线路创建或修改成功
	public final static Integer STATUS_SYNCHRONIZATION = 2; // 线路同步成功

	/**
	 * 
	 * @类名：UserRegisterSaga.java
	 * @描述：向某个手机发送了短信验证码，开启一个新设置支付密码流程
	 * @@param mobile
	 * @@param validCode
	 */
	public SynchronizationLineSaga(SynchronizationLineCommand command) {
		setId(command.getToken());
		
		setLineId(command.getLineId());
		
		//setLineDealerId(command.getLineDealerId());
		
		setCreateDate(new Date());

		setStatus(SynchronizationLineSaga.STATUS_CREATE_OR_UPDATE);
	}

	@Override
	public boolean checkFinish() {
		return true;
	}

	@Override
	public void handle(BaseEvent event) {
		if (event instanceof ChangeLineEvent) {

			// 发生创建或修改线路事件
			changeLine();

		} else if (event instanceof SynchronizationLineEvent) {

			// 发生同步线路事件
			synchronizationLine();

		}
	}

	/**
	 * 线路创建或修改成功
	 */
	private void changeLine() {
		//setFinish(true);
		setSuccess(true);

		setStatus(SynchronizationLineSaga.STATUS_CREATE_OR_UPDATE);
	}
	
	/**
	 * 线路同步成功
	 */
	private void synchronizationLine() {
		setFinish(true);
		setSuccess(true);
		
		setStatus(SynchronizationLineSaga.STATUS_SYNCHRONIZATION);
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineDealerId() {
		return LineDealerId;
	}

	public void setLineDealerId(String lineDealerId) {
		LineDealerId = lineDealerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
