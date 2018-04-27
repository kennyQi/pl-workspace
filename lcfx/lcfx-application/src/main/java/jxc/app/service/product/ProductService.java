package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateProductBaseInfoCommand;
import hg.pojo.command.DeleteProductCommand;
import hg.pojo.command.ImportBatchProductCommand;
import hg.pojo.command.ModifyProductCommand;
import hg.pojo.command.ModifyProductDescribeCommand;
import hg.pojo.command.ModifyProductSkuCommand;
import hg.pojo.command.ModifyProductStatusCommand;
import hg.pojo.dto.product.ProductDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.BrandQO;
import hg.pojo.qo.CategoryQO;
import hg.pojo.qo.DealerProductMappingQO;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.SkuProductQO;
import hg.pojo.qo.SpecDetailQO;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;
import hg.pojo.qo.UnitQO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxc.app.dao.product.ProductDao;
import jxc.app.service.image.ProductImageService;
import jxc.app.service.system.UnitService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.Constants;
import jxc.domain.model.product.Brand;
import jxc.domain.model.product.DealerProductMapping;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductCategory;
import jxc.domain.model.product.ProductStatus;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;
import jxc.domain.model.product.Specification;
import jxc.domain.model.system.Project;
import jxc.domain.model.system.Unit;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("productService")
public class ProductService extends BaseServiceImpl<Product, ProductQO, ProductDao> {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private SkuProductService skuProductService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private SpecDetailService specDetailService;
	@Autowired
	private SpecificationService specificationService;
	@Autowired
	private SpecValueService specValueService;
	@Autowired
	private DealerProductMappingService dealerProductMappingService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private SupplierListOfProductService supplierListService;

	@Autowired
	private ProductSnapshotService snapshotService;

	@Autowired
	private JxcLogger logger;

