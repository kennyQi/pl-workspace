package hg.pojo.command;

import java.util.List;

/**
 * 删除品牌
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteBrandCommand extends JxcCommand {

	/**
	 * 品牌id列表
	 */
	private List<String> brandIdList;

	public List<String> getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(List<String> brandIdList) {
		this.brandIdList = brandIdList;
	}


}
