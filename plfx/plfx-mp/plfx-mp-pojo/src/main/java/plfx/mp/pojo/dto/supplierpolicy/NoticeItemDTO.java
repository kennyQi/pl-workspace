package plfx.mp.pojo.dto.supplierpolicy;

import java.io.Serializable;

/**
 * 须知明细
 * 
 * @author Administrator
 */
public class NoticeItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 明细名称
	 */
	private String name;

	/**
	 * 明细内容
	 */
	private String content;

	/**
	 * 所属须知类型
	 */
	private String typeId;

	/**
	 * 序号
	 */
	private Integer sort;

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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}