package hg.system.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：META标签查询对象
 * @类修改者：
 * @修改日期：2015-1-23下午4:46:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-23下午4:46:51
 */
@SuppressWarnings("serial")
public class MetaTagQo extends BaseQo {

	/**
	 * 请求地址(模糊匹配)
	 */
	private String uri;

	/**
	 * 标题(模糊匹配)
	 */
	private String title;

	/**
	 * 关键词(模糊匹配)
	 */
	private String keywords;

	/**
	 * 描述(模糊匹配)
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
