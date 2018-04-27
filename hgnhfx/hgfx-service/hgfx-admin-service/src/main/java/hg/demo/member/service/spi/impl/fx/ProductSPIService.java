package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.ChannelDAO;
import hg.demo.member.service.dao.hibernate.fx.NewMileOrderDao;
import hg.demo.member.service.dao.hibernate.fx.ProductDAO;
import hg.demo.member.service.domain.manager.fx.ProductManager;
import hg.demo.member.service.qo.hibernate.fx.NewMileOrderQO;
import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.product.CreateProductCommand;
import hg.fx.command.product.DeleteProductByIDCommand;
import hg.fx.command.product.ModifyProductCommand;
import hg.fx.domain.Channel;
import hg.fx.domain.MileOrder;
import hg.fx.domain.Product;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductSQO;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("productSPIService")
public class ProductSPIService extends BaseService implements ProductSPI{

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ChannelDAO channelDAO;
	
	@Autowired
	private NewMileOrderDao newMileOrderDao;
	/**
	 * 新建商品
	 */
	@Override
	public Product create(CreateProductCommand command) {
		Product product = new Product();
		Channel channel =null;
		if(StringUtils.isNotBlank(command.getChannelID())){
			channel = channelDAO.get(command.getChannelID());
		}
		return productDAO.save(new ProductManager(product).create(command, channel).get());
	}

	/**
	 * 修改商品
	 */
	@Override
	public Product modify(ModifyProductCommand command) {
		Product product = new Product();
		if(StringUtils.isNotBlank(command.getProductID())&&productDAO.get(command.getProductID())!=null){
			product = productDAO.get(command.getProductID());
			Channel channel =null;
			if(StringUtils.isNotBlank(command.getChannelID())){
				channel = channelDAO.get(command.getChannelID());
			}
			return productDAO.update(new ProductManager(product).modify(command, channel).get());
		}else{
			return null;
		}
	}

	/**
	 * 删除商品
	 */
	@Override
	public boolean deleteByID(DeleteProductByIDCommand command) {
		Product product = new Product();
		if(StringUtils.isNotBlank(command.getProductID())&&productDAO.get(command.getProductID())!=null){
			MileOrderSQO sqo = new MileOrderSQO();
			sqo.setProductId(command.getProductID());
			if(newMileOrderDao.queryCount(NewMileOrderQO.build(sqo))!=0){
				return false;
			}
			product = productDAO.get(command.getProductID());
			productDAO.delete(product);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 查询商品
	 */
	@Override
	public Product queryProductByID(ProductSQO sqo) {
		Product product = queryUnique(sqo);
		if(product!=null){
			Hibernate.initialize(product.getChannel());
		}
		return product;
	}

	/**
	 * @param sqo
	 * @return
	 */
	public Product queryUnique(ProductSQO sqo) {
		Product product = productDAO.queryUnique(ProductQO.build(sqo));
		return product;
	}

	/**
	 * 列表查询商品
	 */
	@Override
	public List<Product> queryProductList(ProductSQO sqo) {
		List<Product> products = productDAO.queryList(ProductQO.build(sqo));
		for (Product product : products) {
			Hibernate.initialize(product.getChannel());
		}
		return products;
	}

	/**
	 * 分页查询商品
	 */
	@Override
	public Pagination<Product> queryProductPagination(ProductSQO sqo) {
		Pagination<Product> pagination = productDAO.queryPagination(ProductQO.build(sqo));
		for (Product product : pagination.getList()) {
			Hibernate.initialize(product.getChannel());
		}
		return pagination;
	}

}
