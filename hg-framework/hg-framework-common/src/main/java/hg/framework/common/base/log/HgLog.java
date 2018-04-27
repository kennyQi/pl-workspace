package hg.framework.common.base.log;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.Document;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 汇购日志
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class HgLog implements Serializable {

	/**
	 * 日志级别值
	 *
	 * @see Level
	 */
	private Integer levelInt;

	/**
	 * 日志级别字符串
	 *
	 * @see Level
	 */
	private String levelStr;

	/**
	 * 工程名称
	 */
	private String projectName;

	/**
	 * 工程版本
	 */
	private String projectVersion;

	/**
	 * 日志记录器名称
	 */
	private String loggerName;

	/**
	 * 日志内容
	 */
	private String content;

	/**
	 * 记录时间
	 */
	private Date recordDate;

	/**
	 * 异常堆栈信息
	 */
	private String errorStackInfo;

	public HgLog() {
	}

	public HgLog(Level level, String projectName, String projectVersion, String loggerName, String content, Date recordDate, String errorStackInfo) {
		this(level.toInt(), level.toString(), projectName, projectVersion, loggerName, content, recordDate, errorStackInfo);
	}

	public HgLog(Integer levelInt, String levelStr, String projectName, String projectVersion, String loggerName, String content, Date recordDate, String errorStackInfo) {
		this.levelInt = levelInt;
		this.levelStr = levelStr;
		this.projectName = projectName;
		this.projectVersion = projectVersion;
		this.loggerName = loggerName;
		this.content = content;
		this.recordDate = recordDate;
		this.errorStackInfo = errorStackInfo;
	}

	public Document buildMongoDocument() {
		try {
			Map<String, Object> describe = PropertyUtils.describe(this);
			describe.remove("class");
			return new Document(describe);
		} catch (Exception ignored) {
		}
		return null;
	}

	public Integer getLevelInt() {
		return levelInt;
	}

	public void setLevelInt(Integer levelInt) {
		this.levelInt = levelInt;
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getErrorStackInfo() {
		return errorStackInfo;
	}

	public void setErrorStackInfo(String errorStackInfo) {
		this.errorStackInfo = errorStackInfo;
	}
}
