package hg.system.command.seo;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建META标签
 * @类修改者：
 * @修改日期：2015-1-23下午4:37:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-23下午4:37:22
 */
@SuppressWarnings("serial")
public class CreateMetaTagCommand extends BaseCommand {

	/**
	 * 请求地址
	 */
	private String uri;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 关键词
	 */
	private String keywords;

	/**
	 * 描述
	 */
	private String description;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
