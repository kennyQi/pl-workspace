package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.product.CreateProductCommand;
import hg.fx.command.product.ModifyProductCommand;
import hg.fx.domain.Channel;
import hg.fx.domain.Product;

import java.util.Date;

/**
 * @author cangs
 */
public class ProductManager extends BaseDomainManager<Product>{

	public ProductManager(Product entity) {
		super(entity);
	}

	/**
	 * 新建商品
	 */
	public ProductManager create(CreateProductCommand command,Channel channel){
		entity.setId(UUIDGenerator.getUUID());
		entity.setProdCode(command.getProdCode());
		entity.setProdName(command.getProdName());
		entity.setType(command.getType());
		entity.setDocumentPath(command.getDocumentPath());
		entity.setAgreementPath(command.getAgreementPath());
		entity.setCreateDate(new Date());
		entity.setChannel(channel);
		return this;
	}

	/**
	 * 修改商品
	 */
	public ProductManager modify(ModifyProductCommand command,Channel channel){
		entity.setProdCode(command.getProdCode());
		entity.setProdName(command.getProdName());
		entity.setType(command.getType());
		entity.setDocumentPath(command.getDocumentPath());
		entity.setAgreementPath(command.getAgreementPath());
		entity.setChannel(channel);
		return this;
	}
	
}
