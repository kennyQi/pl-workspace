package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.productInUse.ModifyProductInUseCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;

public class ProductInUseManager extends BaseDomainManager<ProductInUse> {

	public ProductInUseManager(ProductInUse entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public ProductInUseManager modify(ModifyProductInUseCommand command)
	{
		entity.setStatus(command.getStatus());
		return this;
	}

	public ProductInUseManager create(CreateProductInUseCommand command,Distributor distributor,Product product) {
		entity.setId(command.getId());
		entity.setAgreementPath(command.getAgreementPath());
		entity.setDistributor(distributor);
		entity.setPhone(command.getPhone());
		entity.setProduct(product);
		entity.setQq(command.getQq());
		entity.setStatus(command.getStatus());
		entity.setTrialDate(command.getTrialDate());
		entity.setUseDate(command.getUseDate());
		return this;
	}
}
