package hg.pojo.command;

import hg.pojo.dto.product.ProductDTO;

import java.util.List;

/**
 * 批量导入商品command
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ImportBatchProductCommand extends JxcCommand{

	private List<ProductDTO> productList;

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}
	
	
}
