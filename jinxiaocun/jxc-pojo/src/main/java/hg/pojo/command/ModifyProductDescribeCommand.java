package hg.pojo.command;


/**
 * 编辑商品描述
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyProductDescribeCommand extends JxcCommand {

	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 商品描述
	 */
	private String productDescribe;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDescribe() {
		return productDescribe;
	}
	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}
	
	
}
