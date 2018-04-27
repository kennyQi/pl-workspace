package hg.pojo.command;


/**
 * 创建商品分类
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class CreateCategoryCommand extends JxcCommand {

	
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
	
}