	@Transactional(rollbackFor = Exception.class)
	public String importProduct(ImportBatchProductCommand batchProductCommand) {
		JSONArray message = new JSONArray();
		List<ProductDTO> products = batchProductCommand.getProductList();
		Map<CreateProductBaseInfoCommand, ModifyProductSkuCommand> command = new HashMap<CreateProductBaseInfoCommand, ModifyProductSkuCommand>();
		if (products != null && products.size() > 0) {
			List<String> specValueIdList = null;
			ModifyProductSkuCommand skuCommand = null;
			CreateProductBaseInfoCommand productCommand = null;
			JSONObject obj = null;
			Product prod = null;
			Brand brand = null;
			Unit unit = null;
			ProductCategory productCategory = null;
			List<Specification> specifications = specificationService.queryList(new SpecificationQO());
			boolean flag = false;
			// 将所有规格+规格值存入specMapAll进行比较
			Map<String, List<SpecValue>> specMapAll = new HashMap<String, List<SpecValue>>();
			for (Specification specification : specifications) {
				SpecValueQO specValueQo = new SpecValueQO();
				specValueQo.setSpecificationId(specification.getId());
				List<SpecValue> list = specValueService.queryList(specValueQo);
				specMapAll.put(specification.getId(), list);
			}
			List<SpecValue> specs = null;
			for (int i = 0; i < products.size(); i++) {
				boolean valueFlag = true;
				specs = new ArrayList<SpecValue>();
				obj = new JSONObject();
				JSONArray error = new JSONArray();
				ProductDTO productDTO = products.get(i);
				CategoryQO categoryQo = new CategoryQO();
				// 验证必填项是否填写
				checkValue(productDTO, error);

				categoryQo.setName(productDTO.getCategoryName());
				productCategory = productCategoryService.queryUnique(categoryQo);
				if (productCategory == null) {
					obj.put("行数", i + 2);
					obj.put("错误", "该商品分类不存在");
					message.add(obj);
					continue;
				}
				specifications = productCategory.getSpecList();
				if (StringUtils.isBlank(productDTO.getSpecValue())) {
					valueFlag = false;
				}
				String[] values = productDTO.getSpecValue().split(" ");
				// 规格值判空
				if (valueFlag) {
					// 判断该规格值是否存在
					if (!checkSpecValue(specifications, specMapAll, values, specs)) {
						obj.put("行数", i + 2);
						obj.put("错误", "该规格异常,请填写正确的规格值");
						message.add(obj);
						continue;
					}
				}
				ProductQO productQo = new ProductQO();
				productQo.setName(productDTO.getProductName());
				Product prodUnique = queryUnique(productQo);
				// 判断该商品及该规格是否已存在
				if (!checkProduct(prodUnique, values)) {
					obj.put("行数", i + 2);
					obj.put("错误", "该商品及规格已存在");
					message.add(obj);
					continue;
				}
				if (prod != null) {
					// 判断是否为同一商品
					if (productDTO.getProductName().equals(prod.getProductName())) {
						flag = true;
					} else {
						flag = false;
					}
				}
				if (!flag) {
					prod = new Product();
					prod.setProductName(productDTO.getProductName());
					BrandQO brandQo = new BrandQO();
					brandQo.setChineseName(productDTO.getBrandName());
					brand = brandService.queryUnique(brandQo);
					if (brand == null) {
						error.add(productDTO.getBrandName() + "该商品品牌不存在");
					}
					if (StringUtils.isNotBlank(productDTO.getUnitName())) {
						UnitQO unitQo = new UnitQO();
						unitQo.setName(productDTO.getUnitName());
						unit = unitService.queryUnique(unitQo);
						if (unit == null) {
							error.add(productDTO.getUnitName() + "该单位不存在");
						}
					}
					ProductStatus status = new ProductStatus();
					if ("启用".equals(productDTO.getUsing())) {
						status.setUsing(true);
					} else if ("禁用".equals(productDTO.getUsing())) {
						status.setUsing(false);
					} else {
						error.add(productDTO.getUsing() + "不是正确的商品状态");
					}
					int attribute = 0;
					if ("虚拟商品".equals(productDTO.getAttribute())) {
						attribute = Constants.PRODUCT_ATTRIBUTE_VIRTUAL;
					} else if ("普通商品".equals(productDTO.getAttribute())) {
						attribute = Constants.PRODUCT_ATTRIBUTE_NORMAL;
					} else {
						error.add(productDTO.getAttribute() + "不是正确的商品属性");
					}
					// 如果没有错误信息则把数据插入command
					if (error.size() == 0) {
						if (productCommand != null) {
							if (specValueIdList != null && specValueIdList.size() > 0) {
								skuCommand.setOperatorAccount(batchProductCommand.getOperatorAccount());
								skuCommand.setOperatorName(batchProductCommand.getOperatorName());
								skuCommand.setOperatorType(batchProductCommand.getOperatorType());
								skuCommand.setSpecValueIdList(specValueIdList);
							}
							productCommand.setOperatorAccount(batchProductCommand.getOperatorAccount());
							productCommand.setOperatorName(batchProductCommand.getOperatorName());
							productCommand.setOperatorType(batchProductCommand.getOperatorType());
							command.put(productCommand, skuCommand);
						}
						productCommand = productDTOToProductCommand(productDTO, productCategory, brand, unit, status, attribute, error);
						if (valueFlag) {
							skuCommand = new ModifyProductSkuCommand();
							String specificationIdList = "";
							String specValueIds = "";
							specValueIdList = new ArrayList<String>();

							for (SpecValue spec : specs) {
								specificationIdList += spec.getSpecification().getId() + ",";
								specValueIds += spec.getId() + ",";
							}
							specValueIdList.add(specValueIds);
							skuCommand.setSpecificationIdList(specificationIdList);
						} else {
							skuCommand = null;
						}
					}
				} else {
					if (error.size() == 0) {
						String specValueIds = "";
						for (SpecValue spec : specs) {
							specValueIds += spec.getId() + ",";
						}
						specValueIdList.add(specValueIds);
					}
				}
				if (error.size() > 0) {
					obj.put("行数", i + 2);
					obj.put("错误", error);
					message.add(obj);
				}
				if (i == products.size() - 1) {
					if (specValueIdList != null && specValueIdList.size() > 0) {
						skuCommand.setOperatorAccount(batchProductCommand.getOperatorAccount());
						skuCommand.setOperatorName(batchProductCommand.getOperatorName());
						skuCommand.setOperatorType(batchProductCommand.getOperatorType());
						skuCommand.setSpecValueIdList(specValueIdList);
					}
					productCommand.setOperatorAccount(batchProductCommand.getOperatorAccount());
					productCommand.setOperatorName(batchProductCommand.getOperatorName());
					productCommand.setOperatorType(batchProductCommand.getOperatorType());
					command.put(productCommand, skuCommand);
				}
			}
		}
		if (message.size() > 0) {
			return message.toJSONString();
		} else {
			createProductBatch(command);
			logger.debug(this.getClass(), "czh", "导入商品" + command.size() + "个", batchProductCommand.getOperatorName(), batchProductCommand.getOperatorType(),
					batchProductCommand.getOperatorAccount(), "");
			return "";
		}
	}

