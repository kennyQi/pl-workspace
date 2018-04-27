package jxc.app.service.product;


import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import jxc.app.dao.product.ProductSnapshotDao;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSnapshotService extends BaseServiceImpl<ProductSnapshot, BaseQo, ProductSnapshotDao> {

	@Autowired
	private ProductSnapshotDao productSnapshotDao;

	@Override
	protected ProductSnapshotDao getDao() {
		return productSnapshotDao;
	}

	public void createProductSnap(Product p) {
		
		ProductSnapshot productSnapshot =new ProductSnapshot();
		productSnapshot.create(p);
		save(productSnapshot);
	}

}
