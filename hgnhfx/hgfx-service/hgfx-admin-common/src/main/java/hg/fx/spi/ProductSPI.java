package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.product.CreateProductCommand;
import hg.fx.command.product.DeleteProductByIDCommand;
import hg.fx.command.product.ModifyProductCommand;
import hg.fx.domain.Product;
import hg.fx.spi.qo.ProductSQO;

public interface ProductSPI extends BaseServiceProviderInterface{

	/**
	 * 新建商品
	 */
	Product create(CreateProductCommand command);
	
	/**
	 * 修改商品
	 */
	Product modify(ModifyProductCommand command);
	
	/**
	 * 删除商品
	 */
	boolean deleteByID(DeleteProductByIDCommand command);
	
	/**
	 * 查询商品
	 */
	Product queryProductByID(ProductSQO sqo);
	
	/**
	 * 列表查询商品
	 */
	List<Product> queryProductList(ProductSQO sqo);
	
	/**
	 * 分页查询商品
	 */
	Pagination<Product> queryProductPagination(ProductSQO sqo);

	public abstract Product queryUnique(ProductSQO sqo);
	
}
