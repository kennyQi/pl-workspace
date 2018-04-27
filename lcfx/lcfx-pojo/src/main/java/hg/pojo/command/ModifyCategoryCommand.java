package hg.pojo.command;


/**
 * 修改商品分类
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyCategoryCommand extends JxcCommand {

	
	/**
	 * 分类id
	 */
	private String categoryId;
	
	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 父类id
	 */
	private String parentCategoryId;
	
	/**
	 * 是否开启
	 */
	private Boolean using;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
