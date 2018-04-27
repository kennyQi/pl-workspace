package pl.cms.domain.entity.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pl.cms.domain.entity.M;
import hg.common.component.BaseModel;

/**
 * 资讯频道
 * @author yuxx
 *
 */
@Entity
@Table(name = M.TABLE_PREFIX + "ARTICLE_CHANNEL")
@SuppressWarnings("serial")
public class ArticleChannel extends BaseModel {

	/**
	 * 频道名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
