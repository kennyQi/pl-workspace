package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.pojo.command.ModifyProductSkuCommand;
import hg.pojo.dto.product.SkuProductDTO;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.PurchaseOrderDetailQo;
import hg.pojo.qo.SkuProductQO;
import hg.pojo.qo.SpecValueQO;

import java.util.ArrayList;
import java.util.List;

import jxc.app.dao.product.SkuProductDao;
import jxc.app.service.warehousing.PurchaseOrderDetailService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;
import jxc.domain.model.purchaseorder.PurchaseOrder;
import jxc.domain.model.purchaseorder.PurchaseOrderDetail;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkuProductService extends BaseServiceImpl<SkuProduct, SkuProductQO, SkuProductDao> {
	@Autowired
	private SkuProductDao skuProductDao;

	@Autowired
	private SpecDetailService specDetailService;

	@Autowired
	private ProductService productService;

	@Autowired
	private JxcLogger logger;
	@Autowired
	private PurchaseOrderDetailService purchaseOrderDetailService;
	@Autowired
	private SpecValueService specValueService;

	@Override
	protected SkuProductDao getDao() {
		return skuProductDao;
	}

	public String[] queryListWithSpecDetail(String productId) {
		String[] ret = new String[4];
		SkuProductQO qo = new SkuProductQO();
		qo.setProductId(productId);
		List<SkuProduct> list = queryList(qo);
		if (list.size() == 0) {
			ret[0] = "";
			return ret;
		}
		int longSpecDetailSize = 0;
		for (SkuProduct skuProduct : list) {
			int size = skuProduct.getSpecDetails().size();
			if (size > longSpecDetailSize) {
				longSpecDetailSize = size;
			}
		}
		
		ArrayList<String> specValStrList = new ArrayList<String>();
		for (int i = 0; i < longSpecDetailSize; i++) {
			specValStrList.add("");
		}
		String specStr = "";
		String specIdStr = "";
		String specValStr = "";
		String specStock = "";
		for (SkuProduct skuProduct : list) {
			List<SpecDetail> specDetails = skuProduct.getSpecDetails();
			if (longSpecDetailSize == specDetails.size()) {
				if (specStr.equals("")) {
					for (SpecDetail specDetail : specDetails) {
						specStr = specStr + specDetail.getSpecification().getSpecName()+";";
						specIdStr = specIdStr + specDetail.getSpecification().getId()+";";
					}
					specStr = specStr.substring(0, specStr.length()-1);
					specIdStr = specIdStr.substring(0, specIdStr.length()-1);
				}
				for (int i = 0; i < specDetails.size(); i++) {
					SpecDetail specDetail = specDetails.get(i);
					String specString = specValStrList.get(i);
					String s = specDetail.getSpecValue().getSpecValue()+";";
					if (!specString.contains(s)) {
						specValStrList.set(i, specValStrList.get(i)+specDetail.getSpecValue().getSpecValue()+";");
						
					}
				}
				specStock = specStock +"1-";
//				specValStr = specValStr.substring(0, specValStr.length()-1)+"-";
				
			}
		}
		specStock = specStock.substring(0, specStock.length()-1);
		for (String specVal : specValStrList) {
			specValStr = specValStr +specVal.substring(0, specVal.length()-1)+"-";
		}
		specValStr = specValStr.substring(0, specValStr.length()-1);
		ret[0] = specStr;
		ret[1] = specValStr;
		ret[2] = specIdStr;
		ret[3] = specStock;
		return ret;
	}

	public void querySkuDto(StringBuffer sb, String skuId, SkuProductDTO dto) {
		SkuProductQO qo = new SkuProductQO();
		qo.setId(skuId);
		SkuProduct skuProd = queryUnique(qo);

		List<SpecDetail> specDetails = skuProd.getSpecDetails();
		for (SpecDetail specDetail : specDetails) {
			sb.append(specDetail.getSpecValue().getSpecValue());
			sb.append(" ");
		}
		dto.setSkuCode(skuProd.getId());
		dto.setProductCode(skuProd.getProduct().getProductCode());
		dto.setProductName(skuProd.getProduct().getProductName());
		dto.setSpecificationsName(sb.toString());
		sb.setLength(0);
		dto.setUnit(skuProd.getProduct().getUnit().getUnitName());

	}

	public List<SkuProduct> createSkuProduct(ModifyProductSkuCommand command) {
		List<SkuProduct> skus = new ArrayList<SkuProduct>();
		Product product = productService.get(command.getProductId());
		if (product != null) {
			List<String> specValues = command.getSpecValueIdList();
			String[] specification = command.getSpecificationIdList().split(",");
			for (int i = 0; i < specValues.size(); i++) {
				String values = specValues.get(i);
				SkuProduct skuProduct = new SkuProduct();
				skuProduct.createSkuProduct(command, values.replaceAll(",", ""), product.getProductCode());
				skuProduct = save(skuProduct);
				logger.debug(this.getClass(), "czh", "新增商品sku " + skuProduct.getId(), command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
				String[] value = values.split(",");
				for (int j = 0; j < value.length; j++) {
					String[] detail = new String[] { skuProduct.getId(), specification[j], value[j] };
					specDetailService.createSpecDetail(detail);
				}
				skus.add(skuProduct);
			}
		}
		return skus;
	}

	public List<SkuProduct> updateSkuProduct(ModifyProductSkuCommand command) {
		List<SkuProduct> skus = new ArrayList<SkuProduct>();

		Product product = productService.get(command.getProductId());

		List<String> skuList = command.getSkuList();
		List<String> specValues = command.getSpecValueIdList();
		List<String> deleteSkus = new ArrayList<String>();
		if (skuList != null && skuList.size() > 0) {
			for (String sku : skuList) {
				String deleteValue = sku.substring(7);
				boolean flag = checkSkuProduct(deleteValue, specValues);
				if (flag) {
					SkuProduct skuProduct = new SkuProduct();
					skuProduct.setId(sku);
					skuProduct.setProduct(product);
					StringBuffer value = new StringBuffer();
					for (int i = 0; i < deleteValue.length() / 4; i++) {
						value.append(deleteValue.substring(i * 4, (i + 1) * 4)).append(",");
					}
					specValues.remove(value.toString());
					skus.add(skuProduct);
				} else {
					deleteSkus.add(sku);
				}
			}
			for (String deleteSku : deleteSkus) {
				deleteSkuProduct(deleteSku);
				logger.debug(this.getClass(), "czh", "删除商品sku " + deleteSku, command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
			}
		}
		command.setSpecValueIdList(specValues);
		skus.addAll(createSkuProduct(command));
		return skus;
	}

	public void deleteSkuProduct(String id) {
		SkuProductQO qo = new SkuProductQO();
		qo.setId(id);
		SkuProduct skuProduct = queryUnique(qo);
		if (skuProduct != null) {
			List<SpecDetail> details = skuProduct.getSpecDetails();
			for (SpecDetail detail : details) {
				specDetailService.deleteById(detail.getId());
			}
			deleteById(id);
		}
	}

	public void deleteSkuProductByProductId(String id) {
		SkuProductQO qo = new SkuProductQO();
		qo.setProductId(id);
		List<SkuProduct> skuProducts = queryList(qo);
		for (SkuProduct skuProduct : skuProducts) {
			if (skuProduct != null) {
				List<SpecDetail> details = skuProduct.getSpecDetails();
				for (SpecDetail detail : details) {
					specDetailService.deleteById(detail.getId());
				}
				deleteById(skuProduct.getId());
			}
		}
	}

	private boolean checkSkuProduct(String skuValue, List<String> specValues) {
		for (String specValue : specValues) {
			String sku = skuValue;
			String value = specValue.replaceAll(",", "");
			sku = sku.replaceAll(value, "");
			if (StringUtils.isBlank(sku)) {
				return true;
			}
		}
		return false;
	}

	@Transactional(rollbackFor = Exception.class)
	public void modifySkuProduct(ModifyProductSkuCommand command) {
		ProductQO productQo = new ProductQO();
		productQo.setId(command.getProductId());
		Product product = productService.queryUnique(productQo);

		// 需要添加的sku
		List<String> skuCode2update = command.getSpecValueIdList();

		// 移除不被使用的sku
		List<String> existSkuCode = command.getSkuList();
		for (int i = 0; i < existSkuCode.size(); i++) {
			String skuCode = existSkuCode.get(i);
			if (!checkSkuProductUsing(skuCode)) {
				deleteSkuProduct(skuCode);
				existSkuCode.set(i, null);
			}
		}

		for (int i = 0; i < skuCode2update.size(); i++) {
			for (int j = 0; j < existSkuCode.size(); j++) {
				if (skuCode2update.get(i) == null) {
					continue;
				}
				String expectSkuCode = product.getProductCode() + skuCode2update.get(i).replaceAll(",", "");
				if (existSkuCode.get(j) != null && expectSkuCode.equals(existSkuCode.get(j))) {
					skuCode2update.set(i, null);
				}

			}
		}

		for (int i = 0; i < skuCode2update.size(); i++) {

			String skuCodeArrStr = skuCode2update.get(i);
			if (skuCodeArrStr != null) {
				SkuProduct skuProduct = new SkuProduct();
				String skuCode = product.getProductCode() + skuCodeArrStr.replaceAll(",", "");
				skuProduct.create(skuCode, product.getId());
				// 添加sku
				save(skuProduct);
				String[] specValCodeArr = skuCodeArrStr.split(",");
				// 添加sku详情
				for (String specValCode : specValCodeArr) {
					SpecValueQO specValQo = new SpecValueQO();
					specValQo.setId(specValCode);
					SpecValue specVal = specValueService.queryUnique(specValQo);
					specDetailService.createSkuSpecDetail(specVal, skuProduct);

				}
			}

		}

	}

	public boolean checkSkuProductUsing(String skuCode) {
		PurchaseOrderDetailQo qo = new PurchaseOrderDetailQo();
		qo.setSkuProductCode(skuCode);
		PurchaseOrderDetail pruPurchaseOrderDetail = purchaseOrderDetailService.queryUnique(qo);
		// 被使用
		if (pruPurchaseOrderDetail != null) {
			return true;
		}
		return false;
	}

	/**
	 * 检测是否有任一sku使用
	 */
	public boolean checkProductAnySkuUsing(String productId) {
		SkuProductQO qo = new SkuProductQO();
		qo.setProductId(productId);
		List<SkuProduct> skuList = queryList(qo);
		for (SkuProduct skuProduct : skuList) {
			if (checkSkuProductUsing(skuProduct.getId())) {
				return true;
			}
		}
		return false;
	}

}
