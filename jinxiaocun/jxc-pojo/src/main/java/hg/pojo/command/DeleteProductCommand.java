package hg.pojo.command;

import java.util.List;

/**
 * 删除商品
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteProductCommand extends JxcCommand {

	/**
	 * 商品id列表
	 */
	private List<String> productListId;

	public List<String> getProductListId() {
		return productListId;
	}

	public void setProductListId(List<String> productListId) {
		this.productListId = productListId;
	}

	
}
