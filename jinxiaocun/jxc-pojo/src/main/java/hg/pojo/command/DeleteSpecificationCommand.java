package hg.pojo.command;

import java.util.List;

/**
 * 删除规格
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteSpecificationCommand extends JxcCommand {

	/**
	 * 规格id列表
	 */
	private List<String> specificationListId;

	public List<String> getSpecificationListId() {
		return specificationListId;
	}

	public void setSpecificationListId(List<String> specificationListId) {
		this.specificationListId = specificationListId;
	}
	
}
