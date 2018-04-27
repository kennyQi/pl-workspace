package hg.pojo.command;

import java.util.List;

/**
 * 删除类别
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteCategoryCommand extends JxcCommand {

	/**
	 * 分类id列表
	 */
	private List<String> categoryIdList;

	public List<String> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<String> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

}
