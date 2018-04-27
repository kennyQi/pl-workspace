package hg.framework.common.base.log;

import hg.framework.common.model.LimitQuery;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.Date;

/**
 * 汇购日志查询对象
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class HgLogQO implements Serializable {

	/**
	 * 日志级别（TRACE的值最小）
	 *
	 * @see Level
	 */
	private Integer levelIntMin;

	/**
	 * 工程名称
	 */
	private String projectName;

	/**
	 * 工程版本
	 */
	private String projectVersion;

	/**
	 * 日志记录器名称（模糊匹配）
	 */
	private String loggerName;

	/**
	 * 日志内容（模糊匹配）
	 */
	private String content;

	/**
	 * 记录时间 - 开始
	 */
	private Date recordDateBegin;

	/**
	 * 记录时间 - 截止
	 */
	private Date recordDateEnd;

	/**
	 * 记录时间排序（大于0升序，小于0降序）
	 */
	private Integer recordDateOrder;

	/**
	 * 异常堆栈信息（模糊匹配）
	 */
	private String errorStackInfo;

	/**
	 * 分页限制条件
	 */
	private LimitQuery limit;

	public Integer getLevelIntMin() {
		return levelIntMin;
	}

	public void setLevelIntMin(Integer levelIntMin) {
		this.levelIntMin = levelIntMin;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRecordDateBegin() {
		return recordDateBegin;
	}

	public void setRecordDateBegin(Date recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}

	public Date getRecordDateEnd() {
		return recordDateEnd;
	}

	public void setRecordDateEnd(Date recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
	}

	public Integer getRecordDateOrder() {
		return recordDateOrder;
	}

	public void setRecordDateOrder(Integer recordDateOrder) {
		this.recordDateOrder = recordDateOrder;
	}

	public String getErrorStackInfo() {
		return errorStackInfo;
	}

	public void setErrorStackInfo(String errorStackInfo) {
		this.errorStackInfo = errorStackInfo;
	}

	public LimitQuery getLimit() {
		return limit;
	}

	public void setLimit(LimitQuery limit) {
		this.limit = limit;
	}
}