	public void createProductBatch(Map<CreateProductBaseInfoCommand, ModifyProductSkuCommand> commands) {
		for (Map.Entry<CreateProductBaseInfoCommand, ModifyProductSkuCommand> command : commands.entrySet()) {
			Product product = createProduct(command.getKey());
			ModifyProductSkuCommand skuCommand = command.getValue();
			if (skuCommand != null) {
				skuCommand.setProductId(product.getId());
				skuProductService.createSkuProduct(skuCommand);
			}
		}
	}

	/**
	 * 将数据存入command
	 * 
	 * @param attribute
	 * @param status
	 * @param unit
	 * @param brand
	 * @param productCategory
	 * @return
	 */
	private CreateProductBaseInfoCommand productDTOToProductCommand(ProductDTO productDTO, ProductCategory productCategory, Brand brand, Unit unit,
			ProductStatus status, int attribute, JSONArray error) {

		CreateProductBaseInfoCommand createProductCommand = new CreateProductBaseInfoCommand();
		createProductCommand.setCategoryId(productCategory.getId());
		createProductCommand.setProductName(productDTO.getProductName());
		createProductCommand.setBrandId(brand.getId());
		if (unit != null) {
			createProductCommand.setUnitId(unit.getId());
		}
		createProductCommand.setUsing(status.getUsing());
		createProductCommand.setAttribute(attribute);
		createProductCommand.setWeight(productDTO.getWeight());
		createProductCommand.setOutStockWeight(productDTO.getOutStockWeight());
		createProductCommand.setRemark(productDTO.getRemark());

		createProductCommand.setSizeWidth(productDTO.getSizeWidth());
		createProductCommand.setSizeHigh(productDTO.getSizeHigh());
		createProductCommand.setSizeLong(productDTO.getSizeLong());
		createProductCommand.setOutstockSizeWidth(productDTO.getOutstockSizeWidth());
		createProductCommand.setOutstockSizeHigh(productDTO.getOutstockSizeHigh());
		createProductCommand.setOutstockSizeLong(productDTO.getOutstockSizeLong());

		return createProductCommand;
	}

	/**
	 * 验证商品导入必填项是否填写
	 * 
	 * @param row
	 *            导入数据
	 * @param obj
	 * @return
	 */
	private boolean checkValue(ProductDTO productDTO, JSONArray error) {
		if (StringUtils.isBlank(productDTO.getCategoryName())) {
			error.add("商品分类未填写");
			return false;
		} else if (StringUtils.isBlank(productDTO.getProductName())) {
			error.add("商品名称未填写");
			return false;
		} else if (StringUtils.isBlank(productDTO.getBrandName())) {
			error.add("商品品牌未填写");
			return false;
		} else if (StringUtils.isBlank(productDTO.getUsing())) {
			error.add("商品状态未填写");
			return false;
		} else if (StringUtils.isBlank(productDTO.getAttribute())) {
			error.add("商品类型未填写");
			return false;
		}
		return true;
	}

