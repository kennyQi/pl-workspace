package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.productInUse.ModifyProductInUseCommand;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.qo.ProductInUseSQO;

import java.util.List;

/**
 * 商户使用商品SPI
 * @author Caihuan
 * @date   2016年6月1日
 */
public interface ProductInUseSPI extends BaseServiceProviderInterface{

	/**
	 * 条件查询商户使用的商品记录
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public List<ProductInUse> queryList(ProductInUseSQO qo);
	
	/**
	 * 修改商户使用商品的状态 0--试用中  1--申请中  2--使用中  3--已拒绝  4--停用中
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public ProductInUse changeStatus(ModifyProductInUseCommand command);
	
	/**
	 * 未使用商品列表
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public List<Product> productNotUseList(String distributorId);
	
	/**
	 * 添加商户使用商品
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public ProductInUse addProductInUse(CreateProductInUseCommand command) throws Exception;

	public abstract boolean checkInUse(String distributorId, String productId);
}
