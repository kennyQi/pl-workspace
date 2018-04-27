package hg.pojo.qo;


@SuppressWarnings("serial")
public class CategoryQO extends JxcBaseQo{
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 父类id
	 */
	private String parentCategoryId;
	/**
	 * 是否启用
	 */
	private Boolean using;

	
	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
