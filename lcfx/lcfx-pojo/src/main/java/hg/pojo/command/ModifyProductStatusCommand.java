package hg.pojo.command;

import java.util.List;


/**
 * 修改商品状态
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyProductStatusCommand extends JxcCommand {

	/**
	 * 商品id
	 */
	private List<String> productListId;
	

	/**
	 * 是否启用
	 */
	private Boolean using;

	public List<String> getProductListId() {
		return productListId;
	}

	public void setProductListId(List<String> productListId) {
		this.productListId = productListId;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}


	
	
}
