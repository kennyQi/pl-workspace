package hg.demo.member.service.spi.impl.fx;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.ProductDAO;
import hg.demo.member.service.dao.hibernate.fx.ProductInUseDAO;
import hg.demo.member.service.dao.mybatis.ProductInUseMyBatisDao;
import hg.demo.member.service.domain.manager.fx.ProductInUseManager;
import hg.demo.member.service.qo.hibernate.fx.ProductInUseQO;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.productInUse.ModifyProductInUseCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.qo.ProductInUseSQO;

/**
 * 商户使用商品service实现
 * @author Caihuan
 * @date   2016年6月1日
 */
@Transactional
@Service("productInUseSPIService")
public class ProductInUseSPIService extends BaseService implements ProductInUseSPI{
	
	@Autowired
	private ProductInUseDAO productInUseDAO;
	
	@Autowired
	private ProductInUseMyBatisDao productInUseMyBatisDao;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private DistributorDAO distributorDAO;
	
	@Autowired
	private DistributorSPI distributorSPIService;

	@Override
	public List<ProductInUse> queryList(ProductInUseSQO qo) {
		return productInUseDAO.queryList(ProductInUseQO.build(qo));
	}

	@Override
	public ProductInUse changeStatus(ModifyProductInUseCommand command) {
		
		ProductInUse use = productInUseDAO.get(command.getProductInUseId());
		return productInUseDAO.update(new ProductInUseManager(use).modify(command).get());
	}

	@Override
	public List<Product> productNotUseList(String distributorId) {
		return productInUseMyBatisDao.productNotUseList(distributorId);
	}

	@Override
	public ProductInUse addProductInUse(CreateProductInUseCommand command) throws Exception{
		ProductInUse productInUse = new ProductInUse();
		Product prod = productDAO.get(command.getProdId());
		if(prod==null)
		{
			throw new Exception("商品不存在！");
		}
		Distributor distributor = distributorDAO.get(command.getDistributorId());
		if(distributor==null)
		{
			throw new Exception("商户不存在");
		}
		//修改商户使用商品数量
		ProductInUse productUse = productInUseDAO.save(new ProductInUseManager(productInUse).create(command,distributor,prod).get());
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setDistributorId(command.getDistributorId());
		List<ProductInUse> list = queryList(sqo);
		ModifyDistributorCommand mCommand = new ModifyDistributorCommand();
		mCommand.setId(command.getDistributorId());
		mCommand.setProdNum(list.size());
		distributorSPIService.modifyDistributor(mCommand);
		return productUse;
	}
	
	@Override
	public boolean checkInUse(String distributorId, String productId){
		ProductInUseSQO qo=new ProductInUseSQO();
		qo.setDistributorId(distributorId);
		boolean check=false;
		for(ProductInUse use: this.queryList(qo)){
			if(use.getProduct().getId().equals(productId) && use.getStatus()==ProductInUse.STATUS_OF_IN_USE){
				check=true;
				break;
			}
		}
		return check;
	}
	
}
