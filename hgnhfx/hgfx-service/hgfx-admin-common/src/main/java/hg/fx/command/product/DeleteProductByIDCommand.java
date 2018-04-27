package hg.fx.command.product;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class DeleteProductByIDCommand extends BaseSPICommand {

	/**
	 * 商品ID
	 */
	private String productID;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

}
