package hg.system.model.seo;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.seo.CreateMetaTagCommand;
import hg.system.command.seo.ModifyMetaTagCommand;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：HTML头部中的META标签
 * @类修改者：
 * @修改日期：2015-1-23下午4:30:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-23下午4:30:13
 */
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "META_TAG")
public class MetaTag extends BaseModel {
	private static final long serialVersionUID = 1L;
	// <title>title</title>
	// <meta name="keywords" content="keywords"/>
	// <meta name="description" content="description"/>
	
	/**
	 * 请求地址
	 */
	@Column(name = "URI", length = 256)
	private String uri;

	/**
	 * 标题
	 */
	@Column(name = "TITLE", length = 256)
	private String title;

	/**
	 * 关键词
	 */
	@Column(name = "KEYWORDS", length = 256)
	private String keywords;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION", length = 1024)
	private String description;
	
	/**
	 * @方法功能说明：创建META标签
	 * @修改者名字：zhurz
	 * @修改时间：2015-1-23下午4:41:24
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void create(CreateMetaTagCommand command) {
		setId(UUIDGenerator.getUUID());
		setUri(command.getUri());
		setTitle(command.getTitle());
		setKeywords(command.getKeywords());
		setDescription(command.getDescription());
	}
	
	/**
	 * @方法功能说明：修改META标签
	 * @修改者名字：zhurz
	 * @修改时间：2015-1-23下午4:41:24
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modify(ModifyMetaTagCommand command) {
		setUri(command.getUri());
		setTitle(command.getTitle());
		setKeywords(command.getKeywords());
		setDescription(command.getDescription());
	}

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
