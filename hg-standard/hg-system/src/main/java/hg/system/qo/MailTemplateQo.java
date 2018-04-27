package hg.system.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：Mail邮件模板Qo
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月28日 上午9:47:08
 */
public class MailTemplateQo extends BaseQo {
	private static final long serialVersionUID = 1L;
	
	/**模板名称*/
	private String name;
	/**内容*/
	private String content;
	/**是否默认*/
	private Boolean defAble;
	/**创建时间*/
	private String timeBefore;
	private String timeAfter;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean isDefAble() {
		return defAble;
	}
	public void setDefAble(Boolean defAble) {
		this.defAble = defAble;
	}
	public String getTimeBefore() {
		return timeBefore;
	}
	public void setTimeBefore(String timeBefore) {
		this.timeBefore = timeBefore;
	}
	public String getTimeAfter() {
		return timeAfter;
	}
	public void setTimeAfter(String timeAfter) {
		this.timeAfter = timeAfter;
	}
}