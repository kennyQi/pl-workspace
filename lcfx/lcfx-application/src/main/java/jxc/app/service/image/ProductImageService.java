package jxc.app.service.image;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.UploadProductImageCommand;
import hg.pojo.qo.ProductImageQO;
import hg.pojo.qo.ProductQO;
import jxc.app.dao.image.ProductImageDao;
import jxc.app.service.product.ProductService;
import jxc.domain.model.image.ProductImage;
import jxc.domain.model.product.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductImageService extends BaseServiceImpl<ProductImage,ProductImageQO,ProductImageDao>{
	
	@Autowired
	private ProductImageDao productImageDao;
	@Autowired
	private ProductService productService;
	
	@Override
	protected ProductImageDao getDao() {
		return productImageDao;
	}
	
	public ProductImage uploadProductImage(UploadProductImageCommand command){
		ProductImage image = new ProductImage();
		image.uploadProductImage(command);
		image = save(image);
		return image;
	}
	
	public ProductImage updateProductImage(UploadProductImageCommand command){
		ProductImage image = new ProductImage();
		ProductQO qo = new ProductQO();
		qo.setId(command.getProductId());
		Product product = productService.queryUnique(qo);
		if(product != null){
			image.updateProductImage(command,product);
			image = update(image);
		}
		return image;
	}
}
