package hg.pojo.command;

import java.util.List;

/**
 * 创建规格
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class CreateSpecificationCommand extends JxcCommand {

	/**
	 * 分类id
	 */
	private String categoryId;
	
	/**
	 * 规格名称
	 */
	private String specificationName;
	
	/**
	 * 规格值
	 */
	private List<String> specValueList;


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}

	public List<String> getSpecValueList() {
		return specValueList;
	}

	public void setSpecValueList(List<String> specValueList) {
		this.specValueList = specValueList;
	}

}