	/**
	 * 判断商品及规格是否已存在
	 * 
	 * @param name
	 *            商品名
	 * @param specs
	 *            规格值
	 * @return
	 */
	private boolean checkProduct(Product prod, String[] specs) {
		if (prod == null) {
			return true;
		} else if (StringUtils.isBlank(specs[0])) {
			return false;
		} else {
			List<String> specValues = new ArrayList<String>();
			SkuProductQO skuProductQo = new SkuProductQO();
			skuProductQo.setProductId(prod.getId());
			List<SkuProduct> skus = skuProductService.queryList(skuProductQo);
			// 获取当前商品的所有规格值
			SpecDetailQO qo = null;
			for (SkuProduct sku : skus) {
				qo = new SpecDetailQO();
				qo.setSkuProductId(sku.getId());
				List<SpecDetail> details = specDetailService.queryList(qo);
				String specValueCode = "";
				for (SpecDetail detail : details) {
					specValueCode += "," + detail.getSpecValue().getSpecValue();
				}
				specValues.add(specValueCode);
			}

			// 比对当前规格值是否存在于已有规格值中，如发现不存在则说明为新规格值返回true
			for (String specValue : specValues) {
				boolean flag = false;
				for (String spec : specs) {
					if (specValue.contains(spec)) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
				if (flag) {
					return false;
				}
			}
			return true;
		}
	}

	public HSSFWorkbook exportProduct(ProductQO qo) {
		List<Product> productList = queryList(qo);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("商品");
		// 表格的字段
		String[] headers = "商品分类名称,商品名称,商品品牌名称,规格,计量单位名称,商品状态,商品属性,商品重量 kg,商品尺寸（长）cm,商品尺寸（宽）cm,商品尺寸（高）cm,商品出库重量 kg,出库尺寸（长）cm,出库尺寸（宽）cm,出库尺寸（高）cm,备注信息,商品编码,sku编码"
				.split(",");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<Product> it = productList.iterator();
		int index = 0;
		while (it.hasNext()) {
			Product t = (Product) it.next();
			SkuProductQO skuProductQo = new SkuProductQO();
			skuProductQo.setProductId(t.getId());
			List<SkuProduct> skuList = skuProductService.queryList(skuProductQo);
			if (skuList != null && skuList.size() > 0) {
				for (SkuProduct sku : skuList) {
					index++;
					row = sheet.createRow(index);
					importDataToRow(t, sku, row);
				}
			} else {
				index++;
				row = sheet.createRow(index);
				importDataToRow(t, null, row);
			}
		}
		return workbook;
	}

	/**
	 * 将sku对象转换为row对象
	 * 
	 * @param sku
	 * @param row
	 */
	private void importDataToRow(Product prod, SkuProduct sku, HSSFRow row) {
		List<String> data = new ArrayList<String>();
		data.add(prod.getProductCategory().getName());
		data.add(prod.getProductName());
		data.add(prod.getProductBrand().getChineseName());
		StringBuffer specValue = new StringBuffer();
		if (sku != null) {
			SpecDetailQO specDtailQo = new SpecDetailQO();
			specDtailQo.setSkuProductId(sku.getId());
			List<SpecDetail> specDetails = specDetailService.queryList(specDtailQo);
			for (SpecDetail specDetail : specDetails) {
				specValue.append(specDetail.getSpecValue().getSpecValue()).append(" ");
			}
			if (specValue.length() > 0) {
				specValue.deleteCharAt(specValue.length() - 1);
			}
		}
		data.add(specValue.toString());
		if (prod.getUnit() != null) {
			data.add(prod.getUnit().getUnitName());
		} else {
			data.add("");
		}
		String status = "";
		if (prod.getStatus().getUsing()) {
			status = "启用";
		} else {
			status = "禁用";
		}
		data.add(status);
		String attribute = "";
		if (prod.getAttribute() == Constants.PRODUCT_ATTRIBUTE_NORMAL) {
			attribute = "普通商品";
		} else {
			attribute = "虚拟商品";
		}
		data.add(attribute);
		if (prod.getWeight() != null) {
			data.add(prod.getWeight().toString());
		} else {
			data.add("");
		}

		// 长宽高
		if (prod.getSizeLong() != null) {
			data.add(prod.getSizeLong().toString());
		} else {
			data.add("");
		}
		if (prod.getSizeWidth() != null) {
			data.add(prod.getSizeWidth().toString());
		} else {
			data.add("");
		}
		if (prod.getSizeHigh() != null) {
			data.add(prod.getSizeHigh().toString());
		} else {
			data.add("");
		}

		// 出库重量
		if (prod.getOutStockWeight() != null) {
			data.add(prod.getOutStockWeight().toString());
		} else {
			data.add("");
		}

		if (prod.getOutstockSizeLong() != null) {
			data.add(prod.getOutstockSizeLong().toString());
		} else {
			data.add("");
		}
		if (prod.getOutstockSizeWidth() != null) {
			data.add(prod.getOutstockSizeWidth().toString());
		} else {
			data.add("");
		}
		if (prod.getOutstockSizeHigh() != null) {
			data.add(prod.getOutstockSizeHigh().toString());
		} else {
			data.add("");
		}

		data.add(StringUtils.isNotBlank(prod.getRemark()) ? prod.getRemark() : "");

		data.add(prod.getProductCode());
		data.add(sku.getId());

		for (int i = 0; i < data.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(data.get(i));
		}
	}

	/**
	 * 验证规格值是否存在
	 * 
	 * @param specifications
	 *            该分类下的规格集合
	 * @param specMapAll
	 *            所有的规格及对应的规格值Map
	 * @param values
	 *            要验证的规格值 例： 红 XL
	 * @param specs
	 *            返回当前规格值对应的对象集合
	 * @param error
	 *            返回错误信息
	 * @return
	 */
	private boolean checkSpecValue(List<Specification> specifications, Map<String, List<SpecValue>> specMapAll, String[] values, List<SpecValue> specs) {
		Map<String, SpecValue> specValues = new HashMap<String, SpecValue>();
		for (Specification specification : specifications) {
			List<SpecValue> value = specMapAll.get(specification.getId());
			for (SpecValue specValue : value) {
				specValues.put(specValue.getSpecValue(), specValue);
			}
		}
		for (String value : values) {
			SpecValue specValue = specValues.get(value);
			if (specValue != null) {
				specs.add(specValue);
			} else {
				return false;
			}
		}
		return true;
	}

	public Product createProduct(CreateProductBaseInfoCommand command) {
		Product product = new Product();
		product.createProductCommand(command);
		product = save(product);
		logger.debug(this.getClass(), "czh", "新增商品" + command.getProductName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");

		snapshotService.createProductSnap(product);
		supplierListService.resetSupplierList(product.getId(), command.getSupplierList());
		return product;
	}

	@Transactional(rollbackFor = Exception.class)
	public Product updateProduct(ModifyProductCommand command) throws ProductException {
		if (productNameIsExisted(command.getProductName(), command.getProductId(), false)) {
			throw new ProductException(null, "商品名称已存在");

		}
		ProductQO qo = new ProductQO();
		qo.setId(command.getProductId());
		Product product = queryUnique(qo);

		boolean categoryChanged = false;
		if (!product.getProductCategory().getId().equals(command.getCategoryId())) {
			if (skuProductService.checkProductAnySkuUsing(command.getProductId())) {
				throw new ProductException(null, "该商品有sku被使用，不能更改所属分类");
			}
			categoryChanged = true;
		}

		product.modifyProductCommand(command);
		product = update(product);
		// 清空sku、平台对照
		if (categoryChanged == true) {
			dealerProductMappingService.deleteByProductId(product.getId());
			skuProductService.deleteSkuProductByProductId(product.getId());
		}

		logger.debug(this.getClass(), "czh", "修改商品" + command.getProductName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");
		snapshotService.createProductSnap(product);
		supplierListService.resetSupplierList(product.getId(), command.getSupplierList());

		return product;
	}

	public void deleteProduct(DeleteProductCommand command) throws ProductException {
		List<String> idList = command.getProductListId();
		for (String prodIdStr : idList) {
			if (skuProductService.checkProductAnySkuUsing(prodIdStr)) {
				throw new ProductException(null, "商品sku被使用，删除失败");
			}
		}

		List<String> ids = command.getProductListId();
		for (String id : ids) {
			ProductQO productQo = new ProductQO();
			productQo.setId(id);
			Product product = queryUnique(productQo);

			dealerProductMappingService.deleteByProductId(id);

			if (product != null) {
				logger.debug(this.getClass(), "czh", "删除商品" + product.getProductName(), command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
				SkuProductQO skuProductqo = new SkuProductQO();
				skuProductqo.setProductId(product.getId());
				List<SkuProduct> skus = skuProductService.queryList(skuProductqo);
				if (skus != null && skus.size() > 0) {
					for (SkuProduct skuProduct : skus) {
						skuProductService.deleteSkuProduct(skuProduct.getId());
					}
				}

				product.setStatusRemoved(true);
				update(product);
			}
		}

	}

	public Product updateProductDescribe(ModifyProductDescribeCommand command) {
		ProductQO productQo = new ProductQO();
		productQo.setId(command.getProductId());
		Product product = queryUnique(productQo);
		if (product != null) {
			product.setProductDescribe(command.getProductDescribe());
			product = update(product);
			logger.debug(this.getClass(), "czh", "修改商品描述" + product.getProductName(), command.getOperatorName(), command.getOperatorType(),
					command.getOperatorAccount(), "");
		}
		return product;

	}

	public List<Product> updateProductStatus(ModifyProductStatusCommand command) {
		List<String> ids = command.getProductListId();
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < ids.size(); i++) {
			ProductQO productQo = new ProductQO();
			productQo.setId(ids.get(i));
			Product product = queryUnique(productQo);
			if (product != null) {
				ProductStatus status = new ProductStatus();
				status.setUsing(command.getUsing());
				status.setSettingOutStockParam(product.getStatus().getSettingOutStockParam());
				product.setStatus(status);
				product = update(product);
				String using = command.getUsing() ? "启用" : "禁用";
				logger.debug(this.getClass(), "czh", "修改商品状态" + product.getProductName() + ":" + using, command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
				products.add(product);
			}
		}

		return products;

	}

	public Set<Project> findProjectByProductId(String productId) {
		Set<Project> projects = new HashSet<Project>();
		SkuProductQO skuProductQo = new SkuProductQO();
		skuProductQo.setProductId(productId);
		List<SkuProduct> skus = skuProductService.queryList(skuProductQo);
		for (SkuProduct skuProduct : skus) {
			DealerProductMappingQO dealerProductMappingQo = new DealerProductMappingQO();
			dealerProductMappingQo.setSkuProductId(skuProduct.getId());
			List<DealerProductMapping> mappings = dealerProductMappingService.queryList(dealerProductMappingQo);
			for (DealerProductMapping dealerProductMapping : mappings) {
				projects.add(dealerProductMapping.getProject());
			}
		}
		return projects;
	}

	public Set<DealerProductMapping> findDealerProductMappingByProductIdAndSequence(String productId, Integer sequence) {
		Set<DealerProductMapping> dealerProductMappings = new HashSet<DealerProductMapping>();
		SkuProductQO skuProductQo = new SkuProductQO();
		skuProductQo.setProductId(productId);
		List<SkuProduct> skus = skuProductService.queryList(skuProductQo);
		for (SkuProduct skuProduct : skus) {
			DealerProductMappingQO dealerProductMappingQo = new DealerProductMappingQO();
			dealerProductMappingQo.setSkuProductId(skuProduct.getId());
			dealerProductMappingQo.setSequence(sequence);
			List<DealerProductMapping> mappings = dealerProductMappingService.queryList(dealerProductMappingQo);
			for (DealerProductMapping dealerProductMapping : mappings) {
				dealerProductMappings.add(dealerProductMapping);
			}
		}
		return dealerProductMappings;
	}

	public Project findProjectByProductIdAndSequence(String productId, Integer sequence) {
		Project project = new Project();
		SkuProductQO skuProductQo = new SkuProductQO();
		skuProductQo.setProductId(productId);
		List<SkuProduct> skus = skuProductService.queryList(skuProductQo);
		for (SkuProduct skuProduct : skus) {
			DealerProductMappingQO dealerProductMappingQo = new DealerProductMappingQO();
			dealerProductMappingQo.setSkuProductId(skuProduct.getId());
			dealerProductMappingQo.setSequence(sequence);
			List<DealerProductMapping> mappings = dealerProductMappingService.queryList(dealerProductMappingQo);
			if (mappings != null && mappings.size() > 0) {
				DealerProductMapping mapping = mappings.get(0);
				if (mapping != null) {
					project = mapping.getProject();
					return project;
				}
			}
		}
		return null;
	}

	public Set<Integer> findSequenceByProductId(String productId) {
		Set<Integer> sequence = new HashSet<Integer>();
		SkuProductQO skuProductQo = new SkuProductQO();
		skuProductQo.setProductId(productId);
		List<SkuProduct> skus = skuProductService.queryList(skuProductQo);
		for (SkuProduct skuProduct : skus) {
			DealerProductMappingQO dealerProductMappingQo = new DealerProductMappingQO();
			dealerProductMappingQo.setSkuProductId(skuProduct.getId());
			List<DealerProductMapping> mappings = dealerProductMappingService.queryList(dealerProductMappingQo);
			for (DealerProductMapping dealerProductMapping : mappings) {
				sequence.add(dealerProductMapping.getSequence());
			}
		}
		return sequence;
	}

	/**
	 * 判断商品名称唯一性
	 * 
	 * @return
	 */

	public boolean productNameIsExisted(String name, String id, boolean isCreate) {
		ProductQO qo = new ProductQO();
		qo.setName(name);
		Product product = queryUnique(qo);

		if (isCreate) {
			if (product != null) {
				return true;
			}
		} else {
			if (product != null && !id.equals(product.getId())) {
				return true;
			}
		}
		return false;

	}

	@Override
	protected ProductDao getDao() {
		return productDao;
	}

}
