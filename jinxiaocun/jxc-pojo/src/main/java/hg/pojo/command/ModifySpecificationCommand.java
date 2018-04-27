package hg.pojo.command;

import java.util.List;

/**
 * 修改规格
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifySpecificationCommand extends JxcCommand {

	/**
	 * 规格id
	 */
	private String specificationId;
	
	/**
	 * 规格Code
	 */
	private String specificationCode;
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

	/**
	 * 是否开启
	 */
	private Boolean using;
	
	public String getSpecificationCode() {
		return specificationCode;
	}

	public void setSpecificationCode(String specificationCode) {
		this.specificationCode = specificationCode;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}


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
