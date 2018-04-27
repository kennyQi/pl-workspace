package hg.log.po.normal;

import org.springframework.data.mongodb.core.mapping.Document;

import hg.log.po.base.BaseLog;

/**
 * 
 * @类功能说明：记录汇购各系统常规日志
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月31日下午2:40:33
 * 
 */
@Document
public class HgLog extends BaseLog {

	private String developerName;

	private String className;

	private String errorInfoStack;

	private String content;

	private Integer level;

	public final static Integer HGLOG_LEVEL_DEBUG = 1;
	public final static Integer HGLOG_LEVEL_INFO = 2;
	public final static Integer HGLOG_LEVEL_WARN = 3;
	public final static Integer HGLOG_LEVEL_ERROR = 4;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getErrorInfoStack() {
		return errorInfoStack;
	}

	public void setErrorInfoStack(String errorInfoStack) {
		this.errorInfoStack = errorInfoStack;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
