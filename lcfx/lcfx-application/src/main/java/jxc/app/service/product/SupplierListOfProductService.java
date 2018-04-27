package jxc.app.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.PaymentMethodQo;
import hg.pojo.qo.ProdSuppCorrelationQo;
import jxc.app.dao.system.ProdSuppCorrelationDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductSupplierCorrelation;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.system.PaymentMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierListOfProductService extends BaseServiceImpl<ProductSupplierCorrelation, ProdSuppCorrelationQo, ProdSuppCorrelationDao> {
	@Autowired
	private ProdSuppCorrelationDao prodSuppCorrelationDao;

	@Override
	protected ProdSuppCorrelationDao getDao() {
		return prodSuppCorrelationDao;
	}


	public void resetSupplierList(String productId, List<String> supplisrIdList) {
		List<ProductSupplierCorrelation> pscList = getListByProductId(productId);
		for (ProductSupplierCorrelation productSupplierCorrelation : pscList) {
			deleteById(productSupplierCorrelation.getId());
		}
		if (supplisrIdList == null) {
			return;
		}
		for (String id : supplisrIdList) {
			ProductSupplierCorrelation e = new ProductSupplierCorrelation();
			e.setId(UUIDGenerator.getUUID());
			Product p = new Product();
			p.setId(productId);
			e.setProduct(p);
			Supplier s = new Supplier();
			s.setId(id);
			e.setSupplier(s);
			save(e);
		}
	}

	public List<String> querySupplierIdList(String productId) {
		List<ProductSupplierCorrelation> pscList = getListByProductId(productId);
		ArrayList<String> idList = new ArrayList<String>();
		for (ProductSupplierCorrelation productSupplierCorrelation : pscList) {
			idList.add(productSupplierCorrelation.getSupplier().getId());
		}
		return idList;
	}

	public List<Supplier> querySupplierList(String productId) {
		List<ProductSupplierCorrelation> pscList = getListByProductId(productId);
		ArrayList<Supplier> suppList = new ArrayList<Supplier>();
		for (ProductSupplierCorrelation productSupplierCorrelation : pscList) {
			suppList.add(productSupplierCorrelation.getSupplier());
		}
		return suppList;
	}

	private List<ProductSupplierCorrelation> getListByProductId(String productId) {
		ProdSuppCorrelationQo qo = new ProdSuppCorrelationQo();
		qo.setProductId(productId);
		List<ProductSupplierCorrelation> pscList = queryList(qo);
		return pscList;
	}
}
