package plfx.gjjp.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * @类功能说明：国际机票订单操作日志
 * @类修改者：
 * @修改日期：2015-7-20下午2:28:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午2:28:37
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "gjjpOrderLogDao")
public class GJJPOrderLogQo extends BaseQo {

	/**
	 * 日志操作时间
	 */
	@QOAttr(name = "logDate", type = QOAttrType.GE)
	private Date logDateBegin;
	@QOAttr(name = "logDate", type = QOAttrType.LE)
	private Date logDateEnd;

	/**
	 * 操作名称
	 */
	@QOAttr(name = "logDate", ifTrueUseLike = "logNameLike")
	private String logName;
	private Boolean logNameLike;

	/**
	 * 操作人
	 */
	@QOAttr(name = "logWorker", ifTrueUseLike = "logWorkerLike")
	private String logWorker;
	private Boolean logWorkerLike;

	/**
	 * 操作信息
	 */
	@QOAttr(name = "logInfo", type = QOAttrType.LIKE_ANYWHERE)
	private String logInfo;

	/**
	 * 机票订单基本信息
	 */
	@QOAttr(name = "jpOrder", type = QOAttrType.LEFT_JOIN)
	private GJJPOrderQo jpOrderQo;

	/**
	 * 操作时间排序
	 */
	@QOAttr(name = "logDate", type = QOAttrType.ORDER)
	private Integer logDateSort;

	public Date getLogDateBegin() {
		return logDateBegin;
	}

	public void setLogDateBegin(Date logDateBegin) {
		this.logDateBegin = logDateBegin;
	}

	public Date getLogDateEnd() {
		return logDateEnd;
	}

	public void setLogDateEnd(Date logDateEnd) {
		this.logDateEnd = logDateEnd;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public Boolean getLogNameLike() {
		return logNameLike;
	}

	public void setLogNameLike(Boolean logNameLike) {
		this.logNameLike = logNameLike;
	}

	public String getLogWorker() {
		return logWorker;
	}

	public void setLogWorker(String logWorker) {
		this.logWorker = logWorker;
	}

	public Boolean getLogWorkerLike() {
		return logWorkerLike;
	}

	public void setLogWorkerLike(Boolean logWorkerLike) {
		this.logWorkerLike = logWorkerLike;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public GJJPOrderQo getJpOrderQo() {
		return jpOrderQo;
	}

	public void setJpOrderQo(GJJPOrderQo jpOrderQo) {
		this.jpOrderQo = jpOrderQo;
	}

	public Integer getLogDateSort() {
		return logDateSort;
	}

	public void setLogDateSort(Integer logDateSort) {
		this.logDateSort = logDateSort;
	}

}
